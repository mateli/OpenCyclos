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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImportQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.external.ExternalAccountService;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferImportService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Searches external file imports for the given parameters
 * @author luis
 */
public class SearchTransferImportsAction extends BaseQueryAction {

    private ExternalAccountService                  externalAccountService;
    private ExternalTransferImportService           externalTransferImportService;
    private DataBinder<ExternalTransferImportQuery> dataBinder;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setExternalAccountService(final ExternalAccountService externalAccountService) {
        this.externalAccountService = externalAccountService;
    }

    @Inject
    public void setExternalTransferImportService(final ExternalTransferImportService externalTransferImportService) {
        this.externalTransferImportService = externalTransferImportService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final List<ExternalTransferImport> imports = externalTransferImportService.search((ExternalTransferImportQuery) queryParameters);
        context.getRequest().setAttribute("externalTransferImports", imports);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchTransferImportsForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final long externalAccountId = form.getExternalAccountId();
        if (externalAccountId <= 0L) {
            throw new ValidationException();
        }
        final ExternalAccount externalAccount = externalAccountService.load(externalAccountId);
        request.setAttribute("externalAccount", externalAccount);

        final boolean editable = true;
        request.setAttribute("editable", editable);

        final ExternalTransferImportQuery query = getDataBinder().readFromString(form.getQuery());
        query.setAccount(externalAccount);
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<ExternalTransferImportQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<ExternalTransferImportQuery> binder = BeanBinder.instance(ExternalTransferImportQuery.class);
            binder.registerBinder("period", DataBinderHelper.periodBinder(localSettings, "period"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }
}
