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

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlType;

/**
 * Represents an account status
 * @author luis
 */
@XmlType(name = "accountStatus")
public class AccountStatusVO implements Serializable {
    private static final long serialVersionUID = -6280638918625688804L;
    private BigDecimal        balance;
    private String            formattedBalance;
    private BigDecimal        availableBalance;
    private String            formattedAvailableBalance;
    private BigDecimal        reservedAmount;
    private String            formattedReservedAmount;
    private BigDecimal        creditLimit;
    private String            formattedCreditLimit;
    private BigDecimal        upperCreditLimit;
    private String            formattedUpperCreditLimit;

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public String getFormattedAvailableBalance() {
        return formattedAvailableBalance;
    }

    public String getFormattedBalance() {
        return formattedBalance;
    }

    public String getFormattedCreditLimit() {
        return formattedCreditLimit;
    }

    public String getFormattedReservedAmount() {
        return formattedReservedAmount;
    }

    public String getFormattedUpperCreditLimit() {
        return formattedUpperCreditLimit;
    }

    public BigDecimal getReservedAmount() {
        return reservedAmount;
    }

    public BigDecimal getUpperCreditLimit() {
        return upperCreditLimit;
    }

    public void setAvailableBalance(final BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreditLimit(final BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setFormattedAvailableBalance(final String formattedAvailableBalance) {
        this.formattedAvailableBalance = formattedAvailableBalance;
    }

    public void setFormattedBalance(final String formattedBalance) {
        this.formattedBalance = formattedBalance;
    }

    public void setFormattedCreditLimit(final String formattedCreditLimit) {
        this.formattedCreditLimit = formattedCreditLimit;
    }

    public void setFormattedReservedAmount(final String formattedReservedAmount) {
        this.formattedReservedAmount = formattedReservedAmount;
    }

    public void setFormattedUpperCreditLimit(final String formattedUpperCreditLimit) {
        this.formattedUpperCreditLimit = formattedUpperCreditLimit;
    }

    public void setReservedAmount(final BigDecimal reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public void setUpperCreditLimit(final BigDecimal upperCreditLimit) {
        this.upperCreditLimit = upperCreditLimit;
    }

    @Override
    public String toString() {
        return "AccountStatusVO(balance=" + balance + ", formattedBalance=" + formattedBalance + ", availableBalance=" + availableBalance + ", formattedAvailableBalance=" + formattedAvailableBalance + ", reservedAmount=" + reservedAmount + ", formattedReservedAmount=" + formattedReservedAmount + ", creditLimit=" + creditLimit + ", formattedCreditLimit=" + formattedCreditLimit + ", upperCreditLimit=" + upperCreditLimit + ", formattedUpperCreditLimit=" + formattedUpperCreditLimit + ")";
    }

}
