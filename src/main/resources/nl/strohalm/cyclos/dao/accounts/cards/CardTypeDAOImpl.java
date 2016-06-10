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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;

/**
 * 
 * @author rodrigo
 */
public class CardTypeDAOImpl extends BaseDAOImpl<CardType> implements CardTypeDAO {

    public CardTypeDAOImpl() {
        super(CardType.class);
    }

    public List<CardType> listAll() {
        return list("from CardType ct order by ct.name", null);
    }

    public boolean searchWithCardType(final long cardTypeId) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select count(*)");
        hql.append(" from " + Card.class.getName() + " c");
        hql.append(" left join c.cardType ct");
        hql.append(" where ct.id = :cardTypeId ");
        namedParameters.put("cardTypeId", cardTypeId);
        final Integer count = (Integer) (uniqueResult(hql.toString(), namedParameters));
        final boolean hasCardType = count == 0 ? false : true;
        return hasCardType;
    }

}
