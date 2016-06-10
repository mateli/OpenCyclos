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
package nl.strohalm.cyclos.services.accountfees;

import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.RunMode;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogDetailsDTO;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLogQuery;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeQuery;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLogQuery;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

/**
 * Security implementation for {@link AccountFeeService}
 * 
 * @author Luis
 */
public class AccountFeeServiceSecurity extends BaseServiceSecurity implements AccountFeeService {

    private AccountFeeServiceLocal   accountFeeService;
    private TransferTypeServiceLocal transferTypeService;

    @Override
    public void chargeManual(final AccountFee accountFee) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNT_FEES_CHARGE).check();
        accountFeeService.chargeManual(accountFee);
    }

    @Override
    public AccountFeeLogDetailsDTO getLogDetails(final Long id) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNT_FEES_VIEW).check();
        return accountFeeService.getLogDetails(id);
    }

    @Override
    public AccountFee load(final Long id, final Relationship... fetch) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_VIEW, AdminSystemPermission.ACCOUNT_FEES_VIEW).check();
        return accountFeeService.load(id, fetch);
    }

    @Override
    public AccountFeeLog loadLog(final Long id, final Relationship... fetch) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNT_FEES_VIEW).check();
        return accountFeeService.loadLog(id, fetch);
    }

    @Override
    public void rechargeFailed(final AccountFeeLog accountFeeLog) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNT_FEES_CHARGE).check();
        accountFeeService.rechargeFailed(accountFeeLog);
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        return accountFeeService.remove(ids);
    }

    @Override
    public AccountFee save(final AccountFee accountFee) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        checkReadOnlyValues(accountFee);
        checkGeneratedTransferType(accountFee);
        return accountFeeService.save(accountFee);
    }

    @Override
    public List<AccountFee> search(final AccountFeeQuery query) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_VIEW, AdminSystemPermission.ACCOUNT_FEES_VIEW).check();
        return accountFeeService.search(query);
    }

    @Override
    public List<AccountFeeLog> searchLogs(final AccountFeeLogQuery query) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNT_FEES_VIEW).check();
        return accountFeeService.searchLogs(query);
    }

    @Override
    public List<MemberAccountFeeLog> searchMembers(final MemberAccountFeeLogQuery query) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNT_FEES_VIEW).check();
        PermissionHelper.checkSelection(permissionService.getManagedMemberGroups(), query.getGroups());
        return accountFeeService.searchMembers(query);
    }

    public void setAccountFeeServiceLocal(final AccountFeeServiceLocal accountFeeService) {
        this.accountFeeService = accountFeeService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final AccountFee accountFee) {
        // No permission check is needed on validate
        accountFeeService.validate(accountFee);
    }

    private void checkGeneratedTransferType(final AccountFee accountFee) {
        MemberAccountType accountType = accountFee.getAccountType();
        PaymentDirection paymentDirection = accountFee.getPaymentDirection();
        PermissionHelper.checkContains(transferTypeService.getPosibleTTsForAccountFee(accountType, paymentDirection), accountFee.getTransferType());
    }

    private void checkReadOnlyValues(final AccountFee modifiedAF) {
        if (!modifiedAF.isTransient()) {
            AccountFee savedAF = load(modifiedAF.getId());
            // Charged mode
            PermissionHelper.checkEquals(savedAF.getChargeMode(), modifiedAF.getChargeMode());
            // Run mode
            PermissionHelper.checkEquals(savedAF.getRunMode(), modifiedAF.getRunMode());
            // Recurrence
            if (savedAF.getRunMode().equals(RunMode.SCHEDULED)) {
                PermissionHelper.checkEquals(savedAF.getRecurrence(), modifiedAF.getRecurrence());
            }
            // Account type id
            if (modifiedAF.getAccountType() != null) {
                PermissionHelper.checkEquals(savedAF.getAccountType(), modifiedAF.getAccountType());
            }

        }
    }

}
