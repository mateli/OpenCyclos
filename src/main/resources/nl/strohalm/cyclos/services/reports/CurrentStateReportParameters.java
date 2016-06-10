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
package nl.strohalm.cyclos.services.reports;

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.MemberGroup;

public class CurrentStateReportParameters {

    public enum TimePointType {
        TIME_POINT_CURRENT, TIME_POINT_HISTORY;
    }

    private boolean                 ads                      = false;
    private boolean                 invoices                 = false;
    private boolean                 loans                    = false;
    private boolean                 memberAccountInformation = false;
    private boolean                 memberGroupInformation   = false;
    private boolean                 references               = false;
    private boolean                 systemAccountInformation = false;
    private Calendar                timePoint;
    private TimePointType           timePointType            = TimePointType.TIME_POINT_CURRENT;
    private Collection<MemberGroup> memberGroups;

    public Collection<MemberGroup> getMemberGroups() {
        return memberGroups;
    }

    public Calendar getTimePoint() {
        return timePoint;
    }

    public TimePointType getTimePointType() {
        return timePointType;
    }

    public boolean isAds() {
        return ads;
    }

    public boolean isInvoices() {
        return invoices;
    }

    public boolean isLoans() {
        return loans;
    }

    public boolean isMemberAccountInformation() {
        return memberAccountInformation;
    }

    public boolean isMemberGroupInformation() {
        return memberGroupInformation;
    }

    public boolean isNothing() {
        return !ads && !memberGroupInformation && !memberAccountInformation && !references && !systemAccountInformation && !invoices && !loans;
    }

    public boolean isReferences() {
        return references;
    }

    public boolean isSystemAccountInformation() {
        return systemAccountInformation;
    }

    public void setAds(final boolean ads) {
        this.ads = ads;
    }

    public void setInvoices(final boolean invoices) {
        this.invoices = invoices;
    }

    public void setLoans(final boolean loans) {
        this.loans = loans;
    }

    public void setMemberAccountInformation(final boolean memberAccountInformation) {
        this.memberAccountInformation = memberAccountInformation;
    }

    public void setMemberGroupInformation(final boolean memberGroupInformation) {
        this.memberGroupInformation = memberGroupInformation;
    }

    public void setMemberGroups(final Collection<MemberGroup> memberGroups) {
        this.memberGroups = memberGroups;
    }

    public void setReferences(final boolean references) {
        this.references = references;
    }

    public void setSystemAccountInformation(final boolean systemAccountInformation) {
        this.systemAccountInformation = systemAccountInformation;
    }

    public void setTimePoint(final Calendar timePoint) {
        this.timePoint = timePoint;
    }

    public void setTimePointType(final TimePointType timePointType) {
        this.timePointType = timePointType;
    }

}
