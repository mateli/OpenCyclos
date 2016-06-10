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
package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;

/**
 * Custom field value for payments
 * @author luis
 */
public class PaymentCustomFieldValue extends CustomFieldValue {

    public static enum Relationships implements Relationship {
        SCHEDULED_PAYMENT("scheduledPayment"), TRANSFER("transfer"), INVOICE("invoice"), GUARANTEE("guarantee");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -5360132689596383745L;

    private ScheduledPayment  scheduledPayment;
    private Transfer          transfer;
    private Invoice           invoice;
    private Guarantee         guarantee;
    // This one is not actually stored, but needed in order to validate
    private DoPaymentDTO      doPaymentDTO;

    public Guarantee getGuarantee() {
        return guarantee;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    @Override
    public Object getOwner() {
        if (transfer != null) {
            return transfer;
        }
        if (invoice != null) {
            return invoice;
        }
        if (scheduledPayment != null) {
            return scheduledPayment;
        }
        if (guarantee != null) {
            return guarantee;
        }
        if (doPaymentDTO != null) {
            return doPaymentDTO;
        }

        return null;
    }

    public ScheduledPayment getScheduledPayment() {
        return scheduledPayment;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setGuarantee(final Guarantee guarantee) {
        this.guarantee = guarantee;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public void setOwner(final Object owner) {
        scheduledPayment = null;
        transfer = null;
        invoice = null;
        guarantee = null;
        doPaymentDTO = null;

        if (owner instanceof Transfer) {
            transfer = (Transfer) owner;
        } else if (owner instanceof ScheduledPayment) {
            scheduledPayment = (ScheduledPayment) owner;
        } else if (owner instanceof Invoice) {
            invoice = (Invoice) owner;
        } else if (owner instanceof Guarantee) {
            guarantee = (Guarantee) owner;
        } else if (owner instanceof DoPaymentDTO) {
            doPaymentDTO = (DoPaymentDTO) owner;
        }
    }

    public void setScheduledPayment(final ScheduledPayment scheduledPayment) {
        this.scheduledPayment = scheduledPayment;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }
}
