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
package nl.strohalm.cyclos.webservices.webshop;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Parameters for generating a webshop ticket via web services
 * @author luis
 */
public class GenerateWebShopTicketParams implements Serializable {

    private static final long serialVersionUID = -1816777948198919713L;
    private BigDecimal        amount;
    private String            currency;
    private String            clientAddress;
    private String            description;
    private String            returnUrl;
    private String            toUsername;

    public BigDecimal getAmount() {
        return amount;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setClientAddress(final String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setReturnUrl(final String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public void setToUsername(final String toUsername) {
        this.toUsername = toUsername;
    }

    @Override
    public String toString() {
        return "GenerateWebShopTicketParams(amount=" + amount + ", currency=" + currency + ", clientAddress=" + clientAddress + ", description=" + description + ", returnUrl=" + returnUrl + ", toUsername=" + toUsername + ")";

    }

}
