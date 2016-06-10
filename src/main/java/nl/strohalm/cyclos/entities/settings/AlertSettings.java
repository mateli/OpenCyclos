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
package nl.strohalm.cyclos.entities.settings;

import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.TimePeriod;

/**
 * Groups alert settings
 * @author luis
 */
public class AlertSettings extends DataObject {

    private static final long serialVersionUID      = -2165202823135558055L;

    private int               givenVeryBadRefs      = 3;
    private int               receivedVeryBadRefs   = 3;

    private TimePeriod        idleInvoiceExpiration = new TimePeriod(1, TimePeriod.Field.MONTHS);
    private int               amountDeniedInvoices  = 3;

    private int               amountIncorrectLogin  = 10;

    public int getAmountDeniedInvoices() {
        return amountDeniedInvoices;
    }

    public int getAmountIncorrectLogin() {
        return amountIncorrectLogin;
    }

    public int getGivenVeryBadRefs() {
        return givenVeryBadRefs;
    }

    public TimePeriod getIdleInvoiceExpiration() {
        return idleInvoiceExpiration;
    }

    public int getReceivedVeryBadRefs() {
        return receivedVeryBadRefs;
    }

    public void setAmountDeniedInvoices(final int amountDeniedInvoices) {
        this.amountDeniedInvoices = amountDeniedInvoices;
    }

    public void setAmountIncorrectLogin(final int amountIncorrectLogin) {
        this.amountIncorrectLogin = amountIncorrectLogin;
    }

    public void setGivenVeryBadRefs(final int amountGivenVeryBadRefs) {
        givenVeryBadRefs = amountGivenVeryBadRefs;
    }

    public void setIdleInvoiceExpiration(final TimePeriod idleInvoiceExpiration) {
        this.idleInvoiceExpiration = idleInvoiceExpiration;
    }

    public void setReceivedVeryBadRefs(final int amountReceivedVeryBadRefs) {
        receivedVeryBadRefs = amountReceivedVeryBadRefs;
    }
}
