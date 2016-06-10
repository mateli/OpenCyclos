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
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.access.PermissionCheck;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.loans.BaseLoanQuery;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanRepaymentAmountsDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link LoanService}
 * 
 * @author Luis
 */
public class LoanServiceSecurity extends BaseServiceSecurity implements LoanService {

    private LoanServiceLocal loanService;

    @Override
    public List<LoanPayment> calculatePaymentProjection(final ProjectionDTO params) {
        permissionService.permission().admin(AdminMemberPermission.LOANS_GRANT).check();
        return loanService.calculatePaymentProjection(params);
    }

    @Override
    public LoanPayment discard(final LoanPaymentDTO dto) throws UnexpectedEntityException {
        permission(dto).admin(AdminMemberPermission.LOANS_DISCARD).check();
        Calendar date = dto.getDate();
        // Check for a specific permission for admins to set a date
        if (date != null) {
            // Currently, there's no specific permission for discard with date. We use repay with date
            permissionService.permission().admin(AdminMemberPermission.LOANS_REPAY_WITH_DATE).check();
        }
        return loanService.discard(dto);
    }

    @Override
    public LoanRepaymentAmountsDTO getLoanPaymentAmount(final LoanPaymentDTO dto) {
        checkView(permission(dto));
        return loanService.getLoanPaymentAmount(dto);
    }

    @Override
    public Loan grant(final GrantLoanDTO params) throws CreditsException {
        if (params.isAutomatic()) {
            // This is a manually granted loan
            throw new PermissionDeniedException();
        }
        permissionService.permission(params.getMember()).admin(AdminMemberPermission.LOANS_GRANT).check();
        Calendar date = params.getDate();
        // Check for a specific permission for admins to set a date
        if (date != null) {
            // Currently, there's no specific permission for discard with date. We use repay with date
            permissionService.permission().admin(AdminMemberPermission.LOANS_GRANT_WITH_DATE).check();
        }
        TransferType transferType = params.getTransferType();
        // Null transfer type will fail validation, so, we don't need to check for it here
        if (transferType != null) {
            // Check the permission to the loan transfer type
            AdminGroup group = LoggedUser.group();
            Collection<TransferType> transferTypes = fetchService.fetch(group, Group.Relationships.TRANSFER_TYPES).getTransferTypes();
            if (!transferTypes.contains(transferType)) {
                throw new PermissionDeniedException();
            }
        }
        return loanService.grant(params);
    }

    @Override
    public Loan load(final Long id, final Relationship... fetch) {
        Loan loan = loanService.load(id, fetch);
        checkView(permission(loan));
        return loan;
    }

    @Override
    public TransactionSummaryVO loanSummary(final Member member) {
        checkView(permissionService.permission(member));
        return loanService.loanSummary(member);
    }

    @Override
    public Loan markAsInProcess(final Loan loan) throws UnexpectedEntityException {
        checkExpiredStatus(loan);
        return loanService.markAsInProcess(loan);
    }

    @Override
    public Loan markAsRecovered(final Loan loan) throws UnexpectedEntityException {
        checkExpiredStatus(loan);
        return loanService.markAsRecovered(loan);
    }

    @Override
    public Loan markAsUnrecoverable(final Loan loan) throws UnexpectedEntityException {
        checkExpiredStatus(loan);
        return loanService.markAsUnrecoverable(loan);
    }

    @Override
    public TransactionSummaryVO paymentsSummary(final LoanPaymentQuery query) {
        applyRestrictions(query);
        return loanService.paymentsSummary(query);
    }

    @Override
    public Transfer repay(final RepayLoanDTO params) throws UpperCreditLimitReachedException, UnexpectedEntityException {
        Loan loan = fetchLoan(params.getLoan());

        // Members can repay loans to their loan groups, depending on group settings
        if (LoggedUser.isMember() && loan.getLoanGroup() != null && !LoggedUser.member().equals(loan.getMember())) {
            // Loan is to a group and not directly to the logged member
            boolean hasPermission = false;
            if (loan.getToMembers().contains(LoggedUser.member())) {
                // The member was really a receiver of the loan, by the group
                MemberGroup group = LoggedUser.member().getMemberGroup();
                if (group.getMemberSettings().isRepayLoanByGroup()) {
                    // This permission does not check the member
                    hasPermission = permissionService.permission()
                            .member(MemberPermission.LOANS_REPAY)
                            .operator(OperatorPermission.LOANS_REPAY)
                            .hasPermission();
                }
            }
            if (!hasPermission) {
                // Cannot view
                throw new PermissionDeniedException();
            }
        } else {
            // Brokers cannot repay
            permission(params)
                    .admin(AdminMemberPermission.LOANS_REPAY)
                    .member(MemberPermission.LOANS_REPAY)
                    .operator(OperatorPermission.LOANS_REPAY)
                    .check();
        }

        // Check for a specific permission for admins to set a date
        Calendar date = params.getDate();
        if (date != null) {
            permissionService.permission().admin(AdminMemberPermission.LOANS_REPAY_WITH_DATE).check();
        }
        return loanService.repay(params);
    }

    @Override
    public List<LoanPayment> search(final LoanPaymentQuery query) {
        applyRestrictions(query);
        return loanService.search(query);
    }

    @Override
    public List<Loan> search(final LoanQuery query) {
        applyRestrictions(query);
        return loanService.search(query);
    }

    public void setLoanServiceLocal(final LoanServiceLocal loanService) {
        this.loanService = loanService;
    }

    @Override
    public void validate(final GrantLoanDTO params) {
        // No permission check needed for validate
        loanService.validate(params);
    }

    private void applyRestrictions(final BaseLoanQuery query) {
        Member member = query.getMember();
        if (member == null) {
            permissionService.permission()
                    .admin(AdminMemberPermission.LOANS_VIEW)
                    .broker(BrokerPermission.LOANS_VIEW)
                    .member(MemberPermission.LOANS_VIEW)
                    .operator(OperatorPermission.LOANS_VIEW)
                    .check();
            if (LoggedUser.isAdministrator()) {
                query.setGroups(PermissionHelper.checkSelection(permissionService.getVisibleMemberGroups(), query.getGroups()));
            } else if (LoggedUser.isBroker()) {
                query.setBroker(LoggedUser.member());
            }
        } else {
            permissionService.permission(member)
                    .admin(AdminMemberPermission.LOANS_VIEW)
                    .broker(BrokerPermission.LOANS_VIEW)
                    .member(MemberPermission.LOANS_VIEW)
                    .operator(OperatorPermission.LOANS_VIEW)
                    .check();
        }
    }

    private void checkExpiredStatus(final Loan loan) {
        permission(loan).admin(AdminMemberPermission.LOANS_MANAGE_EXPIRED_STATUS).check();
    }

    private void checkView(final PermissionCheck permission) {
        permission
                .admin(AdminMemberPermission.LOANS_VIEW)
                .broker(BrokerPermission.LOANS_VIEW)
                .member(MemberPermission.LOANS_VIEW)
                .operator(OperatorPermission.LOANS_VIEW)
                .check();
    }

    private Loan fetchLoan(final Loan loan) {
        if (loan == null) {
            throw new ValidationException();
        }
        return fetchService.fetch(loan, RelationshipHelper.nested(Loan.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER));
    }

    private boolean loggedUserManagesSomebodyWhoCanViewTheLoan(final Loan loan) {
        // Can manage the responsible
        if (permissionService.manages(loan.getMember())) {
            return true;
        }

        // Or, can manage one of the members of the loan group.
        if (loan.getLoanGroup() != null) {
            for (Member member : loan.getToMembers()) {
                if (permissionService.manages(member)) {
                    MemberGroup group = member.getMemberGroup();
                    if (group.getMemberSettings().isViewLoansByGroup()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private PermissionCheck permission(Loan loan) {
        loan = fetchLoan(loan);
        if (loggedUserManagesSomebodyWhoCanViewTheLoan(loan)) {
            // This permission does not check the member
            return permissionService.permission()
                    .admin(AdminMemberPermission.LOANS_VIEW)
                    .broker(BrokerPermission.LOANS_VIEW)
                    .member(MemberPermission.LOANS_VIEW)
                    .operator(OperatorPermission.LOANS_VIEW);
        }

        // Cannot view
        throw new PermissionDeniedException();
    }

    private PermissionCheck permission(final LoanPaymentDTO dto) {
        if (dto.getLoan() != null) {
            if (dto.getLoanPayment() != null) {
                // When both are present, make
                LoanPayment loanPayment = fetchService.fetch(dto.getLoanPayment());
                if (!loanPayment.getLoan().equals(dto.getLoan())) {
                    throw new PermissionDeniedException();
                }
            }
            return permission(dto.getLoan());
        } else if (dto.getLoanPayment() != null) {
            LoanPayment loanPayment = fetchService.fetch(dto.getLoanPayment());
            return permission(loanPayment.getLoan());
        }
        throw new ValidationException();
    }
}
