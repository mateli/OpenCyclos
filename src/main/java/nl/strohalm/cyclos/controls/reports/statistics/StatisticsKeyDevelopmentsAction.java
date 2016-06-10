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
import nl.strohalm.cyclos.entities.reports.StatisticalKeyDevelopmentsQuery;
import nl.strohalm.cyclos.entities.reports.StatisticsWhatToShow;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.stats.StatisticalKeyDevelopmentsService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Shows a list with statistics to select, and calls the service to create the stats after clicking submit.
 * @author rinke
 */
public class StatisticsKeyDevelopmentsAction extends StatisticsAction {

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        super.executeQuery(context, queryParameters);
        final StatisticalKeyDevelopmentsQuery query = (StatisticalKeyDevelopmentsQuery) queryParameters;
        final List<StatisticalDataProducer> dataList = new ArrayList<StatisticalDataProducer>();

        if (query.getWhatToShow() == StatisticsWhatToShow.SINGLE_PERIOD) {
            if (query.isNumberOfMembers()) {
                dataList.add(producerFactory(getService().getSinglePeriodNumberOfMembers(query), context, StatisticalDataProducer.class));
            }

            if (query.isGrossProduct()) {
                dataList.add(producerFactory(getService().getSinglePeriodGrossProduct(query), context, StatisticalDataProducer.class));
            }

            if (query.isNumberOfTransactions()) {
                dataList.add(producerFactory(getService().getSinglePeriodNumberOfTransactions(query), context, StatisticalDataProducer.class));
            }

            if (query.isTransactionAmount()) {
                dataList.add(producerFactory(getService().getSinglePeriodMedianAmountPerTransaction(query), context, StatisticalDataProducer.class));
                dataList.add(producerFactory(getService().getSinglePeriodHighestTransactionAmount(query), context, StatisticalDataProducer.class));
            }

            if (query.isNumberOfAds()) {
                dataList.add(producerFactory(getService().getSinglePeriodNumberOfAds(query), context, StatisticalDataProducer.class));
            }
        } else if (query.getWhatToShow() == StatisticsWhatToShow.COMPARE_PERIODS) {
            if (query.isNumberOfMembers()) {
                dataList.add(producerFactory(getService().getComparePeriodsNumberOfMembers(query), context, StatisticalDataProducer.class));
            }

            if (query.isGrossProduct()) {
                dataList.add(producerFactory(getService().getComparePeriodsGrossProduct(query), context, StatisticalDataProducer.class));
            }

            if (query.isNumberOfTransactions()) {
                dataList.add(producerFactory(getService().getComparePeriodsNumberOfTransactions(query), context, StatisticalDataProducer.class));
            }

            if (query.isTransactionAmount()) {
                dataList.add(producerFactory(getService().getComparePeriodsMedianAmountPerTransaction(query), context, StatisticalDataProducer.class));
                dataList.add(producerFactory(getService().getComparePeriodsHighestTransactionAmount(query), context, StatisticalDataProducer.class));
            }

            if (query.isNumberOfAds()) {
                dataList.add(producerFactory(getService().getComparePeriodsNumberOfAds(query), context, StatisticalDataProducer.class));
            }
        } else { // query.getWhatToShow() == StatisticsWhatToShow.THROUGH_TIME
            // this one needs a different approach, as it doesn't apply to the StatisticalKeyResultsDataProducer layout (being: one column
            // present period, one column last, one column %)
            dataList.add(producerFactory(getService().getThroughTheTime(query), context, StatisticalDataProducer.class));
        }
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("dataList", dataList);
    }

    private StatisticalKeyDevelopmentsService getService() {
        return (StatisticalKeyDevelopmentsService) getBaseStatisticalService();
    }

    @Override
    public StatisticsType getStatisticsType() {
        return StatisticsType.KEY_DEVELOPMENTS;
    }

    @Override
    public DataBinder<StatisticalKeyDevelopmentsQuery> initDataBinder(final LocalSettings settings) {
        final BeanBinder<StatisticalKeyDevelopmentsQuery> binder = BeanBinder.instance(StatisticalKeyDevelopmentsQuery.class);
        StatisticsAction.bindCommonFields(binder, settings);
        binder.registerBinder("numberOfMembers", PropertyBinder.instance(Boolean.TYPE, "numberOfMembers"));
        binder.registerBinder("grossProduct", PropertyBinder.instance(Boolean.TYPE, "grossProduct"));
        binder.registerBinder("numberOfTransactions", PropertyBinder.instance(Boolean.TYPE, "numberOfTransactions"));
        binder.registerBinder("transactionAmount", PropertyBinder.instance(Boolean.TYPE, "transactionAmount"));
        binder.registerBinder("numberOfAds", PropertyBinder.instance(Boolean.TYPE, "numberOfAds"));
        binder.registerBinder("numberOfMembersGraph", PropertyBinder.instance(Boolean.TYPE, "numberOfMembersGraph"));
        binder.registerBinder("grossProductGraph", PropertyBinder.instance(Boolean.TYPE, "grossProductGraph"));
        binder.registerBinder("numberOfTransactionsGraph", PropertyBinder.instance(Boolean.TYPE, "numberOfTransactionsGraph"));
        binder.registerBinder("transactionAmountGraph", PropertyBinder.instance(Boolean.TYPE, "transactionAmountGraph"));
        binder.registerBinder("numberOfAdsGraph", PropertyBinder.instance(Boolean.TYPE, "numberOfAdsGraph"));
        binder.registerBinder("thruTimeGraph", PropertyBinder.instance(Boolean.TYPE, "thruTimeGraph"));
        return binder;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        applyGroupFilter(request);
        applyPaymentFilter(request);
        final QueryParameters queryParameters = super.prepareForm(context);
        // next line should be called AFTER calling super.
        request.setAttribute("whatToShow", Arrays.asList(StatisticsWhatToShow.SINGLE_PERIOD, StatisticsWhatToShow.COMPARE_PERIODS, StatisticsWhatToShow.THROUGH_TIME));
        getService().setMaximumDataPoints(250);
        return queryParameters;
    }

    @Inject
    public void setStatisticalKeyDevelopmentsService(final StatisticalKeyDevelopmentsService statisticalService) {
        setStatisticalService(statisticalService);
    }

}
