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
package nl.strohalm.cyclos.initializations;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentPage;
import nl.strohalm.cyclos.entities.customization.documents.DocumentQuery;
import nl.strohalm.cyclos.entities.customization.documents.DynamicDocument;
import nl.strohalm.cyclos.services.customization.DocumentServiceLocal;
import nl.strohalm.cyclos.utils.CustomizationHelper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Initializes the custom documents.<br>
 * NOTE: This initialization doesn't require a transaction to run
 * @author luis
 */
public class DocumentInitialization implements LocalInitialization {
    private static final Log     LOG = LogFactory.getLog(DocumentInitialization.class);

    private DocumentServiceLocal documentService;
    private CustomizationHelper  customizationHelper;

    @Override
    public String getName() {
        return "Documents";
    }

    @Override
    public void initialize() {
        final DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setNatures(Collections.singleton(Document.Nature.DYNAMIC));
        documentQuery.fetch(DynamicDocument.Relationships.FORM_PAGE, DynamicDocument.Relationships.DOCUMENT_PAGE);
        final List<Document> documents = documentService.search(documentQuery);
        for (final Document document : documents) {
            final DynamicDocument dynamicDocument = (DynamicDocument) document;
            if (dynamicDocument.isHasFormPage()) {
                final File formFile = customizationHelper.formFile(dynamicDocument);
                final DocumentPage formPage = dynamicDocument.getFormPage();
                try {
                    FileUtils.writeStringToFile(formFile, formPage.getContents());
                } catch (final IOException e) {
                    LOG.warn("Error handling document form file: " + formFile.getAbsolutePath(), e);
                }
            }
            final File documentFile = customizationHelper.documentFile(dynamicDocument);
            final DocumentPage documentPage = dynamicDocument.getDocumentPage();
            try {
                FileUtils.writeStringToFile(documentFile, documentPage.getContents());
            } catch (final IOException e) {
                LOG.warn("Error handling document page file: " + documentFile.getAbsolutePath(), e);
            }
        }
    }

    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    public void setDocumentServiceLocal(final DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }
}
