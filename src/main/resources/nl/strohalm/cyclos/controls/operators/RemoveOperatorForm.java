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

import nl.strohalm.cyclos.controls.elements.RemoveElementForm;

/**
 * Form used to remove an operator
 * @author jefferson
 */
public class RemoveOperatorForm extends RemoveElementForm {

    private static final long serialVersionUID = -8517902117679316366L;

    public long getOperatorId() {
        return getElementId();
    }

    public void setOperatorId(final long operatorId) {
        setElementId(operatorId);
    }

}
