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
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.external.ExternalTransferImportDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImportQuery;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.utils.CustomObjectHandler;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.transactionimport.IllegalTransactionFileFormatException;
import nl.strohalm.cyclos.utils.transactionimport.TransactionImportDTO;

/**
 * Implementation for external transfer import service
 * @author luis
 */
public class ExternalTransferImportServiceImpl implements ExternalTransferImportServiceLocal {

    private ExternalTransferServiceLocal externalTransferService;
    private ExternalTransferImportDAO    externalTransferImportDao;
    private FetchServiceLocal            fetchService;
    private CustomObjectHandler          customObjectHandler;

    @Override
    public ExternalTransferImport importNew(FileMapping mapping, final Reader in) throws IllegalTransactionFileFormatException, IOException {
        mapping = fetchService.fetch(mapping, ExternalTransferImport.Relationships.ACCOUNT);

        // Insert the import
        ExternalTransferImport transferImport = new ExternalTransferImport();
        transferImport.setAccount(mapping.getAccount());
        transferImport.setDate(Calendar.getInstance());
        transferImport.setBy(LoggedUser.element());
        transferImport = externalTransferImportDao.insert(transferImport);

        // Import each transfer
        final List<TransactionImportDTO> transactions = mapping.getImport(customObjectHandler).readTransactions(in);
        for (final TransactionImportDTO dto : transactions) {
            externalTransferService.importNew(transferImport, dto);
        }
        return transferImport;
    }

    @Override
    public ExternalTransferImport load(final Long id, final Relationship... fetch) {
        return externalTransferImportDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        if (externalTransferImportDao.hasCheckedTransfers(ids)) {
            throw new UnexpectedEntityException();
        }
        return externalTransferImportDao.delete(ids);
    }

    @Override
    public List<ExternalTransferImport> search(final ExternalTransferImportQuery query) {
        return externalTransferImportDao.search(query);
    }

    public void setCustomObjectHandler(final CustomObjectHandler customObjectHandler) {
        this.customObjectHandler = customObjectHandler;
    }

    public void setExternalTransferImportDao(final ExternalTransferImportDAO externalTransferImportDao) {
        this.externalTransferImportDao = externalTransferImportDao;
    }

    public void setExternalTransferServiceLocal(final ExternalTransferServiceLocal externalTransferService) {
        this.externalTransferService = externalTransferService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

}
