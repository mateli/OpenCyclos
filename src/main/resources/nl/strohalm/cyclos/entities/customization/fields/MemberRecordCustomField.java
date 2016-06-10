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
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * A custom field for a member record
 * @author Jefferson Magno
 */
public class MemberRecordCustomField extends CustomField {

    public static enum Access implements StringValuedEnum {
        NONE("N"), READ_ONLY("R"), EDITABLE("E");
        private final String value;

        private Access(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public static enum Relationships implements Relationship {
        MEMBER_RECORD_TYPE("memberRecordType");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -3513364926074077653L;

    private MemberRecordType  memberRecordType;
    private boolean           showInSearch;
    private boolean           showInList;
    private Access            brokerAccess;

    public Access getBrokerAccess() {
        return brokerAccess;
    }

    public MemberRecordType getMemberRecordType() {
        return memberRecordType;
    }

    public boolean isShowInList() {
        return showInList;
    }

    public boolean isShowInSearch() {
        return showInSearch;
    }

    public void setBrokerAccess(final Access brokerAccess) {
        this.brokerAccess = brokerAccess;
    }

    public void setMemberRecordType(final MemberRecordType memberRecordType) {
        this.memberRecordType = memberRecordType;
    }

    public void setShowInList(final boolean showInList) {
        this.showInList = showInList;
    }

    public void setShowInSearch(final boolean showInSearch) {
        this.showInSearch = showInSearch;
    }

}
