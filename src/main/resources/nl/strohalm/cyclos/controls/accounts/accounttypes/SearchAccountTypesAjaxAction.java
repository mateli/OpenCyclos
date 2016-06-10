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
package nl.strohalm.cyclos.controls.accounts.accounttypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.AccountOwnerConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.PageHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

/**
 * Action for searching member account types using Ajax
 * @author luis
 */
public class SearchAccountTypesAjaxAction extends BaseAjaxAction {

    /**
     * Transforms account types in maps containing, normal properties plus a flag indicating whether there's at least 1 transfer type that allows
     * scheduling
     */
    private class AccountTypesWithScheduling implements Transformer {
        private AccountOwner from;
        private AccountOwner to;

        public AccountTypesWithScheduling(final AccountOwner from, final AccountOwner to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public Object transform(final Object at) {
            final AccountType accountType = (AccountType) at;
            final Map<String, Object> map = new HashMap<String, Object>();

            final TransferTypeQuery ttQuery = new TransferTypeQuery();
            ttQuery.setPageForCount();
            ttQuery.setContext(TransactionContext.PAYMENT);
            ttQuery.setFromOwner(from);
            ttQuery.setToOwner(to);
            ttQuery.setToAccountType(accountType);
            ttQuery.setSchedulable(true);
            if (from instanceof Member) {
                final Member member = elementService.load(((Member) from).getId(), Element.Relationships.GROUP);
                ttQuery.setGroup(member.getGroup());
            }
            final int count = PageHelper.getTotalCount(transferTypeService.search(ttQuery));

            map.put("id", accountType.getId());
            map.put("name", accountType.getName());
            map.put("currency", accountType.getCurrency());
            map.put("allowsScheduledPayments", count > 0);

            return map;
        }
    }

    private DataBinder<MemberAccountTypeQuery>        queryBinder;
    private BeanCollectionBinder<Map<String, Object>> accountTypeBinder;
    private BeanCollectionBinder<Map<String, Object>> accountTypeBinderWithScheduling;
    private AccountTypeService                        accountTypeService;
    private TransferTypeService                       transferTypeService;

    public BeanCollectionBinder<Map<String, Object>> getAccountTypeBinder() {
        if (accountTypeBinder == null) {
            accountTypeBinder = BeanCollectionBinder.instance(DataBinderHelper.accountTypeBinder());
        }
        return accountTypeBinder;
    }

    public BeanCollectionBinder<Map<String, Object>> getAccountTypeBinderWithScheduling() {
        if (accountTypeBinderWithScheduling == null) {
            final BeanBinder<Map<String, Object>> binder = DataBinderHelper.accountTypeBinder();
            binder.registerBinder("allowsScheduledPayments", PropertyBinder.instance(Boolean.TYPE, "allowsScheduledPayments"));
            accountTypeBinderWithScheduling = BeanCollectionBinder.instance(binder);
        }
        return accountTypeBinderWithScheduling;
    }

    public DataBinder<MemberAccountTypeQuery> getQueryBinder() {
        if (queryBinder == null) {
            final BeanBinder<MemberAccountTypeQuery> binder = BeanBinder.instance(MemberAccountTypeQuery.class);
            binder.registerBinder("owner", PropertyBinder.instance(Member.class, "ownerId", ReferenceConverter.instance(Member.class)));
            binder.registerBinder("canPay", PropertyBinder.instance(AccountOwner.class, "canPayOwnerId", AccountOwnerConverter.zeroIsSystemInstance()));
            binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currencyId"));
            binder.registerBinder("relatedToGroups", SimpleCollectionBinder.instance(MemberGroup.class, "memberGroupId"));
            queryBinder = binder;
        }
        return queryBinder;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void renderContent(final ActionContext context) throws Exception {
        final SearchAccountTypesAjaxForm form = context.getForm();
        final MemberAccountTypeQuery query = getQueryBinder().readFromString(form);
        final List<MemberAccountType> accountTypes = (List<MemberAccountType>) accountTypeService.search(query);
        DataBinder<?> collectionBinder;
        if (form.isScheduling()) {
            final AccountTypesWithScheduling transformer = new AccountTypesWithScheduling(query.getCanPay(), query.getOwner());
            CollectionUtils.transform(accountTypes, transformer);
            collectionBinder = getAccountTypeBinderWithScheduling();
        } else {
            collectionBinder = getAccountTypeBinder();
        }
        final String json = collectionBinder.readAsString(accountTypes);
        responseHelper.writeJSON(context.getResponse(), json);
    }
}
