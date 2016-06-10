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
package nl.strohalm.cyclos.dao.access;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.access.LoginHistoryLog;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.reports.StatisticalDTO;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Pair;

import org.apache.commons.collections.CollectionUtils;

public class LoginHistoryDAOImpl extends BaseDAOImpl<LoginHistoryLog> implements LoginHistoryDAO {

    public LoginHistoryDAOImpl() {
        super(LoginHistoryLog.class);
    }

    public Calendar getFirstLoginHistoryDate() {
        final String hql = "select min(lh.date) from LoginHistoryLog as lh";
        return uniqueResult(hql, new HashMap<String, Object>());
    }

    // Used by Activity Stats > all using number of Logins
    public List<Pair<User, Number>> list(final StatisticalDTO dto) throws DaoException, EntityNotFoundException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();

        hql.append(" select new " + Pair.class.getName() + "(mu, count(lhl.id)) ");
        hql.append(" from MemberUser mu ");
        hql.append(" join mu.element m ");
        hql.append(" left join mu.loginHistory lhl ");
        hql.append(" where 1=1 ");

        // Period
        Calendar begin = dto.getPeriod().getBegin();
        Calendar end = dto.getPeriod().getEnd();
        if (dto.getPeriod().isUseTime()) {
            if (end != null) {
                // Add a second to end
                end = (Calendar) end.clone();
                end.add(Calendar.SECOND, 1);
            }
        } else {
            // Truncate both dates
            begin = DateHelper.truncate(begin);
            end = DateHelper.truncateNextDay(end);
        }
        namedParameters.put("begin", begin);
        namedParameters.put("end", end);
        hql.append("and (lhl.date is null or (lhl.date >= :begin and lhl.date < :end))");

        // Member groups
        if (!CollectionUtils.isEmpty(dto.getGroups())) {
            hql.append(" and exists ");
            hql.append(" ( ");
            hql.append("    select ghl.id from GroupHistoryLog ghl ");
            hql.append("    where ghl.element.id = m.id ");
            hql.append("    and ghl.group in (:groups) ");
            hql.append("    and ghl.period.begin < :end ");
            hql.append("    and (ghl.period.end is null or ghl.period.end >= :begin) ");
            hql.append("    and (lhl.date is null or lhl.date between ghl.period.begin and ifnull(ghl.period.end, lhl.date)) ");
            hql.append(" ) ");
            namedParameters.put("groups", dto.getGroups());
        }

        // hql.append(" group by m.user ");
        hql.append(" group by mu ");
        hql.append(" order by count(lhl.id) desc ");
        final List<Pair<User, Number>> logs = list(hql.toString(), namedParameters);
        return logs;
    }

}
