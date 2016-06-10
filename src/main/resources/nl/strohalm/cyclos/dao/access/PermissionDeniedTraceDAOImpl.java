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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.access.PermissionDeniedTrace;
import nl.strohalm.cyclos.entities.access.User;

/**
 * Implementation for {@link PermissionDeniedTraceDAO}
 * 
 * @author luis
 */
public class PermissionDeniedTraceDAOImpl extends BaseDAOImpl<PermissionDeniedTrace> implements PermissionDeniedTraceDAO {

    public PermissionDeniedTraceDAOImpl() {
        super(PermissionDeniedTrace.class);
    }

    @Override
    public void clear(final Calendar limit) {
        Map<String, Calendar> params = Collections.singletonMap("limit", limit);
        bulkUpdate("delete from PermissionDeniedTrace where date < :limit", params);
    }

    @Override
    public void clear(final User user) {
        Map<String, User> params = Collections.singletonMap("user", user);
        bulkUpdate("delete from PermissionDeniedTrace where user = :user", params);
    }

    @Override
    public int count(final Calendar limit, final User user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("limit", limit);
        params.put("user", user);
        return this.<Integer> uniqueResult("select count(*) from PermissionDeniedTrace t where t.date >= :limit and t.user = :user", params);
    }

    @Override
    public PermissionDeniedTrace record(final User user) {
        PermissionDeniedTrace trace = new PermissionDeniedTrace();
        trace.setDate(Calendar.getInstance());
        trace.setUser(user);
        return insert(trace);
    }

}
