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
package nl.strohalm.cyclos.services.application;

import java.util.Map;

import nl.strohalm.cyclos.entities.IndexStatus;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for application-wide (global) events like status, time and messages.
 * @author luis
 */
public interface ApplicationService extends Service {

    /**
     * Return statistical data regarding the application
     */
    ApplicationStatusVO getApplicationStatus();

    /**
     * Returns the application version
     */
    String getCyclosVersion();

    /**
     * A map indicating each index status by entity type
     */
    Map<Class<? extends Indexable>, IndexStatus> getFullTextIndexesStatus();

    /**
     * Notifies the application initialization
     */
    void initialize();

    /**
     * Returns whether the system is online
     */
    boolean isOnline();

    /**
     * Rebuilds the given full-text index from scratch, or all if the param is null
     */
    void rebuildIndexes(Class<? extends Indexable> entityType);

    /**
     * Sets the system offline status
     */
    void setOnline(boolean online);

    /**
     * Notifies the application shutdown
     */
    void shutdown();

}
