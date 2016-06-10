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
package nl.strohalm.cyclos.controls.customization.documents;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DynamicDocument;
import nl.strohalm.cyclos.entities.customization.documents.MemberDocument;
import nl.strohalm.cyclos.entities.customization.documents.StaticDocument;
import nl.strohalm.cyclos.services.customization.DocumentService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a document
 * @author luis
 * @author Jefferson Magno
 */
public class RemoveDocumentAction extends BaseAction {

    private DocumentService     documentService;
    private CustomizationHelper customizationHelper;

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Inject
    public void setDocumentService(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final RemoveDocumentForm form = context.getForm();
        final long id = form.getDocumentId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        final Document document = documentService.load(id, DynamicDocument.Relationships.FORM_PAGE, DynamicDocument.Relationships.DOCUMENT_PAGE, StaticDocument.Relationships.BINARY_FILE);
        String forwardName = null;
        Long memberId = null;
        documentService.remove(id);
        if (document instanceof DynamicDocument) {
            final DynamicDocument dynamicDocument = (DynamicDocument) document;
            if (dynamicDocument.isHasFormPage()) {
                final File formFile = customizationHelper.formFile(dynamicDocument);
                customizationHelper.deleteFile(formFile);
            }
            final File docFile = customizationHelper.documentFile(dynamicDocument);
            customizationHelper.deleteFile(docFile);
            forwardName = "listDocuments";
        } else if (document instanceof MemberDocument) {
            final MemberDocument memberDocument = (MemberDocument) document;
            memberId = memberDocument.getMember().getId();
            forwardName = "selectDocument";
        } else { // document instanceof StaticDocument
            forwardName = "listDocuments";
        }
        final ActionForward forward = context.findForward(forwardName);
        context.sendMessage("document.removed");
        return ActionHelper.redirectWithParam(request, forward, "memberId", memberId);
    }

}
