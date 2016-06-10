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
package nl.strohalm.cyclos.controls.operators;

import java.util.Map;

import nl.strohalm.cyclos.controls.elements.CreateElementForm;

/**
 * Form used by a member to create an operator
 * @author luis
 */
public class CreateOperatorForm extends CreateElementForm {

    private static final long serialVersionUID = 3741967437011765985L;

    public Map<String, Object> getOperator() {
        return values;
    }

    public Object getOperator(final String key) {
        return values.get(key);
    }

    public void setOperator(final Map<String, Object> admin) {
        values = admin;
    }

    public void setOperator(final String key, final Object value) {
        values.put(key, value);
    }

}
