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

import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.PendingMemberQuery;

/**
 * DAO interface for pending members
 * @author luis
 */
public interface PendingMemberDAO extends BaseDAO<PendingMember>, InsertableDAO<PendingMember>, UpdatableDAO<PendingMember>, DeletableDAO<PendingMember> {

    /**
     * Deletes all pending members before the given date
     * @param date
     */
    void deleteBefore(Calendar date);

    /**
     * Checks whether the given e-mail address is already used by an existing pending member
     * @param pendingMember
     */
    boolean emailExists(PendingMember pendingMember, String email);

    /**
     * Loads a PendingMember by key
     */
    PendingMember loadByKey(String key, Relationship... fetch);

    /**
     * Loads a PendingMember by username
     */
    PendingMember loadByUsername(String username, Relationship... fetch);

    /**
     * Searches for pending members
     */
    List<PendingMember> search(PendingMemberQuery params);

}
