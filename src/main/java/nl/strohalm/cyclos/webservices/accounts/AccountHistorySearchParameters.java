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

import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.SearchParameters;
import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Parameters for retrieving and account history
 * @author luis
 */
public class AccountHistorySearchParameters extends SearchParameters {
    private static final long  serialVersionUID = 3508395169859539803L;
    private String             principalType;
    private String             principal;
    private Long               accountTypeId;
    private String             currency;
    private String             relatedMemberPrincipalType;
    private String             relatedMember;
    private Calendar           beginDate;
    private Calendar           endDate;
    private List<FieldValueVO> fields;
    private Boolean            reverseOrder     = false;
    private String             credentials;

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public Calendar getBeginDate() {
        return beginDate;
    }

    public String getCredentials() {
        return credentials;
    }

    public String getCurrency() {
        return currency;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public List<FieldValueVO> getFields() {
        return fields;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPrincipalType() {
        return principalType;
    }

    public String getRelatedMember() {
        return relatedMember;
    }

    public String getRelatedMemberPrincipalType() {
        return relatedMemberPrincipalType;
    }

    @XmlTransient
    public boolean getReverseOrder() {
        return ObjectHelper.valueOf(reverseOrder);
    }

    public void setAccountTypeId(final Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setBeginDate(final Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public void setCredentials(final String credentials) {
        this.credentials = credentials;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setEndDate(final Calendar endDate) {
        this.endDate = endDate;
    }

    public void setFields(final List<FieldValueVO> fields) {
        this.fields = fields;
    }

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }

    public void setPrincipalType(final String principalType) {
        this.principalType = principalType;
    }

    public void setRelatedMember(final String relatedMember) {
        this.relatedMember = relatedMember;
    }

    public void setRelatedMemberPrincipalType(final String relatedMemberPrincipalType) {
        this.relatedMemberPrincipalType = relatedMemberPrincipalType;
    }

    public void setReverseOrder(final boolean reverseOrder) {
        this.reverseOrder = reverseOrder;
    }

    @Override
    public String toString() {
        return "AccountHistorySearchParameters [accountTypeId=" + accountTypeId + ", beginDate=" + beginDate + ", credentials=****" + ", currency=" + currency + ", endDate=" + endDate + ", fields=" + fields + ", principal=" + principal + ", principalType=" + principalType + ", relatedMember=" + relatedMember + ", relatedMemberPrincipalType=" + relatedMemberPrincipalType + ", reverseOrder=" + reverseOrder + ", " + super.toString() + "]";
    }
}
