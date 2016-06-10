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
import java.math.MathContext;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.accounts.fee.transaction.TransactionFeeDAO;
import nl.strohalm.cyclos.dao.members.brokerings.BrokeringCommissionStatusDAO;
import nl.strohalm.cyclos.dao.members.brokerings.DefaultBrokerCommissionDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.When;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.WhichBroker;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee.ARateRelation;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.ChargeType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Nature;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Subject;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatus;
import nl.strohalm.cyclos.entities.members.brokerings.BrokeringCommissionStatusQuery;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommissionQuery;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.ARatedFeeDTO;
import nl.strohalm.cyclos.services.accounts.rates.RateServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.RatesDTO;
import nl.strohalm.cyclos.services.accounts.rates.RatesPreviewDTO;
import nl.strohalm.cyclos.services.accounts.rates.RatesResultDTO;
import nl.strohalm.cyclos.services.elements.BrokeringServiceLocal;
import nl.strohalm.cyclos.services.elements.CommissionServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.utils.validation.Validator.Property;
import nl.strohalm.cyclos.webservices.model.TransactionFeeVO;
import nl.strohalm.cyclos.webservices.utils.PaymentHelper;

/**
 * Implementation class for transaction fee service
 * @author Jefferson Magno
 * @author rafael
 * @author Rinke (everything with rates)
 */
public class TransactionFeeServiceImpl implements TransactionFeeServiceLocal {

    /**
     * Count is required when "When" property is COUNT or DAYS
     * @author Jefferson Magno
     */
    public class CountValidation implements PropertyValidation {
        private static final long serialVersionUID = -8075223146818925038L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final BrokerCommission commission = (BrokerCommission) object;
            final When when = commission.getWhen();
            if (when == When.COUNT || when == When.DAYS) {
                return RequiredValidation.instance().validate(object, property, value);
            }
            return null;
        }
    }

    /**
     * Validates the generated transfer type
     * @author Jefferson Magno
     */
    public class GeneratedTransferTypeValidation implements GeneralValidation {
        private static final long serialVersionUID = 1616929350799341483L;

        @Override
        public ValidationError validate(final Object object) {
            final TransactionFee fee = (TransactionFee) object;
            final TransferType generatedType = fetchService.fetch(fee.getGeneratedTransferType(), TransferType.Relationships.FROM);
            fee.setGeneratedTransferType(generatedType);

            // This validation is necessary only when updating a transaction fee
            if (fee.isTransient()) {
                return null;
            }

            // Retrieve saved fee and it's generated transfer type
            final TransactionFee savedFee = load(fee.getId(), TransactionFee.Relationships.GENERATED_TRANSFER_TYPE);
            final TransferType savedGeneratedType = savedFee.getGeneratedTransferType();

            // If it's a broker commission paid by member, the generated type cannot be changed
            if (savedFee.getNature() == Nature.BROKER && !savedFee.isFromSystem()) {
                if (!generatedType.equals(savedGeneratedType)) {
                    return new ValidationError("transactionFee.error.cannotChangeGeneratedType");
                }
            }

            // It's not allowed to change a "from system" generated type to a "from member" generated type
            if (savedFee.isFromSystem() && !fee.isFromSystem()) {
                return new ValidationError("transactionFee.erro.fromSystemGeneratedTypeRequired");
            }

            return null;
        }
    }

    private static class PayerAndReceiverValidation implements GeneralValidation {

        private static final long serialVersionUID = -1969165853079125625L;

        @Override
        public ValidationError validate(final Object object) {
            final SimpleTransactionFee fee = (SimpleTransactionFee) object;
            final Subject payer = fee.getPayer();
            final Subject receiver = fee.getReceiver();
            if ((payer == Subject.SOURCE || payer == Subject.DESTINATION) && payer == receiver) {
                return new ValidationError("transactionFee.error.samePayerAndReceiver");
            }
            return null;
        }
    }

    private MessageResolver              messageResolver;

    private AccountServiceLocal          accountService;

    private RateServiceLocal             rateService;
    private BrokeringServiceLocal        brokeringService;
    private CommissionServiceLocal       commissionService;
    private FetchServiceLocal            fetchService;
    private SettingsServiceLocal         settingsService;
    private TransferTypeServiceLocal     transferTypeService;
    private BrokeringCommissionStatusDAO brokeringCommissionStatusDao;
    private DefaultBrokerCommissionDAO   defaultBrokerCommissionDao;
    private TransactionFeeDAO            transactionFeeDao;
    private PaymentHelper                paymentHelper;

    @Override
    public Transfer buildTransfer(final BuildTransferWithFeesDTO params) {
        final Calendar date = params.getDate();
        final Account from = params.getFrom();
        final Account to = params.getTo();
        final BigDecimal transferAmount = params.getTransferAmount();
        TransactionFee fee = params.getFee();
        final boolean simulation = params.isSimulation();
        final Calendar rawARateParam = params.getEmissionDate();
        final Calendar rawDRateParam = params.getExpirationDate();

        if (fee.isTransient()) {
            throw new UnexpectedEntityException();
        }
        fee = fetchService.fetch(fee, TransactionFee.Relationships.ORIGINAL_TRANSFER_TYPE, RelationshipHelper.nested(TransactionFee.Relationships.GENERATED_TRANSFER_TYPE, TransferType.Relationships.FROM, TransferType.Relationships.TO, AccountType.Relationships.CURRENCY), TransactionFee.Relationships.FROM_GROUPS, TransactionFee.Relationships.TO_GROUPS, TransactionFee.Relationships.FROM_FIXED_MEMBER, SimpleTransactionFee.Relationships.TO_FIXED_MEMBER);

        if (!doTests(fee, transferAmount, from, to)) {
            return null;
        }

        BrokerCommissionContract commissionContract = null;

        ChargeType feeChargeType = fee.getChargeType();
        BigDecimal feeValue = fee.getValue();
        final TransferType generated = fee.getGeneratedTransferType();

        final AccountOwner fromOwner = getOwner(fee.getPayer(), fee.getFromFixedMember(), from, to);

        // Get the to owner
        AccountOwner toOwner = null;
        if (fee.getNature() == Nature.SIMPLE) {
            SimpleTransactionFee simpleFee = (SimpleTransactionFee) fee;
            toOwner = getOwner(simpleFee.getReceiver(), simpleFee.getToFixedMember(), from, to);
        } else { // It´s a broker commission
            // TODO this is an extremely long method. What about putting this broker part in a separate method?
            final BrokerCommission brokerCommission = (BrokerCommission) fee;

            // Check if the broker is the source´s broker or is destination´s broker
            Account relatedAccount = null;
            switch (brokerCommission.getWhichBroker()) {
                case SOURCE:
                    relatedAccount = from;
                    break;
                case DESTINATION:
                    relatedAccount = to;
                    break;
            }

            // When source member pays destination´s broker or when destination member pays source´s broker, it´s a cross payment
            boolean crossPayment = false;
            if ((fee.getPayer() == TransactionFee.Subject.SOURCE && brokerCommission.getWhichBroker() == BrokerCommission.WhichBroker.DESTINATION) || (fee.getPayer() == TransactionFee.Subject.DESTINATION && brokerCommission.getWhichBroker() == BrokerCommission.WhichBroker.SOURCE)) {
                crossPayment = true;
            }

            // The toOwner is a member's broker
            if (relatedAccount instanceof MemberAccount) {
                final Member member = ((MemberAccount) relatedAccount).getMember();
                toOwner = member.getBroker();

                // For a broker commission, first, get the brokering
                final Brokering brokering = brokeringService.getActiveBrokering(member);
                // There is no active brokering, return null
                if (brokering == null) {
                    return null;
                }
                final Member broker = brokering.getBroker();

                // Check broker groups
                if (!brokerCommission.isAllBrokerGroups()) {
                    final BrokerGroup brokerGroup = (BrokerGroup) fetchService.fetch(brokering.getBroker().getGroup());
                    if (!brokerCommission.getBrokerGroups().contains(brokerGroup)) {
                        return null;
                    }
                }

                // When the broker commission status is created, it's data is gotten from the default broker commission (commissions
                // paid by member) or from the broker commission (commissions paid by system)
                BrokeringCommissionStatus brokeringCommissionStatus = commissionService.getBrokeringCommissionStatus(brokering, brokerCommission);

                boolean testBrokeringCommissionStatus = false;
                When when = null;
                int maxCount = 0;
                Amount feeAmount;
                // Member paying commission to one broker
                final boolean fromMember = generated.isFromMember();
                if (fromMember) {
                    if (crossPayment) {
                        // On cross payments, the commission is always charged and the fee is retrieved from the commission it self
                        // The brokering commission status conditions are never tested
                        feeAmount = brokerCommission.getAmount();
                    } else {
                        commissionContract = commissionService.getActiveBrokerCommissionContract(brokering, brokerCommission);
                        if (commissionContract != null) {
                            // There is an active broker contract, the fee is retrieved from the contract
                            feeAmount = commissionContract.getAmount();
                        } else if (brokeringCommissionStatus != null) {
                            // There is a default broker commission, the fee is retrieved from the brokering commission status
                            if (brokeringCommissionStatus.getPeriod().getEnd() != null) {
                                return null;
                            }
                            // Parameters for testing broker commission status conditions
                            testBrokeringCommissionStatus = true;
                            when = brokeringCommissionStatus.getWhen();
                            if (when != BrokerCommission.When.ALWAYS) {
                                maxCount = brokeringCommissionStatus.getMaxCount();
                            }
                            feeAmount = brokeringCommissionStatus.getAmount();
                        } else {
                            // If there is not a contract and there is not a default broker commission, no commission will be charged
                            return null;
                        }
                    }
                } else {
                    // System paying a commission to a broker
                    // Parameters for testing brokering commission status conditions
                    testBrokeringCommissionStatus = true;
                    when = brokerCommission.getWhen();
                    if (when != BrokerCommission.When.ALWAYS) {
                        maxCount = brokerCommission.getCount();
                    }
                    feeAmount = brokeringCommissionStatus.getAmount();
                }

                // Set the charge data
                feeChargeType = ChargeType.from(feeAmount.getType());
                feeValue = feeAmount.getValue();

                // Test brokering commission status conditions (suspended, number of transactions or period of validity)
                if (testBrokeringCommissionStatus) {
                    // When member's pay, a DefaultBrokerCommission is required
                    if (fromMember) {
                        // The broker commission is suspended, don´t charge the commission
                        final DefaultBrokerCommission defaultBrokerCommission = commissionService.getDefaultBrokerCommission(broker, brokerCommission);
                        if (defaultBrokerCommission != null && defaultBrokerCommission.isSuspended()) {
                            return null;
                        }
                    }
                    // The brokering commission status is closed, don't charge commission anymore
                    if (brokeringCommissionStatus.getPeriod().getEnd() != null) {
                        return null;
                    }
                    if (when == BrokerCommission.When.COUNT) {
                        final int count = brokeringCommissionStatus.getTotal().getCount();
                        // Number of transactions exceeded, don't charge commission anymore
                        if (count >= maxCount) {
                            return null;
                        }
                        // This is the last transaction that generates the fee, so it's necessary to close the brokering commission status
                        if (count == (maxCount - 1) && !simulation) {
                            brokeringCommissionStatus = commissionService.closeBrokeringCommissionStatus(brokeringCommissionStatus);
                        }
                    }
                    if (when == BrokerCommission.When.DAYS) {
                        // Pays if the current day is not beyond max. day stored in the fee or default broker commission
                        final Calendar begin = brokeringCommissionStatus.getPeriod().getBegin();
                        final Calendar maxDay = new TimePeriod(maxCount, Field.DAYS).add(begin);
                        // Period expired, dont charge commission anymore
                        if (Calendar.getInstance().after(maxDay)) {
                            return null;
                        }
                    }
                }

                if (!simulation && !crossPayment && commissionContract == null) {
                    // Update total count and total amount of the brokering commission status
                    brokeringCommissionStatus.setTotal(brokeringCommissionStatus.getTotal().add(transferAmount));
                    commissionService.updateBrokeringCommissionStatus(brokeringCommissionStatus);
                }
            }
        }// END OF BROKER PART

        // Check if we found the from and to owner
        if (fromOwner == null || toOwner == null) {
            return null;
        }
        if (fromOwner instanceof Member && fromOwner.equals(toOwner)) {
            // If the combination resulted in the same member as from and to, don't generate the fee
            return null;
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Get the accounts
        Account fromAccount;
        Account toAccount;
        try {
            fromAccount = accountService.getAccount(new AccountDTO(fromOwner, generated.getFrom()));
            toAccount = accountService.getAccount(new AccountDTO(toOwner, generated.getTo()));
        } catch (EntityNotFoundException e) {
            // If any of the expected accounts are not found, the fee is not generated
            return null;
        }
        if (fromAccount.equals(toAccount)) {
            // Same accounts? Don't charge the fee
            return null;
        }

        // Calculate the fee amount
        BigDecimal amount = BigDecimal.ZERO;
        final Currency currency = fromAccount.getType().getCurrency();

        switch (feeChargeType) {
            case FIXED:
                amount = feeValue;
                break;
            case PERCENTAGE:
                amount = Amount.percentage(feeValue).apply(transferAmount);
                break;
            case A_RATE:
            case MIXED_A_D_RATES:
                if (rawARateParam != null) {
                    feeValue = rateService.getARatedFeePercentage(fee, rawARateParam, rawDRateParam, date);
                    fee.setAmountForRates(Amount.percentage(feeValue));
                    amount = Amount.percentage(feeValue).apply(transferAmount);
                }
                break;
            case D_RATE:
                if (rawDRateParam != null) {
                    final RatesDTO setOfUnits = RatesDTO.createSetOfUnitsForMerge(transferAmount, rawDRateParam, currency);
                    setOfUnits.setDate(params.getDate());
                    final BigDecimal result = rateService.getDRateConversionResult(setOfUnits);
                    amount = transferAmount.subtract(result);
                    final MathContext mc = new MathContext(LocalSettings.BIG_DECIMAL_DIVISION_PRECISION);
                    feeValue = amount.multiply(new BigDecimal(100)).divide(transferAmount, mc);
                    fee.setAmountForRates(Amount.percentage(feeValue));
                }
                break;
        }
        // A very small fee which was rounded to zero
        amount = localSettings.round(amount);
        if (!params.isShowZeroFees() && amount.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        // Get the description variables
        final String description = fee.getGeneratedTransferType().getDescription();
        final UnitsConverter unitsConverter = localSettings.getUnitsConverter(generated.getFrom().getCurrency().getPattern());
        final NumberConverter<BigDecimal> numberConverter = localSettings.getNumberConverter();
        final Map<String, Object> values = new HashMap<String, Object>();
        values.put("amount", unitsConverter.toString(amount));
        values.put("transfer", unitsConverter.toString(transferAmount));
        values.put("original_amount", values.get("transfer")); // Aliasing to keep old transfer
        switch (feeChargeType) {
            case FIXED:
                values.put("fee", unitsConverter.toString(feeValue));
                break;
            case PERCENTAGE:
                values.put("fee", numberConverter.toString(feeValue) + "%");
                break;
            case A_RATE:
                values.put("fee", numberConverter.toString(feeValue) + "%");
                BigDecimal aRate = getRoundedARate(date, rawARateParam, localSettings);
                values.put("a_rate", aRate);
                break;
            case MIXED_A_D_RATES:
                values.put("fee", numberConverter.toString(feeValue) + "%");
                BigDecimal aRate1 = getRoundedARate(date, rawARateParam, localSettings);
                values.put("a_rate", aRate1);
                // break was left out deliberately
            case D_RATE:
                RatesDTO ratesDTO = new RatesDTO();
                ratesDTO.setExpirationDate(rawDRateParam);
                ratesDTO.setDate(date);
                ratesDTO.setCurrency(currency);
                BigDecimal dRate = rateService.dateToRate(ratesDTO).getdRate();
                dRate = localSettings.round(dRate);
                values.put("d_rate", dRate);
                break;
        }
        values.put("fee_amount", values.get("fee")); // Aliasing to keep old transfer
        values.put("member", fromAccount.getOwnerName());

        // Build the transfer
        final Transfer feeTransfer = new Transfer();
        feeTransfer.setFrom(fromAccount);
        feeTransfer.setTo(toAccount);
        feeTransfer.setAmount(amount);
        feeTransfer.setDescription(MessageProcessingHelper.processVariables(description, values));
        feeTransfer.setType(generated);
        feeTransfer.setTransactionFee(fee);
        if (commissionContract != null) {
            feeTransfer.setBrokerCommissionContract(commissionContract);
        }
        return feeTransfer;
    }

    @Override
    public Collection<ChargeType> getPossibleChargeType(final TransferType originalTransferType, final Nature feeNature) {
        Collection<ChargeType> chargeTypes = Arrays.asList(ChargeType.FIXED, ChargeType.PERCENTAGE);
        TransferType transferType = fetchService.fetch(originalTransferType, TransferType.Relationships.FROM, TransferType.Relationships.TO);
        if (feeNature == TransactionFee.Nature.SIMPLE) {
            // Rates are allowed only in payments from member to unlimited system accounts
            if (transferType.isFromMember() && transferType.isToSystem() && !transferType.getTo().isLimited()) {
                final Currency currency = transferType.getCurrency();
                final boolean allowARate = currency.isEnableARate();
                final boolean allowDRate = currency.isEnableDRate();
                chargeTypes = EnumSet.allOf(ChargeType.class);
                if (!allowARate) {
                    chargeTypes.remove(ChargeType.A_RATE);
                    chargeTypes.remove(ChargeType.MIXED_A_D_RATES);
                }
                if (!allowDRate) {
                    chargeTypes.remove(ChargeType.D_RATE);
                }
            }
        }
        return chargeTypes;
    }

    @Override
    public Collection<Subject> getPossibleSubjects(TransferType originalTransferType, final Nature nature) {
        originalTransferType = fetchService.fetch(originalTransferType, TransferType.Relationships.FROM, TransferType.Relationships.TO);
        switch (nature) {
            case SIMPLE:
                if (originalTransferType.isFromSystem()) {
                    if (originalTransferType.isToSystem()) {
                        // System to system may only have system as payer or receiver
                        return Collections.singleton(Subject.SYSTEM);
                    } else {
                        // System to member may have system or destination as payer or receiver
                        return EnumSet.of(Subject.SYSTEM, Subject.DESTINATION, Subject.DESTINATION_BROKER, Subject.FIXED_MEMBER);
                    }
                } else {
                    if (originalTransferType.isToSystem()) {
                        // Member to member may have source or system as payer or receiver
                        return EnumSet.of(Subject.SYSTEM, Subject.SOURCE, Subject.SOURCE_BROKER, Subject.FIXED_MEMBER);
                    } else {
                        // Member to member may be any
                        return EnumSet.allOf(Subject.class);
                    }
                }
            case BROKER:
                // BrokerCommissions can't be fixed member
                return EnumSet.of(Subject.SYSTEM, Subject.SOURCE, Subject.DESTINATION);

            default:
                throw new IllegalArgumentException("Unexpected transaction fee nature: " + nature);
        }
    }

    @Override
    public List<TransactionFeeVO> getTransactionFeeVOs(final TransactionFeePreviewDTO preview) {
        Map<TransactionFee, BigDecimal> fees = preview.getFees();
        return paymentHelper.toTransactionFeeVOs(fees);
    }

    @Override
    public TransactionFee load(final Long id, final Relationship... fetch) {
        return transactionFeeDao.load(id, fetch);
    }

    @Override
    public TransactionFeePreviewDTO preview(final AccountOwner from, final AccountOwner to, TransferType transferType, final BigDecimal amount) {
        RatesResultDTO rates = new RatesResultDTO();
        transferType = fetchService.fetch(transferType);
        // if the TT has rated fees, the rates MUST be provided.
        if (transferType.isHavingRatedFees()) {
            final Account fromAccount = accountService.getAccount(new AccountDTO(from, transferType.getFrom()));
            rates = rateService.getRatesForTransferFrom(fromAccount, amount, null);
        }
        return preview(from, to, transferType, amount, rates);
    }

    @Override
    public TransactionFeePreviewDTO preview(AccountOwner from, final AccountOwner to, TransferType transferType, final BigDecimal amount, final RatesResultDTO rates) {
        Calendar rawARate = rates.getEmissionDate();
        Calendar rawDRate = rates.getExpirationDate();
        final LocalSettings localSettings = settingsService.getLocalSettings();
        if (from == null) {
            from = LoggedUser.accountOwner();
        }
        boolean showZeroFees = (rates instanceof RatesPreviewDTO && ((RatesPreviewDTO) rates).isGraph());
        final Account fromAccount = accountService.getAccount(new AccountDTO(from, transferType.getFrom()));
        final Account toAccount = accountService.getAccount(new AccountDTO(to, transferType.getTo()));
        BigDecimal finalAmount = amount;
        final Map<TransactionFee, BigDecimal> map = new LinkedHashMap<TransactionFee, BigDecimal>();
        transferType = fetchService.fetch(transferType, TransferType.Relationships.TRANSACTION_FEES);
        final Collection<? extends TransactionFee> fees = transferType.getTransactionFees();
        final Calendar processDate = (rates != null && rates.getDate() != null) ? rates.getDate() : Calendar.getInstance();
        if (fees != null && !fees.isEmpty()) {
            // Search for enabled fees that the source pays
            for (final TransactionFee fee : fees) {
                // We just want fees the source member would pay
                if (!shouldPreviewFee(from, to, amount, fee)) {
                    continue;
                }
                // The buildTransfer() method returns a transfer if the fee should be applied or null
                final BuildTransferWithFeesDTO buildParams = new BuildTransferWithFeesDTO(processDate, fromAccount, toAccount, amount, fee, true);
                buildParams.setEmissionDate(rawARate);
                buildParams.setExpirationDate(rawDRate);
                buildParams.setShowZeroFees(showZeroFees);
                final Transfer generatedTransfer = buildTransfer(buildParams);
                if (generatedTransfer != null) {
                    final BigDecimal feeAmount = generatedTransfer.getAmount();
                    map.put(fee, feeAmount);
                    // Check deducted amount
                    if (fee.isDeductAmount()) {
                        finalAmount = finalAmount.subtract(feeAmount);
                    }
                }
            }
        }
        final TransactionFeePreviewForRatesDTO result = new TransactionFeePreviewForRatesDTO();
        result.setFees(map);
        result.setFinalAmount(localSettings.round(finalAmount));
        result.setAmount(localSettings.round(amount));
        result.setARate(rates.getaRate());
        result.setDRate(rates.getdRate());
        return result;
    }

    @Override
    public TransactionFeePreviewDTO preview(final Invoice invoice) {
        TransferType transferType = invoice.getTransferType();
        if (transferType == null) {
            return null;
        }
        return preview(invoice.getTo(), invoice.getFrom(), transferType, invoice.getAmount());
    }

    @Override
    public int remove(final Long... ids) {
        for (final Long id : ids) {
            final TransactionFee transactionFee = load(id);
            if (transactionFee instanceof BrokerCommission) {
                // Before removing the broker commission, check if it was already charged ...
                final BrokerCommission brokerCommission = (BrokerCommission) transactionFee;
                BrokeringCommissionStatusQuery query = new BrokeringCommissionStatusQuery();
                query.setBrokerCommission(brokerCommission);
                query.setAlreadyCharged(true);
                query.setPageForCount();
                List<BrokeringCommissionStatus> brokeringCommissionStatusList = brokeringCommissionStatusDao.search(query);
                // ... if it was already charged, it´s not possible to remove the broker commission, so throw an exception
                if (PageHelper.getTotalCount(brokeringCommissionStatusList) > 0) {
                    throw new DaoException();
                }
                // ... if it was not charged yet, remove the brokering commission status objects related to it
                query = new BrokeringCommissionStatusQuery();
                query.setBrokerCommission(brokerCommission);
                brokeringCommissionStatusList = brokeringCommissionStatusDao.search(query);
                for (final BrokeringCommissionStatus brokeringCommissionStatus : brokeringCommissionStatusList) {
                    brokeringCommissionStatusDao.delete(brokeringCommissionStatus.getId());
                }

                // Check if the commission was already customized by the broker ...
                DefaultBrokerCommissionQuery defaultBrokerCommissionQuery = new DefaultBrokerCommissionQuery();
                defaultBrokerCommissionQuery.setBrokerCommission(brokerCommission);
                defaultBrokerCommissionQuery.setSetByBroker(true);
                defaultBrokerCommissionQuery.setPageForCount();
                List<DefaultBrokerCommission> defaultBrokerCommissions = defaultBrokerCommissionDao.search(defaultBrokerCommissionQuery);
                // ... if it was already customized, it´s not possible to remove the broker commission, so throw an exception
                if (PageHelper.getTotalCount(defaultBrokerCommissions) > 0) {
                    throw new DaoException();
                }
                // ... if it was not customized yet, remove the default broker commissions related to it
                defaultBrokerCommissionQuery = new DefaultBrokerCommissionQuery();
                defaultBrokerCommissionQuery.setBrokerCommission(brokerCommission);
                defaultBrokerCommissions = defaultBrokerCommissionDao.search(defaultBrokerCommissionQuery);
                for (final DefaultBrokerCommission defaultBrokerCommission : defaultBrokerCommissions) {
                    defaultBrokerCommissionDao.delete(defaultBrokerCommission.getId());
                }
            }
        }
        return transactionFeeDao.delete(ids);
    }

    @Override
    public BrokerCommission save(BrokerCommission brokerCommission) {
        preSave(brokerCommission);
        if (brokerCommission.isAllBrokerGroups()) {
            brokerCommission.setBrokerGroups(null);
        }

        validate(brokerCommission);

        if (brokerCommission.isTransient()) {
            brokerCommission = transactionFeeDao.insert(brokerCommission);
            if (brokerCommission.isFromMember()) {
                commissionService.createDefaultBrokerCommissions(brokerCommission);
            } else { // Broker commission paid by system
                commissionService.createBrokeringCommissionStatus(brokerCommission);
            }
        } else {
            final BrokerCommission savedBrokerCommission = transactionFeeDao.load(brokerCommission.getId(), BrokerCommission.Relationships.BROKER_GROUPS);
            fetchService.removeFromCache(savedBrokerCommission);
            brokerCommission = transactionFeeDao.update(brokerCommission);
            if (brokerCommission.isFromMember()) {
                commissionService.updateDefaultBrokerCommissions(brokerCommission, savedBrokerCommission);
            } else { // Broker commission paid by system
                commissionService.updateBrokeringCommissionStatus(brokerCommission, savedBrokerCommission);
            }
        }
        return brokerCommission;
    }

    @Override
    public SimpleTransactionFee save(SimpleTransactionFee fee, final ARateRelation aRateRelation) {
        preSave(fee);

        // Ensure deduct amount is set for rates
        if (Arrays.asList(ChargeType.A_RATE, ChargeType.D_RATE, ChargeType.MIXED_A_D_RATES).contains(fee.getChargeType())) {
            fee.setDeductAmount(true);
        }

        validate(fee, aRateRelation);
        if (fee.getReceiver() != Subject.FIXED_MEMBER) {
            fee.setToFixedMember(null);
        }
        if (fee.isTransient()) {
            fee = transactionFeeDao.insert(fee);
        } else {
            fee = transactionFeeDao.update(fee);
        }
        return fee;
    }

    @Override
    public List<TransactionFee> search(final TransactionFeeQuery query) {
        return transactionFeeDao.search(query);
    }

    @Override
    public List<TransferType> searchGeneratedTransferTypes(final TransactionFee fee, final boolean allowAnyAccount, final boolean onlyFromSystem) {
        TransferTypeQuery generatedQuery = buildGeneratedTypeQuery(fee, allowAnyAccount);
        if (onlyFromSystem) {
            generatedQuery.setFromNature(AccountType.Nature.SYSTEM);
        }
        return transferTypeService.search(generatedQuery);
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setBrokeringCommissionStatusDao(final BrokeringCommissionStatusDAO brokeringCommissionStatusDao) {
        this.brokeringCommissionStatusDao = brokeringCommissionStatusDao;
    }

    public void setBrokeringServiceLocal(final BrokeringServiceLocal brokeringService) {
        this.brokeringService = brokeringService;
    }

    public void setCommissionServiceLocal(final CommissionServiceLocal commissionService) {
        this.commissionService = commissionService;
    }

    public void setDefaultBrokerCommissionDao(final DefaultBrokerCommissionDAO defaultBrokerCommissionDao) {
        this.defaultBrokerCommissionDao = defaultBrokerCommissionDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setPaymentHelper(final PaymentHelper paymentHelper) {
        this.paymentHelper = paymentHelper;
    }

    public void setRateServiceLocal(final RateServiceLocal rateService) {
        this.rateService = rateService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionFeeDao(final TransactionFeeDAO dao) {
        transactionFeeDao = dao;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final BrokerCommission brokerCommission) {
        getValidator(brokerCommission).validate(brokerCommission);
    }

    @Override
    public void validate(final SimpleTransactionFee transactionFee, final ARateRelation aRateRelation) {
        getValidator(transactionFee, aRateRelation).validate(transactionFee);
    }

    /**
     * Checks whether the given fee will be shown on the preview
     */
    protected boolean shouldPreviewFee(final AccountOwner from, final AccountOwner to, final BigDecimal amount, final TransactionFee fee) {
        if (fee.getPayer() == Subject.SOURCE || from.equals(fee.getFromFixedMember())) {
            return true;
        } else if (fee.getPayer() == Subject.SYSTEM && (from instanceof SystemAccountOwner)) {
            return true;
        }
        return false;
    }

    private Member brokerOf(final AccountOwner owner) {
        if (owner instanceof Member) {
            return ((Member) owner).getBroker();
        }
        return null;
    }

    private TransferTypeQuery buildGeneratedTypeQuery(final TransactionFee fee, final boolean allowAnyAccount) {
        final TransferType originalTransferType = fetchService.fetch(fee.getOriginalTransferType(), TransferType.Relationships.FROM, TransferType.Relationships.TO);
        final AccountType fromAccountType = originalTransferType.getFrom();
        final AccountType toAccountType = originalTransferType.getTo();

        final TransferTypeQuery generatedQuery = new TransferTypeQuery();
        generatedQuery.setContext(TransactionContext.ANY);

        // Get the from account
        switch (fee.getPayer()) {
            case SYSTEM:
                generatedQuery.setFromNature(AccountType.Nature.SYSTEM);
                break;
            case SOURCE:
                if (allowAnyAccount) {
                    generatedQuery.setFromNature(fromAccountType.getNature());
                } else {
                    generatedQuery.setFromAccountType(fromAccountType);
                }
                break;
            case DESTINATION:
                if (allowAnyAccount) {
                    generatedQuery.setFromNature(toAccountType.getNature());
                } else {
                    generatedQuery.setFromAccountType(toAccountType);
                }
                break;
            case FIXED_MEMBER:
                if (allowAnyAccount) {
                    generatedQuery.setFromNature(AccountType.Nature.MEMBER);
                } else {
                    generatedQuery.setFromAccountType(fromAccountType);
                }
                break;
            case SOURCE_BROKER:
            case DESTINATION_BROKER:
                // There's no way to know the accounts of the broker
                generatedQuery.setFromNature(AccountType.Nature.MEMBER);
                break;
        }

        // The to account depends on the nature
        switch (fee.getNature()) {
            case SIMPLE:
                switch (((SimpleTransactionFee) fee).getReceiver()) {
                    case SYSTEM:
                        generatedQuery.setToNature(AccountType.Nature.SYSTEM);
                        break;
                    case SOURCE:
                        if (allowAnyAccount) {
                            generatedQuery.setToNature(fromAccountType.getNature());
                        } else {
                            generatedQuery.setToAccountType(fromAccountType);
                        }
                        break;
                    case DESTINATION:
                        if (allowAnyAccount) {
                            generatedQuery.setToNature(toAccountType.getNature());
                        } else {
                            generatedQuery.setToAccountType(toAccountType);
                        }
                        break;
                    case FIXED_MEMBER:
                        if (allowAnyAccount) {
                            generatedQuery.setToNature(AccountType.Nature.MEMBER);
                        } else {
                            generatedQuery.setToAccountType(toAccountType);
                        }
                        break;
                    case SOURCE_BROKER:
                    case DESTINATION_BROKER:
                        // There's no way to know the accounts of the broker
                        generatedQuery.setToNature(AccountType.Nature.MEMBER);
                        break;
                }
                break;
            case BROKER:
                generatedQuery.setToNature(AccountType.Nature.MEMBER);
                break;
        }
        return generatedQuery;
    }

    /**
     * performs all initial tests for buildTransfer method
     * @return false when tests not passed.
     */
    private boolean doTests(final TransactionFee fee, final BigDecimal transferAmount, final Account from, final Account to) {
        // If fee is not enabled, does not generate the fee transfer
        if (!fee.isEnabled()) {
            return false;
        }

        // Test the amount range
        final BigDecimal initialAmount = fee.getInitialAmount();
        if (initialAmount != null && transferAmount.compareTo(initialAmount) < 0) {
            return false;
        }
        final BigDecimal finalAmount = fee.getFinalAmount();
        if (finalAmount != null && transferAmount.compareTo(finalAmount) > 0) {
            return false;
        }

        // Test from group
        final AccountOwner originalFromAccountOwner = from.getOwner();
        if (originalFromAccountOwner instanceof Member && !fee.isFromAllGroups()) {
            final Member fromMember = fetchService.fetch((Member) originalFromAccountOwner, Element.Relationships.GROUP);
            final MemberGroup fromGroup = fromMember.getMemberGroup();
            if (!fee.getFromGroups().contains(fromGroup)) {
                return false;
            }
        }
        // Test to group
        final AccountOwner originalToAccountOwner = to.getOwner();
        if (originalToAccountOwner instanceof Member && !fee.isToAllGroups()) {
            final Member toMember = fetchService.fetch((Member) originalToAccountOwner, Element.Relationships.GROUP);
            final MemberGroup toGroup = toMember.getMemberGroup();
            if (!fee.getToGroups().contains(toGroup)) {
                return false;
            }
        }
        return true;
    }

    private Validator getBasicValidator(final TransactionFee fee) {
        final Validator validator = new Validator("transactionFee");
        validator.property("name").required().maxLength(100);
        validator.property("description").maxLength(1000);
        validator.property("chargeType").required();
        final Property value = validator.property("value");
        if (fee.getChargeType() != null) {
            switch (fee.getChargeType()) {
                case PERCENTAGE:
                    value.required().positiveNonZero().lessEquals(100);
                    break;
                case FIXED:
                    value.required().positiveNonZero();
                    break;
            }
        }
        validator.property("originalTransferType").required();
        validator.property("generatedTransferType").required();
        validator.property("payer").required();
        if (fee.getPayer() == Subject.FIXED_MEMBER) {
            validator.property("fromFixedMember").key("transactionFee.fromFixedMember.name").required();
        }
        return validator;
    }

    private AccountOwner getOwner(final Subject subject, final Member fixedMember, final Account from, final Account to) {
        switch (subject) {
            case SYSTEM:
                return SystemAccountOwner.instance();
            case SOURCE:
                return from.getOwner();
            case DESTINATION:
                return to.getOwner();
            case FIXED_MEMBER:
                return fixedMember;
            case SOURCE_BROKER:
                return brokerOf(from.getOwner());
            case DESTINATION_BROKER:
                return brokerOf(to.getOwner());
        }
        return null;
    }

    private BigDecimal getRoundedARate(final Calendar date, final Calendar rawARateParam, final LocalSettings localSettings) {
        RatesDTO ratesDTO = new RatesDTO();
        ratesDTO.setEmissionDate(rawARateParam);
        ratesDTO.setDate(date);
        BigDecimal aRate = rateService.dateToRate(ratesDTO).getaRate();
        aRate = localSettings.round(aRate);
        return aRate;
    }

    private Validator getValidator(final BrokerCommission commission) {
        final Validator validator = getBasicValidator(commission);
        validator.property("maxFixedValue").positiveNonZero();
        validator.property("maxPercentageValue").positiveNonZero();
        validator.property("when").required();
        validator.property("count").add(new CountValidation());
        validator.property("chargeType").anyOf(ChargeType.FIXED, ChargeType.PERCENTAGE);
        validator.property("payer").anyOf(Subject.SYSTEM, Subject.SOURCE, Subject.DESTINATION);
        Subject payer = commission.getPayer();
        if (payer == Subject.SOURCE) {
            validator.property("whichBroker").anyOf(WhichBroker.SOURCE);
        } else if (payer == Subject.DESTINATION) {
            validator.property("whichBroker").anyOf(WhichBroker.DESTINATION);
        }
        if (commission.getInitialAmount() != null && commission.getFinalAmount() != null) {
            final Property initialAmount = validator.property("initialAmount");
            final BigDecimal finalAmount = commission.getFinalAmount();
            initialAmount.comparable(finalAmount, "<=", new ValidationError("errors.greaterThan", messageResolver.message("transactionFee.finalAmount")));
        }
        return validator;
    }

    private Validator getValidator(final SimpleTransactionFee fee, final ARateRelation arateRelation) {
        final TransferType originalTransferType = fetchService.fetch(fee.getOriginalTransferType(), RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY));

        Validator validator = getBasicValidator(fee);

        validator.property("receiver").required();
        if (fee.getReceiver() == Subject.FIXED_MEMBER) {
            validator.property("toFixedMember").key("transactionFee.toFixedMember.name").required();
        }
        validator.general(new PayerAndReceiverValidation());

        final Collection<ChargeType> allowedChargeTypes = EnumSet.allOf(ChargeType.class);
        final Currency currency = originalTransferType.getCurrency();
        if (!currency.isEnableARate()) {
            allowedChargeTypes.remove(ChargeType.A_RATE);
            allowedChargeTypes.remove(ChargeType.MIXED_A_D_RATES);
        }
        if (!currency.isEnableDRate()) {
            allowedChargeTypes.remove(ChargeType.D_RATE);
            allowedChargeTypes.remove(ChargeType.MIXED_A_D_RATES);
        }

        final ARatedFeeDTO dto = new ARatedFeeDTO(fee);
        validator = rateService.applyARateFieldsValidation(validator, dto, arateRelation);

        validator.property("chargeType").anyOf(allowedChargeTypes);

        if (fee.getInitialAmount() != null && fee.getFinalAmount() != null) {
            final Property initialAmount = validator.property("initialAmount");
            final BigDecimal finalAmount = fee.getFinalAmount();
            initialAmount.comparable(finalAmount, "<=", new ValidationError("errors.greaterThan", messageResolver.message("transactionFee.finalAmount")));
        }

        return validator;
    }

    private void preSave(final TransactionFee transactionFee) {
        if (transactionFee.isFromAllGroups()) {
            transactionFee.setFromGroups(null);
        }
        if (transactionFee.isToAllGroups()) {
            transactionFee.setToGroups(null);
        }
        if (transactionFee.getPayer() != Subject.FIXED_MEMBER) {
            transactionFee.setFromFixedMember(null);
        }
    }

}
