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
import nl.strohalm.cyclos.entities.reports.StatisticalActivityQuery;
import nl.strohalm.cyclos.entities.reports.StatisticsWhatToShow;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.services.stats.StatisticalActivityService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Shows a list of statistics to select
 * @author rinke
 */
public class StatisticsActivityAction extends StatisticsAction implements LocalSettingsChangeListener {

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        super.executeQuery(context, queryParameters);
        final StatisticalActivityQuery query = (StatisticalActivityQuery) queryParameters;

        final List<StatisticalDataProducer> dataList = new ArrayList<StatisticalDataProducer>();

        if (query.getWhatToShow() == StatisticsWhatToShow.SINGLE_PERIOD) {
            if (query.isGrossProduct()) {
                dataList.add(producerFactory(getService().getSinglePeriodGrossProduct(query), context, StatisticalDataProducer.class));
            }
            if (query.isNumberTransactions()) {
                dataList.add(producerFactory(getService().getSinglePeriodNumberTransactions(query), context, StatisticalDataProducer.class));
            }
            if (query.isPercentageNoTrade()) {
                dataList.add(producerFactory(getService().getSinglePeriodPercentageNoTrade(query), context, StatisticalDataProducer.class));
            }
            if (query.isLoginTimes()) {
                dataList.add(producerFactory(getService().getSinglePeriodLoginTimes(query), context, StatisticalDataProducer.class));
            }
        } else if (query.getWhatToShow() == StatisticsWhatToShow.COMPARE_PERIODS) {
            if (query.isGrossProduct()) {
                dataList.add(producerFactory(getService().getComparePeriodsGrossProduct(query), context, StatisticalDataProducer.class));
            }
            if (query.isNumberTransactions()) {
                dataList.add(producerFactory(getService().getComparePeriodsNumberTransactions(query), context, StatisticalDataProducer.class));
            }
            if (query.isPercentageNoTrade()) {
                dataList.add(producerFactory(getService().getComparePeriodsPercentageNoTrade(query), context, StatisticalDataProducer.class));
            }
            if (query.isLoginTimes()) {
                dataList.add(producerFactory(getService().getComparePeriodsLoginTimes(query), context, StatisticalDataProducer.class));
            }
        } else if (query.getWhatToShow() == StatisticsWhatToShow.THROUGH_TIME) {
            if (query.isGrossProduct()) {
                dataList.add(producerFactory(getService().getThroughTheTimeGrossProduct(query), context, StatisticalDataProducer.class));
            }
            if (query.isNumberTransactions()) {
                dataList.add(producerFactory(getService().getThroughTheTimeNumberTransactions(query), context, StatisticalDataProducer.class));
            }
            if (query.isPercentageNoTrade()) {
                dataList.add(producerFactory(getService().getThroughTheTimePercentageNoTrade(query), context, StatisticalDataProducer.class));
            }
            if (query.isLoginTimes()) {
                dataList.add(producerFactory(getService().getThroughTheTimeLoginTimes(query), context, StatisticalDataProducer.class));
            }
        } else { // query.getWhatToShow() == StatisticsWhatToShow.DISTRIBUTION
            if (query.isGrossProduct()) {
                dataList.add(producerFactory(getService().getHistogramGrossProduct(query), context, StatisticalDataProducer.class));
            }
            if (query.isNumberTransactions()) {
                dataList.add(producerFactory(getService().getHistogramNumberTransactions(query), context, StatisticalDataProducer.class));
            }
            if (query.isLoginTimes()) {
                dataList.add(producerFactory(getService().getHistogramLoginTimes(query), context, StatisticalDataProducer.class));
            }
            if (query.isGrossProductTopten()) {
                dataList.add(producerFactory(getService().getToptenPersonalGrossProduct(query), context, StatisticalDataProducer.class));
            }
            if (query.isNumberTransactionsTopten()) {
                dataList.add(producerFactory(getService().getToptenPersonalNumberTransactions(query), context, StatisticalDataProducer.class));
            }
            if (query.isLoginTimesTopten()) {
                dataList.add(producerFactory(getService().getToptenPersonalLoginTimes(query), context, StatisticalDataProducer.class));
            }
        }
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("dataList", dataList);
    }

    private StatisticalActivityService getService() {
        return (StatisticalActivityService) getBaseStatisticalService();
    }

    @Override
    public StatisticsType getStatisticsType() {
        return StatisticsType.MEMBER_ACTIVITIES;
    }

    @Override
    public DataBinder<StatisticalActivityQuery> initDataBinder(final LocalSettings settings) {
        final BeanBinder<StatisticalActivityQuery> binder = BeanBinder.instance(StatisticalActivityQuery.class);
        StatisticsAction.bindCommonFields(binder, settings);
        binder.registerBinder("grossProduct", PropertyBinder.instance(Boolean.TYPE, "grossProduct"));
        binder.registerBinder("grossProductGraph", PropertyBinder.instance(Boolean.TYPE, "grossProductGraph"));
        binder.registerBinder("grossProductTopten", PropertyBinder.instance(Boolean.TYPE, "grossProductTopten"));
        binder.registerBinder("numberTransactions", PropertyBinder.instance(Boolean.TYPE, "numberTransactions"));
        binder.registerBinder("numberTransactionsGraph", PropertyBinder.instance(Boolean.TYPE, "numberTransactionsGraph"));
        binder.registerBinder("numberTransactionsTopten", PropertyBinder.instance(Boolean.TYPE, "numberTransactionsTopten"));
        binder.registerBinder("percentageNoTrade", PropertyBinder.instance(Boolean.TYPE, "percentageNoTrade"));
        binder.registerBinder("percentageNoTradeGraph", PropertyBinder.instance(Boolean.TYPE, "percentageNoTradeGraph"));
        binder.registerBinder("loginTimes", PropertyBinder.instance(Boolean.TYPE, "loginTimes"));
        binder.registerBinder("loginTimesGraph", PropertyBinder.instance(Boolean.TYPE, "loginTimesGraph"));
        binder.registerBinder("loginTimesTopten", PropertyBinder.instance(Boolean.TYPE, "loginTimesTopten"));
        return binder;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        applyGroupFilter(request);
        applyPaymentFilter(request);
        final QueryParameters queryParameters = super.prepareForm(context);
        // next line should be called AFTER calling super.
        request.setAttribute("whatToShow", Arrays.asList(StatisticsWhatToShow.SINGLE_PERIOD, StatisticsWhatToShow.COMPARE_PERIODS, StatisticsWhatToShow.THROUGH_TIME, StatisticsWhatToShow.DISTRIBUTION));
        getService().setMaximumDataPoints(150);
        return queryParameters;
    }

    @Inject
    public void setStatisticalActivityService(final StatisticalActivityService statisticalService) {
        setStatisticalService(statisticalService);
    }

}
