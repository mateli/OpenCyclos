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
package nl.strohalm.cyclos.controls.customization.fields;

import org.apache.struts.action.ActionForm;

/**
 * Form used to change all occurrences of a field value to another one
 * @author luis
 */
public class MoveCustomFieldPossibleValueForm extends ActionForm {

    private static final long serialVersionUID = -2590042457969080716L;
    private long              oldValueId;
    private long              newValueId;
    private String            nature;

    public String getNature() {
        return nature;
    }

    public long getNewValueId() {
        return newValueId;
    }

    public long getOldValueId() {
        return oldValueId;
    }

    public void setNature(final String nature) {
        this.nature = nature;
    }

    public void setNewValueId(final long newValueId) {
        this.newValueId = newValueId;
    }

    public void setOldValueId(final long oldValueId) {
        this.oldValueId = oldValueId;
    }

}
