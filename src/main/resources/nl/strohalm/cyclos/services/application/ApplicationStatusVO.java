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
package nl.strohalm.cyclos.services.application;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Return class for the statistical data regarding application status.
 * @author rafael
 */
public class ApplicationStatusVO extends DataObject {

    private static final long serialVersionUID = 9199781548603379611L;
    private int               connectedAdmins;
    private int               connectedMembers;
    private int               connectedBrokers;
    private int               connectedOperators;
    private String            cyclosVersion;
    private int               memberAlerts;
    private int               systemAlerts;
    private int               errors;
    private int               uptimeDays;
    private int               uptimeHours;
    private int               unreadMessages;
    private int               openInvoices;

    public int getConnectedAdmins() {
        return connectedAdmins;
    }

    public int getConnectedBrokers() {
        return connectedBrokers;
    }

    public int getConnectedMembers() {
        return connectedMembers;
    }

    public int getConnectedOperators() {
        return connectedOperators;
    }

    public String getCyclosVersion() {
        return cyclosVersion;
    }

    public int getErrors() {
        return errors;
    }

    public int getMemberAlerts() {
        return memberAlerts;
    }

    public int getOpenInvoices() {
        return openInvoices;
    }

    public int getSystemAlerts() {
        return systemAlerts;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public int getUptimeDays() {
        return uptimeDays;
    }

    public int getUptimeHours() {
        return uptimeHours;
    }

    public void setConnectedAdmins(final int connectedAdmins) {
        this.connectedAdmins = connectedAdmins;
    }

    public void setConnectedBrokers(final int connectedBrokers) {
        this.connectedBrokers = connectedBrokers;
    }

    public void setConnectedMembers(final int connectedMembers) {
        this.connectedMembers = connectedMembers;
    }

    public void setConnectedOperators(final int connectedOperators) {
        this.connectedOperators = connectedOperators;
    }

    public void setCyclosVersion(final String cyclosVersion) {
        this.cyclosVersion = cyclosVersion;
    }

    public void setErrors(final int errors) {
        this.errors = errors;
    }

    public void setMemberAlerts(final int memberAlerts) {
        this.memberAlerts = memberAlerts;
    }

    public void setOpenInvoices(final int openInvoices) {
        this.openInvoices = openInvoices;
    }

    public void setSystemAlerts(final int systemAlerts) {
        this.systemAlerts = systemAlerts;
    }

    public void setUnreadMessages(final int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public void setUptimeDays(final int uptimeDays) {
        this.uptimeDays = uptimeDays;
    }

    public void setUptimeHours(final int uptimeHour) {
        uptimeHours = uptimeHour;
    }
}
