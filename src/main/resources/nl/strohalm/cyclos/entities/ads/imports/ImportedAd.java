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
package nl.strohalm.cyclos.entities.ads.imports;

import java.math.BigDecimal;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.Ad.TradeType;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * An imported ad, not yet processed
 * 
 * @author luis
 */
public class ImportedAd extends Entity implements CustomFieldsContainer<AdCustomField, ImportedAdCustomFieldValue> {

    public static enum Relationships implements Relationship {
        AD_IMPORT("adImport"), IMPORTED_CATEGORY("importedCategory"), EXISTING_CATEGORY("existingCategory"), CUSTOM_VALUES("customValues"), OWNER("owner");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static enum Status implements StringValuedEnum {
        SUCCESS, INVALID_CATEGORY, MISSING_CATEGORY, TOO_MANY_CATEGORY_LEVELS, INVALID_OWNER, MISSING_OWNER, MISSING_TITLE, MISSING_DESCRIPTION, MISSING_PUBLICATION_PERIOD, INVALID_PUBLICATION_START, INVALID_PUBLICATION_END, PUBLICATION_BEGIN_AFTER_END, MAX_PUBLICATION_EXCEEDED, INVALID_PRICE, INVALID_CUSTOM_FIELD, MISSING_CUSTOM_FIELD, UNKNOWN_ERROR;

        public String getValue() {
            return name();
        }
    }

    private static final long                      serialVersionUID = 2434556061835999931L;
    private AdImport                               _import;
    private Integer                                lineNumber;
    private Status                                 status;
    private String                                 errorArgument1;
    private String                                 errorArgument2;
    private AdCategory                             existingCategory;
    private ImportedAdCategory                     importedCategory;
    private Collection<ImportedAdCustomFieldValue> customValues;
    private String                                 description;
    private boolean                                html;
    private boolean                                externalPublication;
    private Member                                 owner;
    private boolean                                permanent;
    private BigDecimal                             price;
    private Period                                 publicationPeriod;
    private String                                 title;
    private TradeType                              tradeType;

    public Class<AdCustomField> getCustomFieldClass() {
        return AdCustomField.class;
    }

    public Class<ImportedAdCustomFieldValue> getCustomFieldValueClass() {
        return ImportedAdCustomFieldValue.class;
    }

    public Collection<ImportedAdCustomFieldValue> getCustomValues() {
        return customValues;
    }

    public String getDescription() {
        return description;
    }

    public String getErrorArgument1() {
        return errorArgument1;
    }

    public String getErrorArgument2() {
        return errorArgument2;
    }

    public AdCategory getExistingCategory() {
        return existingCategory;
    }

    public AdImport getImport() {
        return _import;
    }

    public ImportedAdCategory getImportedCategory() {
        return importedCategory;
    }

    public Integer getLineNumber() {
        return lineNumber;
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
        return status;
    }

    public String getTitle() {
        return title;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public boolean isExternalPublication() {
        return externalPublication;
    }

    public boolean isHtml() {
        return html;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setCustomValues(final Collection<ImportedAdCustomFieldValue> customValues) {
        this.customValues = customValues;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setErrorArgument1(final String errorArgument1) {
        this.errorArgument1 = errorArgument1;
    }

    public void setErrorArgument2(final String errorArgument2) {
        this.errorArgument2 = errorArgument2;
    }

    public void setExistingCategory(final AdCategory existingCategory) {
        this.existingCategory = existingCategory;
    }

    public void setExternalPublication(final boolean externalPublication) {
        this.externalPublication = externalPublication;
    }

    public void setHtml(final boolean html) {
        this.html = html;
    }

    public void setImport(final AdImport adImport) {
        _import = adImport;
    }

    public void setImportedCategory(final ImportedAdCategory importedCategory) {
        this.importedCategory = importedCategory;
    }

    public void setLineNumber(final Integer lineNumber) {
        this.lineNumber = lineNumber;
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

    public void setStatus(final Status status) {
        this.status = status;
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

}
