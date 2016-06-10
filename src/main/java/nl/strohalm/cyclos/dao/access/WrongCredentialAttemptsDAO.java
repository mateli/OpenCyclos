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

import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.access.WrongCredentialAttempt;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;

/**
 * DAO interface for wrong credential attempts
 * @author luis
 */
public interface WrongCredentialAttemptsDAO {

    /**
     * Removes all traces prior to the given limit
     */
    void clear(Calendar limit);

    /**
     * Clears all wrong attempts for the given card
     */
    void clear(Card card);

    /**
     * Clears all wrong attempts for the given member pos
     */
    void clear(MemberPos memberPos);

    /**
     * Clears all wrong attempts for the given user and credential type
     */
    void clear(User user, Credentials credentialType);

    /**
     * Counts the wrong attempts for the given card after the given time limit
     */
    int count(Calendar limit, Card card);

    /**
     * Counts the wrong attempts for the given member pos the given time limit
     */
    int count(Calendar limit, MemberPos memberPos);

    /**
     * Counts the wrong attempts for the given user and credential type today
     */
    int count(Calendar limit, User user, Credentials credentialType);

    /**
     * Adds a wrong attempt for the given card, returning the current number of wrong attempts for it (including this one)
     */
    WrongCredentialAttempt record(Card card);

    /**
     * Adds a wrong attempt for the given member pos, returning the current number of wrong attempts for them (including this one)
     */
    WrongCredentialAttempt record(MemberPos memberPos);

    /**
     * Adds a wrong attempt for the given user and credential type, returning the current number of wrong attempts for them (including this one)
     */
    WrongCredentialAttempt record(User user, Credentials credentialType);

}
