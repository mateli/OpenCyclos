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
package nl.strohalm.cyclos.dao.groups;

import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;

/**
 * Data access object interface for groups
 * @author rafael
 */
public interface GroupDAO extends BaseDAO<Group>, InsertableDAO<Group>, UpdatableDAO<Group>, DeletableDAO<Group> {

    /**
     * Finds a group by it's login page name
     */
    SystemGroup findByLoginPageName(String loginPageName);

    /**
     * Returns a Map containing, as key, the group name, and as value, the member count for that group, ordered by nature (members then brokers) and
     * name If any exception is thrown by the underlying implementation, it should be wrapped by a DaoException.
     */
    Map<String, Integer> getGroupMemberCount();

    /**
     * Returns a list of all operator groups of all members in this group
     */
    List<OperatorGroup> iterateOperatorGroups(MemberGroup memberGroup);

    /**
     * Returns a List of MemberGroups that are active (have as least one account)
     */
    List<MemberGroup> listActiveMemberGroups();

    /**
     * Searches groups according to the given query. Results should be ordered by name by default. If the sortByNature flag is set on the query
     * object, then the nature (first admin, then member, then broker) plus the group name should be used to determine the ordering. If no Group can
     * be found, returns an empty List. If any exception is thrown by the underlying implementation, it should be wrapped by a DaoException.
     * 
     * @throws DaoException
     */
    List<? extends Group> search(GroupQuery query) throws DaoException;

}
