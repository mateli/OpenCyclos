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
package nl.strohalm.cyclos.services.stats.general;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains the data for one month inside a through time range for key developments statistics, for one not specified numerical field. For each
 * requested period, the result of an all-transaction-data-in-one query is saved into this class. The class contains the following data:
 * <ul>
 * <li>the not specified data to get
 * <li>the month
 * <li>the year
 * </ul>
 * It may be used on any kind of data, for example on number of members in a month, number of transactions in a month, or gross product in a month.
 * The not specified data field will be used for this.
 * 
 * @author rinke
 * 
 */
public class KeyDevelopmentsStatsPerMonthVO extends DataObject {

    private static final long serialVersionUID = -6234220215328256820L;
    private int               month;
    private int               year;
    private Number            dataField;

    public KeyDevelopmentsStatsPerMonthVO(final Number data, final int month, final int year) {
        dataField = data;
        this.month = month;
        this.year = year;
    }

    public Number getDataField() {
        return dataField;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDataField(final Number dataField) {
        this.dataField = dataField;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public void setYear(final int year) {
        this.year = year;
    }

}
