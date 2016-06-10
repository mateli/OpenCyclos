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

import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransfersAwaitingAuthorizationQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.transactions.exceptions.AlreadyAuthorizedException;
import nl.strohalm.cyclos.utils.access.LoggedUser;

/**
 * Security layer for {@link TransferAuthorizationService}
 * 
 * @author luis
 */
public class TransferAuthorizationServiceSecurity extends BaseServiceSecurity implements TransferAuthorizationService {

    private TransferAuthorizationServiceLocal transferAuthorizationService;

    @Override
    public Transfer authorize(final TransferAuthorizationDTO dto) throws AlreadyAuthorizedException, EntityNotFoundException, UnexpectedEntityException {
        if (!transferAuthorizationService.canAuthorizeOrDeny(dto.getTransfer())) {
            throw new PermissionDeniedException();
        }
        return transferAuthorizationService.authorize(dto);
    }

    @Override
    public Transfer cancel(final TransferAuthorizationDTO dto) throws EntityNotFoundException, UnexpectedEntityException {
        if (!transferAuthorizationService.canCancel(dto.getTransfer())) {
            throw new PermissionDeniedException();
        }
        return transferAuthorizationService.cancel(dto);
    }

    @Override
    public Transfer deny(final TransferAuthorizationDTO dto) throws EntityNotFoundException, UnexpectedEntityException {
        if (!transferAuthorizationService.canAuthorizeOrDeny(dto.getTransfer())) {
            throw new PermissionDeniedException();
        }
        return transferAuthorizationService.deny(dto);
    }

    @Override
    public boolean hasAlreadyAuthorized(final Transfer transfer) {
        // No permission check needed, as if the logged user cannot view the given transfer, will just return false
        return transferAuthorizationService.hasAlreadyAuthorized(transfer);
    }

    @Override
    public List<TransferAuthorization> searchAuthorizations(final TransferAuthorizationQuery query) {
        checkViewAuthorized();
        Element by = fetchService.fetch(query.getBy());
        if ((by instanceof Administrator) && !LoggedUser.isAdministrator()) {
            // Cannot check a manages with other admin, or the logged admin would need to manage other admins in order to view authorizations
            throw new PermissionDeniedException();
        } else if (by != null) {
            // Ensure the given user is managed by the logged user
            permissionService.checkManages(by);
        }
        Member member = query.getMember();
        if (member != null) {
            permissionService.checkRelatesTo(member);
        }
        return transferAuthorizationService.searchAuthorizations(query);
    }

    @Override
    public List<Transfer> searchTransfersAwaitingAuthorization(final TransfersAwaitingAuthorizationQuery query) {
        checkViewAuthorized();
        Member member = query.getMember();
        if (member != null) {
            permissionService.checkRelatesTo(member);
        }
        return transferAuthorizationService.searchTransfersAwaitingAuthorization(query);
    }

    public void setTransferAuthorizationServiceLocal(final TransferAuthorizationServiceLocal transferAuthorizationService) {
        this.transferAuthorizationService = transferAuthorizationService;
    }

    private void checkViewAuthorized() {
        permissionService.permission()
                .admin(AdminSystemPermission.PAYMENTS_AUTHORIZE, AdminMemberPermission.PAYMENTS_AUTHORIZE)
                .broker(BrokerPermission.MEMBER_PAYMENTS_AUTHORIZE)
                .member(MemberPermission.PAYMENTS_AUTHORIZE)
                .operator(OperatorPermission.PAYMENTS_AUTHORIZE)
                .check();
    }
}
