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

import nl.strohalm.cyclos.dao.accounts.AccountDAO;
import nl.strohalm.cyclos.dao.accounts.MemberGroupAccountSettingsDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.dao.members.ElementDAO;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.transactions.GrantSinglePaymentLoanDTO;
import nl.strohalm.cyclos.services.transactions.LoanServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Handles member account creation / removal
 * @author luis
 */
public class MemberAccountHandler {

    private static final float            PRECISION_DELTA = 0.0001F;
    private ElementDAO                    elementDao;
    private AccountDAO                    accountDao;
    private TransferDAO                   transferDao;
    private AccountServiceLocal           accountService;
    private PaymentServiceLocal           paymentService;
    private AlertServiceLocal             alertService;
    private LoanServiceLocal              loanService;
    private MemberGroupAccountSettingsDAO memberGroupAccountSettingsDao;
    private FetchServiceLocal             fetchService;
    private TransactionHelper             transactionHelper;

    /**
     * Activate the member account if it exists, or create if it doesn't
     */
    public MemberAccount activate(final Member member, final MemberAccountType type) {
        if (member == null || member.isTransient() || type == null || type.isTransient()) {
            throw new UnexpectedEntityException();
        }

        MemberAccount account;
        try {
            account = (MemberAccount) accountDao.load(member, type);
            account = activate(account);
        } catch (final EntityNotFoundException e) {
            account = create(member, type);
        }
        return account;
    }

    /**
     * Activate the member account
     */
    public MemberAccount activate(final MemberAccount account) {
        // The account exists, mark as active
        account.setStatus(MemberAccount.Status.ACTIVE);
        account.setAction(null);
        accountDao.update(account);

        // Ensure the member is activated
        activateMember(account.getMember());

        return account;
    }

    /**
     * Deactivate a member account, or remove it if it has no transactions
     */
    public void deactivate(final Member member, final MemberAccountType type, final boolean enforceZeroBalance) {
        try {
            final MemberAccount account = (MemberAccount) accountDao.load(member, type);
            deactivate(account, enforceZeroBalance);
        } catch (final EntityNotFoundException e) {
            // Ok, the account already didn't exist
        }
    }

    /**
     * When there are no transfers, remove the account. When there are transfers and the balance is zero, set the status to inactive. Otherwise, when
     * balance is not zero, throws UnexpectedEntityException
     */
    public void deactivate(final MemberAccount account, final boolean enforceZeroBalance) {
        boolean hasTransfers = transferDao.hasTransfers(account);
        final boolean hasCreditLimit = Math.abs(account.getCreditLimit().floatValue()) > PRECISION_DELTA;
        if (hasTransfers || hasCreditLimit) {
            BigDecimal balance = accountService.getBalance(new AccountDateDTO(account));
            if (enforceZeroBalance && Math.abs(balance.floatValue()) > PRECISION_DELTA) {
                // When there is non zero balance in the account, throw an error
                throw new UnexpectedEntityException();
            }
            account.setStatus(MemberAccount.Status.INACTIVE);
            account.setAction(null);
            accountDao.update(account);
        } else {
            accountDao.delete(account.getId());
        }
    }

    public void setAccountDao(final AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAccountSettingsDao(final MemberGroupAccountSettingsDAO accountSettingsDao) {
        memberGroupAccountSettingsDao = accountSettingsDao;
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setElementDao(final ElementDAO elementDao) {
        this.elementDao = elementDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLoanServiceLocal(final LoanServiceLocal loanService) {
        this.loanService = loanService;
    }

    public void setMemberGroupAccountSettingsDao(final MemberGroupAccountSettingsDAO memberGroupAccountSettingsDao) {
        this.memberGroupAccountSettingsDao = memberGroupAccountSettingsDao;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTransferDao(final TransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    private void activateMember(final Member member) {
        if (member.getActivationDate() == null) {
            member.setActivationDate(Calendar.getInstance());
            elementDao.update(member);
        }
    }

    /**
     * Create a member account
     */
    private MemberAccount create(final Member m, final MemberAccountType at) {

        final Member member = fetchService.fetch(m, Element.Relationships.USER, Element.Relationships.GROUP);
        final MemberAccountType accountType = fetchService.fetch(at);

        final MemberGroupAccountSettings accountSettings;
        try {
            accountSettings = memberGroupAccountSettingsDao.load(member.getMemberGroup().getId(), accountType.getId());
        } catch (EntityNotFoundException e) {
            // The member has been moved to another group before the account is activated. Do nothing
            return null;
        }

        // Create the account
        final MemberAccount account = new MemberAccount();
        account.setCreationDate(Calendar.getInstance());
        account.setCreditLimit(accountSettings.getDefaultCreditLimit());
        account.setUpperCreditLimit(accountSettings.getDefaultUpperCreditLimit());
        account.setType(accountType);
        account.setMember(member);
        account.setOwnerName(member.getUsername());
        accountDao.insert(account);

        // Ensure the member is activated
        activateMember(member);

        // Check for initial credit
        final BigDecimal initialCredit = accountSettings.getInitialCredit();
        final TransferType initialCreditTransferType = accountSettings.getInitialCreditTransferType();
        final BigDecimal minimumPayment = paymentService.getMinimumPayment();
        if (initialCredit != null && (initialCredit.compareTo(minimumPayment) > 0) && initialCreditTransferType != null) {
            // The initial credit is granted in a new transaction. So, when it fails, an alert is created instead of failing the account creation
            CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
                @Override
                public void onTransactionCommit() {
                    try {
                        // Grant the initial credit
                        transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                            @Override
                            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                                grantInitialCredit(account, initialCredit, initialCreditTransferType);
                            }
                        });
                    } catch (Exception e) {
                        transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                            @Override
                            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                                alertService.create(member, MemberAlert.Alerts.INITIAL_CREDIT_FAILED, account.getType().getName());
                            }
                        });
                    }
                }
            });
        }

        return account;
    }

    private void grantInitialCredit(MemberAccount account, final BigDecimal amount, TransferType transferType) {
        account = fetchService.fetch(account, RelationshipHelper.nested(MemberAccount.Relationships.MEMBER, Element.Relationships.USER));
        transferType = fetchService.fetch(transferType);
        if (transferType.isLoanType()) {
            Integer repaymentDays = transferType.getLoan().getRepaymentDays();
            if (repaymentDays == null) {
                repaymentDays = 30;
            }
            final GrantSinglePaymentLoanDTO dto = new GrantSinglePaymentLoanDTO();
            dto.setAutomatic(true);
            dto.setRepaymentDate(new TimePeriod(repaymentDays, TimePeriod.Field.DAYS).add(Calendar.getInstance()));
            dto.setMember(account.getMember());
            dto.setAmount(amount);
            dto.setTransferType(transferType);
            dto.setDescription(transferType.getDescription());
            loanService.insert(dto);
        } else {
            final TransferDTO dto = new TransferDTO();
            dto.setContext(TransactionContext.AUTOMATIC);
            dto.setAmount(amount);
            dto.setTransferType(transferType);
            dto.setFromOwner(SystemAccountOwner.instance());
            dto.setTo(account);
            dto.setDescription(transferType.getDescription());
            paymentService.insertWithoutNotification(dto);
        }
    }
}
