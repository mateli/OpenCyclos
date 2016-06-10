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
package nl.strohalm.cyclos.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An abstract implementation for a thread group of consumer threads
 * @author luis
 */
public abstract class WorkerThreads<T> {

    private final class WorkerThread extends Thread {
        private long    lastUsedAt;
        private boolean inProcess;

        @Override
        public void run() {
            while (true) {
                // Block until something is available or this thread has been stopped
                final T object;
                try {
                    object = queue.take();
                } catch (final InterruptedException e) {
                    // This thread has been interrupted. Leave the loop
                    break;
                }

                inProcess = true;

                // Update the last used counter
                lastUsedAt = System.currentTimeMillis();

                // Process the object
                LoggedUser.runAsSystem(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        try {
                            process(object);
                        } catch (final Exception e) {
                            LOG.error("Error processing work by " + name, e);
                        }
                        return null;
                    }
                });

                inProcess = false;
            }
        }
    }

    private static final long  CHECK_INTERVAL = DateUtils.MILLIS_PER_MINUTE;
    private static final Log   LOG            = LogFactory.getLog(WorkerThreads.class);

    private String             name;
    private List<WorkerThread> threads;
    private int                maxThreads;
    private BlockingQueue<T>   queue          = new LinkedBlockingQueue<T>();
    private long               threadIndex;
    private Timer              cleanUpTimer;

    protected WorkerThreads(final String name, final int maxThreads) {
        this(name, maxThreads, true);
    }

    protected WorkerThreads(final String name, final int maxThreads, final boolean purgeOld) {
        this.name = name;
        this.maxThreads = maxThreads;
        threads = Collections.synchronizedList(new LinkedList<WorkerThread>());
        if (purgeOld) {
            cleanUpTimer = new Timer("Clean up timer for " + name);
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    interruptOldThreads();
                }
            };
            cleanUpTimer.scheduleAtFixedRate(task, CHECK_INTERVAL, CHECK_INTERVAL);
        }
    }

    /**
     * Enqueues the given object for processing
     */
    public synchronized void enqueue(final T object) {
        if (maxThreads <= 0) {
            return;
        }
        queue.offer(object);
        final int queueSize = queue.size();
        final int threadsSize = threads.size();
        if (threadsSize < maxThreads && threadsSize < queueSize) {
            // Start another thread
            final WorkerThread thread = new WorkerThread();
            thread.setName("#" + (threadIndex++) + " " + name);
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Enqueues all the given objects for processing
     */
    public void enqueueAll(final Collection<T> objects) {
        for (final T object : objects) {
            enqueue(object);
        }
    }

    /**
     * Interrupts all threads
     */
    public synchronized void interrupt() {
        if (cleanUpTimer != null) {
            cleanUpTimer.cancel();
        }
        for (final WorkerThread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }

    /**
     * Should be implemented in order to do the actual work with the given object
     */
    protected abstract void process(T object);

    private synchronized void interruptOldThreads() {
        final long tolerance = System.currentTimeMillis() - CHECK_INTERVAL;
        for (final Iterator<WorkerThread> iterator = threads.iterator(); iterator.hasNext();) {
            final WorkerThread thread = iterator.next();
            if (thread.lastUsedAt < tolerance && !thread.inProcess) {
                thread.interrupt();
                iterator.remove();
            }
        }
    }

}
