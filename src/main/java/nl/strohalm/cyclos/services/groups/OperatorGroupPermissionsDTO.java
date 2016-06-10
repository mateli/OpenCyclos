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
package nl.strohalm.cyclos.services.groups;

import java.util.ArrayList;
import java.util.Collection;

import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;

/**
 * Class used to store a member an operator group's permissions
 * @author jefferson
 */
public class OperatorGroupPermissionsDTO extends GroupPermissionsDTO<OperatorGroup> {

    private static final long        serialVersionUID = -823068618685104044L;

    private Collection<AccountType>  canViewInformationOf;
    private Collection<TransferType> memberToMemberTTs;
    private Collection<TransferType> memberToSystemTTs;
    private Collection<TransferType> selfPaymentTTs;
    private Collection<TransferType> externalPaymentTTs;

    public Collection<AccountType> getCanViewInformationOf() {
        return canViewInformationOf;
    }

    public Collection<TransferType> getExternalPaymentTTs() {
        return externalPaymentTTs;
    }

    public Collection<TransferType> getMemberToMemberTTs() {
        return memberToMemberTTs;
    }

    public Collection<TransferType> getMemberToSystemTTs() {
        return memberToSystemTTs;
    }

    public Collection<TransferType> getSelfPaymentTTs() {
        return selfPaymentTTs;
    }

    public void setCanViewInformationOf(final Collection<AccountType> canViewInformationOf) {
        this.canViewInformationOf = canViewInformationOf;
    }

    public void setExternalPaymentTTs(final Collection<TransferType> externalPaymentTTs) {
        this.externalPaymentTTs = externalPaymentTTs;
    }

    public void setMemberToMemberTTs(final Collection<TransferType> memberToMemberTTs) {
        this.memberToMemberTTs = memberToMemberTTs;
    }

    public void setMemberToSystemTTs(final Collection<TransferType> memberToSystemTTs) {
        this.memberToSystemTTs = memberToSystemTTs;
    }

    public void setSelfPaymentTTs(final Collection<TransferType> selfPaymentTTs) {
        this.selfPaymentTTs = selfPaymentTTs;
    }

    @Override
    public void updateOperations() {

        update(OperatorPermission.GUARANTEES_ISSUE_GUARANTEES, getGuaranteeTypes());

        // Check account information
        update(OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION, getCanViewInformationOf());
    }

    @Override
    protected void updateCollections(final OperatorGroup operatorGroup) {
        super.updateCollections(operatorGroup);

        operatorGroup.setCanViewInformationOf(getCanViewInformationOf());

        final Collection<TransferType> memberToMemberTTs = getMemberToMemberTTs();
        final Collection<TransferType> memberToSystemTTs = getMemberToSystemTTs();
        final Collection<TransferType> selfPaymentTTs = getSelfPaymentTTs();
        final Collection<TransferType> externalPaymentTTs = getExternalPaymentTTs();

        final Collection<TransferType> transferTypes = new ArrayList<TransferType>();
        if (memberToMemberTTs != null) {
            transferTypes.addAll(memberToMemberTTs);
        }
        if (memberToSystemTTs != null) {
            transferTypes.addAll(memberToSystemTTs);
        }
        if (selfPaymentTTs != null) {
            transferTypes.addAll(selfPaymentTTs);
        }
        if (externalPaymentTTs != null) {
            transferTypes.addAll(externalPaymentTTs);
        }
        operatorGroup.setTransferTypes(transferTypes);
    }

}
