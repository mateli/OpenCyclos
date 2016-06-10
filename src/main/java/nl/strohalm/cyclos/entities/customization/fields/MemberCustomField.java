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

import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * A custom field for members
 * @author luis
 */
public class MemberCustomField extends CustomField {

    /**
     * Restrict the field usage for specific user types
     * @author luis
     */
    public static enum Access implements StringValuedEnum {
        NONE("N"), WEB_SERVICE("W"), ADMIN("A"), BROKER("B"), REGISTRATION("R"), MEMBER_NOT_REGISTRATION("T"), MEMBER("M"), OTHER("O"), MEMBER_NOT_OPERATOR("P");
        private final String value;

        private Access(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        /**
         * Return whether this access matches the specified group, given that the member is the broker
         */
        public boolean granted(final Group group, final boolean byOwner, final boolean byBroker, final boolean atRegistration, final boolean byWebService) {
            if (byWebService) {
                return this != NONE;
            }
            boolean isGranted = granted(group);
            if (isGranted && group instanceof MemberGroup) {
                switch (this) {
                    case MEMBER:
                        isGranted = byOwner || byBroker;
                        break;
                    case MEMBER_NOT_REGISTRATION:
                        isGranted = !atRegistration && (byOwner || byBroker);
                        break;
                    case REGISTRATION:
                        isGranted = atRegistration || byBroker;
                        break;
                    case BROKER:
                        isGranted = byBroker;
                        break;
                }
            }
            return isGranted;
        }

        public boolean grantedToBroker() {
            return this != NONE && this != ADMIN && this != WEB_SERVICE;
        }

        /**
         * Return if this access matches the specified group
         */
        private boolean granted(final Group group) {
            switch (this) {
                case NONE:
                    return false;
                case BROKER:
                    return group instanceof BrokerGroup || group instanceof AdminGroup;
                case ADMIN:
                    return group instanceof AdminGroup;
                case MEMBER:
                case MEMBER_NOT_REGISTRATION:
                case REGISTRATION:
                case MEMBER_NOT_OPERATOR:
                    return !(group instanceof OperatorGroup);
                case OTHER:
                    return true;
            }
            return false;
        }
    }

    public static enum Indexing implements StringValuedEnum {
        NONE("N"), MEMBERS_ONLY("M"), MEMBERS_AND_ADS("A");
        private final String value;

        private Indexing(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        GROUPS("groups");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long       serialVersionUID   = 8982250513905556430L;
    private Access                  adSearchAccess     = Access.NONE;
    private Access                  loanSearchAccess   = Access.NONE;
    private boolean                 memberCanHide      = true;
    private Access                  memberSearchAccess = Access.NONE;
    private Access                  visibilityAccess   = Access.OTHER;
    private Access                  updateAccess       = Access.MEMBER;
    private boolean                 showInPrint        = true;
    private Indexing                indexing           = Indexing.MEMBERS_AND_ADS;
    private Collection<MemberGroup> groups;

    public Access getAdSearchAccess() {
        return adSearchAccess;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Indexing getIndexing() {
        return indexing;
    }

    public Access getLoanSearchAccess() {
        return loanSearchAccess;
    }

    public Access getMemberSearchAccess() {
        return memberSearchAccess;
    }

    public Access getUpdateAccess() {
        return updateAccess;
    }

    public Access getVisibilityAccess() {
        return visibilityAccess;
    }

    public boolean isMemberCanHide() {
        return memberCanHide;
    }

    public boolean isShowInPrint() {
        return showInPrint;
    }

    public void setAdSearchAccess(final Access adSearchVisibility) {
        adSearchAccess = adSearchVisibility;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setIndexing(final Indexing indexing) {
        this.indexing = indexing;
    }

    public void setLoanSearchAccess(final Access loanSearchVisibility) {
        loanSearchAccess = loanSearchVisibility;
    }

    public void setMemberCanHide(final boolean memberCanHide) {
        this.memberCanHide = memberCanHide;
    }

    public void setMemberSearchAccess(final Access memberSearchVisibility) {
        memberSearchAccess = memberSearchVisibility;
    }

    public void setShowInPrint(final boolean showInPrint) {
        this.showInPrint = showInPrint;
    }

    public void setUpdateAccess(final Access editableAccess) {
        updateAccess = editableAccess;
    }

    public void setVisibilityAccess(final Access registrationVisibility) {
        visibilityAccess = registrationVisibility;
    }
}
