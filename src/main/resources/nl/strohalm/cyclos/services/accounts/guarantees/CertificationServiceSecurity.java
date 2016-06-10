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
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class CertificationServiceSecurity extends BaseServiceSecurity implements CertificationService {

    private CertificationServiceLocal certificationService;
    private GuaranteeServiceLocal     guaranteeService;

    @Override
    public boolean canChangeStatus(final Certification certification, final Certification.Status newStatus) {
        checkHasUser();
        return certificationService.canChangeStatus(certification, newStatus);
    }

    @Override
    public boolean canDelete(final Certification certification) {
        checkHasUser();
        return certificationService.canDelete(certification);
    }

    @Override
    public void changeStatus(final Long certificationId, final Certification.Status newStatus) {
        Certification certification = certificationService.load(certificationId);

        switch (newStatus) {
            case CANCELLED:
                permissionService.permission(certification.getIssuer())
                        .admin(AdminMemberPermission.GUARANTEES_CANCEL_CERTIFICATIONS_AS_MEMBER)
                        .check();
                break;
            default:
                permissionService.permission(certification.getIssuer())
                        .member(MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS)
                        .operator(OperatorPermission.GUARANTEES_ISSUE_CERTIFICATIONS)
                        .check();
        }

        certificationService.changeStatus(certificationId, newStatus);
    }

    @Override
    public Certification getActiveCertification(final Currency currency, final Member buyer, final Member issuer) {
        boolean hasPermission = false;

        if (permissionService.relatesTo(buyer) && guaranteeService.isSeller()) {
            hasPermission = permissionService.permission()
                    .member(MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                    .operator(OperatorPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                    .hasPermission();
        }

        if (!hasPermission) {
            throw new PermissionDeniedException();
        }

        return certificationService.getActiveCertification(currency, buyer, issuer);
    }

    @Override
    public List<Member> getCertificationIssuers(final PaymentObligation paymentObligation) {
        boolean hasPermission = false;
        if (guaranteeService.isSeller()) {
            hasPermission = permissionService.permission(paymentObligation.getSeller())
                    .member(MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                    .operator(OperatorPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS)
                    .hasPermission();
        }

        if (!hasPermission) {
            throw new PermissionDeniedException();
        }

        return certificationService.getCertificationIssuers(paymentObligation);
    }

    @Override
    public BigDecimal getUsedAmount(final Certification certification, final boolean includePendingGuarantees) {
        checkView(certification);
        return certificationService.getUsedAmount(certification, includePendingGuarantees);
    }

    @Override
    public Certification load(final Long id, final Relationship... fetch) {
        Certification certification = certificationService.load(id, fetch);
        checkView(certification);

        return certification;
    }

    @Override
    public int remove(final Long certificationId) {
        Certification certification = certificationService.load(certificationId);
        if (canDelete(certification)) {
            return certificationService.remove(certificationId);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Certification save(final Certification certification) {
        permissionService.permission(certification.getIssuer())
                .member(MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS)
                .check();

        if (!certification.isTransient()) {
            throw new PermissionDeniedException();
        }

        checkGuaranteeType(certification);

        MemberGroup buyerGroup = (MemberGroup) fetchService.fetch(certification.getBuyer(), Element.Relationships.GROUP).getGroup();

        // Check the buyer
        Collection<MemberGroup> allowedBuyerGroups = (Collection<MemberGroup>) guaranteeService.getBuyers();
        PermissionHelper.checkContains(allowedBuyerGroups, buyerGroup);

        return certificationService.save(certification);
    }

    @Override
    public List<Certification> search(final CertificationQuery queryParameters) {
        checkSearch(queryParameters);

        return certificationService.search(queryParameters);
    }

    @Override
    public List<CertificationDTO> searchWithUsedAmount(final CertificationQuery queryParameters) {
        checkSearch(queryParameters);

        return certificationService.searchWithUsedAmount(queryParameters);
    }

    public void setCertificationServiceLocal(final CertificationServiceLocal certificationService) {
        this.certificationService = certificationService;
    }

    public void setGuaranteeServiceLocal(final GuaranteeServiceLocal guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @Override
    public void validate(final Certification certification) {
        certificationService.validate(certification);
    }

    private void checkGuaranteeType(final Certification certification) {

        Collection<GuaranteeType> allowedGuaranteeTypes = fetchService.fetch(LoggedUser.group(), Group.Relationships.GUARANTEE_TYPES).getGuaranteeTypes();

        CollectionUtils.filter(allowedGuaranteeTypes, new Predicate() {

            @Override
            public boolean evaluate(final Object object) {
                return GuaranteeType.Model.WITH_PAYMENT_OBLIGATION == ((GuaranteeType) object).getModel();
            }
        });

        PermissionHelper.checkContains(allowedGuaranteeTypes, certification.getGuaranteeType());

    }

    private void checkSearch(final CertificationQuery queryParameters) {
        permissionService.permission()
                .admin(AdminMemberPermission.GUARANTEES_VIEW_CERTIFICATIONS)
                .member(MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS, MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                .operator(OperatorPermission.GUARANTEES_ISSUE_CERTIFICATIONS, OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                .check();

        if (LoggedUser.isAdministrator()) {
            queryParameters.setManagedMemberGroups(permissionService.getManagedMemberGroups());
        } else {
            queryParameters.setViewer((Member) LoggedUser.accountOwner());
        }
    }

    private void checkView(Certification certification) {
        certification = fetchService.fetch(certification, Certification.Relationships.BUYER, Certification.Relationships.ISSUER);

        boolean manages = permissionService.manages(certification.getIssuer()) || permissionService.manages(certification.getBuyer());
        if (!manages) {
            throw new PermissionDeniedException();
        } else {
            permissionService.permission()
                    .admin(AdminMemberPermission.GUARANTEES_VIEW_CERTIFICATIONS)
                    .member(MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS, MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                    .operator(OperatorPermission.GUARANTEES_ISSUE_CERTIFICATIONS, OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)
                    .check();
        }
    }
}
