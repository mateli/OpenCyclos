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
package nl.strohalm.cyclos.controls.accounts.transactionfees;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to edit a transaction fee
 * @author luis
 */
public class EditTransactionFeeForm extends BaseBindingForm {

    private static final long serialVersionUID = 4544878915770776566L;
    private long              accountTypeId;
    private String            nature;
    private long              transferTypeId;
    private long              transactionFeeId;
    private boolean           allowAnyAccount;

    public EditTransactionFeeForm() {
        setTransactionFee("maxAmount", new MapBean("type", "value"));
        setTransactionFee("fromGroups", Collections.emptyList());
        setTransactionFee("toGroups", Collections.emptyList());
        setTransactionFee("brokerGroups", Collections.emptyList());
    }

    public long getAccountTypeId() {
        return accountTypeId;
    }

    public String getNature() {
        return nature;
    }

    public Map<String, Object> getTransactionFee() {
        return values;
    }

    public Object getTransactionFee(final String key) {
        return values.get(key);
    }

    public long getTransactionFeeId() {
        return transactionFeeId;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public boolean isAllowAnyAccount() {
        return allowAnyAccount;
    }

    public void setAccountTypeId(final long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setAllowAnyAccount(final boolean allowAnyAccount) {
        this.allowAnyAccount = allowAnyAccount;
    }

    public void setNature(final String nature) {
        this.nature = nature;
    }

    public void setTransactionFee(final Map<String, Object> map) {
        values = map;
    }

    public void setTransactionFee(final String key, final Object value) {
        values.put(key, value);
    }

    public void setTransactionFeeId(final long transactionFeeId) {
        this.transactionFeeId = transactionFeeId;
    }

    public void setTransferTypeId(final long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

}
