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
package nl.strohalm.cyclos.controls.accounts.details;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery.SearchType;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.accounts.SystemAccountTypeQuery;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search for scheduled payments
 * @author Jefferson Magno
 */
public class SearchScheduledPaymentsAction extends BaseQueryAction {

    private DataBinder<ScheduledPaymentQuery> dataBinder;
    private AccountTypeService                accountTypeService;
    private ScheduledPaymentService           scheduledPaymentService;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setScheduledPaymentService(final ScheduledPaymentService scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final ScheduledPaymentQuery query = (ScheduledPaymentQuery) queryParameters;
        List<ScheduledPayment> payments = null;
        if (LoggedUser.isAdministrator()) {
            query.setSearchType(SearchType.OUTGOING);
        }
        payments = scheduledPaymentService.search(query);
        context.getRequest().setAttribute("payments", payments);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchScheduledPaymentsForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        final ScheduledPaymentQuery query = getDataBinder().readFromString(form.getQuery());
        query.fetch(ScheduledPayment.Relationships.TRANSFERS, RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));

        // Account owner
        AccountOwner owner = null;
        if (form.getMemberId() > 0) {
            owner = (Member) elementService.load(form.getMemberId());
            request.setAttribute("memberId", form.getMemberId());
        } else {
            // An admin or member or an operator searching his own scheduled payments
            owner = context.getAccountOwner();
        }
        query.setOwner(owner);

        List<? extends AccountType> accountTypes;
        if (context.isAdmin() && owner instanceof SystemAccountOwner) {
            final SystemAccountTypeQuery satq = new SystemAccountTypeQuery();
            accountTypes = accountTypeService.search(satq);
        } else {
            final MemberAccountTypeQuery matq = new MemberAccountTypeQuery();
            matq.setOwner((Member) owner);
            accountTypes = accountTypeService.search(matq);
        }
        request.setAttribute("accountTypes", accountTypes);

        if (query.getMember() != null) {
            final Member member = elementService.load(query.getMember().getId(), Element.Relationships.USER);
            query.setMember(member);
        }
        if (query.getStatusList() == null) {
            query.setStatusGroup(ScheduledPaymentQuery.StatusGroup.OPEN);
            form.setQuery("statusGroup", ScheduledPaymentQuery.StatusGroup.OPEN);
        }

        RequestHelper.storeEnum(request, ScheduledPaymentQuery.SearchType.class, "searchTypes");
        RequestHelper.storeEnum(request, ScheduledPaymentQuery.StatusGroup.class, "statusGroups");
        request.setAttribute("accountOwner", owner);
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<ScheduledPaymentQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ScheduledPaymentQuery> binder = BeanBinder.instance(ScheduledPaymentQuery.class);
            final LocalSettings localSettings = settingsService.getLocalSettings();
            binder.registerBinder("accountType", PropertyBinder.instance(AccountType.class, "accountType"));
            binder.registerBinder("searchType", PropertyBinder.instance(ScheduledPaymentQuery.SearchType.class, "searchType"));
            binder.registerBinder("statusGroup", PropertyBinder.instance(ScheduledPaymentQuery.StatusGroup.class, "statusGroup"));
            binder.registerBinder("period", DataBinderHelper.periodBinder(localSettings, "period"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

}
