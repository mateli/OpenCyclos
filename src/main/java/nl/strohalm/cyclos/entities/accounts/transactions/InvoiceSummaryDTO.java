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

import java.util.Collection;
import java.util.Collections;
import java.util.Currency;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.Period;

import org.apache.commons.collections.CollectionUtils;

public class InvoiceSummaryDTO extends DataObject {

    private static final long       serialVersionUID = 3851785338018145320L;

    private InvoiceQuery.Direction  direction;
    private AccountOwner            owner;
    private AccountOwner            relatedOwner;
    private boolean                 fromMemberToMember;
    private Collection<AccountType> types;
    private PaymentFilter           filter;
    private Period                  period;
    private Invoice.Status          status;
    private Currency               	currency;

    public Currency getCurrency() {
        return currency;
    }

    public InvoiceQuery.Direction getDirection() {
        return direction;
    }

    public PaymentFilter getFilter() {
        return filter;
    }

    public AccountOwner getOwner() {
        return owner;
    }

    public Period getPeriod() {
        return period;
    }

    public AccountOwner getRelatedOwner() {
        return relatedOwner;
    }

    public Invoice.Status getStatus() {
        return status;
    }

    public AccountType getType() {
        return (CollectionUtils.isEmpty(getTypes()) ? null : getTypes().iterator().next());
    }

    public Collection<AccountType> getTypes() {
        return types;
    }

    public boolean isFromMemberToMember() {
        return fromMemberToMember;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setDirection(final InvoiceQuery.Direction direction) {
        this.direction = direction;
    }

    public void setFilter(final PaymentFilter filter) {
        this.filter = filter;
    }

    public void setFromMemberToMember(final boolean fromMemberToMember) {
        this.fromMemberToMember = fromMemberToMember;
    }

    public void setOwner(final AccountOwner owner) {
        this.owner = owner;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setRelatedOwner(final AccountOwner relatedOwner) {
        this.relatedOwner = relatedOwner;
    }

    public void setStatus(final Invoice.Status status) {
        this.status = status;
    }

    public void setType(final AccountType type) {
        types = Collections.singletonList(type);
    }

    public void setTypes(final Collection<AccountType> types) {
        this.types = types;
    }

}
