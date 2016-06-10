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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.dao.accounts.transactions.AuthorizationLevelDAO;
import nl.strohalm.cyclos.dao.accounts.transactions.TransferTypeDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.AccountType.Nature;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanParameters;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferListener;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType.Context;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.exceptions.HasPendingPaymentsException;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PositiveNonZeroValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.webservices.model.TransferTypeVO;
import nl.strohalm.cyclos.webservices.utils.AccountHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation class for transfer types
 * @author rafael
 * @author Rinke (conversion methods)
 */
public class TransferTypeServiceImpl implements TransferTypeServiceLocal {

    private final class DestinationAccountTypeValidator implements PropertyValidation {

        private static final long serialVersionUID = -1068050406929695757L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final TransferType transferType = (TransferType) object;

            // Get source and destination account types
            final AccountType from = fetchService.fetch(transferType.getFrom(), AccountType.Relationships.CURRENCY);
            AccountType to = (AccountType) value;

            // Validate if the currency of the source account type is the same currency of the destination account type
            final Currency sourceAccountTypeCurrency = from.getCurrency();
            to = fetchService.fetch(to, AccountType.Relationships.CURRENCY);
            final Currency destinationAccountTypeCurrency = to.getCurrency();
            if (!sourceAccountTypeCurrency.equals(destinationAccountTypeCurrency)) {
                return new ValidationError("transferType.error.invalidDestinationType");
            }

            if (from != null && to != null) {
                if (from.equals(to) && from.getNature() == AccountType.Nature.SYSTEM) {
                    // Cannot be from and to the same account if system
                    return new InvalidError();
                }
                if (transferType.isLoanType() && ((from.getNature() == AccountType.Nature.MEMBER) || (to.getNature() == AccountType.Nature.SYSTEM))) {
                    // When is a loan, can only be from system to member
                    return new InvalidError();
                }
            }

            return null;
        }
    }

    /**
     * A property validator that is only used when transaction feedback is required
     * @author luis
     */
    private class FeedbackValidator implements PropertyValidation {

        private static final long        serialVersionUID = 2435741054912450932L;
        private final PropertyValidation validation;

        public FeedbackValidator(final PropertyValidation validation) {
            this.validation = validation;
        }

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final TransferType tt = (TransferType) object;
            if (tt.isRequiresFeedback()) {
                return validation.validate(object, property, value);
            }
            return null;
        }
    }

    /**
     * Validates a repayment type to be required if it's associated component is present
     * @author luis
     */
    private final class LoanWithInterestRepaymentTypeValidator implements PropertyValidation {
        private static final long serialVersionUID = -3441031471188004677L;
        private final String      property;
        private final boolean     toSystem;

        public LoanWithInterestRepaymentTypeValidator(final String property, final boolean toSystem) {
            this.property = property;
            this.toSystem = toSystem;
        }

        @Override
        public ValidationError validate(final Object object, final Object name, final Object value) {
            final LoanParameters loan = (LoanParameters) object;
            final TransferType repayment = fetchService.fetch((TransferType) value, TransferType.Relationships.FROM, TransferType.Relationships.TO);
            ValidationError error = null;
            if (loan.getType() == Loan.Type.WITH_INTEREST) {
                final Object related = PropertyHelper.get(loan, property);
                boolean required = false;
                if (related instanceof Amount) {
                    final Amount amount = ((Amount) related);
                    required = amount != null && amount.getValue() != null && amount.getValue().compareTo(BigDecimal.ZERO) == 1;
                } else if (related instanceof BigDecimal) {
                    final BigDecimal f = ((BigDecimal) related);
                    required = (f != null) && (f.compareTo(BigDecimal.ZERO) == 1);
                }
                if (required) {
                    // There must be a repayment type for this value
                    error = RequiredValidation.instance().validate(object, name, value);
                    if (error == null) {
                        // Validates the TT direction
                        if (toSystem && !(!repayment.isFromSystem() && repayment.isToSystem())) {
                            // From system to member
                            error = new InvalidError();
                        } else if (!toSystem && !(repayment.isFromSystem() && !repayment.isToSystem())) {
                            // From member to system
                            error = new InvalidError();
                        }
                    }
                }
            }
            return error;
        }
    }

    private final class MaxMinAmountValidator implements GeneralValidation {
        private static final long serialVersionUID = -7872725176651230479L;

        @Override
        public ValidationError validate(final Object object) {
            TransferType tt = (TransferType) object;
            BigDecimal minAmount = tt.getMinAmount();
            BigDecimal maxAmountPerDay = tt.getMaxAmountPerDay();
            if (minAmount != null && maxAmountPerDay != null) {
                if (minAmount.compareTo(maxAmountPerDay) > 0) {
                    return new ValidationError("transferType.error.minMaxPerDayAmount");
                }
            }
            return null;
        }
    }

    private static final float     PRECISION_DELTA = 0.0001F;
    private AuthorizationLevelDAO  authorizationLevelDao;
    private FetchServiceLocal      fetchService;
    private TransferTypeDAO        transferTypeDao;
    private PaymentServiceLocal    paymentService;
    private AccountServiceLocal    accountService;
    private GroupServiceLocal      groupService;
    private PermissionServiceLocal permissionService;
    private AccountHelper          accountHelper;

    @Override
    public Collection<TransferType> getAllowedTTs(Element element) {
        element = fetchService.fetch(element, RelationshipHelper.nested(Element.Relationships.GROUP, Group.Relationships.TRANSFER_TYPES), RelationshipHelper.nested(Element.Relationships.GROUP, AdminGroup.Relationships.TRANSFER_TYPES_AS_MEMBER), RelationshipHelper.nested(Element.Relationships.GROUP, Group.Relationships.CONVERSION_SIMULATION_TTS));
        Group group = element.getGroup();

        Set<TransferType> allowed = new HashSet<TransferType>();
        CollectionUtils.addAll(allowed, group.getConversionSimulationTTs().iterator());

        // Add the TTs with permission as member
        if (group instanceof AdminGroup) {
            CollectionUtils.addAll(allowed, ((AdminGroup) group).getTransferTypesAsMember().iterator());
        } else if (group instanceof BrokerGroup) {
            CollectionUtils.addAll(allowed, ((BrokerGroup) group).getTransferTypesAsMember().iterator());
        }

        // Add the TTs with permission
        if (group instanceof OperatorGroup) { // an operator doesn't have its own TT it inherits from member
            OperatorGroup operatorGroup = (OperatorGroup) group;
            group = fetchService.fetch(operatorGroup.getMember().getGroup(), Group.Relationships.TRANSFER_TYPES);
        }
        CollectionUtils.addAll(allowed, group.getTransferTypes().iterator());

        // All TTs used to pay account fees should be accessible
        if (group instanceof MemberGroup) {
            MemberGroup memberGroup = (MemberGroup) group;
            Collection<AccountFee> accountFees = memberGroup.getAccountFees();
            for (AccountFee accountFee : accountFees) {
                allowed.add(accountFee.getTransferType());
            }
        }

        return allowed;
    }

    @Override
    public List<TransferType> getAuthorizableTTs() {
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setAuthorizable(true);
        if (!permissionService.hasPermission(AdminSystemPermission.ACCOUNTS_VIEW)) {
            query.setPossibleTransferTypes(getAllowedTTs(LoggedUser.element()));
        }
        return search(query);
    }

    @Override
    public List<TransferType> getConversionTTs() {
        final TransferTypeQuery ttQuery = makeConversionTransferTypeQuery();
        return search(ttQuery);
    }

    @Override
    public List<TransferType> getConversionTTs(final AccountType fromAccountType) {
        final TransferTypeQuery ttQuery = makeConversionTransferTypeQuery();
        final List<AccountType> accountTypes = new ArrayList<AccountType>(1);
        accountTypes.add(fromAccountType);
        ttQuery.setFromAccountTypes(accountTypes);
        return search(ttQuery);
    }

    @Override
    public List<TransferType> getConversionTTs(final Currency currency) {
        final TransferTypeQuery ttQuery = makeConversionTransferTypeQuery();
        ttQuery.setCurrency(currency);
        return search(ttQuery);
    }

    @Override
    public List<TransferType> getPaymentAndSelfPaymentTTs() {
        if (!(LoggedUser.element() instanceof Administrator)) {
            throw new IllegalArgumentException("Expected an administrator logged user");
        }
        AdminGroup group = (AdminGroup) fetchService.fetch(LoggedUser.element().getGroup(), AdminGroup.Relationships.VIEW_INFORMATION_OF);
        final TransferTypeQuery transferTypeQuery = new TransferTypeQuery();
        transferTypeQuery.setFromOrToAccountTypes(group.getViewInformationOf());
        if (!permissionService.hasPermission(AdminSystemPermission.ACCOUNTS_VIEW)) {
            transferTypeQuery.setPossibleTransferTypes(getAllowedTTs(LoggedUser.element()));
        }
        final List<TransferType> transferTypes = search(transferTypeQuery);
        for (final Iterator<TransferType> iterator = transferTypes.iterator(); iterator.hasNext();) {
            // Remove those transfer types which does not allow direct payment or self payment
            final TransferType tt = iterator.next();
            final Context ttContext = tt.getContext();
            if (!ttContext.isPayment() && !ttContext.isSelfPayment()) {
                iterator.remove();
            }
        }
        return transferTypes;
    }

    @Override
    public List<TransferType> getPosibleTTsForAccountFee(final MemberAccountType accountType, final PaymentDirection paymentDirection) {
        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setContext(TransactionContext.ANY);
        switch (paymentDirection) {
            case TO_MEMBER:
                ttQuery.setFromNature(AccountType.Nature.SYSTEM);
                ttQuery.setToAccountType(accountType);
                break;
            case TO_SYSTEM:
                ttQuery.setFromAccountType(accountType);
                ttQuery.setToNature(AccountType.Nature.SYSTEM);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return search(ttQuery);
    }

    @Override
    public TransferTypeVO getTransferTypeVO(final Long transferTypeId, final boolean extended) {
        if (transferTypeId == null) {
            return null;
        }
        TransferType tt = load(transferTypeId);
        if (extended) {
            return accountHelper.toDetailedVO(tt);
        } else {
            return accountHelper.toVO(tt);
        }
    }

    /**
     * gets transferTypes possibly being a conversion, and having an A-Rated TransferFee.
     */
    @Override
    public List<TransferType> listARatedTTs() {
        final List<TransferType> conversionTTs = getConversionTTs();
        final List<TransferType> result = new ArrayList<TransferType>(conversionTTs.size());
        for (final TransferType tt : conversionTTs) {
            if (tt.isHavingAratedFees()) {
                result.add(tt);
            }
        }
        return result;
    }

    @Override
    public TransferType load(final Long id, final Relationship... fetch) {
        return transferTypeDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        return transferTypeDao.delete(ids);
    }

    @Override
    public TransferType save(TransferType transferType) {
        // Validates the transfer type, if validation fails,
        // a Validation exception will be thrown
        validate(transferType);
        if (transferType.getContext().isSelfPayment() && transferType.getFrom().getClass() == Nature.SYSTEM.getType() && transferType.getTo().getClass() == Nature.MEMBER.getType()) {
            final TransferType.Context context = new TransferType.Context();
            context.setPayment(true);
            transferType.setContext(context);
        } else if (transferType.getContext().isPayment() && transferType.getFrom().getClass() == Nature.SYSTEM.getType() && transferType.getTo().getClass() == Nature.SYSTEM.getType()) {
            final TransferType.Context context = new TransferType.Context();
            context.setSelfPayment(true);
            transferType.setContext(context);
        }

        // Clear all loan parameters if they are not valid
        if (!transferType.isLoanType()) {
            transferType.setLoan(null);
        }

        if (transferType.isTransient()) {
            if (transferType.isRequiresFeedback()) {
                transferType.setFeedbackEnabledSince(Calendar.getInstance());
            }
            return transferTypeDao.insert(transferType);
        } else {
            // We must keep the many-to-many relationships
            final TransferType current = load(transferType.getId(), TransferType.Relationships.PAYMENT_FILTERS, TransferType.Relationships.LINKED_CUSTOM_FIELDS);
            transferType.setPaymentFilters(new ArrayList<PaymentFilter>(current.getPaymentFilters()));
            transferType.setLinkedCustomFields(new ArrayList<PaymentCustomField>(current.getLinkedCustomFields()));

            if (current.isRequiresAuthorization() && !transferType.isRequiresAuthorization()) {
                // Authorization has been disabled. Raise an error if there are any pending payments
                final TransferQuery query = new TransferQuery();
                query.setPageForCount();
                query.setTransferType(transferType);
                query.setRequiresAuthorization(true);
                query.setStatus(Payment.Status.PENDING);
                final int payments = PageHelper.getTotalCount(paymentService.search(query));
                if (payments > 0) {
                    throw new HasPendingPaymentsException();
                }
            }

            // Update the feedbackEnabledSince
            if (current.isRequiresFeedback() && !transferType.isRequiresFeedback()) {
                // It was enabled but is no longer - remove the enabled since
                transferType.setFeedbackEnabledSince(null);
            } else if (!current.isRequiresFeedback() && transferType.isRequiresFeedback()) {
                // It was not enabled but is now - set the enabled since
                transferType.setFeedbackEnabledSince(Calendar.getInstance());
            } else {
                // Keep it as is
                transferType.setFeedbackEnabledSince(current.getFeedbackEnabledSince());
            }
            transferType = transferTypeDao.update(transferType);

            // If transfer type does not require authorization, clean authorization levels
            final Collection<AuthorizationLevel> authorizationLevels = transferType.getAuthorizationLevels();
            if (!transferType.isRequiresAuthorization() && !CollectionUtils.isEmpty(authorizationLevels)) {
                for (final AuthorizationLevel authorizationLevel : authorizationLevels) {
                    authorizationLevelDao.delete(authorizationLevel.getId());
                }
            }
            return transferType;
        }
    }

    @Override
    public List<TransferType> search(final TransferTypeQuery query) {
        // If searching for an operator group permissions, the transfer types are the same as his member's. So we have to actually check by his
        // member's permissions
        final Group group = fetchService.fetch(query.getGroup(), RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
        if (group instanceof OperatorGroup) {
            final OperatorGroup operatorGroup = (OperatorGroup) group;
            query.setGroup(operatorGroup.getMember().getGroup());
        }

        final TransferTypeQuery finalQuery = (TransferTypeQuery) query.clone();
        finalQuery.setUsePriority(false);
        if (query.isUsePriority()) {
            query.setPriority(true);
            query.setPageForCount();
            final int totalCount = PageHelper.getTotalCount(transferTypeDao.search(query));
            finalQuery.setPriority(totalCount > 0);
        }

        // When fromOwner is a member, ensure disabled accounts (when credit limit is zero) are not used
        if (query.getFromOwner() instanceof Member) {
            final Member member = fetchService.fetch((Member) query.getFromOwner(), RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.ACCOUNT_SETTINGS));
            final List<AccountType> accountTypes = new ArrayList<AccountType>();
            final List<? extends Account> accounts = accountService.getAccounts(member);
            for (final Account account : accounts) {
                boolean hidden = false;
                try {
                    final MemberGroupAccountSettings accountSettings = groupService.loadAccountSettings(member.getGroup().getId(), account.getType().getId());
                    if (accountSettings.isHideWhenNoCreditLimit() && Math.abs(account.getCreditLimit().floatValue()) < PRECISION_DELTA) {
                        hidden = true;
                    }
                } catch (final EntityNotFoundException e) {
                    continue;
                }
                if (!hidden) {
                    accountTypes.add(account.getType());
                }
            }
            if (CollectionUtils.isNotEmpty(finalQuery.getFromAccountTypes())) {
                // When there were already from account types set, retain them only
                accountTypes.retainAll(finalQuery.getFromAccountTypes());
            }
            // Finally, ensure that only transfer types from visible accounts are used
            finalQuery.setFromAccountTypes(accountTypes);
        }
        return transferTypeDao.search(finalQuery);
    }

    public void setAccountHelper(final AccountHelper accountHelper) {
        this.accountHelper = accountHelper;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAuthorizationLevelDao(final AuthorizationLevelDAO authorizationLevelDao) {
        this.authorizationLevelDao = authorizationLevelDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setTransferTypeDao(final TransferTypeDAO dao) {
        transferTypeDao = dao;
    }

    @Override
    public void validate(final TransferType transferType) {
        if (transferType.isLoanType()) {
            getLoanValidator().validate(transferType);
        } else {
            getValidator().validate(transferType);
        }
    }

    private Validator createValidator() {
        final Validator validator = new Validator("transferType");
        validator.property("name").required().maxLength(100);
        validator.property("description").required().maxLength(1000);
        validator.property("confirmationMessage").maxLength(4000);
        validator.property("from").required();
        validator.property("to").required().add(new DestinationAccountTypeValidator());
        validator.property("maxAmountPerDay").positiveNonZero();
        validator.property("minAmount").positiveNonZero();
        validator.property("feedbackExpirationTime.number").key("transferType.feedbackExpirationTime").add(new FeedbackValidator(RequiredValidation.instance())).add(new FeedbackValidator(PositiveNonZeroValidation.instance()));
        validator.property("feedbackExpirationTime.field").key("transferType.feedbackExpirationTime").add(new FeedbackValidator(RequiredValidation.instance()));
        validator.property("feedbackReplyExpirationTime.number").key("transferType.feedbackReplyExpirationTime").add(new FeedbackValidator(RequiredValidation.instance())).add(new FeedbackValidator(PositiveNonZeroValidation.instance()));
        validator.property("feedbackReplyExpirationTime.field").key("transferType.feedbackReplyExpirationTime").add(new FeedbackValidator(RequiredValidation.instance()));
        validator.property("defaultFeedbackComments").add(new FeedbackValidator(RequiredValidation.instance()));
        validator.property("defaultFeedbackLevel").add(new FeedbackValidator(RequiredValidation.instance()));
        validator.property("transferListenerClass").instanceOf(TransferListener.class);
        validator.general(new MaxMinAmountValidator());
        return validator;
    }

    private Validator getLoanValidator() {
        final Validator loanValidator = createValidator();

        // Chain the loan parameters validator
        final Validator nestedValidator = new Validator("loan", "loan");
        loanValidator.chained(nestedValidator);

        nestedValidator.property("repaymentType").add(new PropertyValidation() {
            private static final long serialVersionUID = -3441031471188004677L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final LoanParameters lp = ((LoanParameters) object);
                ValidationError error = null;
                if (lp != null && lp.getType() != null) {
                    // There must be a repayment type on loan types
                    error = RequiredValidation.instance().validate(object, name, value);
                    if (error == null) {
                        // Validate the repayment type as being from member to system
                        final TransferType repayment = fetchService.fetch((TransferType) value, TransferType.Relationships.FROM, TransferType.Relationships.TO);
                        if (!(!repayment.isFromSystem() && repayment.isToSystem())) {
                            // Must be from member to system
                            error = new InvalidError();
                        }
                    }
                }
                return error;
            }
        });
        nestedValidator.property("repaymentDays").positiveNonZero().add(new PropertyValidation() {
            private static final long serialVersionUID = -3665200579172755969L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final LoanParameters lp = ((LoanParameters) object);
                if (lp != null && lp.getType() == Loan.Type.SINGLE_PAYMENT) {
                    // RepaymentDays is required on single payment type
                    return RequiredValidation.instance().validate(object, name, value);
                }
                return null;
            }
        });
        nestedValidator.property("grantFee").positiveNonZero();
        nestedValidator.property("grantFeeRepaymentType").add(new LoanWithInterestRepaymentTypeValidator("grantFee", true));
        nestedValidator.property("monthlyInterest").positiveNonZero();
        nestedValidator.property("monthlyInterestRepaymentType").add(new LoanWithInterestRepaymentTypeValidator("monthlyInterest", true));
        nestedValidator.property("expirationFee").positiveNonZero();
        nestedValidator.property("expirationFeeRepaymentType").add(new LoanWithInterestRepaymentTypeValidator("expirationFee", true));
        nestedValidator.property("expirationDailyInterest").positiveNonZero();
        nestedValidator.property("expirationDailyInterestRepaymentType").add(new LoanWithInterestRepaymentTypeValidator("expirationDailyInterest", true));
        return loanValidator;
    }

    private Validator getValidator() {
        return createValidator();
    }

    private TransferTypeQuery makeConversionTransferTypeQuery() {
        final TransferTypeQuery ttQuery = new TransferTypeQuery();
        ttQuery.setContext(TransactionContext.PAYMENT);
        ttQuery.setFromNature(AccountType.Nature.MEMBER);
        ttQuery.setToNature(AccountType.Nature.SYSTEM);
        ttQuery.setToLimitType(AccountType.LimitType.UNLIMITED);
        return ttQuery;
    }

}
