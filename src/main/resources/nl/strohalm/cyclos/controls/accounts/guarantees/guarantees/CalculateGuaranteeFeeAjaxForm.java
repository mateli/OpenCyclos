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

import nl.strohalm.cyclos.utils.binding.MapBean;

import org.apache.struts.action.ActionForm;

public class CalculateGuaranteeFeeAjaxForm extends ActionForm {

    private static final long serialVersionUID = 4248067363018377462L;
    private String            guaranteeTypeId;
    private String            amount;
    private MapBean           validity;
    private MapBean           creditFeeSpec;
    private MapBean           issueFeeSpec;

    public CalculateGuaranteeFeeAjaxForm() {
        creditFeeSpec = new MapBean("type", "fee", "readonly");
        issueFeeSpec = new MapBean("type", "fee", "readonly");
        validity = new MapBean("begin", "end");
    }

    public String getAmount() {
        return amount;
    }

    public MapBean getCreditFeeSpec() {
        return creditFeeSpec;
    }

    public String getGuaranteeTypeId() {
        return guaranteeTypeId;
    }

    public MapBean getIssueFeeSpec() {
        return issueFeeSpec;
    }

    public MapBean getValidity() {
        return validity;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public void setCreditFeeSpec(final MapBean creditFeeSpec) {
        this.creditFeeSpec = creditFeeSpec;
    }

    public void setGuaranteeTypeId(final String guaranteeTypeId) {
        this.guaranteeTypeId = guaranteeTypeId;
    }

    public void setIssueFeeSpec(final MapBean issueFeeSpec) {
        this.issueFeeSpec = issueFeeSpec;
    }

    public void setValidity(final MapBean validity) {
        this.validity = validity;
    }
}
