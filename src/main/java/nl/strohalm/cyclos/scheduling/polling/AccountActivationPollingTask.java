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
package nl.strohalm.cyclos.scheduling.polling;

import nl.strohalm.cyclos.dao.accounts.AccountDAO;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.MemberAccountHandler;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionRollbackListener;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * A {@link PollingTask} which activates or deactivates accounts associated to groups (which result in potentially large number of accounts)
 * @author luis
 */
public class AccountActivationPollingTask extends PollingTask {

    private AccountDAO           accountDao;
    private FetchServiceLocal    fetchService;
    private MemberAccountHandler memberAccountHandler;
    private AlertServiceLocal    alertService;
    private TransactionHelper    transactionHelper;

    /**
     * Handles a failure in initial credit processing, by generating a member alert and deleting the {@link PendingInitialCredit} instance.
     */
    private void handleFailure(final MemberAccount account) {
        // Create the alert in a new transaction, and remove the marker for activation, otherwise cyclos will be trying and retrying infinitely
        transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                // Create the alert
                MemberAccount fetchedAccount = fetchService.fetch(account, MemberAccount.Relationships.MEMBER, Account.Relationships.TYPE);
                Member member = fetchedAccount.getMember();
                alertService.create(member, MemberAlert.Alerts.ACCOUNT_ACTIVATION_FAILED, fetchedAccount.getType().getName());

                // Remove the activation marker
                fetchedAccount.setAction(null);
                accountDao.update(fetchedAccount);
            }
        });
    }

    private void performAction(final MemberAccount account) {
        MemberAccount.Action action = account.getAction();
        switch (action) {
            case ACTIVATE:
                memberAccountHandler.activate(account);
                break;
            case REMOVE:
                memberAccountHandler.deactivate(account, false);
                break;
            default:
                throw new IllegalStateException("Only MemberAccount.Action.ACTIVATE and REMOVE allowed");
        }
    }

    @Override
    protected boolean runTask() {
        final MemberAccount account = accountDao.getNextPendingProcessing();
        if (account == null) {
            return false;
        }
        // Add a rollback listener to handle the failure
        CurrentTransactionData.addTransactionRollbackListener(new TransactionRollbackListener() {
            @Override
            public void onTransactionRollback() {
                handleFailure(account);
            }
        });

        // Perform the action
        performAction(account);

        // Immediately process the next account
        return true;
    }

    public void setAccountDao(final AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMemberAccountHandler(final MemberAccountHandler memberAccountHandler) {
        this.memberAccountHandler = memberAccountHandler;
    }
}
