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
package nl.strohalm.cyclos.services.transfertypes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.model.TransferTypeVO;

/**
 * Security implementation for {@link TransferTypeService}
 * 
 * @author Rinke
 */
public class TransferTypeServiceSecurity extends BaseServiceSecurity implements TransferTypeService {

    private TransferTypeServiceLocal transferTypeService;

    @Override
    public List<TransferType> getAuthorizableTTs() {
        permissionService.permission().admin(AdminMemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS).check();
        return transferTypeService.getAuthorizableTTs();
    }

    @Override
    public List<TransferType> getPaymentAndSelfPaymentTTs() {
        permissionService.permission().admin(AdminMemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS).check();
        return transferTypeService.getPaymentAndSelfPaymentTTs();
    }

    @Override
    public List<TransferType> getPosibleTTsForAccountFee(final MemberAccountType accountType, final PaymentDirection paymentDirection) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        return transferTypeService.getPosibleTTsForAccountFee(accountType, paymentDirection);
    }

    @Override
    public TransferTypeVO getTransferTypeVO(final Long transferTypeId, final boolean extended) {
        if (transferTypeId == null) {
            return null;
        }
        return transferTypeService.getTransferTypeVO(transferTypeId, extended);
    }

    @Override
    public List<TransferType> listARatedTTs() {

        return transferTypeService.listARatedTTs();
    }

    @Override
    public TransferType load(final Long id, final Relationship... fetch) {
        TransferType tt = transferTypeService.load(id, fetch);

        if (!permissionService.hasPermission(AdminSystemPermission.ACCOUNTS_VIEW)) {
            if (!transferTypeService.getAllowedTTs(LoggedUser.element()).contains(tt)) {
                throw new PermissionDeniedException();
            }
        }
        return tt;
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_MANAGE)
                .check();

        return transferTypeService.remove(ids);
    }

    @Override
    public TransferType save(final TransferType transferType) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_MANAGE)
                .check();

        return transferTypeService.save(transferType);
    }

    @Override
    public List<TransferType> search(final TransferTypeQuery query) {
        AccountOwner fromOwner = query.getFromOwner();
        if (fromOwner instanceof Member) {
            // Needs to be relates to to support the posweb receive (we need the possible TTs for the other member to pay)
            permissionService.checkRelatesTo((Member) fromOwner);
        }
        AccountOwner toOwner = query.getToOwner();
        if (toOwner instanceof Member) {
            permissionService.checkRelatesTo((Member) toOwner);
        }
        AccountOwner fromOrToOwner = query.getFromOrToOwner();
        if (fromOrToOwner instanceof Member) {
            permissionService.checkRelatesTo((Member) fromOrToOwner);
        }

        if (!hasPermission(AdminSystemPermission.ACCOUNTS_VIEW)) {
            // When not able to view the accounts configuration, where all transfer types are visible, we need to restrict to the accessible only
            if (LoggedUser.isWebService()) {
                ServiceClient client = LoggedUser.serviceClient();
                Set<TransferType> possible = new HashSet<TransferType>();
                possible.addAll(client.getDoPaymentTypes());
                possible.addAll(client.getReceivePaymentTypes());
                possible.addAll(client.getChargebackPaymentTypes());
                query.setPossibleTransferTypes(possible);
            } else {
                Set<TransferType> possible = new HashSet<TransferType>();
                if (fromOwner instanceof Member) {
                    Member fromMember = (Member) fromOwner;
                    possible.addAll(transferTypeService.getAllowedTTs(fromMember));
                }
                if (LoggedUser.hasUser()) {
                    Element loggedElement = LoggedUser.element();
                    possible.addAll(transferTypeService.getAllowedTTs(loggedElement));
                }
                query.setPossibleTransferTypes(possible);
            }
        }
        return transferTypeService.search(query);
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final TransferType transferType) {
        transferTypeService.validate(transferType);
    }

}
