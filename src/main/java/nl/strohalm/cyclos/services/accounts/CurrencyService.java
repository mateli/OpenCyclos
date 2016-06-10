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
package nl.strohalm.cyclos.services.accounts;

import java.util.List;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.accounts.rates.WhatRate;

/**
 * Service interface for currencies used to manage various currencies in the system.
 * @author luis
 */
public interface CurrencyService extends Service {

    /**
     * Lists all currencies
     */
    List<Currency> listAll();

    /**
     * Lists the currencies accessible to the given member
     */
    List<Currency> listByMember(Member member);

    /**
     * Lists currencies of account types associated with the given group
     */
    List<Currency> listByMemberGroup(MemberGroup group);

    /**
     * Lists currencies having D-rate Enabled.
     */
    List<Currency> listDRatedCurrencies();

    /**
     * Loads a currency by id
     */
    Currency load(Long id);

    /**
     * Loads a currency by symbol or identifier
     */
    Currency loadBySymbolOrId(String symbolOrId);

    /**
     * Removes the specified currencies
     */
    int remove(Long... ids);

    /**
     * Saves the currency
     */
    Currency save(Currency currency, WhatRate whatRate);

    /**
     * Validate the specified currency
     */
    void validate(Currency currency, WhatRate whatRate);

}
