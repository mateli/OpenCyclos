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
package nl.strohalm.cyclos.dao.customizations;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField.Nature;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;

/**
 * Data access object interface for custom fields
 * @author rafael
 */
public interface CustomFieldDAO extends BaseDAO<CustomField>, InsertableDAO<CustomField>, UpdatableDAO<CustomField>, DeletableDAO<CustomField> {

    /**
     * @param field
     * @return true if the field's internal name already was used inside the according to the specified field
     */
    boolean isInternalNameUsed(CustomField field);

    /**
     * Returns a list of all custom fields of the given nature, ordered by it's order. If no entity can be found, returns an empty list. If any
     * exception is thrown by the underlying implementation, it should be wrapped by a DaoException.
     * 
     * @throws DaoException
     */
    List<? extends CustomField> listByNature(Nature nature, Relationship... fetch) throws DaoException;

    /**
     * Lists all member record custom fields for the member record type
     */
    List<MemberRecordCustomField> listMemberRecordFields(MemberRecordType memberRecordType);

    /**
     * Lists all operator custom fields for the given group
     */
    List<OperatorCustomField> listOperatorFields(Member member);

    /**
     * Lists payment custom fields which are visible result column for the given account history. The second argument is a flag indicating whether the
     * custom fields are to be shown under loan details or normal account history
     */
    List<PaymentCustomField> listPaymentFieldForList(Account account, boolean loan, Relationship... fetch);

    /**
     * Lists payment custom fields which are visible as search filters for the given account history. The second argument is a flag indicating whether
     * the custom fields are to be shown under loan details or normal account history
     */
    List<PaymentCustomField> listPaymentFieldForSearch(Account account, boolean loan, Relationship... fetch);

    /**
     * Lists all payment custom fields for the given transfer type
     */
    List<PaymentCustomField> listPaymentFields(TransferType transferType, boolean includeDisabled, Relationship... fetch);

    /**
     * Returns a payment custom field by internal name, no matter the transfer type
     */
    PaymentCustomField loadPaymentFieldByInternalName(String internalName, Relationship[] fetch);

}
