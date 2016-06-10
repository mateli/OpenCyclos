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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.services.transactions.InvoiceServiceLocal;
import nl.strohalm.cyclos.services.transactions.exceptions.SendingInvoiceWithMultipleTransferTypesWithCustomFields;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for {@link PaymentCustomFieldServiceLocal}
 * 
 * @author luis
 */
public class PaymentCustomFieldServiceImpl extends BaseCustomFieldServiceImpl<PaymentCustomField> implements PaymentCustomFieldServiceLocal {

    private InvoiceServiceLocal invoiceService;

    protected PaymentCustomFieldServiceImpl() {
        super(PaymentCustomField.class);
    }

    @Override
    public Validator getValueValidator(final TransferType transferType) {
        return getValueValidator(list(transferType, false));
    }

    @Override
    public void link(TransferType transferType, PaymentCustomField customField) {
        transferType = fetchService.fetch(transferType, TransferType.Relationships.LINKED_CUSTOM_FIELDS);
        customField = fetchService.fetch(customField);
        final Collection<PaymentCustomField> linkedCustomFields = transferType.getLinkedCustomFields();
        if (!linkedCustomFields.contains(customField)) {
            // The underlying persistence engine should persist the relationship
            linkedCustomFields.add(customField);
        }
        getCache().clear();
    }

    @Override
    public List<PaymentCustomField> list(final TransferType transferType, final boolean includeDisabled) {
        return getCache().get("_FIELDS_BY_TYPE_" + transferType.getId() + "_" + includeDisabled, new CacheCallback() {
            @Override
            public Object retrieve() {
                return customFieldDao.listPaymentFields(transferType, includeDisabled, fetch);
            }
        });
    }

    @Override
    public List<PaymentCustomField> listForList(final Account account, final boolean loan) {
        return customFieldDao.listPaymentFieldForList(account, loan, fetch);
    }

    @Override
    public List<PaymentCustomField> listForSearch(final Account account, final boolean loan) {
        return customFieldDao.listPaymentFieldForSearch(account, loan, fetch);
    }

    @Override
    public PaymentCustomField loadByInternalName(final String internalName, final Relationship... fetch) {
        return customFieldDao.loadPaymentFieldByInternalName(internalName, fetch);
    }

    @Override
    public void saveValues(final Guarantee guarantee, final boolean validate) {
        final TransferType loanTransferType = guarantee.getGuaranteeType().getLoanTransferType();
        if (validate) {
            getValueValidator(loanTransferType).validate(guarantee);
        }
        doSaveValues(guarantee);
    }

    @Override
    public void saveValues(final Invoice invoice) throws SendingInvoiceWithMultipleTransferTypesWithCustomFields {
        TransferType transferType = invoice.getTransferType();
        if (transferType == null) {
            // Get the possible transfer types
            final List<TransferType> transferTypes = invoiceService.getPossibleTransferTypes(invoice);
            if (transferTypes.size() == 1) {
                transferType = transferTypes.iterator().next();
                invoice.setTransferType(transferType);
            } else {
                // Custom fields only are used when there is a single possible transfer type
                for (final TransferType tt : transferTypes) {
                    if (!tt.getCustomFields().isEmpty()) {
                        throw new SendingInvoiceWithMultipleTransferTypesWithCustomFields();
                    }
                }
                // There will be no custom values
                invoice.setCustomValues(null);
                return;
            }
        }
        getValueValidator(transferType).validate(invoice);
        doSaveValues(invoice);
    }

    @Override
    public void saveValues(final Payment payment) {
        getValueValidator(payment.getType()).validate(payment);
        doSaveValues(payment);
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    protected List<PaymentCustomField> list(final PaymentCustomField field) {
        return list(field.getTransferType(), !field.isEnabled());
    }

    @Override
    protected Collection<? extends Relationship> resolveAdditionalFetch() {
        return Arrays.asList(PaymentCustomField.Relationships.TRANSFER_TYPE);
    }
}
