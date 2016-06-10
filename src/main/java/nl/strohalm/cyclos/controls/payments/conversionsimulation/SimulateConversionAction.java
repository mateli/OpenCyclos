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
package nl.strohalm.cyclos.controls.payments.conversionsimulation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.controls.reports.statistics.graphs.StatisticalDataProducer;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.rates.ConversionSimulationDTO;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewForRatesDTO;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;
import org.jfree.chart.plot.Marker;

/**
 * Action used to simulate a conversion
 * @author luis
 * @author Rinke
 */
public class SimulateConversionAction extends BaseFormAction implements LocalSettingsChangeListener {

    private AccountService                      accountService;
    private TransferTypeService                 transferTypeService;
    private PaymentService                      paymentService;

    private DataBinder<ConversionSimulationDTO> dataBinder;
    private ReadWriteLock                       lock = new ReentrantReadWriteLock(true);

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
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SimulateConversionForm form = context.getForm();
        if (form.isReloadData()) {
            return handleDisplay(context);
        }
        reloadForm(context);
        final ConversionSimulationDTO dto = getDataBinder().readFromString(form.getSimulation());
        final Member member = resolveMember(context);
        final boolean myAccount = member.equals(context.getAccountOwner());

        showResults(request, dto, myAccount);
        showGraph(context, dto);
        return context.getInputForward();
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SimulateConversionForm form = context.getForm();

        final boolean firstLoad = (!form.isReloadData()) && (request.getParameter("advanced") == null);
        if (firstLoad) {
            if (context.isAdmin()) {
                form.setAdvanced(true);
            } else {
                form.setAdvanced(false);
            }
        }

        reloadForm(context);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final SimulateConversionForm form = context.getForm();
        final ConversionSimulationDTO dto = getDataBinder().readFromString(form.getSimulation());
        paymentService.validate(dto);
    }

    /**
     * replaces the marker keys with their message labels from the resource bundle. Also sets the subTitle of the graph.
     */
    private void attachLabels(final StatisticalResultDTO rawDataObject, final ConversionSimulationDTO dto, final ActionContext context) {
        final Marker[] markers = rawDataObject.getDomainMarkers();
        if (markers != null) {
            for (final Marker marker : markers) {
                final String key = marker.getLabel();
                if (key != null) {
                    final String title = context.message(key);
                    marker.setLabel(title);
                }
            }
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();
        TransferType transferType = dto.getTransferType();
        transferType = transferTypeService.load(transferType.getId(), TransferType.Relationships.TRANSACTION_FEES, RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY));
        final String unitsPattern = transferType.getFrom().getCurrency().getPattern();
        final NumberConverter<BigDecimal> numberConverter = localSettings.getUnitsConverter(unitsPattern);
        final String numberString = numberConverter.toString(dto.getAmount());
        final String subTitle = context.message("conversionSimulation.result.graph.subtitle", numberString);
        rawDataObject.setSubTitle(subTitle);
    }

    /**
     * filters out all the transferTypes of allTransferTypes which do not have account as their from account.
     * @return only transferTypes remain which go FROM account
     */
    private List<TransferType> filterTransferTypesByAccount(final MemberAccount account, final Collection<TransferType> allTransferTypes) {
        final List<TransferType> result = new ArrayList<TransferType>(allTransferTypes.size());
        for (final TransferType currentTT : allTransferTypes) {
            if (account.getType().getFromTransferTypes().contains(currentTT)) {
                result.add(currentTT);
            }
        }
        return result;
    }

    private DataBinder<ConversionSimulationDTO> getDataBinder() {
        try {
            lock.readLock().lock();
            if (dataBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final BeanBinder<ConversionSimulationDTO> binder = BeanBinder.instance(ConversionSimulationDTO.class);
                binder.registerBinder("account", PropertyBinder.instance(MemberAccount.class, "account"));
                binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType"));
                binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
                binder.registerBinder("useActualRates", PropertyBinder.instance(Boolean.TYPE, "useActualRates"));
                binder.registerBinder("arate", PropertyBinder.instance(BigDecimal.class, "arate", localSettings.getNumberConverter()));
                binder.registerBinder("drate", PropertyBinder.instance(BigDecimal.class, "drate", localSettings.getNumberConverter()));
                binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
                binder.registerBinder("graph", PropertyBinder.instance(Boolean.TYPE, "graph"));
                dataBinder = binder;
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    private void reloadForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final SimulateConversionForm form = context.getForm();

        final Member member = resolveMember(context);
        final boolean myAccount = member.equals(context.getAccountOwner());
        final Collection<TransferType> allowedTTs = resolveAllowedTTs(context, myAccount);

        ConversionSimulationDTO dto = getDataBinder().readFromString(form.getSimulation());
        MemberAccount account = dto.getAccount();
        final List<Account> allowedAccounts = new ArrayList<Account>(accountService.getAccountsFromTTs(member, allowedTTs, TransferType.Direction.FROM));

        // Check if we have to reload data, and get default account
        boolean changed = false;
        if (account == null) {
            account = (MemberAccount) accountService.getDefaultAccountFromList(member, allowedAccounts);
            changed = true;
        }

        // limit transferTypes to only the relevant for this account
        account = accountService.load(account.getId());
        final List<TransferType> transferTypes = filterTransferTypesByAccount(account, allowedTTs);

        // get the default rates
        final ConversionSimulationDTO defaultDto = paymentService.getDefaultConversionDTO(account, transferTypes);
        if (changed || form.isReloadData()) {
            // reloadData is only true if the user chose another account.
            dto = defaultDto;
        }
        getDataBinder().writeAsString(form.getSimulation(), dto);

        request.setAttribute("member", member);
        request.setAttribute("account", account);
        request.setAttribute("myAccount", myAccount);

        request.setAttribute("accounts", allowedAccounts);
        if (allowedAccounts.size() == 1) {
            request.setAttribute("singleAccount", allowedAccounts.get(0));
        }
        request.setAttribute("arateDefault", defaultDto.getArate());
        request.setAttribute("drateDefault", defaultDto.getDrate());

        request.setAttribute("tts", transferTypes);
        if (transferTypes.size() == 1) {
            request.setAttribute("singleTT", transferTypes.get(0));
        }
    }

    private Collection<TransferType> resolveAllowedTTs(final ActionContext context, final boolean myAccount) {
        Group loggedUserGroup = context.getGroup();
        final Collection<TransferType> allowedTTs;
        if (!myAccount && loggedUserGroup instanceof BrokerGroup) {
            loggedUserGroup = groupService.load(loggedUserGroup.getId(), BrokerGroup.Relationships.BROKER_CONVERSION_SIMULATION_TTS);
            allowedTTs = ((BrokerGroup) loggedUserGroup).getBrokerConversionSimulationTTs();
        } else {
            loggedUserGroup = groupService.load(loggedUserGroup.getId(), Group.Relationships.CONVERSION_SIMULATION_TTS);
            allowedTTs = loggedUserGroup.getConversionSimulationTTs();
        }
        return allowedTTs;
    }

    private Member resolveMember(final ActionContext context) {
        final SimulateConversionForm form = context.getForm();
        Member member = null;
        final long memberId = form.getMemberId();
        if (memberId > 0) {
            try {
                member = elementService.load(memberId);
            } catch (final Exception e) {
                member = null;
            }
        }
        if (member == null && !context.isAdmin()) {
            member = (Member) context.getAccountOwner();
        }
        if (member == null) {
            throw new ValidationException();
        }
        return member;
    }

    private void showGraph(final ActionContext context, final ConversionSimulationDTO dto) {
        if (dto.isGraph()) {
            final HttpServletRequest request = context.getRequest();
            final List<StatisticalDataProducer> dataList = new ArrayList<StatisticalDataProducer>();
            final StatisticalResultDTO rawDataObject = paymentService.getSimulateConversionGraph(dto);
            attachLabels(rawDataObject, dto, context);
            final StatisticalDataProducer producer = new StatisticalDataProducer(rawDataObject, context);
            final LocalSettings localSettings = settingsService.getLocalSettings();
            producer.setSettings(localSettings);
            dataList.add(producer);
            request.setAttribute("dataList", dataList);
        }
    }

    private void showResults(final HttpServletRequest request, final ConversionSimulationDTO dto, final boolean myAccount) {
        final Calendar now = Calendar.getInstance();
        if (dto.getDate() == null) {
            dto.setDate(now);
        }
        // adapt the time so that the date entered in the form has the same time as now
        final Calendar equalizedProcessDate = DateHelper.equalizeTime(dto.getDate(), now);
        dto.setDate(equalizedProcessDate);
        // Perform the simulation
        final TransactionFeePreviewForRatesDTO result = paymentService.simulateConversion(dto);
        request.setAttribute("feelessAmount", result.getFinalAmount());
        request.setAttribute("totalAmount", result.getAmount());
        request.setAttribute("totalFees", result.getRatesAsFeePercentage());
        request.setAttribute("totalFeeAmount", result.getTotalFeeAmount());
        request.setAttribute("fees", result.getFees());
        // jsp will read wrong percentages from fees as fees objects will change when creating the graph. So we store the percentages directly
        final ArrayList<BigDecimal> feePercentages = new ArrayList<BigDecimal>(result.getFees().size());
        for (final TransactionFee fee : result.getFees().keySet()) {
            feePercentages.add(fee.getAmount().getValue());
        }
        request.setAttribute("feePercentages", feePercentages);
        // save the used rates to the form (via the accountStatus methods)
        TransferType transferType = dto.getTransferType();
        transferType = transferTypeService.load(transferType.getId(), TransferType.Relationships.TRANSACTION_FEES, RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY));
        request.setAttribute("unitsPattern", transferType.getFrom().getCurrency().getPattern());
        request.setAttribute("usedARate", result.getARate());
        request.setAttribute("usedDRate", result.getDRate());
        if (transferType.isHavingRatedFees()) {
            request.setAttribute("usedDate", equalizedProcessDate);
        }
    }

}
