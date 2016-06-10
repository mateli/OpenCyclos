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
package nl.strohalm.cyclos.entities.accounts.fees.transaction;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Nature;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for transaction fees
 * @author luis
 */
public class TransactionFeeQuery extends QueryParameters {

    private static final long               serialVersionUID = 4155950478435678837L;
    private String                          description;
    private String                          name;
    private boolean                         returnDisabled;
    private AccountType.Nature              generatedTransferTypeFromNature;
    private TransferType                    transferType;
    private Class<? extends TransactionFee> entityType;
    private Nature                          nature;
    private BrokerGroup                     brokerGroup;
    private MemberGroup                     memberGroup;

    public BrokerGroup getBrokerGroup() {
        return brokerGroup;
    }

    public String getDescription() {
        return description;
    }

    public Class<? extends TransactionFee> getEntityType() {
        return entityType;
    }

    public AccountType.Nature getGeneratedTransferTypeFromNature() {
        return generatedTransferTypeFromNature;
    }

    public MemberGroup getMemberGroup() {
        return memberGroup;
    }

    public String getName() {
        return name;
    }

    public Nature getNature() {
        return nature;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isReturnDisabled() {
        return returnDisabled;
    }

    public void setBrokerGroup(final BrokerGroup brokerGroup) {
        this.brokerGroup = brokerGroup;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEntityType(final Class<? extends TransactionFee> entityType) {
        this.entityType = entityType;
    }

    public void setGeneratedTransferTypeFromNature(final AccountType.Nature generatedTransferTypeFromNature) {
        this.generatedTransferTypeFromNature = generatedTransferTypeFromNature;
    }

    public void setMemberGroup(final MemberGroup memberGroup) {
        this.memberGroup = memberGroup;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNature(final Nature nature) {
        this.nature = nature;
    }

    public void setReturnDisabled(final boolean returnDisabled) {
        this.returnDisabled = returnDisabled;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

}
