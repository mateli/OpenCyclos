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

/**
 * A {@link TransactionSummaryVO} that allows negative amounts
 * 
 * @author luis
 */
public class NegativeAllowedTransactionSummaryVO extends TransactionSummaryVO {

    private static final long serialVersionUID = -6816696837085285886L;

    public NegativeAllowedTransactionSummaryVO() {
    }

    public NegativeAllowedTransactionSummaryVO(final int count, final BigDecimal amount) {
        super(count, amount);
    }

    @Override
    protected boolean allowNegative() {
        return true;
    }

}
