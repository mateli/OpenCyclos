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
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.access.WrongCredentialAttempt;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;

/**
 * Implementation for {@link WrongCredentialAttemptsDAO}
 * @author luis
 */
public class WrongCredentialAttemptsDAOImpl extends BaseDAOImpl<WrongCredentialAttempt> implements WrongCredentialAttemptsDAO {

    public WrongCredentialAttemptsDAOImpl() {
        super(WrongCredentialAttempt.class);
    }

    @Override
    public void clear(final Calendar limit) {
        Map<String, ?> params = Collections.singletonMap("limit", limit);
        bulkUpdate("delete from WrongCredentialAttempt where date < :limit", params);
    }

    @Override
    public void clear(final Card card) {
        Map<String, ?> params = Collections.singletonMap("card", card);
        bulkUpdate("delete from WrongCredentialAttempt where card = :card", params);
    }

    @Override
    public void clear(final MemberPos memberPos) {
        Map<String, ?> params = Collections.singletonMap("memberPos", memberPos);
        bulkUpdate("delete from WrongCredentialAttempt where memberPos = :memberPos", params);
    }

    @Override
    public void clear(final User user, final Credentials credentialType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        params.put("credentialType", credentialType);
        bulkUpdate("delete from WrongCredentialAttempt where user = :user and credentialType = :credentialType", params);
    }

    @Override
    public int count(final Calendar limit, final Card card) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("limit", limit);
        params.put("card", card);
        return this.<Integer> uniqueResult("select count(*) from WrongCredentialAttempt a where a.date >= :limit and a.card = :card", params);
    }

    @Override
    public int count(final Calendar limit, final MemberPos memberPos) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("limit", limit);
        params.put("memberPos", memberPos);
        return this.<Integer> uniqueResult("select count(*) from WrongCredentialAttempt a where a.date >= :limit and a.memberPos = :memberPos", params);
    }

    @Override
    public int count(final Calendar limit, final User user, final Credentials credentialType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("limit", limit);
        params.put("user", user);
        params.put("credentialType", credentialType);
        return this.<Integer> uniqueResult("select count(*) from WrongCredentialAttempt a where a.date >= :limit and a.user = :user and a.credentialType = :credentialType", params);
    }

    @Override
    public WrongCredentialAttempt record(final Card card) {
        WrongCredentialAttempt attempt = new WrongCredentialAttempt();
        attempt.setDate(Calendar.getInstance());
        attempt.setCard(card);
        return insert(attempt);
    }

    @Override
    public WrongCredentialAttempt record(final MemberPos memberPos) {
        WrongCredentialAttempt attempt = new WrongCredentialAttempt();
        attempt.setDate(Calendar.getInstance());
        attempt.setMemberPos(memberPos);
        return insert(attempt);
    }

    @Override
    public WrongCredentialAttempt record(final User user, final Credentials credentialType) {
        WrongCredentialAttempt attempt = new WrongCredentialAttempt();
        attempt.setDate(Calendar.getInstance());
        attempt.setUser(user);
        attempt.setCredentialType(credentialType);
        return insert(attempt);
    }

}
