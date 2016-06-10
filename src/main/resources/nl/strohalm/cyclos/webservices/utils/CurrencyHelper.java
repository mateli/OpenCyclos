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
package nl.strohalm.cyclos.webservices.utils;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.services.accounts.CurrencyServiceLocal;
import nl.strohalm.cyclos.webservices.model.CurrencyVO;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class for currencies<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * @author luis
 */
public class CurrencyHelper {

    private CurrencyServiceLocal currencyServiceLocal;

    /**
     * Attempts to load by either id or symbol
     */
    public Currency loadByIdOrSymbol(final Long id, final String symbol) {
        return resolve(id == null ? symbol : id.toString());
    }

    /**
     * Given a currency symbol or identifier as string, returns the {@link Currency} instance
     */
    public Currency resolve(final String string) {
        if (StringUtils.isNotEmpty(string)) {
            return currencyServiceLocal.loadBySymbolOrId(string);
        }
        return null;
    }

    public void setCurrencyServiceLocal(final CurrencyServiceLocal currencyService) {
        currencyServiceLocal = currencyService;
    }

    public CurrencyVO toVO(final Currency currency) {
        if (currency == null) {
            return null;
        }
        final CurrencyVO vo = new CurrencyVO();
        vo.setId(currency.getId());
        vo.setName(currency.getName());
        vo.setSymbol(currency.getSymbol());
        return vo;
    }
}
