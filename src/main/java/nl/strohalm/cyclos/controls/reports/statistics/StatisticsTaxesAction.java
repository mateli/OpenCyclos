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
package nl.strohalm.cyclos.controls.reports.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.reports.statistics.graphs.StatisticalDataProducer;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeQuery;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalTaxesQuery;
import nl.strohalm.cyclos.entities.reports.StatisticsWhatToShow;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accountfees.AccountFeeService;
import nl.strohalm.cyclos.services.stats.StatisticalTaxesService;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Shows a list with statistics to select.
 * 
 * @author rinke
 */
public class StatisticsTaxesAction extends StatisticsAction {

    private AccountFeeService     accountFeeService;
    private TransactionFeeService transactionFeeService;

    @Override
    public StatisticsType getStatisticsType() {
        return StatisticsType.TAXES;
    }

    @Override
    public DataBinder<StatisticalTaxesQuery> initDataBinder(final LocalSettings settings) {
        final BeanBinder<StatisticalTaxesQuery> binder = BeanBinder.instance(StatisticalTaxesQuery.class);
        StatisticsAction.bindCommonFields(binder, settings);
        binder.registerBinder("volume", PropertyBinder.instance(Boolean.TYPE, "volume"));
        binder.registerBinder("numberOfMembers", PropertyBinder.instance(Boolean.TYPE, "numberOfMembers"));
        binder.registerBinder("medianPerMember", PropertyBinder.instance(Boolean.TYPE, "medianPerMember"));
        binder.registerBinder("maxMember", PropertyBinder.instance(Boolean.TYPE, "maxMember"));
        binder.registerBinder("relativeToGrossProduct", PropertyBinder.instance(Boolean.TYPE, "relativeToGrossProduct"));
        binder.registerBinder("paidOrNot", PropertyBinder.instance(StatisticalTaxesService.PaidOrNot.class, "paidOrNot"));
        binder.registerBinder("notPaidLimit", PropertyBinder.instance(Integer.TYPE, "notPaidLimit"));
        binder.registerBinder("accountFees", SimpleCollectionBinder.instance(AccountFee.class, "accountFees", ReferenceConverter.instance(AccountFee.class)));
        binder.registerBinder("transactionFees", SimpleCollectionBinder.instance(TransactionFee.class, "transactionFees", ReferenceConverter.instance(TransactionFee.class)));
        return binder;
    }

    @Inject
    public void setAccountFeeService(final AccountFeeService accountFeeService) {
        this.accountFeeService = accountFeeService;
    }

    @Inject
    public void setStatisticalTaxesService(final StatisticalTaxesService statisticalService) {
        setStatisticalService(statisticalService);
    }

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    protected void applyFeeFilters(final HttpServletRequest request) {
        // get account fees
        final AccountFeeQuery accountFeeQuery = new AccountFeeQuery();
        accountFeeQuery.setReturnDisabled(false);
        // feeQuery.setGroups(managedGroups);
        final List<AccountFee> accountFees = accountFeeService.search(accountFeeQuery);
        request.setAttribute("accountFeeList", accountFees);
        // get transaction fees
        final TransactionFeeQuery transactionFeeQuery = new TransactionFeeQuery();
        transactionFeeQuery.setReturnDisabled(false);
        // transactionFeeQuery.setGroups(...)
        final List<? extends TransactionFee> transactionFees = transactionFeeService.search(transactionFeeQuery);
        request.setAttribute("transactionFeeList", transactionFees);
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        super.executeQuery(context, queryParameters);
        final StatisticalTaxesQuery query = (StatisticalTaxesQuery) queryParameters;
        final List<StatisticalDataProducer> dataList = new ArrayList<StatisticalDataProducer>();

        if (query.getWhatToShow() == StatisticsWhatToShow.SINGLE_PERIOD) {
            if (query.isVolume()) {
                dataList.add(producerFactory(getService().getSinglePeriodVolume(query), context, StatisticalDataProducer.class));
            }
            if (query.isNumberOfMembers()) {
                dataList.add(producerFactory(getService().getSinglePeriodNumberOfMembers(query), context, StatisticalDataProducer.class));
            }
            if (query.isMedianPerMember()) {
                dataList.add(producerFactory(getService().getSinglePeriodMedianPerMember(query), context, StatisticalDataProducer.class));
            }
            if (query.isMaxMember()) {
                dataList.add(producerFactory(getService().getSinglePeriodMaxMember(query), context, StatisticalDataProducer.class));
            }
            if (query.isRelativeToGrossProduct()) {
                dataList.add(producerFactory(getService().getSinglePeriodRelativeToGrossProduct(query), context, StatisticalDataProducer.class));
            }
        } else if (query.getWhatToShow() == StatisticsWhatToShow.COMPARE_PERIODS) {
            if (query.isVolume()) {
                dataList.add(producerFactory(getService().getComparePeriodsVolume(query), context, StatisticalDataProducer.class));
            }
            if (query.isNumberOfMembers()) {
                dataList.add(producerFactory(getService().getComparePeriodsNumberOfMembers(query), context, StatisticalDataProducer.class));
            }
            if (query.isMedianPerMember()) {
                dataList.add(producerFactory(getService().getComparePeriodsMedianPerMember(query), context, StatisticalDataProducer.class));
            }
            if (query.isMaxMember()) {
                dataList.add(producerFactory(getService().getComparePeriodsMaxMember(query), context, StatisticalDataProducer.class));
            }
            if (query.isRelativeToGrossProduct()) {
                dataList.add(producerFactory(getService().getComparePeriodsRelativeToGrossProduct(query), context, StatisticalDataProducer.class));
            }
        } else if (query.getWhatToShow() == StatisticsWhatToShow.THROUGH_TIME) {
            if (query.isVolume()) {
                dataList.add(producerFactory(getService().getThroughTimeVolume(query), context, StatisticalDataProducer.class));
            }
            if (query.isNumberOfMembers()) {
                dataList.add(producerFactory(getService().getThroughTimeNumberOfMembers(query), context, StatisticalDataProducer.class));
            }
            if (query.isMedianPerMember()) {
                dataList.add(producerFactory(getService().getThroughTimeMedianPerMember(query), context, StatisticalDataProducer.class));
            }
            if (query.isMaxMember()) {
                dataList.add(producerFactory(getService().getThroughTimeMaxMember(query), context, StatisticalDataProducer.class));
            }
            if (query.isRelativeToGrossProduct()) {
                dataList.add(producerFactory(getService().getThroughTimeRelativeToGrossProduct(query), context, StatisticalDataProducer.class));
            }
        } else if (query.getWhatToShow() == StatisticsWhatToShow.DISTRIBUTION) {
            if (query.isMedianPerMember()) {
                dataList.add(producerFactory(getService().getDistributionMedianPerMember(query), context, StatisticalDataProducer.class));
            }
            if (query.isRelativeToGrossProduct()) {
                dataList.add(producerFactory(getService().getDistributionRelativeToGrossProduct(query), context, StatisticalDataProducer.class));
            }
        }
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("dataList", dataList);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        RequestHelper.storeEnum(request, StatisticalTaxesService.PaidOrNot.class, "paidOrNot");
        applyGroupFilter(request);
        applyFeeFilters(request);
        final QueryParameters queryParameters = super.prepareForm(context);
        // next line should be called AFTER calling super.
        request.setAttribute("whatToShow", Arrays.asList(StatisticsWhatToShow.SINGLE_PERIOD, StatisticsWhatToShow.COMPARE_PERIODS, StatisticsWhatToShow.THROUGH_TIME, StatisticsWhatToShow.DISTRIBUTION));
        // getService().setMaximumDataPoints(250);
        return queryParameters;
    }

    private StatisticalTaxesService getService() {
        return (StatisticalTaxesService) getBaseStatisticalService();
    }

}
