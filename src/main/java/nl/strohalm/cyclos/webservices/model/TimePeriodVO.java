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
package nl.strohalm.cyclos.webservices.model;

import javax.xml.bind.annotation.XmlType;

/**
 * A time period representation to web services
 * @author luis
 */
@XmlType(name = "timePeriod")
public class TimePeriodVO {

    @XmlType(name = "timeField")
    public static enum TimePeriodVOField {
        DAYS, WEEKS, MONTHS;
    }

    private Integer           number;
    private TimePeriodVOField field;

    public TimePeriodVOField getField() {
        return field;
    }

    public Integer getNumber() {
        return number;
    }

    public void setField(final TimePeriodVOField field) {
        this.field = field;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "TimePeriod(number=" + number + ", field=" + field + ")";
    }
}
