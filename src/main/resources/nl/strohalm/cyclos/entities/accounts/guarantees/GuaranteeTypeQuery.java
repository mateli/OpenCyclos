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
package nl.strohalm.cyclos.entities.accounts.guarantees;

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Parameters for guarantee types queries
 * @author Jefferson Magno
 */
public class GuaranteeTypeQuery extends QueryParameters {

    private static final long               serialVersionUID = -2935538452336699158L;
    private boolean                         enabled;
    private Collection<Currency>            currencies;
    private Collection<GuaranteeType.Model> models;

    public Collection<Currency> getCurrencies() {
        return currencies;
    }

    public Collection<GuaranteeType.Model> getModels() {
        return models;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setCurrencies(final Collection<Currency> currencies) {
        this.currencies = currencies;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setModels(final Collection<GuaranteeType.Model> models) {
        this.models = models;
    }
}
