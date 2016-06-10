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
package nl.strohalm.cyclos.scheduling.polling;

import java.util.Random;
import java.util.concurrent.Callable;

import nl.strohalm.cyclos.utils.tasks.TaskRunner;

/**
 * Base class for tasks which keeps looking for available data to be processed. When running in a cluster, it is guaranteed that only a single node
 * runs the same polling task at the same time.
 * @author luis
 */
public abstract class PollingTask extends Thread {

    private int        sleepSeconds;
    private String     key;
    private TaskRunner taskRunner;

    public PollingTask() {
        setSleepSeconds(10);
        setKey(getClass().getSimpleName());
    }

    /**
     * Awakes this polling task from sleep, if it is sleeping
     */
    public void awake() {
        synchronized (this) {
            notify();
        }
    }

    public String getKey() {
        return key;
    }

    public int getSleepSeconds() {
        return sleepSeconds;
    }

    public TaskRunner getTaskRunner() {
        return taskRunner;
    }

    @Override
    public final void run() {
        // Sleep the first time. The random time is to help not having multiple polling tasks running at the same second
        try {
            int initialSleepSeconds = new Random().nextInt(sleepSeconds);
            Thread.sleep(initialSleepSeconds * 1000);
        } catch (InterruptedException e) {
            return;
        }
        while (true) {
            // Execute the task
            boolean runImmediately = taskRunner.runPollingTask(getKey(), new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return runTask();
                }
            });

            // Sleep if no more data for now
            if (!runImmediately) {
                synchronized (this) {
                    try {
                        wait(sleepSeconds * 1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }

    public void setKey(final String key) {
        this.key = key;
        setName("Polling task: " + key);
    }

    public void setSleepSeconds(final int sleepSeconds) {
        this.sleepSeconds = sleepSeconds;
    }

    public void setTaskRunner(final TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    /**
     * Should be implemented by subclasses to perform the actual task. Returning true means that this method should be immediately invoked right after
     * this execution. False means the thread will sleep until the next execution.
     */
    protected abstract boolean runTask();

}
