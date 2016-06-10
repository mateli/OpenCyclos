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

/**
 * parameters for Statistical queries.
 * 
 * @author rinke
 * 
 */
public class StatisticalKeyDevelopmentsQuery extends StatisticalQuery {

    private static final long serialVersionUID = 3346504531080331769L;
    private boolean           grossProduct;
    private boolean           numberOfAds;
    private boolean           numberOfMembers;
    private boolean           numberOfTransactions;
    private boolean           transactionAmount;
    private boolean           grossProductGraph;
    private boolean           numberOfAdsGraph;
    private boolean           numberOfMembersGraph;
    private boolean           numberOfTransactionsGraph;
    private boolean           transactionAmountGraph;
    private boolean           thruTimeGraph;

    public boolean isGrossProduct() {
        return grossProduct;
    }

    public boolean isGrossProductGraph() {
        return grossProductGraph;
    }

    public boolean isNumberOfAds() {
        return numberOfAds;
    }

    public boolean isNumberOfAdsGraph() {
        return numberOfAdsGraph;
    }

    public boolean isNumberOfMembers() {
        return numberOfMembers;
    }

    public boolean isNumberOfMembersGraph() {
        return numberOfMembersGraph;
    }

    public boolean isNumberOfTransactions() {
        return numberOfTransactions;
    }

    public boolean isNumberOfTransactionsGraph() {
        return numberOfTransactionsGraph;
    }

    public boolean isThruTimeGraph() {
        return thruTimeGraph;
    }

    public boolean isTransactionAmount() {
        return transactionAmount;
    }

    public boolean isTransactionAmountGraph() {
        return transactionAmountGraph;
    }

    public void setGrossProduct(final boolean grossProduct) {
        this.grossProduct = grossProduct;
    }

    public void setGrossProductGraph(final boolean grossProductGraph) {
        this.grossProductGraph = grossProductGraph;
    }

    public void setNumberOfAds(final boolean numberOfAds) {
        this.numberOfAds = numberOfAds;
    }

    public void setNumberOfAdsGraph(final boolean numberOfAdsGraph) {
        this.numberOfAdsGraph = numberOfAdsGraph;
    }

    public void setNumberOfMembers(final boolean numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public void setNumberOfMembersGraph(final boolean numberOfMembersGraph) {
        this.numberOfMembersGraph = numberOfMembersGraph;
    }

    public void setNumberOfTransactions(final boolean numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public void setNumberOfTransactionsGraph(final boolean numberOfTransactionsGraph) {
        this.numberOfTransactionsGraph = numberOfTransactionsGraph;
    }

    public void setThruTimeGraph(final boolean thruTimeGraph) {
        this.thruTimeGraph = thruTimeGraph;
    }

    public void setTransactionAmount(final boolean transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setTransactionAmountGraph(final boolean transactionAmountGraph) {
        this.transactionAmountGraph = transactionAmountGraph;
    }

}
