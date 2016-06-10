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
package nl.strohalm.cyclos.controls.payments;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentDTO;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.AccountOwnerConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action for performing payments from an account owner to another one
 * @author luis
 */
public class PaymentAction extends BasePaymentAction {

    @Override
    protected AccountOwner getFromOwner(final ActionContext context) {
        final PaymentForm form = context.getForm();
        final Long fromId = IdConverter.instance().valueOf(form.getFrom());
        if (fromId == null) {
            return context.getAccountOwner();
        }
        final Element element = elementService.load(fromId, Element.Relationships.GROUP);
        if (element instanceof Member) {
            return (Member) element;
        }
        return null;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final PaymentForm form = context.getForm();
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("selectMember", form.isSelectMember());
        params.put("toSystem", form.isToSystem());
        params.put("from", form.getFrom());
        return ActionHelper.redirectWithParams(context.getRequest(), super.handleSubmit(context), params);
    }

    @Override
    protected DataBinder<DoPaymentDTO> initDataBinder() {
        final BeanBinder<DoPaymentDTO> binder = (BeanBinder<DoPaymentDTO>) super.initDataBinder();
        binder.registerBinder("to", PropertyBinder.instance(AccountOwner.class, "to", AccountOwnerConverter.instance()));

        final LocalSettings localSettings = settingsService.getLocalSettings();

        final BeanBinder<ScheduledPaymentDTO> scheduledPayments = BeanBinder.instance(ScheduledPaymentDTO.class);
        scheduledPayments.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
        scheduledPayments.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
        binder.registerBinder("payments", BeanCollectionBinder.instance(scheduledPayments, "payments"));
        return binder;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        super.prepareForm(context);
        final HttpServletRequest request = context.getRequest();
        final PaymentForm form = context.getForm();

        String titleKey = null;

        final boolean toSystem = form.isToSystem();
        final boolean selectMember = form.isSelectMember();

        final boolean asMember = (Boolean) request.getAttribute("asMember");
        final Member fromMember = (Member) request.getAttribute("member");

        if (toSystem) {
            // Payment to system
            if (asMember) {
                // from selected member to system
                if (context.isAdmin()) {
                    // admin making member to system payment
                    titleKey = "payment.title.systemMemberToSystem";
                } else {
                    // broker making member to system payment
                    titleKey = "payment.title.brokerMemberToSystem";
                }
            } else {
                // payment from logged member to system
                titleKey = "payment.title.memberToSystem";
            }
        } else if (selectMember) {
            // Payment to an yet unknown member
            if (asMember) {
                // from selected member to another member
                if (context.isAdmin()) {
                    // admin making member to another member
                    titleKey = "payment.title.systemMemberToMember";
                } else {
                    // broker making member to another member
                    titleKey = "payment.title.brokerMemberToMember";
                }
            } else {
                // from logged user to another member
                if (context.isAdmin()) {
                    titleKey = "payment.title.systemToMember";
                } else {
                    titleKey = "payment.title.memberToMember";
                }
            }
        } else {
            // Payment to a pre-selected member
            Member member = null;
            final Long memberId = IdConverter.instance().valueOf(form.getTo());
            final Element loggedElement = context.getElement();
            if (memberId != null && memberId != loggedElement.getId()) {
                final Element element = elementService.load(memberId, Element.Relationships.USER);
                if (element instanceof Member) {
                    member = (Member) element;
                }
            }
            if (member == null) {
                throw new ValidationException();
            }
            request.setAttribute("member", member);

            if (context.isAdmin()) {
                titleKey = "payment.title.systemToMember";
            } else {
                titleKey = "payment.title.memberToMember";
            }
        }

        request.setAttribute("titleKey", titleKey);
        request.setAttribute("toSystem", toSystem);
        request.setAttribute("toMember", !toSystem);
        request.setAttribute("selectMember", selectMember);
        request.setAttribute("currentTime", System.currentTimeMillis());
        request.setAttribute("fromMember", fromMember);

        // Check whether scheduled payments may be performed
        boolean allowsScheduling = false;
        boolean allowsMultipleScheduling = false;
        if (context.isAdmin() && fromMember == null) {
            allowsScheduling = true;
            allowsMultipleScheduling = true;
        } else {
            MemberGroup memberGroup;
            if (fromMember == null) {
                memberGroup = ((Member) context.getAccountOwner()).getMemberGroup();
            } else {
                memberGroup = fromMember.getMemberGroup();
            }
            final MemberGroupSettings memberSettings = memberGroup.getMemberSettings();
            allowsScheduling = memberSettings.isAllowsScheduledPayments();
            allowsMultipleScheduling = memberSettings.isAllowsMultipleScheduledPayments();
        }
        if (allowsScheduling) {
            request.setAttribute("allowsScheduling", allowsScheduling);
            request.setAttribute("allowsMultipleScheduling", allowsMultipleScheduling);
            final Collection<SchedulingType> schedulingTypes = EnumSet.of(SchedulingType.IMMEDIATELY, SchedulingType.SINGLE_FUTURE);
            if (allowsMultipleScheduling) {
                schedulingTypes.add(SchedulingType.MULTIPLE_FUTURE);
            }
            request.setAttribute("schedulingTypes", schedulingTypes);
            request.setAttribute("schedulingFields", Arrays.asList(TimePeriod.Field.MONTHS, TimePeriod.Field.WEEKS, TimePeriod.Field.DAYS));
        }
    }

    /**
     * Reads the payment DTO from the form
     */
    @Override
    protected DoPaymentDTO resolvePaymentDTO(final ActionContext context) {
        final DoPaymentDTO dto = super.resolvePaymentDTO(context);
        dto.setContext(TransactionContext.PAYMENT);
        final PaymentForm form = context.getForm();
        if (form.isToSystem()) {
            dto.setTo(SystemAccountOwner.instance());
        }
        // When there is a single payment scheduled for today, remove it, making the payment to be processed now
        final List<ScheduledPaymentDTO> payments = dto.getPayments();
        if (payments != null && payments.size() == 1) {
            final ScheduledPaymentDTO payment = payments.get(0);
            if (DateUtils.isSameDay(Calendar.getInstance(), payment.getDate())) {
                // A single payment scheduled for today is handled as not scheduled
                dto.setPayments(null);
            }
        }
        return dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TransferTypeQuery resolveTransferTypeQuery(final ActionContext context) {

        final PaymentForm form = context.getForm();
        final Long fromId = IdConverter.instance().valueOf(form.getFrom());
        final Long toId = IdConverter.instance().valueOf(form.getTo());

        final boolean fromMe = fromId == null;
        final boolean asMember = !fromMe;
        final boolean toSpecificMember = !form.isSelectMember() && !form.isToSystem();
        final boolean toUnknownMember = form.isSelectMember();
        final boolean toSystem = form.isToSystem();

        if (toUnknownMember || (asMember && !toSystem)) {
            // Since we don't know who will receive the payment yet, we cannot resolve transfer types
            return null;
        }

        // Check the preselected currency
        Currency currency = CoercionHelper.coerce(Currency.class, form.getCurrency());

        // When there's none, use the first one
        if (currency == null) {
            final HttpServletRequest request = context.getRequest();
            final Collection<Currency> currencies = (Collection<Currency>) request.getAttribute("currencies");
            if (!currencies.isEmpty()) {
                currency = currencies.iterator().next();
            }
        }

        // Build the query
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setChannel(Channel.WEB);
        query.setContext(TransactionContext.PAYMENT);
        query.setUsePriority(true);
        // query.setCurrency(currency);

        // Determine the from
        if (fromMe) {
            query.setGroup(context.getGroup());
            query.setFromOwner(context.getAccountOwner());
        } else {
            query.setBy(context.getElement());
            query.setFromOwner(EntityHelper.reference(Member.class, fromId));
        }

        // Determine the to
        if (toSystem) {
            query.setToOwner(SystemAccountOwner.instance());
        } else if (toSpecificMember) {
            query.setToOwner(EntityHelper.reference(Member.class, toId));
        }

        return query;
    }
}
