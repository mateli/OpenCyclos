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
package nl.strohalm.cyclos.dao.members;

import java.util.Arrays;
import java.util.List;

import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Access;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Indexing;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.lucene.DocumentBuilder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.document.Document;

/**
 * Maps {@link Member}s to lucene {@link Document}s
 * 
 * @author luis
 */
public class MemberDocumentMapper extends ElementDocumentMapper<Member> {

    public static final List<Access> ACCESS_FOR_FILTERING = Arrays.asList(Access.WEB_SERVICE, Access.ADMIN, Access.BROKER, Access.MEMBER);

    @Override
    protected boolean indexEmail(final Member member) {
        return super.indexEmail(member) && !member.isHideEmail();
    }

    @Override
    protected DocumentBuilder newDocumentBuilder() {
        return new DocumentBuilder(fetchDao) {
            @Override
            protected boolean includeInKeywordsSearch(final String name, final CustomFieldValue fieldValue) {
                return MemberDocumentMapper.this.includeInKeywordsSearch((MemberCustomFieldValue) fieldValue);
            }

            @Override
            protected boolean includeInSpecificFiltering(final String name, final CustomFieldValue fieldValue) {
                return MemberDocumentMapper.this.includeInSpecificFiltering((MemberCustomFieldValue) fieldValue);
            }
        };
    }

    @Override
    protected void process(final DocumentBuilder document, Member member) {
        member = fetchDao.fetch(member, Member.Relationships.IMAGES);
        final boolean hasImages = CollectionUtils.isNotEmpty(member.getImages());

        super.process(document, member);
        document.add("activationDate", member.getActivationDate());
        document.add("broker", member.getBroker());
        document.add("hasImages", hasImages);
    }

    /**
     * A member field is included in keywords search if the field indexing is either {@link Indexing#MEMBERS_ONLY} or {@link Indexing#MEMBERS_AND_ADS}
     * and the field value is not hidden by the member
     */
    private boolean includeInKeywordsSearch(final MemberCustomFieldValue fieldValue) {
        MemberCustomField field = (MemberCustomField) fieldValue.getField();
        return field.getIndexing() != Indexing.NONE && !fieldValue.isHidden();
    }

    /**
     * A member field is included in specific field filtering if it is marked to show in search for members, brokers or admins
     */
    private boolean includeInSpecificFiltering(final MemberCustomFieldValue fieldValue) {
        MemberCustomField field = (MemberCustomField) fieldValue.getField();
        return ACCESS_FOR_FILTERING.contains(field.getMemberSearchAccess());
    }

}
