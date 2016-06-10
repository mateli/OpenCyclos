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
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentPage;
import nl.strohalm.cyclos.entities.customization.documents.DynamicDocument;
import nl.strohalm.cyclos.services.customization.DocumentService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a dynamic document
 * @author luis
 * @author Jefferson Magno
 */
public class EditDynamicDocumentAction extends BaseFormAction {

    private DocumentService             documentService;
    private DataBinder<DynamicDocument> dataBinder;
    private CustomizationHelper         customizationHelper;

    public DataBinder<DynamicDocument> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<DocumentPage> formPageBinder = BeanBinder.instance(DocumentPage.class, "formPage");
            formPageBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            formPageBinder.registerBinder("contents", PropertyBinder.instance(String.class, "contents", CoercionConverter.instance(String.class)));

            final BeanBinder<DocumentPage> documentPageBinder = BeanBinder.instance(DocumentPage.class, "documentPage");
            documentPageBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            documentPageBinder.registerBinder("contents", PropertyBinder.instance(String.class, "contents", CoercionConverter.instance(String.class)));

            final BeanBinder<DynamicDocument> binder = BeanBinder.instance(DynamicDocument.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("formPage", formPageBinder);
            binder.registerBinder("documentPage", documentPageBinder);
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Inject
    public void setDocumentService(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditDynamicDocumentForm form = context.getForm();
        DynamicDocument document = getDataBinder().readFromString(form.getDocument());
        final boolean isInsert = document.getId() == null;
        document = (DynamicDocument) documentService.saveDynamic(document);

        // Physically update the form and document pages
        if (document.isHasFormPage()) {
            final File formFile = customizationHelper.formFile(document);
            customizationHelper.updateFile(formFile, document.getFormPage());
        }

        final File documentFile = customizationHelper.documentFile(document);
        customizationHelper.updateFile(documentFile, document.getDocumentPage());
        context.sendMessage(isInsert ? "document.inserted" : "document.modified");
        return ActionHelper.redirectWithParam(request, context.getSuccessForward(), "documentId", document.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditDynamicDocumentForm form = context.getForm();
        final long id = form.getDocumentId();
        DynamicDocument document;
        if (id > 0L) {
            document = (DynamicDocument) documentService.load(id, DynamicDocument.Relationships.FORM_PAGE, DynamicDocument.Relationships.DOCUMENT_PAGE);
        } else {
            document = new DynamicDocument();
        }
        getDataBinder().writeAsString(form.getDocument(), document);
        request.setAttribute("document", document);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditDynamicDocumentForm form = context.getForm();
        final Document document = retrieveDocument(form);
        documentService.validate(document, false);
    }

    private DynamicDocument retrieveDocument(final EditDynamicDocumentForm form) {
        final DynamicDocument document = getDataBinder().readFromString(form.getDocument());
        final DocumentPage formPage = document.getFormPage();
        if (formPage == null || StringUtils.isEmpty(formPage.getContents())) {
            document.setFormPage(null);
        } else {
            final File formFile = customizationHelper.formFile(document);
            formPage.setName(formFile.getName());
        }
        final DocumentPage documentPage = document.getDocumentPage();
        if (documentPage == null || StringUtils.isEmpty(documentPage.getContents())) {
            document.setDocumentPage(null);
        } else {
            final File documentFile = customizationHelper.documentFile(document);
            documentPage.setName(documentFile.getName());
        }
        return document;
    }

}
