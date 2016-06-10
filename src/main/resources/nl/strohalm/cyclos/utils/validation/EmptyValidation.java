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
package nl.strohalm.cyclos.utils.validation;

/**
 * Validates a property value to be empty or null
 * @author luis
 */
public class EmptyValidation implements PropertyValidation {

    public static EmptyValidation INSTANCE         = new EmptyValidation();
    private static final long     serialVersionUID = 6947607327255919456L;

    public static EmptyValidation instance() {
        return INSTANCE;
    }

    private EmptyValidation() {
    }

    public ValidationError validate(final Object object, final Object name, final Object value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        return new InvalidError();
    }
}
