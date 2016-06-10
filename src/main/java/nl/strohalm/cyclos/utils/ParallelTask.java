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
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.mutable.MutableObject;

/**
 * Used to easily execute a parametrized task in parallel
 * @author luis
 */
public abstract class ParallelTask<T> {

    private static final int DEFAULT_MAX_PARALLEL_THREADS = 5;

    private String           name;
    private int              maxParallelThreads;

    public ParallelTask(final String name) {
        this(name, DEFAULT_MAX_PARALLEL_THREADS);
    }

    public ParallelTask(final String name, final int maxParallelThreads) {
        this.name = name;
        this.maxParallelThreads = maxParallelThreads;
    }

    /**
     * Runs all the given tasks. If any exception occurs, throws it and interrupts other any tasks
     */
    public void run(final Collection<T> tasks) {
        final CountDownLatch latch = new CountDownLatch(tasks.size());
        final MutableObject exception = new MutableObject();

        // Use a WorkerThreads to actually run everything
        final WorkerThreads<T> threads = new WorkerThreads<T>(name, maxParallelThreads, false) {
            @Override
            protected void process(final T object) {
                try {
                    // When some task has already failed, simply skip
                    if (exception.getValue() == null) {
                        ParallelTask.this.process(object);
                    }
                } catch (final Exception e) {
                    exception.setValue(e);
                } finally {
                    latch.countDown();
                }
            }
        };

        // Dispatch the tasks
        threads.enqueueAll(tasks);

        // Await for all tasks to complete
        try {
            latch.await();
        } catch (final InterruptedException e) {
            throw new IllegalStateException(e);
        } finally {
            // Interrupt all threads
            threads.interrupt();
        }

        // Throw the exception, if any
        final Exception e = (Exception) exception.getValue();
        if (e != null) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new IllegalStateException(e);
        }
    }

    /**
     * Should be implemented by subclasses to perform the actual work
     */
    protected abstract void process(final T object);
}
