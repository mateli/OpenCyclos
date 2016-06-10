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
package nl.strohalm.cyclos.dao.alerts;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntryQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for error log entry DAO
 * @author luis
 */
public class ErrorLogEntryDAOImpl extends BaseDAOImpl<ErrorLogEntry> implements ErrorLogEntryDAO {

    public ErrorLogEntryDAOImpl() {
        super(ErrorLogEntry.class);
    }

    /**
     * Error log entries are never physically removed, just have their removed property changed to true
     */
    @Override
    public int delete(final boolean flush, final Long... ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        final String hql = "update " + getEntityType().getName() + " e set e.removed=true where e.id in (:ids)";
        final Map<String, ?> namedParameters = Collections.singletonMap("ids", Arrays.asList(ids));
        final int results = bulkUpdate(hql.toString(), namedParameters);
        if (flush) {
            flush();
        }
        return results;
    }

    public List<ErrorLogEntry> search(final ErrorLogEntryQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "e", query.getFetch());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "e.date", query.getPeriod());
        if (!query.isShowRemoved()) {
            hql.append(" and e.removed = false ");
        }
        HibernateHelper.appendOrder(hql, "e.date desc");
        return list(query, hql.toString(), namedParameters);
    }
}
