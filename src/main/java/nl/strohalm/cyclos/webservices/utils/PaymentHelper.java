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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentRequestTicket;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidUserForChannelException;
import nl.strohalm.cyclos.services.access.exceptions.UserNotFoundException;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.MemberServiceLocal;
import nl.strohalm.cyclos.services.services.ServiceClientServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.TransactionFeeVO;
import nl.strohalm.cyclos.webservices.payments.AbstractPaymentParameters;
import nl.strohalm.cyclos.webservices.payments.AccountHistoryParams;
import nl.strohalm.cyclos.webservices.payments.PaymentParameters;
import nl.strohalm.cyclos.webservices.payments.PaymentStatus;
import nl.strohalm.cyclos.webservices.payments.RequestPaymentParameters;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Helper class for web services payments<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * @author luis
 */
public class PaymentHelper {

    private ChannelHelper                  channelHelper;
    private PaymentCustomFieldServiceLocal paymentCustomFieldServiceLocal;
    private CurrencyHelper                 currencyHelper;
    private TransferTypeServiceLocal       transferTypeServiceLocal;
    private ServiceClientServiceLocal      serviceClientServiceLocal;
    private MemberHelper                   memberHelper;
    private MemberServiceLocal             memberServiceLocal;
    private AccountServiceLocal            accountServiceLocal;
    private QueryHelper                    queryHelper;
    private CustomFieldHelper              customFieldHelper;
    private SettingsServiceLocal           settingsServiceLocal;

    /**
     * Lists the possible transfer types for this payment
     */
    public Collection<TransferType> listPossibleTypes(final DoPaymentDTO dto) {
        final String channel = channelHelper.restricted();
        if (StringUtils.isEmpty(channel)) {
            return Collections.emptyList();
        }

        final Member member = WebServiceContext.getMember();
        final ServiceClient client = WebServiceContext.getClient();

        // First, we need a list of existing TTs for the payment
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setResultType(ResultType.LIST);
        query.setContext(TransactionContext.PAYMENT);
        query.setChannel(channel);
        query.setFromOwner(dto.getFrom());
        query.setToOwner(dto.getTo());
        query.setCurrency(dto.getCurrency());
        final List<TransferType> transferTypes = transferTypeServiceLocal.search(query);

        // Then, restrict according to the web service client permissions
        boolean doPayment = true;
        if (member != null) {
            doPayment = member.equals(dto.getFrom());
        }
        Collection<TransferType> possibleTypes;
        if (doPayment) {
            possibleTypes = serviceClientServiceLocal.load(client.getId(), ServiceClient.Relationships.DO_PAYMENT_TYPES).getDoPaymentTypes();
        } else {
            possibleTypes = serviceClientServiceLocal.load(client.getId(), ServiceClient.Relationships.RECEIVE_PAYMENT_TYPES).getReceivePaymentTypes();
        }
        transferTypes.retainAll(possibleTypes);

        return transferTypes;
    }

    /**
     * Returns the from member, either by id or username
     * @throws EntityNotFoundException When a member was expected (had either id or username) but not found
     */
    public Member resolveFromMember(final AbstractPaymentParameters paymentParameters) throws EntityNotFoundException {
        final String principalType = paymentParameters.getFromMemberPrincipalType();
        final String principal = paymentParameters.getFromMember();

        return resolveMember(principalType, principal, paymentParameters instanceof PaymentParameters && CollectionUtils.isNotEmpty(((PaymentParameters) paymentParameters).getFromMemberFieldsToReturn()));
    }

    /**
     * Returns the to member, either by id or username
     * @throws EntityNotFoundException When a member was expected (had either id or username) but not found
     */
    public Member resolveToMember(final AbstractPaymentParameters paymentParameters) throws EntityNotFoundException {
        final String principalType = paymentParameters.getToMemberPrincipalType();
        final String principal = paymentParameters.getToMember();

        return resolveMember(principalType, principal, paymentParameters instanceof PaymentParameters && CollectionUtils.isNotEmpty(((PaymentParameters) paymentParameters).getToMemberFieldsToReturn()));
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountServiceLocal) {
        this.accountServiceLocal = accountServiceLocal;
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

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setMemberServiceLocal(final MemberServiceLocal memberServiceLocal) {
        this.memberServiceLocal = memberServiceLocal;
    }

    public void setPaymentCustomFieldServiceLocal(final PaymentCustomFieldServiceLocal paymentCustomFieldService) {
        paymentCustomFieldServiceLocal = paymentCustomFieldService;
    }

    public void setQueryHelper(final QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    public void setServiceClientServiceLocal(final ServiceClientServiceLocal serviceClientService) {
        serviceClientServiceLocal = serviceClientService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        settingsServiceLocal = settingsService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        transferTypeServiceLocal = transferTypeService;
    }

    /**
     * Transform a payment parameters into a DoExternalPaymentDTO
     */
    public DoPaymentDTO toExternalPaymentDTO(final AbstractPaymentParameters params, final AccountOwner from, final AccountOwner to) {
        if (params == null) {
            return null;
        }

        final DoPaymentDTO dto = new DoPaymentDTO();
        dto.setAmount(params.getAmount());
        dto.setCurrency(currencyHelper.resolve(params.getCurrency()));
        dto.setDescription(params.getDescription());
        dto.setFrom(from == null ? resolveFromMember(params) : from);
        dto.setTo(to == null ? resolveToMember(params) : to);

        // Do not accept empty trace numbers.
        if (params.getTraceNumber() != null && params.getTraceNumber().trim().equals("")) {
            throw new ValidationException();
        }

        dto.setTraceNumber(params.getTraceNumber());
        dto.setTraceData(params.getTraceData());

        // Handle specific types
        if (params instanceof RequestPaymentParameters) {
            // When requesting a payment, the payment itself will be from the destination channel
            final RequestPaymentParameters request = (RequestPaymentParameters) params;
            final String destinationChannel = request.getDestinationChannel();
            dto.setChannel(destinationChannel);
            if (destinationChannel == null) {
                dto.setChannel(channelHelper.restricted());
            }
        } else if (params instanceof PaymentParameters) {
            final PaymentParameters payment = (PaymentParameters) params;
            dto.setChannel(channelHelper.restricted());

            // Only positive id for transfer types
            final Long transferTypeId = payment.getTransferTypeId();
            if (transferTypeId != null && transferTypeId <= 0L) {
                throw new ValidationException();
            }

            // Get the transfer type
            TransferType transferType = null;
            if (transferTypeId != null) {
                transferType = transferTypeServiceLocal.load(transferTypeId);
            }
            if (transferType == null) {
                // Try to find a default transfer type
                final Collection<TransferType> possibleTypes = listPossibleTypes(dto);
                if (possibleTypes.isEmpty()) {
                    throw new UnexpectedEntityException();
                }
                transferType = possibleTypes.iterator().next();
            }
            dto.setTransferType(transferType);

            final List<FieldValueVO> fieldValueVOs = payment.getCustomValues();
            List<PaymentCustomField> allowedFields = paymentCustomFieldServiceLocal.list(transferType, true);
            final Collection<PaymentCustomFieldValue> customValues = customFieldHelper.toValueCollection(allowedFields, fieldValueVOs);
            dto.setCustomValues(customValues);

            // Set the default description as the transfer type description
            if (StringUtils.isEmpty(payment.getDescription())) {
                dto.setDescription(transferType.getDescription());
            }
        }

        // Set the context, according to the current owners
        if (dto.getFrom().equals(dto.getTo())) {
            dto.setContext(TransactionContext.SELF_PAYMENT);
        } else {
            dto.setContext(TransactionContext.AUTOMATIC);
        }
        return dto;
    }

    /**
     * Returns the payment status that corresponds to the given error
     */
    public PaymentStatus toStatus(final Throwable error) {
        if (error instanceof InvalidCredentialsException) {
            return PaymentStatus.INVALID_CREDENTIALS;
        } else if (error instanceof BlockedCredentialsException) {
            return PaymentStatus.BLOCKED_CREDENTIALS;
        } else if (error instanceof InvalidUserForChannelException) {
            return PaymentStatus.INVALID_CHANNEL;
        } else if (error instanceof NotEnoughCreditsException) {
            return PaymentStatus.NOT_ENOUGH_CREDITS;
        } else if (error instanceof UpperCreditLimitReachedException) {
            return PaymentStatus.RECEIVER_UPPER_CREDIT_LIMIT_REACHED;
        } else if (error instanceof MaxAmountPerDayExceededException) {
            return PaymentStatus.MAX_DAILY_AMOUNT_EXCEEDED;
        } else if (error instanceof IllegalArgumentException || error instanceof ValidationException || error instanceof UnexpectedEntityException || error instanceof EntityNotFoundException || error instanceof UserNotFoundException) {
            return PaymentStatus.INVALID_PARAMETERS;
        } else if (ExceptionUtils.indexOfThrowable(error, DataIntegrityViolationException.class) != -1) {
            return PaymentStatus.INVALID_PARAMETERS;
        } else {
            return PaymentStatus.UNKNOWN_ERROR;
        }
    }

    /**
     * Return the payment status for the given transfer
     */
    public PaymentStatus toStatus(final Transfer transfer) {
        if (transfer.getProcessDate() == null) {
            return PaymentStatus.PENDING_AUTHORIZATION;
        } else {
            return PaymentStatus.PROCESSED;
        }
    }

    /**
     * Transform a request payment parameters into a payment request ticket
     */
    public PaymentRequestTicket toTicket(final RequestPaymentParameters params, final TransferType transferType) {
        if (params == null) {
            return null;
        }
        String description = params.getDescription();
        if (StringUtils.isEmpty(description) && transferType != null) {
            description = transferType.getDescription();
        }
        final PaymentRequestTicket ticket = new PaymentRequestTicket();
        ticket.setTraceData(params.getTraceData());
        ticket.setFromChannel(WebServiceContext.getChannel());
        Channel toChannel = channelHelper.get(params.getDestinationChannel());
        if (toChannel == null) {
            toChannel = ticket.getFromChannel();
        }
        ticket.setToChannel(toChannel);
        ticket.setAmount(params.getAmount());
        ticket.setTransferType(transferType);
        ticket.setDescription(description);
        ticket.setCurrency(currencyHelper.resolve(params.getCurrency()));
        return ticket;
    }

    /**
     * Returns a list of TransactionFeeVO that contains information about the transaction fee and the amount that corresponds to the application of
     * that fee.
     * @param fees A map containing the transaction fee and the resulting amount of applying that fee.
     */
    public List<TransactionFeeVO> toTransactionFeeVOs(final Map<TransactionFee, BigDecimal> fees) {
        if (fees == null) {
            return null;
        }
        List<TransactionFeeVO> result = new ArrayList<TransactionFeeVO>(fees.size());
        final LocalSettings localSettings = settingsServiceLocal.getLocalSettings();
        for (Map.Entry<TransactionFee, BigDecimal> entry : fees.entrySet()) {
            TransactionFee key = entry.getKey();
            BigDecimal value = entry.getValue();
            String formattedAmount = localSettings.getUnitsConverter(key.getGeneratedTransferType().getCurrency().getPattern()).toString(value);
            TransactionFeeVO transactionFeeVO = new TransactionFeeVO(key.getName(), value, formattedAmount);
            result.add(transactionFeeVO);
        }
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public TransferQuery toTransferQuery(final AccountHistoryParams params) {
        Member member = memberServiceLocal.loadByIdOrPrincipal(params.getMemberId(), null, params.getMemberPrincipal());

        // Convert the params to a TransferQuery
        TransferQuery query = new TransferQuery();
        queryHelper.fill(params, query);
        MemberAccount account = null;
        if (EntityHelper.isValidId(params.getMemberAccountId())) {
            account = accountServiceLocal.load(params.getMemberAccountId());
        } else {
            account = accountServiceLocal.getDefaultAccount();
        }
        query.setOwner(account.getOwner());
        query.setType(account.getType());
        query.setPeriod(Period.between(params.getBeginDate(), params.getEndDate()));
        query.setMember(member);
        query.setReverseOrder(true);

        final List<PaymentCustomField> fieldsForSearch = paymentCustomFieldServiceLocal.listForSearch(account, false);
        query.setCustomValues((Collection) customFieldHelper.toValueCollection(fieldsForSearch, params.getCustomValues()));
        return query;
    }

    private Member resolveMember(final String principalType, final String principal, final boolean requiredFields) throws EntityNotFoundException {
        if (requiredFields) {
            return memberHelper.loadByPrincipal(principalType, principal, Member.Relationships.CUSTOM_VALUES);
        } else {
            return memberHelper.loadByPrincipal(principalType, principal);
        }
    }

}
