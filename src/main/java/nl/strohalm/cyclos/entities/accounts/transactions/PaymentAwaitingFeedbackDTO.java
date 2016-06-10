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

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains data for payments awaiting feedback
 * 
 * @author luis
 */
public class PaymentAwaitingFeedbackDTO extends DataObject {

    private static final long serialVersionUID = -7227096576225211399L;
    private Long              id;
    private boolean           isScheduled;
    private Long              memberId;
    private String            memberName;
    private String            memberUsername;
    private Long              transferTypeId;
    private Calendar          date;
    private BigDecimal        amount;
    private Currency          currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Calendar getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public void setMemberName(final String memberName) {
        this.memberName = memberName;
    }

    public void setMemberUsername(final String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public void setScheduled(final boolean isScheduled) {
        this.isScheduled = isScheduled;
    }

    public void setTransferTypeId(final Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

}
