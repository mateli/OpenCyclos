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
import java.util.GregorianCalendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.DateUtils;

/**
 * A date period, which can be configured not to take into account the time part in calculations by setting the useTime flag in false. Note that the
 * useTime in false doesn't mean that the begin and end dates have a zero time. The useTime only interfere in calculations. <br>
 * Also not that useTime==false is the default behavior. So by default the begin and end dates are truncated. If you don't want this behavior and need
 * to use the times, you must explicitly set useTime to true.<br>
 * You may also set the inclusiveBegin (default true) and inclusiveEnd (default true) flags.
 * 
 * @author luis
 */
public class Period implements Serializable, Cloneable {

    private static final long serialVersionUID = 6246167529034823152L;

    public static Period begginingAt(final Calendar begin) {
        return new Period(begin, null);
    }

    public static Period between(final Calendar begin, final Calendar end) {
        return new Period(begin, end);
    }

    public static Period betweenOneYear(final int year) {
        final Calendar begin = new GregorianCalendar(year, 0, 1);
        final Calendar end = new GregorianCalendar(year, 11, 31, 23, 59, 59);
        return between(begin, end);
    }

    public static Period day(Calendar day) {
        day = DateHelper.truncate(day);
        return new Period(day, day);
    }

    public static Period endingAt(final Calendar end) {
        return new Period(null, end);
    }

    /**
     * creates a period representing a day. If you set the setUseTime() flag afterwards, then it will represent a point in time.
     * @param time
     * @return
     */
    public static Period exact(final Calendar time) {
        return new Period(time, time);
    }

    private Calendar begin;
    private Calendar end;
    private boolean  useTime;
    private boolean  inclusiveBegin = true;
    private boolean  inclusiveEnd   = true;

    public Period() {
    }

    public Period(final Calendar begin, final Calendar end) {
        setBegin(begin);
        setEnd(end);
    }

    @Override
    public Period clone() {
        Period newPeriod;
        try {
            newPeriod = (Period) super.clone();
        } catch (final CloneNotSupportedException e) {
            // this should never happen, since it is Cloneable
            throw new InternalError(e.getMessage());
        }
        newPeriod.begin = begin == null ? null : (Calendar) begin.clone();
        newPeriod.end = end == null ? null : (Calendar) end.clone();

        return newPeriod;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Period)) {
            return false;
        }
        final Period p = (Period) obj;
        return new EqualsBuilder().append(begin, p.begin).append(end, p.end).isEquals();
    }

    public Calendar getBegin() {
        return begin;
    }

    public Quarter getBeginQuarter() {
        return getQuarter(begin);
    }

    /**
     * Returns the number of days between the begin and end date. If either is null, returns -1.
     * @see DateHelper#daysBetween(Calendar, Calendar)
     */
    public int getDays() {
        if (begin == null || end == null) {
            return -1;
        }
        return DateHelper.daysBetween(begin, end);
    }

    /**
     * @return The difference between beginDate and endDate from period in seconds
     */
    public long getDifference() {
        if (begin == null || end == null) {
            throw new IllegalStateException("Not a complete period: " + this);
        }

        final double millis = end.getTimeInMillis() - begin.getTimeInMillis();
        return (long) Math.ceil(millis / DateUtils.MILLIS_PER_SECOND);
    }

    public Calendar getEnd() {
        return end;
    }

    public Quarter getEndQuarter() {
        return getQuarter(end);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(begin).append(end).toHashCode();
    }

    /**
     * Checks whether the given date is included in this period. When the useTime flag is true, the time of the parameter (date) and the time of the
     * begin and end dates of the period are taken into account to compute the result. If the exact begin date/time is included depends on the
     * <code>inclusiveBegin</code> flag; by default, this is true, so begin date/time is considered to be <strong>included</strong> in the period. If
     * the exact end date/time is included depends on the <code>inclusiveEnd</code> flag; which behaves in the same way as the inclusiveBegin flag.
     * Both are true by default.<br>
     * When the useTime flag is false, the dates are truncated to compute the result, and the inclusiveBegin and inclusiveEnd flags are ignored.
     */
    public boolean includes(final Calendar date) {
        if (date == null) {
            return false;
        } else if (begin == null && end == null) {
            return true;
        } else {
            if (useTime) {
                if (begin == null) {
                    return (inclusiveEnd) ? !end.before(date) : date.before(end);
                } else if (end == null) {
                    return (inclusiveBegin) ? !date.before(begin) : begin.before(date);
                } else {
                    final boolean beginOK = (inclusiveBegin) ? !date.before(begin) : begin.before(date);
                    final boolean endOK = (inclusiveEnd) ? !end.before(date) : date.before(end);
                    return beginOK && endOK;
                }
            } else {
                final Calendar tDate = DateUtils.truncate(date, Calendar.DATE);
                Calendar tBegin = begin;
                Calendar tEnd = end;

                if (begin != null) {
                    tBegin = DateUtils.truncate(begin, Calendar.DATE);
                }
                if (end != null) {
                    // If not using time, we'll assume the end of the interval is
                    // the instant before the next day.
                    tEnd = DateHelper.truncateNextDay(end);
                }

                if (tBegin == null) {
                    // it's included if the date is an instant before the next day.
                    return tDate.before(tEnd);
                } else if (tEnd == null) {
                    // it's included if the date is not before the begin
                    return !tDate.before(tBegin);
                } else {
                    return !tDate.before(tBegin) && tDate.before(tEnd);
                }
            }
        }
    }

    public boolean isInclusiveBegin() {
        return inclusiveBegin;
    }

    public boolean isInclusiveEnd() {
        return inclusiveEnd;
    }

    public boolean isUseTime() {
        return useTime;
    }

    public void setBegin(final Calendar begin) {
        this.begin = begin;
    }

    public void setEnd(final Calendar end) {
        this.end = end;
    }

    public void setInclusiveBegin(final boolean inclusiveBegin) {
        this.inclusiveBegin = inclusiveBegin;
    }

    public void setInclusiveEnd(final boolean inclusiveEnd) {
        this.inclusiveEnd = inclusiveEnd;
    }

    public void setUseTime(final boolean useTime) {
        this.useTime = useTime;
    }

    @Override
    public String toString() {
        return "begin: " + FormatObject.formatObject(begin, "<null>") + ", end: " + FormatObject.formatObject(end, "<null>");
    }

    public Period useTime() {
        useTime = true;
        return this;
    }

    private Quarter getQuarter(final Calendar cal) {
        final int month = cal.get(Calendar.MONTH);
        int quarter = month / 3;
        quarter++;
        return Quarter.getQuarter(quarter);
    }
}
