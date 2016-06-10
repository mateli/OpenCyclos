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
package nl.strohalm.cyclos.entities.members.imports;

import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;

/**
 * Contains aggregations for member record values on temporary imported members
 * 
 * @author luis
 */
public class ImportedMemberRecord extends Entity implements CustomFieldsContainer<MemberRecordCustomField, ImportedMemberRecordCustomFieldValue> {

    public static enum Relationships implements Relationship {
        MEMBER("member"), RECORD_TYPE("recordType"), CUSTOM_VALUES("customValues");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long                                serialVersionUID = -2185121332687018594L;
    private ImportedMember                                   member;
    private MemberRecordType                                 type;
    private Collection<ImportedMemberRecordCustomFieldValue> customValues;

    public Class<MemberRecordCustomField> getCustomFieldClass() {
        return MemberRecordCustomField.class;
    }

    public Class<ImportedMemberRecordCustomFieldValue> getCustomFieldValueClass() {
        return ImportedMemberRecordCustomFieldValue.class;
    }

    public Collection<ImportedMemberRecordCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public ImportedMember getMember() {
        return member;
    }

    public MemberRecordType getType() {
        return type;
    }

    public void setCustomValues(final Collection<ImportedMemberRecordCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setMember(final ImportedMember member) {
        this.member = member;
    }

    public void setType(final MemberRecordType recordType) {
        type = recordType;
    }

    @Override
    public String toString() {
        return getId() + " - " + type + " - " + member;
    }
}
