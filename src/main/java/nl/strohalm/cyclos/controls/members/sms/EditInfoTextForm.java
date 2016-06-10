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
package nl.strohalm.cyclos.controls.members.sms;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * @author hselvaggi
 */
public class EditInfoTextForm extends BaseBindingForm {

    private static final long serialVersionUID = 1L;
    private Long              infoTextId;

    public EditInfoTextForm() {
        values.put("validity", new MapBean("begin", "end"));
    }

    public Map<String, Object> getInfoText() {
        return values;
    }

    public Object getInfoText(final String key) {
        return values.get(key);
    }

    public Long getInfoTextId() {
        return infoTextId;
    }

    public void setInfoText(final Map<String, Object> data) {
        values = data;
    }

    public void setInfoText(final String key, final Object value) {
        values.put(key, value);
    }

    public void setInfoTextId(final Long infoTextId) {
        this.infoTextId = infoTextId;
    }
}
