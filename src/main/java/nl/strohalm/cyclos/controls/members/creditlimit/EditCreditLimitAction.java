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
package nl.strohalm.cyclos.controls.members.creditlimit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.CreditLimitDTO;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.MapBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a member's credit limit
 * @author luis
 */
public class EditCreditLimitAction extends BaseFormAction implements LocalSettingsChangeListener {
    private AccountService             accountService;

    private DataBinder<CreditLimitDTO> dataBinder;

    public AccountService getAccountService() {
        return accountService;
    }

    public DataBinder<CreditLimitDTO> getDataBinder() {
        if (dataBinder == null) {

            final LocalSettings localSettings = settingsService.getLocalSettings();

            final PropertyBinder<AccountType> keyBinder = PropertyBinder.instance(AccountType.class, "accountTypeIds");
            final PropertyBinder<BigDecimal> limitValueBinder = PropertyBinder.instance(BigDecimal.class, "newCreditLimits", localSettings.getNumberConverter().negativeToAbsolute());
            final PropertyBinder<BigDecimal> upperLimitValueBinder = PropertyBinder.instance(BigDecimal.class, "newUpperCreditLimits", localSettings.getNumberConverter());

            final MapBinder<AccountType, BigDecimal> limitBinder = MapBinder.instance(keyBinder, limitValueBinder);
            final MapBinder<AccountType, BigDecimal> upperLimitBinder = MapBinder.instance(keyBinder, upperLimitValueBinder);

            final BeanBinder<CreditLimitDTO> binder = BeanBinder.instance(CreditLimitDTO.class);
            binder.registerBinder("limitPerType", limitBinder);
            binder.registerBinder("upperLimitPerType", upperLimitBinder);
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditCreditLimitForm form = context.getForm();
        final CreditLimitDTO creditLimit = getDataBinder().readFromString(form);
        accountService.setCreditLimit(getMember(form), creditLimit);
        context.sendMessage("creditLimit.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "memberId", form.getMemberId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditCreditLimitForm form = context.getForm();
        final long id = form.getMemberId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        final Member member = elementService.load(id, Element.Relationships.USER);
        final CreditLimitDTO creditLimit = accountService.getCreditLimits(member);

        // Transform positive to negative values to pass to the JSP
        Map<? extends AccountType, BigDecimal> limitPerType = creditLimit.getLimitPerType();
        final Map<AccountType, BigDecimal> newLimitPerType = new HashMap<AccountType, BigDecimal>();
        for (final AccountType accountType : limitPerType.keySet()) {
            BigDecimal limit = limitPerType.get(accountType);
            if (limit != null && limit.compareTo(new BigDecimal(0)) == 1) {
                limit = limit.negate();
            }
            newLimitPerType.put(accountType, limit);
        }
        limitPerType = newLimitPerType;
        creditLimit.setLimitPerType(limitPerType);

        request.setAttribute("member", member);
        request.setAttribute("limits", creditLimit.getEntries());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditCreditLimitForm form = context.getForm();
        final CreditLimitDTO creditLimit = getDataBinder().readFromString(form);
        accountService.validate(getMember(form), creditLimit);
    }

    /**
     * @param form
     */
    private Member getMember(final EditCreditLimitForm form) {
        return EntityHelper.reference(Member.class, form.getMemberId());
    }

}
