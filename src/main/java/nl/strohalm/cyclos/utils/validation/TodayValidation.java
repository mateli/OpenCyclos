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
package nl.strohalm.cyclos.utils.validation;

import java.util.Calendar;

import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Abstract Property validation
 * 
 * @author luis
 */
public class TodayValidation implements PropertyValidation {

    private static final long            serialVersionUID = -4427502602052617578L;
    private static final TodayValidation PAST             = new TodayValidation(true, false, false);
    private static final TodayValidation PAST_OR_TODAY    = new TodayValidation(true, true, false);
    private static final TodayValidation FUTURE           = new TodayValidation(false, false, true);
    private static final TodayValidation FUTURE_OR_TODAY  = new TodayValidation(false, true, true);

    public static TodayValidation future() {
        return FUTURE;
    }

    public static TodayValidation futureOrToday() {
        return FUTURE_OR_TODAY;
    }

    public static TodayValidation past() {
        return PAST;
    }

    public static TodayValidation pastOrToday() {
        return PAST_OR_TODAY;
    }

    private final boolean allowPast;
    private final boolean allowToday;
    private final boolean allowFuture;

    public TodayValidation(final boolean allowPast, final boolean allowToday, final boolean allowFuture) {
        this.allowPast = allowPast;
        this.allowToday = allowToday;
        this.allowFuture = allowFuture;
    }

    public ValidationError validate(final Object object, final Object property, final Object value) {
        final Calendar calendar = DateHelper.truncate(CoercionHelper.coerce(Calendar.class, value));
        if (calendar == null) {
            return null;
        }
        final Calendar today = DateHelper.truncate(Calendar.getInstance());
        final int comparision = calendar.compareTo(today);
        if (allowPast && comparision < 0 || allowToday && comparision == 0 || allowFuture && comparision > 0) {
            return null;
        }
        return new InvalidError();
    }

}
