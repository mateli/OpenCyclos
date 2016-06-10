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
import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PendingEmailChange;

/**
 * Implementation for {@link PendingEmailChangeDAO}
 * @author luis
 */
public class PendingEmailChangeDAOImpl extends BaseDAOImpl<PendingEmailChange> implements PendingEmailChangeDAO {

    public PendingEmailChangeDAOImpl() {
        super(PendingEmailChange.class);
    }

    @Override
    public int deleteBefore(final Calendar limit) {
        final Map<String, ?> params = Collections.singletonMap("limit", limit);
        return bulkUpdate("delete from PendingEmailChange pec where pec.lastEmailDate < :limit", params);
    }

    @Override
    public PendingEmailChange getByMember(final Member member, final Relationship... fetch) {
        final Map<String, ?> params = Collections.singletonMap("member", member);
        final StringBuilder hql = new StringBuilder();
        hql.append(" from PendingEmailChange pec");
        hql.append(" where pec.member = :member");
        return uniqueResult(hql.toString(), params);
    }

    @Override
    public int removeAll(final Member member) {
        final Map<String, ?> params = Collections.singletonMap("member", member);
        return bulkUpdate("delete from PendingEmailChange pec where pec.member = :member", params);
    }

}
