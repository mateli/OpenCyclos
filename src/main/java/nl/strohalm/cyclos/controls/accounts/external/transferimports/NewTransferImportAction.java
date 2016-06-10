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
package nl.strohalm.cyclos.controls.accounts.external.transferimports;

import java.io.InputStreamReader;
import java.io.Reader;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.external.ExternalAccountService;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferImportService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.transactionimport.IllegalTransactionFileFormatException;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to import a new external transfer file
 * @author luis
 */
public class NewTransferImportAction extends BaseAction {

    private ExternalAccountService        externalAccountService;
    private ExternalTransferImportService externalTransferImportService;

    @Inject
    public void setExternalAccountService(final ExternalAccountService externalAccountService) {
        this.externalAccountService = externalAccountService;
    }

    @Inject
    public void setExternalTransferImportService(final ExternalTransferImportService externalTransferImportService) {
        this.externalTransferImportService = externalTransferImportService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final LocalSettings settings = settingsService.getLocalSettings();
        final NewTransferImportForm form = context.getForm();
        final long externalAccountId = form.getExternalAccountId();
        if (externalAccountId <= 0L) {
            throw new ValidationException();
        }
        final FormFile file = form.getFile();
        if (file == null || file.getFileSize() == 0) {
            throw new ValidationException();
        }

        final ExternalAccount externalAccount = externalAccountService.load(externalAccountId, ExternalAccount.Relationships.FILE_MAPPING);
        try {
            final Reader in = new InputStreamReader(file.getInputStream(), settings.getCharset());
            final ExternalTransferImport transferImport = externalTransferImportService.importNew(externalAccount.getFileMapping(), in);
            context.sendMessage("externalTransferImport.imported");
            return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "transferImportId", transferImport.getId());
        } catch (final IllegalTransactionFileFormatException e) {
            final String message = e.getMessage();
            if (StringUtils.isEmpty(message)) {
                return context.sendError("externalTransferImport.error.format.detailed", e.getLine(), e.getColumn(), e.getField(), e.getValue());
            } else {
                return context.sendError("externalTransferImport.error.format.general", message);
            }
        } catch (final Exception e) {
            return context.sendError("externalTransferImport.error.importing");
        }
    }
}
