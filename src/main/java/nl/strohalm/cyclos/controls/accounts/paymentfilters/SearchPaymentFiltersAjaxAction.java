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
package nl.strohalm.cyclos.controls.accounts.paymentfilters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery.Context;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;

/**
 * Searches payment filters and returns the list as an JSON
 * @author jefferson
 */
public class SearchPaymentFiltersAjaxAction extends BaseAjaxAction {

    private PaymentFilterService           paymentFilterService;
    private DataBinder<?>                  dataBinder;
    private DataBinder<PaymentFilterQuery> queryDataBinder;

    public DataBinder<?> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<PaymentFilter> binder = BeanBinder.instance(PaymentFilter.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("accountTypeId", PropertyBinder.instance(Long.class, "accountType.id"));
            binder.registerBinder("accountTypeName", PropertyBinder.instance(String.class, "accountType.name"));

            dataBinder = BeanCollectionBinder.instance(binder);
        }
        return dataBinder;
    }

    public DataBinder<PaymentFilterQuery> getQueryDataBinder() {
        if (queryDataBinder == null) {
            final BeanBinder<PaymentFilterQuery> binder = BeanBinder.instance(PaymentFilterQuery.class);
            binder.registerBinder("accountTypes", SimpleCollectionBinder.instance(AccountType.class, "accountTypeId"));
            binder.registerBinder("memberGroups", SimpleCollectionBinder.instance(MemberGroup.class, "memberGroups"));
            queryDataBinder = binder;
        }
        return queryDataBinder;
    }

    @Inject
    public void setPaymentFilterService(final PaymentFilterService paymentFilterService) {
        this.paymentFilterService = paymentFilterService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final SearchPaymentFiltersAjaxForm form = context.getForm();
        final PaymentFilterQuery pfQuery = getQueryDataBinder().readFromString(form);
        final Set<Relationship> fetch = new HashSet<Relationship>();
        fetch.add(PaymentFilter.Relationships.ACCOUNT_TYPE);
        pfQuery.setFetch(fetch);
        pfQuery.setContext(Context.REPORT);
        final List<PaymentFilter> paymentFilters = paymentFilterService.search(pfQuery);
        final String json = getDataBinder().readAsString(paymentFilters);
        responseHelper.writeJSON(context.getResponse(), json);
    }
}
