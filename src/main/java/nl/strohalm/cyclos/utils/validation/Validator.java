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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.RangeConstraint;

import org.apache.commons.lang.StringUtils;

/**
 * This class has the information needed to validate an object. Common validation rules, like required and length constraints are handled out of the
 * box. Other validations should be done with an external implementation of the Validation interface. When the constructor with a baseName is called,
 * properties will have a default key "baseName.propertyName"
 * @author luis
 */
public class Validator implements Serializable {

    /**
     * Bean style property retrieving strategy
     * @author luis
     */
    public static class BeanPropertyRetrieveStrategy implements PropertyRetrieveStrategy {

        private static final long serialVersionUID = -5276405909672992884L;

        private final String      name;

        public BeanPropertyRetrieveStrategy(final String name) {
            this.name = name;
        }

        @Override
        public Object description(final Object object, final String name) {
            return name;
        }

        @Override
        public Object get(final Object object) {
            return PropertyHelper.get(object, name);
        }
    }

    /**
     * Describes a property validation
     * @author luis
     */
    public class Property implements Serializable {
        private static final long              serialVersionUID = -6974004392652790404L;
        private final String                   name;
        private String                         displayName;
        private String                         key;
        private final List<PropertyValidation> validations      = new ArrayList<PropertyValidation>();
        private final PropertyRetrieveStrategy retrieveStrategy;

        private Property(final String name) {
            this(name, new BeanPropertyRetrieveStrategy(name));
        }

        private Property(final String name, final PropertyRetrieveStrategy retrieveStrategy) {
            this.name = name;
            this.retrieveStrategy = retrieveStrategy;
            if (StringUtils.isNotEmpty(baseName)) {
                key(baseName + "." + name);
            }
        }

        public Property add(final PropertyValidation... validations) {
            if (validations != null) {
                for (final PropertyValidation validation : validations) {
                    this.validations.add(validation);
                }
            }
            return this;
        }

        public Property anyOf(final Collection<?> values) {
            return add(new AnyOfValidation(values));
        }

        public Property anyOf(final Object... values) {
            return add(new AnyOfValidation(values));
        }

        public Property between(final Number from, final Number to) {
            return between(from, to, true, true);
        }

        public Property between(final Number from, final Number to, final boolean includeLowerBound, final boolean includeUpperBound) {
            if (includeLowerBound) {
                greaterEquals(from);
            } else {
                greaterThan(from);
            }
            if (includeUpperBound) {
                return lessEquals(to);
            } else {
                return lessThan(to);
            }
        }

        public Property comparable(final Comparable<?> comparable, final String operation) {
            return comparable(comparable, operation, null);
        }

        public Property comparable(final Comparable<?> comparable, final String operation, final ValidationError error) {
            final boolean acceptNegative = Arrays.asList("<", "<=").contains(operation);
            final boolean acceptZero = Arrays.asList("<=", "=", ">=").contains(operation);
            final boolean acceptPositive = Arrays.asList(">", ">=").contains(operation);
            return add(new CompareToValidation(comparable, acceptNegative, acceptZero, acceptPositive, error));
        }

        public Property displayName(final String displayName) {
            this.displayName = displayName;
            key = null;
            return this;
        }

        public Property fixedLength(final int length) {
            return length(length, length);
        }

        public Property future() {
            return add(TodayValidation.future());
        }

        public Property futureOrToday() {
            return add(TodayValidation.futureOrToday());
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public PropertyRetrieveStrategy getRetrieveStrategy() {
            return retrieveStrategy;
        }

        public List<PropertyValidation> getValidations() {
            return validations;
        }

        public Property greaterEquals(final Number number) {
            return add(CompareToValidation.greaterEquals((Comparable<?>) number));
        }

        public Property greaterThan(final Number number) {
            return add(CompareToValidation.greaterThan((Comparable<?>) number));
        }

        public Property inetAddr() {
            return add(InetAddrValidation.instance());
        }

        public Property instanceOf(final Class<?> expectedType) {
            return add(new InstanceOfValidation(expectedType));
        }

        public Property key(final String key) {
            this.key = key;
            displayName = null;
            return this;
        }

        public Property length(final int from, final int to) {
            return add(new LengthValidation(RangeConstraint.between(from, to)));
        }

        public Property lessEquals(final Number number) {
            return add(CompareToValidation.lessEquals((Comparable<?>) number));
        }

        public Property lessThan(final Number number) {
            return add(CompareToValidation.lessThan((Comparable<?>) number));
        }

        public Property maxLength(final int maxLength) {
            return add(new LengthValidation(RangeConstraint.to(maxLength)));
        }

        public Property minLength(final int minLength) {
            return add(new LengthValidation(RangeConstraint.from(minLength)));
        }

        public Property noneOf(final Collection<?> values) {
            return add(new NoneOfValidation(values));
        }

        public Property noneOf(final Object... values) {
            return add(new NoneOfValidation(values));
        }

        public Property numeric() {
            return add(NumericValidation.instance());
        }

        public Property past() {
            return add(TodayValidation.past());
        }

        public Property pastOrToday() {
            return add(TodayValidation.pastOrToday());
        }

        public Property positive() {
            return add(PositiveValidation.instance());
        }

        public Property positiveNonZero() {
            return add(PositiveNonZeroValidation.instance());
        }

        public Property regex(final String regex) {
            return add(new RegexValidation(regex));
        }

        public Property required() {
            return add(RequiredValidation.instance());
        }

        public Property url() {
            return url(false);
        }

        public Property url(final boolean requireDotOnHostname) {
            return add(URLValidation.instance(requireDotOnHostname));
        }
    }

    /**
     * Strategy for retrieving a property
     * @author luis
     */
    public static interface PropertyRetrieveStrategy extends Serializable {
        /**
         * Returns a property description
         */
        Object description(Object object, String name);

        /**
         * Returns the property value for a given object
         */
        Object get(Object object);
    }

    private static final long             serialVersionUID   = -2281234585616311623L;
    private String                        baseName;
    private String                        nestedProperty;
    private final List<GeneralValidation> generalValidations = new LinkedList<GeneralValidation>();
    private final Map<String, Property>   properties         = new LinkedHashMap<String, Property>();
    private final List<Validator>         chainedValidators  = new LinkedList<Validator>();

    public Validator() {
    }

    public Validator(final String baseName) {
        this.baseName = baseName;
    }

    public Validator(final String baseName, final String nestedProperty) {
        this(baseName);
        this.nestedProperty = nestedProperty;
    }

    public Validator chained(final Validator... validators) {
        if (validators != null && validators.length > 0) {
            chainedValidators.addAll(Arrays.asList(validators));
        }
        return this;
    }

    /**
     * Register general validations
     */
    public Validator general(final GeneralValidation... validations) {
        if (validations != null && validations.length > 0) {
            generalValidations.addAll(Arrays.asList(validations));
        }
        return this;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getNestedProperty() {
        return nestedProperty;
    }

    /**
     * Return a property validation descriptor
     */
    public Property property(final String name) {
        return property(name, null);
    }

    /**
     * Return a property validation descriptor
     */
    public Property property(final String name, final PropertyRetrieveStrategy retrieveStrategy) {
        Property property = properties.get(name);
        if (property == null) {
            if (retrieveStrategy == null) {
                property = new Property(name);
            } else {
                property = new Property(name, retrieveStrategy);
            }
            properties.put(name, property);
        }
        return property;
    }

    /**
     * Validates the given object, throwing a ValidationException if the entity is invalid
     */
    public void validate(final Object object) throws ValidationException {
        final ValidationException vex = new ValidationException();
        vex.setBaseName(baseName);
        appendValidationErrors(vex, object);
        for (final Validator chained : chainedValidators) {
            final String chainedProperty = chained.getNestedProperty();
            if (chainedProperty == null) {
                chained.appendValidationErrors(vex, object);
            } else {
                final Object toValidate = PropertyHelper.get(object, chainedProperty);
                if (toValidate != null) {
                    chained.appendValidationErrors(vex, toValidate);
                }
            }
        }
        vex.throwIfHasErrors();
    }

    protected void appendValidationErrors(final ValidationException vex, final Object object) {
        // Add general validations
        for (final GeneralValidation val : generalValidations) {
            final ValidationError error = val.validate(object);
            if (error != null) {
                vex.addGeneralError(error);
            }
        }
        // Add property validations
        for (final Map.Entry<String, Property> entry : properties.entrySet()) {
            final String name = entry.getKey();
            final Property property = entry.getValue();
            if (property.getKey() != null) {
                vex.setPropertyKey(name, property.getKey());
            } else if (property.getDisplayName() != null) {
                vex.setPropertyDisplayName(name, property.getDisplayName());
            }
            final Object value = property.getRetrieveStrategy().get(object);
            final Object propertyData = property.getRetrieveStrategy().description(object, name);
            for (final PropertyValidation val : property.getValidations()) {
                final ValidationError error = val.validate(object, propertyData, value);
                if (error != null) {
                    vex.addPropertyError(name, error);
                }
            }
        }
    }
}
