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
package nl.strohalm.cyclos.controls.loans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.loangroups.LoanGroupService;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.MapBean;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search loan payments
 * @author luis
 */
public class SearchLoanPaymentsAction extends BaseQueryAction {

    public static DataBinder<LoanPaymentQuery> loanPaymentQueryDataBinder(final LocalSettings localSettings) {
        final BeanBinder<MemberCustomFieldValue> memberCustomValueBinder = BeanBinder.instance(MemberCustomFieldValue.class);
        memberCustomValueBinder.registerBinder("field", PropertyBinder.instance(MemberCustomField.class, "field", ReferenceConverter.instance(MemberCustomField.class)));
        memberCustomValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value"));

        final BeanBinder<PaymentCustomFieldValue> loanCustomValueBinder = BeanBinder.instance(PaymentCustomFieldValue.class);
        loanCustomValueBinder.registerBinder("field", PropertyBinder.instance(PaymentCustomField.class, "field", ReferenceConverter.instance(PaymentCustomField.class)));
        loanCustomValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value"));

        final BeanBinder<LoanPaymentQuery> binder = BeanBinder.instance(LoanPaymentQuery.class);
        binder.registerBinder("statusList", SimpleCollectionBinder.instance(LoanPayment.Status.class, "statusList"));
        binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType"));
        binder.registerBinder("member", PropertyBinder.instance(Member.class, "member", ReferenceConverter.instance(Member.class)));
        binder.registerBinder("broker", PropertyBinder.instance(Member.class, "broker", ReferenceConverter.instance(Member.class)));
        binder.registerBinder("loanGroup", PropertyBinder.instance(LoanGroup.class, "loanGroup", ReferenceConverter.instance(LoanGroup.class)));
        binder.registerBinder("memberCustomValues", BeanCollectionBinder.instance(memberCustomValueBinder, "memberValues"));
        binder.registerBinder("loanCustomValues", BeanCollectionBinder.instance(loanCustomValueBinder, "loanValues"));
        binder.registerBinder("expirationPeriod", DataBinderHelper.periodBinder(localSettings, "expirationPeriod"));
        binder.registerBinder("repaymentPeriod", DataBinderHelper.periodBinder(localSettings, "repaymentPeriod"));
        binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
        return binder;
    }

    protected PaymentCustomFieldService  paymentCustomFieldService;
    protected MemberCustomFieldService   memberCustomFieldService;
    protected LoanService                loanService;
    protected TransferTypeService        transferTypeService;
    protected LoanGroupService           loanGroupService;

    private DataBinder<LoanPaymentQuery> dataBinder;

    private CustomFieldHelper            customFieldHelper;

    public DataBinder<LoanPaymentQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            dataBinder = loanPaymentQueryDataBinder(settings);
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public final void setLoanGroupService(final LoanGroupService loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    @Inject
    public final void setLoanService(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Inject
    public final void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final SearchLoanPaymentsForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final LoanPaymentQuery query = (LoanPaymentQuery) queryParameters;
        final List<LoanPayment> loanPayments = loanService.search(query);
        request.setAttribute("loanPayments", loanPayments);
        form.setQueryAlreadyExecuted(true);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final SearchLoanPaymentsForm form = context.getForm();
        final LoanPaymentQuery query = getDataBinder().readFromString(form.getQuery());
        query.fetch(RelationshipHelper.nested(LoanPayment.Relationships.LOAN, Loan.Relationships.TRANSFER, Payment.Relationships.CUSTOM_VALUES), RelationshipHelper.nested(LoanPayment.Relationships.LOAN, Loan.Relationships.TRANSFER, Payment.Relationships.TO, MemberAccount.Relationships.MEMBER, Element.Relationships.USER));

        // Just search loan payments of members in groups managed by admin group
        AdminGroup adminGroup = context.getGroup();
        adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
        query.setGroups(adminGroup.getManagesGroups());

        // Retrieve a list of all transfer types that are loans
        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setContext(TransactionContext.LOAN);
        ttQuery.setToGroups(adminGroup.getManagesGroups());
        final List<TransferType> transferTypes = transferTypeService.search(ttQuery);
        if (transferTypes.size() == 1) {
            // When there is a single transfer type, set it, so that the custom fields will be shown
            query.setTransferType(transferTypes.iterator().next());
            request.setAttribute("singleTransferType", query.getTransferType());
        }
        request.setAttribute("transferTypes", transferTypes);

        // Get the member custom fields
        final List<MemberCustomField> memberFields = customFieldHelper.onlyForLoanSearch(memberCustomFieldService.list());
        request.setAttribute("memberFieldValues", customFieldHelper.buildEntries(memberFields, query.getMemberCustomValues()));

        // Get the payment custom fields
        final TransferType transferType = query.getTransferType();
        form.setQuery("loanValues", new MapBean(true, "field", "value"));
        if (transferType == null) {
            // If setting no transfer type, don't filter by custom fields also
            query.setLoanCustomValues(null);
        } else {
            // Get the custom fields for search and for list
            final List<PaymentCustomField> allFields = paymentCustomFieldService.list(transferType, true);
            request.setAttribute("allFields", allFields);
            final List<PaymentCustomField> customFieldsForSearch = new ArrayList<PaymentCustomField>();
            final List<PaymentCustomField> customFieldsForList = new ArrayList<PaymentCustomField>();
            for (final PaymentCustomField customField : allFields) {
                if (customField.getSearchAccess() != PaymentCustomField.Access.NONE) {
                    customFieldsForSearch.add(customField);
                }
                if (customField.getListAccess() != PaymentCustomField.Access.NONE) {
                    customFieldsForList.add(customField);
                }
            }
            request.setAttribute("customFieldsForList", customFieldsForList);

            // Ensure the query has no custom values which are not visible
            final Collection<PaymentCustomFieldValue> loanCustomValues = query.getLoanCustomValues();
            if (loanCustomValues != null) {
                final Iterator<PaymentCustomFieldValue> iterator = loanCustomValues.iterator();
                while (iterator.hasNext()) {
                    final PaymentCustomFieldValue fieldValue = iterator.next();
                    if (!customFieldsForSearch.contains(fieldValue.getField())) {
                        iterator.remove();
                    }
                }
            }

            request.setAttribute("loanFieldValues", customFieldHelper.buildEntries(customFieldsForSearch, loanCustomValues));
        }

        RequestHelper.storeEnum(request, LoanPayment.Status.class, "status");

        if (permissionService.hasPermission(AdminSystemPermission.LOAN_GROUPS_VIEW)) {
            // Retrieve a list of all loan groups
            final LoanGroupQuery lgQuery = new LoanGroupQuery();
            request.setAttribute("loanGroups", loanGroupService.search(lgQuery));
        } else {
            request.setAttribute("loanGroups", Collections.emptyList());
        }

        if (query.getMember() != null) {
            query.setMember((Member) elementService.load(query.getMember().getId(), Element.Relationships.USER));
        }
        if (query.getBroker() != null) {
            query.setBroker((Member) elementService.load(query.getBroker().getId(), Element.Relationships.USER));
        }

        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        final SearchLoanPaymentsForm form = context.getForm();
        if (form.isQueryAlreadyExecuted()) {
            return true;
        }
        return super.willExecuteQuery(context, queryParameters);
    }
}
