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
package nl.strohalm.cyclos.controls.reports.simulations;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * form for the configuration simulation of the DRate.
 * @author Rinke
 */
public class RateConfigSimulationForm extends BaseBindingForm {

    private static final long serialVersionUID = 6928407013721816158L;
    private boolean           reloadData;

    public Map<String, Object> getSimulation() {
        return values;
    }

    public Object getSimulation(final String key) {
        return values.get(key);
    }

    public boolean isReloadData() {
        return reloadData;
    }

    public void setSimulation(final String key, final Object value) {
        values.put(key, value);
    }

    public void setReloadData(final boolean reloadData) {
        this.reloadData = reloadData;
    }

    public void setSimulation(final Map<String, Object> map) {
        values = map;
    }

}
