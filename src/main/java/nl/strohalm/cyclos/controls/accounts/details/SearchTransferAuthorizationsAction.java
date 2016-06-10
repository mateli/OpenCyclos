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

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search for transfer authorizations given by a given member / administration
 * @author luis
 */
public class SearchTransferAuthorizationsAction extends BaseTransferAuthorizationSearchAction {

    private DataBinder<TransferAuthorizationQuery> dataBinder;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final TransferAuthorizationQuery query = (TransferAuthorizationQuery) queryParameters;
        final List<TransferAuthorization> authorizations = transferAuthorizationService.searchAuthorizations(query);
        context.getRequest().setAttribute("authorizations", authorizations);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchTransferAuthorizationsForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        final TransferAuthorizationQuery query = getDataBinder().readFromString(form.getQuery());
        query.fetch(TransferAuthorization.Relationships.BY, RelationshipHelper.nested(TransferAuthorization.Relationships.TRANSFER, Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER), RelationshipHelper.nested(TransferAuthorization.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));

        if (query.getMember() != null) {
            final Member member = elementService.load(query.getMember().getId(), Element.Relationships.USER);
            query.setMember(member);
        }

        final long memberId = form.getMemberId();
        Element by = null;
        if (memberId > 0L) {
            by = elementService.load(memberId, Element.Relationships.USER);
            query.setBy(by);
            request.setAttribute("by", by);
        } else if (context.isAdmin()) {
            if (form.getAdminId() > 0L) {
                query.setBy(EntityHelper.reference(Administrator.class, form.getAdminId()));
            }
            if (query.getBy() != null) {
                by = elementService.load(query.getBy().getId(), Element.Relationships.USER);
                query.setBy(by);
            }
        }
        request.setAttribute("transferTypes", resolveTransferTypes(by == null ? context.getElement() : by));
        RequestHelper.storeEnum(request, TransferAuthorization.Action.class, "actions");

        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<TransferAuthorizationQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<TransferAuthorizationQuery> binder = BeanBinder.instance(TransferAuthorizationQuery.class);
            final LocalSettings localSettings = settingsService.getLocalSettings();
            binder.registerBinder("period", DataBinderHelper.periodBinder(localSettings, "period"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("by", PropertyBinder.instance(Administrator.class, "by"));
            binder.registerBinder("action", PropertyBinder.instance(TransferAuthorization.Action.class, "action"));
            binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }
}
