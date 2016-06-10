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
package nl.strohalm.cyclos.entities.accounts;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Parent class for ARateParameters and DRateParameters classes. Common property is that they can be initialized at a certain init value.
 * @author rinke
 */
public abstract class InitializableRateParameters extends RateParameters {

    private static final long serialVersionUID = -1767176919948045138L;
    private BigDecimal        initValue;
    private Calendar          initDate;

    public Calendar getInitDate() {
        return initDate;
    }

    public BigDecimal getInitValue() {
        return initValue;
    }

    public void setInitDate(final Calendar initDate) {
        this.initDate = initDate;
    }

    public void setInitValue(final BigDecimal initValue) {
        this.initValue = initValue;
    }

}
