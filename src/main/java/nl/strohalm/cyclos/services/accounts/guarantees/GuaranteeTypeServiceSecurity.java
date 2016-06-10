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

import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeTypeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link GuaranteeTypeService}
 */
public class GuaranteeTypeServiceSecurity extends BaseServiceSecurity implements GuaranteeTypeService {

    private GuaranteeTypeServiceLocal guaranteeTypeService;
    private TransferTypeServiceLocal  transferTypeService;
    private GuaranteeServiceLocal     guaranteeService;

    @Override
    public GuaranteeType load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        // this method is used to calculate the guarantee's fees and to search guarantee by GT (as issuer or admin)
        if (!guaranteeService.isIssuer()) {
            permissionService.permission()
                    .admin(AdminSystemPermission.GUARANTEE_TYPES_VIEW, AdminMemberPermission.GUARANTEES_REGISTER_GUARANTEES, AdminMemberPermission.GUARANTEES_VIEW_GUARANTEES)
                    .check();
        }

        return guaranteeTypeService.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission()
                .admin(AdminSystemPermission.GUARANTEE_TYPES_MANAGE)
                .check();

        return guaranteeTypeService.remove(ids);
    }

    @Override
    public GuaranteeType save(final GuaranteeType guaranteeType) {
        permissionService.permission()
                .admin(AdminSystemPermission.GUARANTEE_TYPES_MANAGE)
                .check();
        checkReadOnlyValues(guaranteeType);
        checkTransferTypes(guaranteeType);
        return guaranteeTypeService.save(guaranteeType);
    }

    @Override
    public List<GuaranteeType> search(final GuaranteeTypeQuery guaranteeTypeQuery) {
        permissionService.permission()
                .admin(AdminSystemPermission.GUARANTEE_TYPES_VIEW, AdminMemberPermission.GUARANTEES_VIEW_GUARANTEES)
                .check();

        return guaranteeTypeService.search(guaranteeTypeQuery);
    }

    public void setGuaranteeServiceLocal(final GuaranteeServiceLocal guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    public void setGuaranteeTypeServiceLocal(final GuaranteeTypeServiceLocal guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final GuaranteeType guaranteeType) throws ValidationException {
        guaranteeTypeService.validate(guaranteeType);
    }

    private void checkReadOnlyValues(final GuaranteeType modifiedGT) {
        if (!modifiedGT.isTransient()) {
            GuaranteeType savedGT = guaranteeTypeService.load(modifiedGT.getId());
            PermissionHelper.checkEquals(savedGT.getCurrency(), modifiedGT.getCurrency());
            PermissionHelper.checkEquals(savedGT.getModel(), modifiedGT.getModel());
        }
    }

    private void checkTransferTypes(final GuaranteeType guaranteeType) {
        List<TransferType> allowedTTs;

        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setCurrency(guaranteeType.getCurrency());

        // Credit fee TT query
        if (guaranteeType.getCreditFeeTransferType() != null) {
            ttQuery.setContext(TransactionContext.ANY);
            ttQuery.setFromNature(AccountType.Nature.MEMBER);
            ttQuery.setToNature(AccountType.Nature.SYSTEM);
            allowedTTs = transferTypeService.search(ttQuery);
            PermissionHelper.checkContains(allowedTTs, guaranteeType.getCreditFeeTransferType());
        }

        // Issue fee TT query
        if (guaranteeType.getIssueFeeTransferType() != null) {
            ttQuery.setContext(TransactionContext.ANY);
            ttQuery.setFromNature(AccountType.Nature.MEMBER);
            ttQuery.setToNature(AccountType.Nature.MEMBER);
            allowedTTs = transferTypeService.search(ttQuery);
            PermissionHelper.checkContains(allowedTTs, guaranteeType.getIssueFeeTransferType());
        }

        // Forward TT query
        if (guaranteeType.getForwardTransferType() != null) {
            ttQuery.setContext(TransactionContext.ANY);
            ttQuery.setFromNature(AccountType.Nature.MEMBER);
            ttQuery.setToNature(AccountType.Nature.MEMBER);
            allowedTTs = transferTypeService.search(ttQuery);
            PermissionHelper.checkContains(allowedTTs, guaranteeType.getForwardTransferType());
        }

        // Loan TT query
        if (guaranteeType.getLoanTransferType() != null) {
            ttQuery.setContext(TransactionContext.AUTOMATIC_LOAN);
            ttQuery.setFromNature(AccountType.Nature.SYSTEM);
            ttQuery.setToNature(AccountType.Nature.MEMBER);
            allowedTTs = transferTypeService.search(ttQuery);
            PermissionHelper.checkContains(allowedTTs, guaranteeType.getLoanTransferType());
        }
    }
}
