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
package nl.strohalm.cyclos.controls.mobile;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.mobile.exceptions.MobileException;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to perform a payment on mobile access
 * @author luis
 */
public class MobileDoPaymentAction extends MobileBaseAction {

    private ElementService      elementService;
    private PaymentService      paymentService;
    private TransferTypeService transferTypeService;

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public TransferTypeService getTransferTypeService() {
        return transferTypeService;
    }

    @Inject
    public void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward executeAction(final MobileActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final HttpSession session = request.getSession();
        if (RequestHelper.isGet(request)) {
            if (RequestHelper.isFromMenu(request)) {
                session.removeAttribute("mobileDoPaymentDTO");
            }
            // Form display: store the bookmark so, on errors, we will return to here
            storeBookmark(context);
            return context.getInputForward();
        } else {
            try {
                // Build the payment DTO
                final Member fromMember = context.getMember();
                final Member toMember = validateTo(context);
                final DoPaymentDTO payment = new DoPaymentDTO();
                payment.setChannel(context.getChannel());
                payment.setContext(TransactionContext.PAYMENT);
                payment.setFrom(fromMember);
                payment.setTo(toMember);
                payment.setAmount(validateAmount(context));
                payment.setDescription(validateDescription(context));
                payment.setTransferType(validateTransferType(context, toMember));

                // Check if the payment can be performed
                validatePayment(payment);

                // Store the DTO, so, the confirmation action will access it
                session.setAttribute("mobileDoPaymentDTO", payment);
            } catch (final MobileException e) {
                return MobileHelper.sendException(context.getActionMapping(), request, e);
            }
            return context.getSuccessForward();
        }
    }

    /**
     * Validate the typed amount
     */
    private BigDecimal validateAmount(final MobileActionContext context) {
        final MobileDoPaymentForm form = context.getForm();
        final UnitsConverter converter = getUnitsConverter(context);
        BigDecimal amount = null;
        try {
            amount = converter.valueOf(form.getAmount());
        } catch (final Exception e) {
            throw new MobileException("errors.invalid", context.message("mobile.payment.amount"));
        }
        if (amount == null) {
            throw new MobileException("errors.required", context.message("mobile.payment.amount"));
        }
        if (amount.compareTo(paymentService.getMinimumPayment()) == -1) {
            throw new MobileException("errors.invalid", context.message("mobile.payment.amount"));
        }
        return amount;
    }

    /**
     * Validate the typed description
     */
    private String validateDescription(final MobileActionContext context) {
        final MobileDoPaymentForm form = context.getForm();
        return StringUtils.trimToNull(form.getDescription());
    }

    /**
     * Ensure the payment can be performed
     */
    private void validatePayment(final DoPaymentDTO payment) {
        String key = null;
        String arg = null;
        try {
            paymentService.simulatePayment(payment);
        } catch (final NotEnoughCreditsException e) {
            key = "mobile.payment.error.notEnoughCredits";
        } catch (final MaxAmountPerDayExceededException e) {
            final Calendar date = e.getDate();
            if (date == null || DateHelper.sameDay(date, Calendar.getInstance())) {
                key = "mobile.payment.error.maxAmountPerDay";
            } else {
                key = "mobile.payment.error.maxAmountPerDay.at";
                arg = settingsService.getLocalSettings().getRawDateConverter().toString(date);
            }
        } catch (final UnexpectedEntityException e) {
            key = "mobile.payment.error.noTransferType";
        }
        if (key != null) {
            throw new MobileException(key, arg);
        }
    }

    /**
     * Validate the typed username
     */
    private Member validateTo(final MobileActionContext context) {
        final MobileDoPaymentForm form = context.getForm();
        final String username = form.getUsername();
        if (StringUtils.isEmpty(username)) {
            throw new MobileException("errors.required", context.message("mobile.payment.username"));
        }
        User user;
        try {
            user = elementService.loadUser(username, User.Relationships.ELEMENT);
            if (!(user instanceof MemberUser) || context.getUser().equals(user)) {
                throw new Exception();
            }
        } catch (final Exception e) {
            throw new MobileException("errors.invalid", context.message("mobile.payment.username"));
        }
        return (Member) user.getElement();
    }

    /**
     * Retrieve the transfer type used for the payment
     */
    private TransferType validateTransferType(final MobileActionContext context, final AccountOwner to) {
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setUniqueResult();
        query.setFromAccountType(context.getCurrentAccountType());
        query.setChannel(context.getChannel());
        query.setContext(TransactionContext.PAYMENT);
        query.setFromOwner(context.getAccountOwner());
        query.setToOwner(to);
        query.setUsePriority(true);
        final List<TransferType> types = transferTypeService.search(query);
        if (types == null || types.isEmpty()) {
            throw new MobileException("mobile.payment.error.noTransferType");
        }
        return types.get(0);
    }

}
