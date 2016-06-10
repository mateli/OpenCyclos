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
package nl.strohalm.cyclos.entities.accounts.external.filemapping;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * Maps a specific field inside a transactions file
 * @author luis
 */
public class FieldMapping extends Entity {

    /**
     * The field inside Cyclos that this field mapping refers
     * @author luis
     */
    public static enum Field implements StringValuedEnum {
        /** The field is ignored */
        IGNORED("ig", null),

        /** The field is the member ID */
        MEMBER_ID("id", "memberId"),

        /** The field is the member username */
        MEMBER_USERNAME("un", "memberUsername"),

        /** The field is the value of a member custom field */
        MEMBER_CUSTOM_FIELD("cf", "memberFieldValues"),

        /** The field is the payment type code */
        TYPE("tp", "typeCode"),

        /** The field is the payment description */
        DESCRIPTION("dc", "description"),

        /** The field is the payment date */
        DATE("dt", "date"),

        /** The field is the payment amount */
        AMOUNT("am", "amount"),

        /** The field is the a flag to set the amount to negative */
        NEGATE_AMOUNT("na", "negateAmount");

        private final String value;
        private final String dtoProperty;

        private Field(final String value, final String dtoProperty) {
            this.value = value;
            this.dtoProperty = dtoProperty;
        }

        public String getDtoProperty() {
            return dtoProperty;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        FILE_MAPPING("fileMapping"), MEMBER_FIELD("memberField");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -8059629760396294846L;
    private FileMapping       fileMapping;
    private int               order;
    private String            name;
    private Field             field;
    private MemberCustomField memberField;

    public Field getField() {
        return field;
    }

    public FileMapping getFileMapping() {
        return fileMapping;
    }

    public MemberCustomField getMemberField() {
        return memberField;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public void setField(final Field field) {
        this.field = field;
    }

    public void setFileMapping(final FileMapping fileMapping) {
        this.fileMapping = fileMapping;
    }

    public void setMemberField(final MemberCustomField memberField) {
        this.memberField = memberField;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return getId() + " - " + getName();
    }

}
