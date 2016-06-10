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
import nl.strohalm.cyclos.entities.reports.StatisticalFinancesQuery;
import nl.strohalm.cyclos.entities.reports.StatisticsWhatToShow;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.stats.StatisticalFinancesService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Shows a list with statistics to select.
 * 
 * @author rinke
 */
public class StatisticsFinancesAction extends StatisticsAction {

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        super.executeQuery(context, queryParameters);
        final StatisticalFinancesQuery query = (StatisticalFinancesQuery) queryParameters;
        final List<StatisticalDataProducer> dataList = new ArrayList<StatisticalDataProducer>();

        if (query.getWhatToShow() == StatisticsWhatToShow.SINGLE_PERIOD) {
            if (query.isOverview()) {
                dataList.add(producerFactory(getService().getSinglePeriodOverview(query), context, StatisticalDataProducer.class));
            }
            if (query.isIncome()) {
                dataList.add(producerFactory(getService().getSinglePeriodIncome(query), context, StatisticalDataProducer.class));
            }
            if (query.isExpenditure()) {
                dataList.add(producerFactory(getService().getSinglePeriodExpenditure(query), context, StatisticalDataProducer.class));
            }
        } else if (query.getWhatToShow() == StatisticsWhatToShow.COMPARE_PERIODS) {
            if (query.isIncome()) {
                dataList.add(producerFactory(getService().getComparePeriodsIncome(query), context, StatisticalDataProducer.class));
            }
            if (query.isExpenditure()) {
                dataList.add(producerFactory(getService().getComparePeriodsExpenditure(query), context, StatisticalDataProducer.class));
            }
        } else if (query.getWhatToShow() == StatisticsWhatToShow.THROUGH_TIME) {
            if (query.isIncome()) {
                dataList.add(producerFactory(getService().getThroughTimeIncome(query), context, StatisticalDataProducer.class));
            }
            if (query.isExpenditure()) {
                dataList.add(producerFactory(getService().getThroughTimeExpenditure(query), context, StatisticalDataProducer.class));
            }
        }
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("dataList", dataList);
    }

    private StatisticalFinancesService getService() {
        return (StatisticalFinancesService) getBaseStatisticalService();
    }

    @Override
    public StatisticsType getStatisticsType() {
        return StatisticsType.FINANCES;
    }

    @Override
    public DataBinder<StatisticalFinancesQuery> initDataBinder(final LocalSettings settings) {
        final BeanBinder<StatisticalFinancesQuery> binder = BeanBinder.instance(StatisticalFinancesQuery.class);
        StatisticsAction.bindCommonFields(binder, settings);
        binder.registerBinder("overview", PropertyBinder.instance(Boolean.TYPE, "overview"));
        binder.registerBinder("income", PropertyBinder.instance(Boolean.TYPE, "income"));
        binder.registerBinder("expenditure", PropertyBinder.instance(Boolean.TYPE, "expenditure"));
        binder.registerBinder("overviewGraph", PropertyBinder.instance(Boolean.TYPE, "overviewGraph"));
        binder.registerBinder("incomeGraph", PropertyBinder.instance(Boolean.TYPE, "incomeGraph"));
        binder.registerBinder("expenditureGraph", PropertyBinder.instance(Boolean.TYPE, "expenditureGraph"));
        return binder;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        applySystemAccountFilter(request);
        applyPaymentFilter(request);
        final QueryParameters queryParameters = super.prepareForm(context);
        request.setAttribute("whatToShow", Arrays.asList(StatisticsWhatToShow.SINGLE_PERIOD, StatisticsWhatToShow.COMPARE_PERIODS, StatisticsWhatToShow.THROUGH_TIME));
        getService().setMaximumDataPoints(300);
        return queryParameters;
    }

    @Inject
    public void setStatisticalFinancesService(final StatisticalFinancesService statisticalService) {
        setStatisticalService(statisticalService);
    }

}
