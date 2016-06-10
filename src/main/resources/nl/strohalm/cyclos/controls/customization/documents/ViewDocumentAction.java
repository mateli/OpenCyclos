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

import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.binaryfiles.BinaryFile;
import nl.strohalm.cyclos.entities.customization.documents.StaticDocument;
import nl.strohalm.cyclos.services.customization.DocumentService;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForward;

public class ViewDocumentAction extends BaseAction {

    protected DocumentService documentService;
    protected ResponseHelper  responseHelper;

    @Inject
    public void setDocumentService(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final PreviewDocumentForm form = context.getForm();
        final long documentId = form.getDocumentId();
        if (documentId < 1) {
            throw new ValidationException();
        }
        final StaticDocument document = (StaticDocument) documentService.load(documentId, StaticDocument.Relationships.BINARY_FILE);
        final BinaryFile binaryFile = document.getBinaryFile();
        final HttpServletResponse response = context.getResponse();
        responseHelper.setDownload(response, binaryFile.getName());
        response.setContentType(binaryFile.getContentType());
        response.setContentLength(binaryFile.getSize());
        response.setDateHeader("Last-Modified", binaryFile.getLastModified().getTimeInMillis());
        IOUtils.copy(binaryFile.getContents().getBinaryStream(), response.getOutputStream());
        response.flushBuffer();
        return null;
    }

}
