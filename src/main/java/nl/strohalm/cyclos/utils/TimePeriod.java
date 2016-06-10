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
package nl.strohalm.cyclos.utils;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.DateUtils;

/**
 * Defines a time period, with a number and a field. It represents an amount of time in the specified unit, i.e.: 5 DAYS.
 * @author luis
 */
public class TimePeriod implements Serializable, Cloneable {

    /**
     * A given time field
     * @author luis
     */
    public static enum Field implements IntValuedEnum {
        MILLIS(Calendar.MILLISECOND), SECONDS(Calendar.SECOND), MINUTES(Calendar.MINUTE), HOURS(Calendar.HOUR_OF_DAY, Calendar.HOUR), DAYS(Calendar.DATE), WEEKS(Calendar.WEEK_OF_YEAR), MONTHS(Calendar.MONTH), YEARS(Calendar.YEAR);

        public static Field findByCalendarField(final int value) {
            for (final Field field : values()) {
                if (ArrayUtils.contains(field.calendarValues, value)) {
                    return field;
                }
            }
            return null;
        }

        private int[] calendarValues;

        private Field(final int calendarValue) {
            calendarValues = new int[] { calendarValue };
        }

        private Field(final int... calendarValues) {
            this.calendarValues = calendarValues;
        }

        public int getCalendarValue() {
            return calendarValues[0];
        }

        @Override
        public int getValue() {
            return getCalendarValue();
        }
    }

    /**
     * A time period of 1 month
     */
    public static final TimePeriod ONE_MONTH        = new TimePeriod(1, Field.MONTHS);

    /**
     * A time period of 1 day
     */
    public static final TimePeriod ONE_DAY          = new TimePeriod(1, Field.DAYS);

    private static final long      serialVersionUID = 859616150477032565L;

    private int                    number;
    private Field                  field;

    public TimePeriod() {
    }

    public TimePeriod(final int number, final Field field) {
        this.number = number;
        this.field = field == null ? Field.DAYS : field;
    }

    /**
     * Return a new calendar adding this time period
     */
    public Calendar add(final Calendar date) {
        if (date == null) {
            return null;
        }
        if (!isValid()) {
            return date;
        }
        final Calendar ret = (Calendar) date.clone();
        ret.add(field.getCalendarValue(), number);
        return ret;
    }

    @Override
    public TimePeriod clone() {
        try {
            return (TimePeriod) super.clone();
        } catch (final CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Returns the full period which includes the given date
     */
    public Period currentPeriod(final Calendar date) {
        final Calendar start = previousPeriod(date).getEnd();
        start.add(Calendar.SECOND, 1);
        return periodStartingAt(start);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof TimePeriod)) {
            return false;
        }
        final TimePeriod tp = (TimePeriod) obj;
        return new EqualsBuilder().append(number, tp.number).append(field, tp.field).isEquals();
    }

    public Field getField() {
        return field;
    }

    public int getNumber() {
        return number;
    }

    public float getValueIn(final Field field) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0L);
        final Calendar cal2 = add(cal);
        final float millisDiff = cal2.getTimeInMillis() - cal.getTimeInMillis();
        switch (field) {
            case MILLIS:
                return millisDiff;
            case SECONDS:
                return millisDiff / DateUtils.MILLIS_PER_SECOND;
            case MINUTES:
                return millisDiff / DateUtils.MILLIS_PER_MINUTE;
            case HOURS:
                return millisDiff / DateUtils.MILLIS_PER_HOUR;
            case DAYS:
                return millisDiff / DateUtils.MILLIS_PER_DAY;
            case WEEKS:
                return millisDiff / (DateUtils.MILLIS_PER_DAY * 7);
            case MONTHS:
                return millisDiff / (DateUtils.MILLIS_PER_DAY * 30);
            case YEARS:
                return millisDiff / (DateUtils.MILLIS_PER_DAY * 365);
        }
        return 0F;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(number).append(field).toHashCode();
    }

    public boolean isValid() {
        return number > 0 && field != null;
    }

    /**
     * Returns a period ending at the given date (not including it), with this size
     */
    public Period periodEndingAt(Calendar endDate) {
        endDate = (Calendar) endDate.clone();
        final Calendar beginDate = remove(endDate);
        endDate.add(Calendar.SECOND, -1);
        return Period.between(beginDate, endDate);
    }

    /**
     * Returns a period starting at the given date, with this size
     */
    public Period periodStartingAt(final Calendar beginDate) {
        final Calendar endDate = add(beginDate);
        endDate.add(Calendar.SECOND, -1);
        return Period.between(beginDate, endDate);
    }

    /**
     * Returns the previous full period that does not include the given date. Example: date = 2007-02-15, field = month, number = 2 returns a period
     * of 2 months from 2006-12-01 to 2007-01-31
     */
    public Period previousPeriod(final Calendar date) {
        Calendar end;
        if (field == Field.WEEKS) {
            // Weeks are not supported on DateUtils.truncate, so, go back to the last monday, and get weeks before
            end = DateHelper.truncate(date);
            while (end.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                end.add(Calendar.DATE, -1);
            }
        } else {
            end = DateUtils.truncate(date, field.getCalendarValue());
        }
        return periodEndingAt(end);
    }

    /**
     * Return a new calendar removing this time period
     */
    public Calendar remove(final Calendar date) {
        if (date == null) {
            return null;
        }
        if (!isValid()) {
            return date;
        }
        final Calendar ret = (Calendar) date.clone();
        ret.add(field.getCalendarValue(), -number);
        return ret;
    }

    public void setField(final Field field) {
        this.field = field;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number + " " + field;
    }

}
