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
package nl.strohalm.cyclos.entities.accounts.loans;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.utils.Period;

/**
 * Parameters for loan queries
 * @author luis
 */
public class LoanQuery extends BaseLoanQuery {

    /**
     * More detailed status that may be used on searches
     * @author luis
     */
    public static enum QueryStatus {
        ANY_OPEN, ANY_CLOSED, OPEN, CLOSED, EXPIRED, IN_PROCESS, RECOVERED, UNRECOVERABLE, PENDING_AUTHORIZATION, AUTHORIZATION_DENIED;

        /**
         * Returns whether this status is related to authorization
         */
        public boolean isAuthorizationRelated() {
            return this == PENDING_AUTHORIZATION || this == AUTHORIZATION_DENIED;
        }
    }

    private static final long serialVersionUID = 334174191910001865L;
    private Period            expirationPeriod;
    private Period            grantPeriod;
    private Period            paymentPeriod;
    private Loan.Status       status;
    private QueryStatus       queryStatus;
    private Currency          currency;
    private boolean           hideAuthorizationRelated;

    public Currency getCurrency() {
        return currency;
    }

    public Period getExpirationPeriod() {
        return expirationPeriod;
    }

    public Period getGrantPeriod() {
        return grantPeriod;
    }

    public Period getPaymentPeriod() {
        return paymentPeriod;
    }

    public QueryStatus getQueryStatus() {
        return queryStatus;
    }

    public Loan.Status getStatus() {
        return status;
    }

    public boolean isHideAuthorizationRelated() {
        return hideAuthorizationRelated;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setExpirationPeriod(final Period expirationPeriod) {
        this.expirationPeriod = expirationPeriod;
    }

    public void setGrantPeriod(final Period grantPeriod) {
        this.grantPeriod = grantPeriod;
    }

    public void setHideAuthorizationRelated(final boolean hideAuthorizationRelated) {
        this.hideAuthorizationRelated = hideAuthorizationRelated;
    }

    public void setPaymentPeriod(final Period paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public void setQueryStatus(final QueryStatus queryStatus) {
        this.queryStatus = queryStatus;
    }

    public void setStatus(final Loan.Status status) {
        this.status = status;
    }
}
