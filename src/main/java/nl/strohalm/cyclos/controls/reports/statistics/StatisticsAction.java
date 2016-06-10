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

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.controls.reports.statistics.graphs.StatisticalDataProducer;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalQuery;
import nl.strohalm.cyclos.entities.reports.StatisticsWhatToShow;
import nl.strohalm.cyclos.entities.reports.ThroughTimeRange;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.SystemAccountTypeQuery;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;
import nl.strohalm.cyclos.services.stats.StatisticalService;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterService;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Month;
import nl.strohalm.cyclos.utils.NamedPeriod;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.Quarter;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * The common ancestor for all statistics actions. Defines some general methods for statistics.
 * 
 * @author Rinke
 * 
 */
public abstract class StatisticsAction extends BaseQueryAction implements LocalSettingsChangeListener {

    /**
     * Enumeration to indicate what type of statistics will be shown
     */
    public enum StatisticsType {
        KEY_DEVELOPMENTS, MEMBER_ACTIVITIES, FINANCES, TAXES;
    }

    /**
     * binds the common fields to the form, such as periods and filters. Method to be called from the initDataBinder method
     * 
     * @param binder
     * @param settings
     */
    protected static void bindCommonFields(final BeanBinder<? extends StatisticalQuery> binder, final LocalSettings settings) {
        binder.registerBinder("periodMain", DataBinderHelper.namedPeriodBinder(settings, "periodMain"));
        binder.registerBinder("periodComparedTo", DataBinderHelper.namedPeriodBinder(settings, "periodComparedTo"));
        binder.registerBinder("throughTimeRange", PropertyBinder.instance(ThroughTimeRange.class, "throughTimeRange"));
        binder.registerBinder("initialMonth", PropertyBinder.instance(Month.class, "initialMonth"));
        binder.registerBinder("finalMonth", PropertyBinder.instance(Month.class, "finalMonth"));
        binder.registerBinder("initialQuarter", PropertyBinder.instance(Quarter.class, "initialQuarter"));
        binder.registerBinder("finalQuarter", PropertyBinder.instance(Quarter.class, "finalQuarter"));
        binder.registerBinder("initialYear", PropertyBinder.instance(Integer.class, "initialYear"));
        binder.registerBinder("initialMonthYear", PropertyBinder.instance(Integer.class, "initialMonthYear"));
        binder.registerBinder("initialQuarterYear", PropertyBinder.instance(Integer.class, "initialQuarterYear"));
        binder.registerBinder("finalYear", PropertyBinder.instance(Integer.class, "finalYear"));
        binder.registerBinder("finalMonthYear", PropertyBinder.instance(Integer.class, "finalMonthYear"));
        binder.registerBinder("finalQuarterYear", PropertyBinder.instance(Integer.class, "finalQuarterYear"));
        binder.registerBinder("paymentFilter", PropertyBinder.instance(PaymentFilter.class, "paymentFilter", ReferenceConverter.instance(PaymentFilter.class)));
        binder.registerBinder("paymentFilters", SimpleCollectionBinder.instance(PaymentFilter.class, "paymentFilters", ReferenceConverter.instance(PaymentFilter.class)));
        binder.registerBinder("groupFilters", SimpleCollectionBinder.instance(GroupFilter.class, "groupFilters"));
        binder.registerBinder("groups", SimpleCollectionBinder.instance(Group.class, "groups"));
        binder.registerBinder("systemAccountFilter", PropertyBinder.instance(SystemAccountType.class, "systemAccountFilter"));
        binder.registerBinder("whatToShow", PropertyBinder.instance(StatisticsWhatToShow.class, "whatToShow"));
    }

    /**
     * the Service. Child classes should assign this via
     * 
     * @inject setBlaService, calling StatisticsAction.setStatisticalService.
     */
    private StatisticalService                     statisticalService;
    private PaymentFilterService                   paymentFilterService;
    private AccountTypeService                     accountTypeService;
    private GroupFilterService                     groupFilterService;

    private DataBinder<? extends StatisticalQuery> dataBinder;

    public DataBinder<? extends StatisticalQuery> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            dataBinder = initDataBinder(settings);
        }
        return dataBinder;
    }

    /**
     * Returns the statistics type related to the action
     */
    public abstract StatisticsType getStatisticsType();

    /**
     * each subclass is forced to bind datafields via this method
     * 
     * @param settings
     */
    public abstract DataBinder<? extends StatisticalQuery> initDataBinder(final LocalSettings settings);

    /*
     * takes care that the transfer type filter box filled with appropriate transfer types. Method to be called from the prepareForm method. Currently
     * not used, but may be in future. If never used in future, then remove this and all associated.
     * 
     * @param context UNCOMMENT IF USED IN FUTURE
     */
    /*
     * protected void applyTransferTypeFilter(final ActionContext context) { final TransferTypeQuery ttQuery = resolveTransferTypeQuery(context); if
     * (ttQuery != null) { final List<TransferType> transferTypes = transferTypeService.search(ttQuery); final HttpServletRequest request =
     * context.getRequest(); request.setAttribute("transferTypeList", transferTypes); } }
     */

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Inject
    public void setPaymentFilterService(final PaymentFilterService paymentFilterService) {
        this.paymentFilterService = paymentFilterService;
    }

    /**
     * takes care that a group filter box is filled with appropriate values. Method to be called from the prepareForm method.
     * 
     * @param request
     */
    protected void applyGroupFilter(final HttpServletRequest request) {
        final GroupQuery groupQuery = new GroupQuery();
        groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
        request.setAttribute("groups", groupService.search(groupQuery));
        final GroupFilterQuery groupFilterQuery = new GroupFilterQuery();
        // no need to set anything on the GroupFilterQuery, as stats fall outside the scope of group managing permissions
        final Collection<GroupFilter> groupFilters = groupFilterService.search(groupFilterQuery);
        if (CollectionUtils.isNotEmpty(groupFilters)) {
            request.setAttribute("groupFilters", groupFilters);
        }
    }

    /**
     * takes care that the payment filter box filled with appropriate payment filters. Method to be called from the prepareForm method
     * 
     * @param request
     */
    protected void applyPaymentFilter(final HttpServletRequest request) {
        final PaymentFilterQuery pfQuery = new PaymentFilterQuery();
        pfQuery.setContext(PaymentFilterQuery.Context.REPORT);
        final List<PaymentFilter> paymentFilters = paymentFilterService.search(pfQuery);
        request.setAttribute("paymentFilterList", paymentFilters);
    }

    /**
     * takes care that the system account filter box filled with appropriate system accounts. Method to be called from the prepareForm method
     * 
     * @param request
     */
    protected void applySystemAccountFilter(final HttpServletRequest request) {
        final List<? extends AccountType> systemAccounts = accountTypeService.search(new SystemAccountTypeQuery());
        request.setAttribute("systemAccounts", systemAccounts);
    }

    /**
     * Takes care that both the named periods are filled with default values. To be called from the prepareForm method.
     * 
     * @param query
     * @param form
     */
    protected void bindPeriods(final StatisticalQuery query, final StatisticsForm form) {
        if (query.getPeriodMain().getEnd() == null) {
            final NamedPeriod periodMain = NamedPeriod.getLastQuarterPeriod();
            final NamedPeriod periodComparedTo = periodMain.getOneYearEarlier();
            bindPeriod("periodMain", form, periodMain);
            bindPeriod("periodComparedTo", form, periodComparedTo);
        }
    }

    // call this method via super() from the inhereting classes.
    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final StatisticalQuery query = (StatisticalQuery) queryParameters;
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("statisticsType", getStatisticsType());
        // If is through the time, calculate periods and set into the queryparams
        if (query.getWhatToShow() == StatisticsWhatToShow.THROUGH_TIME) {
            final ThroughTimeRange throughTimeRange = query.getThroughTimeRange();
            final Period period = getPeriodByTimeRange(query);
            final Period[] periods = DateHelper.getPeriodsThroughTheTime(period, throughTimeRange);
            query.setPeriods(periods);
        }
        // if groups are empty, but groupFilters are used, put all the groups from the groupFilter in the groups.
        final Collection<GroupFilter> groupFilters = query.getGroupFilters();
        final boolean hasGroupFilters = CollectionUtils.isNotEmpty(groupFilters);
        final boolean hasGroups = CollectionUtils.isNotEmpty(query.getGroups());
        if (hasGroupFilters && !hasGroups) {
            final Set<Group> groupsFromFilters = new HashSet<Group>();
            if (hasGroupFilters) {
                // Get all groups from selected group filters
                for (GroupFilter groupFilter : groupFilters) {
                    groupFilter = groupFilterService.load(groupFilter.getId(), GroupFilter.Relationships.GROUPS);
                    groupsFromFilters.addAll(groupFilter.getGroups());
                }
                query.setGroups(groupsFromFilters);
            }
        }

    }

    /**
     * gets the StatisticalService in its basic form as an instance of the StatisticalService class. Child classes should create a
     * getStatisticalService() method casting the StatisticalService instance to one of its subclasses
     * 
     * @return StatisticalService
     */
    protected StatisticalService getBaseStatisticalService() {
        return statisticalService;
    }

    /*
     * Create a period with its 'begin' and 'end' date corresponding to the first and second param on the period selection for months, quarters and
     * years.
     */
    protected Period getPeriodByTimeRange(final StatisticalQuery queryParameters) {
        final ThroughTimeRange throughTimeRange = queryParameters.getThroughTimeRange();
        Calendar calendarBegin = null;
        Calendar calendarEnd = null;

        if (throughTimeRange == ThroughTimeRange.MONTH) {
            calendarBegin = new GregorianCalendar(queryParameters.getInitialMonthYear(), queryParameters.getInitialMonth().getValue(), 1);
            final Calendar calendarEndAux = new GregorianCalendar(queryParameters.getFinalMonthYear(), queryParameters.getFinalMonth().getValue(), 1);
            calendarEnd = new GregorianCalendar(queryParameters.getFinalMonthYear(), queryParameters.getFinalMonth().getValue(), calendarEndAux.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);

        } else if (throughTimeRange == ThroughTimeRange.QUARTER) {
            final Quarter initialQuarter = queryParameters.getInitialQuarter();
            final Quarter finalQuarter = queryParameters.getFinalQuarter();

            calendarBegin = new GregorianCalendar(queryParameters.getInitialQuarterYear(), initialQuarter.getValue() * 3 - 3, 1);
            final Calendar calendarEndAux = new GregorianCalendar(queryParameters.getFinalQuarterYear(), finalQuarter.getValue() * 3 - 3, 1);
            calendarEnd = new GregorianCalendar(queryParameters.getFinalQuarterYear(), finalQuarter.getValue() * 3 - 3, calendarEndAux.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);

        } else if (throughTimeRange == ThroughTimeRange.YEAR) {
            calendarBegin = new GregorianCalendar(queryParameters.getInitialYear(), 0, 1);
            calendarEnd = new GregorianCalendar(queryParameters.getFinalYear(), 11, 31, 23, 59, 59);
        }

        final Period period = new Period(calendarBegin, calendarEnd);
        return period;
    }

    /**
     * This prepares the form. Some common fields are taken care of (Periods); all filters must be assigned by the descendant class prepareForm
     * method, which should call return super.prepareForm(context);
     */
    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final StatisticsForm form = context.getForm();
        final StatisticalQuery query = getDataBinder().readFromString(form.getQuery());
        bindPeriods(query, form);

        // Send enums to JSP
        final HttpServletRequest request = context.getRequest();
        RequestHelper.storeEnum(request, StatisticsWhatToShow.class, "whatToShow");
        RequestHelper.storeEnum(request, ThroughTimeRange.class, "throughTimeRange");
        RequestHelper.storeEnum(request, Month.class, "months");
        RequestHelper.storeEnum(request, Quarter.class, "quarters");

        // Set default through time range
        if (form.getQuery("throughTimeRange") == null) {
            form.setQuery("throughTimeRange", ThroughTimeRange.MONTH);
        }

        // Set default initial and final months and years
        if (form.getQuery("initialMonth") == null) {
            final Map<String, Object> completedMonthAndYear = DateHelper.getLastCompletedMonthAndYear();
            final int lastCompletedMonth = (Integer) completedMonthAndYear.get("month");
            final int lastCompletedMonthYear = (Integer) completedMonthAndYear.get("year");
            form.setQuery("initialMonth", lastCompletedMonth);
            form.setQuery("initialMonthYear", lastCompletedMonthYear - 1);
            form.setQuery("finalMonth", lastCompletedMonth);
            form.setQuery("finalMonthYear", lastCompletedMonthYear);
        }

        // Set default initial and final quarters and years
        if (form.getQuery("initialQuarter") == null) {
            final Map<String, Object> completedQuarterAndYear = DateHelper.getLastCompletedQuarterAndYear();
            final Quarter lastCompletedQuarter = (Quarter) completedQuarterAndYear.get("quarter");
            final int lastCompletedQuarterYear = (Integer) completedQuarterAndYear.get("year");
            form.setQuery("initialQuarter", lastCompletedQuarter);
            form.setQuery("initialQuarterYear", lastCompletedQuarterYear - 1);
            form.setQuery("finalQuarter", lastCompletedQuarter);
            form.setQuery("finalQuarterYear", lastCompletedQuarterYear);
        }

        // Set default initial and final years
        if (form.getQuery("initialYear") == null) {
            final Calendar date = elementService.getFirstMemberActivationDate();
            int firstYear = 0;
            if (date != null) {
                firstYear = date.get(Calendar.YEAR);
            } else {
                firstYear = Calendar.getInstance().get(Calendar.YEAR);
            }
            final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int lastYear = currentYear - 1;
            if (firstYear > lastYear) {
                // The systems started to run this year
                firstYear = currentYear;
                lastYear = currentYear;
            }
            form.setQuery("initialYear", firstYear);
            form.setQuery("finalYear", lastYear);
        }

        return query;
    }

    /**
     * This method creates the StatisticalDataProducer which will be read by the jsp. It is a factory, so dependant of the producerClass parameter, it
     * returns any subtype of StatisticalDataProducer (or StatisticalDataProducer itself).
     * 
     * @param rawDataObject the raw Data object from cyclos3
     * @param context the action context
     * @param producerClass this must be a StatisticalDataProducer or one of its subclasses. This parameter determines the type of the returned
     * DataProducer.
     * @return StatisticalDataProducer (or one of its subclasses), a wrapper type around the raw data.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected StatisticalDataProducer producerFactory(final StatisticalResultDTO rawDataObject, final ActionContext context, final Class producerClass) {
        // create a StatisticalDataProducer of the correct class by getting the
        // constructor via reflection
        final Class[] argumentClasses = new Class[] { StatisticalResultDTO.class, ActionContext.class };
        final Object[] constructorArguments = new Object[] { rawDataObject, context };
        Constructor producerConstructor = null;
        StatisticalDataProducer producer = null;
        try {
            producerConstructor = producerClass.getConstructor(argumentClasses);
            producer = (StatisticalDataProducer) producerConstructor.newInstance(constructorArguments);
        } catch (final Exception e) {
            // in case of any silly error because of the use of reflection, just
            // use the base type
            e.printStackTrace();
            producer = new StatisticalDataProducer(rawDataObject, context);
        }
        final LocalSettings settings = settingsService.getLocalSettings();
        if (producer != null) {
            producer.setSettings(settings);
        }
        return producer;
    }

    /**
     * builds the query for getting the transfer types, to fill the transfer type drop down. Not used at present, maybe in future? If not, then
     * remove.
     * 
     * @param context
     * @return always null; subclass to get a real query.
     */
    protected TransferTypeQuery resolveTransferTypeQuery(final ActionContext context) {
        return null;
    }

    /**
     * basic setter for the statistical Services, to be called by child classes with the inject annotation.
     * 
     * @param statisticalService
     */
    protected void setStatisticalService(final StatisticalService statisticalService) {
        this.statisticalService = statisticalService;
    }

    /**
     * validates the following:
     * <ul>
     * <li>if any item checkbox in the form is selected.
     * <li>if correct syntax for through time fields is entered (no end date smaller than start date; year fields not empty)
     * <li>if the maximum number of requested data points is not exceeded. This max number is set via the service, and set in the prepareform method
     * of the action
     * <li>in case the paymentFilters multi drop down is used: if the maximum number of payment filters is not exceeded.
     * <li>in case the paymentFilters multi drop down is used: if there is there is no overlap in the selected payment filters
     * <li>there is at least one payment filter selected.
     * </ul>
     * 
     */
    @Override
    protected void validateForm(final ActionContext context) {
        final StatisticsForm form = context.getForm();
        final StatisticalQuery query = getDataBinder().readFromString(form.getQuery());
        statisticalService.validate(query);
    }

    /*
     * Helper method fills a NamedPeriod instance with its default value Called from bindPeriods @param name @param form @param period
     */
    private void bindPeriod(final String name, final StatisticsForm form, final NamedPeriod period) {
        final LocalSettings settings = settingsService.getLocalSettings();
        final BeanBinder<NamedPeriod> periodBinder = DataBinderHelper.namedPeriodBinder(settings, name);
        periodBinder.writeAsString(form.getQuery(), period);
    }
}
