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

/**
 * Enumeration containing all weekdays
 * @author luis
 */
public enum WeekDay implements IntValuedEnum {
    SUNDAY(Calendar.SUNDAY), MONDAY(Calendar.MONDAY), THURSDAY(Calendar.THURSDAY), WEDNESDAY(Calendar.WEDNESDAY), TUESDAY(Calendar.TUESDAY), FRIDAY(Calendar.FRIDAY), SATURDAY(Calendar.SATURDAY);

    public static WeekDay valueOf(final int number) {
        for (final WeekDay weekDay : values()) {
            if (weekDay.value == number) {
                return weekDay;
            }
        }
        throw new IllegalArgumentException("Invalid weekday: " + number);
    }

    private final int value;

    private WeekDay(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
