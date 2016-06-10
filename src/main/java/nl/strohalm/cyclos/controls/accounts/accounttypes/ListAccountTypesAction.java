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
package nl.strohalm.cyclos.controls.accounts.accounttypes;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.accounts.SystemAccountTypeQuery;

import org.apache.struts.action.ActionForward;

/**
 * Action used to list the existing account types
 * @author luis
 */
public class ListAccountTypesAction extends BaseAction {

    private AccountTypeService accountTypeService;

    public AccountTypeService getAccountTypeService() {
        return accountTypeService;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final List<AccountType> accountTypes = new ArrayList<AccountType>();
        // Get the system accounts
        final SystemAccountTypeQuery systemQuery = new SystemAccountTypeQuery();
        systemQuery.fetch(AccountType.Relationships.CURRENCY);
        accountTypes.addAll(accountTypeService.search(systemQuery));
        // Get the member accounts
        final MemberAccountTypeQuery memberQuery = new MemberAccountTypeQuery();
        memberQuery.fetch(AccountType.Relationships.CURRENCY);
        accountTypes.addAll(accountTypeService.search(memberQuery));

        request.setAttribute("accountTypes", accountTypes);
        return context.getInputForward();
    }
}
