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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.accounts.AccountTypeServiceLocal;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.webservices.model.PaymentFilterVO;

/**
 * Security implementation for {@link PaymentFilterService}
 * 
 * @author jcomas
 */
public class PaymentFilterServiceSecurity extends BaseServiceSecurity implements PaymentFilterService {

    private PaymentFilterServiceLocal paymentFilterService;
    private AccountTypeServiceLocal   accountTypeService;

    @Override
    public List<PaymentFilterVO> getPaymentFilterVOs(final List<PaymentFilter> paymentFilters) {
        if (paymentFilters == null) {
            return Collections.emptyList();
        }
        for (PaymentFilter paymentFilter : paymentFilters) {
            checkView(paymentFilter);
        }
        return paymentFilterService.getPaymentFilterVOs(paymentFilters);
    }

    @Override
    public Collection<PaymentFilter> load(final Collection<Long> ids, final Relationship... fetch) {
        Collection<PaymentFilter> paymentFilters = paymentFilterService.load(ids);
        checkView(paymentFilters.toArray(new PaymentFilter[paymentFilters.size()]));
        return paymentFilters;
    }

    @Override
    public PaymentFilter load(final Long id, final Relationship... fetch) {
        PaymentFilter paymentFilter = paymentFilterService.load(id, fetch);
        checkView(paymentFilter);
        return paymentFilter;
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        return paymentFilterService.remove(ids);
    }

    @Override
    public PaymentFilter save(final PaymentFilter paymentFilter) {
        permissionService.permission().admin(AdminSystemPermission.ACCOUNTS_MANAGE).check();
        return paymentFilterService.save(paymentFilter);
    }

    @Override
    public List<PaymentFilter> search(final PaymentFilterQuery query) {
        if (!applyQueryRestrictions(query)) {
            return Collections.emptyList();
        }
        return paymentFilterService.search(query);
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setPaymentFilterServiceLocal(final PaymentFilterServiceLocal paymentFilterService) {
        this.paymentFilterService = paymentFilterService;
    }

    @Override
    public void validate(final PaymentFilter paymentFilter) {
        // Nothing to check.
        paymentFilterService.validate(paymentFilter);
    }

    private boolean applyQueryRestrictions(final PaymentFilterQuery query) {
        // Ensure the account types are visible.
        Collection<AccountType> accountTypes = PermissionHelper.checkSelection(accountTypeService.getVisibleAccountTypes(), query.getAccountTypes());
        query.setAccountTypes(accountTypes);
        return accountTypes != null;
    }

    private void checkView(final PaymentFilter... paymentFilters) {
        // Check that the payment filters being retrieved have a visible account type.
        Collection<AccountType> visibleAccountTypes = accountTypeService.getVisibleAccountTypes();
        for (PaymentFilter pf : paymentFilters) {
            if (!visibleAccountTypes.contains(pf.getAccountType())) {
                throw new PermissionDeniedException();
            }
        }
    }

}
