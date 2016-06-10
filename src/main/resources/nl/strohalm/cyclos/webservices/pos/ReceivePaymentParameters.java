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
package nl.strohalm.cyclos.webservices.pos;

/**
 * Parameters for receive a payment in a POS device
 * @author luis
 */
public class ReceivePaymentParameters extends BasePaymentParameters {

    private String fromMemberPrincipalType;
    private String fromMemberPrincipal;
    private String fromMemberCredentials;

    public String getFromMemberCredentials() {
        return fromMemberCredentials;
    }

    public String getFromMemberPrincipal() {
        return fromMemberPrincipal;
    }

    public String getFromMemberPrincipalType() {
        return fromMemberPrincipalType;
    }

    public void setFromMemberCredentials(final String fromMemberCredentials) {
        this.fromMemberCredentials = fromMemberCredentials;
    }

    public void setFromMemberPrincipal(final String fromMemberPrincipal) {
        this.fromMemberPrincipal = fromMemberPrincipal;
    }

    public void setFromMemberPrincipalType(final String fromMemberPrincipalType) {
        this.fromMemberPrincipalType = fromMemberPrincipalType;
    }

    @Override
    public String toString() {
        return "ReceivePaymentParameters [fromMemberCredentials=****" + ", fromMemberPrincipal=" + fromMemberPrincipal + ", fromMemberPrincipalType=" + fromMemberPrincipalType + ", " + super.toString() + "]";
    }
}
