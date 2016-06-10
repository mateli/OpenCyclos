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
 * @author rinke
 */
public class StatisticalFinancesQuery extends StatisticalQuery {

    private static final long serialVersionUID = -3306344120754200592L;
    private boolean           overview;
    private boolean           income;
    private boolean           expenditure;
    private boolean           overviewGraph;
    private boolean           incomeGraph;
    private boolean           expenditureGraph;

    public boolean isExpenditure() {
        return expenditure;
    }

    public boolean isExpenditureGraph() {
        // always show this graph when expenditure is selected together with overview for single period
        if (getWhatToShow() == StatisticsWhatToShow.SINGLE_PERIOD && isOverview() && isExpenditure()) {
            return true;
        }
        return expenditureGraph;
    }

    public boolean isIncome() {
        return income;
    }

    public boolean isIncomeGraph() {
        // always show this graph when expenditure is selected together with overview for single period
        if (getWhatToShow() == StatisticsWhatToShow.SINGLE_PERIOD && isOverview() && isIncome()) {
            return true;
        }
        return incomeGraph;
    }

    public boolean isOverview() {
        return overview;
    }

    public boolean isOverviewGraph() {
        return overviewGraph;
    }

    public void setExpenditure(final boolean expenditure) {
        this.expenditure = expenditure;
    }

    public void setExpenditureGraph(final boolean expenditureGraph) {
        this.expenditureGraph = expenditureGraph;
    }

    public void setIncome(final boolean income) {
        this.income = income;
    }

    public void setIncomeGraph(final boolean incomeGraph) {
        this.incomeGraph = incomeGraph;
    }

    public void setOverview(final boolean overview) {
        this.overview = overview;
    }

    public void setOverviewGraph(final boolean overviewGraph) {
        this.overviewGraph = overviewGraph;
    }

}
