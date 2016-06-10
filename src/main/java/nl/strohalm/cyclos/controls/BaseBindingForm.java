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
package nl.strohalm.cyclos.controls;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

/**
 * Base classes for forms that use a databinder
 * @author luis
 */
public abstract class BaseBindingForm extends ActionForm {

    private static final long     serialVersionUID = 2325049157565387768L;
    protected Map<String, Object> values;

    public BaseBindingForm() {
        values = new HashMap<String, Object>();
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(final Map<String, Object> values) {
        this.values = values;
    }

}
