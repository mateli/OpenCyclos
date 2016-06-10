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
package nl.strohalm.cyclos.scheduling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.scheduling.tasks.ScheduledTask;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.logging.LoggingHandler;
import nl.strohalm.cyclos.utils.lucene.IndexHandler;
import nl.strohalm.cyclos.utils.tasks.TaskRunner;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Handles execution of scheduled jobs
 * @author luis
 */
public class SchedulingHandler implements InitializingBean, DisposableBean, LocalSettingsChangeListener {

    /**
     * A {@link TimerTask} that dispatches the execution to a {@link HourlyScheduledTasks} if no other {@link HourlyScheduledTasks} is currently being
     * executed, or enqueues it for later execution
     * @author luis
     */
    private class SchedulingTimerTask extends TimerTask {
        @Override
        public void run() {
            Calendar time = DateUtils.truncate(Calendar.getInstance(), Calendar.HOUR_OF_DAY);
            HourlyScheduledTasks runner = new HourlyScheduledTasks(SchedulingHandler.this, time);
            if (queue.isEmpty()) {
                // Nothing is currently being executed. Execute it right away
                runner.start();
            } else {
                // Tasks of a previous hour are being executed. Enqueue this execution
                queue.offer(runner);
            }
        }
    }

    private Timer                       timer;
    private SettingsServiceLocal        settingsService;
    private LoggingHandler              loggingHandler;
    private TransactionTemplate         transactionTemplate;
    private IndexHandler                indexHandler;
    private TaskRunner                  taskRunner;
    private Queue<HourlyScheduledTasks> queue;
    private Map<String, ScheduledTask>  tasks;
    private Integer                     lastScheduledMinute;

    @Override
    public void afterPropertiesSet() throws Exception {
        queue = new ConcurrentLinkedQueue<HourlyScheduledTasks>();
        settingsService.addListener(this);
    }

    @Override
    public void destroy() throws Exception {
        shutdown();
        if (queue != null) {
            queue.clear();
            queue = null;
        }
    }

    public IndexHandler getIndexHandler() {
        return indexHandler;
    }

    public LoggingHandler getLoggingHandler() {
        return loggingHandler;
    }

    public SettingsServiceLocal getSettingsServiceLocal() {
        return settingsService;
    }

    public ScheduledTask getTask(final String name) {
        return tasks.get(name);
    }

    public TaskRunner getTaskRunner() {
        return taskRunner;
    }

    public List<ScheduledTask> getTasks() {
        return new ArrayList<ScheduledTask>(tasks.values());
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        updateTime();
    }

    public void runNextTasks() {
        HourlyScheduledTasks nextToRun = queue.poll();
        if (nextToRun != null) {
            nextToRun.start();
        }
    }

    public void setIndexHandler(final IndexHandler indexHandler) {
        this.indexHandler = indexHandler;
    }

    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTaskRunner(final TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    public void setTasks(final List<ScheduledTask> tasks) {
        this.tasks = new LinkedHashMap<String, ScheduledTask>(tasks.size());
        for (ScheduledTask task : tasks) {
            ScheduledTask old = this.tasks.put(task.getName(), task);
            if (old != null) {
                throw new IllegalStateException("Trying to add 2 tasks with the same name '" + task.getName() + "': " + old + " and " + task);
            }
        }
    }

    public void setTransactionTemplate(final TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * Starts running scheduled jobs
     */
    public void start() {
        initializeTimer();
    }

    /**
     * Should be called when the time the tasks run should be modified
     */
    public synchronized void updateTime() {
        if (timer == null) {
            return;
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();
        if (lastScheduledMinute == null || lastScheduledMinute != localSettings.getSchedulingMinute()) {
            shutdown();
            initializeTimer();
        }
    }

    private synchronized void initializeTimer() {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        timer = new Timer("Scheduled tasks handler for " + localSettings.getApplicationName());
        timer.scheduleAtFixedRate(new SchedulingTimerTask(), startsTaskAt(), DateUtils.MILLIS_PER_HOUR);
        lastScheduledMinute = localSettings.getSchedulingMinute();
    }

    /**
     * Stops all running tasks
     */
    private synchronized void shutdown() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Returns the date where the task will start running. It should be on the next hour, settings the minute to the <code>minute</code> property
     */
    private Date startsTaskAt() {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Calendar startAt = Calendar.getInstance();
        startAt.add(Calendar.HOUR_OF_DAY, 1);
        startAt.set(Calendar.MINUTE, localSettings.getSchedulingMinute());
        startAt.set(Calendar.SECOND, 30);
        return startAt.getTime();
    }

}
