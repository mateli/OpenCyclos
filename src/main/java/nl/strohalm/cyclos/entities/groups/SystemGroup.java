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
package nl.strohalm.cyclos.entities.groups;

import java.util.ArrayList;
import java.util.Collection;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;

/**
 * Base class for all system-wide groups, that is, the groups that are managed by a system administrator.<br>
 * <b>Groups categories:</b>
 * <ul>
 * <li>system-wide: member, admin and broker groups.</li>
 * <li>Others: operator groups (these groups are managed by the owner member).</li>
 * </ul>
 * 
 * @author ameyer
 */
public abstract class SystemGroup extends Group {
    public static enum Relationships implements Relationship {
        DOCUMENTS("documents"), MESSAGE_CATEGORIES("messageCategories"), CHARGEBACK_TRANSFER_TYPES("chargebackTransferTypes");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long           serialVersionUID = 1L;
    private String                      rootUrl;
    private String                      loginPageName;
    private String                      containerUrl;
    private Collection<Document>        documents;
    private Collection<MessageCategory> messageCategories;
    private Collection<TransferType>    chargebackTransferTypes;

    public void addDocument(final Document document) {
        if (documents == null) {
            documents = new ArrayList<Document>();
        }

        documents.add(document);
    }

    public Collection<TransferType> getChargebackTransferTypes() {
        return chargebackTransferTypes;
    }

    public String getContainerUrl() {
        return containerUrl;
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public String getLoginPageName() {
        return loginPageName;
    }

    public Collection<MessageCategory> getMessageCategories() {
        return messageCategories;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setChargebackTransferTypes(final Collection<TransferType> chargebackTransferTypes) {
        this.chargebackTransferTypes = chargebackTransferTypes;
    }

    public void setContainerUrl(final String containerUrl) {
        this.containerUrl = containerUrl;
    }

    public void setDocuments(final Collection<Document> documents) {
        this.documents = documents;
    }

    public void setLoginPageName(final String loginPageName) {
        this.loginPageName = loginPageName;
    }

    public void setMessageCategories(final Collection<MessageCategory> messageCategories) {
        this.messageCategories = messageCategories;
    }

    public void setRootUrl(final String rootUrl) {
        this.rootUrl = rootUrl;
    }

}
