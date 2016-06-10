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

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Parameters for invoice searches
 * @author rafael
 */
public class InvoiceQuery extends QueryParameters {

    public static enum Direction {
        INCOMING, OUTGOING;
    }

    private static final long       serialVersionUID = 2534355418534766617L;
    private AccountOwner            owner;
    private AccountOwner            relatedOwner;
    private Collection<MemberGroup> groups;
    private String                  description;
    private Direction               direction;
    private Period                  period;
    private Period                  paymentPeriod;
    private Invoice.Status          status;
    private TransferType            transferType;
    private String                  transactionNumber;
    private Element                 by;

    public Element getBy() {
        return by;
    }

    public String getDescription() {
        return description;
    }

    public Direction getDirection() {
        return direction;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Member getMember() {
        return owner instanceof Member ? (Member) owner : null;
    }

    public AccountOwner getOwner() {
        return owner;
    }

    public Period getPaymentPeriod() {
        return paymentPeriod;
    }

    public Period getPeriod() {
        return period;
    }

    public Member getRelatedMember() {
        return relatedOwner instanceof Member ? (Member) relatedOwner : null;
    }

    public AccountOwner getRelatedOwner() {
        return relatedOwner;
    }

    public Invoice.Status getStatus() {
        return status;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setOwner(final AccountOwner owner) {
        this.owner = owner;
    }

    public void setPaymentPeriod(final Period paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
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

    public void setTransactionNumber(final String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

}
