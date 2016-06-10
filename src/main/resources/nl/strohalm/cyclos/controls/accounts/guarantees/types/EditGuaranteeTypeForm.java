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
package nl.strohalm.cyclos.controls.accounts.guarantees.types;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

public class EditGuaranteeTypeForm extends BaseBindingForm {
    private static final long serialVersionUID = 8841319162860447765L;
    private Long              guaranteeTypeId;

    public EditGuaranteeTypeForm() {
        setGuaranteeType("pendingGuaranteeExpiration", new MapBean("field", "number"));
        setGuaranteeType("paymentObligationPeriod", new MapBean("field", "number"));
        setGuaranteeType("creditFee", new MapBean("type", "fee", "readonly"));
        setGuaranteeType("issueFee", new MapBean("type", "fee", "readonly"));
    }

    public Map<String, Object> getGuaranteeType() {
        return values;
    }

    public Object getGuaranteeType(final String key) {
        return values.get(key);
    }

    public Long getGuaranteeTypeId() {
        return guaranteeTypeId;
    }

    public void setGuaranteeType(final Map<String, Object> map) {
        values = map;
    }

    public void setGuaranteeType(final String key, final Object value) {
        values.put(key, value);
    }

    public void setGuaranteeTypeId(final Long guaranteeTypeId) {
        this.guaranteeTypeId = guaranteeTypeId;
    }
}
