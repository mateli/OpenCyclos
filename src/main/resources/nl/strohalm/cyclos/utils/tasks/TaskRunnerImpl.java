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
package nl.strohalm.cyclos.utils.tasks;

import java.util.Calendar;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import nl.strohalm.cyclos.scheduling.SchedulingHandler;
import nl.strohalm.cyclos.scheduling.tasks.ScheduledTask;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.ParallelTask;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Base implementation for task runners
 * 
 * @author luis
 */
public class TaskRunnerImpl implements TaskRunner, ApplicationContextAware {

    /**
     * Parallel threads used to run initializations
     * @author luis
     */
    private class InitializationThreads extends ParallelTask<String> {

        public InitializationThreads() {
            super("Initialization");
        }

        @Override
        protected void process(final String initialization) {
            doRunInitialization(initialization);
        }

    }

    /**
     * Parallel threads used to run scheduled tasks
     * @author luis
     */
    private class ScheduledTaskThreads extends ParallelTask<String> {
        private final Calendar time;

        public ScheduledTaskThreads(final Calendar time) {
            super("Scheduled tasks for " + FormatObject.formatObject(time));
            this.time = time;
        }

        @Override
        protected void process(final String scheduledTask) {
            doRunScheduledTask(scheduledTask, time);
        }

    }

    protected static final Log   LOG = LogFactory.getLog(TaskRunnerImpl.class);

    protected ApplicationContext applicationContext;
    private TransactionHelper    transactionHelper;
    private SchedulingHandler    schedulingHandler;

    @Override
    public void handleDatabaseInitialization(final Runnable runnable) {
        doHandleDatabaseInitialization(runnable);
    }

    @Override
    public void runInitializations(final Collection<String> beanNames) {
        InitializationThreads threads = new InitializationThreads();
        threads.run(beanNames);
    }

    @Override
    public boolean runPollingTask(final String key, final Callable<Boolean> task) {
        return doRunPollingTask(key, task);
    }

    @Override
    public void runScheduledTasks(final Calendar time, final Collection<String> taskNames) {
        ScheduledTaskThreads threads = new ScheduledTaskThreads(time);
        threads.run(taskNames);
    }

    @Override
    public final void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void doHandleDatabaseInitialization(final Runnable runnable) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Handling database initialization");
            }
            runnable.run();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected void doRunInitialization(final String beanName) {
        final InitializingService service = applicationContext.getBean(beanName, InitializingService.class);
        getTransactionHelper().runInCurrentThread(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Running initialization for bean " + beanName);
                    }
                    LoggedUser.runAsSystem(Executors.callable(new Runnable() {
                        @Override
                        public void run() {
                            service.initializeService();
                        }
                    }));
                } catch (RuntimeException e) {
                    LOG.error("Error running initialization for bean " + beanName, e);
                    throw e;
                }
            }
        });
    }

    protected boolean doRunPollingTask(final String key, final Callable<Boolean> task) {
        return getTransactionHelper().runInCurrentThread(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(final TransactionStatus status) {
                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Running polling task " + key);
                    }
                    return LoggedUser.runAsSystem(task);
                } catch (RuntimeException e) {
                    LOG.error("Error running polling task" + key, e);
                    return false;
                }
            }
        });
    }

    protected void doRunScheduledTask(final String taskName, final Calendar time) {
        final ScheduledTask task = getSchedulingHandler().getTask(taskName);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Running scheduled task " + taskName + " with base time " + FormatObject.formatObject(time));
        }
        if (task.shouldRunInTransaction()) {
            getTransactionHelper().runInCurrentThread(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(final TransactionStatus status) {
                    if (!doRunScheduledTask(task, time)) {
                        status.setRollbackOnly();
                    }
                }
            });
        } else {
            doRunScheduledTask(task, time);
        }
    }

    protected SchedulingHandler getSchedulingHandler() {
        if (schedulingHandler == null) {
            schedulingHandler = applicationContext.getBean("schedulingHandler", SchedulingHandler.class);
        }
        return schedulingHandler;
    }

    protected TransactionHelper getTransactionHelper() {
        if (transactionHelper == null) {
            transactionHelper = applicationContext.getBean("transactionHelper", TransactionHelper.class);
        }
        return transactionHelper;
    }

    private boolean doRunScheduledTask(final ScheduledTask task, final Calendar time) {
        try {
            LoggedUser.runAsSystem(Executors.callable(new Runnable() {
                @Override
                public void run() {
                    task.run(time);
                }
            }));
            return true;
        } catch (final Exception e) {
            LOG.error("Error running scheduled task " + task.getName(), e);
            return false;
        }
    }

}
