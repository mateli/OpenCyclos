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
package nl.strohalm.cyclos.webservices.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.AccountTypeServiceLocal;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.WebServiceFaultsEnum;
import nl.strohalm.cyclos.webservices.accounts.AccountHistoryResultPage;
import nl.strohalm.cyclos.webservices.accounts.AccountHistorySearchParameters;
import nl.strohalm.cyclos.webservices.accounts.ScheduledPaymentsResultPage;
import nl.strohalm.cyclos.webservices.accounts.TransferTypeSearchParameters;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.model.AccountTypeVO;
import nl.strohalm.cyclos.webservices.model.BasePaymentDataVO;
import nl.strohalm.cyclos.webservices.model.BasePaymentVO;
import nl.strohalm.cyclos.webservices.model.CurrencyVO;
import nl.strohalm.cyclos.webservices.model.DetailedAccountTypeVO;
import nl.strohalm.cyclos.webservices.model.DetailedTransferTypeVO;
import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;
import nl.strohalm.cyclos.webservices.model.PaymentFilterVO;
import nl.strohalm.cyclos.webservices.model.PaymentStatusVO;
import nl.strohalm.cyclos.webservices.model.ScheduledPaymentInstallmentVO;
import nl.strohalm.cyclos.webservices.model.ScheduledPaymentVO;
import nl.strohalm.cyclos.webservices.model.TransferTypeVO;

/**
 * Utility class for accounts<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * @author luis
 */
public class AccountHelper {

    private PaymentCustomFieldServiceLocal paymentCustomFieldService;
    private SettingsServiceLocal           settingsService;
    private AccountServiceLocal            accountService;
    private TransferTypeServiceLocal       transferTypeService;
    private AccountTypeServiceLocal        accountTypeService;

    private QueryHelper                    queryHelper;
    private MemberHelper                   memberHelper;
    private FieldHelper                    fieldHelper;
    private ChannelHelper                  channelHelper;
    private CurrencyHelper                 currencyHelper;
    private CustomFieldHelper              customFieldHelper;

    /**
     * Returns the default account for the current member, but only if there is a logged member. Throws {@link EntityNotFoundException} otherwise, or
     * if the member has no accounts
     */
    public MemberAccount getDefaultAccount() {
        MemberAccount account = null;
        if (LoggedUser.hasUser()) {
            MemberGroup group = LoggedUser.group();
            MemberAccountType defaultType = accountTypeService.getDefault(group);
            if (defaultType != null) {
                account = (MemberAccount) accountService.getAccount(new AccountDTO(LoggedUser.accountOwner(), defaultType));
            }
        }
        if (account == null) {
            throw new EntityNotFoundException(Account.class);
        }
        return account;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAccountTypeServiceLocal(final AccountTypeServiceLocal accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setChannelHelper(final ChannelHelper channelHelper) {
        this.channelHelper = channelHelper;
    }

    public void setCurrencyHelper(final CurrencyHelper currencyHelper) {
        this.currencyHelper = currencyHelper;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setFieldHelper(final FieldHelper fieldHelper) {
        this.fieldHelper = fieldHelper;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setPaymentCustomFieldServiceLocal(final PaymentCustomFieldServiceLocal paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    public void setQueryHelper(final QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    /**
     * Transform a list of transfers into an account history result page
     */
    public AccountHistoryResultPage toAccountHistoryResultPage(final AccountOwner owner, final List<Transfer> transfers) {
        final Map<TransferType, Collection<PaymentCustomField>> customFieldsByTransferType = new HashMap<TransferType, Collection<PaymentCustomField>>();

        return queryHelper.toResultPage(AccountHistoryResultPage.class, transfers, new Transformer<Transfer, AccountHistoryTransferVO>() {
            @Override
            public AccountHistoryTransferVO transform(final Transfer transfer) {
                final TransferType transferType = transfer.getType();
                Collection<PaymentCustomField> customFields = customFieldsByTransferType.get(transferType);
                if (customFields == null) {
                    customFields = paymentCustomFieldService.list(transferType, true);
                    customFieldsByTransferType.put(transferType, customFields);
                }

                return toVO(owner, transfer, customFields);
            }
        });
    }

    /**
     * Converts a list of transfer types into detailed VOs
     */
    public List<DetailedTransferTypeVO> toDetailedTransferTypeVOs(final List<TransferType> transferTypes) {
        final List<DetailedTransferTypeVO> vos = new ArrayList<DetailedTransferTypeVO>(transferTypes.size());
        for (final TransferType transferType : transferTypes) {
            vos.add(toDetailedVO(transferType));
        }
        return vos;
    }

    /**
     * Transforms a MemberAccount into a {@link DetailedAccountTypeVO}
     */
    public DetailedAccountTypeVO toDetailedTypeVO(final String channel, MemberAccount memberAccount) {
        if (memberAccount == null) {
            return null;
        }
        memberAccount = accountService.load(memberAccount.getId(), Account.Relationships.TYPE, RelationshipHelper.nested(MemberAccount.Relationships.MEMBER, Element.Relationships.GROUP, MemberGroup.Relationships.ACCOUNT_SETTINGS));

        // Find the default account type for the member's group
        final AccountType type = memberAccount.getType();
        final Member member = memberAccount.getOwner();
        final MemberGroup memberGroup = member.getMemberGroup();
        final boolean isDefault = isDefault(type, memberGroup);

        // Build the VO
        final DetailedAccountTypeVO vo = new DetailedAccountTypeVO();
        vo.setId(type.getId());
        vo.setName(type.getName());
        vo.setDefault(isDefault);
        vo.setCurrency(toVO(type.getCurrency()));

        // Search the possible transfer types
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setChannel(channel);
        query.setContext(TransactionContext.PAYMENT);
        query.setFromOwner(member);
        query.setFromAccountType(type);
        final List<TransferTypeVO> transferTypes = new ArrayList<TransferTypeVO>();
        for (final TransferType tt : transferTypeService.search(query)) {
            transferTypes.add(toVO(tt));
        }
        vo.setTransferTypes(transferTypes);
        return vo;
    }

    /**
     * Transforms the given transfer type to a detailed VO
     */
    public DetailedTransferTypeVO toDetailedVO(final TransferType tt) {
        final DetailedTransferTypeVO vo = new DetailedTransferTypeVO();
        if (tt == null) {
            return null;
        }
        assignFieldsToTranserTypeVO(tt, vo);
        TransferType t = new TransferType();
        t.setId(tt.getId());
        List<PaymentCustomField> fields = paymentCustomFieldService.list(t, false);
        vo.setCustomFields(fieldHelper.toFieldVOs(fields));
        return vo;
    }

    public List<PaymentFilterVO> toPaymentFilterVOs(final Collection<PaymentFilter> paymentFilters) {
        List<PaymentFilterVO> result = new ArrayList<PaymentFilterVO>();
        if (paymentFilters != null) {
            for (PaymentFilter paymentFilter : paymentFilters) {
                result.add(toVO(paymentFilter));
            }
        }
        return result;
    }

    public TransferQuery toQuery(final AccountHistorySearchParameters params, final Member member) {
        if (params == null) {
            return null;
        }
        final TransferQuery query = new TransferQuery();
        queryHelper.fill(params, query);
        query.setReverseOrder(params.getReverseOrder());
        AccountType accountType = CoercionHelper.coerce(AccountType.class, params.getAccountTypeId());
        if (accountType == null) {
            accountType = resolveAccountType(params, member);
        }
        // Use the client member when restricted
        final Member ownerMember = memberHelper.resolveMember(params.getPrincipalType(), params.getPrincipal());
        query.setOwner(ownerMember == null ? SystemAccountOwner.instance() : ownerMember);
        query.setMember(memberHelper.loadByPrincipal(params.getRelatedMemberPrincipalType(), params.getRelatedMember()));
        final Account account = accountService.getAccount(new AccountDTO(ownerMember, accountType), Account.Relationships.TYPE);
        query.setType(account.getType());
        final List<FieldValueVO> fields = params.getFields();
        if (fields != null && fields.size() > 0) {
            final List<PaymentCustomField> fieldsForSearch = paymentCustomFieldService.listForSearch(account, false);
            final Collection<PaymentCustomFieldValue> customFields = customFieldHelper.toValueCollection(fieldsForSearch, fields);
            query.setCustomValues(customFields);
        }
        if (params.getBeginDate() != null || params.getEndDate() != null) {
            final Period period = new Period(params.getBeginDate(), params.getEndDate());
            query.setPeriod(period);
        }
        return query;
    }

    public TransferTypeQuery toQuery(final TransferTypeSearchParameters params) {
        if (params == null) {
            return null;
        }

        final TransferTypeQuery query = new TransferTypeQuery();
        query.setResultType(ResultType.LIST);
        query.setCurrency(currencyHelper.resolve(params.getCurrency()));
        query.setFromAccountType(CoercionHelper.coerce(AccountType.class, params.getFromAccountTypeId()));
        query.setToAccountType(CoercionHelper.coerce(AccountType.class, params.getToAccountTypeId()));
        query.setChannel(channelHelper.restricted());

        final Member restrictedMember = WebServiceContext.getMember();

        if (restrictedMember != null) {
            if (params.getToMember() != null) {
                query.setToOwner(memberHelper.loadByPrincipal(params.getToMemberPrincipalType(), params.getToMember()));
            } else if (params.getToSystem()) {
                query.setToOwner(SystemAccountOwner.instance());
            } else {
                query.setToOwner(restrictedMember);
            }

            if (params.getFromMember() != null) {
                final Member member = memberHelper.loadByPrincipal(params.getFromMemberPrincipalType(), params.getFromMember());
                query.setFromOwner(member);
                query.setGroup(member == null ? null : member.getGroup()); // this is to take into account the group permissions in the search TT
            } else if (params.getFromSystem()) {
                query.setFromOwner(SystemAccountOwner.instance());
            }

            if (!restrictedMember.equals(query.getFromOwner()) && !restrictedMember.equals(query.getToOwner())) {
                throw WebServiceHelper.fault(WebServiceFaultsEnum.UNAUTHORIZED_ACCESS);
            }
        } else {
            if (params.getFromSystem()) {
                query.setFromNature(AccountType.Nature.SYSTEM);
            } else {
                final Member member = memberHelper.loadByPrincipal(params.getFromMemberPrincipalType(), params.getFromMember());
                query.setFromOwner(member);
                query.setGroup(member == null ? null : member.getGroup()); // this is to take into account the group permissions in the search TT
            }

            if (params.getToSystem()) {
                query.setToNature(AccountType.Nature.SYSTEM);
            } else {
                query.setToOwner(memberHelper.loadByPrincipal(params.getToMemberPrincipalType(), params.getToMember()));
            }
        }

        return query;
    }

    public ScheduledPaymentsResultPage toScheduledResultPage(final Member owner, final List<ScheduledPayment> payments) {
        final Map<TransferType, Collection<PaymentCustomField>> customFieldsByTransferType = new HashMap<TransferType, Collection<PaymentCustomField>>();
        return queryHelper.toResultPage(ScheduledPaymentsResultPage.class, payments, new Transformer<ScheduledPayment, ScheduledPaymentVO>() {
            @Override
            public ScheduledPaymentVO transform(final ScheduledPayment scheduledPayment) {
                final TransferType transferType = scheduledPayment.getType();
                Collection<PaymentCustomField> customFields = customFieldsByTransferType.get(transferType);
                if (customFields == null) {
                    customFields = paymentCustomFieldService.list(transferType, true);
                    customFieldsByTransferType.put(transferType, customFields);
                }

                return toVO(owner, scheduledPayment, customFields);
            }
        });
    }

    /**
     * Converts a list of transfer types into VOs
     */
    public List<TransferTypeVO> toTransferTypeVOs(final List<TransferType> transferTypes) {
        final List<TransferTypeVO> vos = new ArrayList<TransferTypeVO>(transferTypes.size());
        for (final TransferType transferType : transferTypes) {
            vos.add(toVO(transferType));
        }
        return vos;
    }

    /**
     * Converts an scheduled payments into VO
     */
    public ScheduledPaymentVO toVO(final AccountOwner viewingOwner, final ScheduledPayment scheduledPayment, final Collection<PaymentCustomField> customFields) {
        if (scheduledPayment == null) {
            return null;
        }
        final ScheduledPaymentVO vo = new ScheduledPaymentVO();
        fill(vo, viewingOwner, scheduledPayment, customFields, null, null);
        vo.setOpen(scheduledPayment.getFirstOpenTransfer() != null);
        List<ScheduledPaymentInstallmentVO> installments = new ArrayList<ScheduledPaymentInstallmentVO>();
        for (Transfer installment : scheduledPayment.getTransfers()) {
            ScheduledPaymentInstallmentVO ivo = new ScheduledPaymentInstallmentVO();
            fill(ivo, viewingOwner, installment);
            installments.add(ivo);
        }
        vo.setInstallments(installments);
        return vo;
    }

    /**
     * Converts a list of scheduled payments into VOs
     */
    public AccountHistoryTransferVO toVO(final AccountOwner viewingOwner, final Transfer transfer, final Collection<PaymentCustomField> customFields) {
        return toVO(viewingOwner, transfer, customFields, null, null);
    }

    /**
     * Transforms the given transfer to a VO, according to the member viewing it
     * @param fromCustomFields collection of fields to be set to the transfer's payer MemberVO.
     * @param toCustomFields collection of fields to be set to the transfer's receiver MemberVO.
     */
    public AccountHistoryTransferVO toVO(final AccountOwner viewingOwner, final Transfer transfer, final Collection<PaymentCustomField> customFields, final Collection<MemberCustomField> fromCustomFields, final Collection<MemberCustomField> toCustomFields) {
        if (transfer == null) {
            return null;
        }
        final AccountHistoryTransferVO vo = new AccountHistoryTransferVO();
        fill(vo, viewingOwner, transfer, customFields, fromCustomFields, toCustomFields);
        vo.setTransactionNumber(transfer.getTransactionNumber());
        // only for the client which originate the transfer sets the trace number
        if (WebServiceHelper.isCurrentClient(transfer.getClientId())) {
            vo.setTraceNumber(transfer.getTraceNumber());
        }
        return vo;
    }

    /**
     * Transforms the given account status to a VO
     */
    public AccountStatusVO toVO(final AccountStatus status) {
        if (status == null) {
            return null;
        }
        final UnitsConverter unitsConverter = settingsService.getLocalSettings().getUnitsConverter(status.getAccount().getType().getCurrency().getPattern());
        final AccountStatusVO vo = new AccountStatusVO();
        vo.setBalance(status.getBalance());
        vo.setFormattedBalance(unitsConverter.toString(vo.getBalance()));
        vo.setAvailableBalance(status.getAvailableBalance());
        vo.setFormattedAvailableBalance(unitsConverter.toString(vo.getAvailableBalance()));
        vo.setReservedAmount(status.getReservedAmount());
        vo.setFormattedReservedAmount(unitsConverter.toString(vo.getReservedAmount()));
        vo.setCreditLimit(status.getCreditLimit());
        vo.setFormattedCreditLimit(unitsConverter.toString(vo.getCreditLimit()));
        vo.setUpperCreditLimit(status.getUpperCreditLimit());
        vo.setFormattedUpperCreditLimit(unitsConverter.toString(vo.getUpperCreditLimit()));
        return vo;
    }

    /**
     * Transforms the given account type to a VO
     */
    public AccountTypeVO toVO(final AccountType type) {
        if (type == null) {
            return null;
        }
        final AccountTypeVO vo = new AccountTypeVO();
        vo.setId(type.getId());
        vo.setCurrency(toVO(type.getCurrency()));
        vo.setName(type.getName());
        return vo;
    }

    /**
     * Transforms the given currency to a VO
     */
    public CurrencyVO toVO(final Currency currency) {
        if (currency == null) {
            return null;
        }
        final CurrencyVO vo = new CurrencyVO();
        vo.setId(currency.getId());
        vo.setName(currency.getName());
        vo.setSymbol(currency.getSymbol());
        vo.setPattern(currency.getPattern());
        return vo;
    }

    /**
     * Transforms the given account status to a VO
     */
    public MemberAccountVO toVO(MemberAccount memberAccount) {
        if (memberAccount == null) {
            return null;
        }
        memberAccount = accountService.load(memberAccount.getId(), Account.Relationships.TYPE, RelationshipHelper.nested(MemberAccount.Relationships.MEMBER, Element.Relationships.GROUP, MemberGroup.Relationships.ACCOUNT_SETTINGS));

        // Find the default account type for the member's group
        final AccountType type = memberAccount.getType();
        final MemberGroup memberGroup = memberAccount.getOwner().getMemberGroup();
        final boolean isDefault = isDefault(type, memberGroup);

        final MemberAccountVO vo = new MemberAccountVO();
        vo.setId(memberAccount.getId());
        vo.setDefault(isDefault);
        vo.setType(toVO(type));
        return vo;
    }

    public PaymentFilterVO toVO(final PaymentFilter paymentFilter) {
        if (paymentFilter == null) {
            return null;
        }
        PaymentFilterVO vo = new PaymentFilterVO();
        vo.setId(paymentFilter.getId());
        vo.setName(paymentFilter.getName());
        return vo;
    }

    /**
     * Transforms the given transfer type to a VO
     */
    public TransferTypeVO toVO(final TransferType tt) {
        if (tt == null) {
            return null;
        }
        final TransferTypeVO vo = new TransferTypeVO();
        assignFieldsToTranserTypeVO(tt, vo);
        return vo;
    }

    /**
     * Converts a list of accounts into VOs
     */
    public List<MemberAccountVO> toVOs(final List<MemberAccount> accounts) {
        final List<MemberAccountVO> vos = new ArrayList<MemberAccountVO>(accounts.size());
        for (final MemberAccount account : accounts) {
            vos.add(toVO(account));
        }
        return vos;
    }

    private void assignFieldsToTranserTypeVO(TransferType tt, final TransferTypeVO vo) {
        tt = transferTypeService.load(tt.getId(), TransferType.Relationships.FROM, TransferType.Relationships.TO);
        vo.setId(tt.getId());
        vo.setName(tt.getName());
        vo.setFrom(toVO(tt.getFrom()));
        vo.setTo(toVO(tt.getTo()));
    }

    private void fill(final BasePaymentDataVO vo, final AccountOwner viewingOwner, final Payment payment) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final BigDecimal amount = payment.getActualAmount();
        vo.setId(payment.getId());
        vo.setDate(payment.getDate());
        vo.setFormattedDate(localSettings.getDateConverter().toString(vo.getDate()));
        vo.setProcessDate(payment.getProcessDate());
        vo.setFormattedProcessDate(localSettings.getDateConverter().toString(vo.getProcessDate()));
        vo.setStatus(PaymentStatusVO.valueOf(payment.getStatus().name()));

        final Member restrictedMember = WebServiceContext.getMember();
        if (restrictedMember == null && viewingOwner == null) { // in this case is a nonsense have a viewing member
            vo.setAmount(amount);
        } else {
            final boolean isDebit = viewingOwner.equals(payment.getActualFrom().getOwner());
            vo.setAmount(isDebit ? amount.negate() : amount);
        }
        TransferType tt = transferTypeService.load(payment.getType().getId(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY));
        vo.setFormattedAmount(localSettings.getUnitsConverter(tt.getFrom().getCurrency().getPattern()).toString(vo.getAmount()));
    }

    private void fill(final BasePaymentVO vo, final AccountOwner viewingOwner, final Payment payment, Collection<PaymentCustomField> customFields, final Collection<MemberCustomField> fromCustomFields, final Collection<MemberCustomField> toCustomFields) {
        fill(vo, viewingOwner, payment);

        final LocalSettings localSettings = settingsService.getLocalSettings();
        final BigDecimal amount = payment.getActualAmount();
        vo.setId(payment.getId());
        vo.setDate(payment.getDate());
        vo.setFormattedDate(localSettings.getDateConverter().toString(vo.getDate()));
        vo.setProcessDate(payment.getProcessDate());
        vo.setFormattedProcessDate(localSettings.getDateConverter().toString(vo.getProcessDate()));
        vo.setTransferType(toVO(payment.getType()));
        vo.setDescription(payment.getDescription());

        final Member restrictedMember = WebServiceContext.getMember();
        if (restrictedMember == null && viewingOwner == null) { // in this case is a nonsense have a viewing member
            vo.setAmount(amount);
            setRelatedAccount(vo, accountService.load(payment.getActualFrom().getId()), false, fromCustomFields);
            setRelatedAccount(vo, accountService.load(payment.getActualTo().getId()), true, toCustomFields);
        } else {
            final boolean isDebit = viewingOwner.equals(payment.getActualFrom().getOwner());
            final Account relatedAccount = accountService.load(isDebit ? payment.getActualTo().getId() : payment.getActualFrom().getId());
            vo.setAmount(isDebit ? amount.negate() : amount);
            setRelatedAccount(vo, relatedAccount, true, null);
        }
        if (customFields == null) {
            customFields = paymentCustomFieldService.list(payment.getType(), true);
        }
        vo.setFields(fieldHelper.toList(customFields, null, payment.getCustomValues()));
    }

    /**
     * Checks whether the given account type is the default for the given group
     */
    private boolean isDefault(final AccountType accountType, final MemberGroup memberGroup) {
        boolean isDefault = false;
        for (final MemberGroupAccountSettings accountSettings : memberGroup.getAccountSettings()) {
            if (accountSettings.getAccountType().equals(accountType) && accountSettings.isDefault()) {
                isDefault = true;
                break;
            }
        }
        return isDefault;
    }

    private AccountType resolveAccountType(final AccountHistorySearchParameters params, final Member member) {
        AccountType type = null;
        if (params.getAccountTypeId() == null) {
            // No account type id was passed. Try by currency first
            final Currency currency = currencyHelper.resolve(params.getCurrency());
            if (currency == null) {
                // No currency was passed: get the default account
                type = resolveDefaultAccountType(member);
            } else {
                // Get the first account with the given currency
                for (final Account account : accountService.getAccounts(member)) {
                    if (currency.equals(account.getType().getCurrency())) {
                        type = account.getType();
                        break;
                    }
                }
                if (type == null) {
                    // No account of the given currency
                    throw WebServiceHelper.fault(WebServiceFaultsEnum.INVALID_PARAMETERS, "No account of the given currency: " + currency);
                }
            }
        }
        return type;
    }

    @SuppressWarnings("unchecked")
    private AccountType resolveDefaultAccountType(final Member member) {
        final List<Account> allAccounts = (List<Account>) accountService.getAccounts(member, Account.Relationships.TYPE);
        final Account defaultAccount = accountService.getDefaultAccountFromList(member, allAccounts);
        return defaultAccount == null ? null : defaultAccount.getType();
    }

    private void setRelatedAccount(final BasePaymentVO vo, final Account account, final boolean isRelated, final Collection<MemberCustomField> requiredFields) {
        if (account instanceof MemberAccount) {
            if (isRelated) {
                vo.setMember(memberHelper.toVO((Member) account.getOwner(), requiredFields, requiredFields, false));
            } else {
                vo.setFromMember(memberHelper.toVO((Member) account.getOwner(), requiredFields, requiredFields, false));
            }
        } else {
            if (isRelated) {
                vo.setSystemAccountName(account.getOwnerName());
            } else {
                vo.setFromSystemAccountName(account.getOwnerName());
            }
        }
    }
}
