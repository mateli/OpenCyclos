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
package nl.strohalm.cyclos.entities.groups;

import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;

/**
 * A group of members that are also brokers of other members
 * @author luis
 */
public class BrokerGroup extends MemberGroup {

    public static enum Relationships implements Relationship {
        BROKER_DOCUMENTS("brokerDocuments"), TRANSFER_TYPES_AS_MEMBER("transferTypesAsMember"), BROKER_CONVERSION_SIMULATION_TTS("brokerConversionSimulationTTs"), BROKER_CAN_VIEW_INFORMATION_OF("brokerCanViewInformationOf"), BROKER_MEMBER_RECORD_TYPES("brokerMemberRecordTypes"), BROKER_CREATE_MEMBER_RECORD_TYPES("brokerCreateMemberRecordTypes"), BROKER_MODIFY_MEMBER_RECORD_TYPES("brokerModifyMemberRecordTypes"), BROKER_DELETE_MEMBER_RECORD_TYPES("brokerDeleteMemberRecordTypes"), POSSIBLE_INITIAL_GROUPS("possibleInitialGroups");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long            serialVersionUID = -7238110440943417007L;

    private Collection<TransferType>     transferTypesAsMember;
    private Collection<TransferType>     brokerConversionSimulationTTs;
    private Collection<Document>         brokerDocuments;
    private Collection<AccountType>      brokerCanViewInformationOf;
    private Collection<MemberRecordType> brokerMemberRecordTypes;
    private Collection<MemberRecordType> brokerCreateMemberRecordTypes;
    private Collection<MemberRecordType> brokerModifyMemberRecordTypes;
    private Collection<MemberRecordType> brokerDeleteMemberRecordTypes;
    private Collection<MemberGroup>      possibleInitialGroups;

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

    @Override
    public Nature getNature() {
        return Nature.BROKER;
    }

    public Collection<MemberGroup> getPossibleInitialGroups() {
        return possibleInitialGroups;
    }

    public Collection<TransferType> getTransferTypesAsMember() {
        return transferTypesAsMember;
    }

    @Override
    public boolean isBroker() {
        return true;
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

    public void setPossibleInitialGroups(final Collection<MemberGroup> possibleInitialGroups) {
        this.possibleInitialGroups = possibleInitialGroups;
    }

    public void setTransferTypesAsMember(final Collection<TransferType> transferTypesAsMember) {
        this.transferTypesAsMember = transferTypesAsMember;
    }

}
