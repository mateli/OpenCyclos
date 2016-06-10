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

import org.apache.struts.action.ActionForm;

/**
 * Form used to retrieve details about a loan
 * @author luis
 */
public class LoanDetailsForm extends ActionForm {
    private static final long serialVersionUID = -5332918704436171080L;
    private long              loanGroupId;
    private long              loanId;
    private long              loanPaymentId;
    private long              guaranteeId;
    private long              memberId;

    public long getGuaranteeId() {
        return guaranteeId;
    }

    public long getLoanGroupId() {
        return loanGroupId;
    }

    public long getLoanId() {
        return loanId;
    }

    public long getLoanPaymentId() {
        return loanPaymentId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setGuaranteeId(final long guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public void setLoanGroupId(final long loanGroupId) {
        this.loanGroupId = loanGroupId;
    }

    public void setLoanId(final long loanId) {
        this.loanId = loanId;
    }

    public void setLoanPaymentId(final long loanPaymentId) {
        this.loanPaymentId = loanPaymentId;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }
}
