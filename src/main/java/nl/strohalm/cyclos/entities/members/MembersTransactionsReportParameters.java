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

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.PageParameters;

/**
 * Parameters used to build the member transactions report
 * 
 * @author luis
 */
public class MembersTransactionsReportParameters extends DataObject {

    private static final long         serialVersionUID = -5856820625601506756L;
    private boolean                   fetchBroker;
    private Collection<MemberGroup>   memberGroups;
    private Period                    period;
    private boolean                   credits;
    private boolean                   debits;
    private Collection<PaymentFilter> paymentFilters;
    private PageParameters            pageParameters;

    public Collection<MemberGroup> getMemberGroups() {
        return memberGroups;
    }

    public PageParameters getPageParameters() {
        return pageParameters;
    }

    public Collection<PaymentFilter> getPaymentFilters() {
        return paymentFilters;
    }

    public Period getPeriod() {
        return period;
    }

    public boolean isCredits() {
        return credits;
    }

    public boolean isDebits() {
        return debits;
    }

    public boolean isFetchBroker() {
        return fetchBroker;
    }

    public void setCredits(final boolean credits) {
        this.credits = credits;
    }

    public void setDebits(final boolean debits) {
        this.debits = debits;
    }

    public void setFetchBroker(final boolean fetchBroker) {
        this.fetchBroker = fetchBroker;
    }

    public void setMemberGroups(final Collection<MemberGroup> memberGroups) {
        this.memberGroups = memberGroups;
    }

    public void setPageParameters(final PageParameters pageParameters) {
        this.pageParameters = pageParameters;
    }

    public void setPaymentFilters(final Collection<PaymentFilter> paymentFilters) {
        this.paymentFilters = paymentFilters;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

}
