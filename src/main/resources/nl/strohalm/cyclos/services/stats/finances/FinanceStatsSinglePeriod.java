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
package nl.strohalm.cyclos.services.stats.finances;

import java.util.Collection;

import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.utils.NamedPeriod;
import nl.strohalm.cyclos.utils.Period;

/**
 * Takes care of the calculations for finance statistics for a single period.
 * 
 * @author Rinke
 * 
 */
public class FinanceStatsSinglePeriod extends FinanceStats {

    private enum FlowType {
        INCOME, EXPENDITURE;
    }

    private Period            period;
    private SystemAccountType systemAccountFilter;

    /**
     * constructor receiving the transfer dao
     */
    public FinanceStatsSinglePeriod(final TransferDAO transferDao) {
        super(transferDao);
    }

    /**
     * gives the resulting table data for expenditure single period. It first checks if the period, paymentFilters and systemAccountType are the same,
     * and if so, it will return a possibly already available result, rather than calculating it anew.
     * 
     * <code>TransferType</code>s
     * @return a Number[][] containing the table data for overview single period.
     */
    public Number[][] getTableCellsExpenditureSinglePeriod(final NamedPeriod aPeriod, final Collection<PaymentFilter> aPaymentFilters, final SystemAccountType aSystemAccountFilter) {
        if (!hasEqualParameters(aPeriod, aPaymentFilters, aSystemAccountFilter) || tableCells == null) {
            tableCells = getTableCellsOverviewSinglePeriod(aPeriod, aPaymentFilters, aSystemAccountFilter);
        }
        return getTableCellsInOrOut(FlowType.EXPENDITURE);
    }

    /**
     * gives the resulting table data for income single period. It first checks if the period, paymentFilters and systemAccountType are the same, and
     * if so, it will return a possibly already available result, rather than calculating it anew.
     * 
     * @param aPeriod
     * @param aPaymentFilters a collection of paymentFilters. If only one paymentFilter is selected, this filter will be split up into its containing
     * <code>TransferType</code>s
     * @param aSystemAccountFilter
     * @return a Number[][] containing the table data for overview single period.
     */
    public Number[][] getTableCellsIncomeSinglePeriod(final NamedPeriod aPeriod, final Collection<PaymentFilter> aPaymentFilters, final SystemAccountType aSystemAccountFilter) {
        if (!hasEqualParameters(aPeriod, aPaymentFilters, aSystemAccountFilter) || tableCells == null) {
            tableCells = getTableCellsOverviewSinglePeriod(aPeriod, aPaymentFilters, aSystemAccountFilter);
        }
        return getTableCellsInOrOut(FlowType.INCOME);
    }

    /**
     * gives the resulting table data for overview single period. It first checks if the period, paymentFilters and systemAccountType are the same,
     * and if so, it will return a possibly already available result, rather than calculating it anew.
     * 
     * @param aPeriod
     * @param aPaymentFilters a collection of paymentFilters. If only one paymentFilter is selected, this filter will be split up into its containing
     * <code>TransferType</code>s
     * @param aSystemAccountFilter
     * @return a Number[][] containing the table data for overview single period.
     */
    public Number[][] getTableCellsOverviewSinglePeriod(final Period aPeriod, final Collection<PaymentFilter> aPaymentFilters, final SystemAccountType aSystemAccountFilter) {
        Number[][] lTableCells = tableCells;
        if (!hasEqualParameters(aPeriod, aPaymentFilters, aSystemAccountFilter) || tableCells == null) {
            if (aPaymentFilters.size() == 1) {
                for (final PaymentFilter paymentFilter : aPaymentFilters) {
                    lTableCells = new Number[paymentFilter.getTransferTypes().size() + 2][3];
                    int i = 0;
                    for (final TransferType transferType : paymentFilter.getTransferTypes()) {
                        lTableCells[i][0] = getIncome(aPeriod, transferType, aSystemAccountFilter);
                        lTableCells[i][1] = getExpenditure(aPeriod, transferType, aSystemAccountFilter);
                        i++;
                    }
                }
            } else {
                lTableCells = new Number[aPaymentFilters.size() + 2][3];
                // Assign table values
                int i = 0;
                for (final PaymentFilter paymentFilter : aPaymentFilters) {
                    lTableCells[i][0] = getIncome(aPeriod, paymentFilter, aSystemAccountFilter);
                    lTableCells[i][1] = getExpenditure(aPeriod, paymentFilter, aSystemAccountFilter);
                    i++;
                }
            }
            lTableCells[lTableCells.length - 2][0] = getIncomeRest(aPeriod, aPaymentFilters, aSystemAccountFilter);
            lTableCells[lTableCells.length - 2][1] = getExpenditureRest(aPeriod, aPaymentFilters, aSystemAccountFilter);
            assignRowDifferences(lTableCells);
            assignColumnSums(lTableCells);
            tableCells = lTableCells;
            // reset fields
            period = aPeriod;
            paymentFilters = aPaymentFilters;
            systemAccountFilter = aSystemAccountFilter;
            paymentFilterNames = null;
        }
        return lTableCells;
    }

    /**
     * calculates the sums of the columns, and saves these to the last row of the input parameter
     * 
     * @param tableCells
     */
    private void assignColumnSums(final Number[][] tableCells) {
        final int rows = tableCells.length;
        final int cols = tableCells[0].length;
        for (int j = 0; j < cols; j++) {
            double sum = 0;
            for (int i = 0; i < rows - 1; i++) {
                sum += tableCells[i][j].doubleValue();
            }
            tableCells[rows - 1][j] = new StatisticalNumber(sum, (byte) 2);
        }
    }

    /**
     * calculates the difference between column 0 and column 1, and saves these to the last column of the input param
     * 
     * @param tableCells
     */
    private void assignRowDifferences(final Number[][] tableCells) {
        final int rows = tableCells.length;
        final int cols = tableCells[0].length;
        for (int i = 0; i < rows - 1; i++) {
            double sum = 0;
            for (int j = 0; j < cols - 1; j++) {
                if (j == 0) {
                    sum = tableCells[i][j].doubleValue();
                } else {
                    sum -= tableCells[i][j].doubleValue();
                }
            }
            tableCells[i][cols - 1] = new StatisticalNumber(sum, (byte) 2);
        }
    }

    /**
     * retrieves the income or expenditure from the 2 dimensional array containing both of them.
     * 
     * @param flowType INCOME or EXPENDITURE
     * @return the <code>Number</code> two dimensional array
     */
    private Number[][] getTableCellsInOrOut(final FlowType flowType) {
        int col = 1;
        if (flowType == FlowType.INCOME) {
            col = 0;
        }
        final Number[][] lTableCells = new Number[tableCells.length - 1][1];
        for (int i = 0; i < lTableCells.length; i++) {
            lTableCells[i][0] = tableCells[i][col];
        }
        return lTableCells;
    }

    /**
     * checks if the parameters are changed, and if a local cached result is available. If parameters haven't changed and a cached value of the
     * results is available, the calling method should read results from local field values rather than reading them anew from the database.
     * Overloaded versions may exists for different sets of params.<br>
     * The method does <b>NOT</b> update the values it checks, that is the responsibility of the calling method, after having redone all calculations.
     * 
     * @param aPeriod
     * @param aPaymentFilters
     * @param aSystemAccountFilter
     * @return <code>false</code> if the input parameters are different from the corresponding class instance variables, or if there is no cached
     * result available.
     */
    private boolean hasEqualParameters(final Period aPeriod, final Collection<PaymentFilter> aPaymentFilters, final SystemAccountType aSystemAccountFilter) {
        boolean nulls = false;
        if (period == null || paymentFilters == null || systemAccountFilter == null) {
            nulls = true;
        }
        if (!nulls && period.equals(aPeriod) && systemAccountFilter.equals(aSystemAccountFilter) && paymentFilters.equals(aPaymentFilters)) {
            return true;
        }
        return false;
    }

}
