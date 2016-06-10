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

/**
 * Webshop ticket for web services
 * @author luis
 */
@XmlType(name = "webshopTicket")
public class WebShopTicketVO extends TicketVO {

    private static final long serialVersionUID = 6293121762451720074L;
    private String            clientAddress;
    private String            memberAddress;
    private String            returnUrl;

    public String getClientAddress() {
        return clientAddress;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setClientAddress(final String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public void setMemberAddress(final String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public void setReturnUrl(final String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString() {
        return "WebShopTicketVO(clientAddress=" + clientAddress + ", memberAddress=" + memberAddress + ", returnUrl=" + returnUrl + ")";
    }

}
