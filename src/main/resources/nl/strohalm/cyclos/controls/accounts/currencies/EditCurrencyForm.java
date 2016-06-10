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
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to edit a currency
 * @author luis
 */
public class EditCurrencyForm extends BaseBindingForm {
    private static final long serialVersionUID = 6583203622757028445L;
    private long              currencyId;
    private boolean           enableARate;
    private boolean           enableDRate;
    private boolean           enableIRate;

    public EditCurrencyForm() {
        final MapBean aRateParameters = new MapBean("id", "initValue", "initDate", "creationValue");
        setCurrency("aRateParameters", aRateParameters);
        final MapBean dRateParameters = new MapBean("id", "interest", "baseMalus", "minimalD", "initValue", "initDate", "creationValue");
        setCurrency("dRateParameters", dRateParameters);
        final MapBean iRateParameters = new MapBean("id");
        setCurrency("iRateParameters", iRateParameters);
    }

    public Map<String, Object> getCurrency() {
        return values;
    }

    public Object getCurrency(final String key) {
        return values.get(key);
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public boolean isEnableARate() {
        return enableARate;
    }

    public boolean isEnableDRate() {
        return enableDRate;
    }

    public boolean isEnableIRate() {
        return enableIRate;
    }

    public void setCurrency(final Map<String, Object> map) {
        values = map;
    }

    public void setCurrency(final String key, final Object value) {
        values.put(key, value);
    }

    public void setCurrencyId(final long currencyId) {
        this.currencyId = currencyId;
    }

    public void setEnableARate(final boolean enableARate) {
        this.enableARate = enableARate;
    }

    public void setEnableDRate(final boolean enableDRate) {
        this.enableDRate = enableDRate;
    }

    public void setEnableIRate(final boolean enableIRate) {
        this.enableIRate = enableIRate;
    }

}
