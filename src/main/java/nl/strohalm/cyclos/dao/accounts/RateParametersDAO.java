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
package nl.strohalm.cyclos.dao.accounts;

import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.RateParameters;
import nl.strohalm.cyclos.utils.Period;

/**
 * DAO interface for generalized Rate parameters
 * 
 * @author rinke
 */
public interface RateParametersDAO<R extends RateParameters> extends BaseDAO<R>, InsertableDAO<R>, UpdatableDAO<R>, DeletableDAO<R> {

    /**
     * gets past date rateparameters for currencies. If you want present ratepareameters for currency, just use currency.getXRateParameters(). This
     * method is only useful if you need to get rateparameters of a past date which are now not valid any more on the currency.
     * @param currency
     * @param date
     * @return the RateParameters entity.
     */
    R getByDate(Currency currency, Calendar date);

    /**
     * gets a list of all rateParameters that were valid during the specified period, in reversed date order - this means ordered by enabledSince, the
     * latest entities first.
     * @param currency
     * @param period despite any settings on the period, this is always with beginDate exclusive, and endDate inclusive, because RateParameters are
     * defined with beginDate inclusive and endDate exclusive. For the same reason, the useTime flag in period is ignored, and period is always
     * treated as if this flag was set to true.
     */
    List<R> getByPeriod(Currency currency, Period period);

}
