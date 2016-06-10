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
package nl.strohalm.cyclos.controls.loans;

import nl.strohalm.cyclos.controls.BaseQueryForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to search loans
 * @author luis
 */
public class SearchLoansForm extends BaseQueryForm {

    private static final long serialVersionUID = -5275865610699846428L;
    private long              loanGroupId;
    private long              memberId;
    private boolean           queryAlreadyExecuted;

    public SearchLoansForm() {
        setQuery("loanValues", new MapBean(true, "field", "value"));
        setQuery("memberValues", new MapBean(true, "field", "value"));
        setQuery("grantPeriod", new MapBean("begin", "end"));
        setQuery("expirationPeriod", new MapBean("begin", "end"));
        setQuery("paymentPeriod", new MapBean("begin", "end"));
    }

    public long getLoanGroupId() {
        return loanGroupId;
    }

    public long getMemberId() {
        return memberId;
    }

    public boolean isQueryAlreadyExecuted() {
        return queryAlreadyExecuted;
    }

    public void setLoanGroupId(final long loanGroupId) {
        this.loanGroupId = loanGroupId;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setQueryAlreadyExecuted(final boolean queryAlreadyExecuted) {
        this.queryAlreadyExecuted = queryAlreadyExecuted;
    }
}
