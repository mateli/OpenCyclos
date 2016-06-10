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
package nl.strohalm.cyclos.services.alerts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntryQuery;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for error logs
 * @author luis
 */
public interface ErrorLogService extends Service {

    /**
     * Insert a new error log asynchronously
     */
    Future<ErrorLogEntry> insert(Throwable t, String path, Map<String, ?> parameters);

    /**
     * Loads an error log by id
     */
    ErrorLogEntry load(Long id, Relationship... fetch);

    /**
     * Removes the given error log entries. They are not physically removed, but marked as removed.
     */
    int remove(Long... ids);

    /**
     * Searches for error logs
     */
    List<ErrorLogEntry> search(ErrorLogEntryQuery query);

}
