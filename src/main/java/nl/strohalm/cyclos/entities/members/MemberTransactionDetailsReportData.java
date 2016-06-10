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
package nl.strohalm.cyclos.entities.members;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains data for a row in the member report: transaction details
 * 
 * @author luis
 */
public class MemberTransactionDetailsReportData extends DataObject {

    private static final long serialVersionUID = -5109382208791934688L;
    private String            username;
    private String            name;
    private String            brokerUsername;
    private String            brokerName;
    private String            accountTypeName;
    private Calendar          date;
    private BigDecimal        amount;
    private String            description;
    private String            relatedUsername;
    private String            relatedName;
    private String            transferTypeName;
    private String            transactionNumber;

    public MemberTransactionDetailsReportData() {
    }

    public MemberTransactionDetailsReportData(final String username, final String name, final String brokerUsername, final String brokerName, final String accountTypeName, final Calendar date, final BigDecimal amount, final String description, final String relatedUsername, final String relatedName, final String transferTypeName, final String transactionNumber) {
        this.username = username;
        this.name = name;
        this.brokerUsername = brokerUsername;
        this.brokerName = brokerName;
        this.accountTypeName = accountTypeName;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.relatedUsername = relatedUsername;
        this.relatedName = relatedName;
        this.transferTypeName = transferTypeName;
        this.transactionNumber = transactionNumber;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public String getBrokerUsername() {
        return brokerUsername;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getRelatedName() {
        return relatedName;
    }

    public String getRelatedUsername() {
        return relatedUsername;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public String getTransferTypeName() {
        return transferTypeName;
    }

    public String getUsername() {
        return username;
    }

    public void setAccountTypeName(final String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setBrokerName(final String brokerName) {
        this.brokerName = brokerName;
    }

    public void setBrokerUsername(final String brokerUsername) {
        this.brokerUsername = brokerUsername;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRelatedName(final String relatedName) {
        this.relatedName = relatedName;
    }

    public void setRelatedUsername(final String relatedUsername) {
        this.relatedUsername = relatedUsername;
    }

    public void setTransactionNumber(final String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setTransferTypeName(final String transferTypeName) {
        this.transferTypeName = transferTypeName;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
