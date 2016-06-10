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
package nl.strohalm.cyclos.entities.accounts.guarantees;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation.Status;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

public class PaymentObligationQuery extends QueryParameters {

    private static final long       serialVersionUID = -2169964645483885409L;
    private Member                  buyer;
    private Member                  seller;
    private List<Status>            statusList;
    private Period                  expiration;
    // if it's true then use the expiration field to filter by the PO's maxPublishDate too
    private boolean                 applyExpirationToMaxPublishDate;
    private BigDecimal              amountUpperLimit;
    private BigDecimal              amountLowerLimit;
    private Currency                currency;
    // this property is used to add an OR filter in the resulting HQL query
    // to restrict the result to this member
    private Member                  loggedMember;

    // this collection is used to add an IN operator to the query to restrict the result to those payment obligations of buyers or seller whose groups
    // belong to this collection
    private Collection<MemberGroup> managedMemberGroups;

    public BigDecimal getAmountLowerLimit() {
        return amountLowerLimit;
    }

    public BigDecimal getAmountUpperLimit() {
        return amountUpperLimit;
    }

    public Member getBuyer() {
        return buyer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Period getExpiration() {
        return expiration;
    }

    public Member getLoggedMember() {
        return loggedMember;
    }

    public Collection<MemberGroup> getManagedMemberGroups() {
        return managedMemberGroups;
    }

    public Member getSeller() {
        return seller;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public boolean isApplyExpirationToMaxPublishDate() {
        return applyExpirationToMaxPublishDate;
    }

    public void setAmountLowerLimit(final BigDecimal amountLowerLimit) {
        this.amountLowerLimit = amountLowerLimit;
    }

    public void setAmountUpperLimit(final BigDecimal amountUpperLimit) {
        this.amountUpperLimit = amountUpperLimit;
    }

    public void setApplyExpirationToMaxPublishDate(final boolean applyExpirationToMaxPublishDate) {
        this.applyExpirationToMaxPublishDate = applyExpirationToMaxPublishDate;
    }

    public void setBuyer(final Member buyer) {
        this.buyer = buyer;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setExpiration(final Period startIn) {
        expiration = startIn;
    }

    public void setLoggedMember(final Member loggedMember) {
        this.loggedMember = loggedMember;
    }

    public void setManagedMemberGroups(final Collection<MemberGroup> groups) {
        managedMemberGroups = groups;
    }

    public void setSeller(final Member seller) {
        this.seller = seller;
    }

    public void setStatusList(final List<Status> status) {
        statusList = status;
    }
}
