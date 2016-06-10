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
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.ReferenceHistoryLog;
import nl.strohalm.cyclos.entities.members.ReferenceHistoryLogQuery;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

public class ReferenceHistoryDAOImpl extends BaseDAOImpl<ReferenceHistoryLog> implements ReferenceHistoryDAO {

    public ReferenceHistoryDAOImpl() {
        super(ReferenceHistoryLog.class);
    }

    public Map<Level, Integer> countReferencesHistoryByLevel(final Member member, final Collection<MemberGroup> memberGroups, final Period date, final boolean received) {
        final Map<Level, Integer> countGivenReferences = new EnumMap<Level, Integer>(Level.class);
        for (final Level level : Level.values()) {
            countGivenReferences.put(level, 0);
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("select rh.level, count(rh.id) from ReferenceHistoryLog rh where 1=1 ");
        HibernateHelper.addParameterToQuery(hql, namedParameters, (received ? "rh.to" : "rh.from"), member);
        if (memberGroups != null && !memberGroups.isEmpty()) {
            hql.append(" and " + (received ? "rh.to" : "rh.from") + ".group in (:memberGroups) ");
            namedParameters.put("memberGroups", memberGroups);
        }
        if (date != null) {
            final Calendar begin = DateHelper.truncate(date.getBegin());
            if (begin != null) {
                HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "rh.period.begin", ">=", begin);
            }
            final Calendar end = DateHelper.truncate(date.getEnd());
            if (end != null) {
                hql.append(" and rh.period.begin < :endNextDay and (rh.period.end is null or rh.period.end >= :end)");
                namedParameters.put("end", end);
                namedParameters.put("endNextDay", DateHelper.truncateNextDay(end));
            }
        }
        hql.append(" group by rh.level order by rh.level");
        final List<Object[]> rows = list(hql.toString(), namedParameters);
        for (final Object[] row : rows) {
            countGivenReferences.put((Level) row[0], (Integer) row[1]);
        }
        return countGivenReferences;
    }

    public Map<Level, Integer> countReferencesHistoryByLevel(final Member member, final Period date, final boolean received) {
        return countReferencesHistoryByLevel(member, null, date, received);
    }

    public ReferenceHistoryLog getOpenReferenceHistoryLog(final ReferenceHistoryLogQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "rhl", fetch);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "rhl.from", query.getFrom());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "rhl.to", query.getTo());
        hql.append(" and rhl.period.end is null ");

        return uniqueResult(hql.toString(), namedParameters);
    }
}
