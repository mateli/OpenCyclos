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

import java.util.ArrayList;
import java.util.Arrays;

import nl.strohalm.cyclos.controls.BaseQueryForm;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to search loan payments
 * @author luis
 */
public class SearchLoanPaymentsForm extends BaseQueryForm {

    private static final long serialVersionUID = -5275865610699846428L;

    private boolean           queryAlreadyExecuted;

    public SearchLoanPaymentsForm() {
        setQuery("loanValues", new MapBean(true, "field", "value"));
        setQuery("memberValues", new MapBean(true, "field", "value"));
        setQuery("expirationPeriod", new MapBean("begin", "end"));
        setQuery("repaymentPeriod", new MapBean("begin", "end"));
        setQuery("statusList", new ArrayList<Object>(Arrays.asList(LoanPayment.Status.OPEN.name())));
    }

    public boolean isQueryAlreadyExecuted() {
        return queryAlreadyExecuted;
    }

    public void setQueryAlreadyExecuted(final boolean queryAlreadyExecuted) {
        this.queryAlreadyExecuted = queryAlreadyExecuted;
    }
}
