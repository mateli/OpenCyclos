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
package nl.strohalm.cyclos.services.transactions;

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.model.ScheduledPaymentVO;

/**
 * Security implementation for {@link ScheduledPaymentService}
 * 
 * @author jcomas
 */
public class ScheduledPaymentServiceSecurity extends BaseServiceSecurity implements ScheduledPaymentService {

    private ScheduledPaymentServiceLocal scheduledPaymentService;
    private PaymentServiceLocal          paymentService;
    private AccountServiceLocal          accountService;

    @Override
    public ScheduledPayment block(final ScheduledPayment scheduledPayment) {
        if (!canBlock(scheduledPayment)) {
            throw new PermissionDeniedException();
        }
        return scheduledPaymentService.block(scheduledPayment);
    }

    @Override
    public boolean canBlock(final ScheduledPayment scheduledPayment) {
        // Nothing to be checked, as only checks for an action for the logged user
        return scheduledPaymentService.canBlock(scheduledPayment);
    }

    @Override
    public boolean canCancel(final ScheduledPayment scheduledPayment) {
        // Nothing to be checked, as only checks for an action for the logged user
        return scheduledPaymentService.canCancel(scheduledPayment);
    }

    @Override
    public ScheduledPayment cancel(final ScheduledPayment scheduledPayment) {
        if (!canCancel(scheduledPayment)) {
            throw new PermissionDeniedException();
        }
        return scheduledPaymentService.cancel(scheduledPayment);
    }

    @Override
    public boolean canPayNow(final Transfer transfer) {
        // Nothing to check.
        return scheduledPaymentService.canPayNow(transfer);
    }

    @Override
    public boolean canUnblock(final ScheduledPayment scheduledPayment) {
        // Nothing to be checked, as only checks for an action for the logged user
        return scheduledPaymentService.canUnblock(scheduledPayment);
    }

    @Override
    public ScheduledPaymentVO getScheduledPaymentVO(final Long scheduledPaymentId) {
        ScheduledPayment scheduledPayment = load(scheduledPaymentId);
        checkView(scheduledPayment);
        return scheduledPaymentService.getScheduledPaymentVO(scheduledPaymentId);
    }

    @Override
    public ScheduledPayment load(final Long id, final Relationship... fetch) {
        ScheduledPayment scheduledPayment = scheduledPaymentService.load(id, fetch);

        checkView(scheduledPayment);
        return scheduledPayment;
    }

    @Override
    public Transfer processTransfer(final Transfer transfer) {
        if (!paymentService.canMakePayment(transfer.getFromOwner(), transfer.getToOwner(), transfer.getType())) {
            throw new PermissionDeniedException();
        }
        return scheduledPaymentService.processTransfer(transfer);
    }

    @Override
    public List<ScheduledPayment> search(final ScheduledPaymentQuery query) {
        if (!applyQueryRestrictions(query)) {
            return Collections.emptyList();
        }
        return scheduledPaymentService.search(query);
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setScheduledPaymentServiceLocal(final ScheduledPaymentServiceLocal scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    @Override
    public ScheduledPayment unblock(final ScheduledPayment scheduledPayment) {
        if (!canUnblock(scheduledPayment)) {
            throw new PermissionDeniedException();
        }
        return scheduledPaymentService.unblock(scheduledPayment);
    }

    private boolean applyQueryRestrictions(final ScheduledPaymentQuery query) {

        if (!permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_SCHEDULED_INFORMATION, AdminMemberPermission.ACCOUNTS_SCHEDULED_INFORMATION)
                .broker(BrokerPermission.ACCOUNTS_SCHEDULED_INFORMATION)
                .member(MemberPermission.ACCOUNT_SCHEDULED_INFORMATION)
                .operator(OperatorPermission.ACCOUNT_SCHEDULED_INFORMATION).hasPermission()) {
            return false;
        }

        if (!canAccessAccount(query.getOwner(), query.getAccountType())) {
            return false;
        }

        // Must only filter by a managed member
        if (query.getMember() != null) {
            if (LoggedUser.isAdministrator() || LoggedUser.isBroker()) {
                if (!permissionService.manages(query.getMember())) {
                    return false;
                }
            } else if ((LoggedUser.isMember() || LoggedUser.isOperator()) && !LoggedUser.member().equals(query.getOwner())) {
                return false;
            }
        }
        return true;
    }

    private boolean canAccessAccount(final AccountOwner owner, final AccountType type) {
        if (type == null && owner != null) {
            return accountService.canViewAccountsOf(owner);
        } else if (type != null && owner != null) {
            Account account = accountService.getAccount(new AccountDTO(owner, type));
            return accountService.canView(account);
        }
        return false;
    }

    private void checkView(final ScheduledPayment scheduledPayment) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_SCHEDULED_INFORMATION, AdminMemberPermission.ACCOUNTS_SCHEDULED_INFORMATION)
                .broker(BrokerPermission.ACCOUNTS_SCHEDULED_INFORMATION)
                .member(MemberPermission.ACCOUNT_SCHEDULED_INFORMATION)
                .operator(OperatorPermission.ACCOUNT_SCHEDULED_INFORMATION)
                .check();

        if (!paymentService.isVisible(scheduledPayment)) {
            throw new PermissionDeniedException();
        }
    }

}
