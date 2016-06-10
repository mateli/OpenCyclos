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

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A date period which can be given a name (currently used in stats, where two periods are compared). Inherits most of its functionality directly from
 * Period.
 * @author rinke
 */
public class NamedPeriod extends Period {

    private static final long serialVersionUID = 474875095971518438L;

    /**
     * takes today, and calculates which quarter is the last one which has been completed. example: if today is 14 octobre, the last completed quarter
     * of the year is july to september, so the method returns a 3.
     * @return int quarter, being 1, 2, 3 or 4, for jan-march, april-june, july-sept and oct-dec.
     */
    public static int getLastQuarter() {
        final Calendar today = Calendar.getInstance();
        return getLastQuarter(today);
    }

    /**
     * See getLastQuarter() without a parameter. This version does not take today as starting point, but an arbitrary date, which is fed as a
     * parameter
     * @param cal : the date for which you want to know the last completed quarter of the year
     * @return the number of the quarter being 1, 2, 3 or 4, for jan-march, april-june, july-sept and oct-dec.
     */
    public static int getLastQuarter(final Calendar cal) {
        final int month = cal.get(Calendar.MONTH);
        final int quarter = (month / 3) + 1;
        return quarter;
    }

    /**
     * @see #getQuarterPeriod(Calendar)
     * @return a quarterly period, the last one falling before today
     */
    public static NamedPeriod getLastQuarterPeriod() {
        final Calendar today = Calendar.getInstance();
        return NamedPeriod.getQuarterPeriod(today);
    }

    /**
     * Makes a defaultperiod for the form. It calculates the last quarter which was finished via the getLastQuarter() method. Then it calculates the
     * starting and ending day for this period. However, if the needed quarter is the last of a year, then it returns a whole year, in stead of a
     * whole quarter. In this case, the enddate is the same as it would have been with the quarter. The name of the Period is generated via the
     * produceName method
     * @param aDate returns the last completed quarter which falls before this date. So if this date is january 1st, the method returns quarter 4 of
     * the previous year (octobre 1st to decembre 31st)
     * @return a quarterly period
     * @see #getLastQuarter()
     * @see #produceName(int, int)
     */
    public static NamedPeriod getQuarterPeriod(final Calendar aDate) {
        int year = aDate.get(Calendar.YEAR);
        final int quarter = getLastQuarter(aDate);
        final int endMonth = (3 * quarter) - 3;
        final Calendar endDay = new GregorianCalendar(year, endMonth, 1, 0, 0, 0);
        final Calendar startDay = (Calendar) endDay.clone();
        if (quarter == 1) {
            startDay.add(Calendar.YEAR, -1);
            year--;
        } else {
            startDay.add(Calendar.MONTH, -3);
        }
        endDay.add(Calendar.MILLISECOND, -1); // do not include the endday, by distracting one millisecond from the first of next month
        final NamedPeriod period = new NamedPeriod(startDay, endDay, produceName(year, quarter));
        return period;
    }

    /**
     * makes a name, based on the year and the quarter (so: "1997 II" for example). In the last quarter, the default period is a whole year, so then
     * it will be just "1997"
     * @param year
     * @param quarter
     * @return the name for the NamedPeriod
     */
    private static String produceName(final int year, final int quarter) {
        String name = "";
        for (int i = quarter - 1; i > 0; i--) {
            name = name + "I";
        }
        name = ("" + year + " " + name).trim();
        return name;
    }

    /**
     * name of the Period, to be displayed in the result jsp
     */
    private String name;

    /**
     * default constructor
     * 
     */
    public NamedPeriod() {
        super();
    }

    /**
     * constructor with begin and end date
     * @param begin the start date of the period
     * @param end the end date of the period
     */
    public NamedPeriod(final Calendar begin, final Calendar end) {
        super(begin, end);
    }

    /**
     * constructor with begin and enddate, plus a name
     * @param begin the start date of the period
     * @param end the end date of the period
     * @param name the name of the period
     */
    public NamedPeriod(final Calendar begin, final Calendar end, final String name) {
        this(begin, end);
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof NamedPeriod)) {
            return false;
        }
        final NamedPeriod p = (NamedPeriod) obj;
        return new EqualsBuilder().append(getBegin(), p.getBegin()).append(getEnd(), p.getEnd()).isEquals();
    };

    public String getName() {
        return name;
    }

    /**
     * @return a new NamedPeriod, exactly one year earlier than the present period If the 4 digit year is part of the name, then the substring
     * representing the year is adapted to the previous year. Otherwise, the name is just copied.
     */
    public NamedPeriod getOneYearEarlier() {
        final Calendar beginDate = (Calendar) getBegin().clone();
        beginDate.add(Calendar.YEAR, -1);
        final Calendar endDate = (Calendar) getEnd().clone();
        endDate.add(Calendar.YEAR, -1);
        final int presentYear = getBegin().get(Calendar.YEAR);
        String newName = getName();
        if (getName().indexOf(String.valueOf(presentYear)) >= 0) {
            newName = getName().replaceAll(String.valueOf(presentYear), String.valueOf(presentYear - 1));
        }
        return new NamedPeriod(beginDate, endDate, newName);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getBegin()).append(getEnd()).toHashCode();
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + ", name: " + FormatObject.formatObject(name, "<null>");
    }

}
