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
package nl.strohalm.cyclos.services.accounts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.AccountTypeQuery;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

import org.apache.commons.beanutils.BeanComparator;

/**
 * Security implementation for {@link AccountTypeService}
 * 
 * @author Luis
 */
public class AccountTypeServiceSecurity extends BaseServiceSecurity implements AccountTypeService {

    private AccountTypeServiceLocal accountTypeService;

    @Override
    public MemberAccountType getDefault(final MemberGroup group, final Relationship... fetch) {
        PermissionHelper.checkContains(permissionService.getVisibleMemberGroups(), group);
        return accountTypeService.getDefault(group, fetch);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<? extends AccountType> listAll() {
        // Listing all is actually listing all visible types
        List<AccountType> accountTypes = new ArrayList<AccountType>();
        accountTypes.addAll(accountTypeService.getVisibleAccountTypes());
        Collections.sort(accountTypes, new BeanComparator("name"));
        return accountTypes;
    }

    @Override
    public Collection<AccountType> load(final Collection<Long> ids) {
        Collection<AccountType> accountTypes = accountTypeService.load(ids);
        for (AccountType accountType : accountTypes) {
            checkVisible(accountType);
        }
        return accountTypes;
    }

    @Override
    public AccountType load(final Long id) {
        AccountType accountType = accountTypeService.load(id);
        checkVisible(accountType);
        return accountType;
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        return accountTypeService.remove(ids);
    }

    @Override
    public <AT extends AccountType> AT save(final AT accountType) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        return accountTypeService.save(accountType);
    }

    @Override
    public List<? extends AccountType> search(final AccountTypeQuery query) {
        List<? extends AccountType> accountTypes = accountTypeService.search(query);
        accountTypes.retainAll(accountTypeService.getVisibleAccountTypes());
        return accountTypes;
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Override
    public void validate(final AccountType accountType) {
        // No permissions needed for validation
        accountTypeService.validate(accountType);
    }

    private boolean checkVisible(final AccountType accountType) {
        if (permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_VIEW).hasPermission()) {
            return true;
        }
        if (accountType instanceof SystemAccountType) {
            // A system account can only be visible by admins with specific permissions
            AdminGroup group = LoggedUser.group();
            Collection<SystemAccountType> systemTypes = fetchService.fetch(group, AdminGroup.Relationships.VIEW_INFORMATION_OF).getViewInformationOf();
            return systemTypes.contains(accountType);
        } else {
            Collection<MemberGroup> groups = permissionService.getManagedMemberGroups();
            // Check for all associations with groups, to see if the account is visible
            MemberAccountType memberType = (MemberAccountType) fetchService.fetch(accountType, MemberAccountType.Relationships.SETTINGS);
            for (MemberGroupAccountSettings settings : memberType.getSettings()) {
                if (groups.contains(settings.getGroup())) {
                    return true;
                }
            }
            return false;
        }
    }
}
