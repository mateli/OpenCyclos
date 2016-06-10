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
package nl.strohalm.cyclos.controls.accounts.transfertypes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.AccountOwnerConverter;

/**
 * Action for searching transfer types using Ajax
 * @author luis
 */
public class SearchTransferTypesAjaxAction extends BaseAjaxAction {

    public static enum Options {
        LOAN_DATA, DIRECTION, SCHEDULING, CURRENCY;
    }

    private DataBinder<TransferTypeQuery> queryBinder;
    protected TransferTypeService         transferTypeService;

    public DataBinder<TransferTypeQuery> getQueryBinder() {
        if (queryBinder == null) {
            final BeanBinder<TransferTypeQuery> binder = BeanBinder.instance(TransferTypeQuery.class);
            binder.registerBinder("channel", PropertyBinder.instance(String.class, "channel"));
            binder.registerBinder("context", PropertyBinder.instance(TransactionContext.class, "context"));
            binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currencyId"));
            binder.registerBinder("fromOwner", PropertyBinder.instance(AccountOwner.class, "fromOwnerId", AccountOwnerConverter.instance()));
            binder.registerBinder("toOwner", PropertyBinder.instance(AccountOwner.class, "toOwnerId", AccountOwnerConverter.instance()));
            binder.registerBinder("fromAccountTypes", SimpleCollectionBinder.instance(AccountType.class, "fromAccountTypeId"));
            binder.registerBinder("toAccountTypes", SimpleCollectionBinder.instance(AccountType.class, "toAccountTypeId"));
            binder.registerBinder("fromGroups", SimpleCollectionBinder.instance(MemberGroup.class, "fromGroups"));
            binder.registerBinder("toGroups", SimpleCollectionBinder.instance(MemberGroup.class, "toGroups"));
            binder.registerBinder("fromOrToGroups", SimpleCollectionBinder.instance(MemberGroup.class, "fromOrToGroups"));
            binder.registerBinder("fromNature", PropertyBinder.instance(AccountType.Nature.class, "fromNature"));
            binder.registerBinder("toNature", PropertyBinder.instance(AccountType.Nature.class, "toNature"));
            queryBinder = binder;
        }
        return queryBinder;
    }

    public BeanCollectionBinder<Map<String, Object>> getTransferTypeBinder(final Options... opts) {
        final BeanBinder<Map<String, Object>> transferTypeBinder = DataBinderHelper.transferTypeBinder();
        List<Options> options;
        if (opts == null) {
            options = Collections.emptyList();
        } else {
            options = Arrays.asList(opts);
        }

        if (options.contains(Options.LOAN_DATA)) {
            // Copy the loan parameters binder to the transfer type binder
            final BeanBinder<Map<String, Object>> loanBinder = DataBinderHelper.loanParametersBinder(settingsService.getLocalSettings());
            for (final Map.Entry<String, DataBinder<?>> entry : loanBinder.getMappings().entrySet()) {
                final DataBinder<?> nestedBinder = entry.getValue();
                nestedBinder.setPath("loan." + nestedBinder.getPath());
                transferTypeBinder.registerBinder(entry.getKey(), nestedBinder);
            }
        }
        if (options.contains(Options.DIRECTION)) {
            transferTypeBinder.registerBinder("fromSystem", PropertyBinder.instance(Boolean.TYPE, "fromSystem"));
            transferTypeBinder.registerBinder("toSystem", PropertyBinder.instance(Boolean.TYPE, "toSystem"));
        }
        if (options.contains(Options.SCHEDULING)) {
            transferTypeBinder.registerBinder("allowsScheduledPayments", PropertyBinder.instance(Boolean.TYPE, "allowsScheduledPayments"));
        }
        if (options.contains(Options.CURRENCY)) {
            transferTypeBinder.registerBinder("currencyId", PropertyBinder.instance(Currency.class, "from.currency"));
        }
        return BeanCollectionBinder.instance(transferTypeBinder);
    }

    public TransferTypeService getTransferTypeService() {
        return transferTypeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    /**
     * May be overridden to retrieve a custom transfer type query
     */
    protected List<TransferType> executeQuery(final ActionContext context) {
        final SearchTransferTypesAjaxForm form = context.getForm();
        final TransferTypeQuery query = getQueryBinder().readFromString(form);

        final AccountOwner fromOwner = query.getFromOwner();
        final TransactionContext transactionContext = query.getContext();
        if (transactionContext != TransactionContext.ANY && transactionContext != TransactionContext.AUTOMATIC && transactionContext != TransactionContext.AUTOMATIC_LOAN) {
            if (form.isUseBy() && !context.getAccountOwner().equals(fromOwner) && fromOwner != null) {
                query.setBy(context.getElement());
            } else if (!form.isIgnoreGroup()) {
                if (form.isUseFromGroup() && fromOwner instanceof Member) {
                    final Member member = elementService.load(((Member) fromOwner).getId(), Element.Relationships.GROUP);
                    query.setGroup(member.getGroup());
                } else {
                    query.setGroup(context.getGroup());
                }
            }
            query.setUsePriority(!form.isIgnoreGroup());
        }
        return transferTypeService.search(query);
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final List<TransferType> transferTypes = executeQuery(context);
        final String json = getTransferTypeBinder(resolveOptions(context)).readAsString(transferTypes);
        responseHelper.writeJSON(context.getResponse(), json);
    }

    /**
     * May be overridden to resolve the rendering options
     */
    protected Options[] resolveOptions(final ActionContext context) {
        final SearchTransferTypesAjaxForm form = context.getForm();
        return form.getOptions();
    }
}
