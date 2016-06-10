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
package nl.strohalm.cyclos.scheduling.tasks;

import java.util.Calendar;

import nl.strohalm.cyclos.utils.logging.LoggingHandler;

/**
 * Base implementation for a scheduled task
 * @author luis
 */
public abstract class BaseScheduledTask implements ScheduledTask {
    private final String   name;
    private final boolean  everyHour;
    private final boolean  shouldRunInTransaction;
    private LoggingHandler loggingHandler;

    public BaseScheduledTask(final String name, final boolean everyHour) {
        this(name, everyHour, true);
    }

    public BaseScheduledTask(final String name, final boolean everyHour, final boolean shouldRunInTransaction) {
        this.name = name;
        this.everyHour = everyHour;
        this.shouldRunInTransaction = shouldRunInTransaction;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEveryHour() {
        return everyHour;
    }

    @Override
    public void run(final Calendar time) {
        try {
            final long start = System.currentTimeMillis();
            doRun(time);
            loggingHandler.logScheduledTaskTrace(getName(), time, System.currentTimeMillis() - start);
        } catch (final Exception e) {
            loggingHandler.logScheduledTaskError(getName(), time, e);
        }
    }

    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

    @Override
    public boolean shouldRunInTransaction() {
        return shouldRunInTransaction;
    }

    /**
     * Should be overriden by subclasses to perform actual execution
     */
    protected abstract void doRun(Calendar time);
}
