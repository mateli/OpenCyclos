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
package nl.strohalm.cyclos.webservices.accounts;

import java.io.Serializable;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Parameters for searching transfer types via web services
 * @author luis
 */
public class TransferTypeSearchParameters implements Serializable {
    private static final long serialVersionUID = -2353662110535076676L;
    private String            currency;
    private String            fromMemberPrincipalType;
    private String            fromMember;
    private Long              fromAccountTypeId;
    private String            toMemberPrincipalType;
    private String            toMember;
    private Long              toAccountTypeId;
    private Boolean           toSystem         = false;
    private Boolean           fromSystem       = false;

    public String getCurrency() {
        return currency;
    }

    public Long getFromAccountTypeId() {
        return fromAccountTypeId;
    }

    public String getFromMember() {
        return fromMember;
    }

    public String getFromMemberPrincipalType() {
        return fromMemberPrincipalType;
    }

    public boolean getFromSystem() {
        return ObjectHelper.valueOf(fromSystem);
    }

    public Long getToAccountTypeId() {
        return toAccountTypeId;
    }

    public String getToMember() {
        return toMember;
    }

    public String getToMemberPrincipalType() {
        return toMemberPrincipalType;
    }

    public boolean getToSystem() {
        return ObjectHelper.valueOf(toSystem);
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setFromAccountTypeId(final Long fromAccountTypeId) {
        this.fromAccountTypeId = fromAccountTypeId;
    }

    public void setFromMember(final String fromMember) {
        this.fromMember = fromMember;
    }

    public void setFromMemberPrincipalType(final String fromMemberPrincipalType) {
        this.fromMemberPrincipalType = fromMemberPrincipalType;
    }

    public void setFromSystem(final boolean fromSystem) {
        this.fromSystem = fromSystem;
    }

    public void setToAccountTypeId(final Long toAccountTypeId) {
        this.toAccountTypeId = toAccountTypeId;
    }

    public void setToMember(final String toMember) {
        this.toMember = toMember;
    }

    public void setToMemberPrincipalType(final String toMemberPrincipalType) {
        this.toMemberPrincipalType = toMemberPrincipalType;
    }

    public void setToSystem(final boolean toSystem) {
        this.toSystem = toSystem;
    }

    @Override
    public String toString() {
        return "TransferTypeSearchParameters [currency=" + currency + ", fromAccountTypeId=" + fromAccountTypeId + ", fromMember=" + fromMember + ", fromMemberPrincipalType=" + fromMemberPrincipalType + ", fromSystem=" + fromSystem + ", toAccountTypeId=" + toAccountTypeId + ", toMember=" + toMember + ", toMemberPrincipalType=" + toMemberPrincipalType + ", toSystem=" + toSystem + "]";
    }
}
