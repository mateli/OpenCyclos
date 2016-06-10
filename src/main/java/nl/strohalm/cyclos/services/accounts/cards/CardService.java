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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.CardQuery;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.elements.BulkMemberActionResultVO;

/**
 * Service interface for Cards
 * @author rodrigo
 */
public interface CardService extends Service {

    /**
     * Activate the given card
     */
    Card activateCard(Card card, String cardCode);

    /**
     * Block given card
     */
    Card blockCard(Card card);

    /**
     * The generated cards must be in status PENDING and without cardSecurityNumber (it will be set in the card activation action)
     */
    BulkMemberActionResultVO bulkGenerateNewCard(FullTextMemberQuery query, boolean generateForPending, boolean generateForActive);

    /**
     * Cancel given card
     */
    Card cancelCard(Card card);

    /**
     * Change card code
     */
    Card changeCardCode(Card card, String code);

    /**
     * If there is an active card associated with the member it will be canceled before the new card is created.
     */
    Card generateNewCard(Member member);

    /**
     * Returns the current active card for the given user, or null if there's no active card
     */
    Card getActiveCard(Member member);

    /**
     * Loads a Card, fetching the specified relationships
     */
    Card load(long cardId, final Relationship... fetch);

    /**
     * Search for Cards based on the given query
     */
    List<Card> search(CardQuery query);

    /**
     * Unblock given card
     */
    Card unblockCard(Card card);

    /**
     * Unblock card security code
     */
    void unblockSecurityCode(Card card);

}
