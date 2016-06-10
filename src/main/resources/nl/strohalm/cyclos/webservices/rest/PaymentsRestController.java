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
package nl.strohalm.cyclos.webservices.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.User.TransactionPasswordStatus;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.elements.MemberService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transactions.exceptions.MaxAmountPerDayExceededException;
import nl.strohalm.cyclos.services.transactions.exceptions.NotEnoughCreditsException;
import nl.strohalm.cyclos.services.transactions.exceptions.PaymentException;
import nl.strohalm.cyclos.services.transactions.exceptions.TransferMinimumPaymentException;
import nl.strohalm.cyclos.services.transactions.exceptions.UpperCreditLimitReachedException;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.model.DetailedTransferTypeVO;
import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.MemberVO;
import nl.strohalm.cyclos.webservices.model.PaymentDataVO;
import nl.strohalm.cyclos.webservices.model.ServerErrorVO;
import nl.strohalm.cyclos.webservices.model.TransactionFeeVO;
import nl.strohalm.cyclos.webservices.model.TransferTypeVO;
import nl.strohalm.cyclos.webservices.rest.TransferTypesRestController.Destination;
import nl.strohalm.cyclos.webservices.rest.TransferTypesRestController.TransferTypeSearchParams;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller which handles /payments paths
 * 
 * @author luis
 */
@Controller
public class PaymentsRestController extends BaseRestController {

    /**
     * Result after doing a payment confirmation
     * @author jcomas
     */
    public static class ConfirmPaymentResult {
        private Long    id;
        private boolean pending;

        public Long getId() {
            return id;
        }

        public boolean isPending() {
            return pending;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public void setPending(final boolean pending) {
            this.pending = pending;
        }
    }

    /**
     * Parameters for doing a payment to a member
     * @author luis
     */
    public static class DoMemberPaymentParameters extends DoPaymentParameters {
        private Long     toMemberId;
        private String   toMemberPrincipal;
        private Integer  installments = 0;
        private Calendar firstInstallmentDate;

        public Calendar getFirstInstallmentDate() {
            return firstInstallmentDate;
        }

        public int getInstallments() {
            return installments;
        }

        public Long getToMemberId() {
            return toMemberId;
        }

        public String getToMemberPrincipal() {
            return toMemberPrincipal;
        }

        public void setFirstInstallmentDate(final Calendar firstInstallmentDate) {
            this.firstInstallmentDate = firstInstallmentDate;
        }

        public void setInstallments(final int installments) {
            this.installments = installments;
        }

        public void setToMemberId(final Long toMemberId) {
            this.toMemberId = toMemberId;
        }

        public void setToMemberPrincipal(final String toMemberPrincipal) {
            this.toMemberPrincipal = toMemberPrincipal;
        }
    }

    /**
     * Parameters for doing a payment to system
     * @author luis
     */
    public static class DoPaymentParameters {
        private Long               currencyId;
        private String             currencySymbol;
        private Long               transferTypeId;
        private BigDecimal         amount;
        private String             description;
        private String             transactionPassword;
        private List<FieldValueVO> customValues;

        public BigDecimal getAmount() {
            return amount;
        }

        public Long getCurrencyId() {
            return currencyId;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public List<FieldValueVO> getCustomValues() {
            return customValues;
        }

        public String getDescription() {
            return description;
        }

        public String getTransactionPassword() {
            return transactionPassword;
        }

        public Long getTransferTypeId() {
            return transferTypeId;
        }

        public void setAmount(final BigDecimal amount) {
            this.amount = amount;
        }

        public void setCurrencyId(final Long currencyId) {
            this.currencyId = currencyId;
        }

        public void setCurrencySymbol(final String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }

        public void setCustomValues(final List<FieldValueVO> customValues) {
            this.customValues = customValues;
        }

        public void setDescription(final String description) {
            this.description = description;
        }

        public void setTransactionPassword(final String transactionPassword) {
            this.transactionPassword = transactionPassword;
        }

        public void setTransferTypeId(final Long transferTypeId) {
            this.transferTypeId = transferTypeId;
        }

        @Override
        public String toString() {
            return "DoPaymentParameters [currencyId=" + currencyId + ", currencySymbol=" + currencySymbol + ", transferTypeId=" + transferTypeId + ", amount=" + amount + ", description=" + description + ", transactionPassword=" + transactionPassword + ", customValues=" + customValues + "]";
        }

    }

    /**
     * Result after doing a payment
     * @author luis
     */
    public static class DoPaymentResult {
        private boolean                wouldRequireAuthorization;
        private MemberVO               from;
        private MemberVO               to;
        private BigDecimal             finalAmount;
        private String                 formattedFinalAmount;
        private List<TransactionFeeVO> fees;
        private TransferTypeVO         transferType;
        private Map<String, String>    customValues;

        public Map<String, String> getCustomValues() {
            return customValues;
        }

        public List<TransactionFeeVO> getFees() {
            return fees;
        }

        public BigDecimal getFinalAmount() {
            return finalAmount;
        }

        public String getFormattedFinalAmount() {
            return formattedFinalAmount;
        }

        public MemberVO getFrom() {
            return from;
        }

        public MemberVO getTo() {
            return to;
        }

        public TransferTypeVO getTransferType() {
            return transferType;
        }

        public boolean isWouldRequireAuthorization() {
            return wouldRequireAuthorization;
        }

        public void setCustomValues(final Map<String, String> customValues) {
            this.customValues = customValues;
        }

        public void setFees(final List<TransactionFeeVO> fees) {
            this.fees = fees;
        }

        public void setFinalAmount(final BigDecimal finalAmount) {
            this.finalAmount = finalAmount;
        }

        public void setFormattedFinalAmount(final String formattedFinalAmount) {
            this.formattedFinalAmount = formattedFinalAmount;
        }

        public void setFrom(final MemberVO from) {
            this.from = from;
        }

        public void setTo(final MemberVO to) {
            this.to = to;
        }

        public void setTransferType(final TransferTypeVO transferType) {
            this.transferType = transferType;
        }

        public void setWouldRequireAuthorization(final boolean wouldRequireAuthorization) {
            this.wouldRequireAuthorization = wouldRequireAuthorization;
        }

    }

    private static final String         BLOCKED_TRANSACTION_PASSWORD_ERROR  = "BLOCKED_TRANSACTION_PASSWORD";
    private static final String         INVALID_TRANSACTION_PASSWORD_ERROR  = "INVALID_TRANSACTION_PASSWORD";
    private static final String         MISSING_TRANSACTION_PASSWORD_ERROR  = "MISSING_TRANSACTION_PASSWORD";
    private static final String         INACTIVE_TRANSACTION_PASSWORD_ERROR = "INACTIVE_TRANSACTION_PASSWORD";
    private static final String         NO_POSSIBLE_TRANSFER_TYPES_ERROR    = "NO_POSSIBLE_TRANSFER_TYPES";

    private static final String         INVALID_AMOUNT_ERROR                = "INVALID_AMOUNT";

    private static final String         MAX_AMOUNT_PER_DAY_EXCEEDED         = "MAX_AMOUNT_PER_DAY_EXCEEDED";
    private static final String         NOT_ENOUGH_CREDITS                  = "NOT_ENOUGH_FUNDS";
    private static final String         UPPER_CREDIT_LIMIT_REACHED          = "UPPER_CREDIT_LIMIT_REACHED";
    protected static final String       TRANSFER_MINIMUM_PAYMENT            = "TRANSFER_MINIMUM_PAYMENT";
    protected static final String       UNKNOWN_PAYMENT_ERROR               = "UNKNOWN_PAYMENT_ERROR";

    private PaymentService              paymentService;
    private TransferTypeService         transferTypeService;
    private AccountTypeService          accountTypeService;
    private AccountService              accountService;
    private AccessService               accessService;
    private ElementService              elementService;
    private TransactionFeeService       transactionFeeService;
    private SettingsService             settingsService;
    private MemberService               memberService;
    private CustomFieldHelper           customFieldHelper;
    private PaymentCustomFieldService   paymentCustomFieldService;

    private TransferTypesRestController transferTypesRestController;
    private AccessRestController        accessRestController;
    private AccountsRestController      accountsRestController;

    /**
     * Confirms a payment to another member
     */
    @RequestMapping(value = "payments/confirmMemberPayment", method = RequestMethod.POST)
    @ResponseBody
    public ConfirmPaymentResult confirmPaymentToMember(@RequestBody final DoMemberPaymentParameters params) {
        if (params == null) {
            throw new ValidationException();
        }

        Member toMember = memberService.loadByIdOrPrincipal(params.getToMemberId(), null, params.getToMemberPrincipal());
        if (toMember == null) {
            throw new EntityNotFoundException(Member.class);
        }

        return confirmPayment(params, toMember);
    }

    /**
     * Confirms a payment to a system account
     */
    @RequestMapping(value = "payments/confirmSystemPayment", method = RequestMethod.POST)
    @ResponseBody
    public ConfirmPaymentResult confirmPaymentToSystem(@RequestBody final DoPaymentParameters params) {
        return confirmPayment(params, SystemAccountOwner.instance());
    }

    /**
     * Performs a payment between accounts
     */
    // TODO not possible now because transfer types with context self payment cannot have channels. Should we change this?
    // @RequestMapping(value = "payments/selfPayment", method = RequestMethod.POST)
    // @ResponseBody
    // public DoPaymentResult doPaymentToSelf(@RequestBody final DoPaymentParameters params) {
    // return doPayment(params, LoggedUser.member());
    // }

    /**
     * Performs a payment to another member
     */
    @RequestMapping(value = "payments/memberPayment", method = RequestMethod.POST)
    @ResponseBody
    public DoPaymentResult doPaymentToMember(@RequestBody final DoMemberPaymentParameters params) {
        AccountOwner to = memberService.loadByIdOrPrincipal(params.getToMemberId(), null, params.getToMemberPrincipal());
        if (to == null) {
            throw new EntityNotFoundException(Member.class);
        }
        return doPayment(params, to);
    }

    /**
     * Performs a payment to a system account
     */
    @RequestMapping(value = "payments/systemPayment", method = RequestMethod.POST)
    @ResponseBody
    public DoPaymentResult doPaymentToSystem(@RequestBody final DoPaymentParameters params) {
        return doPayment(params, SystemAccountOwner.instance());
    }

    /**
     * Returns the necessary information to prepare a payment
     */
    @RequestMapping(value = "payments/paymentData", method = RequestMethod.GET)
    @ResponseBody
    public PaymentDataVO getPaymentData(final TransferTypeSearchParams params) {
        PaymentDataVO paymentData = new PaymentDataVO();
        TransferTypeQuery query = transferTypesRestController.toTransferTypeQuery(params);
        List<TransferType> transferTypes = transferTypeService.search(query);
        List<DetailedTransferTypeVO> transferTypeVOs = new ArrayList<DetailedTransferTypeVO>();
        for (TransferType transferType : transferTypes) {
            transferTypeVOs.add((DetailedTransferTypeVO) transferTypeService.getTransferTypeVO(transferType.getId(), true));
        }
        paymentData.setTransferTypes(transferTypeVOs);
        Map<Long, AccountStatusVO> statuses = new HashMap<Long, AccountStatusVO>();
        for (TransferType tt : transferTypes) {
            if (statuses.get(tt.getFrom().getId()) == null) {
                AccountDTO accountDTO = new AccountDTO(LoggedUser.member(), tt.getFrom());
                statuses.put(tt.getFrom().getId(), accountService.getCurrentAccountStatusVO(accountDTO));
            }
        }
        paymentData.setAccountsStatus(statuses);
        if (params.getDestination() == Destination.MEMBER) {
            Member toMember = null;
            if (params.getToMemberId() != null) {
                toMember = elementService.load(params.getToMemberId());
            } else if (params.getToMemberPrincipal() != null) {
                toMember = elementService.loadByPrincipal(null, params.getToMemberPrincipal());
            }
            paymentData.setToMember(memberService.getMemberVO(toMember, false, false));
        }

        return paymentData;
    }

    /**
     * Handles {@link PaymentException}s
     */
    @ExceptionHandler(PaymentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_FAILURE)
    public ServerErrorVO handlePaymentException(final PaymentException ex) {
        String errorCode = UNKNOWN_PAYMENT_ERROR;
        if (ex instanceof MaxAmountPerDayExceededException) {
            errorCode = MAX_AMOUNT_PER_DAY_EXCEEDED;
        } else if (ex instanceof NotEnoughCreditsException) {
            errorCode = NOT_ENOUGH_CREDITS;
        } else if (ex instanceof TransferMinimumPaymentException) {
            errorCode = TRANSFER_MINIMUM_PAYMENT;
        } else if (ex instanceof UpperCreditLimitReachedException) {
            errorCode = UPPER_CREDIT_LIMIT_REACHED;
        }
        return new ServerErrorVO(errorCode, null);
    }

    public void setAccessRestController(final AccessRestController accessRestController) {
        this.accessRestController = accessRestController;
    }

    public void setAccessService(final AccessService accessService) {
        this.accessService = accessService;
    }

    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    public void setAccountsRestController(final AccountsRestController accountsRestController) {
        this.accountsRestController = accountsRestController;
    }

    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }

    public void setMemberService(final MemberService memberService) {
        this.memberService = memberService;
    }

    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    // Create the result
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    public void setTransferTypesRestController(final TransferTypesRestController transferTypesRestController) {
        this.transferTypesRestController = transferTypesRestController;
    }

    private DoPaymentDTO buildDoPaymentDTO(final DoPaymentParameters params, final AccountOwner toOwner, final TransferType transferType) {
        DoPaymentDTO dto = new DoPaymentDTO();
        dto.setContext(TransactionContext.PAYMENT);
        dto.setChannel(Channel.REST);
        dto.setAmount(params.getAmount());
        dto.setCurrency(null);
        dto.setTo(toOwner);
        dto.setTransferType(transferType);
        dto.setDescription(params.getDescription());
        // dto.setPayments(installments);
        List<PaymentCustomField> allowedFields = paymentCustomFieldService.list(transferType, true);
        final Collection<PaymentCustomFieldValue> customValues = customFieldHelper.toValueCollection(allowedFields, params.getCustomValues());
        dto.setCustomValues(customValues);
        return dto;
    }

    /*
     * 
     * private List<ScheduledPaymentDTO> buildInstallments(final DoPaymentParameters params, final Member loggedMember, final BigDecimal amount, final
     * TransferType transferType) { List<ScheduledPaymentDTO> installments = null; if (params instanceof DoMemberPaymentParameters) {
     * DoMemberPaymentParameters mp = (DoMemberPaymentParameters) params; Integer installmentCount = mp.getInstallments(); if (installmentCount !=
     * null && installmentCount > 0) { Calendar firstDate = mp.getFirstInstallmentDate(); if (firstDate == null) { firstDate = Calendar.getInstance();
     * } ProjectionDTO projection = new ProjectionDTO(); projection.setAmount(amount); projection.setFrom(loggedMember);
     * projection.setRecurrence(TimePeriod.ONE_MONTH); projection.setTransferType(transferType); projection.setFirstExpirationDate(firstDate);
     * projection.setPaymentCount(installmentCount); installments = paymentService.calculatePaymentProjection(projection); } } return installments; }
     */

    private ConfirmPaymentResult confirmPayment(final DoPaymentParameters params, final AccountOwner toOwner) {
        if (params == null) {
            throw new ValidationException();
        }

        Member loggedMember = LoggedUser.member();

        BigDecimal amount = params.getAmount();
        if (amount == null) {
            throw new IllegalArgumentException(INVALID_AMOUNT_ERROR);
        }

        TransferType transferType = resolveTransferType(params, toOwner);

        // Calculate the installments
        // List<ScheduledPaymentDTO> installments = buildInstallments(params, amount, transferType);

        // Check the transaction password, if needed
        if (accessRestController.isRequireTransactionPassword()) {
            String transactionPassword = params.getTransactionPassword();
            TransactionPasswordStatus status = loggedMember.getUser().getTransactionPasswordStatus();
            if (status == null || status == TransactionPasswordStatus.PENDING || status == TransactionPasswordStatus.NEVER_CREATED) {
                throw new IllegalArgumentException(INACTIVE_TRANSACTION_PASSWORD_ERROR);
            }
            if (StringUtils.isEmpty(transactionPassword)) {
                throw new IllegalArgumentException(MISSING_TRANSACTION_PASSWORD_ERROR);
            }
            try {
                accessService.checkTransactionPassword(transactionPassword);
            } catch (InvalidCredentialsException e) {
                throw new IllegalArgumentException(INVALID_TRANSACTION_PASSWORD_ERROR);
            } catch (BlockedCredentialsException e) {
                throw new IllegalArgumentException(BLOCKED_TRANSACTION_PASSWORD_ERROR);
            }
        }

        // Create the DoPaymentDTO
        DoPaymentDTO dto = buildDoPaymentDTO(params, toOwner, transferType);

        // Perform the payment

        Payment payment = paymentService.doPayment(dto);

        // Create the result
        ConfirmPaymentResult result = new ConfirmPaymentResult();
        result.setId(payment.getId());
        result.setPending(payment.getProcessDate() == null);
        return result;
    }

    private DoPaymentResult doPayment(final DoPaymentParameters params, final AccountOwner to) {
        DoPaymentResult result = new DoPaymentResult();

        if (params == null) {
            throw new ValidationException();
        }

        AccountOwner from = LoggedUser.accountOwner();

        TransferType transferType = resolveTransferType(params, to);
        transferType = transferTypeService.load(transferType.getId(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY), TransferType.Relationships.TO);

        DoPaymentDTO doPaymentDTO = buildDoPaymentDTO(params, to, transferType);
        paymentService.validate(doPaymentDTO);

        result.setTransferType(transferTypeService.getTransferTypeVO(transferType.getId(), false));
        final boolean wouldRequireAuthorization = paymentService.wouldRequireAuthorization(transferType, params.getAmount(), LoggedUser.accountOwner());
        result.setWouldRequireAuthorization(wouldRequireAuthorization);

        // TODO support past dates
        // if (wouldRequireAuthorization && params.getDate() != null) {
        // throw new ValidationException("payment.error.authorizedInPast");
        // }

        // Fetch related data

        result.setFrom(memberService.getMemberVO((Member) from, false, false));
        if (to != null && to instanceof Member) {
            result.setTo(memberService.getMemberVO((Member) to, false, false));
        }

        // Store the transaction fees
        final TransactionFeePreviewDTO preview = transactionFeeService.preview(from, to, transferType, params.getAmount());
        result.setFinalAmount(preview.getFinalAmount());

        final LocalSettings localSettings = settingsService.getLocalSettings();
        String formattedAmount = localSettings.getUnitsConverter(transferType.getCurrency().getPattern()).toString(preview.getFinalAmount());

        result.setFees(transactionFeeService.getTransactionFeeVOs(preview));
        result.setFormattedFinalAmount(formattedAmount);

        // TODO: support scheduled payments
        // Calculate the transaction fees for every scheduled payment
        /*
         * final List<ScheduledPaymentDTO> payments = payment.getPayments(); final boolean isScheduled = CollectionUtils.isNotEmpty(payments); if
         * (isScheduled) { for (final ScheduledPaymentDTO current : payments) { final TransactionFeePreviewDTO currentPreview =
         * transactionFeeService.preview(from, to, transferType, current.getAmount()); current.setFinalAmount(currentPreview.getFinalAmount()); } }
         * request.setAttribute("isScheduled", isScheduled);
         */

        // Add the custom values to the result.
        if (doPaymentDTO.getCustomValues() != null) {
            Map<String, String> customValues = new LinkedHashMap<String, String>();
            for (PaymentCustomFieldValue cfv : doPaymentDTO.getCustomValues()) {
                customValues.put(cfv.getField().getName(), cfv.getValue());
            }
            result.setCustomValues(customValues);
        }

        return result;

    }

    private TransferType resolveTransferType(final DoPaymentParameters params, final AccountOwner toOwner) {
        Member loggedMember = LoggedUser.member();
        Currency currency = accountsRestController.loadCurrencyByIdOrSymbol(params.getCurrencyId(), params.getCurrencySymbol());
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setResultType(ResultType.LIST);
        query.setContext(loggedMember.equals(toOwner) ? TransactionContext.SELF_PAYMENT : TransactionContext.PAYMENT);
        query.setChannel(Channel.REST);
        query.setFromOwner(loggedMember);
        query.setToOwner(toOwner);
        query.setCurrency(currency);
        List<TransferType> possibleTransferTypes = transferTypeService.search(query);
        if (possibleTransferTypes.isEmpty()) {
            throw new IllegalArgumentException(NO_POSSIBLE_TRANSFER_TYPES_ERROR);
        }

        // Resolve the transfer type
        Long transferTypeId = params.getTransferTypeId();
        TransferType transferType = null;
        if (transferTypeId != null) {
            for (TransferType tt : possibleTransferTypes) {
                if (tt.getId().equals(transferTypeId)) {
                    transferType = tt;
                    break;
                }
            }
        } else {
            // When there are multiple transfer types, prefer the first one from the default account
            if (possibleTransferTypes.size() > 1) {
                MemberAccountType defaultType = accountTypeService.getDefault(loggedMember.getMemberGroup());
                for (TransferType current : possibleTransferTypes) {
                    if (current.getFrom().equals(defaultType)) {
                        transferType = current;
                        break;
                    }
                }
            }
            // No TT found so far. Get the first one
            if (transferType == null) {
                transferType = possibleTransferTypes.isEmpty() ? null : possibleTransferTypes.get(0);
            }
        }
        if (transferType == null) {
            throw new EntityNotFoundException(TransferType.class);
        }
        return transferType;
    }

}
