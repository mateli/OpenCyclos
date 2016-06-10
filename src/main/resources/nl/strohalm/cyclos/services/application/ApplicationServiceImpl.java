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
package nl.strohalm.cyclos.services.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import nl.strohalm.cyclos.dao.ApplicationDAO;
import nl.strohalm.cyclos.dao.IndexOperationDAO;
import nl.strohalm.cyclos.entities.Application;
import nl.strohalm.cyclos.entities.Application.PasswordHash;
import nl.strohalm.cyclos.entities.IndexOperation.EntityType;
import nl.strohalm.cyclos.entities.IndexStatus;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.access.SessionQuery;
import nl.strohalm.cyclos.entities.accounts.LockedAccountsOnPayments;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert.Alerts;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.messages.MessageBox;
import nl.strohalm.cyclos.entities.members.messages.MessageQuery;
import nl.strohalm.cyclos.initializations.LocalInitialization;
import nl.strohalm.cyclos.scheduling.PollingTasksHandler;
import nl.strohalm.cyclos.scheduling.SchedulingHandler;
import nl.strohalm.cyclos.scheduling.polling.PollingTask;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.RateServiceLocal;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.alerts.ErrorLogServiceLocal;
import nl.strohalm.cyclos.services.elements.MessageServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.transactions.InvoiceServiceLocal;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.MessageResourcesLoadedListener;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.instance.InstanceHandler;
import nl.strohalm.cyclos.utils.lucene.IndexHandler;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.tasks.TaskRunner;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Implementation class for the application service interface.
 * @author rafael
 */
public class ApplicationServiceImpl implements ApplicationServiceLocal, ApplicationContextAware, InitializingBean, MessageResourcesLoadedListener {

    private static final Log    LOG                   = LogFactory.getLog(ApplicationServiceImpl.class);

    private static final String APPLICATION_CACHE_KEY = "application";
    private static final String MINOR_VERSION_TAG     = "_minor_";
    private static final String minorVersion;

    private static String extract(final String cvsTagName) {
        if (cvsTagName == null) {
            return "";
        }

        String result = cvsTagName;
        result = result.replaceAll("\\$Name:", "");
        result = result.replaceAll("\\$", "");
        result = result.replaceAll(" ", "");

        if (StringUtils.isBlank(result) || !result.contains(MINOR_VERSION_TAG)) {
            return "";
        } else {
            String[] tmp = StringUtils.splitByWholeSeparator(result, MINOR_VERSION_TAG);
            return tmp[tmp.length - 1];
        }
    }

    private MessageResolver               messageResolver;

    static {
        // the parameter is interpreted by CVS and expanded
        minorVersion = extract("$Name: not supported by cvs2svn $");
    }

    private boolean                       initialized;
    private boolean                       initializing;
    private boolean                       runScheduling;
    private ApplicationContext            applicationContext;
    private AccessServiceLocal            accessService;
    private AlertServiceLocal             alertService;
    private MessageServiceLocal           messageService;
    private InvoiceServiceLocal           invoiceService;
    private ErrorLogServiceLocal          errorLogService;
    private RateServiceLocal              rateService;
    private ApplicationDAO                applicationDao;
    private IndexOperationDAO             indexOperationDao;
    private SchedulingHandler             schedulingHandler;
    private PollingTasksHandler           pollingTasksHandler;
    private InstanceHandler               instanceHandler;
    private IndexHandler                  indexHandler;
    private CacheManager                  cacheManager;
    private TaskRunner                    taskRunner;
    private long                          startupTime;
    private Properties                    cyclosProperties;
    private TransactionHelper             transactionHelper;
    private LockedAccountsOnPayments      lockedAccountsOnPayments;
    private final HashMap<Alerts, String> deferredEvents = new HashMap<Alerts, String>();

    private PermissionServiceLocal        permissionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            lockedAccountsOnPayments = LockedAccountsOnPayments.valueOf(cyclosProperties.getProperty("cyclos.lockedAccountsOnPayments", LockedAccountsOnPayments.ORIGIN.name()).toUpperCase());
        } catch (Exception e) {
            StringBuilder message = new StringBuilder();
            message.append("Invalid value for cyclos.lockedAccountsOnPayments: ").append(cyclosProperties.getProperty("cyclos.lockedAccountsOnPayments")).append(". Valid values are ");
            boolean first = true;
            for (LockedAccountsOnPayments item : LockedAccountsOnPayments.values()) {
                if (first) {
                    first = false;
                } else {
                    message.append(", ");
                }
                message.append(item.name().toLowerCase());
            }
            throw new IllegalArgumentException(message.toString());
        }
    }

    @Override
    public void awakePollingTask(final Class<? extends PollingTask> type) {
        if (pollingTasksHandler != null) {
            PollingTask pollingTask = pollingTasksHandler.get(type);
            if (pollingTask != null) {
                pollingTask.awake();
            }
        }
    }

    @Override
    public void awakePollingTaskOnTransactionCommit(final Class<? extends PollingTask> type) {
        CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
            @Override
            public void onTransactionCommit() {
                awakePollingTask(type);
            }
        });
    }

    @Override
    public Calendar getAccountStatusEnabledSince() {
        return getApplication().getAccountStatusEnabledSince();
    }

    @Override
    public ApplicationStatusVO getApplicationStatus() {
        final ApplicationStatusVO vo = new ApplicationStatusVO();

        // Uptime period
        final long diff = System.currentTimeMillis() - startupTime;
        final int days = (int) (diff / DateUtils.MILLIS_PER_DAY);
        final int hours = (int) ((diff % DateUtils.MILLIS_PER_DAY) / DateUtils.MILLIS_PER_HOUR);
        vo.setUptimeDays(days);
        vo.setUptimeHours(hours);

        // Connected users
        SessionQuery sessions = new SessionQuery();
        sessions.setGroups(permissionService.getAllVisibleGroups());
        sessions.setPageForCount();

        sessions.setNatures(Collections.singleton(Group.Nature.ADMIN));
        vo.setConnectedAdmins(PageHelper.getTotalCount(accessService.searchSessions(sessions)));

        sessions.setNatures(Collections.singleton(Group.Nature.MEMBER));
        vo.setConnectedMembers(PageHelper.getTotalCount(accessService.searchSessions(sessions)));

        sessions.setNatures(Collections.singleton(Group.Nature.BROKER));
        vo.setConnectedBrokers(PageHelper.getTotalCount(accessService.searchSessions(sessions)));

        sessions.setNatures(Collections.singleton(Group.Nature.OPERATOR));
        vo.setConnectedOperators(PageHelper.getTotalCount(accessService.searchSessions(sessions)));

        // Cyclos version
        vo.setCyclosVersion(getCyclosVersion());

        // Number of alerts
        vo.setMemberAlerts(alertService.getAlertCount(Alert.Type.MEMBER));
        vo.setSystemAlerts(alertService.getAlertCount(Alert.Type.SYSTEM));
        vo.setErrors(errorLogService.getCount());

        // Unread messages
        vo.setUnreadMessages(countUnreadMessages());

        // Open invoices
        vo.setOpenInvoices(countOpenInvoices());

        return vo;
    }

    @Override
    public String getCyclosVersion() {
        String suffix = "";
        if (StringUtils.isNotBlank(minorVersion)) {
            suffix = " (" + minorVersion + ")";
        }
        return getApplication().getVersion() + suffix;
    }

    @Override
    public Map<Class<? extends Indexable>, IndexStatus> getFullTextIndexesStatus() {
        final Map<Class<? extends Indexable>, IndexStatus> stats = new LinkedHashMap<Class<? extends Indexable>, IndexStatus>();
        for (EntityType type : EntityType.values()) {
            Class<? extends Indexable> entityClass = type.getEntityClass();
            stats.put(entityClass, indexHandler.getIndexStatus(entityClass));
        }
        return stats;
    }

    @Override
    public LockedAccountsOnPayments getLockedAccountsOnPayments() {
        return lockedAccountsOnPayments;
    }

    public MessageResolver getMessageResolver() {
        return messageResolver;
    }

    @Override
    public PasswordHash getPasswordHash() {
        return getApplication().getPasswordHash();
    }

    @Override
    public synchronized void initialize() {
        if (initialized || initializing) {
            return;
        }
        initializing = true;
        try {
            // Store the startup time
            startupTime = System.currentTimeMillis();

            // Set AWT to headless mode. This way we can use XFree86 libs
            // on *nix systems without an X server running.
            setHeadlessMode();

            // The InitializingServices should be only initialized when scheduling is used on this cyclos instance.
            runScheduling = !Boolean.valueOf(cyclosProperties.getProperty("cyclos.disableScheduling", "false"));
            if (runScheduling) {
                // Run the initializations (beans of type InitializingService)
                Set<String> beanNames = applicationContext.getBeansOfType(InitializingService.class).keySet();
                taskRunner.runInitializations(beanNames);

                // Start both scheduling and polling tasks handlers
                schedulingHandler.start();
                pollingTasksHandler.start();
            }

            // Run the initializations
            Collection<LocalInitialization> initializations = applicationContext.getBeansOfType(LocalInitialization.class).values();
            runAll(initializations);

            if (runScheduling) {
                // Create a system alert
                transactionHelper.runAsync(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(final TransactionStatus status) {
                        // at this point, the resource bundle for translations is not loaded yet
                        // thus, we need to defer the creation of the event
                        deferEvent(SystemAlert.Alerts.APPLICATION_RESTARTED, instanceHandler.getId());
                    }
                });
            }
            initialized = true;
            // add this object as a listener to the messageResolver, which will inform (notify) this
            // object when the translation resource bundles have been loaded, so that finally alerts can be created
            messageResolver.addMessageResourcesLoadedListener(this);
        } finally {
            initializing = false;
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized && !initializing;
    }

    @Override
    public boolean isOnline() {
        Application app = getApplication();
        return app != null && app.isOnline();
    }

    @Override
    public boolean isRunScheduling() {
        return runScheduling;
    }

    /**
     * 
     * @see nl.strohalm.cyclos.utils.MessageResourcesLoadedListener#onApplicationResourcesLoaded()
     */
    @Override
    public void onApplicationResourcesLoaded() {
        Iterator<Alerts> it = deferredEvents.keySet().iterator();
        while (it.hasNext()) {
            Alerts key = it.next();
            String val = deferredEvents.get(key);

            alertService.create(key, val);
        }
    }

    @Override
    public void purgeIndexOperations(final Calendar time) {
        Calendar limit = (Calendar) time.clone();
        limit.add(Calendar.HOUR_OF_DAY, -24);
        indexOperationDao.deleteBefore(limit);
    }

    @Override
    public void rebuildIndexes(final Class<? extends Indexable> entityType) {
        for (Class<? extends Indexable> entityClass : resolveIndexedClasses(entityType)) {
            indexHandler.rebuild(entityClass);
        }
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        this.accessService = accessService;
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setApplicationDao(final ApplicationDAO applicationDao) {
        this.applicationDao = applicationDao;
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setCyclosProperties(final Properties cyclosProperties) {
        this.cyclosProperties = cyclosProperties;
    }

    public void setErrorLogServiceLocal(final ErrorLogServiceLocal errorLogService) {
        this.errorLogService = errorLogService;
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

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        this.messageService = messageService;
    }

    @Override
    public void setOnline(final boolean online) {
        Application application = getApplication();
        final boolean changed = application.isOnline() != online;
        if (changed) {
            if (online && (rateService.checkPendingRateInitializations(null) != null)) {
                throw new ValidationException("rates.error.notOnlineWhileRateInitsPending");
            }
            application.setOnline(online);
            applicationDao.update(application);
            getCache().remove(APPLICATION_CACHE_KEY);

            if (!online) {
                // Disconnect all logged users but the current user
                accessService.disconnectAllButLogged();
            }
        }
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setPollingTasksHandler(final PollingTasksHandler pollingTasksHandler) {
        this.pollingTasksHandler = pollingTasksHandler;
    }

    public void setRateServiceLocal(final RateServiceLocal rateService) {
        this.rateService = rateService;
    }

    public void setSchedulingHandler(final SchedulingHandler schedulingHandler) {
        this.schedulingHandler = schedulingHandler;
    }

    public void setTaskRunner(final TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    public void shutdown() {
        initialized = false;
        transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                alertService.create(SystemAlert.Alerts.APPLICATION_SHUTDOWN, instanceHandler.getId());
            }
        });
        applicationDao.shutdownDBIfNeeded();
    }

    private int countOpenInvoices() {
        final InvoiceQuery query = new InvoiceQuery();
        query.setOwner(SystemAccountOwner.instance());
        query.setDirection(InvoiceQuery.Direction.INCOMING);
        query.setStatus(Invoice.Status.OPEN);
        query.setPageForCount();
        return PageHelper.getTotalCount(invoiceService.search(query));
    }

    private int countUnreadMessages() {
        final MessageQuery query = new MessageQuery();
        query.setGetter(LoggedUser.element());
        query.setMessageBox(MessageBox.INBOX);
        query.setRead(false);
        query.setPageForCount();
        return PageHelper.getTotalCount(messageService.search(query));
    }

    /**
     * Defer the event creation for later firing
     * 
     * @param applicationRestarted Alert type
     * @param id application id
     */
    private void deferEvent(final Alerts applicationRestarted, final String id) {
        deferredEvents.put(applicationRestarted, id);
    }

    private Application getApplication() {
        return getCache().get(APPLICATION_CACHE_KEY, new CacheCallback() {
            @Override
            public Object retrieve() {
                return applicationDao.read();
            }
        });
    }

    private Cache getCache() {
        return cacheManager.getCache("cyclos.Application");
    }

    /**
     * Returns all indexed classes if the parameter is null, or a singleton collection otherwise
     */
    private Collection<Class<? extends Indexable>> resolveIndexedClasses(final Class<? extends Indexable> entityType) {
        if (entityType == null) {
            Collection<Class<? extends Indexable>> entityClasses = new ArrayList<Class<? extends Indexable>>();
            for (EntityType type : EntityType.values()) {
                entityClasses.add(type.getEntityClass());
            }
            return entityClasses;
        } else {
            return Collections.<Class<? extends Indexable>> singleton(entityType);
        }
    }

    /**
     * Run all given local initializations in its own transaction
     */
    private void runAll(final Collection<LocalInitialization> initializations) {
        for (final LocalInitialization initialization : initializations) {
            LOG.debug(String.format("Running initialization (%s)...", initialization.getName()));
            transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(final TransactionStatus status) {
                    initialization.initialize();
                }
            });
        }
    }

    private void setHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }
}
