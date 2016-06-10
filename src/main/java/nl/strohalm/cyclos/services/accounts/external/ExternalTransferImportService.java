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
package nl.strohalm.cyclos.services.accounts.external;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImportQuery;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.transactionimport.IllegalTransactionFileFormatException;

/**
 * Service interface for importing new and managing external transfer files
 * @author luis
 */
public interface ExternalTransferImportService extends Service {

    /**
     * Imports a new file
     * @throws IllegalTransactionFileFormatException When the file does not have the expected format
     */
    ExternalTransferImport importNew(FileMapping mapping, Reader in) throws IllegalTransactionFileFormatException, IOException;

    /**
     * Loads a file import
     */
    ExternalTransferImport load(Long id, Relationship... fetch);

    /**
     * Removes the given file imports
     * @throws UnexpectedEntityException When any of the given ids identifies an import with at least one checked or processed transaction
     */
    int remove(Long... ids) throws UnexpectedEntityException;

    /**
     * Searches for file imports with given query parameters
     */
    List<ExternalTransferImport> search(ExternalTransferImportQuery query);
}
