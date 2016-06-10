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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.reports.StatisticalDTO;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.utils.Period;

/**
 * takes care of the calculations for finance stats for one single point (bar or section) in the graphs/tables. Should be subclassed for specific sets
 * of stats (i.e.: a subclass for single period, a subclass for Compared, etc)
 * 
 * @author Rinke
 * 
 */
public abstract class FinanceStats {

    /**
     * the cached result
     */
    protected Number[][]                tableCells;

    /**
     * cached paymentFilters
     */
    protected Collection<PaymentFilter> paymentFilters;
    /**
     * the cached rowKeys
     */
    protected String[]                  paymentFilterNames;
    private final TransferDAO           transferDao;

    protected FinanceStats(final TransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    /**
     * returns the paymentFilter names in a String array. If there is only one paymentFilter in the paymentFilters, then an array of containing
     * transferType names is returned.
     * 
     * @return a array of paymentFilter names.
     */
    public String[] getPaymentFilterNames(final Collection<PaymentFilter> aPaymentFilters) {
        if (!hasEqualParameters(aPaymentFilters) || paymentFilterNames == null) {
            paymentFilterNames = null;
            final List<String> names = new ArrayList<String>();
            for (final PaymentFilter paymentFilter : aPaymentFilters) {
                if (aPaymentFilters.size() == 1) {
                    for (final TransferType transferType : paymentFilter.getTransferTypes()) {
                        names.add(transferType.getName());
                    }
                    break;
                }
                names.add(paymentFilter.getName());
            }
            paymentFilterNames = names.toArray(new String[] {});
        }
        return paymentFilterNames;
    }

    // TODO Jefferson: please javadoc
    private StatisticalNumber getSumOfTransactions(final Period aPeriod, final TransferType transferType) {
        final StatisticalDTO dto = new StatisticalDTO();
        dto.setPeriod(aPeriod);
        dto.setTransferType(transferType);
        final BigDecimal sumOfTransaction = transferDao.getSumOfTransactions(dto);
        return new StatisticalNumber(sumOfTransaction.doubleValue(), (byte) 2);
    }

    /**
     * checks if the paymentFilters param has changed, and if a local cached result is available. If parameters haven't changed and a cached value of
     * the results is available, the calling method should read filternames from local field values rather than reretrieving them again. The method
     * does <b>NOT</b> update the values it checks, that is the responsibility of the calling method, after having redone all calculations.
     * 
     * @param aPaymentFilters
     * @return <code>false</code> if the input parameters are different from the corresponding class instance variables, or if there is no cached
     * result available.
     */
    private boolean hasEqualParameters(final Collection<PaymentFilter> aPaymentFilters) {
        return (paymentFilters != null && paymentFilters.equals(aPaymentFilters));
    }

    /**
     * gets the Expenditure which was done from the specified system Account, in the period, and according to the paymentFilter
     * 
     * @param period
     * @param paymentFilter
     * @param systemAccountFilter
     * @return a <code>StatisticalNumber</code> containing the result.
     */
    Number getExpenditure(final Period aPeriod, final PaymentFilter aPaymentFilter, final SystemAccountType aSystemAccountFilter) {
        double expenditure = 0;
        for (final TransferType transferType : aPaymentFilter.getTransferTypes()) {
            expenditure += getExpenditure(aPeriod, transferType, aSystemAccountFilter).doubleValue();
        }
        return new StatisticalNumber(expenditure, (byte) 2);
    }

    /**
     * gets the Expenditure which was done from the specified system Account, in the period, and according to the transferType specified.
     * 
     * @param period2
     * @param transferType
     * @param systemAccountFilter2
     * @return a <code>StatisticalNumber</code> containing the result.
     */
    Number getExpenditure(final Period aPeriod, final TransferType transferType, final SystemAccountType aSystemAccountType) {
        if (!transferType.getFrom().equals(aSystemAccountType)) {
            return new StatisticalNumber(0, (byte) 2);
        } else {
            return getSumOfTransactions(aPeriod, transferType);
        }
    }

    /**
     * calculates the expenditure for this systemAccount in the specified period, which falls NOT in the specified paymentFilter Collection.
     * 
     * @param period
     * @param paymentFilters
     * @param systemAccountFilter
     */
    Number getExpenditureRest(final Period aPeriod, final Collection<PaymentFilter> aPaymentFilters, final SystemAccountType aSystemAccountType) {
        final TransferQuery transferQuery = new TransferQuery();
        transferQuery.setFromAccountType(aSystemAccountType);
        transferQuery.setPaymentFilters(aPaymentFilters);
        transferQuery.setPeriod(aPeriod);
        final BigDecimal sumOfTransactionsRest = transferDao.getSumOfTransactionsRest(transferQuery);
        return new StatisticalNumber(sumOfTransactionsRest.doubleValue(), (byte) 2);
    }

    /**
     * gets the Income retrieved on the specified system Account, in the period, and according to the paymentFilter
     * 
     * @param period
     * @param paymentFilter
     * @param systemAccountFilter
     * @return a <code>StatisticalNumber</code> containing the result.
     */
    Number getIncome(final Period aPeriod, final PaymentFilter aPaymentFilter, final SystemAccountType aSystemAccountFilter) {
        double income = 0;
        for (final TransferType transferType : aPaymentFilter.getTransferTypes()) {
            income += getIncome(aPeriod, transferType, aSystemAccountFilter).doubleValue();
        }
        return new StatisticalNumber(income, (byte) 2);
    }

    /**
     * gets the income retrieved on the specified system Account, in the period, and according to the transferType specified.
     * 
     * @param period2
     * @param transferType
     * @param systemAccountFilter2
     * @return a <code>StatisticalNumber</code> containing the result.
     */
    Number getIncome(final Period aPeriod, final TransferType transferType, final SystemAccountType aSystemAccountType) {
        if (!transferType.getTo().equals(aSystemAccountType)) {
            return new StatisticalNumber(0, (byte) 2);
        } else {
            return getSumOfTransactions(aPeriod, transferType);
        }
    }

    /**
     * calculates the income on this systemAccount in the specified period, which falls NOT in the specified paymentFilter Collection.
     * 
     * @param period
     * @param paymentFilters
     * @param systemAccountFilter
     */
    Number getIncomeRest(final Period aPeriod, final Collection<PaymentFilter> aPaymentFilters, final SystemAccountType aSystemAccountType) {
        final TransferQuery transferQuery = new TransferQuery();
        transferQuery.setToAccountType(aSystemAccountType);
        transferQuery.setPaymentFilters(aPaymentFilters);
        transferQuery.setPeriod(aPeriod);
        final BigDecimal sumOfTransactionsRest = transferDao.getSumOfTransactionsRest(transferQuery);
        return new StatisticalNumber(sumOfTransactionsRest.doubleValue(), (byte) 2);
    }

}
