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
package nl.strohalm.cyclos.controls.customization.themes;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to export a theme
 * @author luis
 */
public class ExportThemeForm extends BaseBindingForm {

    private static final long serialVersionUID = -3939770402845289166L;

    public ExportThemeForm() {
        setTheme("styles", Collections.emptyList());
    }

    public Map<String, Object> getTheme() {
        return values;
    }

    public Object getTheme(final String key) {
        return values.get(key);
    }

    public void setTheme(final Map<String, Object> map) {
        values = map;
    }

    public void setTheme(final String key, final Object value) {
        values.put(key, value);
    }

}
