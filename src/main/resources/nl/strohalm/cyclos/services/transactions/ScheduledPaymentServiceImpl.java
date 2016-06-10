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

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.accounts.transactions.ScheduledPaymentDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment.Status;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;
import nl.strohalm.cyclos.webservices.model.ScheduledPaymentVO;
import nl.strohalm.cyclos.webservices.utils.AccountHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Implementation for scheduled payment service
 * @author Jefferson Magno
 */
public class ScheduledPaymentServiceImpl implements ScheduledPaymentServiceLocal, InitializingService {

    private SettingsServiceLocal              settingsService;
    private FetchServiceLocal                 fetchService;
    private ScheduledPaymentDAO               scheduledPaymentDao;
    private TransferDAO                       transferDao;
    private PaymentServiceLocal               paymentService;
    private PermissionServiceLocal            permissionService;
    private AccountServiceLocal               accountService;
    private InvoiceServiceLocal               invoiceService;
    private TransferAuthorizationServiceLocal transferAuthorizationService;
    private AccountHelper                     accountHelper;
    private PaymentCustomFieldService         paymentCustomFieldService;
    private MemberNotificationHandler         memberNotificationHandler;
    private TransactionHelper                 transactionHelper;

    @Override
    public ScheduledPayment block(ScheduledPayment scheduledPayment) {
        scheduledPayment = fetchService.fetch(scheduledPayment, ScheduledPayment.Relationships.TRANSFERS);
        final AccountOwner owner = LoggedUser.accountOwner();
        // Ensure that if the logged user is the from member, the transfer type allows blocking
        if (owner instanceof Member && owner.equals(scheduledPayment.getFromOwner())) {
            if (!scheduledPayment.getType().isAllowBlockScheduledPayments()) {
                throw new PermissionDeniedException();
            }
        }
        final Status status = scheduledPayment.getStatus();
        if (status == Status.PROCESSED || status == Status.BLOCKED || status == Status.CANCELED || status == Status.DENIED) {
            throw new UnexpectedEntityException();
        }
        for (final Transfer transfer : scheduledPayment.getTransfers()) {
            final Status transferStatus = transfer.getStatus();
            if (transferStatus == Status.SCHEDULED || transferStatus == Status.FAILED) {
                transferDao.updateStatus(transfer.getId(), Status.BLOCKED);
            }
        }
        return updateScheduledPaymentStatus(scheduledPayment);
    }

    @Override
    public boolean canBlock(final ScheduledPayment scheduledPayment) {
        // Can only block if there is at least one installment is scheduled
        boolean hasScheduledTransfer = false;
        for (Transfer transfer : scheduledPayment.getTransfers()) {
            if (transfer.getStatus() == Status.SCHEDULED) {
                hasScheduledTransfer = true;
                break;
            }
        }
        if (!hasScheduledTransfer) {
            return false;
        }
        return hasBlockPermission(scheduledPayment);
    }

    @Override
    public boolean canCancel(final ScheduledPayment scheduledPayment) {
        final Status status = scheduledPayment.getStatus();
        // Depending on the initial state, it can no longer be cancelled
        if (status == Status.PROCESSED || status == Status.CANCELED || status == Status.DENIED) {
            return false;
        }
        // If logged as the from member (or his operators), it also depends on a TT setting to allow cancelling scheduled payments
        Member loggedMember = LoggedUser.member();
        if (loggedMember != null) {
            if (loggedMember.equals(scheduledPayment.getFromOwner())) {
                final boolean allowUserToCancel = scheduledPayment.getType().isAllowCancelScheduledPayments();
                if (!allowUserToCancel) {
                    return false;
                }
            }
        }

        // When there are pending transfers, only allow canceling the scheduled payment if can also cancel a pending payment
        Transfer firstPendingTransfer = null;
        for (Transfer transfer : scheduledPayment.getTransfers()) {
            if (transfer.getStatus() == Status.PENDING) {
                firstPendingTransfer = transfer;
                break;
            }
        }

        // When there is an installment which is pending authorization, only allows cancelling the scheduling if can also cancel the authorization
        if (firstPendingTransfer != null && !transferAuthorizationService.canCancel(firstPendingTransfer)) {
            return false;
        }

        // Finally, check the permission for cancel scheduled payments
        if (scheduledPayment.isFromSystem()) {
            return permissionService.hasPermission(AdminSystemPermission.PAYMENTS_CANCEL_SCHEDULED);
        } else {
            Member fromMember = (Member) scheduledPayment.getFromOwner();
            return permissionService.permission(fromMember)
                    .admin(AdminMemberPermission.PAYMENTS_CANCEL_SCHEDULED_AS_MEMBER)
                    .broker(BrokerPermission.MEMBER_PAYMENTS_CANCEL_SCHEDULED_AS_MEMBER)
                    .member(MemberPermission.PAYMENTS_CANCEL_SCHEDULED)
                    .operator(OperatorPermission.PAYMENTS_CANCEL_SCHEDULED)
                    .hasPermission();
        }
    }

    @Override
    public ScheduledPayment cancel(ScheduledPayment scheduledPayment) {
        scheduledPayment = fetchService.fetch(scheduledPayment, ScheduledPayment.Relationships.TRANSFERS);
        final AccountOwner owner = LoggedUser.accountOwner();
        // Ensure that if the logged user is the from member, the transfer type allows canceling
        if (owner instanceof Member && owner.equals(scheduledPayment.getFromOwner())) {
            if (!scheduledPayment.getType().isAllowCancelScheduledPayments()) {
                throw new PermissionDeniedException();
            }
        }
        final Status status = scheduledPayment.getStatus();
        if (status == Status.PROCESSED || status == Status.CANCELED || status == Status.DENIED) {
            throw new UnexpectedEntityException();
        }
        for (final Transfer transfer : scheduledPayment.getTransfers()) {
            final Status transferStatus = transfer.getStatus();
            if (scheduledPayment.isReserveAmount() && (transferStatus == Status.SCHEDULED || transferStatus == Status.BLOCKED) || transferStatus == Status.PENDING) {
                // Ensure the amount is liberated
                accountService.returnReservationForInstallment(transfer);
            }
            if (transferStatus == Status.PENDING || transferStatus == Status.SCHEDULED || transferStatus == Status.FAILED || transferStatus == Status.BLOCKED) {
                transferDao.updateStatus(transfer.getId(), Status.CANCELED);
            }
        }
        scheduledPayment.setStatus(Status.CANCELED);
        return scheduledPaymentDao.update(scheduledPayment);
    }

    @Override
    public void cancelScheduledPaymentsAndNotify(final Member member, final Collection<MemberAccountType> accountTypes) {
        List<ScheduledPayment> scheduledPayments = scheduledPaymentDao.getUnrelatedPendingPayments(member, accountTypes);
        final Set<Member> membersToNotify = new HashSet<Member>();
        final Set<MemberAccountType> removedAccounts = new HashSet<MemberAccountType>();

        // this flag is true if the member was not removed and at least on of the incoming payment should notify the receiver (in this case the
        // member)
        // or is from an invoice or there is at least one outgoing payment (the member is the payer)
        final MutableBoolean notifyMember = new MutableBoolean(false);
        for (ScheduledPayment scheduledPayment : scheduledPayments) {
            cancel(scheduledPayment);

            boolean incoming = member.equals(scheduledPayment.getToOwner());
            if (incoming) { // member is the receiver then notify the payer
                if (scheduledPayment.getFromOwner() instanceof Member) { // there is not notification for incoming system payments
                    Member payer = (Member) scheduledPayment.getFromOwner();
                    membersToNotify.add(payer);
                    if (!member.getGroup().isRemoved() && !notifyMember.booleanValue()) {
                        notifyMember.setValue(scheduledPayment.isShowToReceiver() || isFromInvoice(scheduledPayment));
                    }
                    removedAccounts.add((MemberAccountType) scheduledPayment.getTo().getType());
                }
            } else { // outgoing (member is the payer)
                if (scheduledPayment.getToOwner() instanceof Member) { // there is not notification for outgoing system payments
                    if (scheduledPayment.isShowToReceiver() || isFromInvoice(scheduledPayment)) {
                        Member receiver = (Member) scheduledPayment.getToOwner();
                        membersToNotify.add(receiver);
                    }
                    removedAccounts.add((MemberAccountType) scheduledPayment.getFrom().getType());
                }
                if (!member.getGroup().isRemoved()) {
                    notifyMember.setValue(true);
                }
            }
        }

        if (!scheduledPayments.isEmpty()) {
            CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
                @Override
                public void onTransactionCommit() {
                    transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(final TransactionStatus status) {
                            memberNotificationHandler.scheduledPaymentsCancelledNotification(member, notifyMember.booleanValue(), membersToNotify, removedAccounts);
                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean canPayNow(final Transfer transfer) {
        // Check, when part of scheduled payment, if can pay now
        boolean canPayNow = false;
        final ScheduledPayment scheduledPayment = transfer.getScheduledPayment();
        if (scheduledPayment != null) {
            canPayNow = transfer.getStatus().canPayNow();
            // Check if there's an expired payment
            if (canPayNow) {
                final List<Transfer> transfers = scheduledPayment.getTransfers();
                final Calendar now = DateHelper.truncate(Calendar.getInstance());
                for (final Transfer current : transfers) {
                    // When there's an expired payment, only that one is payable
                    if (current.getStatus().canPayNow() && current.getDate().before(now)) {
                        canPayNow = transfer.equals(current);
                        break;
                    }
                }
            }
            // Check the allowed transfer types
            if (canPayNow) {
                canPayNow = paymentService.canMakePayment(transfer.getFromOwner(), transfer.getToOwner(), transfer.getType());
            }

            // Check static permissions
            if (canPayNow) {
                if (LoggedUser.isOperator()) {
                    canPayNow = permissionService.permission().operator(OperatorPermission.PAYMENTS_PAYMENT_TO_MEMBER)
                            .hasPermission();
                }
            }
        }
        return canPayNow;
    }

    @Override
    public boolean canUnblock(final ScheduledPayment scheduledPayment) {
        Transfer firstBlockedTransfer = null;
        for (Transfer transfer : scheduledPayment.getTransfers()) {
            if (transfer.getStatus() == Status.BLOCKED) {
                firstBlockedTransfer = transfer;
                break;
            }
        }
        if (firstBlockedTransfer == null) {
            // No blocked transfer - cannot unblock
            return false;
        }
        final Calendar now = Calendar.getInstance();
        final Calendar date = firstBlockedTransfer.getDate();
        if (now.after(date)) {
            return false;
        }
        return hasBlockPermission(scheduledPayment);
    }

    @Override
    public ScheduledPaymentVO getScheduledPaymentVO(final Long scheduledPaymentId) {
        ScheduledPayment scheduledPayment = load(scheduledPaymentId);
        List<PaymentCustomField> fields = paymentCustomFieldService.list(scheduledPayment.getType(), false);
        return accountHelper.toVO(LoggedUser.member(), scheduledPayment, fields);
    }

    @Override
    public void initializeService() {
        recoverScheduledPayments();
    }

    @Override
    public ScheduledPayment load(final Long id, final Relationship... fetch) {
        return scheduledPaymentDao.<ScheduledPayment> load(id, fetch);
    }

    @Override
    public Transfer processTransfer(Transfer transfer) {
        transfer = fetchService.fetch(transfer, RelationshipHelper.nested(Transfer.Relationships.SCHEDULED_PAYMENT, ScheduledPayment.Relationships.TRANSFERS));

        final ScheduledPayment scheduledPayment = transfer.getScheduledPayment();
        if (scheduledPayment == null) {
            throw new UnexpectedEntityException();
        }
        final Status scheduledPaymentStatus = scheduledPayment.getStatus();
        if (scheduledPaymentStatus == Status.PROCESSED || scheduledPaymentStatus == Status.PENDING || scheduledPaymentStatus == Status.CANCELED || scheduledPaymentStatus == Status.DENIED) {
            throw new UnexpectedEntityException();
        }

        final Calendar today = DateHelper.truncate(Calendar.getInstance());
        final Transfer firstOpenTransfer = scheduledPayment.getFirstOpenTransfer();
        if (firstOpenTransfer.getDate().before(today) && !firstOpenTransfer.equals(transfer)) {
            throw new UnexpectedEntityException();
        }

        final Status firstOpenTransferStatus = firstOpenTransfer.getStatus();
        if (firstOpenTransferStatus == Status.PROCESSED || firstOpenTransferStatus == Status.CANCELED || firstOpenTransferStatus == Status.DENIED || firstOpenTransferStatus == Status.PENDING) {
            throw new UnexpectedEntityException();
        }

        return paymentService.processScheduled(transfer);
    }

    @Override
    public List<ScheduledPayment> search(final ScheduledPaymentQuery query) {
        return scheduledPaymentDao.search(query);
    }

    public void setAccountHelper(final AccountHelper accountHelper) {
        this.accountHelper = accountHelper;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setScheduledPaymentDao(final ScheduledPaymentDAO scheduledPaymentDao) {
        this.scheduledPaymentDao = scheduledPaymentDao;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTransferAuthorizationServiceLocal(final TransferAuthorizationServiceLocal transferAuthorizationService) {
        this.transferAuthorizationService = transferAuthorizationService;
    }

    public void setTransferDao(final TransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    @Override
    public ScheduledPayment unblock(ScheduledPayment scheduledPayment) {
        scheduledPayment = fetchService.fetch(scheduledPayment, ScheduledPayment.Relationships.TRANSFERS);
        final Calendar today = DateHelper.truncate(Calendar.getInstance());
        for (final Transfer transfer : scheduledPayment.getTransfers()) {
            final Status transferStatus = transfer.getStatus();
            if (transferStatus == Status.BLOCKED) {
                if (transfer.getDate().before(today)) {
                    throw new UnexpectedEntityException();
                } else {
                    transferDao.updateStatus(transfer.getId(), Status.SCHEDULED);
                }
            }
        }
        return updateScheduledPaymentStatus(scheduledPayment);
    }

    @Override
    public ScheduledPayment updateScheduledPaymentStatus(final ScheduledPayment scheduledPayment) {
        final Transfer firstOpenTransfer = scheduledPayment.getFirstOpenTransfer();
        Payment.Status newStatus = null;
        if (firstOpenTransfer == null) {
            final List<Transfer> transfers = scheduledPayment.getTransfers();
            if (CollectionUtils.isEmpty(transfers)) {
                newStatus = Payment.Status.PROCESSED;
            } else {
                newStatus = transfers.get(transfers.size() - 1).getStatus();
            }
        } else {
            newStatus = firstOpenTransfer.getStatus();
        }
        scheduledPayment.setStatus(newStatus);
        if (newStatus == Payment.Status.PROCESSED) {
            scheduledPayment.setProcessDate(Calendar.getInstance());
        }
        return scheduledPaymentDao.update(scheduledPayment);
    }

    private boolean hasBlockPermission(final ScheduledPayment scheduledPayment) {
        if (scheduledPayment.isFromSystem()) {
            return permissionService.hasPermission(AdminSystemPermission.PAYMENTS_BLOCK_SCHEDULED);
        } else {
            Member fromMember = (Member) scheduledPayment.getFromOwner();

            // When logged as the member, it doesn't suffice to have the permission, but there is a TT setting as well
            Member loggedMember = LoggedUser.member();
            if (fromMember.equals(loggedMember)) {
                if (loggedMember.equals(scheduledPayment.getFromOwner())) {
                    final boolean allowUserToBlock = scheduledPayment.getType().isAllowBlockScheduledPayments();
                    if (!allowUserToBlock) {
                        return false;
                    }
                }
            }

            // Check the permission itself
            return permissionService.permission(fromMember)
                    .admin(AdminMemberPermission.PAYMENTS_BLOCK_SCHEDULED_AS_MEMBER)
                    .broker(BrokerPermission.MEMBER_PAYMENTS_BLOCK_SCHEDULED_AS_MEMBER)
                    .member(MemberPermission.PAYMENTS_BLOCK_SCHEDULED)
                    .operator(OperatorPermission.PAYMENTS_BLOCK_SCHEDULED)
                    .hasPermission();
        }
    }

    /**
     * @return true if the scheduled payment was generated from an invoice
     */
    private boolean isFromInvoice(final ScheduledPayment scheduledPayment) {
        try {
            invoiceService.loadByPayment(scheduledPayment);
            return true;
        } catch (final EntityNotFoundException e) {
            return false;
        }
    }

    private void recoverScheduledPayments() {

        // Find whether we are executing less than one hour before the scheduled tasks run.
        // If yes, consider today as deadline. Otherwise, today the scheduled task will yet run,
        // so, consider yesterday as deadline.
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final Calendar now = Calendar.getInstance();
        final Calendar limit = DateHelper.truncate(now);
        limit.set(Calendar.HOUR_OF_DAY, localSettings.getSchedulingHour());
        limit.add(Calendar.HOUR_OF_DAY, -1);
        limit.set(Calendar.MINUTE, localSettings.getSchedulingMinute());
        Calendar time;
        if (now.before(limit)) {
            time = DateHelper.truncatePreviosDay(now);
        } else {
            time = DateHelper.truncate(now);
        }

        paymentService.processScheduled(Period.endingAt(time));
    }

}
