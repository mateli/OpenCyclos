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
package nl.strohalm.cyclos.controls.accounts.guarantees.paymentobligations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligationQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.accounts.guarantees.CertificationService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.guarantees.PaymentObligationService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

public class SearchPaymentObligationsAction extends BaseQueryAction {
    private PaymentObligationService           paymentObligationService;
    private GuaranteeService                   guaranteeService;
    private CertificationService               certificationService;
    private DataBinder<PaymentObligationQuery> dataBinder;
    private CurrencyService                    currencyService;

    public CertificationService getCertificationService() {
        return certificationService;
    }

    @Inject
    public void setCertificationService(final CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Inject
    public void setPaymentObligationService(final PaymentObligationService paymentObligationService) {
        this.paymentObligationService = paymentObligationService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final PaymentObligationQuery query = (PaymentObligationQuery) queryParameters;
        final List<PaymentObligation> paymentObligations = paymentObligationService.search(query);
        request.setAttribute("currencies", currencyService.listAll());
        request.setAttribute("paymentObligations", paymentObligations);

        context.getSession().setAttribute("executePaymentObligationsQuery", true);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final SearchPaymentObligationsForm form = context.getForm();
        final PaymentObligationQuery query = getDataBinder().readFromString(form.getQuery());

        final boolean isBuyer = (Boolean) context.getSession().getAttribute("isBuyer");
        final boolean isSeller = (Boolean) context.getSession().getAttribute("isSeller");
        final boolean hasViewPermission = permissionService.hasPermission(AdminMemberPermission.GUARANTEES_VIEW_PAYMENT_OBLIGATIONS);

        final boolean showBuyer = isSeller || hasViewPermission;
        final boolean showSeller = isBuyer || hasViewPermission;

        request.setAttribute("hasViewPermission", hasViewPermission);
        request.setAttribute("showBuyer", showBuyer);
        request.setAttribute("showSeller", showSeller);
        request.setAttribute("buyerFiltered", query.getBuyer() != null);
        request.setAttribute("currencyFiltered", query.getCurrency() != null);
        request.setAttribute("publishedStatus", PaymentObligation.Status.PUBLISHED);
        request.setAttribute("registeredStatus", PaymentObligation.Status.REGISTERED);
        request.setAttribute("buyerGroupIds", showBuyer ? EntityHelper.toIdsAsString(guaranteeService.getBuyers()) : "[]");
        request.setAttribute("sellerGroupIds", showSeller ? EntityHelper.toIdsAsString(guaranteeService.getSellers()) : "[]");
        request.setAttribute("currencies", currencyService.listAll());
        request.setAttribute("status", paymentObligationService.getStatusToFilter());

        boolean showNewPaymentObligationButton = false;
        if (isBuyer) {
            final CertificationQuery certificationQuery = new CertificationQuery();
            certificationQuery.setBuyer((Member) context.getAccountOwner());
            certificationQuery.setStatusList(Collections.singletonList(Certification.Status.ACTIVE));
            final List<Certification> certifications = certificationService.search(certificationQuery);
            showNewPaymentObligationButton = certifications.size() > 0;
        }

        request.setAttribute("showNewPaymentObligationButton", showNewPaymentObligationButton);

        query.setBuyer(ensureFilter(query.getBuyer()));
        query.setSeller(ensureFilter(query.getSeller()));

        if (RequestHelper.isFromMenu(request)) { // reset the flag
            context.getSession().setAttribute("executePaymentObligationsQuery", false);
        }

        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        final boolean b = super.willExecuteQuery(context, queryParameters);
        final Object executeQuery = context.getSession().getAttribute("executePaymentObligationsQuery");
        return b || executeQuery != null && (Boolean) executeQuery;
    }

    private Member ensureFilter(final Member filter) {
        return filter == null ? null : (Member) elementService.load(filter.getId());
    }

    private DataBinder<PaymentObligationQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<PaymentObligationQuery> binder = BeanBinder.instance(PaymentObligationQuery.class);
            binder.registerBinder("expiration", DataBinderHelper.periodBinder(localSettings, "expiration"));
            binder.registerBinder("buyer", PropertyBinder.instance(Member.class, "buyer"));
            binder.registerBinder("seller", PropertyBinder.instance(Member.class, "seller"));
            binder.registerBinder("statusList", SimpleCollectionBinder.instance(PaymentObligation.Status.class, "statusList"));
            binder.registerBinder("amountLowerLimit", PropertyBinder.instance(BigDecimal.class, "amountLowerLimit", localSettings.getNumberConverter()));
            binder.registerBinder("amountUpperLimit", PropertyBinder.instance(BigDecimal.class, "amountUpperLimit", localSettings.getNumberConverter()));
            binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());

            dataBinder = binder;
        }

        return dataBinder;
    }
}
