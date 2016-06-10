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
package nl.strohalm.cyclos.controls.accounts.currencies;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.services.accounts.rates.WhatRate;

/**
 * Form used to reinitialize Rates
 * @author rinke
 */
public class ReinitializeRatesForm extends BaseBindingForm {
    private static final long serialVersionUID = 7008145296104068994L;
    private long              currencyId;
    private boolean           doAInit;
    private boolean           doDInit;
    private boolean           doIInit;

    public ReinitializeRatesForm() {
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public Map<String, Object> getReinitializeRatesDto() {
        return values;
    }

    public Object getReinitializeRatesDto(final String key) {
        return values.get(key);
    }

    public WhatRate getWhatRate() {
        final WhatRate whatRate = new WhatRate();
        whatRate.setaRate(doAInit);
        whatRate.setdRate(doDInit);
        whatRate.setiRate(doIInit);
        return whatRate;
    }

    public boolean isDoAInit() {
        return doAInit;
    }

    public boolean isDoDInit() {
        return doDInit;
    }

    public boolean isDoIInit() {
        return doIInit;
    }

    public void setCurrencyId(final long currencyId) {
        this.currencyId = currencyId;
    }

    public void setDoAInit(final boolean doAInit) {
        this.doAInit = doAInit;
    }

    public void setDoDInit(final boolean doDInit) {
        this.doDInit = doDInit;
    }

    public void setDoIInit(final boolean doIInit) {
        this.doIInit = doIInit;
    }

    public void setReinitializeRatesDto(final Map<String, Object> map) {
        values = map;
    }

    public void setReinitializeRatesDto(final String key, final Object value) {
        values.put(key, value);
    }

}
