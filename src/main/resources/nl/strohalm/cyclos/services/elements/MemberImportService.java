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
package nl.strohalm.cyclos.services.elements;

import java.io.InputStream;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberQuery;
import nl.strohalm.cyclos.entities.members.imports.MemberImport;
import nl.strohalm.cyclos.entities.members.imports.MemberImportResult;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Interface used to handle member imports
 * 
 * @author luis
 */
public interface MemberImportService extends Service {

    /**
     * Returns the summarized results for the given member import
     */
    MemberImportResult getSummary(MemberImport memberImport);

    /**
     * Import members from the given stream
     */
    MemberImport importMembers(MemberImport memberImport, InputStream data);

    /**
     * Loads an import with the specified id
     */
    MemberImport load(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Process the import, creating the members and transactions, only for members without errors, optionally sending the activation e-mail
     */
    void processImport(MemberImport memberImport, boolean sendActivationMail);

    /**
     * Searches for imported members
     */
    List<ImportedMember> searchImportedMembers(ImportedMemberQuery params);

    /**
     * Validates a member import
     */
    void validate(MemberImport memberImport) throws ValidationException;

}
