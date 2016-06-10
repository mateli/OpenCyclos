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
package nl.strohalm.cyclos.entities.reports;

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.services.stats.StatisticalTaxesService;

/**
 * Parameters for Statistical queries.
 * @author rinke
 */
public class StatisticalTaxesQuery extends StatisticalQuery {

    private static final long                 serialVersionUID = -7726545570319806657L;

    private boolean                           volume;
    private boolean                           numberOfMembers;
    private boolean                           relativeToGrossProduct;
    private boolean                           medianPerMember;
    private boolean                           maxMember;
    private boolean                           volumeGraph;
    private boolean                           numberOfMembersGraph;
    private boolean                           relativeToGrossProductGraph;
    private boolean                           medianPerMemberGraph;
    private boolean                           maxMemberGraph;
    private StatisticalTaxesService.PaidOrNot paidOrNot;
    private int                               notPaidLimit;
    private Collection<AccountFee>            accountFees;
    private Collection<TransactionFee>        transactionFees;

    public Collection<AccountFee> getAccountFees() {
        return accountFees;
    }

    public int getNotPaidLimit() {
        return notPaidLimit;
    }

    public StatisticalTaxesService.PaidOrNot getPaidOrNot() {
        return paidOrNot;
    }

    public Collection<TransactionFee> getTransactionFees() {
        return transactionFees;
    }

    public boolean isMaxMember() {
        return maxMember;
    }

    public boolean isMaxMemberGraph() {
        return maxMemberGraph;
    }

    public boolean isMedianPerMember() {
        return medianPerMember;
    }

    public boolean isMedianPerMemberGraph() {
        return medianPerMemberGraph;
    }

    public boolean isNumberOfMembers() {
        return numberOfMembers;
    }

    public boolean isNumberOfMembersGraph() {
        return numberOfMembersGraph;
    }

    public boolean isRelativeToGrossProduct() {
        return relativeToGrossProduct;
    }

    public boolean isRelativeToGrossProductGraph() {
        return relativeToGrossProductGraph;
    }

    public boolean isVolume() {
        return volume;
    }

    public boolean isVolumeGraph() {
        return volumeGraph;
    }

    public void setAccountFees(final Collection<AccountFee> accountFees) {
        this.accountFees = accountFees;
    }

    public void setMaxMember(final boolean maxMember) {
        this.maxMember = maxMember;
    }

    public void setMaxMemberGraph(final boolean maxMemberGraph) {
        this.maxMemberGraph = maxMemberGraph;
    }

    public void setMedianPerMember(final boolean medianPerMember) {
        this.medianPerMember = medianPerMember;
    }

    public void setMedianPerMemberGraph(final boolean medianPerMemberGraph) {
        this.medianPerMemberGraph = medianPerMemberGraph;
    }

    public void setNotPaidLimit(final int notPaidLimit) {
        this.notPaidLimit = notPaidLimit;
    }

    public void setNumberOfMembers(final boolean numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public void setNumberOfMembersGraph(final boolean numberOfMembersGraph) {
        this.numberOfMembersGraph = numberOfMembersGraph;
    }

    public void setPaidOrNot(final StatisticalTaxesService.PaidOrNot paidOrNot) {
        this.paidOrNot = paidOrNot;
    }

    public void setRelativeToGrossProduct(final boolean relativeToGrossProduct) {
        this.relativeToGrossProduct = relativeToGrossProduct;
    }

    public void setRelativeToGrossProductGraph(final boolean relativeToGrossProductGraph) {
        this.relativeToGrossProductGraph = relativeToGrossProductGraph;
    }

    public void setTransactionFees(final Collection<TransactionFee> transactionFees) {
        this.transactionFees = transactionFees;
    }

    public void setVolume(final boolean volume) {
        this.volume = volume;
    }

    public void setVolumeGraph(final boolean volumeGraph) {
        this.volumeGraph = volumeGraph;
    }

}
