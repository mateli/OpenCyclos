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
package nl.strohalm.cyclos.controls.accounts.paymentfilters;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to edit a payment filter
 * @author luis
 */
public class EditPaymentFilterForm extends BaseBindingForm {
    private static final long serialVersionUID = 3789843761994065906L;
    private long              accountTypeId;
    private long              paymentFilterId;

    public EditPaymentFilterForm() {
        setPaymentFilter("groups", Collections.emptyList());
        setPaymentFilter("transferTypes", Collections.emptyList());
    }

    public long getAccountTypeId() {
        return accountTypeId;
    }

    public Map<String, Object> getPaymentFilter() {
        return values;
    }

    public Object getPaymentFilter(final String key) {
        return values.get(key);
    }

    public long getPaymentFilterId() {
        return paymentFilterId;
    }

    public void setAccountTypeId(final long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setPaymentFilter(final Map<String, Object> map) {
        values = map;
    }

    public void setPaymentFilter(final String key, final Object value) {
        values.put(key, value);
    }

    public void setPaymentFilterId(final long paymentFilterId) {
        this.paymentFilterId = paymentFilterId;
    }
}
