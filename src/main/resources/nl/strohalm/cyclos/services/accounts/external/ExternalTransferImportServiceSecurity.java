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
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImportQuery;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.transactionimport.IllegalTransactionFileFormatException;

/**
 * Security implementation for {@link ExternalTransferImportService}
 * 
 * @author jcomas
 */
public class ExternalTransferImportServiceSecurity extends BaseServiceSecurity implements ExternalTransferImportService {

    private ExternalTransferImportServiceLocal externalTransferImportService;

    @Override
    public ExternalTransferImport importNew(final FileMapping mapping, final Reader in) throws IllegalTransactionFileFormatException, IOException {
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE_PAYMENT).check();
        return externalTransferImportService.importNew(mapping, in);
    }

    @Override
    public ExternalTransferImport load(final Long id, final Relationship... fetch) {
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_DETAILS).check();
        return externalTransferImportService.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) throws UnexpectedEntityException {
        permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE_PAYMENT).check();
        return externalTransferImportService.remove(ids);
    }

    @Override
    public List<ExternalTransferImport> search(final ExternalTransferImportQuery query) {
        if (!permissionService.permission().admin(AdminSystemPermission.EXTERNAL_ACCOUNTS_DETAILS).hasPermission()) {
            return Collections.emptyList();
        }
        return externalTransferImportService.search(query);
    }

    public void setExternalTransferImportServiceLocal(final ExternalTransferImportServiceLocal externalTransferImportService) {
        this.externalTransferImportService = externalTransferImportService;
    }

}
