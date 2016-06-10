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
package nl.strohalm.cyclos.services.stats;

import java.util.ArrayList;
import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.reports.StatisticalNumber;
import nl.strohalm.cyclos.entities.reports.StatisticalTaxesQuery;
import nl.strohalm.cyclos.services.stats.taxes.TaxesPerMemberStats;
import nl.strohalm.cyclos.utils.Period;

/**
 * implementation of the <code>StatisticalTaxesService</code>.<br>
 * This class is responsible of the gathering of the Taxes Statistics.
 * 
 * @author Rinke
 * 
 */

public class StatisticalTaxesServiceImpl extends StatisticalServiceImpl implements StatisticalTaxesServiceLocal {

    @Override
    public StatisticalResultDTO getComparePeriodsMaxMember(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getComparePeriodsMedianPerMember(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getComparePeriodsNumberOfMembers(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getComparePeriodsRelativeToGrossProduct(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getComparePeriodsVolume(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getDistributionMedianPerMember(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getDistributionRelativeToGrossProduct(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getSinglePeriodMaxMember(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getSinglePeriodMedianPerMember(final StatisticalTaxesQuery query) {
        // getInitializedPaymentFilter(query);
        // final Period periodMain = query.getPeriodMain();
        // boolean paid = query.getPaidOrNot() != PaidOrNot.NOT_PAID;
        // boolean notPaid = query.getPaidOrNot() != PaidOrNot.PAID;
        // int rows = (query.getPaidOrNot() == PaidOrNot.BOTH) ? 2 : 1;
        // final TaxesPerMemberStats statsMainPeriod = new TaxesPerMemberStats(query, periodMain, getTransferDao(), getElementDao());
        // final double[] periodMainSumOfMembers = new double[] {};
        // if (paid) {
        // periodMainSumOfMembers = statsMainPeriod.getVolumePerAllMembers();
        // }
        // // Initialize
        // StatisticalResultDTO result = null;
        // final String baseKey = "reports.stats.taxes.singlePeriod.totalSum";
        // // Assignment of results
        // if (periodMainSumOfMembers.length >= MINIMUM_NUMBER_OF_VALUES) {
        // final StatisticalNumber periodMainMedianPerMember = Median.getMedian(periodMainSumOfMembers, StatisticalService.ALPHA);
        // final Number[][] tableCells = new Number[rows][2];
        // // paid
        // if (paid) {
        // tableCells[0][0] = periodMainMedianPerMember;
        // tableCells[0][1] = new StatisticalNumber(periodMainSumOfMembers.length);
        // }
        //
        // // All members
        // tableCells[1][0] = periodMainMedianPerMember;
        // tableCells[1][1] = new StatisticalNumber(periodMainNofMembers);
        //
        // result = new StatisticalResultDTO(tableCells);
        // result.setBaseKey(baseKey);
        //
        // passGroupFilter(result, queryParameters);
        // passPaymentFilter(result, queryParameters);
        // setGeneralsForSinglePeriod(result, queryParameters, 2, true);
        // // set the currency subHeaders AFTER setGenerals, because subHeaders are overwritten again
        // final Currency currency = getCurrency(queryParameters);
        // final String[] subHeaders = new String[] { parenthesizeString(currency.getSymbol()), queryParameters.getPeriodMain().getName() };
        // result.setColumnSubHeaders(subHeaders);
        // result.setYAxisUnits(currency.getSymbol());
        //
        // } else {
        // result = StatisticalResultDTO.noDataAvailable(baseKey);
        // }
        // return result;
        return null;
    }

    @Override
    public StatisticalResultDTO getSinglePeriodNumberOfMembers(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getSinglePeriodRelativeToGrossProduct(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getSinglePeriodVolume(final StatisticalTaxesQuery query) {
        final Period periodMain = query.getPeriodMain();
        final boolean paid = query.getPaidOrNot() != PaidOrNot.NOT_PAID;
        final boolean notPaid = query.getPaidOrNot() != PaidOrNot.PAID;
        int columns = (query.getPaidOrNot() == PaidOrNot.BOTH) ? 4 : 2;
        final Collection<AccountFee> accountFees = getInitializedAccountFees(query);
        final Collection<TransactionFee> transactionFees = getInitializedTransactionFees(query);
        if (accountFees.size() > 0 && transactionFees.size() > 0) {
            columns++;
        }
        int rows = accountFees.size() + transactionFees.size();
        if (rows > 1) {
            rows++;
        }
        final TaxesPerMemberStats stats = new TaxesPerMemberStats(query, periodMain, getTransferDao());
        StatisticalResultDTO result = null;
        final String baseKey = "reports.stats.taxes.singlePeriod.totalSum";
        // no minimum number of values here, as it is just meant to show the total volume, which may even be 0.
        final Number[][] tableCells = new Number[rows][columns];
        final Currency currency = getCurrency(query);
        final String[] columnKeys = new String[columns];
        final String[] columnSubHeaders = new String[columns];
        int colCounter = 0;
        for (int i = 0; i < rows; i++) {
            if (paid) {
                tableCells[i][0] = new StatisticalNumber(stats.getSumsPerFee()[i].doubleValue());
                columnKeys[0] = "reports.stats.taxes.paid.paid";
                columnSubHeaders[0] = parenthesizeString(currency.getSymbol());
                colCounter = 1;
            }
            if (notPaid) {
                final Double notPaidValue = ((i < accountFees.size()) ? stats.getNotPaidSumsPerAccountFee()[i].doubleValue() : null);
                columnKeys[colCounter] = "reports.stats.taxes.paid.notPaid";
                columnSubHeaders[colCounter] = parenthesizeString(currency.getSymbol());
                tableCells[i][colCounter++] = notPaidValue;
                if (paid) {
                    final Double totalValue = (i < accountFees.size()) ? stats.getSumsPerFee()[i].doubleValue() + stats.getNotPaidSumsPerAccountFee()[i].doubleValue() : null;
                    columnKeys[colCounter] = "reports.stats.general.sum";
                    columnSubHeaders[colCounter] = parenthesizeString(currency.getSymbol());
                    tableCells[i][colCounter++] = new StatisticalNumber(totalValue);
                }
            }
            if (accountFees.size() > 0) {
                final Integer numberCharges = (i < accountFees.size()) ? stats.getNumberOfCharges()[i].intValue() : null;
                columnKeys[colCounter] = "reports.stats.taxes.numberOfCharges";
                columnSubHeaders[colCounter] = "";
                tableCells[i][colCounter++] = new StatisticalNumber(numberCharges);
            }
            if (transactionFees.size() > 0) {
                final Integer numberOfTransactions = (i >= accountFees.size()) ? stats.getNumberOfTransactions()[i].intValue() : null;
                columnKeys[colCounter] = "reports.stats.taxes.numberOfTransactions";
                columnSubHeaders[colCounter] = "";
                tableCells[i][colCounter] = new StatisticalNumber(numberOfTransactions);
            }
        }
        result = new StatisticalResultDTO(tableCells);
        result.setBaseKey(baseKey);
        passGroupFilter(result, query);
        applyRowKeys(result, query);
        result.setColumnKeys(columnKeys);
        result.setColumnSubHeaders(columnSubHeaders);
        result.setYAxisUnits(currency.getSymbol());
        return result;
    }

    @Override
    public StatisticalResultDTO getThroughTimeMaxMember(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getThroughTimeMedianPerMember(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getThroughTimeNumberOfMembers(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getThroughTimeRelativeToGrossProduct(final StatisticalTaxesQuery query) {
        return null;
    }

    @Override
    public StatisticalResultDTO getThroughTimeVolume(final StatisticalTaxesQuery query) {
        return null;
    }

    /**
     * gets the Account Fees from the query, and initializes each of them via the fetchService. The accountFees are then reset in the query, but also
     * returned by this method.
     * 
     * @param query
     * @return a collection with initialized accountFees
     */
    protected Collection<AccountFee> getInitializedAccountFees(final StatisticalTaxesQuery query) {
        final Collection<AccountFee> accountFees = query.getAccountFees();
        final ArrayList<AccountFee> newList = new ArrayList<AccountFee>(accountFees.size());
        boolean anyChanges = false;
        for (AccountFee accountFee : accountFees) {
            if (accountFee.getTransferType() == null) {
                accountFee = fetchService.fetch(accountFee, AccountFee.Relationships.TRANSFER_TYPE);
                anyChanges = true;
            }
            newList.add(accountFee);
        }
        if (anyChanges) {
            query.setAccountFees(newList);
        }
        return newList;
    }

    /**
     * gets the Transaction Fees from the query, and initializes each of them via the fetchService. The transactionFees are then reset in the query,
     * but also returned by this method.
     * 
     * @param query
     * @return a collection with initialized transactionFees
     */
    protected Collection<TransactionFee> getInitializedTransactionFees(final StatisticalTaxesQuery query) {
        final Collection<TransactionFee> transactionFees = query.getTransactionFees();
        final ArrayList<TransactionFee> newList = new ArrayList<TransactionFee>(transactionFees.size());
        boolean anyChanges = false;
        for (TransactionFee transactionFee : transactionFees) {
            if (transactionFee.getGeneratedTransferType() == null) {
                transactionFee = fetchService.fetch(transactionFee, TransactionFee.Relationships.GENERATED_TRANSFER_TYPE);
                anyChanges = true;
            }
            newList.add(transactionFee);
        }
        if (anyChanges) {
            query.setTransactionFees(newList);
        }
        return newList;
    }

    private void applyRowKeys(final StatisticalResultDTO result, final StatisticalTaxesQuery query) {
        final Collection<AccountFee> accountFees = query.getAccountFees();
        final Collection<TransactionFee> transactionFees = query.getTransactionFees();
        int rows = accountFees.size() + transactionFees.size();
        if (rows > 1) {
            rows++;
        }
        final String[] rowKeys = new String[rows];
        for (int i = 0; i < rows - 1; i++) {
            rowKeys[i] = "";
        }
        if (rows > 1) {
            rowKeys[rows - 1] = "reports.stats.general.sum";
        }
        result.setRowKeys(rowKeys);
        int rowCounter = 0;
        for (final AccountFee fee : accountFees) {
            result.setRowHeader(fee.getTransferType().getName(), rowCounter++);
        }
        for (final TransactionFee fee : transactionFees) {
            result.setRowHeader(fee.getGeneratedTransferType().getName(), rowCounter++);
        }
    }

}
