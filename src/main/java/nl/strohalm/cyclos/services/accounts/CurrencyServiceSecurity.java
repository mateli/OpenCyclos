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

import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.accounts.rates.WhatRate;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * Security implementation for {@link CurrencyService}
 * 
 * @author Rinke
 */
public class CurrencyServiceSecurity extends BaseServiceSecurity implements CurrencyService {

    private CurrencyServiceLocal    currencyService;
    private AccountTypeServiceLocal accountTypeService;

    @Override
    public List<Currency> listAll() {
        List<Currency> currencies = currencyService.listAll();
        // Filter out the currencies which are not visible
        for (Iterator<Currency> iterator = currencies.iterator(); iterator.hasNext();) {
            if (!isVisible(iterator.next())) {
                iterator.remove();
            }
        }
        return currencies;
    }

    @Override
    public List<Currency> listByMember(final Member member) {
        permissionService.permission(member)
                .admin(AdminMemberPermission.ACCOUNTS_INFORMATION)
                .broker(BrokerPermission.ACCOUNTS_INFORMATION)
                .member()
                .operator() // Cannot check OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION because an operator which can make / receive payments
                            // also needs to view currencies
                .check();
        return currencyService.listByMember(member);
    }

    @Override
    public List<Currency> listByMemberGroup(final MemberGroup group) {
        PermissionHelper.checkContains(permissionService.getVisibleMemberGroups(), group);
        return currencyService.listByMemberGroup(group);
    }

    @Override
    public List<Currency> listDRatedCurrencies() {
        return currencyService.listDRatedCurrencies();
    }

    @Override
    public Currency load(final Long id) {
        Currency currency = currencyService.load(id);
        checkVisible(currency);
        return currency;
    }

    @Override
    public Currency loadBySymbolOrId(final String symbolOrId) {
        Currency currency = currencyService.loadBySymbolOrId(symbolOrId);
        checkVisible(currency);
        return currency;
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.CURRENCIES_MANAGE).check();
        return currencyService.remove(ids);
    }

    @Override
    public Currency save(final Currency currency, final WhatRate whatRate) {
        permissionService.permission().admin(AdminSystemPermission.CURRENCIES_MANAGE).check();
        return currencyService.save(currency, whatRate);
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setCurrencyServiceLocal(final CurrencyServiceLocal currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public void validate(final Currency currency, final WhatRate whatRate) {
        // no permissions on validation
        currencyService.validate(currency, whatRate);
    }

    private void checkVisible(final Currency currency) {
        if (!isVisible(currency)) {
            throw new PermissionDeniedException();
        }
    }

    private boolean isVisible(final Currency currency) {
        if (permissionService.hasPermission(AdminSystemPermission.CURRENCIES_VIEW)) {
            return true;
        }
        // As currency visibility is not direct, but through account types, we have to check which are the visible account types
        for (AccountType type : accountTypeService.getVisibleAccountTypes()) {
            type = fetchService.fetch(type, AccountType.Relationships.CURRENCY);
            if (type.getCurrency().equals(currency)) {
                return true;
            }
        }
        return false;
    }
}
