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
package nl.strohalm.cyclos.services.customization;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentQuery;
import nl.strohalm.cyclos.entities.customization.documents.DynamicDocument;
import nl.strohalm.cyclos.entities.customization.documents.MemberDocument;
import nl.strohalm.cyclos.entities.customization.documents.StaticDocument;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.Group.Nature;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * Security implementation for {@link DocumentService}
 * 
 * @author jcomas
 */
public class DocumentServiceSecurity extends BaseServiceSecurity implements DocumentService {

    private DocumentServiceLocal documentService;

    @Override
    public Document load(final Long id, final Relationship... fetch) {
        Document doc = documentService.load(id, fetch);
        checkView(doc);
        return doc;
    }

    @Override
    public int remove(final Long... ids) {
        for (Long id : ids) {
            Document md = documentService.load(id);
            checkManage(md);
        }
        return documentService.remove(ids);
    }

    @Override
    public Document saveDynamic(final DynamicDocument document) {
        checkManage(document);
        return documentService.saveDynamic(document);
    }

    @Override
    public Document saveStatic(final StaticDocument document, final InputStream stream, final int size, final String fileName, final String contentType) {
        checkManage(document);
        return documentService.saveStatic(document, stream, size, fileName, contentType);
    }

    @Override
    public List<Document> search(final DocumentQuery documentQuery) {
        if (!applyQueryRestrictions(documentQuery)) {
            return Collections.emptyList();
        }
        return documentService.search(documentQuery);
    }

    public void setDocumentServiceLocal(final DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }

    @Override
    public void validate(final Document document, final boolean validateBinaryFile) {
        // Nothing to check
        documentService.validate(document, validateBinaryFile);
    }

    /**
     * Filters the natures query parameter
     */
    private boolean applyAllowedDocumentNatures(final DocumentQuery documentQuery) {
        List<Document.Nature> allowedNatures = new ArrayList<Document.Nature>();

        if (LoggedUser.isAdministrator()) {
            // Administrator through a member's profile, only member and dynamic documents
            if (documentQuery.getMember() != null) {
                allowedNatures.add(Document.Nature.DYNAMIC);
                allowedNatures.add(Document.Nature.MEMBER);
            } else {
                // Administrator viewing global documents, only dynamic and static
                allowedNatures.add(Document.Nature.DYNAMIC);
                allowedNatures.add(Document.Nature.STATIC);
            }
        } else if (LoggedUser.isMember()) {
            Member member = fetchService.fetch(documentQuery.getMember(), Member.Relationships.BROKER);
            if (LoggedUser.isBroker() && !LoggedUser.member().equals(member)) {
                // Brokers can only view member and dynamic documents of his brokered members.
                if ((member.getBroker() != null) && member.getBroker().equals(LoggedUser.member())) {
                    allowedNatures.add(Document.Nature.DYNAMIC);
                    allowedNatures.add(Document.Nature.MEMBER);
                } else { // It's the broker documents.
                    allowedNatures.add(Document.Nature.DYNAMIC);
                    allowedNatures.add(Document.Nature.MEMBER);
                    allowedNatures.add(Document.Nature.STATIC);
                }
            } else {
                // Members are allowed to view all types of documents.
                allowedNatures.add(Document.Nature.DYNAMIC);
                allowedNatures.add(Document.Nature.MEMBER);
                allowedNatures.add(Document.Nature.STATIC);
            }
        }

        Collection<Document.Nature> natures = PermissionHelper.checkSelection(allowedNatures, documentQuery.getNatures());
        documentQuery.setNatures(natures);
        return natures != null;
    }

    private boolean applyQueryRestrictions(final DocumentQuery query) {

        Collection<Group> visibleGroups = PermissionHelper.checkSelection(permissionService.getAllVisibleGroups(), query.getVisibleGroups());
        if (visibleGroups == null) {
            return false;
        }
        query.setViewer(LoggedUser.element());
        query.setVisibleGroups(visibleGroups);

        if (LoggedUser.isBroker()) {
            // If there is no member assigned and a broker is searching, then it's assumed he is searching his own documents.
            if (query.getMember() == null) {
                query.setMember(LoggedUser.member());
            }
            query.setBrokerCanViewMemberDocuments(permissionService.hasPermission(BrokerPermission.DOCUMENTS_VIEW_MEMBER));
        } else if (LoggedUser.isMember()) {
            // Members can only search for his own member documents.
            query.setMember(LoggedUser.member());
        }

        if (query.getMember() != null && !permissionService.manages(query.getMember())) {
            return false;
        }

        if (!applyAllowedDocumentNatures(query)) {
            return false;
        }
        return true;
    }

    private boolean canManage(final Document document) {
        if (document instanceof MemberDocument) {
            if (!permissionService.permission().admin(AdminMemberPermission.DOCUMENTS_MANAGE_MEMBER)
                    .broker(BrokerPermission.DOCUMENTS_MANAGE_MEMBER).hasPermission()) {
                return false;
            } else {
                return permissionService.manages(((MemberDocument) document).getMember());
            }
        } else if (document instanceof StaticDocument) {
            return permissionService.permission().admin(AdminMemberPermission.DOCUMENTS_MANAGE_STATIC).hasPermission();
        } else { // DynamicDocument
            return permissionService.permission().admin(AdminMemberPermission.DOCUMENTS_MANAGE_DYNAMIC).hasPermission();
        }
    }

    private void checkManage(final Document document) {
        if (!canManage(document)) {
            throw new PermissionDeniedException();
        }
    }

    private void checkView(final Document doc) {

        if (doc instanceof MemberDocument) {
            MemberDocument mDoc = (MemberDocument) doc;
            mDoc = fetchService.fetch(mDoc, MemberDocument.Relationships.MEMBER);
            final MemberDocument.Visibility visibility = mDoc.getVisibility();

            // Check the visibility of the document
            if (((LoggedUser.group().getNature() == Nature.MEMBER) && visibility != MemberDocument.Visibility.MEMBER) || (LoggedUser.isBroker() && visibility == MemberDocument.Visibility.ADMIN)) {
                throw new PermissionDeniedException();
            }

            // Check the permission
            permissionService.permission(mDoc.getMember())
                    .admin(AdminMemberPermission.DOCUMENTS_MANAGE_MEMBER)
                    .broker(BrokerPermission.DOCUMENTS_VIEW_MEMBER)
                    .member()
                    .check();

        } else {
            // Check whether the document can be viewed by the logged user
            permissionService.permission()
                    .adminFor(AdminMemberPermission.DOCUMENTS_DETAILS, doc)
                    .brokerFor(BrokerPermission.DOCUMENTS_VIEW, doc)
                    .memberFor(MemberPermission.DOCUMENTS_VIEW, doc).check();
        }
    }

}
