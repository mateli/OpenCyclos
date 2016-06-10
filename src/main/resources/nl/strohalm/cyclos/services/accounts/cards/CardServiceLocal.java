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
package nl.strohalm.cyclos.services.accounts.cards;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Local interface. It must be used only from other services.
 */
public interface CardServiceLocal extends CardService {

    /**
     * Cancel all cards from a given member. It's called when an administrator is moving a member to another group
     */
    void cancelAllMemberCards(Member member);

    /**
     * Loads a Card based on it's number
     */
    Card loadByNumber(BigInteger number, Relationship... fetch);

    /**
     * Used by the schedule task
     */
    List<Card> processCards(Calendar time);

}
