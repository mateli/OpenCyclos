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
package nl.strohalm.cyclos.controls.members.printsettings;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Form used to edit a receipt printer settings
 * 
 * @author luis
 */
public class EditReceiptPrinterSettingsForm extends BaseBindingForm {

    private static final long serialVersionUID = 3936161176106633986L;

    public Long getId() {
        return CoercionHelper.coerce(Long.class, values.get("id"));
    }

    public Map<String, Object> getReceiptPrinterSettings() {
        return values;
    }

    public Object getReceiptPrinterSettings(final String key) {
        return values.get(key);
    }

    public void setId(final Long id) {
        values.put("id", id);
    }

    public void setReceiptPrinterSettings(final Map<String, Object> values) {
        this.values = values;
    }

    public void setReceiptPrinterSettings(final String key, final Object value) {
        values.put(key, value);
    }

}
