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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.AccountOwnerConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;

/**
 * Base action for payments
 * @author luis
 */
public abstract class BasePaymentAction extends BaseFormAction implements LocalSettingsChangeListener {

    protected AccountTypeService     accountTypeService;
    protected PaymentService         paymentService;
    protected CurrencyService        currencyService;
    protected TransferTypeService    transferTypeService;
    private DataBinder<DoPaymentDTO> dataBinder;

    public DataBinder<DoPaymentDTO> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = initDataBinder();
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public final void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public final void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public final void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public final void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final DoPaymentDTO dto = resolvePaymentDTO(context);
        context.getSession().setAttribute("payment", dto);
    }

    protected abstract AccountOwner getFromOwner(ActionContext context);

    protected PaymentService getPaymentService() {
        return paymentService;
    }

    protected DataBinder<DoPaymentDTO> initDataBinder() {
        final LocalSettings localSettings = settingsService.getLocalSettings();

        final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(PaymentCustomFieldValue.class);
        customValueBinder.registerBinder("field", PropertyBinder.instance(PaymentCustomField.class, "field"));
        customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));

        final BeanBinder<DoPaymentDTO> binder = BeanBinder.instance(DoPaymentDTO.class);
        binder.registerBinder("from", PropertyBinder.instance(AccountOwner.class, "from", AccountOwnerConverter.instance()));
        binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "type"));
        binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
        binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
        binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
        binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
        binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));
        return binder;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {

        final BasePaymentForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        // Check whether the payment is as a member
        final Long fromId = IdConverter.instance().valueOf(form.getFrom());
        final boolean asMember = fromId != null;
        Member fromMember = null;
        if (asMember) {
            final Element element = elementService.load(fromId, Element.Relationships.GROUP, Element.Relationships.USER);
            if (element instanceof Member) {
                fromMember = (Member) element;
                request.setAttribute("member", fromMember);
            }
        }
        request.setAttribute("asMember", asMember);

        // Get the member in action
        Member member = fromMember;
        if (member == null && context.isMember()) {
            member = context.getElement();
        }

        // Resolve the possible currencies
        final List<Currency> currencies = resolveCurrencies(context);

        // Resolve the transfer types
        final TransferTypeQuery ttQuery = resolveTransferTypeQuery(context);
        if (ttQuery != null) {

            Currency defaultCurrency = null;
            if (member != null) {
                final MemberAccountType defaultAccountType = accountTypeService.getDefault(member.getMemberGroup(), AccountType.Relationships.CURRENCY);
                if (defaultAccountType != null) {
                    defaultCurrency = defaultAccountType.getCurrency();
                }
            }

            // Check for transfer types for each currency, removing those currencies without transfer types
            final Map<Currency, List<TransferType>> transferTypesPerCurrency = new LinkedHashMap<Currency, List<TransferType>>();
            final List<TransferType> allTransferTypes = new ArrayList<TransferType>();

            for (final Iterator<Currency> iterator = currencies.iterator(); iterator.hasNext();) {
                final Currency currency = iterator.next();
                final TransferTypeQuery currentQuery = (TransferTypeQuery) ttQuery.clone();
                currentQuery.setCurrency(currency);
                final List<TransferType> tts = transferTypeService.search(currentQuery);
                allTransferTypes.addAll(tts);
                if (tts.isEmpty()) {
                    iterator.remove();
                } else {
                    transferTypesPerCurrency.put(currency, tts);
                }
            }

            // Check which currency to preselect
            Currency currency = null;
            if (CollectionUtils.isNotEmpty(transferTypesPerCurrency.get(defaultCurrency))) {
                // There are TTs for the default currency: preselect it
                currency = defaultCurrency;
            } else if (!transferTypesPerCurrency.isEmpty()) {
                // Get the first currency with TTs
                currency = transferTypesPerCurrency.keySet().iterator().next();
            }
            form.setCurrency(CoercionHelper.coerce(String.class, currency));

            // Store the transfer types associated with the preselected currency
            request.setAttribute("transferTypes", allTransferTypes);
        }

        if (CollectionUtils.isEmpty(currencies)) {
            // No currency with possible transfer type!!!
            throw new ValidationException("payment.error.noTransferType");
        } else if (currencies.size() == 1) {
            request.setAttribute("singleCurrency", currencies.iterator().next());
        }
    }

    protected List<Currency> resolveCurrencies(final ActionContext context) {
        final BasePaymentForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final List<Currency> currencies;
        final AccountOwner fromOwner = getFromOwner(context);
        if (fromOwner instanceof Member) {
            final Member member = elementService.load(((Member) fromOwner).getId(), Element.Relationships.GROUP);
            currencies = currencyService.listByMember(member);
            final MemberAccountType defaultAccountType = accountTypeService.getDefault(member.getMemberGroup(), AccountType.Relationships.CURRENCY);
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
        return currencies;
    }

    /**
     * Reads the payment DTO from the form
     */
    protected DoPaymentDTO resolvePaymentDTO(final ActionContext context) {
        return getDataBinder().readFromString(context.getForm());
    }

    /**
     * Should be overridden to return a transfer type query. When null is returned, it is assumed that there will be no transfer types on the request.
     * Otherwise, a query is executed and an error is generated if there is no returned transfer type
     */
    protected abstract TransferTypeQuery resolveTransferTypeQuery(ActionContext context);

    @Override
    protected void validateForm(final ActionContext context) {
        final DoPaymentDTO dto = resolvePaymentDTO(context);
        paymentService.validate(dto);
    }
}
