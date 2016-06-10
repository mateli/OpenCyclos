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

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAd;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.LoanGroupCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberRecord;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation class for custom field values
 * @author rafael
 */
public class CustomFieldValueDAOImpl extends BaseDAOImpl<CustomFieldValue> implements CustomFieldValueDAO {

    /**
     * Represents a meta data entry about a specific custom field nature
     * @author luis
     */
    private static class MetaEntry {
        private final String                            ownerProperty;
        private final Class<? extends CustomFieldValue> type;

        public MetaEntry(final String ownerProperty, final Class<? extends CustomFieldValue> type) {
            this.ownerProperty = ownerProperty;
            this.type = type;
        }

        public String getOwnerProperty() {
            return ownerProperty;
        }

        public Class<? extends CustomFieldValue> getType() {
            return type;
        }
    }

    public CustomFieldValueDAOImpl() {
        super(CustomFieldValue.class);
    }

    @Override
    public CustomFieldValue load(final CustomField field, final Object owner, final Relationship... fetch) {

        final MetaEntry meta = metaEntryFor(owner);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("field", field);
        params.put("owner", owner);
        final CustomFieldValue fieldValue = uniqueResult("from " + meta.getType().getName() + " v where v.field = :field and v." + meta.getOwnerProperty() + " = :owner", params);
        if (fieldValue == null) {
            throw new EntityNotFoundException(meta.getType());
        }
        // If there's a fetch, use the normal load
        if (fetch != null && fetch.length > 0) {
            return load(fieldValue.getId(), fetch);
        } else {
            return fieldValue;
        }
    }

    @Override
    public int moveValues(final CustomFieldPossibleValue oldValue, final CustomFieldPossibleValue newValue) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("oldValue", oldValue);
        namedParameters.put("newValue", newValue);
        return bulkUpdate("update CustomFieldValue v set v.possibleValue = :newValue where v.possibleValue = :oldValue", namedParameters);
    }

    @Override
    public int unHideValues(final MemberCustomField field) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("fieldId", field.getId());
        StringBuffer hql = new StringBuffer();
        hql.append("update MemberCustomFieldValue mv set mv.hidden = false ");
        hql.append("where mv.field.id = :fieldId");
        return bulkUpdate(hql.toString(), namedParameters);
    }

    @Override
    public boolean valueExists(final CustomFieldValue value) {
        Object owner = value.getOwner();
        final MetaEntry meta = metaEntryFor(owner);
        final Entity ownerEntity = owner instanceof Entity ? (Entity) owner : null;
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final CustomField field = getFetchDao().fetch(value.getField());
        final StringBuilder hql = new StringBuilder();
        hql.append(" select v ");
        hql.append(" from ").append(meta.getType().getName()).append(" v");

        // Specific left joins might be needed
        if (field instanceof PaymentCustomField) {
            hql.append(" left join v.transfer t");
            hql.append(" left join v.scheduledPayment sp");
        }

        hql.append(" where v.field = :field ");
        // Check specific field types
        if (field instanceof MemberCustomField) {
            // Ignore members in removed group
            hql.append(" and v.member.group.status <> :removed");
            namedParameters.put("removed", Group.Status.REMOVED);
        } else if (field instanceof AdCustomField) {
            // Ignore ads which have been removed
            hql.append(" and v.ad.deleteDate is null");
        } else if (field instanceof PaymentCustomField) {
            // Ignore transfers which were denied or canceled
            hql.append(" and ((t.id is null) or (t.status not in (:denied, :canceled)))");
            hql.append(" and ((sp.id is null) or (sp.status not in (:denied, :canceled)))");
            namedParameters.put("denied", Payment.Status.DENIED);
            namedParameters.put("canceled", Payment.Status.CANCELED);
        }
        if (field.getType() == CustomField.Type.ENUMERATED) {
            hql.append(" and v.possibleValue.value = :value");
        } else {
            hql.append(" and v.stringValue = :value");
        }
        namedParameters.put("field", field);
        String val = value.getValue();
        if (StringUtils.isNotEmpty(field.getPattern())) {
            val = StringHelper.removeMask(field.getPattern(), val);
        }
        namedParameters.put("value", val);

        final CustomFieldValue existingValue = uniqueResult(hql.toString(), namedParameters);
        if (ownerEntity == null || ownerEntity.isTransient()) {
            return existingValue != null;
        } else {
            return existingValue != null && !ownerEntity.equals(existingValue.getOwner());
        }
    }

    private MetaEntry metaEntryFor(final Object owner) {
        // Determine the type
        String ownerProperty;
        Class<? extends CustomFieldValue> type;
        if (owner instanceof Member) {
            ownerProperty = "member";
            type = MemberCustomFieldValue.class;
        } else if (owner instanceof PendingMember) {
            ownerProperty = "pendingMember";
            type = MemberCustomFieldValue.class;
        } else if (owner instanceof ImportedMember) {
            ownerProperty = "importedMember";
            type = MemberCustomFieldValue.class;
        } else if (owner instanceof Administrator) {
            ownerProperty = "admin";
            type = AdminCustomFieldValue.class;
        } else if (owner instanceof Operator) {
            ownerProperty = "operator";
            type = OperatorCustomFieldValue.class;
        } else if (owner instanceof Ad) {
            ownerProperty = "ad";
            type = AdCustomFieldValue.class;
        } else if (owner instanceof Transfer || owner instanceof DoPaymentDTO) {
            ownerProperty = "transfer";
            type = PaymentCustomFieldValue.class;
        } else if (owner instanceof ScheduledPayment) {
            ownerProperty = "scheduledPayment";
            type = PaymentCustomFieldValue.class;
        } else if (owner instanceof Invoice) {
            ownerProperty = "invoice";
            type = PaymentCustomFieldValue.class;
        } else if (owner instanceof Guarantee) {
            ownerProperty = "guarantee";
            type = PaymentCustomFieldValue.class;
        } else if (owner instanceof LoanGroup) {
            ownerProperty = "loanGroup";
            type = LoanGroupCustomFieldValue.class;
        } else if (owner instanceof MemberRecord) {
            ownerProperty = "memberRecord";
            type = MemberRecordCustomFieldValue.class;
        } else if (owner instanceof ImportedMemberRecord) {
            ownerProperty = "memberRecord";
            type = ImportedMemberRecordCustomFieldValue.class;
        } else if (owner instanceof ImportedAd) {
            ownerProperty = "ad";
            type = ImportedAdCustomFieldValue.class;
        } else {
            throw new UnexpectedEntityException();
        }
        return new MetaEntry(ownerProperty, type);
    }

}
