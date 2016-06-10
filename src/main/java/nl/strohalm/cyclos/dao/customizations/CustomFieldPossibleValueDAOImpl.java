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
package nl.strohalm.cyclos.dao.customizations;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Implementation class for custom field possible values DAO
 * @author rafael
 * @author luis
 */
public class CustomFieldPossibleValueDAOImpl extends BaseDAOImpl<CustomFieldPossibleValue> implements CustomFieldPossibleValueDAO {

    public CustomFieldPossibleValueDAOImpl() {
        super(CustomFieldPossibleValue.class);
    }

    public void ensureDefault(final CustomFieldPossibleValue possibleValue) {
        if (possibleValue.isDefaultValue()) {
            final Map<String, Object> namedParameters = new HashMap<String, Object>();
            final StringBuilder hql = new StringBuilder();

            hql.append("update ").append(CustomFieldPossibleValue.class.getName());
            hql.append(" set defaultValue = false");
            hql.append(" where field = :field");
            hql.append("   and id <> :id");
            namedParameters.put("field", possibleValue.getField());
            namedParameters.put("id", possibleValue.getId());
            if (possibleValue.getParent() != null) {
                hql.append(" and parent = :parent");
                namedParameters.put("parent", possibleValue.getParent());
            }
            bulkUpdate(hql.toString(), namedParameters);
        }
    }

    public CustomFieldPossibleValue load(final Long fieldId, final String value) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("fieldId", fieldId);
        params.put("value", value);
        final CustomFieldPossibleValue possibleValue = uniqueResult("from " + CustomFieldPossibleValue.class.getName() + " pv where pv.field.id = :fieldId and pv.value = :value", params);
        if (possibleValue == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        return possibleValue;
    }

}
