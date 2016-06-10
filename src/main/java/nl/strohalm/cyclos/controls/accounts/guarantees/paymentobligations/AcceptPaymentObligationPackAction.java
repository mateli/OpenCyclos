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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.guarantees.CertificationService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.guarantees.PaymentObligationPackDTO;
import nl.strohalm.cyclos.services.accounts.guarantees.PaymentObligationService;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.ActiveCertificationNotFoundException;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.CertificationAmountExceededException;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.CertificationValidityExceededException;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

import org.apache.struts.action.ActionForward;

public class AcceptPaymentObligationPackAction extends BaseFormAction {
    private PaymentObligationService paymentObligationService;
    private CertificationService     certificationService;
    private GuaranteeService         guaranteeService;

    @Inject
    public void setCertificationService(final CertificationService certificationService) {
        this.certificationService = certificationService;
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
        final AcceptPaymentObligationPackForm form = context.getForm();
        final Long[] ids = form.getPaymentObligationIds();
        final Long issuerId = form.getIssuerId();

        try {
            final Guarantee guarantee = requestGuarantee(ids, EntityHelper.reference(Member.class, issuerId));
            context.sendMessage("paymentObligation.requestGuaranteeOk", context.message("guarantee.status." + guarantee.getStatus()));
            return context.getSuccessForward();
        } catch (final ActiveCertificationNotFoundException e) {
            context.sendMessage("paymentObligation.error.noActiveCertificationFound", e.getBuyer(), e.getIssuer());

            acceptPaymentObligation(context);
            return context.getInputForward();
        } catch (final CertificationAmountExceededException e) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final UnitsConverter converter = localSettings.getUnitsConverter(e.getCertification().getGuaranteeType().getCurrency().getPattern());
            context.sendMessage("paymentObligation.error.certificationAmountExceeded", converter.toString(e.getRemainingCertificationAmount()), converter.toString(e.getTotalExceededAmount()));

            acceptPaymentObligation(context);
            return context.getInputForward();
            // return context.sendError("paymentObligation.error.certificationAmountExceeded",
            // converter.toString(e.getRemainingCertificationAmount()), converter.toString(e.getTotalExceededAmount()));
        } catch (final CertificationValidityExceededException e) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final CalendarConverter converter = localSettings.getRawDateConverter();

            final String validityBegin = converter.toString(e.getCertification().getValidity().getBegin());
            final String validityEnd = converter.toString(e.getCertification().getValidity().getEnd());

            context.sendMessage("paymentObligation.error.certificationValidityExceeded", validityBegin, validityEnd);

            acceptPaymentObligation(context);
            return context.getInputForward();
        }
    }

    @Override
    protected boolean isFormPreparation(final ActionContext context) {
        return super.isFormPreparation(context) || Boolean.valueOf(context.getRequest().getParameter("selectIssuer"));
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        acceptPaymentObligation(context);
    }

    private void acceptPaymentObligation(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final AcceptPaymentObligationPackForm form = context.getForm();
        final Long[] ids = form.getPaymentObligationIds();

        if (ids == null || ids.length == 0) {
            return; // nothing to accept!
        }

        final List<PaymentObligation> paymentObligations = loadPaymentObligations(ids);

        final PaymentObligation po = paymentObligations.get(0);
        final List<Member> issuers = certificationService.getCertificationIssuers(po);

        request.setAttribute("paymentObligations", paymentObligations);
        request.setAttribute("paymentObligationsTotalAmount", calcTotalAmount(paymentObligations));

        if (issuers == null || issuers.size() == 0) {
            return; // nothing to accept!
        }

        final Member issuer = getIssuer(issuers, form);

        final Long[] exceeded = checkPaymentObligationPeriod(ids, issuer);
        final String paymentObligationIds = arrayToString(exceeded);
        final Certification certification = certificationService.getActiveCertification(po.getCurrency(), po.getBuyer(), issuer);
        request.setAttribute("certification", certification);
        request.setAttribute("issuers", issuers);
        request.setAttribute("paymentObligationIds", paymentObligationIds);
        request.setAttribute("paymentObligationExceeded", exceeded.length > 0);
    }

    private String arrayToString(final Long[] ids) {
        if (ids == null || ids.length == 0) {
            return "[]";
        } else {
            return RequestHelper.arrayToString(ids);
        }
    }

    private BigDecimal calcTotalAmount(final List<PaymentObligation> paymentObligations) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final PaymentObligation paymentObligation : paymentObligations) {
            sum = sum.add(paymentObligation.getAmount());
        }

        return sum;
    }

    private Long[] checkPaymentObligationPeriod(final Long[] ids, final Member issuer) {
        final PaymentObligationPackDTO dto = new PaymentObligationPackDTO();
        dto.setIssuer(issuer);
        dto.setPaymentObligations(ids);

        final Long[] exceeded = paymentObligationService.checkPaymentObligationPeriod(dto);
        return exceeded;
    }

    private Member getIssuer(final List<Member> issuers, final AcceptPaymentObligationPackForm form) {
        if (form.getIssuerId() == null) {
            return issuers.get(0);
        } else {
            return EntityHelper.reference(Member.class, form.getIssuerId());
        }
    }

    private List<PaymentObligation> loadPaymentObligations(final Long[] ids) {
        if (ids == null || ids.length == 0) {
            return Collections.emptyList();
        }

        final List<PaymentObligation> paymentObligations = new ArrayList<PaymentObligation>();
        for (final Long element : ids) {
            paymentObligations.add(paymentObligationService.load(element));
        }
        return paymentObligations;
    }

    private Guarantee requestGuarantee(final Long[] ids, final Member issuer) {
        final PaymentObligationPackDTO pack = new PaymentObligationPackDTO();
        pack.setIssuer(issuer);
        pack.setPaymentObligations(ids);

        return guaranteeService.requestGuarantee(pack);
    }
}
