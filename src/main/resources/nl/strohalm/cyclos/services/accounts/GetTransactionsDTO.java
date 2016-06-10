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
package nl.strohalm.cyclos.services.accounts;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;

/**
 * Data Transfer Object for retrieving transaction information
 * @author luis
 */
public class GetTransactionsDTO extends AccountDTO {

    private static final long         serialVersionUID = -4431471525909266108L;
    private Member                    relatedToMember;
    private Element                   by;
    private Period                    period;
    private Collection<PaymentFilter> paymentFilters;
    private boolean                   rootOnly;

    public GetTransactionsDTO() {
    }

    public GetTransactionsDTO(final Account account) {
        this(account, (Period) null);
    }

    /**
     * creates a GetTransactionsDTO. Note that the date is converted to a Period; this means that it is truncated to 0:00:00 the next day. If you
     * definitely need time and date, use the constructor with period and set <code>Period.setUseTime(true)</code> before passing it.
     */
    public GetTransactionsDTO(final Account account, final Calendar date) {
        this(account, Period.endingAt(date));
    }

    public GetTransactionsDTO(final Account account, final Period period) {
        super(account);
        this.period = period;
    }

    public GetTransactionsDTO(final AccountOwner owner, final AccountType type) {
        this(owner, type, null, null);
    }

    public GetTransactionsDTO(final AccountOwner owner, final AccountType type, final Calendar date) {
        this(owner, type, Period.endingAt(date), null);
    }

    public GetTransactionsDTO(final AccountOwner owner, final AccountType type, final Period period) {
        this(owner, type, period, null);
    }

    public GetTransactionsDTO(final AccountOwner owner, final AccountType type, final Period period, final PaymentFilter paymentFilter) {
        super(owner, type);
        this.period = period;
        if (paymentFilter != null) {
            paymentFilters = Collections.singletonList(paymentFilter);
        }
    }

    public Element getBy() {
        return by;
    }

    public Collection<PaymentFilter> getPaymentFilters() {
        return paymentFilters;
    }

    public Period getPeriod() {
        return period;
    }

    public Member getRelatedToMember() {
        return relatedToMember;
    }

    public boolean isRootOnly() {
        return rootOnly;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setDate(final Calendar cal) {
        setPeriod(Period.endingAt(cal));
    }

    public void setPaymentFilter(final PaymentFilter paymentFilter) {
        if (paymentFilter != null) {
            paymentFilters = Collections.singletonList(paymentFilter);
        } else {
            paymentFilters = null;
        }
    }

    public void setPaymentFilters(final Collection<PaymentFilter> paymentFilters) {
        this.paymentFilters = paymentFilters;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setRelatedToMember(final Member relatedTomember) {
        relatedToMember = relatedTomember;
    }

    public void setRootOnly(final boolean rootOnly) {
        this.rootOnly = rootOnly;
    }

}
