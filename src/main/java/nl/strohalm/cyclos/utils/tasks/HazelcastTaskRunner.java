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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;

import nl.strohalm.cyclos.scheduling.tasks.ScheduledTask;
import nl.strohalm.cyclos.utils.HazelcastHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;

/**
 * A {@link TaskRunner} which runs tasks correctly in a cluster
 * 
 * @author luis
 */
public class HazelcastTaskRunner extends TaskRunnerImpl implements InitializingBean, ApplicationContextAware {

    public static enum KeyType {
        INITIALIZATION, SCHEDULED_TASK, POLLING_TASK, DB_INIT
    }

    public static class LockKey implements Serializable {
        private static final long serialVersionUID = 3480718144050023229L;
        private final KeyType     type;
        private final String      name;

        private LockKey(final KeyType type, final String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LockKey)) {
                return false;
            }
            LockKey key = (LockKey) obj;
            return key.type == key.type && name.equals(key.name);
        }

        @Override
        public int hashCode() {
            return 13 * type.hashCode() * name.hashCode();
        }

        @Override
        public String toString() {
            return type + " " + name;
        }
    }

    private Map<String, String>   initializationControl;
    private Map<String, Calendar> scheduledTaskControl;
    private HazelcastInstance     hazelcastInstance;

    @Override
    public void afterPropertiesSet() throws Exception {
        hazelcastInstance = HazelcastHelper.getHazelcastInstance(applicationContext);
        initializationControl = hazelcastInstance.getMap("cyclos.initializationControl");
        scheduledTaskControl = hazelcastInstance.getMap("cyclos.scheduledTaskControl");
    }

    @Override
    public void runInitializations(final Collection<String> beanNames) {
        // As some other node could be running some initialization, make sure that everything is initialized before returning
        for (boolean firstTime = true; !allInitializationsExecuted(beanNames); firstTime = false) {
            if (!firstTime) {
                // When not the first time, sleep a bit, to give other nodes time to finish the initializations
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
            // Ensure all bean names are in the initialization control
            super.runInitializations(beanNames);
        }
    }

    @Override
    protected void doHandleDatabaseInitialization(final Runnable runnable) {
        LockKey lockKey = new LockKey(KeyType.DB_INIT, StringUtils.EMPTY);
        ILock lock = hazelcastInstance.getLock(lockKey);
        // Sleep until the lock is acquired
        lock.lock();
        try {
            super.doHandleDatabaseInitialization(runnable);
        } finally {
            HazelcastHelper.release(lock);
        }
    }

    @Override
    protected void doRunInitialization(final String beanName) {
        LockKey lockKey = new LockKey(KeyType.INITIALIZATION, beanName);
        // Try to get the initialization lock
        ILock lock = hazelcastInstance.getLock(lockKey);
        if (lock.tryLock()) {
            // No one else is trying to run this initialization right now. Check if it was already ran by someone else
            if (!initializationControl.containsKey(beanName)) {
                try {
                    // This initialization was never executed. Run it and mark it as executed
                    super.doRunInitialization(beanName);
                    initializationControl.put(beanName, beanName);
                } finally {
                    HazelcastHelper.release(lock);
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Not running initialization for bean " + beanName + " because some other node is currently running it");
            }
        }
    }

    @Override
    protected boolean doRunPollingTask(final String key, final Callable<Boolean> task) {
        LockKey lockKey = new LockKey(KeyType.POLLING_TASK, key);
        ILock lock = hazelcastInstance.getLock(lockKey);
        // Ensure multiple nodes can't run a polling task simultaneously
        if (lock.tryLock()) {
            try {
                return super.doRunPollingTask(key, task);
            } finally {
                HazelcastHelper.release(lock);
            }
        } else {
            // Force a sleep, as couldn't get the lock for this polling task
            if (LOG.isDebugEnabled()) {
                LOG.debug("Some other cluster node is running the " + key + " polling task. Leaving.");
            }
            return false;
        }
    }

    @Override
    protected void doRunScheduledTask(final String taskName, final Calendar time) {
        // Scheduled tasks won't run twice for the same hour in the entire cluster.
        LockKey lockKey = new LockKey(KeyType.SCHEDULED_TASK, taskName);
        ILock lock = hazelcastInstance.getLock(lockKey);
        if (lock.tryLock()) {
            // No other node is trying to execute this scheduled task
            try {
                // Determine whether the task is daily
                ScheduledTask scheduledTask = getSchedulingHandler().getTask(taskName);
                boolean daily = !scheduledTask.isEveryHour();
                int field = daily ? Calendar.DAY_OF_MONTH : Calendar.HOUR_OF_DAY;

                // Check the last hour this task was performed
                Calendar lastRun = scheduledTaskControl.get(taskName);
                if (lastRun != null) {
                    lastRun = DateUtils.truncate(lastRun, field);
                }
                Calendar thisRun = DateUtils.truncate(time, field);

                // Fill all the gaps between the last run and this run.
                // In normal execution, this loop will be evaluated only once.
                while (lastRun == null || lastRun.before(thisRun)) {
                    if (lastRun == null) {
                        // Never executed: run as this time
                        lastRun = thisRun;
                    } else {
                        // Increment the field (either hour or day)
                        lastRun.add(field, 1);
                    }

                    // Run the task
                    super.doRunScheduledTask(taskName, lastRun);

                    // Store the task hour, no other node will run it on this hour again
                    scheduledTaskControl.put(taskName, lastRun);
                }
            } finally {
                HazelcastHelper.release(lock);
            }
        }
    }

    /**
     * Returns whether all initializations have been already executed
     */
    private boolean allInitializationsExecuted(final Collection<String> beanNames) {
        for (String beanName : beanNames) {
            if (!initializationControl.containsKey(beanName)) {
                return false;
            }
        }
        return true;
    }
}
