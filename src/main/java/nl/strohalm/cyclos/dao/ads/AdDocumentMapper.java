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
package nl.strohalm.cyclos.dao.ads;

import java.util.Calendar;
import java.util.GregorianCalendar;

import nl.strohalm.cyclos.dao.members.MemberDocumentMapper;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Indexing;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.lucene.AbstractDocumentMapper;
import nl.strohalm.cyclos.utils.lucene.DocumentBuilder;

import org.apache.lucene.document.Document;

/**
 * Maps {@link Ad}s to lucene {@link Document}s
 * 
 * @author luis
 */
public class AdDocumentMapper extends AbstractDocumentMapper<Ad> {

    private static final Calendar PUBLICATION_BEGIN_WHEN_NULL = new GregorianCalendar(1900, 0, 1);
    private static final Calendar PUBLICATION_END_WHEN_NULL   = new GregorianCalendar(2900, 0, 1);

    @Override
    protected DocumentBuilder newDocumentBuilder() {
        return new DocumentBuilder(fetchDao) {
            @Override
            protected boolean includeInKeywordsSearch(final String name, final CustomFieldValue fieldValue) {
                if ("owner.customValues".equals(name)) {
                    return AdDocumentMapper.this.includeInKeywordsSearch((MemberCustomFieldValue) fieldValue);
                } else {
                    return AdDocumentMapper.this.includeInKeywordsSearch((AdCustomFieldValue) fieldValue);
                }
            }

            @Override
            protected boolean includeInSpecificFiltering(final String name, final CustomFieldValue fieldValue) {
                if ("owner.customValues".equals(name)) {
                    return AdDocumentMapper.this.includeInSpecificFiltering((MemberCustomFieldValue) fieldValue);
                } else {
                    return AdDocumentMapper.this.includeInSpecificFiltering((AdCustomFieldValue) fieldValue);
                }
            }
        };
    }

    @Override
    protected void process(final DocumentBuilder document, final Ad ad) {
        // Add the category hierarchy
        AdCategory category = ad.getCategory();
        while (category != null) {
            document.add("category", category);
            category = category.getParent();
        }
        String description = ad.getDescription();
        if (ad.isHtml()) {
            // Ensure html tags / entities are not stored in the description
            description = StringHelper.removeMarkupTagsAndUnescapeEntities(description);
        }

        // Check if the ad has images
        final boolean hasImages = ((Number) getSession().createFilter(ad.getImages(), "select count(*)").uniqueResult()).intValue() > 0;

        // Get the publication period
        Calendar publicationBegin = ad.getPublicationPeriod() == null ? null : ad.getPublicationPeriod().getBegin();
        if (publicationBegin == null) {
            publicationBegin = PUBLICATION_BEGIN_WHEN_NULL;
        }
        Calendar publicationEnd = ad.getPublicationPeriod() == null ? null : ad.getPublicationPeriod().getEnd();
        if (publicationEnd == null) {
            publicationEnd = PUBLICATION_END_WHEN_NULL;
        }
        Calendar baseDate = publicationBegin == null ? ad.getCreationDate() : publicationBegin;
        final Member owner = ad.getOwner();

        document.add("tradeType", ad.getTradeType());
        document.add("title", ad.getTitle());
        document.add("description", description);
        document.add("price", ad.getPrice());
        document.add("currency", ad.getCurrency());
        document.add("permanent", ad.isPermanent());
        document.add("baseDate", baseDate);
        document.add("externalPublication", ad.isExternalPublication());
        document.add("publicationBegin", publicationBegin);
        document.add("publicationEnd", publicationEnd);
        document.add("customValues", ad.getCustomValues());
        document.add("hasImages", hasImages);
        document.add("membersNotified", ad.isMembersNotified());
        document.add("owner", owner);
        document.add("owner.group", owner.getGroup());
        document.add("owner.name", owner.getName());
        document.add("owner.username", owner.getUsername());
        document.add("owner.customValues", owner.getCustomValues());
    }

    /**
     * Ad fields are included for keywords search depending on {@link AdCustomField#isIndexed()}
     */
    private boolean includeInKeywordsSearch(final AdCustomFieldValue fieldValue) {
        AdCustomField field = (AdCustomField) fieldValue.getField();
        return field.isIndexed();
    }

    /**
     * Member fields are included for keywords search if the indexing is {@link Indexing#MEMBERS_AND_ADS} and the owner hasn't hidden it
     */
    private boolean includeInKeywordsSearch(final MemberCustomFieldValue fieldValue) {
        MemberCustomField field = (MemberCustomField) fieldValue.getField();
        return field.getIndexing() == Indexing.MEMBERS_AND_ADS && !fieldValue.isHidden();
    }

    /**
     * Ad fields are included for keywords search depending on {@link AdCustomField#isShowInSearch()}
     */
    private boolean includeInSpecificFiltering(final AdCustomFieldValue fieldValue) {
        AdCustomField field = (AdCustomField) fieldValue.getField();
        return field.isShowInSearch();
    }

    /**
     * Member fields are included for specific filtering depending on the ad search access property
     */
    private boolean includeInSpecificFiltering(final MemberCustomFieldValue fieldValue) {
        MemberCustomField field = (MemberCustomField) fieldValue.getField();
        return MemberDocumentMapper.ACCESS_FOR_FILTERING.contains(field.getAdSearchAccess());
    }
}
