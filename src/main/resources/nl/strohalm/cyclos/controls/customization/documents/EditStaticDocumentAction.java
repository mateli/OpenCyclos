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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.StaticDocument;
import nl.strohalm.cyclos.services.customization.DocumentService;
import nl.strohalm.cyclos.services.customization.exceptions.CannotUploadFileException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to edit a static document
 * @author Jefferson Magno
 */
public class EditStaticDocumentAction extends BaseFormAction {

    public static <T extends StaticDocument> BeanBinder<T> getDataBinder(final Class<T> type) {
        final BeanBinder<T> binder = BeanBinder.instance(type);
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
        return binder;
    }

    protected DocumentService          documentService;

    private DataBinder<StaticDocument> dataBinder;

    @Inject
    public void setDocumentService(final DocumentService documentService) {
        this.documentService = documentService;
    }

    protected Class<? extends StaticDocument> getEntityType() {
        return StaticDocument.class;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditStaticDocumentForm form = context.getForm();
        StaticDocument document = getDataBinder().readFromString(form.getDocument());
        final boolean isInsert = document.getId() == null;
        try {
            final FormFile upload = form.getUpload();
            document = (StaticDocument) documentService.saveStatic(document, upload.getInputStream(), upload.getFileSize(), upload.getFileName(), upload.getContentType());

            context.sendMessage(isInsert ? "document.inserted" : "document.modified");
            request.setAttribute("document", document);

            return ActionHelper.redirectWithParam(request, context.getSuccessForward(), "documentId", document.getId());
        } catch (final IOException e) {
            throw new CannotUploadFileException(e);
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditStaticDocumentForm form = context.getForm();
        final long id = form.getDocumentId();
        StaticDocument document;
        if (id > 0L) {
            document = (StaticDocument) documentService.load(id);
        } else {
            document = ClassHelper.instantiate(getEntityType());
        }
        getDataBinder().writeAsString(form.getDocument(), document);
        request.setAttribute("document", document);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditStaticDocumentForm form = context.getForm();
        final Document document = getDataBinder().readFromString(form.getDocument());
        documentService.validate(document, false);
    }

    private DataBinder<StaticDocument> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = EditStaticDocumentAction.getDataBinder(StaticDocument.class);
        }
        return dataBinder;
    }

}
