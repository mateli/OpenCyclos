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
package nl.strohalm.cyclos.entities.members;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.collections.MapUtils;

/**
 * Contains data for a row in the member report: transaction summary
 * 
 * @author luis
 */
public class MemberTransactionSummaryReportData extends DataObject {

    private static final long                        serialVersionUID = 6613220701881304322L;
    private Member                                   member;
    private Map<PaymentFilter, TransactionSummaryVO> debits;
    private Map<PaymentFilter, TransactionSummaryVO> credits;

    public MemberTransactionSummaryReportData() {
    }

    public void addCredits(final PaymentFilter paymentFilter, final TransactionSummaryVO transactions) {
        if (transactions == null || transactions.getCount() == 0) {
            return;
        }
        if (credits == null) {
            credits = new HashMap<PaymentFilter, TransactionSummaryVO>();
        }
        credits.put(paymentFilter, transactions);
    }

    public void addDebits(final PaymentFilter paymentFilter, final TransactionSummaryVO transactions) {
        if (transactions == null || transactions.getCount() == 0) {
            return;
        }
        if (debits == null) {
            debits = new HashMap<PaymentFilter, TransactionSummaryVO>();
        }
        debits.put(paymentFilter, transactions);
    }

    public Map<PaymentFilter, TransactionSummaryVO> getCredits() {
        return credits;
    }

    public Map<PaymentFilter, TransactionSummaryVO> getDebits() {
        return debits;
    }

    public Member getMember() {
        return member;
    }

    public boolean isHasData() {
        return MapUtils.isNotEmpty(credits) || MapUtils.isNotEmpty(debits);
    }

    public void setCredits(final Map<PaymentFilter, TransactionSummaryVO> credits) {
        this.credits = credits;
    }

    public void setDebits(final Map<PaymentFilter, TransactionSummaryVO> debits) {
        this.debits = debits;
    }

    public void setMember(final Member member) {
        this.member = member;
    }
}
