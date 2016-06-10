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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferAuthorizationDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.LockedAccountsOnPayments;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel.Authorizer;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransfersAwaitingAuthorizationQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.RateServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.RatesToSave;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.transactions.exceptions.AlreadyAuthorizedException;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.Transactional;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.lock.LockHandler;
import nl.strohalm.cyclos.utils.lock.LockHandlerFactory;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

public class TransferAuthorizationServiceImpl implements TransferAuthorizationServiceLocal {

    private FetchServiceLocal            fetchService;
    private ScheduledPaymentServiceLocal scheduledPaymentService;
    private TransferDAO                  transferDao;
    private TransferAuthorizationDAO     transferAuthorizationDao;
    private AccountServiceLocal          accountService;
    private MemberNotificationHandler    memberNotificationHandler;
    private PermissionServiceLocal       permissionService;
    private RateServiceLocal             rateService;
    private LockHandlerFactory           lockHandlerFactory;
    private TransactionHelper            transactionHelper;

    private PaymentServiceLocal          paymentService;

    @Override
    public Transfer authorize(final TransferAuthorizationDTO dto) throws AlreadyAuthorizedException, EntityNotFoundException, UnexpectedEntityException {
        return authorize(dto, true);
    }

    @Override
    public Transfer authorize(final TransferAuthorizationDTO dto, final boolean newTransaction) throws AlreadyAuthorizedException, EntityNotFoundException, UnexpectedEntityException {
        return authorize(dto, newTransaction, false);

    }

    @Override
    public Transfer authorizeOnInsert(final LockHandler lockHandler, Transfer transfer) {
        // Only process when an user is performing a payment
        if (LoggedUser.hasUser()) {
            transfer = fetchService.fetch(transfer, Transfer.Relationships.PARENT, RelationshipHelper.nested(Transfer.Relationships.NEXT_AUTHORIZATION_LEVEL, AuthorizationLevel.Relationships.ADMIN_GROUPS));
            final Transfer parent = transfer.getParent();
            final AuthorizationLevel authorizationLevel = transfer.getNextAuthorizationLevel();
            final Member fromMember = transfer.isFromSystem() ? null : (Member) transfer.getFromOwner();
            // Only process root transfer from members and that require authorization
            if (parent == null && authorizationLevel != null) {
                boolean authorize = false;
                switch (authorizationLevel.getAuthorizer()) {
                    case BROKER:
                        // Automatically authorize when logged as the member's broker and the authorization is made by broker, or as administrator
                        authorize = (LoggedUser.isBroker() && fromMember != null && LoggedUser.element().equals(fromMember.getBroker())) || (LoggedUser.isAdministrator() && authorizationLevel.getAdminGroups().contains(LoggedUser.group()));
                        break;
                    case ADMIN:
                        // Automatically authorize when logged as administrator
                        authorize = LoggedUser.isAdministrator() && authorizationLevel.getAdminGroups().contains(LoggedUser.group());
                        break;
                }
                // Perform the authorization
                if (authorize) {
                    final TransferAuthorizationDTO dto = new TransferAuthorizationDTO();
                    dto.setTransfer(transfer);
                    transfer = doAuthorize(lockHandler, false, dto, true);
                }
            }
        }
        return transfer;
    }

    @Override
    public boolean canAuthorizeOrDeny(Transfer transfer) {
        transfer = fetchService.fetch(transfer, Payment.Relationships.FROM, Payment.Relationships.TO, RelationshipHelper.nested(Transfer.Relationships.NEXT_AUTHORIZATION_LEVEL, AuthorizationLevel.Relationships.ADMIN_GROUPS));
        AuthorizationLevel level = transfer.getNextAuthorizationLevel();
        if (level == null) {
            return false;
        }
        try {
            if (transfer.isFromSystem()) {
                if (!transfer.isToSystem() && level.getAuthorizer() == Authorizer.RECEIVER) {
                    // A payment from system to member, having receiver as authorized
                    permissionService.permission((Member) transfer.getToOwner())
                            .member(MemberPermission.PAYMENTS_AUTHORIZE)
                            .operator(OperatorPermission.PAYMENTS_AUTHORIZE)
                            .check();
                } else {
                    // Only admins can authorize
                    permissionService.permission()
                            .admin(AdminSystemPermission.PAYMENTS_AUTHORIZE)
                            .check();
                }

            } else {
                // A payment from a member. Check according to the role
                switch (level.getAuthorizer()) {
                    case PAYER:
                        permissionService.permission((Member) transfer.getFromOwner())
                                .member(MemberPermission.PAYMENTS_AUTHORIZE)
                                .operator(OperatorPermission.PAYMENTS_AUTHORIZE)
                                .check();
                        break;
                    case RECEIVER:
                        permissionService.permission((Member) transfer.getToOwner())
                                .member(MemberPermission.PAYMENTS_AUTHORIZE)
                                .operator(OperatorPermission.PAYMENTS_AUTHORIZE)
                                .check();
                        break;
                    case BROKER:
                        permissionService.permission((Member) transfer.getFromOwner())
                                .admin(AdminMemberPermission.PAYMENTS_AUTHORIZE)
                                .broker(BrokerPermission.MEMBER_PAYMENTS_AUTHORIZE)
                                .check();
                        break;
                    case ADMIN:
                        permissionService.permission((Member) transfer.getFromOwner())
                                .admin(AdminMemberPermission.PAYMENTS_AUTHORIZE)
                                .check();
                        break;
                }
            }
            // Ensure that if the static permission is granted, and this level allows admins, the logged admin is in the allowed groups
            if (LoggedUser.isAdministrator() && !level.getAdminGroups().contains(LoggedUser.group())) {
                throw new PermissionDeniedException();
            }
            return true;
        } catch (PermissionDeniedException e) {
            return false;
        }
    }

    @Override
    public boolean canCancel(final Transfer transfer) {
        if (transfer.isFromSystem()) {
            return permissionService.permission()
                    .admin(AdminSystemPermission.PAYMENTS_CANCEL)
                    .hasPermission();
        } else {
            return permissionService.permission((Member) transfer.getFromOwner())
                    .admin(AdminMemberPermission.PAYMENTS_CANCEL_AUTHORIZED_AS_MEMBER)
                    .broker(BrokerPermission.MEMBER_PAYMENTS_CANCEL_AUTHORIZED_AS_MEMBER)
                    .member(MemberPermission.PAYMENTS_CANCEL_AUTHORIZED)
                    .operator(OperatorPermission.PAYMENTS_CANCEL_AUTHORIZED)
                    .hasPermission();
        }
    }

    @Override
    public Transfer cancel(final TransferAuthorizationDTO dto) throws EntityNotFoundException, UnexpectedEntityException {
        Transfer transfer = fetchService.fetch(dto.getTransfer());
        validateAuthorization(transfer);

        // User can't cancel a transfer that is part of a scheduled payment
        if (transfer.getScheduledPayment() != null) {
            throw new UnexpectedEntityException();
        }

        return transactionHelper.maybeRunInNewTransaction(new Transactional<Transfer>() {
            @Override
            public Transfer afterCommit(final Transfer result) {
                return fetchService.fetch(result);
            }

            @Override
            public Transfer doInTransaction(final TransactionStatus status) {
                return doCancel(dto);
            }
        });
    }

    @Override
    public Transfer deny(final TransferAuthorizationDTO dto) throws EntityNotFoundException, UnexpectedEntityException {
        if (StringUtils.isEmpty(dto.getComments())) {
            // Deny requires comments
            throw new ValidationException("comments", "transferAuthorization.comments", new RequiredError());
        }

        Transfer transfer = fetchService.fetch(dto.getTransfer());
        validateAuthorization(transfer);

        return transactionHelper.maybeRunInNewTransaction(new Transactional<Transfer>() {
            @Override
            public Transfer afterCommit(final Transfer result) {
                return fetchService.fetch(result);
            }

            @Override
            public Transfer doInTransaction(final TransactionStatus status) {
                return doDeny(dto);
            }
        });
    }

    @Override
    public boolean hasAlreadyAuthorized(Transfer transfer) {
        if (!LoggedUser.hasUser()) {
            return false;
        }
        transfer = fetchService.fetch(transfer, Transfer.Relationships.AUTHORIZATIONS);
        Element logged = LoggedUser.element();
        for (TransferAuthorization auth : transfer.getAuthorizations()) {
            if (logged.equals(auth.getBy())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<TransferAuthorization> load(final Collection<Long> ids, final Relationship... fetch) {
        return transferAuthorizationDao.load(ids, fetch);
    }

    @Override
    public TransferAuthorization load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        return transferAuthorizationDao.load(id, fetch);
    }

    @Override
    public List<TransferAuthorization> searchAuthorizations(final TransferAuthorizationQuery query) {
        final Element by = fetchService.fetch(query.getBy(), Member.Relationships.BROKER);
        if (LoggedUser.isAdministrator()) {
            // Administrators search by administration when 'by member' not specified
            query.setByAdministration(query.getBy() == null);
        } else if (LoggedUser.isBroker()) {
            query.setByAdministration(false);
            query.setBy(by == null ? LoggedUser.element() : by);
        } else {
            // Members can only search by themselves
            final Member loggedMember = (Member) LoggedUser.accountOwner();
            query.setByAdministration(false);
            query.setBy(loggedMember);
        }

        return transferAuthorizationDao.search(query);
    }

    @Override
    public List<Transfer> searchTransfersAwaitingAuthorization(final TransfersAwaitingAuthorizationQuery query) {
        query.setAuthorizer(LoggedUser.element());
        return transferDao.searchTransfersAwaitingAuthorization(query);
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLockHandlerFactory(final LockHandlerFactory lockHandlerFactory) {
        this.lockHandlerFactory = lockHandlerFactory;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setRateServiceLocal(final RateServiceLocal rateService) {
        this.rateService = rateService;
    }

    public void setScheduledPaymentServiceLocal(final ScheduledPaymentServiceLocal scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTransferAuthorizationDao(final TransferAuthorizationDAO transferAuthorizationDao) {
        this.transferAuthorizationDao = transferAuthorizationDao;
    }

    public void setTransferDao(final TransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    private Transfer authorize(final TransferAuthorizationDTO dto, final boolean newTransaction, final boolean automaticallyAuthorize) {
        Transfer transfer = fetchService.fetch(dto.getTransfer());
        validateAuthorization(transfer);

        return transactionHelper.maybeRunInNewTransaction(new Transactional<Transfer>() {
            @Override
            public Transfer afterCommit(final Transfer result) {
                // Ensure the transfer is attached to the current transaction
                return fetchService.fetch(result);
            }

            @Override
            public Transfer doInTransaction(final TransactionStatus status) {
                return doAuthorize(null, newTransaction, dto, automaticallyAuthorize);
            }
        }, newTransaction, LockedAccountsOnPayments.ALL);
    }

    /**
     * Create a transfer authorization log
     */
    private TransferAuthorization createAuthorization(final Transfer transfer, final TransferAuthorization.Action action, final AuthorizationLevel level, final String comments, final boolean showToMember) {
        TransferAuthorization transferAuthorization = new TransferAuthorization();
        transferAuthorization.setTransfer(transfer);
        transferAuthorization.setLevel(level);
        transferAuthorization.setBy(LoggedUser.element());
        transferAuthorization.setDate(Calendar.getInstance());
        transferAuthorization.setAction(action);
        transferAuthorization.setComments(comments);
        transferAuthorization.setShowToMember(showToMember);
        transferAuthorization = transferAuthorizationDao.insert(transferAuthorization);

        return transferAuthorization;
    }

    private Transfer doAuthorize(final LockHandler lockHandler, final boolean newTransaction, final TransferAuthorizationDTO dto, final boolean automaticallyAuthorize) {
        final String comments = dto.getComments();
        final boolean showToMember = dto.isShowToMember();

        Transfer transfer = fetchService.reload(dto.getTransfer(), Transfer.Relationships.SCHEDULED_PAYMENT, Transfer.Relationships.AUTHORIZATIONS);
        // Check if the authorizer has already authorized
        final Element logged = LoggedUser.element();
        for (final TransferAuthorization authorization : transfer.getAuthorizations()) {
            if (logged.equals(authorization.getBy())) {
                throw new AlreadyAuthorizedException();
            }
        }

        // Update transfer
        final AuthorizationLevel authorizationLevel = transfer.getNextAuthorizationLevel();
        final AuthorizationLevel nextAuthorizationLevel = getNextAuthorizationLevel(transfer);

        final boolean processed = nextAuthorizationLevel == null;
        // Create the transfer authorization object
        final TransferAuthorization authorization = createAuthorization(transfer, TransferAuthorization.Action.AUTHORIZE, authorizationLevel, comments, showToMember);

        // Update the authorization data
        if (lockHandler == null) {
            // Invoke the method which creates and releases the locks
            transfer = updateAuthorizationData(transfer, nextAuthorizationLevel, processed, authorization, newTransaction);
        } else {
            // If there is a lockHandler already, it is assumed to be in a transaction already
            transfer = doUpdateAuthorizationData(lockHandler, transfer, nextAuthorizationLevel, processed, authorization);
        }

        transfer.getAuthorizations().add(authorization);

        // If the transfer is part of a scheduled payment, update the scheduled payment status
        if (transfer.getScheduledPayment() != null) {
            scheduledPaymentService.updateScheduledPaymentStatus(transfer.getScheduledPayment());
        }

        // Notify
        memberNotificationHandler.paymentAuthorizedOrDeniedNotification(transfer, !automaticallyAuthorize);

        if (processed) {
            paymentService.notifyTransferProcessed(transfer);
        }

        return transfer;
    }

    private Transfer doCancel(final TransferAuthorizationDTO dto) {
        Transfer transfer = fetchService.fetch(dto.getTransfer());
        final String comments = dto.getComments();
        final AuthorizationLevel authorizationLevel = transfer.getNextAuthorizationLevel();

        // Update the transfer
        transfer = transferDao.updateAuthorizationData(transfer.getId(), Transfer.Status.CANCELED, null, null, null);

        // Create the transfer authorization object
        final TransferAuthorization authorization = createAuthorization(transfer, TransferAuthorization.Action.CANCEL, authorizationLevel, comments, true);

        // Return the reserved amount
        accountService.returnReservation(authorization, transfer);

        // Update the child transfers
        updateChildTransfers(transfer, authorization);

        // Notify
        memberNotificationHandler.paymentCancelledNotification(transfer);
        return transfer;
    }

    private Transfer doDeny(final TransferAuthorizationDTO dto) {
        Transfer transfer = fetchService.fetch(dto.getTransfer());
        final String comments = dto.getComments();
        final boolean showToMember = dto.isShowToMember();

        // Update transfer
        final AuthorizationLevel authorizationLevel = transfer.getNextAuthorizationLevel();
        transfer = transferDao.updateAuthorizationData(transfer.getId(), Transfer.Status.DENIED, null, null, null);

        // If the transfer is part of a scheduled payment, deny transfers of the payment with status PENDING, SCHEDULED or BLOCKED
        if (transfer.getScheduledPayment() != null) {
            final ScheduledPayment scheduledPayment = fetchService.fetch(transfer.getScheduledPayment(), ScheduledPayment.Relationships.TRANSFERS);
            for (final Transfer currentTransfer : scheduledPayment.getTransfers()) {
                final Transfer.Status currentTransferStatus = currentTransfer.getStatus();
                if (currentTransferStatus == Transfer.Status.PENDING || currentTransferStatus == Transfer.Status.SCHEDULED || currentTransferStatus == Transfer.Status.BLOCKED) {
                    transferDao.updateAuthorizationData(currentTransfer.getId(), Transfer.Status.DENIED, null, null, null);
                }
            }
            scheduledPaymentService.updateScheduledPaymentStatus(transfer.getScheduledPayment());
        }

        // Create the transfer authorization object
        final TransferAuthorization authorization = createAuthorization(transfer, TransferAuthorization.Action.DENY, authorizationLevel, comments, showToMember);

        // Return the reserved amount
        accountService.returnReservation(authorization, transfer);

        // Update child transfers
        updateChildTransfers(transfer, authorization);

        // Notify
        memberNotificationHandler.paymentAuthorizedOrDeniedNotification(transfer, true);

        return transfer;
    }

    private Transfer doUpdateAuthorizationData(final LockHandler lockHandler, Transfer transfer, final AuthorizationLevel nextAuthorizationLevel, final boolean processed, final TransferAuthorization authorization) {
        if (processed) {
            lockHandler.lock(transfer.getFrom(), transfer.getTo());
            // apply rates
            RatesToSave rates = rateService.applyTransfer(transfer);
            /*
             * set processDate AFTER applying rates, but before persisting them. This is important, because the transfer itself must not sum up for
             * rates or balances when the rates are processed, and it does if processdate is already set. In that case, the transfer's processDate can
             * equal the fromRates's date.
             */
            Calendar processDate = (rates.getFromRates() == null) ? Calendar.getInstance() : rates.getFromRates().getDate();
            transfer.setProcessDate(processDate);
            rateService.persist(rates);
            // Return the reserved amount
            accountService.returnReservation(authorization, transfer);
            // Remove any pending closed balances on the destination account. The returnReservation will do this for the source account
            accountService.removeClosedBalancesAfter(transfer.getTo(), processDate);

            // update the transfer information, and also the rates.
            transfer = transferDao.updateAuthorizationData(transfer.getId(), Transfer.Status.PROCESSED, null, transfer.getProcessDate(), rates);
        } else {
            transfer = transferDao.updateAuthorizationData(transfer.getId(), Transfer.Status.PENDING, nextAuthorizationLevel, null, null);
        }

        // Update child transfers
        for (final Transfer childTransfer : transfer.getChildren()) {
            updateChildTransfer(lockHandler, transfer, childTransfer, authorization, processed);
        }

        return transfer;
    }

    private AuthorizationLevel getNextAuthorizationLevel(final Transfer transfer) {
        final AuthorizationLevel authorizationLevel = transfer.getNextAuthorizationLevel();
        final List<AuthorizationLevel> authorizationLevels = new ArrayList<AuthorizationLevel>(transfer.getType().getAuthorizationLevels());
        final int index = authorizationLevels.indexOf(authorizationLevel);
        if (index < 0 || index == authorizationLevels.size() - 1) {
            // This level is the highest
            return null;
        }
        final AuthorizationLevel wouldBeNext = authorizationLevels.get(index + 1);
        // Amount is not enough for the next level
        if (transfer.getAmount().compareTo(wouldBeNext.getAmount()) < 0) {
            return null;
        }
        return wouldBeNext;
    }

    private Transfer updateAuthorizationData(final Transfer transfer, final AuthorizationLevel nextAuthorizationLevel, final boolean processed, final TransferAuthorization authorization, final boolean newTransaction) {
        LockHandler lockHandler = lockHandlerFactory.getLockHandlerIfLockingAtLeast(LockedAccountsOnPayments.ALL);
        try {
            return doUpdateAuthorizationData(lockHandler, transfer, nextAuthorizationLevel, processed, authorization);
        } finally {
            if (lockHandler != null) {
                lockHandler.release();
            }
        }
    }

    private void updateChildTransfer(final LockHandler lockHandler, final Transfer parent, Transfer child, final TransferAuthorization authorization, final boolean processed) {
        child = fetchService.fetch(child, Transfer.Relationships.CHILDREN);

        RatesToSave rates = null;
        // Update the account status
        if (processed) {
            lockHandler.lock(child.getFrom(), child.getTo());
            // apply rates
            rates = rateService.applyTransfer(child);
            child.setProcessDate(parent.getProcessDate());
            rateService.persist(rates);
            // Return the reserved amount
            accountService.returnReservation(authorization, child);
            // Remove any pending closed balances on the destination account. The returnReservation will do this for the source account
            accountService.removeClosedBalancesAfter(child.getTo(), child.getProcessDate());
        }

        transferDao.updateAuthorizationData(child.getId(), parent.getStatus(), parent.getNextAuthorizationLevel(), parent.getProcessDate(), rates);

        // Update child transfers
        if (child.getChildren() != null) {
            for (final Transfer childTransfer : child.getChildren()) {
                updateChildTransfer(lockHandler, child, childTransfer, authorization, processed);
            }
        }
    }

    private void updateChildTransfers(final Transfer transfer, final TransferAuthorization authorization) {
        LockHandler lockHandler = lockHandlerFactory.getLockHandlerIfLockingAtLeast(LockedAccountsOnPayments.ALL);
        try {
            for (final Transfer childTransfer : transfer.getChildren()) {
                updateChildTransfer(lockHandler, transfer, childTransfer, authorization, true);
            }
        } finally {
            if (lockHandler != null) {
                lockHandler.release();
            }
        }
    }

    private void validateAuthorization(final Transfer transfer) {
        // Can't cancel a nested transfer, and it must be pending authorization
        if (!transfer.isRoot() || transfer.getStatus() != Transfer.Status.PENDING) {
            throw new UnexpectedEntityException();
        }
    }

}
