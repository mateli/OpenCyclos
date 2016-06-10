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
package nl.strohalm.cyclos.entities.reports;

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.Period;

public class StatisticalDTO {

    private Period                      period;
    private PaymentFilter               paymentFilter;
    private Collection<? extends Group> groups;
    private TransferType                transferType;

    public StatisticalDTO() {
    }

    public StatisticalDTO(final Period period, final PaymentFilter paymentFilter, final Collection<? extends Group> groups) {
        this.period = period;
        this.paymentFilter = paymentFilter;
        this.groups = groups;
    }

    public Collection<? extends Group> getGroups() {
        return groups;
    }

    public PaymentFilter getPaymentFilter() {
        return paymentFilter;
    }

    public Period getPeriod() {
        return period;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setGroups(final Collection<? extends Group> groups) {
        this.groups = groups;
    }

    public void setPaymentFilter(final PaymentFilter paymentFilter) {
        this.paymentFilter = paymentFilter;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

}
