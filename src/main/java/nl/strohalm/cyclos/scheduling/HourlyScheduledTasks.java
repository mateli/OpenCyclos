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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.scheduling.tasks.ScheduledTask;

/**
 * A thread used to run a batch of scheduled tasks. This thread always have the hour it's execution should operate with. That hour is passed to
 * {@link ScheduledTask#run(java.util.Calendar)}
 * @author luis
 */
public class HourlyScheduledTasks extends Thread {

    private static final DateFormat HOUR_FORMAT = new SimpleDateFormat("yyyy-MM-dd.HH");

    private final SchedulingHandler handler;
    private final Calendar          time;

    public HourlyScheduledTasks(final SchedulingHandler handler, final Calendar time) {
        String applicationName = handler.getSettingsServiceLocal().getLocalSettings().getApplicationName();
        String hour = HOUR_FORMAT.format(time.getTime());
        setName(String.format("%s scheduled task runner for %s", applicationName, hour));
        this.handler = handler;
        this.time = time;
    }

    @Override
    public void run() {
        long initialTime = System.currentTimeMillis();

        // Get a list of tasks which will actually run
        Collection<String> tasks = getTasksToRun();

        handler.getTaskRunner().runScheduledTasks(time, tasks);

        // Log
        handler.getLoggingHandler().logSchedulingTrace(time, System.currentTimeMillis() - initialTime);

        // Now that the tasks are executed, run the next tasks, if any
        handler.runNextTasks();
    }

    private Collection<String> getTasksToRun() {
        final boolean runDailyTasks = shouldRunDailyTasks();
        List<String> tasks = new ArrayList<String>();
        for (ScheduledTask task : handler.getTasks()) {
            final boolean runTask = task.isEveryHour() || runDailyTasks;
            if (runTask) {
                tasks.add(task.getName());
            }
        }
        return tasks;
    }

    /**
     * Determines whether this execution should run daily tasks
     */
    private boolean shouldRunDailyTasks() {
        final LocalSettings localSettings = handler.getSettingsServiceLocal().getLocalSettings();
        final TimeZone timeZone = localSettings.getTimeZone();
        // Use a localized formatter to get the time in the instance's local time zone
        final SimpleDateFormat format = new SimpleDateFormat("HH");
        if (timeZone != null) {
            format.setTimeZone(timeZone);
        }
        final boolean runExtraTasks = localSettings.getSchedulingHour() == Integer.parseInt(format.format(time.getTime()));
        return runExtraTasks;
    }

}
