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

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel.Authorizer;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransfersAwaitingAuthorizationQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search for transfers the logged user can authorize
 * @author luis
 */
public class TransfersAwaitingAuthorizationAction extends BaseTransferAuthorizationSearchAction {

    private DataBinder<TransfersAwaitingAuthorizationQuery> dataBinder;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final TransfersAwaitingAuthorizationQuery query = (TransfersAwaitingAuthorizationQuery) queryParameters;
        final List<Transfer> list = transferAuthorizationService.searchTransfersAwaitingAuthorization(query);
        context.getRequest().setAttribute("transfers", list);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final TransfersAwaitingAuthorizationForm form = context.getForm();
        final TransfersAwaitingAuthorizationQuery query = getDataBinder().readFromString(form.getQuery());
        query.fetch(
                RelationshipHelper.nested(Payment.Relationships.FROM, MemberAccount.Relationships.MEMBER),
                RelationshipHelper.nested(Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));

        if (query.getMember() != null) {
            final Member member = elementService.load(query.getMember().getId(), Element.Relationships.USER);
            query.setMember(member);
        }

        final HttpServletRequest request = context.getRequest();
        final List<TransferType> transferTypes = resolveTransferTypes(context.getElement());
        boolean showBrokerCheck = false;
        if (context.isAdmin()) {
            for (final TransferType transferType : transferTypes) {
                final Collection<AuthorizationLevel> levels = transferType.getAuthorizationLevels();
                for (final AuthorizationLevel level : levels) {
                    if (level.getAuthorizer() == Authorizer.BROKER) {
                        showBrokerCheck = true;
                        break;
                    }
                }
                if (showBrokerCheck) {
                    break;
                }
            }
        }
        request.setAttribute("transferTypes", transferTypes);
        request.setAttribute("showBrokerCheck", showBrokerCheck);

        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

    private DataBinder<TransfersAwaitingAuthorizationQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<TransfersAwaitingAuthorizationQuery> binder = BeanBinder.instance(TransfersAwaitingAuthorizationQuery.class);
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            binder.registerBinder("onlyWithoutBroker", PropertyBinder.instance(Boolean.TYPE, "onlyWithoutBroker"));
            binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType"));
            binder.registerBinder("period", DataBinderHelper.periodBinder(localSettings, "period"));
            binder.registerBinder("transactionNumber", PropertyBinder.instance(String.class, "transactionNumber"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

}
