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

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.PropertyHelper;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Class with helper method to work with Hibernate
 * @author rafael
 */
public class HibernateHelper {

    /**
     * Contains a value and an operator for use on queries
     * 
     * @author luis
     */
    public static class QueryParameter {
        private final Object value;
        private final String operator;

        public QueryParameter(final Object value, final String operator) {
            this.value = value;
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }

        public Object getValue() {
            return value;
        }
    }

    /**
     * Cached direct relationship properties (child -> parent) by entity class
     */
    private static Map<Class<? extends Entity>, Set<String>> directPropertiesCache = new HashMap<Class<? extends Entity>, Set<String>>();

    /**
     * Adds an "in elements()" operator parameter to the HQL query, if the given value is not empty, appending the values to the named parameters map
     * Used to search on associated relations
     */
    public static void addInElementsParameter(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final Entity value) {
        if (value != null && value.isPersistent()) {
            final String parameterName = getParameterName(namedParameters, path);
            hql.append(" and :").append(parameterName).append(" in elements(").append(path).append(") ");
            namedParameters.put(parameterName, value);
        }
    }

    /**
     * Adds an "in" operator parameter to the HQL query, if the given value is not empty, appending the values to the named parameters map
     */
    public static void addInParameterToQuery(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            final String parameterName = getParameterName(namedParameters, path);
            hql.append(" and ").append(path).append(" in (:").append(parameterName).append(") ");
            namedParameters.put(parameterName, values);
        }
    }

    /**
     * Adds an "in" operator parameter to the HQL query, if the given value is not empty, appending the values to the named parameters map
     */
    public static void addInParameterToQuery(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final Object... values) {
        if (values != null && values.length > 0) {
            addInParameterToQuery(hql, namedParameters, path, Arrays.asList(values));
        }
    }

    /**
     * Adds a 'path like %value%' parameter to the HQL query if the given value is not empty, appending the value to the named parameters map
     */
    public static void addLikeParameterToQuery(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final String value) {
        doAddLike(hql, namedParameters, path, value, false);
    }

    /**
     * Adds a equals parameter to the HQL query, if the given value is not empty, appending the value to the named parameters map
     */
    public static void addParameterToQuery(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final Object value) {
        addParameterToQueryOperator(hql, namedParameters, path, "=", value);
    }

    /**
     * Adds a custom parameter to the HQL query, if the given parameter is not empty, appending the value to the named parameters map
     */
    public static void addParameterToQuery(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final QueryParameter parameter) {
        if (parameter != null) {
            addParameterToQueryOperator(hql, namedParameters, path, parameter.getOperator(), parameter.getValue());
        }
    }

    /**
     * Adds a custom operator parameter to the HQL query, if the given value is not empty, appending the value to the named parameters map
     */
    public static void addParameterToQueryOperator(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final String operator, final Object value) {
        if (value != null && !"".equals(value)) {
            final String parameterName = getParameterName(namedParameters, path);
            hql.append(" and ").append(path).append(" ").append(operator).append(" :").append(parameterName).append(" ");
            namedParameters.put(parameterName, value);
        }
    }

    /**
     * Adds a period test to the HQL query, if the given period is not empty, appending the value to the named parameters map. See {@link Period}, as
     * it controls whether the begin and end dates are inclusive / exclusive.
     * 
     */
    public static void addPeriodParameterToQuery(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final Period period) {
        addParameterToQuery(hql, namedParameters, path, getBeginParameter(period));
        addParameterToQuery(hql, namedParameters, path, getEndParameter(period));
    }

    /**
     * Adds a 'path like value%' parameter to the HQL query if the given value is not empty, appending the value to the named parameters map
     */
    public static void addRightLikeParameterToQuery(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, final String value) {
        doAddLike(hql, namedParameters, path, value, true);
    }

    /**
     * Appends the join portion on the query to fetch the specified relationships, when appliable
     */
    public static void appendJoinFetch(final StringBuilder hql, final Class<? extends Entity> entityType, final String entityAlias, final Collection<Relationship> fetch) {
        if (fetch != null) {
            final Set<String> directRelationships = getDirectRelationshipProperties(entityType, fetch);

            for (final String directRelationship : directRelationships) {
                hql.append(" left join fetch ").append(entityAlias).append(".").append(directRelationship).append(" ");
            }
        }
    }

    /**
     * Appends the order by portion, with the given path lists (with an optional direction, ie: "e.date desc", "e.name", "x.name")
     */
    public static void appendOrder(final StringBuilder hql, final Collection<String> paths) {
        if (CollectionUtils.isNotEmpty(paths)) {
            hql.append(" order by " + StringUtils.join(paths.iterator(), ","));
        }
    }

    /**
     * Appends the order by portion, with the given path lists (with an optional direction, ie: "e.date desc", "e.name", "x.name")
     */
    public static void appendOrder(final StringBuilder hql, final String... paths) {
        if (paths != null && paths.length > 0) {
            appendOrder(hql, Arrays.asList(paths));
        }
    }

    /**
     * Returns the begin date of the given period, handling null
     */
    public static QueryParameter getBeginParameter(final Period period) {
        if (period == null) {
            return null;
        }
        Calendar begin = period.getBegin();
        if (begin == null) {
            return null;
        }
        // We must consider the time when explicitly set
        if (!period.isUseTime()) {
            // Truncate the begin date
            begin = DateHelper.truncate(begin);
        }
        String operator = period.isInclusiveBegin() ? ">=" : ">";
        return new QueryParameter(begin, operator);
    }

    /**
     * Returns the end date of the given period, handling null
     */
    public static QueryParameter getEndParameter(final Period period) {
        if (period == null) {
            return null;
        }
        Calendar end = period.getEnd();
        if (end == null) {
            return null;
        }
        // We must consider the time when explicitly set
        if (!period.isUseTime()) {
            // Truncate the end date and set the next day
            end = DateHelper.getDayEnd(end);
        }
        String operator = period.isInclusiveEnd() ? "<=" : "<";
        return new QueryParameter(end, operator);
    }

    /**
     * Returns a StringBuilder containing the begin of a single entity select HQL
     * @param entityType The entity type to search
     * @param entityAlias The entity alias on the query
     * @return The StringBuiler
     */
    public static StringBuilder getInitialQuery(final Class<? extends Entity> entityType, final String entityAlias) {
        return getInitialQuery(entityType, entityAlias, null);
    }

    /**
     * Returns a StringBuilder containing the begin of a single entity select HQL, with the especified fetch relationships, when appliable
     * @param entityType The entity type to search
     * @param entityAlias The entity alias on the query
     * @param fetch The relationships to fetch
     * @return The StringBuiler
     */
    public static StringBuilder getInitialQuery(final Class<? extends Entity> entityType, final String entityAlias, final Collection<Relationship> fetch) {
        final StringBuilder hql = new StringBuilder(" from ").append(entityType.getName()).append(" ").append(entityAlias).append(" ");
        appendJoinFetch(hql, entityType, entityAlias, fetch);
        hql.append(" where 1=1 ");
        return hql;
    }

    private static void doAddLike(final StringBuilder hql, final Map<String, Object> namedParameters, final String path, String value, final boolean rightOnly) {
        value = StringUtils.trimToNull(value);
        if (value == null) {
            return;
        }
        // Remove any manually entered '%'
        value = StringUtils.trimToNull(StringUtils.replace(value, "%", ""));
        if (value == null) {
            return;
        }
        // Assuming the default database collation is case insensitive, we don't need to perform case transformations
        if (rightOnly) {
            value += "%";
        } else {
            value = "%" + value + "%";
        }
        addParameterToQueryOperator(hql, namedParameters, path, "like", value);
    }

    /**
     * Returns a set of properties that will be fetched directly on the HQL
     */
    private static Set<String> getDirectRelationshipProperties(final Class<? extends Entity> entityType, final Collection<Relationship> fetch) {
        // Populate the direct properties cache for this entity if not yet exists
        Set<String> cachedDirectProperties = directPropertiesCache.get(entityType);
        if (cachedDirectProperties == null) {
            cachedDirectProperties = new HashSet<String>();
            final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(entityType);
            // Scan for child -> parent relationships
            for (final PropertyDescriptor descriptor : propertyDescriptors) {
                if (descriptor.getReadMethod() != null && descriptor.getWriteMethod() != null && Entity.class.isAssignableFrom(descriptor.getPropertyType())) {
                    // This is a child -> parent relationship. Add it to the cache
                    cachedDirectProperties.add(descriptor.getName());
                }
            }
            directPropertiesCache.put(entityType, cachedDirectProperties);
        }

        // Build the properties to add to HQL fetch from a given relationship set
        final Set<String> propertiesToAddToFetch = new HashSet<String>();
        for (final Relationship relationship : fetch) {
            final String name = PropertyHelper.firstProperty(relationship.getName());
            if (cachedDirectProperties.contains(name)) {
                propertiesToAddToFetch.add(name);
            }
        }
        return propertiesToAddToFetch;
    }

    /**
     * Generates a parameter name
     */
    private static String getParameterName(final Map<String, Object> namedParameters, final String propertyName) {
        int counter = 1;

        // Transform the property in a valid identifier
        final StringBuilder sb = new StringBuilder(propertyName.length());
        for (int i = 0, len = propertyName.length(); i < len; i++) {
            final char c = propertyName.charAt(i);
            if (Character.isJavaIdentifierPart(c)) {
                sb.append(c);
            } else {
                sb.append('_');
            }
        }

        final String field = sb.toString();
        String parameterName = field.concat("_1");
        while (namedParameters.containsKey(parameterName)) {
            parameterName = field.concat("_").concat(String.valueOf(++counter));
        }
        return parameterName;
    }

}
