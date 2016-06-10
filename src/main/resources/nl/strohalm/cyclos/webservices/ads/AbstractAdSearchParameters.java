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
package nl.strohalm.cyclos.webservices.ads;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.SearchParameters;
import nl.strohalm.cyclos.webservices.model.TimePeriodVO;
import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Base class for parameters for searching ads via web services
 * @author luis
 */
public abstract class AbstractAdSearchParameters extends SearchParameters {

    @XmlType(name = "status")
    public static enum AdVOStatus {
        ACTIVE, PERMANENT, SCHEDULED, EXPIRED;
    }

    /**
     * Trade types for searching
     * @author luis
     */
    @XmlType(name = "tradeType")
    public static enum AdVOTradeType {
        OFFER, SEARCH, BOTH;
    }

    private static final long  serialVersionUID = 573855204760203137L;
    private String             keywords;
    private BigDecimal         initialPrice;
    private BigDecimal         finalPrice;
    private List<FieldValueVO> adFields;
    private Long               memberId;
    private List<FieldValueVO> memberFields;
    private List<Long>         memberGroupIds;
    private List<Long>         memberGroupFilterIds;
    private Long               categoryId;
    private TimePeriodVO       since;
    private Calendar           beginDate;
    private Calendar           endDate;
    private AdVOTradeType      tradeType;
    private AdVOStatus         status;
    private Boolean            withImagesOnly   = false;
    private Boolean            showAdFields     = false;
    private Boolean            showMemberFields = false;

    public List<FieldValueVO> getAdFields() {
        return adFields;
    }

    public Calendar getBeginDate() {
        return beginDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public String getKeywords() {
        return keywords;
    }

    public List<FieldValueVO> getMemberFields() {
        return memberFields;
    }

    public List<Long> getMemberGroupFilterIds() {
        return memberGroupFilterIds;
    }

    public List<Long> getMemberGroupIds() {
        return memberGroupIds;
    }

    public Long getMemberId() {
        return memberId;
    }

    public boolean getShowAdFields() {
        return ObjectHelper.valueOf(showAdFields);
    }

    public boolean getShowMemberFields() {
        return ObjectHelper.valueOf(showMemberFields);
    }

    public TimePeriodVO getSince() {
        return since;
    }

    public AdVOStatus getStatus() {
        return status;
    }

    public AdVOTradeType getTradeType() {
        return tradeType;
    }

    public boolean getWithImagesOnly() {
        return ObjectHelper.valueOf(withImagesOnly);
    }

    public void setAdFields(final List<FieldValueVO> adFields) {
        this.adFields = adFields;
    }

    public void setBeginDate(final Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public void setCategoryId(final Long categoryId) {
        if (categoryId != null && categoryId > 0) {
            this.categoryId = categoryId;
        }
    }

    public void setEndDate(final Calendar endDate) {
        this.endDate = endDate;
    }

    public void setFinalPrice(final BigDecimal finalPrice) {
        if (finalPrice != null && finalPrice.compareTo(new BigDecimal(0)) == 1) {
            this.finalPrice = finalPrice;
        }
    }

    public void setInitialPrice(final BigDecimal initialPrice) {
        if (initialPrice != null && initialPrice.compareTo(new BigDecimal(0)) == 1) {
            this.initialPrice = initialPrice;
        }
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public void setMemberFields(final List<FieldValueVO> memberFields) {
        this.memberFields = memberFields;
    }

    public void setMemberGroupFilterIds(final List<Long> memberGroupFilterIds) {
        this.memberGroupFilterIds = memberGroupFilterIds;
    }

    public void setMemberGroupIds(final List<Long> memberGroupIds) {
        this.memberGroupIds = memberGroupIds;
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public void setShowAdFields(final boolean showAdFields) {
        this.showAdFields = showAdFields;
    }

    public void setShowMemberFields(final boolean showMemberFields) {
        this.showMemberFields = showMemberFields;
    }

    public void setSince(final TimePeriodVO since) {
        this.since = since;
    }

    public void setStatus(final AdVOStatus status) {
        this.status = status;
    }

    public void setTradeType(final AdVOTradeType tradeType) {
        this.tradeType = tradeType;
    }

    public void setWithImagesOnly(final boolean withImagesOnly) {
        this.withImagesOnly = withImagesOnly;
    }

    @Override
    public String toString() {
        return "AbstractAdSearchParameters [adFields=" + adFields + ", categoryId=" + categoryId + ", finalPrice=" + finalPrice + ", initialPrice=" + initialPrice + ", keywords=" + keywords + ", memberFields=" + memberFields + ", memberGroupIds=" + memberGroupIds + ", memberGroupFilterIds=" + memberGroupFilterIds + ", memberId=" + memberId + ", showAdFields=" + showAdFields + ", showMemberFields=" + showMemberFields + ", since=" + since + ", tradeType=" + tradeType + ", withImagesOnly=" + withImagesOnly + ", " + super.toString() + "]";
    }
}
