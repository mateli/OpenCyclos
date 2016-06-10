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
package nl.strohalm.cyclos.webservices.model;

import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a transfer from the point of view of an account (no absolute from / to, but the amount is negative for debits and positive for credits)
 * 
 * @author luis
 */
@XmlType(name = "transfer")
public class AccountHistoryTransferVO extends BasePaymentVO {
    private static final long serialVersionUID = 195971585454276098L;
    private String            transactionNumber;
    private String            traceNumber;

    @JsonIgnore
    @Override
    public PaymentStatusVO getStatus() {
        return super.getStatus();
    }

    @JsonIgnore
    public String getTraceNumber() {
        return traceNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTraceNumber(final String traceNumber) {
        this.traceNumber = traceNumber;
    }

    public void setTransactionNumber(final String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    @Override
    public String toString() {
        if (fromMember != null || fromSystemAccountName != null) {
            return "AccountHistoryTransferVO [amount=" + amount + ", fields=" + fields + ", formattedAmount=" + formattedAmount + ", formattedDate=" + formattedDate + ", formattedProcessDate=" + formattedProcessDate + ", fromMember=" + fromMember + ", toMember=" + member + ", fromSystemAccountName=" + fromSystemAccountName + ", toSystemAccountName=" + systemAccountName + ", traceNumber=" + traceNumber + ", transactionNumber=" + transactionNumber + ", transferType=" + transferType + "]";
        } else {
            return "AccountHistoryTransferVO [amount=" + amount + ", fields=" + fields + ", formattedAmount=" + formattedAmount + ", formattedDate=" + formattedDate + ", formattedProcessDate=" + formattedProcessDate + ", member=" + member + ", systemAccountName=" + systemAccountName + ", traceNumber=" + traceNumber + ", transactionNumber=" + transactionNumber + ", transferType=" + transferType + "]";
        }
    }

}
