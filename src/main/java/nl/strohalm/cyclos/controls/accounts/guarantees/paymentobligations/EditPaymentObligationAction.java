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
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligationLog;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.accounts.guarantees.CertificationService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.guarantees.PaymentObligationService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ActionHelper.ByElementExtractor;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.struts.action.ActionForward;

public class EditPaymentObligationAction extends BaseFormAction {

    private DataBinder<PaymentObligation> dataBinder;
    private PaymentObligationService      paymentObligationService;
    private CurrencyService               currencyService;
    private GuaranteeService              guaranteeService;
    private CertificationService          certificationService;

    public DataBinder<PaymentObligation> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<PaymentObligation> binder = BeanBinder.instance(PaymentObligation.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("status", PropertyBinder.instance(PaymentObligation.Status.class, "status"));
            binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
            binder.registerBinder("buyer", PropertyBinder.instance(Member.class, "buyer"));
            binder.registerBinder("seller", PropertyBinder.instance(Member.class, "seller"));
            binder.registerBinder("expirationDate", PropertyBinder.instance(Calendar.class, "expirationDate", localSettings.getRawDateConverter()));
            binder.registerBinder("maxPublishDate", PropertyBinder.instance(Calendar.class, "maxPublishDate", localSettings.getRawDateConverter()));
            binder.registerBinder("registrationDate", PropertyBinder.instance(Calendar.class, "registrationDate", localSettings.getRawDateConverter()));
            binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("guarantee", PropertyBinder.instance(Guarantee.class, "guarantee"));
            dataBinder = binder;
        }
        return dataBinder;
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
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditPaymentObligationForm form = context.getForm();
        final PaymentObligation paymentObligation = getDataBinder().readFromString(form.getPaymentObligation());
        final boolean isInsert = paymentObligation.isTransient();
        initialize(paymentObligation, context);
        paymentObligationService.save(paymentObligation, true);
        context.sendMessage(isInsert ? "paymentObligation.inserted" : "paymentObligation.modified");

        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "paymentObligationId", paymentObligation.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditPaymentObligationForm form = context.getForm();
        final long id = form.getPaymentObligationId();
        final boolean isInsert = id <= 0L;
        Long guaranteeId = -1L;

        final boolean isBuyer = (Boolean) context.getSession().getAttribute("isBuyer");
        boolean isEditable = isInsert || isBuyer;

        PaymentObligation paymentObligation;
        Collection<Currency> currencies = null;
        if (isInsert) {
            // If the logged user is an administrator or is not a buyer, he(she) can not create payment obligations
            if (context.isAdmin() || !isBuyer) {
                throw new PermissionDeniedException();
            }
            currencies = resolveAvailableCurrencies(context, null);
        } else {
            paymentObligation = paymentObligationService.load(id, PaymentObligation.Relationships.GUARANTEE, PaymentObligation.Relationships.BUYER, PaymentObligation.Relationships.SELLER);
            currencies = resolveAvailableCurrencies(context, paymentObligation);

            if (paymentObligation.getStatus() != PaymentObligation.Status.REGISTERED || !paymentObligation.getBuyer().equals(context.getAccountOwner())) {
                isEditable = false;
            }
            if (paymentObligation.getGuarantee() != null) {
                guaranteeId = paymentObligation.getGuarantee().getId();
            }
            final ByElementExtractor extractor = new ByElementExtractor() {
                @Override
                public Element getByElement(final Entity entity) {
                    return ((PaymentObligationLog) entity).getBy();
                }
            };

            final boolean canAccept = paymentObligationService.canChangeStatus(paymentObligation, PaymentObligation.Status.ACCEPTED);
            if (canAccept) {
                final List<Member> issuers = certificationService.getCertificationIssuers(paymentObligation);
                request.setAttribute("issuers", issuers);
            }

            getDataBinder().writeAsString(form.getPaymentObligation(), paymentObligation);

            request.setAttribute("paymentObligation", paymentObligation);
            request.setAttribute("canPublish", paymentObligationService.canChangeStatus(paymentObligation, PaymentObligation.Status.PUBLISHED));
            request.setAttribute("canConceal", paymentObligationService.canChangeStatus(paymentObligation, PaymentObligation.Status.REGISTERED));
            request.setAttribute("canReject", paymentObligationService.canChangeStatus(paymentObligation, PaymentObligation.Status.REJECTED));
            request.setAttribute("canAccept", canAccept);
            request.setAttribute("canDelete", paymentObligationService.canDelete(paymentObligation));
            request.setAttribute("logsBy", ActionHelper.getByElements(context, paymentObligation.getLogs(), extractor));
        }
        request.setAttribute("guaranteeId", guaranteeId);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("isEditable", isEditable);
        request.setAttribute("currencies", currencies);

        if (currencies.size() == 1) { // this is to show the currency's symbol as a label
            request.setAttribute("singleCurrency", currencies.iterator().next());
        }

        if (isEditable) {
            final Collection<? extends Group> sellers = guaranteeService.getSellers();
            request.setAttribute("sellerGroupsId", EntityHelper.toIdsAsString(sellers));
        } else {
            request.setAttribute("sellerGroupsId", "[]");
        }
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditPaymentObligationForm form = context.getForm();
        final PaymentObligation paymentObligation = getDataBinder().readFromString(form.getPaymentObligation());
        initialize(paymentObligation, context);
        paymentObligationService.validate(paymentObligation);
    }

    /*
     * Sets all the not showed data in the view
     */
    private void initialize(final PaymentObligation paymentObligation, final ActionContext context) {
        paymentObligation.setBuyer(EntityHelper.reference(Member.class, ((Member) context.getAccountOwner()).getId()));
    }

    private Collection<Currency> resolveAvailableCurrencies(final ActionContext context, final PaymentObligation paymentObligation) {
        if (context.isAdmin()) {
            return currencyService.listAll();
        }
        Collection<Currency> currencies = new HashSet<Currency>();
        Member member = (Member) context.getAccountOwner();
        member = elementService.load(member.getId(), Element.Relationships.GROUP);
        if (paymentObligation == null || member.equals(paymentObligation.getBuyer())) {
            // The member is the buyer (or an operator of the buyer) of the payment obligation
            final CertificationQuery query = new CertificationQuery();
            query.setBuyer((Member) context.getAccountOwner());
            query.setStatusList(Collections.singletonList(Certification.Status.ACTIVE));
            final List<Certification> certifications = certificationService.search(query);
            for (final Certification certification : certifications) {
                currencies.add(certification.getGuaranteeType().getCurrency());
            }
        } else {
            // The member is the seller (or an operator of the seller) of the payment obligation
            final MemberGroup memberGroup = member.getMemberGroup();
            currencies = currencyService.listByMemberGroup(memberGroup);
        }
        return currencies;
    }

}
