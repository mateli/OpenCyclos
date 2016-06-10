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
package nl.strohalm.cyclos.controls.webshop;

import org.apache.struts.action.ActionForm;

/**
 * Form used to perform webshop payments
 * @author luis
 */
public class WebShopPaymentForm extends ActionForm {
    private static final long serialVersionUID = 6803511094872567772L;
    private long              transferTypeId;
    private String            principalType;
    private String            principal;
    private String            credentials;

    public String getCredentials() {
        return credentials;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPrincipalType() {
        return principalType;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setCredentials(final String credentials) {
        this.credentials = credentials;
    }

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }

    public void setPrincipalType(final String principalType) {
        this.principalType = principalType;
    }

    public void setTransferTypeId(final long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }
}
