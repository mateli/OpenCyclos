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
package nl.strohalm.cyclos.controls.payments.conversionsimulation;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to simulate a conversion
 * @author luis
 */
public class SimulateConversionForm extends BaseBindingForm {

    private static final long serialVersionUID = 1872342617229725002L;
    private boolean           advanced;
    private long              memberId;
    private boolean           reloadData;

    public long getMemberId() {
        return memberId;
    }

    public Map<String, Object> getSimulation() {
        return values;
    }

    public Object getSimulation(final String key) {
        return values.get(key);
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public boolean isReloadData() {
        return reloadData;
    }

    public void setAdvanced(final boolean advanced) {
        this.advanced = advanced;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setReloadData(final boolean reloadData) {
        this.reloadData = reloadData;
    }

    public void setSimulation(final Map<String, Object> map) {
        values = map;
    }

    public void setSimulation(final String key, final Object value) {
        values.put(key, value);
    }

}
