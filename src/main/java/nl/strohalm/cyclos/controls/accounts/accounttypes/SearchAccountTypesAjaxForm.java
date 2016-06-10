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
package nl.strohalm.cyclos.controls.accounts.accounttypes;

import org.apache.struts.action.ActionForm;

/**
 * Form for searching account types using Ajax
 * @author luis
 */
public class SearchAccountTypesAjaxForm extends ActionForm {
    private static final long serialVersionUID = 2788986964514635961L;
    private String            canPayOwnerId;
    private String            ownerId;
    private String            currencyId;
    private String[]          memberGroupId;
    private boolean           scheduling;

    public String getCanPayOwnerId() {
        return canPayOwnerId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public String[] getMemberGroupId() {
        return memberGroupId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public boolean isScheduling() {
        return scheduling;
    }

    public void setCanPayOwnerId(final String canPayOwnerId) {
        this.canPayOwnerId = canPayOwnerId;
    }

    public void setCurrencyId(final String currencyId) {
        this.currencyId = currencyId;
    }

    public void setMemberGroupId(final String[] memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public void setOwnerId(final String ownerId) {
        this.ownerId = ownerId;
    }

    public void setScheduling(final boolean scheduling) {
        this.scheduling = scheduling;
    }

}
