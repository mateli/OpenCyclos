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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * A customized field for a given entity
 * @author luis
 */
public abstract class CustomField extends Entity {

    public static enum Control implements StringValuedEnum {
        TEXT("text", true),
        TEXTAREA("textarea", true),
        RICH_EDITOR("richEditor", false),
        SELECT("select", true),
        RADIO("radio", true),
        CHECKBOX("checkbox", false),
        MEMBER_AUTOCOMPLETE("member", false);

        private final String  value;
        private final boolean useSize;

        private Control(final String value, final boolean useSize) {
            this.value = value;
            this.useSize = useSize;
        }

        @Override
        public String getValue() {
            return value;
        }

        public boolean isUseSize() {
            return useSize;
        }
    }

    public static enum Nature implements StringValuedEnum {
        AD("ad"),
        ADMIN("admin"),
        PAYMENT("payment"),
        LOAN_GROUP("loan_group"),
        MEMBER("member"),
        OPERATOR("op"),
        MEMBER_RECORD("member_record");

        public static Nature getByOwner(final Object owner) {
            return owner == null ? null : getByOwnerType(owner.getClass());
        }

        public static Nature getByOwnerType(final Class<?> ownerType) {
            if (Ad.class.isAssignableFrom(ownerType)) {
                return AD;
            } else if (Member.class.isAssignableFrom(ownerType)) {
                return MEMBER;
            } else if (Administrator.class.isAssignableFrom(ownerType)) {
                return ADMIN;
            } else if (Operator.class.isAssignableFrom(ownerType)) {
                return OPERATOR;
            } else if (Payment.class.isAssignableFrom(ownerType) || DoPaymentDTO.class.isAssignableFrom(ownerType) || Invoice.class.isAssignableFrom(ownerType) || Guarantee.class.isAssignableFrom(ownerType)) {
                return PAYMENT;
            } else if (LoanGroup.class.isAssignableFrom(ownerType)) {
                return LOAN_GROUP;
            } else if (MemberRecord.class.isAssignableFrom(ownerType)) {
                return MEMBER_RECORD;
            }
            return null;
        }

        private final String value;

        private Nature(final String value) {
            this.value = value;
        }

        public Class<? extends CustomField> getEntityType() {
            switch (this) {
                case AD:
                    return AdCustomField.class;
                case ADMIN:
                    return AdminCustomField.class;
                case PAYMENT:
                    return PaymentCustomField.class;
                case LOAN_GROUP:
                    return LoanGroupCustomField.class;
                case MEMBER:
                    return MemberCustomField.class;
                case OPERATOR:
                    return OperatorCustomField.class;
                case MEMBER_RECORD:
                    return MemberRecordCustomField.class;
                default:
                    return null;
            }
        }

        @Override
        public String getValue() {
            return value;
        }

        public Class<? extends CustomFieldValue> getValueType() {
            switch (this) {
                case AD:
                    return AdCustomFieldValue.class;
                case ADMIN:
                    return AdminCustomFieldValue.class;
                case PAYMENT:
                    return PaymentCustomFieldValue.class;
                case LOAN_GROUP:
                    return LoanGroupCustomFieldValue.class;
                case MEMBER:
                    return MemberCustomFieldValue.class;
                case OPERATOR:
                    return OperatorCustomFieldValue.class;
                case MEMBER_RECORD:
                    return MemberRecordCustomFieldValue.class;
                default:
                    return null;
            }
        }
    }

    public static enum Relationships implements Relationship {
        POSSIBLE_VALUES("possibleValues"), PARENT("parent"), CHILDREN("children");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

    }

    public static enum Size implements StringValuedEnum {
        DEFAULT("D"), TINY("T"), SMALL("S"), MEDIUM("M"), LARGE("L"), FULL("F");
        private final String value;

        private Size(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static enum Type implements StringValuedEnum {
        STRING("string", Control.TEXT, Control.TEXTAREA, Control.RICH_EDITOR),
        ENUMERATED("enum", Control.SELECT, Control.RADIO),
        INTEGER("integer", Control.TEXT),
        DECIMAL("decimal", Control.TEXT),
        DATE("date", Control.TEXT),
        BOOLEAN("boolean", Control.CHECKBOX),
        URL("url", Control.TEXT),
        MEMBER("member", Control.MEMBER_AUTOCOMPLETE);

        private final String        value;
        private final List<Control> possibleControls;

        private Type(final String value, final Control... possibleControls) {
            this.value = value;
            this.possibleControls = Collections.unmodifiableList(Arrays.asList(possibleControls));
        }

        public List<Control> getPossibleControls() {
            return possibleControls;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long                    serialVersionUID = 124467598010653164L;

    private String                               name;
    private String                               internalName;
    private String                               description;
    private String                               allSelectedLabel;
    private Type                                 type             = Type.STRING;
    private Control                              control          = Control.TEXT;
    private Size                                 size             = Size.DEFAULT;
    private Validation                           validation       = new Validation();
    private String                               pattern;
    private Integer                              order            = 0;
    private Collection<CustomFieldPossibleValue> possibleValues;
    private CustomField                          parent;
    private Collection<CustomField>              children;

    public String getAllSelectedLabel() {
        return allSelectedLabel;
    }

    public Collection<CustomField> getChildren() {
        return children;
    }

    public Control getControl() {
        return control;
    }

    public String getDescription() {
        return description;
    }

    public String getInternalName() {
        return internalName;
    }

    @Override
    public String getName() {
        return name;
    }

    public Nature getNature() {
        if (this instanceof AdCustomField) {
            return Nature.AD;
        } else if (this instanceof AdminCustomField) {
            return Nature.ADMIN;
        } else if (this instanceof PaymentCustomField) {
            return Nature.PAYMENT;
        } else if (this instanceof LoanGroupCustomField) {
            return Nature.LOAN_GROUP;
        } else if (this instanceof MemberCustomField) {
            return Nature.MEMBER;
        } else if (this instanceof OperatorCustomField) {
            return Nature.OPERATOR;
        } else if (this instanceof MemberRecordCustomField) {
            return Nature.MEMBER_RECORD;
        }
        return null;
    }

    public Integer getOrder() {
        return order;
    }

    public CustomField getParent() {
        return parent;
    }

    public String getPattern() {
        return pattern;
    }

    public Collection<CustomFieldPossibleValue> getPossibleValues() {
        return possibleValues;
    }

    public Collection<CustomFieldPossibleValue> getPossibleValues(final Boolean onlyEnabled) {
        // Filter enabled possible values
        final Collection<CustomFieldPossibleValue> filteredPossibleValues = new ArrayList<CustomFieldPossibleValue>(possibleValues);
        CollectionUtils.filter(filteredPossibleValues, new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                final CustomFieldPossibleValue possibleValue = (CustomFieldPossibleValue) object;
                return onlyEnabled ? possibleValue.isEnabled() : true;
            }
        });
        return possibleValues;
    }

    public Collection<CustomFieldPossibleValue> getPossibleValuesByParent(final CustomFieldPossibleValue parentValue, final Boolean onlyEnabled) {
        // When this field has no parent, return all values
        if (parent == null) {
            return possibleValues;
        }

        // When there's no parent value, or it don't match this field's parent, return an empty list
        if (possibleValues == null || parentValue == null || !parent.equals(parentValue.getField())) {
            return Collections.emptyList();
        }

        // Filter the possible values by parent value
        final Collection<CustomFieldPossibleValue> filteredPossibleValues = new ArrayList<CustomFieldPossibleValue>(possibleValues);
        CollectionUtils.filter(filteredPossibleValues, new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                final CustomFieldPossibleValue possibleValue = (CustomFieldPossibleValue) object;
                return parentValue.equals(possibleValue.getParent()) && (onlyEnabled ? possibleValue.isEnabled() : true);
            }
        });
        return filteredPossibleValues;
    }

    public Size getSize() {
        return size;
    }

    public Type getType() {
        return type;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setAllSelectedLabel(final String allSelectedLabel) {
        this.allSelectedLabel = allSelectedLabel;
    }

    public void setChildren(final Collection<CustomField> children) {
        this.children = children;
    }

    public void setControl(final Control control) {
        this.control = control;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setInternalName(final String internalName) {
        this.internalName = internalName;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOrder(final Integer order) {
        this.order = order;
    }

    public void setParent(final CustomField parent) {
        this.parent = parent;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public void setPossibleValues(final Collection<CustomFieldPossibleValue> possibleValues) {
        this.possibleValues = possibleValues;
    }

    public void setSize(final Size size) {
        this.size = size;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public void setValidation(final Validation validation) {
        this.validation = validation;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

}
