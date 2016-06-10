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
package nl.strohalm.cyclos.dao.accounts;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;

/**
 * DAO implementation for currency
 * @author luis
 */
public class CurrencyDAOImpl extends BaseDAOImpl<Currency> implements CurrencyDAO {

    public CurrencyDAOImpl() {
        super(Currency.class);
    }

    @Override
    public int delete(final boolean flush, final Long... ids) {
        for (final Long id : ids) {
            final Map<String, Object> namedParameters = new HashMap<String, Object>();
            final Currency currency = load(id);
            currency.setaRateParameters(null);
            currency.setdRateParameters(null);
            currency.setiRateParameters(null);
            update(currency);

            namedParameters.put("currency", currency);

            // delete all related rates
            bulkUpdate("delete from ARateParameters r where r.currency = :currency", namedParameters);
            bulkUpdate("delete from DRateParameters r where r.currency = :currency", namedParameters);
            bulkUpdate("delete from IRateParameters r where r.currency = :currency", namedParameters);
        }
        return super.delete(flush, ids);
    }

    @Override
    public List<Currency> listAll(final Relationship... fetch) {
        List<Currency> currencies = list("from Currency c order by c.name", null);
        for (int i = 0; i < currencies.size(); i++) {
            currencies.set(i, getFetchDao().fetch(currencies.get(i), fetch));
        }
        return currencies;
    }

    @Override
    public List<Currency> listByMemberGroup(final MemberGroup group) {
        final String hql = "from Currency c where exists (select mgas.id from MemberGroupAccountSettings mgas where mgas.group = :group and mgas.accountType.currency = c) order by c.name";
        final Map<String, ?> namedParameters = Collections.singletonMap("group", group);
        return list(hql, namedParameters);
    }

    @Override
    public Currency loadBySymbol(final String symbol, final Relationship... fetch) throws EntityNotFoundException {
        Map<String, String> params = Collections.singletonMap("symbol", symbol);
        Currency currency = uniqueResult("from Currency c where c.symbol = :symbol", params);
        if (currency == null) {
            throw new EntityNotFoundException(Currency.class);
        }
        return getFetchDao().fetch(currency, fetch);
    }
}
