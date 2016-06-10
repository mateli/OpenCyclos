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
package nl.strohalm.cyclos.controls.accounts.external.process;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.controls.accounts.external.ExternalAccountHistoryAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferQuery;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.external.ExternalAccountService;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to retrieve the payments to process
 * @author luis
 */
public class RetrieveTransfersToProcessAction extends BaseQueryAction {
    private ExternalAccountService            externalAccountService;
    private ExternalTransferService           externalTransferService;
    private DataBinder<ExternalTransferQuery> dataBinder;

    public DataBinder<ExternalTransferQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            dataBinder = ExternalAccountHistoryAction.externalTransferQueryDataBinder(localSettings);
        }
        return dataBinder;
    }

    @Inject
    public void setExternalAccountService(final ExternalAccountService externalAccountService) {
        this.externalAccountService = externalAccountService;
    }

    @Inject
    public void setExternalTransferService(final ExternalTransferService externalTransferService) {
        this.externalTransferService = externalTransferService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final ExternalAccount externalAccount = (ExternalAccount) request.getAttribute("externalAccount");

        final ExternalTransferQuery query = (ExternalTransferQuery) queryParameters;
        query.setOnlyWithValidTypes(true);
        final List<ExternalTransfer> externalTransfers = externalTransferService.search(query);
        final ExternalTransferToProcessVOIterator voIterator = new ExternalTransferToProcessVOIterator(externalTransfers.iterator(), externalAccount);
        SpringHelper.injectBeans(getServlet().getServletContext(), voIterator);
        request.setAttribute("externalTransfers", voIterator);
    }

    @Override
    protected Integer pageSize(final ActionContext context) {
        // Iterate over all records by returning null
        return null;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final RetrieveTransfersToProcessForm form = context.getForm();
        final Long externalAccountId = form.getExternalAccountId();
        if (externalAccountId == null || externalAccountId <= 0L) {
            throw new ValidationException();
        }

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final HttpServletRequest request = context.getRequest();
        final ExternalAccount externalAccount = externalAccountService.load(externalAccountId, ExternalAccount.Relationships.MEMBER_ACCOUNT_TYPE, ExternalAccount.Relationships.SYSTEM_ACCOUNT_TYPE);
        request.setAttribute("externalAccount", externalAccount);
        request.setAttribute("canSetDate", permissionService.hasPermission(AdminMemberPermission.PAYMENTS_PAYMENT_WITH_DATE));
        request.setAttribute("today", localSettings.getDateConverter().toString(Calendar.getInstance()));

        final ExternalTransferQuery query = getDataBinder().readFromString(form.getQuery());
        query.fetch(RelationshipHelper.nested(ExternalTransfer.Relationships.TYPE, ExternalTransferType.Relationships.TRANSFER_TYPE));
        query.fetch(RelationshipHelper.nested(ExternalTransfer.Relationships.MEMBER, Element.Relationships.USER));
        query.setAccount(externalAccount);
        query.setStatus(ExternalTransfer.SummaryStatus.CHECKED);
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }
}
