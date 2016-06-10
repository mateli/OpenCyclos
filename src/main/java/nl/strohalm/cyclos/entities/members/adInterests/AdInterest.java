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
package nl.strohalm.cyclos.entities.members.adInterests;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.members.Member;

public class AdInterest extends Entity {

    public static enum Relationships implements Relationship {
        MEMBER("member"), CATEGORY("category"), CURRENCY("currency");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 7809184121372381237L;

    private Member            owner;
    private String            title;
    private Ad.TradeType      type;
    private AdCategory        category;
    private Member            member;
    private GroupFilter       groupFilter;
    private BigDecimal        initialPrice;
    private BigDecimal        finalPrice;
    private Currency          currency;
    private String            keywords;

    public AdCategory getCategory() {
        return category;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public GroupFilter getGroupFilter() {
        return groupFilter;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public String getKeywords() {
        return keywords;
    }

    public Member getMember() {
        return member;
    }

    public Member getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public Ad.TradeType getType() {
        return type;
    }

    public void setCategory(final AdCategory category) {
        this.category = category;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public void setFinalPrice(final BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setGroupFilter(final GroupFilter groupFilter) {
        this.groupFilter = groupFilter;
    }

    public void setInitialPrice(final BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setOwner(final Member owner) {
        this.owner = owner;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setType(final Ad.TradeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return title != null ? title : "";
    }

}
