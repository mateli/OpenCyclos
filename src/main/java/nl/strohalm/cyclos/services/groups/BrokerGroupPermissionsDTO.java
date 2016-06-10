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

import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;

/**
 * Class used to store a member group's permissions
 * @author luis
 */
public class BrokerGroupPermissionsDTO extends MemberGroupPermissionsDTO<BrokerGroup> {

    private static final long            serialVersionUID = 8264674596578541889L;
    private Collection<Document>         brokerDocuments;
    private Collection<TransferType>     asMemberToMemberTTs;
    private Collection<TransferType>     asMemberToSelfTTs;
    private Collection<TransferType>     asMemberToSystemTTs;
    private Collection<TransferType>     brokerConversionSimulationTTs;
    private Collection<AccountType>      brokerCanViewInformationOf;
    private Collection<MemberRecordType> brokerMemberRecordTypes;
    private Collection<MemberRecordType> brokerCreateMemberRecordTypes;
    private Collection<MemberRecordType> brokerModifyMemberRecordTypes;
    private Collection<MemberRecordType> brokerDeleteMemberRecordTypes;

    public Collection<TransferType> getAsMemberToMemberTTs() {
        return asMemberToMemberTTs;
    }

    public Collection<TransferType> getAsMemberToSelfTTs() {
        return asMemberToSelfTTs;
    }

    public Collection<TransferType> getAsMemberToSystemTTs() {
        return asMemberToSystemTTs;
    }

    public Collection<AccountType> getBrokerCanViewInformationOf() {
        return brokerCanViewInformationOf;
    }

    public Collection<TransferType> getBrokerConversionSimulationTTs() {
        return brokerConversionSimulationTTs;
    }

    public Collection<MemberRecordType> getBrokerCreateMemberRecordTypes() {
        return brokerCreateMemberRecordTypes;
    }

    public Collection<MemberRecordType> getBrokerDeleteMemberRecordTypes() {
        return brokerDeleteMemberRecordTypes;
    }

    public Collection<Document> getBrokerDocuments() {
        return brokerDocuments;
    }

    public Collection<MemberRecordType> getBrokerMemberRecordTypes() {
        return brokerMemberRecordTypes;
    }

    public Collection<MemberRecordType> getBrokerModifyMemberRecordTypes() {
        return brokerModifyMemberRecordTypes;
    }

    public void setAsMemberToMemberTTs(final Collection<TransferType> asMemberToMemberTTs) {
        this.asMemberToMemberTTs = asMemberToMemberTTs;
    }

    public void setAsMemberToSelfTTs(final Collection<TransferType> asMemberToSelfTTs) {
        this.asMemberToSelfTTs = asMemberToSelfTTs;
    }

    public void setAsMemberToSystemTTs(final Collection<TransferType> asMemberToSystemTTs) {
        this.asMemberToSystemTTs = asMemberToSystemTTs;
    }

    public void setBrokerCanViewInformationOf(final Collection<AccountType> brokerCanViewInformationOf) {
        this.brokerCanViewInformationOf = brokerCanViewInformationOf;
    }

    public void setBrokerConversionSimulationTTs(final Collection<TransferType> brokerConversionSimulationTTs) {
        this.brokerConversionSimulationTTs = brokerConversionSimulationTTs;
    }

    public void setBrokerCreateMemberRecordTypes(final Collection<MemberRecordType> brokerCreateMemberRecordTypes) {
        this.brokerCreateMemberRecordTypes = brokerCreateMemberRecordTypes;
    }

    public void setBrokerDeleteMemberRecordTypes(final Collection<MemberRecordType> brokerDeleteMemberRecordTypes) {
        this.brokerDeleteMemberRecordTypes = brokerDeleteMemberRecordTypes;
    }

    public void setBrokerDocuments(final Collection<Document> brokerDocuments) {
        this.brokerDocuments = brokerDocuments;
    }

    public void setBrokerMemberRecordTypes(final Collection<MemberRecordType> brokerMemberRecordTypes) {
        this.brokerMemberRecordTypes = brokerMemberRecordTypes;
    }

    public void setBrokerModifyMemberRecordTypes(final Collection<MemberRecordType> brokerModifyMemberRecordTypes) {
        this.brokerModifyMemberRecordTypes = brokerModifyMemberRecordTypes;
    }

    @Override
    public void updateOperations() {
        super.updateOperations();

        // Check view documents as broker
        update(BrokerPermission.DOCUMENTS_VIEW, getBrokerDocuments());

        // Check make payment as member
        update(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER, getAsMemberToMemberTTs());

        // Check make payment as member to self
        update(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF, getAsMemberToSelfTTs());

        // Check make payment as member to system
        update(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM, getAsMemberToSystemTTs());


        update(BrokerPermission.REPORTS_SHOW_ACCOUNT_INFORMATION, getBrokerCanViewInformationOf());

        // Check view member records
        update(BrokerPermission.MEMBER_RECORDS_VIEW, getBrokerMemberRecordTypes());

        // Check create member records
        update(BrokerPermission.MEMBER_RECORDS_CREATE, getBrokerCreateMemberRecordTypes());

        // Check modify member records
        update(BrokerPermission.MEMBER_RECORDS_MODIFY, getBrokerModifyMemberRecordTypes());

        // Check delete member records
        update(BrokerPermission.MEMBER_RECORDS_DELETE, getBrokerDeleteMemberRecordTypes());
    }

    @Override
    protected void updateCollections(final BrokerGroup brokerGroup) {
        super.updateCollections(brokerGroup);

        brokerGroup.setBrokerDocuments(getBrokerDocuments());

        final Collection<TransferType> asMemberToMemberTTs = getAsMemberToMemberTTs();
        final Collection<TransferType> asMemberToSelfTTs = getAsMemberToSelfTTs();
        final Collection<TransferType> asMemberToSystemTTs = getAsMemberToSystemTTs();
        final Collection<TransferType> transferTypesAsMember = new ArrayList<TransferType>();
        if (asMemberToMemberTTs != null) {
            transferTypesAsMember.addAll(asMemberToMemberTTs);
        }
        if (asMemberToSelfTTs != null) {
            transferTypesAsMember.addAll(asMemberToSelfTTs);
        }
        if (asMemberToSystemTTs != null) {
            transferTypesAsMember.addAll(asMemberToSystemTTs);
        }
        brokerGroup.setTransferTypesAsMember(transferTypesAsMember);

        final Collection<TransferType> brokerConversionSimulationTTs = getBrokerConversionSimulationTTs();
        brokerGroup.setBrokerConversionSimulationTTs(brokerConversionSimulationTTs);

        brokerGroup.setBrokerCanViewInformationOf(getBrokerCanViewInformationOf());
        brokerGroup.setBrokerMemberRecordTypes(getBrokerMemberRecordTypes());
        brokerGroup.setBrokerCreateMemberRecordTypes(getBrokerCreateMemberRecordTypes());
        brokerGroup.setBrokerModifyMemberRecordTypes(getBrokerModifyMemberRecordTypes());
        brokerGroup.setBrokerDeleteMemberRecordTypes(getBrokerDeleteMemberRecordTypes());
    }
}
