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
package nl.strohalm.cyclos.controls.payments.scheduled;

import org.apache.struts.action.ActionForm;

/**
 * Form used to execute actions over a scheduled payment
 * @author Jefferson Magno
 */
public class ScheduledPaymentForm extends ActionForm {

    private static final long serialVersionUID = -5893287673994930844L;
    private long              paymentId;
    private long              transferId;
    private String            transactionPassword;
    private boolean           showActions;

    public long getPaymentId() {
        return paymentId;
    }

    public String getTransactionPassword() {
        return transactionPassword;
    }

    public long getTransferId() {
        return transferId;
    }

    public boolean isShowActions() {
        return showActions;
    }

    public void setPaymentId(final long paymentId) {
        this.paymentId = paymentId;
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

}
