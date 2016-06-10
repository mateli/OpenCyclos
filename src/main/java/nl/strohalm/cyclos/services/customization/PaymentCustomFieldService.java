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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;

/**
 * Service for payment custom fields
 * 
 * @author luis
 */
public interface PaymentCustomFieldService extends BaseCustomFieldService<PaymentCustomField> {

    /**
     * Links the transfer type with the payment custom field
     */
    void link(TransferType transferType, PaymentCustomField customField);

    /**
     * Lists all payment custom fields for the transfer type
     */
    List<PaymentCustomField> list(TransferType transferType, boolean includeDisabled);

    /**
     * Lists payment custom fields which are visible result column for the given account history. The second argument is a flag indicating whether the
     * custom fields are to be shown under loan details or normal account history
     */
    List<PaymentCustomField> listForList(Account account, boolean loan);

    /**
     * Lists payment custom fields which are visible as search filters for the given account history. The second argument is a flag indicating whether
     * the custom fields are to be shown under loan details or normal account history
     */
    List<PaymentCustomField> listForSearch(Account account, boolean loan);

    /**
     * Loads a payment field by internal name
     */
    PaymentCustomField loadByInternalName(String internalName, Relationship... fetch);

}
