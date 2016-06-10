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
package nl.strohalm.cyclos.controls.invoices;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.controls.payments.SchedulingType;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoicePayment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.transactions.InvoiceService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transactions.exceptions.SendingInvoiceWithMultipleTransferTypesWithCustomFields;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.AccountOwnerConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to send an invoice
 * @author luis
 */
public class SendInvoiceAction extends BaseFormAction {

    private AccountTypeService  accountTypeService;
    private DataBinder<Invoice> dataBinder;
    private InvoiceService      invoiceService;
    private TransferTypeService transferTypeService;
    private CurrencyService     currencyService;

    public AccountTypeService getAccountTypeService() {
        return accountTypeService;
    }

    public DataBinder<Invoice> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(PaymentCustomFieldValue.class);
            customValueBinder.registerBinder("field", PropertyBinder.instance(PaymentCustomField.class, "field"));
            customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));

            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<Invoice> binder = BeanBinder.instance(Invoice.class);
            binder.registerBinder("from", PropertyBinder.instance(AccountOwner.class, "from", AccountOwnerConverter.instance()));
            binder.registerBinder("to", PropertyBinder.instance(AccountOwner.class, "to", AccountOwnerConverter.instance()));
            binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "type", ReferenceConverter.instance(TransferType.class)));
            binder.registerBinder("destinationAccountType", PropertyBinder.instance(AccountType.class, "destType", ReferenceConverter.instance(AccountType.class)));
            binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));

            final BeanBinder<InvoicePayment> scheduledPayments = BeanBinder.instance(InvoicePayment.class);
            scheduledPayments.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
            scheduledPayments.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
            binder.registerBinder("payments", BeanCollectionBinder.instance(scheduledPayments, "payments"));

            dataBinder = binder;
        }
        return dataBinder;
    }

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public TransferTypeService getTransferTypeService() {
        return transferTypeService;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setInvoiceService(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SendInvoiceForm form = context.getForm();
        final boolean toSystem = form.isToSystem();
        final boolean selectMember = form.isSelectMember();

        AccountOwner to;
        final Member fromMember = (form.getFrom() == null) ? null : (Member) elementService.load(Long.valueOf(form.getFrom()));
        final Element loggedElement = context.getElement();
        if (toSystem) {
            // System invoice
            to = SystemAccountOwner.instance();
        } else {
            if (!selectMember) {
                // Retrieve the member to send invoice for
                Member member = null;
                final Long memberId = IdConverter.instance().valueOf(form.getTo());
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
                to = member;
            } else {
                // The member will be selected later
                to = null;
            }
        }

        // If we know who will receive the invoice, get the transfer types or dest account types
        if (to != null) {
            if (context.isAdmin() && fromMember == null) {
                // Only admins may select the transfer type
                final TransferTypeQuery query = new TransferTypeQuery();
                query.setChannel(Channel.WEB);
                query.setContext(TransactionContext.PAYMENT);
                query.setFromOwner(to);
                query.setToOwner(context.getAccountOwner());
                query.setUsePriority(true);
                request.setAttribute("transferTypes", transferTypeService.search(query));
            } else {
                // Members may select the destination account type
                final MemberAccountTypeQuery query = new MemberAccountTypeQuery();
                query.setOwner(fromMember == null ? (Member) loggedElement.getAccountOwner() : fromMember);
                query.setCanPay(to);
                final List<? extends AccountType> accountTypes = accountTypeService.search(query);
                if (accountTypes.isEmpty()) {
                    return context.sendError("invoice.error.noAccountType");
                }
                request.setAttribute("accountTypes", accountTypes);
            }
        }

        // Resolve the possible currencies
        final MemberGroup group = getMemberGroup(context);
        final List<Currency> currencies;
        if (group != null) {
            currencies = currencyService.listByMemberGroup(group);
            final MemberAccountType defaultAccountType = accountTypeService.getDefault(group, AccountType.Relationships.CURRENCY);
            // Preselect the default currency
            if (defaultAccountType != null) {
                form.setCurrency(CoercionHelper.coerce(String.class, defaultAccountType.getCurrency()));
            }
        } else {
            currencies = currencyService.listAll();
        }
        request.setAttribute("currencies", currencies);

        if (currencies.isEmpty()) {
            // No currencies means no possible payment!!!
            throw new ValidationException("payment.error.noTransferType");
        } else if (currencies.size() == 1) {
            // Special case: There is a single currency. The JSP will use this object
            request.setAttribute("singleCurrency", currencies.get(0));
        }

        request.setAttribute("toSystem", toSystem);
        request.setAttribute("toMember", !toSystem);
        request.setAttribute("selectMember", selectMember);
        request.setAttribute("from", fromMember);

        final boolean useTransferType = context.isAdmin() && fromMember == null;
        request.setAttribute("useTransferType", useTransferType);

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

        return context.getInputForward();
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final SendInvoiceForm form = context.getForm();
        final boolean fromProfile = !form.isToSystem() && !form.isSelectMember();

        try {
            final Invoice invoice = invoiceService.send(resolveInvoice(context));
            context.sendMessage("invoice.sent");
            ActionForward forward = null;
            final Map<String, Object> params = new HashMap<String, Object>();
            if (fromProfile) {
                forward = context.findForward("profile");
                params.put("memberId", invoice.getToMember().getId());
            } else {
                forward = context.findForward("newInvoice");
            }
            final Member fromMember = invoice.getFromMember();
            if (fromMember != null && !fromMember.equals(context.getMember())) {
                // From another member
                params.put("from", form.getFrom());
            } else if (fromProfile) {
                params.put("to", form.getTo());
            }
            if (form.isToSystem()) {
                params.put("toSystem", true);
            } else if (form.isSelectMember()) {
                params.put("selectMember", true);
            }
            return ActionHelper.redirectWithParams(context.getRequest(), forward, params);
        } catch (final SendingInvoiceWithMultipleTransferTypesWithCustomFields e) {
            return context.sendError("invoice.error.sendingWithMultipleTransferTypesWithCustomFields");
        }
    }

    @Override
    protected ActionForward handleValidation(final ActionContext context) {
        try {
            final Invoice invoice = resolveInvoice(context);
            invoiceService.validate(invoice);

            // Retrive and fetch the destination account type
            AccountType accountType = invoice.getDestinationAccountType();
            if (accountType == null) {
                final TransferType tt = transferTypeService.load(invoice.getTransferType().getId(), RelationshipHelper.nested(TransferType.Relationships.TO, AccountType.Relationships.CURRENCY));
                accountType = tt.getTo();
            } else {
                accountType = accountTypeService.load(accountType.getId());
            }

            // If the validation passed, resolve the confirmation message
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final UnitsConverter unitsConverter = localSettings.getUnitsConverter(accountType.getCurrency().getPattern());

            final AccountOwner toOwner = invoice.getTo();
            final boolean toSystem = toOwner instanceof SystemAccountOwner;

            // Retrieve the message arguments
            String to;
            if (toSystem) {
                to = localSettings.getApplicationUsername();
            } else {
                final Member member = elementService.load(((Member) toOwner).getId());
                to = member.getName();
            }
            final String amount = unitsConverter.toString(invoice.getAmount());

            final String confirmationKey = "invoice.sendConfirmationMessage";

            final Map<String, Object> fields = new HashMap<String, Object>();
            fields.put("confirmationMessage", context.message(confirmationKey, to, amount));

            responseHelper.writeStatus(context.getResponse(), ResponseHelper.Status.SUCCESS, fields);

        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }
        return null;
    }

    private MemberGroup getMemberGroup(final ActionContext context) {
        final SendInvoiceForm form = context.getForm();
        final Long fromId = IdConverter.instance().valueOf(form.getFrom());
        final Long toId = IdConverter.instance().valueOf(form.getTo());
        Group group = null;
        if (fromId == null && toId == null) {
            group = context.getGroup();
        } else if (fromId != null) {
            final Element element = elementService.load(fromId, Element.Relationships.GROUP);
            group = element.getGroup();
        } else {
            final Element element = elementService.load(toId, Element.Relationships.GROUP);
            group = element.getGroup();
        }
        if (group instanceof MemberGroup) {
            return (MemberGroup) group;
        }
        return null;
    }

    private Invoice resolveInvoice(final ActionContext context) {
        final SendInvoiceForm form = context.getForm();
        final Invoice invoice = getDataBinder().readFromString(form);
        if ((context.isMember() && invoice.getFromMember() == null) || context.isOperator()) {
            invoice.setFrom(context.getAccountOwner());
        }
        return invoice;
    }

}
