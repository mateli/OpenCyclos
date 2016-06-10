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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;

import org.apache.struts.action.ActionForward;

/**
 * Used to retrieve the home page on mobile access
 * @author luis
 */
public class MobileHomeAction extends MobileBaseAction {

    private AccountService accountService;

    public AccountService getAccountService() {
        return accountService;
    }

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected ActionForward executeAction(final MobileActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        // Retrieve the current account
        final MemberAccount account = context.getCurrentAccount();
        request.setAttribute("account", account);

        // Retrieve the current account type
        final MemberAccountType accountType = context.getCurrentAccountType();
        if (accountType == null) {
            // There is no account for mobile access. Invalidate the session
            context.getSession().invalidate();
            if (MobileHelper.isWap1Request(request)) {
                return new ActionForward("/wap", true);
            } else {
                return new ActionForward("/mobile", true);
            }
        }
        request.setAttribute("accountType", accountType);
        request.setAttribute("unitsPattern", accountType.getCurrency().getPattern());

        // Retrieve the account status
        final AccountDTO dto = new AccountDTO(context.getMember(), accountType);
        request.setAttribute("status", accountService.getCurrentStatus(dto));

        final Boolean multipleAccounts = (Boolean) context.getSession().getAttribute("multipleAccounts");
        request.setAttribute("multipleAccounts", multipleAccounts);

        return context.getInputForward();
    }

}
