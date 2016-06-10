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
package nl.strohalm.cyclos.controls.accounts.external;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer.SummaryStatus;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferQuery;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.external.ExternalAccountService;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferImportService;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferService;
import nl.strohalm.cyclos.services.transactions.NegativeAllowedTransactionSummaryVO;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.BigDecimalHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to retrieve the account history
 * @author jefferson
 */
public class ExternalAccountHistoryAction extends BaseQueryAction implements LocalSettingsChangeListener {

    public static DataBinder<ExternalTransferQuery> externalTransferQueryDataBinder(final LocalSettings localSettings) {
        final BeanBinder<ExternalTransferQuery> binder = BeanBinder.instance(ExternalTransferQuery.class);
        binder.registerBinder("account", PropertyBinder.instance(ExternalAccount.class, "account", ReferenceConverter.instance(ExternalAccount.class)));
        binder.registerBinder("type", PropertyBinder.instance(ExternalTransferType.class, "type", ReferenceConverter.instance(ExternalTransferType.class)));
        binder.registerBinder("transferImport", PropertyBinder.instance(ExternalTransferImport.class, "transferImport", ReferenceConverter.instance(ExternalTransferImport.class)));
        binder.registerBinder("status", PropertyBinder.instance(ExternalTransfer.SummaryStatus.class, "status"));
        binder.registerBinder("member", PropertyBinder.instance(Member.class, "member", ReferenceConverter.instance(Member.class)));
        binder.registerBinder("initialAmount", PropertyBinder.instance(BigDecimal.class, "initialAmount"));
        binder.registerBinder("finalAmount", PropertyBinder.instance(BigDecimal.class, "finalAmount"));
        binder.registerBinder("period", DataBinderHelper.periodBinder(localSettings, "period"));
        binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
        return binder;
    }

    private DataBinder<ExternalTransferQuery> dataBinder;
    private ExternalAccountService            externalAccountService;
    private ExternalTransferImportService     externalTransferImportService;

    private ExternalTransferService           externalTransferService;

    public DataBinder<ExternalTransferQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            dataBinder = externalTransferQueryDataBinder(localSettings);
        }
        return dataBinder;
    }

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

    @Inject
    public void setExternalTransferService(final ExternalTransferService externalTransferService) {
        this.externalTransferService = externalTransferService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final ExternalTransferQuery query = (ExternalTransferQuery) queryParameters;
        final List<ExternalTransfer> externalTransfers = externalTransferService.search(query);
        request.setAttribute("externalTransfers", externalTransfers);
        request.setAttribute("summary", processSummary(externalTransfers));
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final ExternalAccountHistoryForm form = context.getForm();

        // Retrieve the query parameters
        final ExternalTransferQuery query = getDataBinder().readFromString(form.getQuery());
        query.fetch(ExternalTransfer.Relationships.TRANSFER_IMPORT, ExternalTransfer.Relationships.TYPE);

        ExternalAccount externalAccount = null;
        final Long transferImportId = form.getTransferImportId();
        if (transferImportId != null && transferImportId > 0) {
            final ExternalTransferImport transferImport = externalTransferImportService.load(transferImportId, RelationshipHelper.nested(ExternalTransferImport.Relationships.ACCOUNT, ExternalAccount.Relationships.TYPES));
            query.setTransferImport(transferImport);
            externalAccount = transferImport.getAccount();
            request.setAttribute("transferImport", transferImport);
        } else {
            final Long externalAccountId = form.getExternalAccountId();
            if (externalAccountId == null) {
                throw new ValidationException();
            }
            externalAccount = externalAccountService.load(externalAccountId, ExternalAccount.Relationships.TYPES);
        }
        query.setAccount(externalAccount);
        final Collection<ExternalTransferType> types = externalAccount.getTypes();
        final boolean process = permissionService.hasPermission(AdminSystemPermission.EXTERNAL_ACCOUNTS_PROCESS_PAYMENT);
        final boolean check = permissionService.hasPermission(AdminSystemPermission.EXTERNAL_ACCOUNTS_CHECK_PAYMENT);
        final boolean managePayment = permissionService.hasPermission(AdminSystemPermission.EXTERNAL_ACCOUNTS_MANAGE_PAYMENT);

        request.setAttribute("isProcess", process);
        request.setAttribute("isManage", managePayment);
        request.setAttribute("isCheck", check);
        request.setAttribute("externalAccount", externalAccount);
        request.setAttribute("types", types);
        RequestHelper.storeEnum(request, ExternalTransfer.SummaryStatus.class, "statusList");

        final List<ExternalTransferAction> listPossibleActions = new ArrayList<ExternalTransferAction>();
        if (check) {
            listPossibleActions.add(ExternalTransferAction.MARK_AS_CHECKED);
            listPossibleActions.add(ExternalTransferAction.MARK_AS_UNCHECKED);
        }
        if (managePayment) {
            listPossibleActions.add(ExternalTransferAction.DELETE);
        }
        request.setAttribute("possibleActions", listPossibleActions);

        getDataBinder().writeAsString(form.getQuery(), query);
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        // The query is always executed
        return true;
    }

    private Map<ExternalTransfer.SummaryStatus, TransactionSummaryVO> processSummary(final List<ExternalTransfer> externalTransfers) {
        // Initialize summary VOs with 0 values
        final Map<ExternalTransfer.SummaryStatus, TransactionSummaryVO> summary = new EnumMap<ExternalTransfer.SummaryStatus, TransactionSummaryVO>(ExternalTransfer.SummaryStatus.class);
        for (final SummaryStatus summaryStatus : ExternalTransfer.SummaryStatus.values()) {
            summary.put(summaryStatus, new NegativeAllowedTransactionSummaryVO(0, new BigDecimal(0)));
        }
        final TransactionSummaryVO totalVo = summary.get(ExternalTransfer.SummaryStatus.TOTAL);
        for (final ExternalTransfer transfer : externalTransfers) {
            final ExternalTransfer.Status status = transfer.getStatus();
            // Get the summary VO corresponding to the external transfer status
            TransactionSummaryVO vo = null;
            switch (status) {
                case PENDING:
                    if (transfer.isComplete()) {
                        vo = summary.get(ExternalTransfer.SummaryStatus.COMPLETE_PENDING);
                    } else {
                        vo = summary.get(ExternalTransfer.SummaryStatus.INCOMPLETE_PENDING);
                    }
                    break;
                case CHECKED:
                    vo = summary.get(ExternalTransfer.SummaryStatus.CHECKED);
                    break;
                case PROCESSED:
                    vo = summary.get(ExternalTransfer.SummaryStatus.PROCESSED);
                    break;
            }
            // Update summary on both current and total
            final BigDecimal amount = BigDecimalHelper.nvl(transfer.getAmount());
            vo.setCount(vo.getCount() + 1);
            vo.setAmount(vo.getAmount().add(amount));
            totalVo.setCount(totalVo.getCount() + 1);
            totalVo.setAmount(totalVo.getAmount().add(amount));
        }
        return summary;
    }

}
