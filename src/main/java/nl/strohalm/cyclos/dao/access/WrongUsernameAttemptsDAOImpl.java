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
import nl.strohalm.cyclos.entities.access.WrongUsernameAttempt;

/**
 * Implementation for {@link WrongUsernameAttemptsDAO}
 * @author luis
 */
public class WrongUsernameAttemptsDAOImpl extends BaseDAOImpl<WrongUsernameAttempt> implements WrongUsernameAttemptsDAO {

    public WrongUsernameAttemptsDAOImpl() {
        super(WrongUsernameAttempt.class);
    }

    @Override
    public void clear(final Calendar limit) {
        Map<String, ?> params = Collections.singletonMap("limit", limit);
        bulkUpdate("delete from WrongUsernameAttempt where date < :limit", params);
    }

    @Override
    public void clear(final String remoteAddress) {
        Map<String, ?> params = Collections.singletonMap("remoteAddress", remoteAddress);
        bulkUpdate("delete from WrongUsernameAttempt where remoteAddress = :remoteAddress", params);
    }

    @Override
    public int count(final Calendar limit, final String remoteAddress) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("limit", limit);
        params.put("remoteAddress", remoteAddress);
        return this.<Integer> uniqueResult("select count(*) from WrongUsernameAttempt a where a.date >= :limit and a.remoteAddress = :remoteAddress", params);
    }

    @Override
    public WrongUsernameAttempt record(final String remoteAddress) {
        WrongUsernameAttempt attempt = new WrongUsernameAttempt();
        attempt.setDate(Calendar.getInstance());
        attempt.setRemoteAddress(remoteAddress);
        return insert(attempt);
    }
}
