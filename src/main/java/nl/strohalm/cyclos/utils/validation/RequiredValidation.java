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

import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Validates a required field
 * @author luis
 */
public class RequiredValidation implements PropertyValidation {

    public static RequiredValidation INSTANCE         = new RequiredValidation();
    private static final long        serialVersionUID = 2480640898730849631L;

    public static RequiredValidation instance() {
        return INSTANCE;
    }

    private RequiredValidation() {
    }

    public ValidationError validate(final Object object, final Object name, final Object value) {
        boolean error = value == null;
        if (!error && (value instanceof String)) {
            if (StringUtils.trimToNull(StringHelper.removeMarkupTagsAndUnescapeEntities((String) value)) == null) {
                error = true;
            }
        } else if (!error && (value instanceof Entity)) {
            final Long id = ((Entity) value).getId();
            error = (id == null || id <= 0L);
        } else if (!error && (value instanceof Collection<?>)) {
            final Collection<?> collection = (Collection<?>) value;
            error = collection.isEmpty();
        } else if (!error && (value.getClass().isArray())) {
            error = ((Object[]) value).length == 0;
        }
        if (error) {
            return new RequiredError();
        }
        return null;
    }
}
