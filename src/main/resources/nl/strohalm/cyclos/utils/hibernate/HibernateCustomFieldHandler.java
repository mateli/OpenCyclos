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
package nl.strohalm.cyclos.utils.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.dao.customizations.CustomFieldPossibleValueDAO;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.commons.lang.StringUtils;

/**
 * Helper class used to handle custom fields
 * @author luis
 */
public class HibernateCustomFieldHandler {
    private FetchDAO                    fetchDao;
    private CustomFieldPossibleValueDAO customFieldPossibleValueDao;

    /**
     * Appends the custom field values on a query. Should be invoked when building the where part of the query
     * @param hql The current HQL buffer
     * @param values The custom field values used to filter
     */
    public void appendConditions(final StringBuilder hql, final Map<String, Object> namedParameters, final Collection<? extends CustomFieldValue> values) {
        if (values == null || values.isEmpty()) {
            return;
        }
        for (final CustomFieldValue fieldValue : values) {
            CustomField field = fieldValue.getField();
            if (field == null) {
                continue;
            }
            field = fetchDao.fetch(field);
            String value = fieldValue.getValue();
            // Remove any manually entered '%'
            value = StringUtils.trimToNull(StringUtils.replace(value, "%", ""));
            if (value == null) {
                continue;
            }
            final String alias = alias(field);
            final String fieldParam = "field_" + alias;
            final String valueParam = "value_" + alias;

            hql.append(" and ").append(alias).append(".field = :").append(fieldParam);
            namedParameters.put(fieldParam, field);

            if (LoggedUser.hasUser() && !LoggedUser.isAdministrator()) {
                if (field.getNature() == CustomField.Nature.MEMBER) {
                    // Exclude hidden fields
                    hql.append(" and ").append(alias).append(".hidden <> true");
                }
            }
            // Check the field type
            switch (field.getType()) {
                case STRING:
                    if (StringUtils.isNotEmpty(field.getPattern())) {
                        // Remove the mask and consider the value as matching exactly
                        value = StringHelper.removeMask(field.getPattern(), value, false);
                        hql.append(" and ").append(alias).append(".stringValue like :").append(valueParam);
                        namedParameters.put(valueParam, value);
                    } else {
                        // Use a like expression
                        hql.append(" and ").append(alias).append(".stringValue like :").append(valueParam);
                        namedParameters.put(valueParam, StringUtils.trimToEmpty(value) + "%");
                    }
                    break;
                case BOOLEAN:
                    if (Boolean.parseBoolean(value)) {
                        hql.append(" and ").append(alias).append(".stringValue = :" + valueParam);
                    } else {
                        hql.append(" and ").append(alias).append(".stringValue <> :" + valueParam);
                    }
                    namedParameters.put(valueParam, "true");
                    break;
                case ENUMERATED:
                    boolean byName = true;
                    if (StringUtils.containsOnly(value, "0123456789,")) {
                        // Try by id
                        try {
                            final Collection<CustomFieldPossibleValue> possibleValues = new ArrayList<CustomFieldPossibleValue>();
                            final String[] possibleValueIds = StringUtils.split(value, ',');
                            for (final String idAsString : possibleValueIds) {
                                final CustomFieldPossibleValue possibleValue = customFieldPossibleValueDao.load(Long.valueOf(idAsString));
                                if (!possibleValue.getField().equals(field)) {
                                    throw new Exception();
                                }
                                possibleValues.add(possibleValue);
                            }
                            byName = false;
                            hql.append(" and ").append(alias).append(".possibleValue in (:").append(valueParam).append(')');
                            namedParameters.put(valueParam, possibleValues);
                        } catch (final Exception e) {
                            // Possible value not found by id - next try by name
                        }
                    }
                    if (byName) {
                        hql.append(" and ").append(alias).append(".possibleValue.value = :").append(valueParam);
                        namedParameters.put(valueParam, value);
                    }
                    break;
                case MEMBER:
                    Long memberId = null;
                    if (fieldValue.getMemberValue() != null) {
                        memberId = fieldValue.getMemberValue().getId();
                    } else {
                        memberId = IdConverter.instance().valueOf(value);
                    }
                    if (memberId != null) {
                        hql.append(" and ").append(alias).append(".memberValue.id = :").append(valueParam);
                        namedParameters.put(valueParam, memberId);
                    }
                    break;
                default:
                    hql.append(" and ").append(alias).append(".stringValue = :").append(valueParam);
                    namedParameters.put(valueParam, value);
                    break;
            }
        }
    }

    /**
     * Appends the inner joins for each custom field. Should be invoked when building the from part of the query.
     * @param hql The current HQL buffer
     * @param fieldValuesPath The path on the query for the custom fields value collection
     * @param values The custom field values used to filter
     */
    public void appendJoins(final StringBuilder hql, final String fieldValuesPath, final Collection<? extends CustomFieldValue> values) {
        if (values == null || values.isEmpty()) {
            return;
        }
        for (final CustomFieldValue fieldValue : values) {
            CustomField field = fieldValue.getField();
            if (field == null) {
                continue;
            }
            String value = fieldValue.getValue();
            // Remove any manually entered '%'
            value = StringUtils.trimToNull(StringUtils.replace(value, "%", ""));
            if (value == null) {
                continue;
            }
            field = fetchDao.fetch(field);
            hql.append(" inner join ").append(fieldValuesPath).append(' ').append(alias(field));
        }
    }

    public void setCustomFieldPossibleValueDao(final CustomFieldPossibleValueDAO customFieldPossibleValueDao) {
        this.customFieldPossibleValueDao = customFieldPossibleValueDao;
    }

    public void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    private String alias(final CustomField field) {
        return "fv_" + field.getId();
    }
}
