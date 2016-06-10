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
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentQuery;
import nl.strohalm.cyclos.entities.customization.documents.DynamicDocument;
import nl.strohalm.cyclos.entities.customization.documents.StaticDocument;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for document management (save, load, edit)
 * @author luis
 * @author Jefferson Magno
 */
public interface DocumentService extends Service {

    /**
     * Loads the document with the specified fetch.
     */
    Document load(Long id, Relationship... fetch);

    /**
     * Remove the specified documents, returning the number of removed documents
     */
    int remove(Long... ids);

    /**
     * Save a dynamic document
     */
    Document saveDynamic(DynamicDocument document);

    /**
     * Save a static document
     */
    Document saveStatic(StaticDocument document, InputStream stream, int size, String fileName, String contentType);

    /**
     * Search documents
     */
    List<Document> search(DocumentQuery documentQuery);

    /**
     * Validates a document
     */
    void validate(Document document, boolean validateBinaryFile);

}
