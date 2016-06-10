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
package nl.strohalm.cyclos.entities.members;

import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomField;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;

/**
 * A member's operator
 * @author luis
 */
public class Operator extends Element implements CustomFieldsContainer<OperatorCustomField, OperatorCustomFieldValue> {

    public static enum Relationships implements Relationship {
        MEMBER("member"), CUSTOM_VALUES("customValues");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long                    serialVersionUID = 4105825541748672232L;
    private Member                               member;
    private Collection<OperatorCustomFieldValue> customValues;

    @Override
    public AccountOwner getAccountOwner() {
        return member;
    }

    public Class<OperatorCustomField> getCustomFieldClass() {
        return OperatorCustomField.class;
    }

    public Class<OperatorCustomFieldValue> getCustomFieldValueClass() {
        return OperatorCustomFieldValue.class;
    }

    public Collection<OperatorCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Element.Nature getNature() {
        return Element.Nature.OPERATOR;
    }

    public OperatorGroup getOperatorGroup() {
        return (OperatorGroup) super.getGroup();
    }

    public OperatorUser getOperatorUser() {
        return (OperatorUser) super.getUser();
    }

    public void setCustomValues(final Collection<OperatorCustomFieldValue> values) {
        customValues = values;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

}
