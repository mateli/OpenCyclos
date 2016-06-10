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
package nl.strohalm.cyclos.controls.accounts.details;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.AccountDateDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to retrieve the account overview, or redirect to the account details if there is a single account
 * @author luis
 */
public class AccountOverviewAction extends BaseAction {

    private AccountService     accountService;
    private AccountTypeService accountTypeService;

    public AccountService getAccountService() {
        return accountService;
    }

    public AccountTypeService getAccountTypeService() {
        return accountTypeService;
    }

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final AccountOverviewForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final long memberId = form.getMemberId();
        AccountOwner owner;
        Member member = null;
        boolean myAccounts = false;
        boolean byBroker = false;

        final Element loggedElement = context.getElement();
        // Resolve the account owner we will use
        if (memberId <= 0L || memberId == loggedElement.getId()) {
            owner = context.getAccountOwner();
            myAccounts = true;
            if (context.isMember()) {
                member = context.getElement();
            }
        } else {
            final Element element = elementService.load(memberId, Element.Relationships.USER);
            if (!(element instanceof Member)) {
                throw new ValidationException();
            }
            member = (Member) element;
            owner = member;
            if (context.isMember()) {
                if (!context.isBrokerOf(member)) {
                    throw new ValidationException();
                }
                byBroker = true;
            }
        }

        // Get the account types of the owner
        final List<? extends Account> accounts = accountService.getAccounts(owner, RelationshipHelper.nested(Account.Relationships.TYPE, AccountType.Relationships.CURRENCY));

        if (accounts.isEmpty()) {
            // No accounts = error
            return context.sendError("accountOverview.error.noAccounts");
        } else if (accounts.size() == 1) {
            // Single account = redirect to the account details

            // Remove the overview from the navigation
            context.getNavigation().removeCurrent();

            // Redirect
            final Account account = accounts.get(0);
            final AccountType accountType = account.getType();
            final Map<String, Object> params = new HashMap<String, Object>();
            params.put("memberId", form.getMemberId());
            params.put("typeId", accountType.getId());
            params.put("singleAccount", true);
            params.put("fromQuickAccess", request.getParameter("fromQuickAccess"));
            return ActionHelper.redirectWithParams(context.getRequest(), context.findForward("accountDetails"), params);
        }

        request.setAttribute("member", member);
        request.setAttribute("myAccounts", myAccounts);
        request.setAttribute("byBroker", byBroker);
        request.setAttribute("overview", resolveAccountOverview(accounts));

        return context.getInputForward();
    }

    private Map<Account, BigDecimal> resolveAccountOverview(final List<? extends Account> accounts) {
        final Map<Account, BigDecimal> overview = new LinkedHashMap<Account, BigDecimal>();
        for (final Account account : accounts) {
            try {
                final BigDecimal balance = accountService.getBalance(new AccountDateDTO(account));
                overview.put(account, balance);
            } catch (final EntityNotFoundException e) {
                // Ignore this account
            }
        }
        return overview;
    }

}
