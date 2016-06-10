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
package nl.strohalm.cyclos.utils.binding;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.NamedPeriod;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.query.PageParameters;

/**
 * Helper class for data binders
 * @author luis
 */
public final class DataBinderHelper {

    private static final Class<Map<String, Object>> MAP_CLASS = ClassHelper.cast(Map.class);

    /**
     * Returns a data binder for a Map to allow reading account types as a Map with the properties: id, name and currency id
     */
    public static BeanBinder<Map<String, Object>> accountTypeBinder() {
        final BeanBinder<Map<String, Object>> binder = BeanBinder.instance(MAP_CLASS);
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        binder.registerBinder("currencyId", PropertyBinder.instance(Currency.class, "currency"));
        return binder;
    }

    /**
     * Returns an Amount binder
     */
    public static BeanBinder<Amount> amountConverter(final String name, final LocalSettings localSettings) {
        final BeanBinder<Amount> binder = BeanBinder.instance(Amount.class, name);
        binder.registerBinder("type", PropertyBinder.instance(Amount.Type.class, "type"));
        binder.registerBinder("value", PropertyBinder.instance(BigDecimal.class, "value", localSettings.getNumberConverter()));
        return binder;
    }

    /**
     * Returns a data binder for a Map to allow reading members as a Map with the properties: username, name, email and group name and group id
     */
    public static BeanBinder<Map<String, Object>> elementBinder() {
        return elementBinder(null);
    }

    /**
     * Returns a data binder for a Map to allow reading members as a Map with the properties: username, name, email and group name and group id
     */
    public static BeanBinder<Map<String, Object>> elementBinder(final String path) {
        final BeanBinder<Map<String, Object>> binder = simpleElementBinder(path);
        binder.registerBinder("group", PropertyBinder.instance(String.class, "group.name"));
        binder.registerBinder("groupId", PropertyBinder.instance(String.class, "group.id"));
        binder.registerBinder("email", PropertyBinder.instance(String.class, "email"));
        return binder;
    }

    /**
     * Returns a high precision amount binder
     */
    public static BeanBinder<Amount> highPrecisionAmountConverter(final String name, final LocalSettings localSettings) {
        final BeanBinder<Amount> binder = BeanBinder.instance(Amount.class, name);
        binder.registerBinder("type", PropertyBinder.instance(Amount.Type.class, "type"));
        binder.registerBinder("value", PropertyBinder.instance(BigDecimal.class, "value", localSettings.getHighPrecisionConverter()));
        return binder;
    }

    /**
     * Returns a data binder for a Map to allow reading transfer types as a Map with the properties: loanType, expirationDailyInterest, expirationFee,
     * grantFee, monthlyInterest, repaymentDays
     */
    public static BeanBinder<Map<String, Object>> loanParametersBinder(final LocalSettings settings) {
        final BeanBinder<Map<String, Object>> binder = BeanBinder.instance(MAP_CLASS);
        binder.registerBinder("loanType", PropertyBinder.instance(Loan.Type.class, "type"));
        binder.registerBinder("expirationDailyInterest", PropertyBinder.instance(Amount.class, "expirationDailyInterestAmount", settings.getAmountConverter()));
        binder.registerBinder("expirationFee", PropertyBinder.instance(Amount.class, "expirationFee", settings.getAmountConverter()));
        binder.registerBinder("grantFee", PropertyBinder.instance(Amount.class, "grantFee", settings.getAmountConverter()));
        binder.registerBinder("monthlyInterest", PropertyBinder.instance(Amount.class, "monthlyInterestAmount", settings.getAmountConverter()));
        binder.registerBinder("repaymentDays", PropertyBinder.instance(Integer.class, "repaymentDays"));
        return binder;
    }

    /**
     * Returns a data binder for named periods
     */
    public static BeanBinder<NamedPeriod> namedPeriodBinder(final LocalSettings localSettings, final String name) {
        final BeanBinder<NamedPeriod> binder = BeanBinder.instance(NamedPeriod.class, name);
        binder.registerBinder("begin", PropertyBinder.instance(Calendar.class, "begin", localSettings.getDateConverter()));
        binder.registerBinder("end", PropertyBinder.instance(Calendar.class, "end", localSettings.getDateConverter()));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        return binder;
    }

    /**
     * Returns a data binder for pagination
     */
    public static BeanBinder<PageParameters> pageBinder() {
        final BeanBinder<PageParameters> binder = BeanBinder.instance(PageParameters.class, "pageParameters");
        binder.registerBinder("currentPage", PropertyBinder.instance(Integer.TYPE, "currentPage"));
        return binder;
    }

    /**
     * Returns a data binder for periods
     */
    public static BeanBinder<Period> periodBinder(final LocalSettings localSettings, final String name) {
        final BeanBinder<Period> binder = BeanBinder.instance(Period.class, name);
        binder.registerBinder("begin", PropertyBinder.instance(Calendar.class, "begin", localSettings.getDateConverter()));
        binder.registerBinder("end", PropertyBinder.instance(Calendar.class, "end", localSettings.getDateConverter()));
        return binder;
    }

    /**
     * Returns a data binder to a range constraint
     */
    public static BeanBinder<RangeConstraint> rangeConstraintBinder(final String name) {
        final BeanBinder<RangeConstraint> binder = BeanBinder.instance(RangeConstraint.class, name);
        binder.registerBinder("min", PropertyBinder.instance(Integer.TYPE, "min"));
        binder.registerBinder("max", PropertyBinder.instance(Integer.TYPE, "max"));
        return binder;
    }

    /**
     * Returns a data binder for periods, ignoring timezone
     */
    public static BeanBinder<Period> rawPeriodBinder(final LocalSettings localSettings, final String name) {
        final BeanBinder<Period> binder = BeanBinder.instance(Period.class, name);
        binder.registerBinder("begin", PropertyBinder.instance(Calendar.class, "begin", localSettings.getRawDateConverter()));
        binder.registerBinder("end", PropertyBinder.instance(Calendar.class, "end", localSettings.getRawDateConverter()));
        return binder;
    }

    /**
     * Returns a data binder for a Map to allow reading members as a Map with the properties: username, name
     */
    public static BeanBinder<Map<String, Object>> simpleElementBinder() {
        return simpleElementBinder(null);
    }

    /**
     * Returns a data binder for a Map to allow reading members as a Map with the properties: username, name
     */
    public static BeanBinder<Map<String, Object>> simpleElementBinder(final String path) {
        final BeanBinder<Map<String, Object>> binder = BeanBinder.instance(MAP_CLASS, path);
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("username", PropertyBinder.instance(String.class, "user.username"));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        return binder;
    }

    /**
     * Returns a data binder for a TimePeriod
     */
    public static BeanBinder<TimePeriod> timePeriodBinder(final String name) {
        final BeanBinder<TimePeriod> binder = BeanBinder.instance(TimePeriod.class, name);
        binder.registerBinder("field", PropertyBinder.instance(TimePeriod.Field.class, "field"));
        binder.registerBinder("number", PropertyBinder.instance(Integer.TYPE, "number"));
        return binder;
    }

    /**
     * Returns a data binder for a Map to allow reading transfer types as a Map with the properties: id and name
     */
    public static BeanBinder<Map<String, Object>> transferTypeBinder() {
        final Class<Map<String, Object>> type = ClassHelper.cast(Map.class);
        final BeanBinder<Map<String, Object>> binder = BeanBinder.instance(type);
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        return binder;
    }

}
