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
package nl.strohalm.cyclos.controls.accounts.transfertypes;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to edit a transfer type
 * @author luis
 */
public class EditTransferTypeForm extends BaseBindingForm {
    private static final long serialVersionUID = -6042254513316216690L;
    private long              accountTypeId;
    private long              transferTypeId;

    public EditTransferTypeForm() {
        final MapBean loan = new MapBean("type", "repaymentDays", "repaymentType", "monthlyInterest", "grantFee", "expirationFee", "expirationDailyInterest", "monthlyInterestRepaymentType", "grantFeeRepaymentType", "expirationFeeRepaymentType", "expirationDailyInterestRepaymentType");
        loan.set("grantFee", new MapBean("type", "value"));
        loan.set("expirationFee", new MapBean("type", "value"));
        setTransferType("loan", loan);
        setTransferType("context", new MapBean("payment", "selfPayment", "mobile", "externalPayment"));
        setTransferType("feedbackExpirationTime", new MapBean("number", "field"));
        setTransferType("feedbackReplyExpirationTime", new MapBean("number", "field"));
        setTransferType("channels", Collections.emptyList());
    }

    public long getAccountTypeId() {
        return accountTypeId;
    }

    public Map<String, Object> getTransferType() {
        return values;
    }

    public Object getTransferType(final String key) {
        return values.get(key);
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setAccountTypeId(final long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setTransferType(final Map<String, Object> map) {
        values = map;
    }

    public void setTransferType(final String key, final Object value) {
        values.put(key, value);
    }

    public void setTransferTypeId(final long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }
}
