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
package nl.strohalm.cyclos.services.accounts;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType.Direction;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberTransactionDetailsReportData;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryReportData;
import nl.strohalm.cyclos.entities.members.MembersTransactionsReportParameters;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;

/**
 * Security layer for {@link AccountService}
 * 
 * @author luis
 */
public class AccountServiceSecurity extends BaseServiceSecurity implements AccountService {

    private AccountServiceLocal accountService;

    @Override
    public boolean canView(final Account account) {
        // No permission check is needed
        return accountService.canView(account);
    }

    @Override
    public boolean canViewAccountsOf(final AccountOwner owner) {
        // No permission check is needed
        return accountService.canViewAccountsOf(owner);
    }

    @Override
    public boolean canViewAuthorizedInformation(final AccountOwner owner) {
        // No permission check is needed
        return accountService.canViewAuthorizedInformation(owner);
    }

    @Override
    public Account getAccount(final AccountDTO params, final Relationship... fetch) {
        Account account = accountService.getAccount(params, fetch);
        checkAccess(account);
        return account;
    }

    @Override
    public List<? extends Account> getAccounts(final AccountOwner owner, final Relationship... fetch) {
        List<? extends Account> accounts = accountService.getAccounts(owner, fetch);
        for (Iterator<? extends Account> iterator = accounts.iterator(); iterator.hasNext();) {
            Account account = iterator.next();
            if (!canView(account)) {
                iterator.remove();
            }
        }
        return accounts;
    }

    @Override
    public Set<? extends Account> getAccountsFromTTs(final Member member, final Collection<TransferType> allowedTTs, final Direction direction) {
        checkAccess(member);
        return accountService.getAccountsFromTTs(member, allowedTTs, direction);
    }

    @Override
    public BigDecimal getBalance(final AccountDateDTO params) {
        Account account = accountService.getAccount(params);
        checkAccess(account);
        params.setAccount(account);
        return accountService.getBalance(params);
    }

    @Override
    public BigDecimal getCreditLimit(final AccountDTO params) {
        checkAccess(accountService.getAccount(params));
        return accountService.getCreditLimit(params);
    }

    @Override
    public CreditLimitDTO getCreditLimits(final Member member) {
        permissionService.permission(member).admin(AdminMemberPermission.ACCOUNTS_CREDIT_LIMIT).check();
        return accountService.getCreditLimits(member);
    }

    @Override
    public AccountStatusVO getCurrentAccountStatusVO(final AccountDTO accountDTO) {
        Account account = accountService.getAccount(accountDTO);
        checkAccess(account);
        return accountService.getCurrentAccountStatusVO(accountDTO);
    }

    @Override
    public AccountStatus getCurrentStatus(final AccountDTO params) {
        Account account = accountService.getAccount(params);
        checkAccess(account);
        return accountService.getCurrentStatus(new AccountDTO(account));
    }

    @Override
    public MemberAccount getDefaultAccount() {
        MemberAccount memberAccount = accountService.getDefaultAccount();
        checkAccess(memberAccount);
        return memberAccount;
    }

    @Override
    public Account getDefaultAccountFromList(final Member member, final List<Account> allowedAccounts) {
        Account account = accountService.getDefaultAccountFromList(member, allowedAccounts);
        checkAccess(account);
        return account;
    }

    @Override
    public MemberAccountVO getMemberAccountVO(final Long memberAccountId) {
        if (memberAccountId == null) {
            return null;
        }
        return accountService.getMemberAccountVO(memberAccountId);
    }

    @Override
    public AccountStatus getRatedStatus(final Account account, final Calendar date) {
        // accountHistoryAction is the only one calling this from the web, so...
        checkAccess(account);
        return accountService.getRatedStatus(account, date);
    }

    @Override
    public boolean hasAccounts(final Member member) {
        if (!permissionService.relatesTo(member)) {
            throw new PermissionDeniedException();
        }
        return accountService.hasAccounts(member);
    }

    @Override
    public <T extends Account> T load(final Long id, final Relationship... fetch) {
        T account = accountService.<T> load(id, fetch);
        checkAccess(account);
        return account;
    }

    @Override
    public Iterator<MemberTransactionDetailsReportData> membersTransactionsDetailsReport(final MembersTransactionsReportParameters params) {
        permissionService.permission().admin(AdminSystemPermission.REPORTS_MEMBER_LIST).check();
        params.setMemberGroups(PermissionHelper.checkSelection(permissionService.getVisibleMemberGroups(), params.getMemberGroups()));
        return accountService.membersTransactionsDetailsReport(params);
    }

    @Override
    public Iterator<MemberTransactionSummaryReportData> membersTransactionsSummaryReport(final MembersTransactionsReportParameters params) {
        permissionService.permission().admin(AdminSystemPermission.REPORTS_MEMBER_LIST).check();
        params.setMemberGroups(PermissionHelper.checkSelection(permissionService.getVisibleMemberGroups(), params.getMemberGroups()));
        return accountService.membersTransactionsSummaryReport(params);
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    @Override
    public void setCreditLimit(final Member member, final CreditLimitDTO limits) {
        permissionService.permission(member).admin(AdminMemberPermission.ACCOUNTS_CREDIT_LIMIT).check();
        accountService.setCreditLimit(member, limits);
    }

    @Override
    public void validate(final Member member, final CreditLimitDTO limits) {
        permissionService.permission(member).admin(AdminMemberPermission.ACCOUNTS_CREDIT_LIMIT).check();
        accountService.validate(member, limits);
    }

    private void checkAccess(final Account account) {
        if (!canView(account)) {
            throw new PermissionDeniedException();
        }
    }

    private void checkAccess(final AccountOwner owner) {
        if (!canViewAccountsOf(owner)) {
            throw new PermissionDeniedException();
        }
    }
}
