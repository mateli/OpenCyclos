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
package nl.strohalm.cyclos.entities.members.imports;

import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Summarized results for a member import
 * 
 * @author luis
 */
public class MemberImportResult extends DataObject {

    private static final long    serialVersionUID = 8007710493307318018L;
    private int                  total;
    private int                  errors;
    private TransactionSummaryVO credits;
    private TransactionSummaryVO debits;

    public TransactionSummaryVO getCredits() {
        return credits;
    }

    public TransactionSummaryVO getDebits() {
        return debits;
    }

    public int getErrors() {
        return errors;
    }

    public int getTotal() {
        return total;
    }

    public void setCredits(final TransactionSummaryVO credits) {
        this.credits = credits;
    }

    public void setDebits(final TransactionSummaryVO debits) {
        this.debits = debits;
    }

    public void setErrors(final int errors) {
        this.errors = errors;
    }

    public void setTotal(final int total) {
        this.total = total;
    }

}
