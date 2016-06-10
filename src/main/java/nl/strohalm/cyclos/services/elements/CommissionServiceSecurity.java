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
package nl.strohalm.cyclos.services.elements;

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link CommissionService}
 * 
 * @author Rinke
 */
public class CommissionServiceSecurity extends BaseServiceSecurity implements CommissionService {

    private CommissionServiceLocal commissionService;

    @Override
    public BrokerCommissionContract acceptBrokerCommissionContract(final Long brokerCommissionContractId) {
        // Called from AcceptBrokerCommissionContractAction
        checkMemberPermission(brokerCommissionContractId);
        return commissionService.acceptBrokerCommissionContract(brokerCommissionContractId);
    }

    @Override
    public BrokerCommissionContract cancelBrokerCommissionContract(final Long brokerCommissionContractId) {
        // called from CancelBrokerCommissionContractAction
        Member member = getMemberFromContractId(brokerCommissionContractId);
        checkAdminBrokerPermission(member);
        return commissionService.cancelBrokerCommissionContract(brokerCommissionContractId);
    }

    @Override
    public BrokerCommissionContract denyBrokerCommissionContract(final Long brokerCommissionContractId) {
        // called from DenyBrokerCommissionContractAction
        checkMemberPermission(brokerCommissionContractId);
        return commissionService.denyBrokerCommissionContract(brokerCommissionContractId);
    }

    @Override
    public List<CommissionChargeStatusDTO> getCommissionChargeStatus(final Member member) {
        // called from ListBrokerCommissionContractsAction
        checkBrokerMemberPermission(member);
        return commissionService.getCommissionChargeStatus(member);
    }

    @Override
    public boolean hasBrokerCommissionContracts() {
        return commissionService.hasBrokerCommissionContracts();
    }

    @Override
    public List<BrokerCommission> listPossibleCommissionsForNewContract(final Member brokered) {
        if (!permissionService.permission(brokered).broker(BrokerPermission.MEMBERS_MANAGE_CONTRACTS).hasPermission()) {
            return Collections.emptyList();
        }
        return commissionService.listPossibleCommissionsForNewContract(brokered);
    }

    @Override
    public BrokerCommissionContract loadBrokerCommissionContract(final Long id, final Relationship... fetch) {
        // Called by EditBrokerCommissionContractAction
        Relationship[] newFetch = addToFetch(fetch, RelationshipHelper.nested(BrokerCommissionContract.Relationships.BROKERING, Brokering.Relationships.BROKERED));
        BrokerCommissionContract contract = commissionService.loadBrokerCommissionContract(id, newFetch);

        if (permissionService.manages(contract.getBrokering().getBrokered()) || permissionService.manages(contract.getBrokering().getBroker())) {
            checkAllPermissions();
        } else {
            throw new PermissionDeniedException();
        }

        return contract;
    }

    @Override
    public List<DefaultBrokerCommission> loadDefaultBrokerCommissions(final Member broker, final Relationship... fetch) {
        // Called by ManageBrokerCommssionAction and DefaultBrokerCommissionAction

        // we must check management and permissions separately because a limitation of the permission-check framework
        // It doesn't support a logged broker manages himself in conjunction with broker permissions
        permissionService.checkManages(broker);
        permissionService.permission()
                .admin(AdminMemberPermission.BROKERINGS_MANAGE_COMMISSIONS)
                .broker(BrokerPermission.MEMBERS_MANAGE_DEFAULTS)
                .check();

        return commissionService.loadDefaultBrokerCommissions(broker, fetch);
    }

    @Override
    public int removeBrokerCommissionContracts(final Long... ids) {
        // called by RemoveBrokerCommissionContractsAction
        for (Long id : ids) {
            Member member = getMemberFromContractId(id);
            checkBrokerContracts(member);
        }
        return commissionService.removeBrokerCommissionContracts(ids);
    }

    @Override
    public BrokerCommissionContract saveBrokerCommissionContract(final BrokerCommissionContract brokerCommissionContract) {
        // called by EditBrokerCommissionContractAction
        Member member = null;
        if (brokerCommissionContract.isTransient()) {
            member = fetchService.fetch(brokerCommissionContract.getBrokering(), Brokering.Relationships.BROKERED).getBrokered();
            // Ensure the given BrokerCommission is valid
            BrokerCommission brokerCommission = brokerCommissionContract.getBrokerCommission();
            if (brokerCommission != null) {
                // When null will fail validation
                List<BrokerCommission> possibleCommissions = commissionService.listPossibleCommissionsForNewContract(member);
                PermissionHelper.checkContains(possibleCommissions, brokerCommission);
            }
        } else {
            member = getMemberFromContractId(brokerCommissionContract.getId());
        }
        checkBrokerContracts(member);
        return commissionService.saveBrokerCommissionContract(brokerCommissionContract);
    }

    @Override
    public List<DefaultBrokerCommission> saveDefaultBrokerCommissions(final List<DefaultBrokerCommission> commissions) {
        // called by DefaultBrokerCommissionsAction
        if (!permissionService.hasPermission(BrokerPermission.MEMBERS_MANAGE_DEFAULTS)) {
            throw new PermissionDeniedException();
        }

        for (DefaultBrokerCommission commission : commissions) {
            if (!LoggedUser.element().equals(commission.getBroker())) {
                throw new PermissionDeniedException();
            }
        }
        return commissionService.saveDefaultBrokerCommissions(commissions);
    }

    @Override
    public List<BrokerCommissionContract> searchBrokerCommissionContracts(final BrokerCommissionContractQuery query) {
        // called by ListBrokerCommissionContraxctsAction, ManageBrokerCommissionsAction, SearchBrokerCommissionContractsAction

        checkAllPermissions();

        if (LoggedUser.isBroker()) {
            query.setBroker((Member) LoggedUser.element());
        } else if (LoggedUser.isMember()) {
            query.setMember((Member) LoggedUser.element());
        } else {
            query.setManagedMemberGroups(permissionService.getManagedMemberGroups());
        }

        return commissionService.searchBrokerCommissionContracts(query);
    }

    public void setCommissionServiceLocal(final CommissionServiceLocal commissionService) {
        this.commissionService = commissionService;
    }

    @Override
    public void stopCommissions(final BrokerCommission brokerCommission, final Member broker, final boolean removeDefaulBrokerCommission) {
        checkAdminPermission(broker);
        commissionService.stopCommissions(brokerCommission, broker, removeDefaulBrokerCommission);
    }

    @Override
    public void suspendCommissions(final BrokerCommission brokerCommission, final Member broker) {
        checkAdminPermission(broker);
        commissionService.suspendCommissions(brokerCommission, broker);
    }

    @Override
    public void unsuspendCommissions(final BrokerCommission brokerCommission, final Member broker) {
        checkAdminPermission(broker);
        commissionService.unsuspendCommissions(brokerCommission, broker);
    }

    @Override
    public void validateBrokerCommissionContract(final BrokerCommissionContract brokerCommissionContract) throws ValidationException {
        // no permissions on validation
        commissionService.validateBrokerCommissionContract(brokerCommissionContract);
    }

    @Override
    public void validateDefaultBrokerCommissions(final List<DefaultBrokerCommission> defaultBrokerCommissions) throws ValidationException {
        // no permissions on validation
        commissionService.validateDefaultBrokerCommissions(defaultBrokerCommissions);
    }

    private void checkAdminBrokerPermission(final Member member) {
        permissionService.permission(member)
                .admin(AdminMemberPermission.BROKERINGS_MANAGE_COMMISSIONS)
                .broker(BrokerPermission.MEMBERS_MANAGE_CONTRACTS)
                .check();
    }

    private void checkAdminPermission(final Member member) {
        permissionService.permission(member)
                .admin(AdminMemberPermission.BROKERINGS_MANAGE_COMMISSIONS)
                .check();
    }

    private void checkAllPermissions() {
        if (!commissionService.hasAllPermissions()) {
            throw new PermissionDeniedException();
        }
    }

    private void checkBrokerContracts(final Member member) {
        permissionService.checkManages(member);
        permissionService.permission()
                .broker(BrokerPermission.MEMBERS_MANAGE_CONTRACTS)
                .check();
    }

    private void checkBrokerMemberPermission(final Member member) {
        permissionService.permission(member)
                .broker(BrokerPermission.MEMBERS_MANAGE_CONTRACTS)
                .member()
                .check();
    }

    private void checkMemberPermission(final Long brokerCommissionContractId) {
        Member member = getMemberFromContractId(brokerCommissionContractId);
        if (!LoggedUser.element().equals(member)) {
            throw new PermissionDeniedException();
        }
    }

    private Member getMemberFromContractId(final Long brokerCommissionContractId) {
        BrokerCommissionContract contract = commissionService.loadBrokerCommissionContract(brokerCommissionContractId,
                RelationshipHelper.nested(BrokerCommissionContract.Relationships.BROKERING, Brokering.Relationships.BROKERED));
        return contract.getBrokering().getBrokered();
    }
}
