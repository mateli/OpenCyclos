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

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.accounts.guarantees.PaymentObligationPackDTO;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.ActiveCertificationNotFoundException;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.CertificationAmountExceededException;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.CertificationValidityExceededException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

import org.apache.struts.action.ActionForward;

public class AcceptPaymentObligationAction extends BaseAction {

    private GuaranteeService guaranteeService;

    @Inject
    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final AcceptPaymentObligationForm form = context.getForm();
        final Long issuerId = form.getIssuerId();
        final Long paymentObligationId = form.getPaymentObligationId();
        try {
            final Guarantee guarantee = requestGuarantee(paymentObligationId, EntityHelper.reference(Member.class, issuerId));
            context.sendMessage("paymentObligation.requestGuaranteeOk", context.message("guarantee.status." + guarantee.getStatus()));
        } catch (final ActiveCertificationNotFoundException e) {
            context.sendMessage("paymentObligation.error.noActiveCertificationFound", e.getBuyer(), e.getIssuer());
        } catch (final CertificationAmountExceededException e) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final UnitsConverter converter = localSettings.getUnitsConverter(e.getCertification().getGuaranteeType().getCurrency().getPattern());
            context.sendMessage("paymentObligation.error.certificationAmountExceeded", converter.toString(e.getRemainingCertificationAmount()), converter.toString(e.getTotalExceededAmount()));
        } catch (final CertificationValidityExceededException e) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final CalendarConverter converter = localSettings.getRawDateConverter();

            final String validityBegin = converter.toString(e.getCertification().getValidity().getBegin());
            final String validityEnd = converter.toString(e.getCertification().getValidity().getEnd());

            context.sendMessage("paymentObligation.error.certificationValidityExceeded", validityBegin, validityEnd);
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "paymentObligationId", paymentObligationId);
    }

    private Guarantee requestGuarantee(final Long id, final Member issuer) {
        final PaymentObligationPackDTO pack = new PaymentObligationPackDTO();
        pack.setIssuer(issuer);
        pack.setPaymentObligations(new Long[] { id });

        return guaranteeService.requestGuarantee(pack);
    }
}
