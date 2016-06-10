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
package nl.strohalm.cyclos.services.transactions;

import java.math.BigDecimal;
import java.math.MathContext;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.BigDecimalHelper;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contais report data of invoice or transaction amounts. Just keeps two kinds of numbers for any transaction amounts: the total sum of amounts, and a
 * counter representing the number of amounts added.
 * 
 * @author luis, rafael
 */
public class TransactionSummaryVO extends DataObject {

    private static final long serialVersionUID = -2308383871952147029L;
    private int               count;
    private BigDecimal        amount;

    public TransactionSummaryVO() {
    }

    public TransactionSummaryVO(final int count, final BigDecimal amount) {
        setCount(count);
        setAmount(amount);
    }

    /**
     * Returns a new transaction summary, adding one transaction with the given amount
     */
    public TransactionSummaryVO add(final BigDecimal amount) {
        return add(1, amount);
    }

    /**
     * Returns a new transaction summary, adding the given parameters to this one
     */
    public TransactionSummaryVO add(final int count, final BigDecimal amount) {
        return new TransactionSummaryVO(this.count + count, BigDecimalHelper.nvl(this.amount).add(BigDecimalHelper.nvl(amount)));
    }

    /**
     * Returns a new transaction summary, adding the given parameters to this one
     */
    public TransactionSummaryVO add(final TransactionSummaryVO other) {
        return add(other.getCount(), other.getAmount());
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getAverage() {
        final MathContext mathContext = new MathContext(LocalSettings.BIG_DECIMAL_DIVISION_PRECISION);
        return count == 0 ? BigDecimal.ZERO : amount.divide(new BigDecimal(count), mathContext);
    }

    public int getCount() {
        return count;
    }

    public void setAmount(BigDecimal amount) {
        amount = BigDecimalHelper.nvl(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0 && !allowNegative()) {
            amount = BigDecimal.ZERO;
        }
        this.amount = amount;
    }

    public void setCount(final int count) {
        this.count = count < 0 ? 0 : count;
    }

    /**
     * Returns a new transaction summary, subtracting one transaction with the given amount
     */
    public TransactionSummaryVO subtract(final BigDecimal amount) {
        return subtract(1, amount);
    }

    /**
     * Returns a new transaction summary, subtracting the given parameters to this one
     */
    public TransactionSummaryVO subtract(final int count, final BigDecimal amount) {
        return add(-count, BigDecimalHelper.nvl(amount).negate());
    }

    /**
     * Returns a new transaction summary, subtracting the given parameters to this one
     */
    public TransactionSummaryVO subtract(final TransactionSummaryVO other) {
        return subtract(other.getCount(), other.getAmount());
    }

    protected boolean allowNegative() {
        return false;
    }

}
