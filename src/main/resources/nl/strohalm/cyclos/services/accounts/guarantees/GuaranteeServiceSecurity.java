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
package nl.strohalm.cyclos.services.accounts.guarantees;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee.Status;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType.Model;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.query.PageParameters;

/**
 * Security implementation for {@link GuaranteeService}
 * 
 * @author jcomas
 */
public class GuaranteeServiceSecurity extends BaseServiceSecurity implements GuaranteeService {

    private GuaranteeServiceLocal guaranteeService;

    @Override
    public Guarantee acceptGuarantee(Guarantee guarantee, final boolean automaticLoanAuthorization) {
        guarantee = fetchService.fetch(guarantee, Guarantee.Relationships.BUYER, Guarantee.Relationships.ISSUER);

        boolean hasPermission = permissionService.permission(guarantee.getBuyer())
                .admin(AdminMemberPermission.GUARANTEES_ACCEPT_GUARANTEES_AS_MEMBER)
                .hasPermission();

        if (!hasPermission) {
            hasPermission = permissionService.permission(guarantee.getIssuer())
                    .member(MemberPermission.GUARANTEES_ISSUE_GUARANTEES)
                    .operator(OperatorPermission.GUARANTEES_ISSUE_GUARANTEES)
                    .hasPermission();
        }

        if (!hasPermission) {
            throw new PermissionDeniedException();
        }

        return guaranteeService.acceptGuarantee(guarantee, automaticLoanAuthorization && hasPermission(AdminSystemPermission.PAYMENTS_AUTHORIZE));
    }

    @Override
    public BigDecimal calculateFee(final GuaranteeFeeCalculationDTO dto) {
        checkHasUser();
        return guaranteeService.calculateFee(dto);
    }

    @Override
    public boolean canChangeStatus(final Guarantee guarantee, final Status newStatus) {
        checkHasUser();
        return guaranteeService.canChangeStatus(guarantee, newStatus);
    }

    @Override
    public boolean canRemoveGuarantee(final Guarantee guarantee) {
        checkHasUser();
        return guaranteeService.canRemoveGuarantee(guarantee);
    }

    @Override
    public Guarantee changeStatus(final Long guaranteeId, final Status newStatus) {
        Guarantee guarantee = guaranteeService.load(guaranteeId, Guarantee.Relationships.BUYER, Guarantee.Relationships.ISSUER);

        switch (newStatus) {
            case CANCELLED:
            case REJECTED:
                if (newStatus == Status.CANCELLED || LoggedUser.isAdministrator()) {
                    permissionService.permission(guarantee.getBuyer())
                            .admin(AdminMemberPermission.GUARANTEES_CANCEL_GUARANTEES_AS_MEMBER)
                            .check();
                    break;
                } // fall down for an Issuer rejecting a guarantee
            default:
                permissionService.permission(guarantee.getIssuer())
                        .member(MemberPermission.GUARANTEES_ISSUE_GUARANTEES)
                        .operator(OperatorPermission.GUARANTEES_ISSUE_GUARANTEES)
                        .check();
        }

        return guaranteeService.changeStatus(guaranteeId, newStatus);
    }

    @Override
    public Collection<? extends MemberGroup> getBuyers() {
        permissionService.permission()
                .admin(AdminMemberPermission.GUARANTEES_VIEW_PAYMENT_OBLIGATIONS, AdminMemberPermission.GUARANTEES_VIEW_CERTIFICATIONS, AdminMemberPermission.GUARANTEES_VIEW_GUARANTEES)
                .member(MemberPermission.GUARANTEES_ISSUE_GUARANTEES, MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                .operator(OperatorPermission.GUARANTEES_ISSUE_GUARANTEES, OperatorPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                .check();

        return guaranteeService.getBuyers();
    }

    @Override
    public List<Guarantee> getGuarantees(final Certification certification, final PageParameters pageParameters, final List<Status> statusList) {
        permissionService.permission()
                .admin(AdminMemberPermission.GUARANTEES_VIEW_CERTIFICATIONS)
                .member(MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS, MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                .operator(OperatorPermission.GUARANTEES_ISSUE_CERTIFICATIONS, OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                .check();

        return guaranteeService.getGuarantees(certification, pageParameters, statusList);
    }

    @Override
    public Collection<? extends MemberGroup> getIssuers() {
        permissionService.permission()
                .admin(AdminMemberPermission.GUARANTEES_VIEW_PAYMENT_OBLIGATIONS, AdminMemberPermission.GUARANTEES_VIEW_CERTIFICATIONS, AdminMemberPermission.GUARANTEES_VIEW_GUARANTEES)
                .member()
                .operator()
                .check();

        return guaranteeService.getIssuers();
    }

    @Override
    public Collection<? extends MemberGroup> getIssuers(final GuaranteeType guaranteeType) {
        permissionService.permission()
                .admin(AdminMemberPermission.GUARANTEES_REGISTER_GUARANTEES)
                .check();

        return guaranteeService.getIssuers(guaranteeType);
    }

    @Override
    public Collection<Model> getRelatedGuaranteeModels() {
        permissionService.permission()
                .member()
                .operator()
                .check();

        return guaranteeService.getRelatedGuaranteeModels();
    }

    @Override
    public Collection<? extends MemberGroup> getSellers() {
        permissionService.permission()
                .admin(AdminMemberPermission.GUARANTEES_VIEW_PAYMENT_OBLIGATIONS, AdminMemberPermission.GUARANTEES_VIEW_CERTIFICATIONS, AdminMemberPermission.GUARANTEES_VIEW_GUARANTEES)
                .member(MemberPermission.GUARANTEES_ISSUE_GUARANTEES, MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                .operator(OperatorPermission.GUARANTEES_ISSUE_GUARANTEES, OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                .check();

        return guaranteeService.getSellers();
    }

    @Override
    public boolean isBuyer() {
        checkHasUser();
        return guaranteeService.isBuyer();
    }

    @Override
    public boolean isIssuer() {
        checkHasUser();
        return guaranteeService.isIssuer();
    }

    @Override
    public boolean isSeller() {
        checkHasUser();
        return guaranteeService.isSeller();
    }

    @Override
    public Guarantee load(final Long id, final Relationship... fetch) {
        Guarantee guarantee = guaranteeService.load(id, fetch);

        checkView(guarantee);

        return guarantee;
    }

    @Override
    public Guarantee loadFromTransfer(final Transfer transfer) {
        Guarantee guarantee = guaranteeService.loadFromTransfer(transfer);
        if (guarantee != null && !canView(guarantee)) {
            guarantee = null;
        }

        return guarantee;
    }

    @Override
    public Guarantee registerGuarantee(final Guarantee guarantee) {
        permissionService.permission(guarantee.getBuyer())
                .admin(AdminMemberPermission.GUARANTEES_REGISTER_GUARANTEES)
                .check();

        if (guarantee.getIssuer() != null) {
            permissionService.checkManages(guarantee.getIssuer());
        }
        if (guarantee.getSeller() != null) {
            permissionService.checkManages(guarantee.getSeller());
        }
        // Ensure a logged user don't update a guarantee.
        if (!guarantee.isTransient()) {
            throw new PermissionDeniedException();
        }

        return guaranteeService.registerGuarantee(guarantee);
    }

    @Override
    public int remove(final Long guaranteeId) {
        Guarantee guarantee = guaranteeService.load(guaranteeId, Guarantee.Relationships.BUYER);
        if (canRemoveGuarantee(guarantee)) {
            return guaranteeService.remove(guaranteeId);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public Guarantee requestGuarantee(final PaymentObligationPackDTO pack) {
        permissionService.permission()
                .member(MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                .operator(OperatorPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                .check();

        return guaranteeService.requestGuarantee(pack);
    }

    @Override
    public List<Guarantee> search(final GuaranteeQuery queryParameters) {
        final boolean isIssuer = isIssuer();
        final boolean isBuyer = isBuyer();
        final boolean isSeller = isSeller();

        if (isIssuer) {
            final Group group = fetchService.fetch(LoggedUser.group(), Group.Relationships.GUARANTEE_TYPES);
            if (group.getGuaranteeTypes().isEmpty()) { // has no permission to any guarantee type
                return Collections.emptyList();
            } else { // check valid guarantee type selection
                if (queryParameters.getGuaranteeType() == null) { // all allowed GT
                    queryParameters.setAllowedGuaranteeTypes(group.getGuaranteeTypes());
                } else if (!group.getGuaranteeTypes().contains(queryParameters.getGuaranteeType())) {
                    throw new IllegalArgumentException("Guarantee type not allowed to filter: " + queryParameters.getGuaranteeType());
                }
            }
            queryParameters.setIssuer((Member) LoggedUser.accountOwner());
        }

        if (isBuyer && isSeller) {
            queryParameters.setLoggedMember((Member) LoggedUser.accountOwner());
        } else if (isBuyer) {
            queryParameters.setBuyer((Member) LoggedUser.accountOwner());
        } else if (isSeller) {
            queryParameters.setLoggedMember((Member) LoggedUser.accountOwner());
        }

        // if hasn't got any role then we must set the logged user as the buyer (to get guarantees whose model is with buyer only)
        boolean hasAdminViewPermission = hasPermission(AdminMemberPermission.GUARANTEES_VIEW_GUARANTEES);
        final boolean hasNoRole = !isBuyer && !isIssuer && !isSeller && !hasAdminViewPermission;
        if (hasNoRole) {
            queryParameters.setBuyer((Member) LoggedUser.accountOwner());
        } else if (hasAdminViewPermission) { // the logged user is an admin with view permissions
            queryParameters.setManagedMemberGroups(permissionService.getManagedMemberGroups());
        }

        return guaranteeService.search(queryParameters);
    }

    public void setGuaranteeServiceLocal(final GuaranteeServiceLocal guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Override
    public void validate(final Guarantee guarantee, final boolean isAuthorization) {
        guaranteeService.validate(guarantee, isAuthorization);
    }

    private boolean canView(final Guarantee guarantee) {
        // we have a permission to define a member as a guarantee's issuer then
        // if the logged user is the guarantee's issuer and he doesn't have permission we must reject him
        // we must use the account owner to support operators too
        AccountOwner owner = LoggedUser.accountOwner();
        if (guarantee.getIssuer().equals(owner) && !permissionService.hasPermission(MemberPermission.GUARANTEES_ISSUE_GUARANTEES, OperatorPermission.GUARANTEES_ISSUE_GUARANTEES)) {
            return false;
        } else if (guarantee.getIssuer().equals(owner)) {
            return true;
        }

        boolean manages = false;
        if (guarantee.getGuaranteeType().getModel() == GuaranteeType.Model.WITH_BUYER_ONLY) {
            manages = permissionService.manages(guarantee.getBuyer());
        } else {
            manages = permissionService.manages(guarantee.getBuyer()) || permissionService.manages(guarantee.getSeller());
        }

        if (!manages) {
            return false;
        } else {
            return permissionService.permission()
                    .admin(AdminMemberPermission.GUARANTEES_VIEW_GUARANTEES)
                    .member()
                    .operator()
                    .hasPermission();
        }
    }

    private void checkView(final Guarantee guarantee) {
        if (!canView(guarantee)) {
            throw new PermissionDeniedException();
        }
    }
}
