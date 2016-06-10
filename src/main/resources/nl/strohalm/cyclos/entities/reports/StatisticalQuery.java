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
package nl.strohalm.cyclos.entities.reports;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.utils.Month;
import nl.strohalm.cyclos.utils.NamedPeriod;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.Quarter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * parameters for Statistical queries. This is the base type; each specific query for the statistics is a child class of this.
 * <p>
 * In order to have correct form validation, the child classes of <code>StatisticalQuery</code> <b>MUST</b> declare the getters of the form checkboxes
 * as a method starting the name with "is", amd returning a boolean. Any method like this will be considered as a getter for a checkbox element on the
 * form. Graph checkboxes must have a getter returning a boolean, and a method name starting with "is" and ending with "Graph".
 * <p>
 * <p>
 * Note that there is a problem with using <code>paymentFilter<b>s</b></code> in a form. This is a drop down where a user can select one or more
 * <code>paymentFilter</code>s. However, <code>paymentFilter</code>s are not mutually exclusive, and two different <code>paymentFilter</code>s may
 * show overlap, when they are both sharing one and the same <code>transferType</code>.<br>
 * This would create at best misleading graphs, at worst (for example in pie charts) it would make graph rendering impossible. Therefore, the
 * following business rules apply for the use of the multi drop down <code>paymentFilters</code>:
 * <ul>
 * <li>When more than one item is selected, <code>paymentFilter</code>s will be tested on mutual exclusivity. If there is overlap the items in the
 * selection, then the form should not accept this.
 * <li>Selecting one item will result in splitting up the <code>paymentFilter</code> to its containing <code>TransferType</code>s.
 * </ul>
 * 
 * @author Rinke
 * 
 */
public class StatisticalQuery extends QueryParameters {

    private static final long         serialVersionUID = 30870554769291883L;
    private NamedPeriod               periodMain;
    private NamedPeriod               periodComparedTo;
    private StatisticsWhatToShow      whatToShow;
    private ThroughTimeRange          throughTimeRange;
    private Month                     initialMonth;
    private Month                     finalMonth;
    private Quarter                   initialQuarter;
    private Quarter                   finalQuarter;
    private Integer                   initialYear;
    private Integer                   initialMonthYear;
    private Integer                   initialQuarterYear;
    private Integer                   finalYear;
    private Integer                   finalMonthYear;
    private Integer                   finalQuarterYear;
    private PaymentFilter             paymentFilter;
    private Collection<PaymentFilter> paymentFilters;
    private Collection<GroupFilter>   groupFilters;
    private Collection<Group>         groups;
    private SystemAccountType         systemAccountFilter;
    private Period[]                  periods;

    /**
     * checks if any graph is checked. The method tests for any of the following conditions on a method:
     * <ul>
     * <li>method is declared, and not inherited from a super class
     * <li>mthod name starting with "is"
     * <li>method name ending with "Graph"
     * <li>return type is boolean
     * <li>no parameters accepted
     * <li>method must return true.
     * </ul>
     * .
     * <p>
     * If there is at least one of such a method, the <code>anyGraphChecked()</code> method will return true. (Calls the to this super class not yet
     * known subclass methods via reflection).
     * 
     * @return true if any graph is checked
     */
    public boolean anyGraphChecked() {
        final Class<? extends StatisticalQuery> cl = this.getClass();
        for (final Method m : cl.getDeclaredMethods()) {
            final String methodName = m.getName();
            final Class<?> rt = m.getReturnType();
            if (methodName.startsWith("is") && methodName.endsWith("Graph") && rt == boolean.class && m.getParameterTypes().length == 0) {
                try {
                    // in case of any exception connected to this invocation, be strict and return true.
                    if ((Boolean) m.invoke(this)) {
                        return true;
                    }
                } catch (final IllegalArgumentException e) {
                    // as it is checked for zero params, this cannot happen, so exception is swallowed.
                } catch (final IllegalAccessException e) {
                    return true;
                } catch (final InvocationTargetException e) {
                    // normally, a simple graph getter would not throw an exception, but to be on the safe side, just act like it is a graph setter if
                    // this exception occurs
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * counts how many items (= subjects to calculate stats upon, like "gross product", "number of members", etc) are checked for this statistics, by
     * running over all declared methods. If the following conditions are met for the method, it is considered a checked item:
     * <ul>
     * <li>method name starts with "is"
     * <li>method name does not end with "Graph"
     * <li>method does not accept params
     * <li>method return type is boolean
     * <li>method returns true
     * </ul>
     * 
     * @return the number of items requested, according to the above conditions.
     * @throws IllegalAccessException if something goes wrong with the invokation of methods via reflection
     */
    public int countItemsChecked() throws IllegalAccessException {
        final Class<? extends StatisticalQuery> cl = this.getClass();
        int itemsCheckedCounter = 0;
        for (final Method m : cl.getDeclaredMethods()) {
            final String methodName = m.getName();
            final Class<?> rt = m.getReturnType();
            if (methodName.startsWith("is") && !methodName.endsWith("Graph") && rt == boolean.class && m.getParameterTypes().length == 0) {
                try {
                    if ((Boolean) m.invoke(this)) {
                        itemsCheckedCounter++;
                    }
                } catch (final IllegalArgumentException e) {
                    // as no params are passed, and as this is checked for, this cannot happen, so the exception is swallowed
                } catch (final InvocationTargetException e) {
                    // as a normal simple getter does not throw an exception, it is assumed that this was not a getter. So don't count it, swallow,
                    // and continue the loop
                }
            }
        }
        return itemsCheckedCounter;
    }

    public Month getFinalMonth() {
        return finalMonth;
    }

    public Integer getFinalMonthYear() {
        return finalMonthYear;
    }

    public Quarter getFinalQuarter() {
        return finalQuarter;
    }

    public Integer getFinalQuarterYear() {
        return finalQuarterYear;
    }

    public Integer getFinalYear() {
        return finalYear;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public Month getInitialMonth() {
        return initialMonth;
    }

    public Integer getInitialMonthYear() {
        return initialMonthYear;
    }

    public Quarter getInitialQuarter() {
        return initialQuarter;
    }

    public Integer getInitialQuarterYear() {
        return initialQuarterYear;
    }

    public Integer getInitialYear() {
        return initialYear;
    }

    public PaymentFilter getPaymentFilter() {
        return paymentFilter;
    }

    public Collection<PaymentFilter> getPaymentFilters() {
        return paymentFilters;
    }

    public NamedPeriod getPeriodComparedTo() {
        return periodComparedTo;
    }

    public NamedPeriod getPeriodMain() {
        return periodMain;
    }

    public Period[] getPeriods() {
        return periods;
    }

    public SystemAccountType getSystemAccountFilter() {
        return systemAccountFilter;
    }

    public ThroughTimeRange getThroughTimeRange() {
        return throughTimeRange;
    }

    public StatisticsWhatToShow getWhatToShow() {
        return whatToShow;
    }

    public void setFinalMonth(final Month finalMonth) {
        this.finalMonth = finalMonth;
    }

    public void setFinalMonthYear(final Integer finalMonthYear) {
        this.finalMonthYear = finalMonthYear;
    }

    public void setFinalQuarter(final Quarter finalQuarter) {
        this.finalQuarter = finalQuarter;
    }

    public void setFinalQuarterYear(final Integer finalQuarterYear) {
        this.finalQuarterYear = finalQuarterYear;
    }

    public void setFinalYear(final Integer finalYear) {
        this.finalYear = finalYear;
    }

    public void setGroups(final Collection<Group> groups) {
        this.groups = groups;
    }

    public void setInitialMonth(final Month initialMonth) {
        this.initialMonth = initialMonth;
    }

    public void setInitialMonthYear(final Integer initialMonthYear) {
        this.initialMonthYear = initialMonthYear;
    }

    public void setInitialQuarter(final Quarter initialQuarter) {
        this.initialQuarter = initialQuarter;
    }

    public void setInitialQuarterYear(final Integer initialQuarterYear) {
        this.initialQuarterYear = initialQuarterYear;
    }

    public void setInitialYear(final Integer initialYear) {
        this.initialYear = initialYear;
    }

    public void setPaymentFilter(final PaymentFilter paymentFilter) {
        this.paymentFilter = paymentFilter;
    }

    public void setPaymentFilters(final Collection<PaymentFilter> paymentFilters) {
        this.paymentFilters = paymentFilters;
    }

    public void setPeriodComparedTo(final NamedPeriod periodComparedTo) {
        this.periodComparedTo = periodComparedTo;
    }

    public void setPeriodMain(final NamedPeriod periodMain) {
        this.periodMain = periodMain;
    }

    public void setPeriods(final Period[] periods) {
        this.periods = periods;
    }

    public void setSystemAccountFilter(final SystemAccountType systemAccountFilter) {
        this.systemAccountFilter = systemAccountFilter;
    }

    public void setThroughTimeRange(final ThroughTimeRange throughTimeRange) {
        this.throughTimeRange = throughTimeRange;
    }

    public void setWhatToShow(final StatisticsWhatToShow whatToShow) {
        this.whatToShow = whatToShow;
    }

    public Collection<GroupFilter> getGroupFilters() {
        return groupFilters;
    }

    public void setGroupFilters(Collection<GroupFilter> groupFilters) {
        this.groupFilters = groupFilters;
    }

}
