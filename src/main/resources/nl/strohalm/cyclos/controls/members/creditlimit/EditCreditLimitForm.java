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
package nl.strohalm.cyclos.controls.members.creditlimit;

import org.apache.struts.action.ActionForm;

/**
 * Form used to edit a member's credit limit
 * @author luis
 */
public class EditCreditLimitForm extends ActionForm {
    private static final long serialVersionUID = 5254024498504729599L;
    private long              memberId;
    private long[]            accountTypeIds;
    private String[]          newCreditLimits;
    private String[]          newUpperCreditLimits;

    public long[] getAccountTypeIds() {
        return accountTypeIds;
    }

    public long getMemberId() {
        return memberId;
    }

    public String[] getNewCreditLimits() {
        return newCreditLimits;
    }

    public String[] getNewUpperCreditLimits() {
        return newUpperCreditLimits;
    }

    public void setAccountTypeIds(final long[] accountTypeIds) {
        this.accountTypeIds = accountTypeIds;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setNewCreditLimits(final String[] newCreditLimits) {
        this.newCreditLimits = newCreditLimits;
    }

    public void setNewUpperCreditLimits(final String[] newUpperCreditLimits) {
        this.newUpperCreditLimits = newUpperCreditLimits;
    }
}
