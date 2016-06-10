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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.Card.Status;
import nl.strohalm.cyclos.entities.accounts.cards.CardQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.lang.ArrayUtils;

/**
 * Implementation DAO for Cards
 * @author rodrigo
 */
public class CardDAOImpl extends BaseDAOImpl<Card> implements CardDAO {

    public CardDAOImpl() {
        super(Card.class);

    }

    @Override
    public void cancelAllMemberCards(final Member member) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("owner", member);
        namedParameters.put("status", Status.CANCELED);
        bulkUpdate("update " + getEntityType().getName() + " set status = :status where owner = :owner ", namedParameters);

    }

    @Override
    public boolean existsNumber(final BigInteger cardNumber) throws DaoException {
        final StringBuilder hql = new StringBuilder();
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        hql.append(" select count(*)");
        hql.append(" from " + Card.class.getName() + " c");
        hql.append(" where c.cardNumber = :cardNumber ");
        namedParameters.put("cardNumber", cardNumber);
        final Integer count = (Integer) (uniqueResult(hql.toString(), namedParameters));
        final boolean hasCardNumber = count == 0 ? false : true;

        return hasCardNumber;
    }

    @Override
    public List<Card> getCardsToExpire(final Calendar expirationDate) {
        final StringBuilder hql = new StringBuilder("select c from " + getEntityType().getName() + " c");
        hql.append(" where c.expirationDate < :expirationDate ");
        hql.append(" and c.status <> :status ");

        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("expirationDate", expirationDate);
        namedParameters.put("status", Card.Status.EXPIRED);

        return list(hql.toString(), namedParameters);
    }

    @Override
    public List<Card> getMemberCards(final long memberId) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("select c from " + Card.class.getName() + " c");
        hql.append(" where c.owner.id = :memberId ");
        namedParameters.put("memberId", memberId);

        HibernateHelper.appendOrder(hql, "c.creationDate desc");
        return list(hql.toString(), namedParameters);
    }

    @Override
    public Calendar lastTimeExpiredCards() {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("select max(c.expirationDate) from " + Card.class.getName() + " c");
        hql.append(" where c.status = :status ");
        namedParameters.put("status", Card.Status.EXPIRED);
        return (Calendar) uniqueResult(hql.toString(), namedParameters);
    }

    @Override
    public Card loadByNumber(final BigInteger number, final Relationship... fetch) {
        Card card = uniqueResult("from Card c where c.cardNumber = :number", Collections.singletonMap("number", number));
        if (card == null) {
            throw new EntityNotFoundException(Card.class);
        }
        if (!ArrayUtils.isEmpty(fetch)) {
            card = getFetchDao().fetch(card, fetch);
        }
        return card;
    }

    @Override
    public Card getLastCard(final long memberId) throws DaoException {
        final Map<String, ?> namedParameters = Collections.singletonMap("memberId", memberId);
        final StringBuilder hql = new StringBuilder();
        hql.append(" from " + Card.class.getName() + " c");
        hql.append(" where c.owner.id = :memberId ");
        hql.append(" order by c.creationDate desc");
        return uniqueResult(hql.toString(), namedParameters);
    }

    @Override
    public List<Card> search(final CardQuery query) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = new StringBuilder("select c from " + getEntityType().getName() + " c");
        hql.append(" left join c.owner owr");
        HibernateHelper.appendJoinFetch(hql, getEntityType(), "c", fetch);
        hql.append(" where 1 = 1");
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "c.expirationDate", query.getExpiration());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "c.status", query.getStatus());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "owr.group", query.getGroups());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "c.cardType", query.getCardType());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "c.owner", query.getMember());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "c.cardNumber", query.getNumber());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "owr.broker", query.getBroker());

        HibernateHelper.appendOrder(hql, "owr.name", "c.creationDate desc");
        return list(query, hql.toString(), namedParameters);
    }

    @Override
    public List<Card> searchActiveCards(final Long ownerId, final Long cardId) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("select c from " + getEntityType().getName() + " c");
        hql.append(" where c.owner.id = :ownerId ");
        hql.append(" and   c.id != :cardId ");
        hql.append(" and c.status not in ('C','E')");
        namedParameters.put("ownerId", ownerId);
        namedParameters.put("cardId", cardId);

        return list(hql.toString(), namedParameters);
    }
}
