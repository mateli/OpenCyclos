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
package nl.strohalm.cyclos.controls.members.brokering;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;

/**
 * Iterates over the brokered list, adding loan information
 * @author luis
 * @author rinke
 */
public class BrokeredIterator implements Iterator<Map<String, Object>> {
    private final Iterator<Brokering> iterator;
    private final boolean             showLoanData;
    private final LoanService         loanService;

    public BrokeredIterator(final Iterator<Brokering> iterator, final LoanService loanService, final boolean showLoanData) {
        this.iterator = iterator;
        this.showLoanData = showLoanData;
        this.loanService = loanService;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Map<String, Object> next() {
        final Brokering brokering = iterator.next();
        final Map<String, Object> row = new HashMap<String, Object>();
        row.put("brokering", brokering);
        if (showLoanData) {
            final TransactionSummaryVO loans = loanService.loanSummary(brokering.getBrokered());
            row.put("loans", loans);
        }
        return row;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
