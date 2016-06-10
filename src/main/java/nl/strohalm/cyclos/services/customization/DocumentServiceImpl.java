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
import java.sql.Blob;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.dao.customizations.BinaryFileDAO;
import nl.strohalm.cyclos.dao.customizations.DocumentDAO;
import nl.strohalm.cyclos.dao.customizations.DocumentPageDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.binaryfiles.BinaryFile;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentPage;
import nl.strohalm.cyclos.entities.customization.documents.DocumentQuery;
import nl.strohalm.cyclos.entities.customization.documents.DynamicDocument;
import nl.strohalm.cyclos.entities.customization.documents.MemberDocument;
import nl.strohalm.cyclos.entities.customization.documents.StaticDocument;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for document service
 * @author luis
 * @author Jefferson Magno
 */
public class DocumentServiceImpl implements DocumentServiceLocal {

    /**
     * If we are inserting a new static document, the binary file is required
     * @author Jefferson Magno
     */
    public class BinaryFileValidation implements PropertyValidation {

        private static final long serialVersionUID = 7546191238179831809L;

        @Override
        public ValidationError validate(final Object object, final Object name, final Object value) {
            final StaticDocument document = (StaticDocument) object;
            if (document.isTransient() && document.getBinaryFile() == null) {
                return new RequiredError();
            }
            return null;
        }
    }

    private BinaryFileDAO     binaryFileDao;
    private DocumentDAO       documentDao;
    private DocumentPageDAO   documentPageDao;
    private GroupServiceLocal groupService;

    @Override
    public Document load(final Long id, final Relationship... fetch) {
        return documentDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        return documentDao.delete(ids);
    }

    @Override
    public DynamicDocument saveDynamic(DynamicDocument document) {

        validate(document, false);

        // Store the pages
        final DocumentPage formPage = document.getFormPage();
        final DocumentPage documentPage = document.getDocumentPage();

        final boolean isInsert = document.isTransient();

        if (isInsert) {
            // Save the document without pages
            document.setFormPage(null);
            document.setDocumentPage(null);
            AdminGroup group = (AdminGroup) groupService.load(LoggedUser.group().getId(), SystemGroup.Relationships.DOCUMENTS);
            document.addGroup(group);
            document = documentDao.insert(document);

            // Add the document to the group
            group.getDocuments().add(document);

            // Ensure the group have the adminMemberDocuments.details permission
            if (!group.getPermissions().contains(AdminMemberPermission.DOCUMENTS_DETAILS)) {
                group.getPermissions().add(AdminMemberPermission.DOCUMENTS_DETAILS);
            }
            // Update the group
            group = groupService.update(group, false);
        } else {
            // Update collections of groups and broker groups
            final Document loadedDocument = documentDao.load(document.getId(), Document.Relationships.GROUPS, Document.Relationships.BROKER_GROUPS);
            final Collection<SystemGroup> loadedGroups = loadedDocument.getGroups();
            final Collection<BrokerGroup> loadedBrokerGroups = loadedDocument.getBrokerGroups();
            document.setGroups(loadedGroups);
            document.setBrokerGroups(loadedBrokerGroups);

            // Update document
            document = documentDao.update(document);
        }

        // Save the pages
        if (formPage != null) {
            formPage.setDocument(document);
            formPage.setName("Form page of " + document.getName());
            document.setFormPage(save(formPage));
        }
        documentPage.setDocument(document);
        documentPage.setName("Document page of " + document.getName());
        document.setDocumentPage(save(documentPage));

        // Update the document with the pages
        if (isInsert) {
            document = documentDao.update(document);
        }
        return document;
    }

    @Override
    public StaticDocument saveStatic(final StaticDocument document, final InputStream stream, final int size, final String fileName, final String contentType) {
        return doSaveStatic(document, stream, size, fileName, contentType);
    }

    @Override
    public List<Document> search(final DocumentQuery documentQuery) {
        return documentDao.search(documentQuery);
    }

    public void setBinaryFileDao(final BinaryFileDAO binaryFileDao) {
        this.binaryFileDao = binaryFileDao;
    }

    public void setDocumentDao(final DocumentDAO documentDao) {
        this.documentDao = documentDao;
    }

    public void setDocumentPageDao(final DocumentPageDAO documentPageDao) {
        this.documentPageDao = documentPageDao;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    @Override
    public void validate(final Document document, final boolean validateBinaryFile) {
        if (document instanceof DynamicDocument) {
            getDynamicValidator().validate(document);
        } else if (document instanceof MemberDocument) {
            getMemberValidator(validateBinaryFile).validate(document);
        } else { // StaticDocument
            getStaticValidator(validateBinaryFile).validate(document);
        }
    }

    private StaticDocument doSaveStatic(StaticDocument document, final InputStream stream, final int size, final String fileName, final String contentType) {
        BinaryFile binaryFile = retrieveBinaryFile(stream, size, fileName, contentType);
        document.setBinaryFile(binaryFile);
        validate(document, true);
        if (document.isTransient()) {
            binaryFile = binaryFileDao.insert(binaryFile);
            document.setBinaryFile(binaryFile);

            final SystemGroup group = groupService.load(LoggedUser.group().getId(), SystemGroup.Relationships.DOCUMENTS);

            group.addDocument(document);
            document.addGroup(group);

            document = documentDao.insert(document);

            if (LoggedUser.isAdministrator()) {
                group.getPermissions().add(AdminMemberPermission.DOCUMENTS_DETAILS);
            } else if (LoggedUser.isBroker()) {
                group.getPermissions().add(BrokerPermission.DOCUMENTS_VIEW);
            }

            groupService.update(group, false);
        } else {
            // Update the binary file
            final StaticDocument loadedDocument = (StaticDocument) load(document.getId(), Document.Relationships.GROUPS, Document.Relationships.BROKER_GROUPS, StaticDocument.Relationships.BINARY_FILE);
            BinaryFile loadedBinaryFile = loadedDocument.getBinaryFile();
            if (binaryFile != null) {
                loadedBinaryFile.setContentType(binaryFile.getContentType());
                loadedBinaryFile.setName(binaryFile.getName());
                loadedBinaryFile.setSize(binaryFile.getSize());
                loadedBinaryFile.setLastModified(binaryFile.getLastModified());
                loadedBinaryFile.setContents(binaryFile.getContents());
                loadedBinaryFile = binaryFileDao.update(loadedBinaryFile);
            }
            document.setBinaryFile(loadedBinaryFile);

            // Update collections of groups and broker groups (if not a member document)
            if (!(loadedDocument instanceof MemberDocument)) {
                final Collection<SystemGroup> loadedGroups = loadedDocument.getGroups();
                final Collection<BrokerGroup> loadedBrokerGroups = loadedDocument.getBrokerGroups();
                document.setGroups(loadedGroups);
                document.setBrokerGroups(loadedBrokerGroups);
            }

            // Update document
            documentDao.update(document);
        }
        return document;
    }

    private Validator getBasicValidator() {
        final Validator validator = new Validator("document");
        validator.property("name").required().maxLength(100);
        validator.property("description").maxLength(1000);
        return validator;
    }

    private Validator getDynamicValidator() {
        final Validator dynamicValidator = getBasicValidator();
        dynamicValidator.property("documentPage.contents").key("document.documentPage").required();
        return dynamicValidator;
    }

    private Validator getMemberValidator(final boolean validateBinaryFile) {
        final Validator memberValidator = getBasicValidator();
        if (validateBinaryFile) {
            memberValidator.property("binaryFile").add(new BinaryFileValidation());
        }
        memberValidator.property("member").required();
        memberValidator.property("visibility").required();
        return memberValidator;
    }

    private Validator getStaticValidator(final boolean validateBinaryFile) {
        final Validator staticValidator = getBasicValidator();
        if (validateBinaryFile) {
            staticValidator.property("binaryFile").add(new BinaryFileValidation());
        }
        return staticValidator;
    }

    private BinaryFile retrieveBinaryFile(final InputStream stream, final int size, final String fileName, final String contentType) {
        if (stream != null && size > 0) {
            // Generate blob contents for the binary file
            final Blob contents = documentDao.createBlob(stream, size);

            // Create binary file and set in the static document
            final BinaryFile binaryFile = new BinaryFile();
            binaryFile.setContentType(contentType);
            binaryFile.setName(fileName);
            binaryFile.setSize(size);
            binaryFile.setLastModified(Calendar.getInstance());
            binaryFile.setContents(contents);
            return binaryFile;
        } else {
            return null;
        }
    }

    private DocumentPage save(final DocumentPage page) {
        page.setLastModified(Calendar.getInstance());
        if (page.isTransient()) {
            return documentPageDao.insert(page);
        } else {
            return documentPageDao.update(page);
        }
    }

}
