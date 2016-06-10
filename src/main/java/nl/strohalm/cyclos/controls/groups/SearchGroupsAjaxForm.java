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
package nl.strohalm.cyclos.controls.groups;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used for Ajax groups search
 * @author jefferson
 */
public class SearchGroupsAjaxForm extends BaseBindingForm {

    private static final long serialVersionUID = -1009308718925545935L;
    private String            accountTypeId;
    private String            paymentFilterId;
    private String[]          groupFilterIds;
    private String[]          natures;
    private String[]          status;

    public String getAccountTypeId() {
        return accountTypeId;
    }

    public String[] getGroupFilterIds() {
        return groupFilterIds;
    }

    public String[] getNatures() {
        return natures;
    }

    public String getPaymentFilterId() {
        return paymentFilterId;
    }

    public String[] getStatus() {
        return status;
    }

    public void setAccountTypeId(final String accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setGroupFilterIds(final String[] groupFilterIds) {
        this.groupFilterIds = groupFilterIds;
    }

    public void setNatures(final String[] natures) {
        this.natures = natures;
    }

    public void setPaymentFilterId(final String paymentFilterId) {
        this.paymentFilterId = paymentFilterId;
    }

    public void setStatus(final String[] status) {
        this.status = status;
    }

}
