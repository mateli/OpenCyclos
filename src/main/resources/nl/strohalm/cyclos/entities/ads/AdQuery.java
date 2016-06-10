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
package nl.strohalm.cyclos.entities.ads;

import java.util.Calendar;

/**
 * Parameters for ad queries
 * @author luis
 */
public class AdQuery extends AbstractAdQuery {
    private static final long serialVersionUID = 6675148879120711265L;
    private Calendar          historyDate;
    private boolean           randomOrder;
    private boolean           skipOrder;
    private boolean           includeDeleted;
    private Calendar          beginDate;
    private Calendar          endDate;

    public Calendar getBeginDate() {
        return beginDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public Calendar getHistoryDate() {
        return historyDate;
    }

    public boolean isIncludeDeleted() {
        return includeDeleted;
    }

    public boolean isRandomOrder() {
        return randomOrder;
    }

    public boolean isSkipOrder() {
        return skipOrder;
    }

    public void setBeginDate(final Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(final Calendar endDate) {
        this.endDate = endDate;
    }

    public void setHistoryDate(final Calendar historyDate) {
        this.historyDate = historyDate;
    }

    public void setIncludeDeleted(final boolean includeDeleted) {
        this.includeDeleted = includeDeleted;
    }

    public void setRandomOrder(final boolean randomOrder) {
        this.randomOrder = randomOrder;
    }

    public void setSkipOrder(final boolean skipOrder) {
        this.skipOrder = skipOrder;
    }

}
