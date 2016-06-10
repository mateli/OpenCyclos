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
package nl.strohalm.cyclos.entities.accounts.transactions;

import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for searching payments that an element needs to authorize
 * @author luis
 */
public class TransfersAwaitingAuthorizationQuery extends QueryParameters {

    private static final long serialVersionUID = -6875561954132486484L;
    private Element           authorizer;
    private Member            member;
    private boolean           onlyWithoutBroker;
    private PaymentFilter     paymentFilter;
    private TransferType      transferType;
    private Period            period;
    private String            transactionNumber;

    public Element getAuthorizer() {
        return authorizer;
    }

    public Member getMember() {
        return member;
    }

    public PaymentFilter getPaymentFilter() {
        return paymentFilter;
    }

    public Period getPeriod() {
        return period;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isOnlyWithoutBroker() {
        return onlyWithoutBroker;
    }

    public void setAuthorizer(final Element authorizer) {
        this.authorizer = authorizer;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setOnlyWithoutBroker(final boolean onlyWithoutBroker) {
        this.onlyWithoutBroker = onlyWithoutBroker;
    }

    public void setPaymentFilter(final PaymentFilter paymentFilter) {
        this.paymentFilter = paymentFilter;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setTransactionNumber(final String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }
}
