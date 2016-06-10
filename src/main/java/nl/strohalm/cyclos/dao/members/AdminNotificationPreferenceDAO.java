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
package nl.strohalm.cyclos.dao.members;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreference;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreferenceQuery;

/**
 * Dao interface for admin notification preferences
 * @author luis
 */
public interface AdminNotificationPreferenceDAO extends BaseDAO<AdminNotificationPreference>, InsertableDAO<AdminNotificationPreference>, UpdatableDAO<AdminNotificationPreference>, DeletableDAO<AdminNotificationPreference> {

    /**
     * Loads the notification preference for the given administrator, returning null when there are no preferences for that admin
     */
    AdminNotificationPreference load(Administrator admin, Relationship... fetch) throws DaoException;

    /**
     * Returns administrators to notify according to the given arguments
     */
    List<Administrator> searchAdmins(AdminNotificationPreferenceQuery query);

}
