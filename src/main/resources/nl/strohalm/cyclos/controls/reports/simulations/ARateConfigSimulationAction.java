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
package nl.strohalm.cyclos.controls.reports.simulations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.controls.reports.statistics.graphs.StatisticalDataProducer;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee.ARateRelation;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.ChargeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.rates.ARatedFeeDTO;
import nl.strohalm.cyclos.services.accounts.rates.RateService;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

import org.apache.struts.action.ActionForward;

/**
 * Action for the Simulation of the ARate Configuration, showing the ARate curve in a graph.
 * @author Rinke
 * 
 */
public class ARateConfigSimulationAction extends BaseFormAction implements LocalSettingsChangeListener {

    private DataBinder<ARatedFeeDTO> dataBinder;
    private TransferTypeService      transferTypeService;
    private RateService              rateService;
    private TransactionFeeService    transactionFeeService;

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public void setRateService(final RateService rateService) {
        this.rateService = rateService;
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
        final HttpServletRequest request = context.getRequest();
        final RateConfigSimulationForm form = context.getForm();
        if (form.isReloadData()) {
            return handleDisplay(context);
        }
        final ARatedFeeDTO dto = getDataBinder().readFromString(form.getSimulation());
        final List<StatisticalDataProducer> dataList = new ArrayList<StatisticalDataProducer>();
        final StatisticalResultDTO rawDataObject = rateService.getRateConfigGraph(dto);
        final StatisticalDataProducer producer = new StatisticalDataProducer(rawDataObject, context);
        final LocalSettings localSettings = settingsService.getLocalSettings();
        producer.setSettings(localSettings);
        dataList.add(producer);
        request.setAttribute("dataList", dataList);
        prepareForm(context);
        return context.getInputForward();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final RateConfigSimulationForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final ARatedFeeDTO dto = getDataBinder().readFromString(form.getSimulation());

        // store necessary radio button cats
        final Collection<ChargeType> chargeTypes = EnumSet.of(ChargeType.A_RATE, ChargeType.MIXED_A_D_RATES);
        request.setAttribute("chargeTypes", chargeTypes);
        RequestHelper.storeEnum(request, ARateRelation.class, "aRateRelations");

        // get the relevant TT's
        final List<TransferType> aRatedTTs = transferTypeService.listARatedTTs();
        TransferType tt = dto.getTransferType();
        // if more than 1, just take the first
        final boolean firstTime = (tt == null);
        if (tt == null && aRatedTTs.size() > 0) {
            tt = aRatedTTs.get(0);
        }

        // get the fees belonging to the tt
        if (tt != null) {
            tt = transferTypeService.load(tt.getId(), TransferType.Relationships.TRANSACTION_FEES);
        }
        final Collection<? extends TransactionFee> fees;
        if (tt == null) {
            fees = Collections.emptyList();
        } else {
            fees = tt.getARatedFees();
        }

        // set some defaults independent in case of no tt's or fees
        ARateRelation arateRelation = dto.getfInfinite() == null ? ARateRelation.LINEAR : ARateRelation.ASYMPTOTICAL;
        if (arateRelation == null) {
            arateRelation = ARateRelation.LINEAR;
        }
        if (dto.getChargeType() == null) {
            dto.setChargeType(ChargeType.A_RATE);
        }

        // if needed fill the fields
        if (tt != null && (firstTime || form.isReloadData())) {
            // load fee if already set
            TransactionFee fee = dto.getTransactionFee();
            if (fee != null) {
                fee = transactionFeeService.load(fee.getId(), TransactionFee.Relationships.ORIGINAL_TRANSFER_TYPE);
            }
            // only take first fee if existing fee doesn't belong to this tt
            if (!(fee != null && fee.getOriginalTransferType().equals(tt))) {
                fee = ((List<TransactionFee>) fees).get(0);
            }
            if (fee != null && (fee instanceof SimpleTransactionFee)) {
                final SimpleTransactionFee simpleFee = (SimpleTransactionFee) fee;
                dto.setChargeType(fee.getChargeType());
                arateRelation = simpleFee.getfInfinite() == null ? ARateRelation.LINEAR : ARateRelation.ASYMPTOTICAL;
                dto.setH(simpleFee.getH());
                dto.setaFIsZero(simpleFee.getaFIsZero());
                dto.setF1(simpleFee.getF1());
                dto.setfInfinite(simpleFee.getfInfinite());
                dto.setfMinimal(simpleFee.getfMinimal());
                dto.setgFIsZero(simpleFee.getgFIsZero());
            }
        }
        if (firstTime) {
            dto.setRangeStart(0);
            dto.setRangeEnd(arateRelation == ARateRelation.ASYMPTOTICAL ? 100 : 30);
        }
        dto.setTransferType(tt);
        form.setSimulation("aRateRelation", arateRelation.name());

        getDataBinder().writeAsString(form.getSimulation(), dto);
        request.setAttribute("tts", aRatedTTs);
        if (aRatedTTs.size() == 1) {
            request.setAttribute("singleTT", "true");
        }
        request.setAttribute("fees", fees);
        if (fees.size() == 1) {
            request.setAttribute("singleFee", "true");
        }
        request.setAttribute("simulation", "true");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final RateConfigSimulationForm form = context.getForm();
        final ARatedFeeDTO dto = getDataBinder().readFromString(form.getSimulation());
        final ARateRelation aRateRelation = PropertyBinder.instance(ARateRelation.class, "aRateRelation").readFromString(form.getSimulation());
        rateService.validate(dto, aRateRelation);
    }

    private DataBinder<ARatedFeeDTO> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final BeanBinder<ARatedFeeDTO> binder = BeanBinder.instance(ARatedFeeDTO.class);
            binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType"));
            binder.registerBinder("transactionFee", PropertyBinder.instance(TransactionFee.class, "transactionFee"));
            binder.registerBinder("chargeType", PropertyBinder.instance(ChargeType.class, "chargeType"));

            binder.registerBinder("h", PropertyBinder.instance(BigDecimal.class, "h", localSettings.getHighPrecisionConverter()));
            binder.registerBinder("aFIsZero", PropertyBinder.instance(BigDecimal.class, "aFIsZero", localSettings.getHighPrecisionConverter()));
            binder.registerBinder("f1", PropertyBinder.instance(BigDecimal.class, "f1", localSettings.getHighPrecisionConverter()));
            binder.registerBinder("fInfinite", PropertyBinder.instance(BigDecimal.class, "fInfinite", localSettings.getHighPrecisionConverter()));
            binder.registerBinder("fMinimal", PropertyBinder.instance(BigDecimal.class, "fMinimal", localSettings.getHighPrecisionConverter()));
            binder.registerBinder("gFIsZero", PropertyBinder.instance(BigDecimal.class, "gFIsZero", localSettings.getHighPrecisionConverter()));
            binder.registerBinder("rangeStart", PropertyBinder.instance(Integer.class, "rangeStart"));
            binder.registerBinder("rangeEnd", PropertyBinder.instance(Integer.class, "rangeEnd"));
            dataBinder = binder;
        }
        return dataBinder;
    }
}
