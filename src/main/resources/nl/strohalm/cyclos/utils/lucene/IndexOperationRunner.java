/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.utils.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import nl.strohalm.cyclos.dao.IndexOperationDAO;
import nl.strohalm.cyclos.entities.IndexOperation;
import nl.strohalm.cyclos.entities.IndexOperation.EntityType;
import nl.strohalm.cyclos.entities.IndexOperation.OperationType;
import nl.strohalm.cyclos.entities.IndexStatus;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.instance.InstanceHandler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Keeps polling the {@link IndexOperation} entities and applying them
 * 
 * @author luis
 */
public class IndexOperationRunner implements Runnable, InitializingBean, DisposableBean {

    private static final String                LAST_OPERATION_TIME     = "lastOperationTime";
    private static final String                LAST_OPERATION_ID       = "lastOperationId";
    private static final long                  SLEEP_TIME              = 20 * DateUtils.MILLIS_PER_SECOND;

    private static final Log                   LOG                     = LogFactory.getLog(IndexOperationRunner.class);

    private Thread                             thread;
    private File                               statusFile;
    private Properties                         status;
    private Calendar                           lastOperationTime;
    private Long                               lastOperationId;
    private PlatformTransactionManager         transactionManager;
    private TransactionHelper                  transactionHelper;
    private TransactionTemplate                readonlyTransactionTemplate;
    private AlertServiceLocal                  alertService;
    private MessageResolver                    messageResolver;
    private IndexHandler                       indexHandler;
    private InstanceHandler                    instanceHandler;
    private SessionFactory                     sessionFactory;
    private Map<Class<?>, IndexWriter>         cachedWriters;
    private SettingsServiceLocal               settingsService;
    private ApplicationServiceLocal            applicationService;
    private IndexOperationDAO                  indexOperationDao;

    private final List<IndexOperationListener> indexOperationListeners = new ArrayList<IndexOperationListener>();

    public void addIndexOperationListener(final IndexOperationListener listener) {
        indexOperationListeners.add(listener);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Our transaction template will be read-only
        readonlyTransactionTemplate = new TransactionTemplate(transactionManager);
        readonlyTransactionTemplate.setReadOnly(true);

        cachedWriters = new HashMap<Class<?>, IndexWriter>();

        statusFile = new File(indexHandler.getIndexRoot(), "status");
        status = new Properties();
        try {
            status.load(new FileReader(statusFile));
            long time = Long.parseLong(status.getProperty(LAST_OPERATION_TIME));
            lastOperationTime = new GregorianCalendar();
            lastOperationTime.setTimeInMillis(time);
            lastOperationId = Long.parseLong(status.getProperty(LAST_OPERATION_ID));
        } catch (Exception e) {
            // Ok, ignore. We'll start with empty properties
            lastOperationTime = null;
            lastOperationId = null;
        }

        // Start the thread
        thread = new Thread(this, "IndexOperationRunner");
        thread.start();
    }

    @Override
    public void destroy() {
        // Stop the thread
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }

        // Close all index writers
        for (final Map.Entry<Class<?>, IndexWriter> entry : cachedWriters.entrySet()) {
            try {
                final IndexWriter writer = entry.getValue();
                writer.close();
            } catch (final Exception e) {
                LOG.warn("Error closing index writer for " + ClassHelper.getClassName(entry.getKey()), e);
            }
        }
        cachedWriters.clear();
    }

    @Override
    public void run() {
        try {
            if (applicationService == null) {
                // When running setup, there are no services - we don't have anything to do then
                return;
            }
            // First, wait until the application is fully initialized. Otherwise, we can have problems, like message handler not being complete yet
            while (!applicationService.isInitialized()) {
                Thread.sleep(SLEEP_TIME);
            }
            while (true) {
                try {
                    if (status.isEmpty()) {
                        // No status means we don't know which was last event, hence we must rebuild all indexes
                        initialRebuild();
                        // After rebuilding all indexes, the status will no longer be empty
                    } else {
                        runNextOperations();
                    }
                } catch (Exception e) {
                    LOG.error("Error on IndexOperationRunner", e);
                }
                Thread.sleep(SLEEP_TIME);
            }
        } catch (final InterruptedException e) {
            // Interrupted. Just leave the loop
        }
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        this.applicationService = applicationService;
    }

    public void setIndexHandler(final IndexHandler indexHandler) {
        this.indexHandler = indexHandler;
    }

    public void setIndexOperationDao(final IndexOperationDAO indexOperationDao) {
        this.indexOperationDao = indexOperationDao;
    }

    public void setInstanceHandler(final InstanceHandler instanceHandler) {
        this.instanceHandler = instanceHandler;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTransactionManager(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    private void add(final Class<? extends Indexable> entityType, final Long id) {
        IndexWriter writer = null;
        try {
            writer = getWriter(entityType);
            final Analyzer analyzer = getAnalyzer();
            Document document = readonlyTransactionTemplate.execute(new TransactionCallback<Document>() {
                @Override
                public Document doInTransaction(final TransactionStatus status) {
                    try {
                        Session session = getSession();
                        Indexable entity = (Indexable) session.load(entityType, id);
                        DocumentMapper documentMapper = indexHandler.getDocumentMapper(entityType);
                        if (entityType.equals(Member.class)) {
                            rebuildMemberAds(id, analyzer, session);
                        }
                        if (entityType.equals(Administrator.class) || entityType.equals(Member.class)) {
                            rebuildMemberRecords(id, analyzer, session);
                        }
                        return documentMapper.map(entity);
                    } catch (ObjectNotFoundException e) {
                        return null;
                    } catch (EntityNotFoundException e) {
                        return null;
                    }
                }
            });
            if (document != null) {
                writer.updateDocument(new Term("id", document.get("id")), document, analyzer);
                commit(entityType, writer);
            }
        } catch (CorruptIndexException e) {
            handleIndexCorrupted(entityType);
        } catch (Exception e) {
            LOG.warn("Error adding entity to search index: " + ClassHelper.getClassName(entityType) + "#" + id, e);
            rollback(entityType, writer);
        }
    }

    private void commit(final Class<? extends Indexable> entityType, final IndexWriter writer) {
        try {
            writer.commit();
        } catch (CorruptIndexException e) {
            handleIndexCorrupted(entityType);
        } catch (Exception e) {
            LOG.warn("Error while committing index writer for " + ClassHelper.getClassName(entityType), e);
        }
    }

    private void createAlert(final SystemAlert.Alerts type, final Class<? extends Indexable> entityType) {
        transactionHelper.runInNewTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                alertService.create(type, resolveAlertArguments(entityType));
            }
        });
    }

    private Analyzer getAnalyzer() {
        return settingsService.getLocalSettings().getLanguage().getAnalyzer();
    }

    private Session getSession() {
        return SessionFactoryUtils.getSession(sessionFactory, true);
    }

    /**
     * Returns an {@link IndexWriter} for the given entity type
     */
    private synchronized IndexWriter getWriter(final Class<? extends Indexable> entityType) {
        IndexWriter writer = cachedWriters.get(entityType);
        if (writer == null) {
            final Analyzer analyzer = getAnalyzer();
            try {
                final Directory directory = indexHandler.getDirectory(entityType);
                IndexWriter.unlock(directory);
                IndexWriterConfig config = new IndexWriterConfig(LuceneUtils.LUCENE_VERSION, analyzer);
                writer = new IndexWriter(directory, config);
                cachedWriters.put(entityType, writer);
            } catch (CorruptIndexException e) {
                handleIndexCorrupted(entityType);
                throw new DaoException(e);
            } catch (final Exception e) {
                LOG.warn("Error while opening index for write on " + ClassHelper.getClassName(entityType), e);
                throw new DaoException(e);
            }
        }
        return writer;
    }

    private void handleIndexCorrupted(final Class<? extends Indexable> entityType) {
        LOG.error("Search index corrupted for " + ClassHelper.getClassName(entityType) + ". Rebuilding index...");
        rebuild(entityType, true, true);
        LOG.info("Search index rebuilt after being corrupted for " + ClassHelper.getClassName(entityType));
    }

    private void initialRebuild() {
        IndexOperation operation = readonlyTransactionTemplate.execute(new TransactionCallback<IndexOperation>() {
            @Override
            public IndexOperation doInTransaction(final TransactionStatus status) {
                return indexOperationDao.last();
            }
        });
        rebuildAll(operation);
    }

    private void persistStatus(final Calendar time, final Long id) {
        lastOperationTime = time;
        lastOperationId = id;
        if (lastOperationTime != null && lastOperationId != null) {
            status.setProperty(LAST_OPERATION_TIME, lastOperationTime.getTimeInMillis() + "");
            status.setProperty(LAST_OPERATION_ID, lastOperationId + "");
        } else {
            status.clear();
        }
        try {
            status.store(new FileWriter(statusFile), "");
        } catch (IOException e) {
            LOG.warn("Error while persisting indexing status", e);
        }
    }

    /**
     * Recreates an index. If the force parameter is false, execute only if the index is corrupt or missing
     */
    private void rebuild(final Class<? extends Indexable> entityType, final boolean force, final boolean createAlert) {
        boolean execute = true;
        // When not forced, run only
        if (!force) {
            final IndexStatus status = indexHandler.getIndexStatus(entityType);
            execute = status != IndexStatus.CORRUPT && status != IndexStatus.MISSING;
        }
        if (!execute) {
            return;
        }

        if (createAlert) {
            // Create the alert for index rebuilding
            createAlert(SystemAlert.Alerts.INDEX_REBUILD_START, entityType);
        }

        IndexWriter indexWriter = cachedWriters.get(entityType);
        if (indexWriter != null) {
            try {
                indexWriter.close();
            } catch (final Exception e) {
                // Silently ignore
            }
            cachedWriters.remove(entityType);
        }
        // Remove all files and recreate the directory
        final File dir = indexHandler.getIndexDir(entityType);
        try {
            FileUtils.deleteDirectory(dir);
        } catch (final IOException e) {
            // Silently ignore
        }
        dir.mkdirs();

        final DocumentMapper documentMapper = indexHandler.getDocumentMapper(entityType);
        final IndexWriter writer = getWriter(entityType);

        // Now, we should add all entities to the index
        boolean success = readonlyTransactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(final TransactionStatus status) {
                Session session = getSession();
                ScrollableResults scroll = session.createQuery(resolveHql(entityType)).scroll(ScrollMode.FORWARD_ONLY);

                try {
                    int index = 0;
                    while (scroll.next()) {
                        Indexable entity = (Indexable) scroll.get(0);
                        Document document = documentMapper.map(entity);
                        try {
                            writer.addDocument(document);
                        } catch (CorruptIndexException e) {
                            handleIndexCorrupted(entityType);
                            return false;
                        } catch (IOException e) {
                            LOG.error("Error while adding document to index after rebuilding " + ClassHelper.getClassName(entityType), e);
                            return false;
                        }
                        // Every batch, clear the session and commit the writer
                        if (++index % 30 == 0) {
                            session.clear();
                            commit(entityType, writer);
                        }
                    }
                    return true;
                } finally {
                    scroll.close();
                }
            }
        });

        // Finish the writer operation
        try {
            if (success) {
                commit(entityType, writer);
            } else {
                rollback(entityType, writer);
            }
        } finally {
            if (createAlert) {
                // Create the alert for index rebuilding
                createAlert(SystemAlert.Alerts.INDEX_REBUILD_END, entityType);
            }
        }
    }

    private void rebuildAll(final IndexOperation last) {
        Calendar startTime = Calendar.getInstance();
        LOG.info("Rebuilding all search indexes...");

        // Create the alert for index rebuilding
        createAlert(SystemAlert.Alerts.INDEX_REBUILD_START, null);

        for (EntityType type : EntityType.values()) {
            long indexStart = System.currentTimeMillis();
            Class<? extends Indexable> entityClass = type.getEntityClass();
            rebuild(entityClass, true, false);
            LOG.debug("Search index for " + ClassHelper.getClassName(entityClass) + " was rebuilt in " + DateHelper.secondsSince(indexStart) + "s");
        }
        LOG.info("All search indexes rebuilt in " + DateHelper.secondsSince(startTime.getTimeInMillis()) + "s");

        // Create the alert for index rebuilding
        createAlert(SystemAlert.Alerts.INDEX_REBUILD_END, null);

        // Write the status to disk, so no longer the rebuild will be done
        Calendar time = last == null ? startTime : last.getDate();
        Long id = last == null ? 0L : last.getId();
        persistStatus(time, id);
    }

    private boolean rebuildMemberAds(final Long userId, final Analyzer analyzer, final Session session) {
        final Class<? extends Indexable> entityType = Ad.class;
        final IndexWriter writer = getWriter(entityType);
        boolean success = false;

        DocumentMapper documentMapper = indexHandler.getDocumentMapper(entityType);
        try {
            writer.deleteDocuments(new Term("owner", userId.toString()));
        } catch (CorruptIndexException e) {
            handleIndexCorrupted(entityType);
            success = false;
        } catch (IOException e) {
            LOG.error("Error while reindexing a member's advertisements", e);
            success = false;
        }

        ScrollableResults scroll = session.createQuery("from Ad a where a.deleteDate is null and a.owner.id = " + userId).scroll(ScrollMode.FORWARD_ONLY);

        try {
            int index = 0;
            while (scroll.next()) {
                Indexable entity = (Indexable) scroll.get(0);
                Document document = documentMapper.map(entity);
                try {
                    writer.addDocument(document, analyzer);
                } catch (CorruptIndexException e) {
                    handleIndexCorrupted(entityType);
                    success = false;
                    break;
                } catch (IOException e) {
                    LOG.error("Error while adding advertisements to index", e);
                    success = false;
                    break;
                }
                // Every batch, clear the session and commit the writer
                if (++index % 30 == 0) {
                    session.clear();
                }
            }
            success = true;
        } finally {
            scroll.close();
        }

        // Finish the writer operation
        if (success) {
            commit(entityType, writer);
            return true;
        } else {
            rollback(entityType, writer);
            return false;
        }
    }

    private boolean rebuildMemberRecords(final Long userId, final Analyzer analyzer, final Session session) {
        final Class<? extends Indexable> entityType = MemberRecord.class;
        final IndexWriter writer = getWriter(entityType);
        boolean success = false;

        DocumentMapper documentMapper = indexHandler.getDocumentMapper(entityType);
        try {
            writer.deleteDocuments(new Term("element", userId.toString()));
        } catch (CorruptIndexException e) {
            handleIndexCorrupted(entityType);
            success = false;
        } catch (IOException e) {
            LOG.error("Error while reindexing an user's records", e);
            success = false;
        }

        ScrollableResults scroll = session.createQuery("from MemberRecord mr where mr.element.id = " + userId).scroll(ScrollMode.FORWARD_ONLY);

        try {
            int index = 0;
            while (scroll.next()) {
                Indexable entity = (Indexable) scroll.get(0);
                Document document = documentMapper.map(entity);
                try {
                    writer.addDocument(document, analyzer);
                } catch (CorruptIndexException e) {
                    handleIndexCorrupted(entityType);
                    success = false;
                    break;
                } catch (IOException e) {
                    LOG.error("Error while adding member records to index", e);
                    success = false;
                    break;
                }
                // Every batch, clear the session and commit the writer
                if (++index % 30 == 0) {
                    session.clear();
                }
            }
            success = true;
        } finally {
            scroll.close();
        }

        // Finish the writer operation
        if (success) {
            commit(entityType, writer);
            return true;
        } else {
            rollback(entityType, writer);
            return false;
        }
    }

    /**
     * Removes the given entities from the index
     */
    private void remove(final Class<? extends Indexable> entityType, final Long id) {
        final IndexWriter writer = getWriter(entityType);
        try {
            writer.deleteDocuments(new TermQuery(new Term("id", id.toString())));
            commit(entityType, writer);
        } catch (CorruptIndexException e) {
            handleIndexCorrupted(entityType);
        } catch (final Exception e) {
            LOG.warn("Error removing from index " + ClassHelper.getClassName(entityType) + "#" + id, e);
            rollback(entityType, writer);
        }
    }

    private Object[] resolveAlertArguments(final Class<? extends Indexable> type) {
        String suffix;
        if (type == null) {
            suffix = "all";
        } else {
            suffix = ClassHelper.getClassName(type);
        }
        return new Object[] {
                messageResolver.message("adminTasks.indexes.type." + suffix),
                instanceHandler.getId() };
    }

    private String resolveHql(final Class<? extends Indexable> entityClass) {
        if (entityClass.equals(Ad.class)) {
            return "from Ad a where deleteDate is null";
        } else {
            return "from " + entityClass.getName();
        }
    }

    private synchronized void rollback(final Class<? extends Indexable> entityType, final IndexWriter writer) {
        if (writer == null) {
            return;
        }
        try {
            writer.rollback();
        } catch (Exception e) {
            LOG.error("Error while rolling back index writer for " + ClassHelper.getClassName(entityType), e);
        }
        // The index writer is closed by rollback. Invalidate it.
        cachedWriters.remove(entityType);
    }

    private void runNextOperations() {
        boolean hasMore = true;
        while (hasMore) {
            IndexOperation operation = readonlyTransactionTemplate.execute(new TransactionCallback<IndexOperation>() {
                @Override
                public IndexOperation doInTransaction(final TransactionStatus txStatus) {
                    IndexOperation operation = indexOperationDao.next(lastOperationTime, lastOperationId);
                    if (operation == null) {
                        return null;
                    }
                    // If the last event was before 24 hours ago (tolerance period for missed events), we will just rebuild all indexes
                    if ((System.currentTimeMillis() - operation.getDate().getTimeInMillis()) % DateUtils.MILLIS_PER_HOUR < 24) {
                        rebuildAll(operation);
                        IndexOperation indexOperation = new IndexOperation();
                        indexOperation.setOperationType(OperationType.REBUILD);
                        return indexOperation;
                    }
                    // "Normal" flow: execute the index operation
                    try {
                        long startTime = System.currentTimeMillis();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Running index operation: " + operation);
                        }
                        runOperation(operation);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Finished index operation: " + operation + " in " + DateHelper.secondsSince(startTime) + "s");
                        }
                    } catch (RuntimeException e) {
                        LOG.warn("Error running index operation " + operation, e);
                        throw e;
                    } finally {
                        // Write the properties to disk, so, when the server restarts, we know exactly where to resume
                        persistStatus(operation.getDate(), operation.getId());
                    }
                    return operation;
                }
            });
            // Notify registered listeners
            if (operation != null) {
                for (IndexOperationListener listener : indexOperationListeners) {
                    listener.onComplete(operation);
                }
            }
            hasMore = operation != null;
        }
    }

    private void runOperation(final IndexOperation operation) {
        // Perform the actual operation
        final Class<? extends Indexable> entityClass = operation.getEntityType().getEntityClass();
        OperationType operationType = operation.getOperationType();
        switch (operationType) {
            case REBUILD:
                rebuild(entityClass, true, true);
                break;
            case REBUILD_IF_CORRUPT:
                rebuild(entityClass, false, true);
                break;
            case ADD:
                add(entityClass, operation.getEntityId());
                break;
            case REMOVE:
                remove(entityClass, operation.getEntityId());
                break;

        }
    }

}
