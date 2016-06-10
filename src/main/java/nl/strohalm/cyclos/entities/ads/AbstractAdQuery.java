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
package nl.strohalm.cyclos.entities.ads;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Base definitions for an advertisement query
 * @author luis
 */
public abstract class AbstractAdQuery extends QueryParameters {
    private static final long                  serialVersionUID = -20835823375349317L;
    private Long                               id;
    private AdCategory                         category;
    private List<Long>                         categoriesIds;
    private Boolean                            externalPublication;
    private boolean                            withImagesOnly;
    private BigDecimal                         finalPrice;
    private BigDecimal                         initialPrice;
    private Currency                           currency;
    private String                             keywords;
    private Collection<MemberGroup>            groups;
    private Collection<GroupFilter>            groupFilters;
    private Member                             owner;
    private Ad.Status                          status;
    private Ad.TradeType                       tradeType;
    private Collection<AdCustomFieldValue>     adValues;
    private Collection<MemberCustomFieldValue> memberValues;
    private Period                             period;
    private TimePeriod                         since;
    private boolean                            lastAds;
    private boolean                            myAds;
    private Boolean                            membersNotified;

    public Collection<AdCustomFieldValue> getAdValues() {
        return adValues;
    }

    public List<Long> getCategoriesIds() {
        return categoriesIds;
    }

    public AdCategory getCategory() {
        return category;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Boolean getExternalPublication() {
        return externalPublication;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public Collection<GroupFilter> getGroupFilters() {
        return groupFilters;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public String getKeywords() {
        return keywords;
    }

    public Boolean getMembersNotified() {
        return membersNotified;
    }

    public Collection<MemberCustomFieldValue> getMemberValues() {
        return memberValues;
    }

    public Member getOwner() {
        return owner;
    }

    public Period getPeriod() {
        return period;
    }

    public TimePeriod getSince() {
        return since;
    }

    public Ad.Status getStatus() {
        return status;
    }

    public Ad.TradeType getTradeType() {
        return tradeType;
    }

    public boolean isLastAds() {
        return lastAds;
    }

    public boolean isMyAds() {
        return myAds;
    }

    public boolean isWithImagesOnly() {
        return withImagesOnly;
    }

    public void setAdValues(final Collection<AdCustomFieldValue> adValues) {
        this.adValues = adValues;
    }

    public void setCategoriesIds(final List<Long> categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public void setCategory(final AdCategory category) {
        this.category = category;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setExternalPublication(final Boolean externalPublication) {
        this.externalPublication = externalPublication;
    }

    public void setFinalPrice(final BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setGroupFilters(final Collection<GroupFilter> groupFilters) {
        this.groupFilters = groupFilters;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setInitialPrice(final BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public void setLastAds(final boolean lastAds) {
        this.lastAds = lastAds;
    }

    public void setMembersNotified(final Boolean membersNotified) {
        this.membersNotified = membersNotified;
    }

    public void setMemberValues(final Collection<MemberCustomFieldValue> memberValues) {
        this.memberValues = memberValues;
    }

    public void setMyAds(final boolean myAds) {
        this.myAds = myAds;
    }

    public void setOwner(final Member owner) {
        this.owner = owner;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setSince(final TimePeriod since) {
        this.since = since;
    }

    public void setStatus(final Ad.Status status) {
        this.status = status;
    }

    public void setTradeType(final Ad.TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public void setWithImagesOnly(final boolean withImagesOnly) {
        this.withImagesOnly = withImagesOnly;
    }

}
