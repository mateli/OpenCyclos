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
package nl.strohalm.cyclos.controls.loans;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to grant a loan
 * @author luis
 */
public class GrantLoanForm extends BaseBindingForm {
    private static final long serialVersionUID = -1184579781409625318L;
    private long              loanGroupId;
    private long              memberId;

    public GrantLoanForm() {
        setLoan("customValues", new MapBean(true, "field", "value"));
        setLoan("payments", new MapBean(true, "expirationDate", "amount"));
    }

    public Map<String, Object> getLoan() {
        return values;
    }

    public Object getLoan(final String key) {
        return values.get(key);
    }

    public long getLoanGroupId() {
        return loanGroupId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setLoan(final Map<String, Object> loan) {
        values = loan;
    }

    public void setLoan(final String key, final Object value) {
        values.put(key, value);
    }

    public void setLoanGroupId(final long loanGroupId) {
        this.loanGroupId = loanGroupId;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }
}
