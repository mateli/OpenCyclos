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
package nl.strohalm.cyclos.webservices.payments;

import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.SearchParameters;
import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Parameters for searching an account history
 * @author luis
 */
public class AccountHistoryParams extends SearchParameters {
    private static final long  serialVersionUID = 1L;
    private Long               paymentFilterId;
    private String             memberPrincipal;
    private Long               memberId;
    private Calendar           beginDate;
    private Calendar           endDate;
    private Boolean            showStatus       = false;
    private List<FieldValueVO> customValues;
    private Long               memberAccountId;

    public Calendar getBeginDate() {
        return beginDate;
    }

    public List<FieldValueVO> getCustomValues() {
        return customValues;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public Long getMemberAccountId() {
        return memberAccountId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberPrincipal() {
        return memberPrincipal;
    }

    public Long getPaymentFilterId() {
        return paymentFilterId;
    }

    public boolean getShowStatus() {
        return ObjectHelper.valueOf(showStatus);
    }

    public void setBeginDate(final Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public void setCustomValues(final List<FieldValueVO> customValues) {
        this.customValues = customValues;
    }

    public void setEndDate(final Calendar endDate) {
        this.endDate = endDate;
    }

    public void setMemberAccountId(final Long accountId) {
        memberAccountId = accountId;
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public void setMemberPrincipal(final String memberPrincipal) {
        this.memberPrincipal = memberPrincipal;
    }

    public void setPaymentFilterId(final Long paymentFilterId) {
        this.paymentFilterId = paymentFilterId;
    }

    public void setShowStatus(final boolean showStatus) {
        this.showStatus = showStatus;
    }

}