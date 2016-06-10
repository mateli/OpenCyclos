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

import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.utils.RelationshipHelper;

import org.apache.struts.action.ActionForward;

public class MobileConfirmAccountAction extends MobileBaseAction {

    private AccountService accountService;

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ActionForward executeAction(final MobileActionContext context) throws Exception {
        final MobileConfirmAccountForm form = context.getForm();
        final Long id = form.getId();
        final Map<Long, MemberAccountType> accountTypesById = (Map<Long, MemberAccountType>) context.getSession().getAttribute("accountTypesById");
        final MemberAccountType accountType = accountTypesById.get(id);
        context.setCurrentAccountType(accountType);

        final Member member = context.getElement();
        final AccountDTO accountDto = new AccountDTO();
        accountDto.setOwner(member);
        accountDto.setType(accountType);
        final MemberAccount account = (MemberAccount) accountService.getAccount(accountDto, RelationshipHelper.nested(Account.Relationships.TYPE, AccountType.Relationships.CURRENCY));
        context.setCurrentAccount(account);

        return context.findForward("success");
    }

}
