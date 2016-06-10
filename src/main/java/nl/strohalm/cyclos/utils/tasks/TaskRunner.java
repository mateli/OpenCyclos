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

import nl.strohalm.cyclos.services.InitializingService;

/**
 * Runs system tasks
 * 
 * @author luis
 */
public interface TaskRunner {

    /**
     * Handles the database initialization
     */
    void handleDatabaseInitialization(Runnable runnable);

    /**
     * Runs all the given initializations, which are spring bean names. All such beans implement {@link InitializingService}
     */
    void runInitializations(Collection<String> beanNames);

    /**
     * Executes the given polling task
     */
    boolean runPollingTask(String key, Callable<Boolean> task);

    /**
     * Runs the scheduled task with the given name
     */
    void runScheduledTasks(Calendar time, Collection<String> taskNames);

}
