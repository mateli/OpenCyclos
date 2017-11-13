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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Indexable;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.images.AdImage;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.utils.Period;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;
import nl.strohalm.cyclos.utils.StringValuedEnum;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

/**
 * A product / service advertisement by a member
 * @author luis
 */
@Table(name = "ads")
@javax.persistence.Entity
public class Ad extends Entity implements CustomFieldsContainer<AdCustomField, AdCustomFieldValue>, Indexable {

    public static enum Relationships implements Relationship {
        CATEGORY("category"), CURRENCY("currency"), CUSTOM_VALUES("customValues"), IMAGES("images"), OWNER("owner");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static enum Status {
        ACTIVE(true), PERMANENT(true), SCHEDULED(false), EXPIRED(false);
        private final boolean active;

        private Status(final boolean active) {
            this.active = active;
        }

        public boolean isActive() {
            return active;
        }
    }

    public static enum TradeType implements StringValuedEnum {
        OFFER("O"), SEARCH("S");
        private final String value;

        private TradeType(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final long              serialVersionUID = 1552286239776108655L;

    @ManyToOne
    @JoinColumn(name = "category_id")
	private AdCategory                     category;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.REMOVE)
	private Collection<AdCustomFieldValue> customValues;

    @Column(name = "description", columnDefinition = "text")
    private String                         description;

    @Column(name = "external_publication", nullable = false)
    private boolean                        externalPublication;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.REMOVE)
	private Collection<AdImage>            images;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
	private Member                         owner;

    @Column(name = "permanent", nullable = false)
    private boolean                        permanent;

    @ManyToOne
    @JoinColumn(name = "currency_id")
	private Currency                       currency;

    @Column(name = "price", precision = 15, scale = 6)
    private BigDecimal                     price;

    @AttributeOverrides({
            @AttributeOverride(name = "begin", column=@Column(name="publication_start")),
            @AttributeOverride(name = "end", column=@Column(name="publication_end"))
    })
    @Embedded
	private Period                         publicationPeriod;

    @Column(name = "title", nullable = false, length = 100)
    private String                         title;

    @Convert(converter = AdTradeTypeAttributeConverter.class)
    @Column(name = "trade_type", nullable = false, length = 1)
	private TradeType                      tradeType;

    @Column(name = "creation_date", updatable = false)
    private Calendar                       creationDate;

    @Column(name = "delete_date")
    private Calendar                       deleteDate;

    @Column(name = "is_html", nullable = false)
    private boolean                        html;

    @Column(name = "members_notified", nullable = false)
    private boolean                        membersNotified;

	public AdCategory getCategory() {
        return category;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public Class<AdCustomField> getCustomFieldClass() {
        return AdCustomField.class;
    }

    @Override
    public Class<AdCustomFieldValue> getCustomFieldValueClass() {
        return AdCustomFieldValue.class;
    }

    @Override
    public Collection<AdCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public Calendar getDeleteDate() {
        return deleteDate;
    }

    public String getDescription() {
        return description;
    }

    public Collection<AdImage> getImages() {
        return images;
    }

    public Member getOwner() {
        return owner;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Period getPublicationPeriod() {
        return publicationPeriod;
    }

    public Status getStatus() {
        if (permanent) {
            return Status.PERMANENT;
        } else {
            final Calendar begin = publicationPeriod == null ? null : publicationPeriod.getBegin();
            final Calendar end = publicationPeriod == null ? null : publicationPeriod.getEnd();
            final Calendar now = Calendar.getInstance();
            if (begin != null && begin.after(now)) {
                return Status.SCHEDULED;
            } else if (end != null && end.before(now)) {
                return Status.EXPIRED;
            }
        }
        return Status.ACTIVE;
    }

    public String getTitle() {
        return title;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public boolean isDeleted() {
        return getDeleteDate() != null;
    }

    public boolean isExternalPublication() {
        return externalPublication;
    }

    public boolean isHasImages() {
        return CollectionUtils.isNotEmpty(getImages());
    }

    public boolean isHtml() {
        return html;
    }

    public boolean isMembersNotified() {
        return membersNotified;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setCategory(final AdCategory category) {
        this.category = category;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    @Override
    public void setCustomValues(final Collection<AdCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setDeleteDate(final Calendar deleteDate) {
        this.deleteDate = deleteDate;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setExternalPublication(final boolean externalPublication) {
        this.externalPublication = externalPublication;
    }

    public void setHtml(final boolean html) {
        this.html = html;
    }

    public void setImages(final Collection<AdImage> images) {
        this.images = images;
    }

    public void setMembersNotified(final boolean notified) {
        membersNotified = notified;
    }

    public void setOwner(final Member owner) {
        this.owner = owner;
    }

    public void setPermanent(final boolean permanent) {
        this.permanent = permanent;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public void setPublicationPeriod(final Period publicationPeriod) {
        this.publicationPeriod = publicationPeriod;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setTradeType(final TradeType tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public String toString() {
        return getId() + " - " + title;
    }

    @Override
    protected void appendVariableValues(final Map<String, Object> variables, final LocalSettings localSettings) {
        variables.put("title", getTitle());
        variables.put("price", localSettings.getNumberConverter().toString(getPrice()));
    }
}
