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
package nl.strohalm.cyclos.controls.accounts.details;

import org.apache.struts.action.ActionForm;

/**
 * Form used to retrieve a transaction details
 * @author luis
 */
public class ViewTransactionForm extends ActionForm {
    private static final long serialVersionUID = 2231197122604612537L;
    private long              transferId;
    private long              memberId;
    private long              typeId;
    private String            transactionPassword;
    private boolean           showActions;

    public long getMemberId() {
        return memberId;
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public long getTransferId() {
        return transferId;
    }

    public long getTypeId() {
        return typeId;
    }

    public boolean isShowActions() {
        return showActions;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setShowActions(final boolean showActions) {
        this.showActions = showActions;
    }

    public void setTransactionPassword(final String transactionPassword) {
        this.transactionPassword = transactionPassword;
    }

    public void setTransferId(final long transferId) {
        this.transferId = transferId;
    }

    public void setTypeId(final long accountId) {
        typeId = accountId;
    }

}
