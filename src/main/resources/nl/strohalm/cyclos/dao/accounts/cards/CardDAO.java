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
package nl.strohalm.cyclos.dao.accounts.cards;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.CardQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Interface DAO for Cards
 * @author rodrigo
 */
public interface CardDAO extends BaseDAO<Card>, InsertableDAO<Card>, UpdatableDAO<Card>, DeletableDAO<Card> {

    /**
     * Cancel all cards from the member
     */
    void cancelAllMemberCards(Member member);

    /**
     * Test if already exists the given cardNumber
     */
    boolean existsNumber(BigInteger cardNumber) throws DaoException;

    /**
     * Returns all card with expire date before expirationDate
     */
    List<Card> getCardsToExpire(Calendar expirationDate);

    /**
     * Return a List<Card> from a given Member
     */
    List<Card> getMemberCards(final long memberId) throws DaoException;

    /**
     * Search for the max expiration_date where status = Card.Status.EXPIRED
     */
    Calendar lastTimeExpiredCards();

    /**
     * Loads a card by it's number
     */
    Card loadByNumber(BigInteger number, Relationship... fetch);

    /**
     * Search for the newest card based on creation date
     */
    Card getLastCard(long memberId) throws DaoException;

    /**
     * Searches for cards accordingly the parameters provided by <code>queryParameters</code>. If no card can be found, returns an empty List. Any
     * exception thrown by the underlying implementation should be wrapped by a DaoException.
     */
    List<Card> search(CardQuery queryParameters) throws DaoException;

    /**
     * Searches for cards with status != CANCELED where card owner = ownerId and card != cardId
     */
    List<Card> searchActiveCards(Long ownerId, Long cardId);
}
