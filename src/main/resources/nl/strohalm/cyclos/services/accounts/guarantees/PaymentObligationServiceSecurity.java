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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation.Status;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligationQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

import org.apache.commons.collections.CollectionUtils;

public class PaymentObligationServiceSecurity extends BaseServiceSecurity implements PaymentObligationService {

    private PaymentObligationServiceLocal paymentObligationService;
    private GuaranteeServiceLocal         guaranteeService;

    @Override
    public boolean canChangeStatus(final PaymentObligation paymentObligation, final PaymentObligation.Status newStatus) {
        checkHasUser();
        return paymentObligationService.canChangeStatus(paymentObligation, newStatus);
    }

    @Override
    public boolean canDelete(final PaymentObligation paymentObligation) {
        checkHasUser();
        return paymentObligationService.canDelete(paymentObligation);
    }

    @Override
    public PaymentObligation changeStatus(final Long paymentObligationId, final PaymentObligation.Status newStatus) {
        PaymentObligation paymentObligation = paymentObligationService.load(paymentObligationId, PaymentObligation.Relationships.BUYER);

        switch (newStatus) {
            case REJECTED:
                permissionService.permission(paymentObligation.getSeller())
                        .member(MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                        .operator(OperatorPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                        .check();
                break;
            default:
                permissionService.permission(paymentObligation.getBuyer())
                        .member(MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                        .operator(OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                        .check();
        }

        return paymentObligationService.changeStatus(paymentObligationId, newStatus);
    }

    @Override
    public Long[] checkPaymentObligationPeriod(final PaymentObligationPackDTO dto) {
        if (!guaranteeService.isSeller()) {
            throw new PermissionDeniedException();
        }

        // ensure each PO
        List<PaymentObligation> paymentObligations = paymentObligationService.loadOrderedByExpiration(dto.getPaymentObligations());
        if (CollectionUtils.isNotEmpty(paymentObligations)) {
            for (PaymentObligation po : paymentObligations) {
                if (!LoggedUser.element().equals(po.getSeller())) {
                    throw new PermissionDeniedException();
                }
            }
        }

        return paymentObligationService.checkPaymentObligationPeriod(dto);
    }

    @Override
    public PaymentObligation.Status[] getStatusToFilter() {
        if (!guaranteeService.isBuyer() && !guaranteeService.isSeller() && !hasPermission(AdminMemberPermission.GUARANTEES_VIEW_PAYMENT_OBLIGATIONS)) {
            throw new PermissionDeniedException();
        }

        return paymentObligationService.getStatusToFilter();
    }

    @Override
    public PaymentObligation load(final Long id, final Relationship... fetch) {
        PaymentObligation po = paymentObligationService.load(id, fetch);
        po = fetchService.fetch(po, RelationshipHelper.nested(PaymentObligation.Relationships.BUYER, Element.Relationships.GROUP), RelationshipHelper.nested(PaymentObligation.Relationships.SELLER, Element.Relationships.GROUP));

        // if the logged user can issue certifications to the payment obligation's buyer group
        // then is an issuer that can view the payment obligation
        boolean hasPermission = permissionService.permission()
                .memberFor(MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS, po.getBuyer().getMemberGroup())
                .operatorFor(OperatorPermission.GUARANTEES_ISSUE_CERTIFICATIONS, MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS, po.getBuyer().getMemberGroup())
                .hasPermission();

        if (hasPermission) {
            return po;
        }

        boolean manages = permissionService.manages(po.getSeller()) || permissionService.manages(po.getBuyer());
        if (!manages) {
            throw new PermissionDeniedException();
        } else {
            permissionService.permission()
                    .admin(AdminMemberPermission.GUARANTEES_VIEW_PAYMENT_OBLIGATIONS)
                    .member(MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS, MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                    .operator(OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS, OperatorPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                    .check();
        }
        return po;
    }

    @Override
    public int remove(final Long paymentObligationId) {
        PaymentObligation paymentObligation = paymentObligationService.load(paymentObligationId);
        if (canDelete(paymentObligation)) {
            return paymentObligationService.remove(paymentObligationId);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public PaymentObligation save(final PaymentObligation paymentObligation, final boolean validateBeforeSave) {
        Member seller = fetchService.fetch(paymentObligation.getSeller(), Element.Relationships.GROUP);
        permissionService.permission(paymentObligation.getBuyer())
                .memberFor(MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS, seller.getGroup())
                .operatorFor(OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS, MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS, paymentObligation.getSeller().getGroup())
                .check();

        // Only can modify an obligation payment with REGISTERED status and the status can only be set to REGISTERED.
        // Transient payment obligation status is ensured afterwards.
        if (!paymentObligation.isTransient()) {
            PaymentObligation savedPO = paymentObligationService.load(paymentObligation.getId());
            PermissionHelper.checkEquals(Status.REGISTERED, savedPO.getStatus());
            PermissionHelper.checkEquals(Status.REGISTERED, paymentObligation.getStatus());
        }

        // the security must ignore the validate parameter and set true!
        return paymentObligationService.save(paymentObligation, true);
    }

    @Override
    public List<PaymentObligation> search(PaymentObligationQuery queryParameters) {
        final boolean isSeller = guaranteeService.isSeller();
        final boolean isBuyer = guaranteeService.isBuyer();
        final boolean adminCanView = hasPermission(AdminMemberPermission.GUARANTEES_VIEW_PAYMENT_OBLIGATIONS);

        if (!isBuyer && !isSeller && !adminCanView) {
            throw new PermissionDeniedException();
        }

        if (isBuyer && isSeller) {
            queryParameters.setLoggedMember((Member) LoggedUser.accountOwner());
        } else if (isBuyer) {
            queryParameters.setBuyer((Member) LoggedUser.accountOwner());
        } else if (isSeller) {
            queryParameters.setSeller((Member) LoggedUser.accountOwner());
            final List<PaymentObligation.Status> sellerStatusToFilter = Arrays.asList(paymentObligationService.getSellerStatusToFilter());
            if (sellerStatusToFilter.isEmpty()) {
                return Collections.emptyList();
            } else if (CollectionUtils.isEmpty(queryParameters.getStatusList())) {
                queryParameters = (PaymentObligationQuery) queryParameters.clone();
                queryParameters.setStatusList(sellerStatusToFilter);
            } else { // check valid status selection
                for (final PaymentObligation.Status st : queryParameters.getStatusList()) {
                    if (!sellerStatusToFilter.contains(st)) {
                        throw new IllegalArgumentException("Payment Obligation status not allowed to filter by: " + st);
                    }
                }
            }
        } else { // admin can view
            queryParameters.setManagedMemberGroups(permissionService.getManagedMemberGroups());
        }

        return paymentObligationService.search(queryParameters);
    }

    public void setGuaranteeServiceLocal(final GuaranteeServiceLocal guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    public void setPaymentObligationServiceLocal(final PaymentObligationServiceLocal paymentObligationService) {
        this.paymentObligationService = paymentObligationService;
    }

    @Override
    public void validate(final PaymentObligation paymentObligation) {
        paymentObligationService.validate(paymentObligation);
    }

}
