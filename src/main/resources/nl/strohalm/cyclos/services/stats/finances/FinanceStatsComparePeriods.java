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
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.utils.NamedPeriod;

/**
 * Takes care of the calculations for finance statistics for compare periods.
 * 
 * @author Rinke
 * 
 */
public class FinanceStatsComparePeriods extends FinanceStats {

    /**
     * constructor receiving the transfer dao
     */
    public FinanceStatsComparePeriods(final TransferDAO transferDao) {
        super(transferDao);
    }

    /**
     * returns the paymentFilter names in a String array. If there is only one paymentFilter in the paymentFilters, then an array of containing
     * transferType names is returned.
     * 
     * @return a array of paymentFilter names.
     */
    @Override
    public String[] getPaymentFilterNames(final Collection<PaymentFilter> aPaymentFilters) {
        final String[] result = super.getPaymentFilterNames(aPaymentFilters);
        paymentFilters = aPaymentFilters;
        return result;
    }

    public Number[][] getTableCellsExpenditureComparePeriods(final NamedPeriod periodMain, final NamedPeriod periodAlt, final Collection<PaymentFilter> aPaymentFilters, final SystemAccountType aSystemAccountFilter) {
        final Number[][] tableCells = new Number[aPaymentFilters.size()][3];
        int i = 0;
        for (final PaymentFilter paymentFilter : aPaymentFilters) {
            tableCells[i][0] = getExpenditure(periodMain, paymentFilter, aSystemAccountFilter);
            tableCells[i][1] = getExpenditure(periodAlt, paymentFilter, aSystemAccountFilter);
            tableCells[i][2] = StatisticalNumber.createPercentage(tableCells[i][1], tableCells[i][0]);
            i++;
        }
        return tableCells;
    }

    public Number[][] getTableCellsIncomeComparePeriods(final NamedPeriod periodMain, final NamedPeriod periodAlt, final Collection<PaymentFilter> aPaymentFilters, final SystemAccountType aSystemAccountFilter) {
        final Number[][] tableCells = new Number[aPaymentFilters.size()][3];
        int i = 0;
        for (final PaymentFilter paymentFilter : aPaymentFilters) {
            tableCells[i][0] = getIncome(periodMain, paymentFilter, aSystemAccountFilter);
            tableCells[i][1] = getIncome(periodAlt, paymentFilter, aSystemAccountFilter);
            tableCells[i][2] = StatisticalNumber.createPercentage(tableCells[i][1], tableCells[i][0]);
            i++;
        }
        return tableCells;
    }

}
