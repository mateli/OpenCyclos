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
package nl.strohalm.cyclos.services.groups;

import java.util.Collection;

import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;

public abstract class SystemGroupPermissionsDTO<G extends SystemGroup> extends GroupPermissionsDTO<G> {
    private static final long           serialVersionUID = 1L;

    private Collection<Document>        documents;
    private Collection<MessageCategory> messageCategories;

    public Collection<Document> getDocuments() {
        return documents;
    }

    public Collection<MessageCategory> getMessageCategories() {
        return messageCategories;
    }

    public void setDocuments(final Collection<Document> documents) {
        this.documents = documents;
    }

    public void setMessageCategories(final Collection<MessageCategory> messageCategories) {
        this.messageCategories = messageCategories;
    }

    @Override
    protected void updateCollections(final G systemGroup) {
        super.updateCollections(systemGroup);

        systemGroup.setDocuments(getDocuments());
        systemGroup.setMessageCategories(getMessageCategories());
    }
}
