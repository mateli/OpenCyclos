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
package nl.strohalm.cyclos.controls.accounts.transfertypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanParameters;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType.Context;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType.TransactionHierarchyVisibility;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.accounts.SystemAccountTypeQuery;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.services.transfertypes.exceptions.HasPendingPaymentsException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a transfer type
 * @author luis, Jefferson Magno
 */
public class EditTransferTypeAction extends BaseFormAction implements LocalSettingsChangeListener {

    private AccountTypeService        accountTypeService;

    private ChannelService            channelService;
    private TransferTypeService       transferTypeService;
    private TransactionFeeService     transactionFeeService;
    private PaymentCustomFieldService paymentCustomFieldService;
    private DataBinder<TransferType>  dataBinder;
    private ReadWriteLock             lock = new ReentrantReadWriteLock(true);

    public AccountTypeService getAccountTypeService() {
        return accountTypeService;
    }

    public DataBinder<TransferType> getDataBinder() {
        try {
            lock.readLock().lock();
            if (dataBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();

                final BeanBinder<Context> contextBinder = BeanBinder.instance(TransferType.Context.class, "context");
                contextBinder.registerBinder("payment", PropertyBinder.instance(Boolean.TYPE, "payment"));
                contextBinder.registerBinder("selfPayment", PropertyBinder.instance(Boolean.TYPE, "selfPayment"));

                final BeanBinder<LoanParameters> loanBinder = BeanBinder.instance(LoanParameters.class, "loan");
                loanBinder.registerBinder("type", PropertyBinder.instance(Loan.Type.class, "type"));
                loanBinder.registerBinder("repaymentDays", PropertyBinder.instance(Integer.class, "repaymentDays"));
                loanBinder.registerBinder("repaymentType", PropertyBinder.instance(TransferType.class, "repaymentType"));
                loanBinder.registerBinder("monthlyInterest", PropertyBinder.instance(BigDecimal.class, "monthlyInterest", localSettings.getNumberConverter()));
                loanBinder.registerBinder("monthlyInterestRepaymentType", PropertyBinder.instance(TransferType.class, "monthlyInterestRepaymentType"));
                loanBinder.registerBinder("grantFee", DataBinderHelper.amountConverter("grantFee", localSettings));
                loanBinder.registerBinder("grantFeeRepaymentType", PropertyBinder.instance(TransferType.class, "grantFeeRepaymentType"));
                loanBinder.registerBinder("expirationFee", DataBinderHelper.amountConverter("expirationFee", localSettings));
                loanBinder.registerBinder("expirationFeeRepaymentType", PropertyBinder.instance(TransferType.class, "expirationFeeRepaymentType"));
                loanBinder.registerBinder("expirationDailyInterest", PropertyBinder.instance(BigDecimal.class, "expirationDailyInterest", localSettings.getNumberConverter()));
                loanBinder.registerBinder("expirationDailyInterestRepaymentType", PropertyBinder.instance(TransferType.class, "expirationDailyInterestRepaymentType"));

                final BeanBinder<TransferType> binder = BeanBinder.instance(TransferType.class);
                binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
                binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
                binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
                binder.registerBinder("confirmationMessage", PropertyBinder.instance(String.class, "confirmationMessage"));
                binder.registerBinder("context", contextBinder);
                binder.registerBinder("channels", SimpleCollectionBinder.instance(Channel.class, "channels"));
                binder.registerBinder("priority", PropertyBinder.instance(Boolean.TYPE, "priority"));
                binder.registerBinder("from", PropertyBinder.instance(AccountType.class, "from"));
                binder.registerBinder("to", PropertyBinder.instance(AccountType.class, "to"));
                binder.registerBinder("maxAmountPerDay", PropertyBinder.instance(BigDecimal.class, "maxAmountPerDay", localSettings.getNumberConverter()));
                binder.registerBinder("minAmount", PropertyBinder.instance(BigDecimal.class, "minAmount", localSettings.getNumberConverter()));
                binder.registerBinder("conciliable", PropertyBinder.instance(Boolean.TYPE, "conciliable"));
                binder.registerBinder("loan", loanBinder);
                binder.registerBinder("requiresAuthorization", PropertyBinder.instance(Boolean.TYPE, "requiresAuthorization"));
                binder.registerBinder("allowsScheduledPayments", PropertyBinder.instance(Boolean.TYPE, "allowsScheduledPayments"));
                binder.registerBinder("requiresFeedback", PropertyBinder.instance(Boolean.TYPE, "requiresFeedback"));
                binder.registerBinder("feedbackExpirationTime", DataBinderHelper.timePeriodBinder("feedbackExpirationTime"));
                binder.registerBinder("feedbackReplyExpirationTime", DataBinderHelper.timePeriodBinder("feedbackReplyExpirationTime"));
                binder.registerBinder("defaultFeedbackComments", PropertyBinder.instance(String.class, "defaultFeedbackComments"));
                binder.registerBinder("defaultFeedbackLevel", PropertyBinder.instance(Reference.Level.class, "defaultFeedbackLevel"));
                binder.registerBinder("fixedDestinationMember", PropertyBinder.instance(Member.class, "fixedDestinationMember"));
                binder.registerBinder("reserveTotalAmountOnScheduling", PropertyBinder.instance(Boolean.TYPE, "reserveTotalAmountOnScheduling"));
                binder.registerBinder("allowCancelScheduledPayments", PropertyBinder.instance(Boolean.TYPE, "allowCancelScheduledPayments"));
                binder.registerBinder("allowBlockScheduledPayments", PropertyBinder.instance(Boolean.TYPE, "allowBlockScheduledPayments"));
                binder.registerBinder("showScheduledPaymentsToDestination", PropertyBinder.instance(Boolean.TYPE, "showScheduledPaymentsToDestination"));
                binder.registerBinder("allowSmsNotification", PropertyBinder.instance(Boolean.TYPE, "allowSmsNotification"));
                binder.registerBinder("transferListenerClass", PropertyBinder.instance(String.class, "transferListenerClass"));
                binder.registerBinder("transactionHierarchyVisibility", PropertyBinder.instance(TransactionHierarchyVisibility.class, "transactionHierarchyVisibility"));
                dataBinder = binder;
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    public TransferTypeService getTransferTypeService() {
        return transferTypeService;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            dataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditTransferTypeForm form = context.getForm();
        TransferType transferType = retrieveTransferType(form);
        final boolean isInsert = transferType.getId() == null;
        try {
            transferType = transferTypeService.save(transferType);
        } catch (final HasPendingPaymentsException e) {
            return context.sendError("transferType.error.hasPendingPayments");
        }
        context.sendMessage(isInsert ? "transferType.inserted" : "transferType.modified");
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountTypeId", form.getAccountTypeId());
        params.put("transferTypeId", transferType.getId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditTransferTypeForm form = context.getForm();
        final long accountTypeId = form.getAccountTypeId();
        if (accountTypeId <= 0L) {
            throw new ValidationException();
        }
        final AccountType accountType = accountTypeService.load(accountTypeId);

        final long transferTypeId = form.getTransferTypeId();
        final boolean isInsert = transferTypeId <= 0L;
        TransferType transferType;
        if (isInsert) {
            transferType = new TransferType();
            transferType.setFrom(accountType);
            transferType.setDefaultFeedbackLevel(Reference.Level.NEUTRAL);

            // Preselect the web channel
            final Channel web = channelService.loadByInternalName(Channel.WEB);
            transferType.setChannels(Collections.singleton(web));

            final List<AccountType> accountTypes = new ArrayList<AccountType>();

            // Search system account types
            final SystemAccountTypeQuery systemAccountTypeQuery = new SystemAccountTypeQuery();
            systemAccountTypeQuery.setCurrency(accountType.getCurrency());
            accountTypes.addAll(accountTypeService.search(systemAccountTypeQuery));

            // Search member account types
            final MemberAccountTypeQuery memberAccountTypeQuery = new MemberAccountTypeQuery();
            memberAccountTypeQuery.setCurrency(accountType.getCurrency());
            accountTypes.addAll(accountTypeService.search(memberAccountTypeQuery));

            // System account types cannot have transfer types to themselves
            if (accountType instanceof SystemAccountType) {
                accountTypes.remove(accountType);
            }
            request.setAttribute("accountTypes", accountTypes);
        } else {
            transferType = transferTypeService.load(transferTypeId, TransferType.Relationships.FROM, TransferType.Relationships.TO, TransferType.Relationships.AUTHORIZATION_LEVELS, TransferType.Relationships.CUSTOM_FIELDS);
            processAuthorizationLevels(request, transferType);
            final GroupQuery query = new GroupQuery();
            query.setNatures(Group.Nature.ADMIN);
            query.setStatus(Group.Status.NORMAL);
            final Collection<AdminGroup> adminGroups = (Collection<AdminGroup>) groupService.search(query);
            request.setAttribute("adminGroups", adminGroups);

            // Get the associated simple transaction fees
            final TransactionFeeQuery simpleQuery = new TransactionFeeQuery();
            final Set<Relationship> fetch = new HashSet<Relationship>();
            fetch.add(RelationshipHelper.nested(TransactionFee.Relationships.GENERATED_TRANSFER_TYPE, TransferType.Relationships.FROM));
            simpleQuery.setFetch(fetch);
            simpleQuery.setNature(TransactionFee.Nature.SIMPLE);
            simpleQuery.setTransferType(transferType);
            simpleQuery.setReturnDisabled(true);
            request.setAttribute("simpleTransactionFees", transactionFeeService.search(simpleQuery));

            if (transferType.isFromMember()) {
                // Get the associated broker commissions
                final TransactionFeeQuery brokerQuery = new TransactionFeeQuery();
                brokerQuery.setNature(TransactionFee.Nature.BROKER);
                brokerQuery.setTransferType(transferType);
                brokerQuery.setReturnDisabled(true);
                request.setAttribute("brokerCommissions", transactionFeeService.search(brokerQuery));
            }

            // Get the custom fields
            final List<PaymentCustomField> customFields = paymentCustomFieldService.list(transferType, true);
            request.setAttribute("customFields", customFields);

            // Get the account types for linked fields
            final List<? extends AccountType> linkedFieldAccountTypes = accountTypeService.listAll();
            request.setAttribute("linkedFieldAccountTypes", linkedFieldAccountTypes);
        }
        request.setAttribute("transferType", transferType);

        // Store the loan repayment transfer types (always from member to system)
        final TransferTypeQuery toSystemRepaymentQuery = new TransferTypeQuery();
        toSystemRepaymentQuery.setContext(TransactionContext.AUTOMATIC);
        toSystemRepaymentQuery.setCurrency(transferType.getCurrency());
        toSystemRepaymentQuery.setFromNature(AccountType.Nature.MEMBER);
        toSystemRepaymentQuery.setToNature(AccountType.Nature.SYSTEM);
        request.setAttribute("loanRepaymentTypes", transferTypeService.search(toSystemRepaymentQuery));

        getDataBinder().writeAsString(form.getTransferType(), transferType);
        final Context ttContext = transferType.getContext();
        form.setTransferType("enabled", String.valueOf(ttContext.isPayment() || ttContext.isSelfPayment()));
        request.setAttribute("accountType", accountType);
        request.setAttribute("isSystemAccount", accountType instanceof SystemAccountType);
        request.setAttribute("isInsert", isInsert);
        RequestHelper.storeEnum(request, Loan.Type.class, "loanTypes");
        RequestHelper.storeEnum(request, Amount.Type.class, "amountTypes");
        RequestHelper.storeEnum(request, AuthorizationLevel.Authorizer.class, "authorizers");
        RequestHelper.storeEnum(request, TransactionHierarchyVisibility.class, "transactionHierarchyVisibilities");
        request.setAttribute("feedbackTimeFields", Arrays.asList(Field.DAYS, Field.WEEKS, Field.MONTHS));
        request.setAttribute("channels", channelService.list());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditTransferTypeForm form = context.getForm();
        final TransferType transferType = retrieveTransferType(form);
        transferTypeService.validate(transferType);
    }

    private void processAuthorizationLevels(final HttpServletRequest request, final TransferType transferType) {
        if (transferType.isRequiresAuthorization()) {
            final Collection<AuthorizationLevel> rawAuthorizationLevels = transferType.getAuthorizationLevels();
            final LinkedList<AuthorizationLevel> authorizationLevels = new LinkedList<AuthorizationLevel>(rawAuthorizationLevels);
            request.setAttribute("authorizationLevels", authorizationLevels);
            boolean insertNewLevel = false;
            Collection<AuthorizationLevel.Authorizer> possibleAuthorizers = null;
            if (CollectionUtils.isEmpty(authorizationLevels)) {
                insertNewLevel = true;
                if (transferType.isFromSystem() && transferType.isToSystem()) {
                    possibleAuthorizers = Arrays.asList(AuthorizationLevel.Authorizer.ADMIN);
                } else if (transferType.isFromSystem() && transferType.isToMember()) {
                    possibleAuthorizers = Arrays.asList(AuthorizationLevel.Authorizer.ADMIN, AuthorizationLevel.Authorizer.RECEIVER);
                } else if (transferType.isToSystem()) {
                    possibleAuthorizers = Arrays.asList(AuthorizationLevel.Authorizer.BROKER, AuthorizationLevel.Authorizer.ADMIN);
                } else {
                    possibleAuthorizers = Arrays.asList(AuthorizationLevel.Authorizer.RECEIVER, AuthorizationLevel.Authorizer.BROKER, AuthorizationLevel.Authorizer.ADMIN);
                }
            } else {
                RequestHelper.storeEnum(request, AuthorizationLevel.Authorizer.class, "authorizers");
                final AuthorizationLevel highestAuthorizationLevel = authorizationLevels.getLast();
                if ((highestAuthorizationLevel.getAuthorizer() == AuthorizationLevel.Authorizer.RECEIVER)) {
                    if (transferType.isFromSystem()) {
                        possibleAuthorizers = Arrays.asList(AuthorizationLevel.Authorizer.ADMIN, AuthorizationLevel.Authorizer.RECEIVER);
                    } else {
                        possibleAuthorizers = Arrays.asList(AuthorizationLevel.Authorizer.PAYER, AuthorizationLevel.Authorizer.BROKER, AuthorizationLevel.Authorizer.ADMIN);
                    }
                } else {
                    possibleAuthorizers = Arrays.asList(AuthorizationLevel.Authorizer.ADMIN);
                }
                final Integer highestLevel = highestAuthorizationLevel.getLevel();
                insertNewLevel = (highestLevel < AuthorizationLevel.MAX_LEVELS);
            }
            request.setAttribute("insertNewLevel", insertNewLevel);
            if (insertNewLevel) {
                request.setAttribute("possibleAuthorizers", possibleAuthorizers);
            }
        }
    }

    private TransferType retrieveTransferType(final EditTransferTypeForm form) {
        final TransferType transferType = getDataBinder().readFromString(form.getTransferType());
        transferType.setFrom(accountTypeService.load(transferType.getFrom().getId()));
        transferType.setTo(accountTypeService.load(transferType.getTo().getId()));
        final Context context = transferType.getContext();
        if (transferType.isFromSystem() || transferType.isToSystem() || transferType.getFrom().equals(transferType.getTo())) {
            final boolean enabled = "true".equals(form.getTransferType("enabled"));
            final boolean selfPayment = enabled && transferType.isFromSystem() && transferType.isToSystem();
            final boolean payment = enabled && !selfPayment;
            context.setSelfPayment(selfPayment);
            context.setPayment(payment);
        }
        if (transferType.isFromMember() && context.isSelfPayment()) {
            transferType.setChannels(null);
        }
        return transferType;
    }

}
