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
package nl.strohalm.cyclos.entities.members.messages;

import java.util.Collection;
import java.util.Collections;

import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * Query parameters for messages
 * @author luis
 */
public class MessageQuery extends QueryParameters {

    private static final long           serialVersionUID = -7608373541255626555L;

    private MessageBox                  messageBox;
    private Message.RootType            rootType;
    private Member                      relatedMember;
    private Element                     getter;
    private Collection<MessageCategory> categories;
    private String                      keywords;
    private Boolean                     read;

    public Collection<MessageCategory> getCategories() {
        return categories;
    }

    public MessageCategory getCategory() {
        return CollectionUtils.isEmpty(categories) ? null : categories.iterator().next();
    }

    public Element getGetter() {
        return getter;
    }

    public String getKeywords() {
        return keywords;
    }

    public MessageBox getMessageBox() {
        return messageBox;
    }

    public Boolean getRead() {
        return read;
    }

    public Member getRelatedMember() {
        return relatedMember;
    }

    public Message.RootType getRootType() {
        return rootType;
    }

    public void setCategories(final Collection<MessageCategory> categories) {
        this.categories = categories;
    }

    public void setCategory(final MessageCategory category) {
        categories = category == null ? null : Collections.singleton(category);
    }

    public void setGetter(final Element getter) {
        this.getter = getter;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public void setMessageBox(final MessageBox messageBox) {
        this.messageBox = messageBox;
    }

    public void setRead(final Boolean read) {
        this.read = read;
    }

    public void setRelatedMember(final Member member) {
        relatedMember = member;
    }

    public void setRootType(final Message.RootType rootType) {
        this.rootType = rootType;
    }
}
