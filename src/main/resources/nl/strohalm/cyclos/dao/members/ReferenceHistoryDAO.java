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

import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.ReferenceHistoryLog;
import nl.strohalm.cyclos.entities.members.ReferenceHistoryLogQuery;
import nl.strohalm.cyclos.utils.Period;

public interface ReferenceHistoryDAO extends BaseDAO<ReferenceHistoryLog>, InsertableDAO<ReferenceHistoryLog>, UpdatableDAO<ReferenceHistoryLog> {

    public Map<Level, Integer> countReferencesHistoryByLevel(Member member, Period date, boolean received);

    public ReferenceHistoryLog getOpenReferenceHistoryLog(ReferenceHistoryLogQuery query);

    Map<Level, Integer> countReferencesHistoryByLevel(Member member, Collection<MemberGroup> memberGroups, Period date, boolean received);
}
