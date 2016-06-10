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
 * Parameters for Statistical queries.
 * @author rinke, jefferson
 */
public class StatisticalActivityQuery extends StatisticalQuery {

    private static final long serialVersionUID = 8839170613237864262L;
    private boolean           grossProduct;
    private boolean           grossProductGraph;
    private boolean           grossProductTopten;
    private boolean           numberTransactions;
    private boolean           numberTransactionsGraph;
    private boolean           numberTransactionsTopten;
    private boolean           percentageNoTrade;
    private boolean           percentageNoTradeGraph;
    private boolean           loginTimes;
    private boolean           loginTimesGraph;
    private boolean           loginTimesTopten;

    public boolean isGrossProduct() {
        return grossProduct;
    }

    public boolean isGrossProductGraph() {
        return grossProductGraph;
    }

    public boolean isGrossProductTopten() {
        return grossProductTopten;
    }

    public boolean isLoginTimes() {
        return loginTimes;
    }

    public boolean isLoginTimesGraph() {
        return loginTimesGraph;
    }

    public boolean isLoginTimesTopten() {
        return loginTimesTopten;
    }

    public boolean isNumberTransactions() {
        return numberTransactions;
    }

    public boolean isNumberTransactionsGraph() {
        return numberTransactionsGraph;
    }

    public boolean isNumberTransactionsTopten() {
        return numberTransactionsTopten;
    }

    public boolean isPercentageNoTrade() {
        return percentageNoTrade;
    }

    public boolean isPercentageNoTradeGraph() {
        return percentageNoTradeGraph;
    }

    public void setGrossProduct(final boolean grossProduct) {
        this.grossProduct = grossProduct;
    }

    public void setGrossProductGraph(final boolean grossProductGraph) {
        this.grossProductGraph = grossProductGraph;
    }

    public void setGrossProductTopten(final boolean grossProductTopten) {
        this.grossProductTopten = grossProductTopten;
    }

    public void setLoginTimes(final boolean loginTimes) {
        this.loginTimes = loginTimes;
    }

    public void setLoginTimesGraph(final boolean loginTimesGraph) {
        this.loginTimesGraph = loginTimesGraph;
    }

    public void setLoginTimesTopten(final boolean loginTimesTopten) {
        this.loginTimesTopten = loginTimesTopten;
    }

    public void setNumberTransactions(final boolean numberTransactions) {
        this.numberTransactions = numberTransactions;
    }

    public void setNumberTransactionsGraph(final boolean numberTransactionsGraph) {
        this.numberTransactionsGraph = numberTransactionsGraph;
    }

    public void setNumberTransactionsTopten(final boolean numberTransactionsTopten) {
        this.numberTransactionsTopten = numberTransactionsTopten;
    }

    public void setPercentageNoTrade(final boolean percentageNoTrade) {
        this.percentageNoTrade = percentageNoTrade;
    }

    public void setPercentageNoTradeGraph(final boolean percentageNoTradeGraph) {
        this.percentageNoTradeGraph = percentageNoTradeGraph;
    }

}
