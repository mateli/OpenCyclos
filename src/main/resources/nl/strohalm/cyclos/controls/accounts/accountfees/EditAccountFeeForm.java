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
package nl.strohalm.cyclos.controls.accounts.accountfees;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to edit an account fee
 * @author luis
 */
public class EditAccountFeeForm extends BaseBindingForm {
    private static final long serialVersionUID = 2585234618262951011L;
    private long              accountTypeId;
    private long              accountFeeId;

    public EditAccountFeeForm() {
        setAccountFee("recurrence", new MapBean("number", "field"));
        setAccountFee("tolerance", new MapBean("number", "field"));
        setAccountFee("groups", Collections.emptyList());
    }

    public Map<String, Object> getAccountFee() {
        return values;
    }

    public Object getAccountFee(final String key) {
        return values.get(key);
    }

    public long getAccountFeeId() {
        return accountFeeId;
    }

    public long getAccountTypeId() {
        return accountTypeId;
    }

    public String getPaymentDirection() {
        return (String) getAccountFee("paymentDirection");
    }

    public void setAccountFee(final Map<String, Object> map) {
        values = map;
    }

    public void setAccountFee(final String key, final Object value) {
        values.put(key, value);
    }

    public void setAccountFeeId(final long transferTypeId) {
        accountFeeId = transferTypeId;
    }

    public void setAccountTypeId(final long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setPaymentDirection(final String paymentDirection) {
        setAccountFee("paymentDirection", paymentDirection);
    }

}
