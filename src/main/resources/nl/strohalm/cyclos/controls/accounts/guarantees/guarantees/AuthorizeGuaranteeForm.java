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
package nl.strohalm.cyclos.controls.accounts.guarantees.guarantees;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

public class AuthorizeGuaranteeForm extends BaseBindingForm {

    private static final long serialVersionUID = -2867242431347609973L;
    private Long              guaranteeId;
    private boolean           automaticLoanAuthorization;

    public AuthorizeGuaranteeForm() {
        setGuarantee("validity", new MapBean("begin", "end"));
        setGuarantee("creditFeeSpec", new MapBean("type", "fee", "readonly"));
        setGuarantee("issueFeeSpec", new MapBean("type", "fee", "readonly"));
        setGuarantee("customValues", new MapBean(true, "field", "value"));
    }

    public Map<String, Object> getGuarantee() {
        return values;
    }

    public Object getGuarantee(final String key) {
        return values.get(key);
    }

    public Long getGuaranteeId() {
        return guaranteeId;
    }

    public boolean isAutomaticLoanAuthorization() {
        return automaticLoanAuthorization;
    }

    public void setAutomaticLoanAuthorization(final boolean automaticLoanAuthorization) {
        this.automaticLoanAuthorization = automaticLoanAuthorization;
    }

    public void setGuarantee(final Map<String, Object> values) {
        this.values = values;
    }

    public void setGuarantee(final String key, final Object value) {
        values.put(key, value);
    }

    public void setGuaranteeId(final Long guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

}
