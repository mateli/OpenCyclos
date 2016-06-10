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
package nl.strohalm.cyclos.services.customization;

import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.services.transactions.exceptions.SendingInvoiceWithMultipleTransferTypesWithCustomFields;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Local interface for {@link PaymentCustomFieldService}
 * 
 * @author luis
 */
public interface PaymentCustomFieldServiceLocal extends PaymentCustomFieldService, BaseCustomFieldServiceLocal<PaymentCustomField> {

    /**
     * Returns an custom field validator for the given payment type
     */
    Validator getValueValidator(TransferType transferType);

    /**
     * Saves the custom values for a guarantee
     */
    void saveValues(Guarantee guarantee, boolean validate);

    /**
     * Saves the custom values for an invoice. This method also ensures that, in invoices from member to member (with only a destination account
     * type), when multiple TTs are possible and at least one have custom fields, a {@link SendingInvoiceWithMultipleTransferTypesWithCustomFields} is
     * thrown
     * @throws SendingInvoiceWithMultipleTransferTypesWithCustomFields When there are more than one possible TTs and at least one have custom fields
     */
    void saveValues(Invoice invoice) throws SendingInvoiceWithMultipleTransferTypesWithCustomFields;

    /**
     * Saves the custom values for a payment
     */
    void saveValues(Payment payment);
}
