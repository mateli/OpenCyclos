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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.IndexOperationDAO;
import nl.strohalm.cyclos.entities.IndexOperation;
import nl.strohalm.cyclos.entities.IndexOperation.EntityType;
import nl.strohalm.cyclos.entities.IndexOperation.OperationType;
import nl.strohalm.cyclos.entities.IndexStatus;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.utils.ClassHelper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Handles configuration and operation of Lucene indexes
 * @author luis
 */
public class IndexHandler implements InitializingBean, DisposableBean {

    private static final Log LOG = LogFactory.getLog(IndexHandler.class);

    /**
     * Returns the root directory where indexes are stored
     */
    public static File resolveIndexRoot() {
        // Setup the Lucene index directory to WEB-INF/indexes directory
        final File bin = FileUtils.toFile(IndexHandler.class.getResource("/")); // WEB-INF/classes
        File root = bin.getParentFile(); // WEB-INF
        // When running on the standalone server, the bin is root/bin, not root/web/WEB-INF/classes
        if (!bin.getAbsolutePath().contains("WEB-INF") && new File(root, "web").exists()) {
            root = new File(root, "web/WEB-INF");
        }
        return new File(root, "indexes"); // WEB-INF/indexes
    }

    private File                                            indexRoot;
    private IndexOperationDAO                               indexOperationDao;
    private Map<Class<? extends Indexable>, DocumentMapper> documentMappers;
    private Map<Class<? extends Indexable>, Directory>      directories;

    @Override
    public void afterPropertiesSet() throws Exception {
        indexRoot = resolveIndexRoot();
        if (!indexRoot.exists()) {
            indexRoot.mkdirs();
        }
        if (indexRoot == null) {
            throw new IllegalStateException("No write access to indexes directory");
        }
        // Initialize the directories
        directories = new HashMap<Class<? extends Indexable>, Directory>();
        for (EntityType entityType : EntityType.values()) {
            Class<? extends Indexable> entityClass = entityType.getEntityClass();
            final File dir = getIndexDir(entityClass);
            FSDirectory directory = FSDirectory.open(dir);
            directories.put(entityClass, directory);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (directories != null) {
            for (Map.Entry<Class<? extends Indexable>, Directory> entry : directories.entrySet()) {
                try {
                    entry.getValue().close();
                } catch (Exception e) {
                    LOG.warn("Error closing index directory for " + entry.getKey(), e);
                }
            }
            directories = null;
        }
    }

    /**
     * Returns the lucene directory for the given entity type
     */
    public Directory getDirectory(final Class<? extends Indexable> entityType) {
        return directories.get(entityType);
    }

    /**
     * Returns the {@link DocumentMapper} for the given entity type
     */
    public DocumentMapper getDocumentMapper(final Class<? extends Indexable> entityType) {
        return documentMappers.get(entityType);
    }

    /**
     * Returns the directory where the index is stored
     */
    public File getIndexDir(final Class<? extends Indexable> entityType) {
        return new File(indexRoot, ClassHelper.getClassName(entityType));
    }

    /**
     * Returns the root directory for all indexes
     */
    public File getIndexRoot() {
        return indexRoot;
    }

    /**
     * Returns the index status for the given entity type
     */
    public IndexStatus getIndexStatus(final Class<? extends Indexable> entityType) {
        IndexReader reader;
        try {
            reader = doOpenReader(entityType);
        } catch (final FileNotFoundException e) {
            return IndexStatus.MISSING;
        } catch (final IOException e) {
            return IndexStatus.CORRUPT;
        }
        try {
            // The isCurrent call will force the check for corrupted indexes
            reader.isCurrent();
            return IndexStatus.ACTIVE;
        } catch (final CorruptIndexException e) {
            return IndexStatus.CORRUPT;
        } catch (final Exception e) {
            LOG.warn("Error while retrieving the index status for " + entityType, e);
            throw new DaoException(e);
        } finally {
            try {
                reader.close();
            } catch (final IOException e) {
                // Silently ignore
            }
        }
    }

    /**
     * Adds the given entity to index, or updates it if already on index
     */
    public void index(final Class<? extends Indexable> entityType, final Long id) {
        createOperation(entityType, OperationType.ADD, id);
    }

    /**
     * Returns whether index dir exists
     */
    public boolean indexesExists() {
        return indexRoot.exists() && indexRoot.list().length > 0;
    }

    /**
     * Opens a new {@link IndexReader} for the given entity type
     */
    public IndexReader openReader(final Class<? extends Indexable> entityType) {
        try {
            return doOpenReader(entityType);
        } catch (final Exception e) {
            LOG.warn("Error while opening index for read on " + entityType, e);
            throw new DaoException(e);
        }
    }

    /**
     * Recreates the index for the given entity type
     */
    public void rebuild(final Class<? extends Indexable> entityType) {
        createOperation(entityType, OperationType.REBUILD);
    }

    /**
     * Recreates the index for the given entity type if it is corrupt
     */
    public void rebuildIfCorrupt(final Class<? extends Indexable> entityType) {
        createOperation(entityType, OperationType.REBUILD_IF_CORRUPT);
    }

    /**
     * Removes the given entities from index
     */
    public void remove(final Class<? extends Indexable> entityType, final List<Long> ids) {
        for (Long id : ids) {
            remove(entityType, id);
        }
    }

    /**
     * Removes the given entity from index
     */
    public void remove(final Class<? extends Indexable> entityType, final Long id) {
        createOperation(entityType, OperationType.REMOVE, id);
    }

    public void setDocumentMappers(final Map<Class<? extends Indexable>, DocumentMapper> documentMappers) {
        this.documentMappers = documentMappers;
    }

    public void setIndexOperationDao(final IndexOperationDAO indexOperationDao) {
        this.indexOperationDao = indexOperationDao;
    }

    private IndexOperation createOperation(final Class<? extends Indexable> entityType, final OperationType operationType) {
        return createOperation(entityType, operationType, null);
    }

    private IndexOperation createOperation(final Class<? extends Indexable> entityType, final OperationType operationType, final Long entityId) {
        IndexOperation operation = new IndexOperation();
        operation.setDate(Calendar.getInstance());
        operation.setEntityType(EntityType.from(entityType));
        operation.setOperationType(operationType);
        operation.setEntityId(entityId);
        return indexOperationDao.insert(operation);
    }

    @SuppressWarnings("deprecation")
    private IndexReader doOpenReader(final Class<? extends Indexable> entityType) throws CorruptIndexException, IOException {
        // TODO if we ever update to Lucene 4 (alpha for now) we shoudln't pass the true parameter, as readers will be always readonly. We won't
        // do it now because readonly readers perform better on high concurrency
        return IndexReader.open(getDirectory(entityType), true);
    }

}
