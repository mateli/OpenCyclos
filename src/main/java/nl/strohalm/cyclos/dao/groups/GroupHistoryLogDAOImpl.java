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

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.groups.GroupHistoryLog;
import nl.strohalm.cyclos.entities.members.Element;

/**
 * Implementation class for interface GroupHistoryLogDAO
 * @author Jefferson Magno
 */
public class GroupHistoryLogDAOImpl extends BaseDAOImpl<GroupHistoryLog> implements GroupHistoryLogDAO {

    public GroupHistoryLogDAOImpl() {
        super(GroupHistoryLog.class);
    }

    public GroupHistoryLog getLastGroupHistoryLog(final Element element) {
        final String hql = "from " + GroupHistoryLog.class.getName() + " ghl where ghl.element = :element and ghl.period.end is null";
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("element", element);
        return uniqueResult(hql, namedParameters);
    }

}
