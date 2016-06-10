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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import nl.strohalm.cyclos.exceptions.ApplicationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Indicates data validation exception. it contains nested validation errors
 * @author luis
 */
public class ValidationException extends ApplicationException {
    private static final Log                         LOG                   = LogFactory.getLog(ValidationException.class);
    private static final long                        serialVersionUID      = -234845842289514432L;
    private Map<String, String>                      keysByProperty        = new LinkedHashMap<String, String>();
    private Map<String, String>                      displayNameByProperty = new LinkedHashMap<String, String>();
    private Map<String, Collection<ValidationError>> errorsByProperty      = new LinkedHashMap<String, Collection<ValidationError>>();
    private Collection<ValidationError>              generalErrors         = new LinkedHashSet<ValidationError>();

    /**
     * This flag is used in the getMessage() method
     */
    private boolean                                  showDetailMessage     = false;

    /**
     * Contains the validator's base name useful to qualify the errors.
     */
    private String                                   baseName;

    /**
     * Empty constructor
     */
    public ValidationException() {
    }

    /**
     * Initializes with a general error with the given error key and arguments
     */
    public ValidationException(final String key, final Object... args) {
        this(new ValidationError(key, args));
    }

    /**
     * Initializes with a property error, with a mapped key
     */
    public ValidationException(final String property, final String key, final ValidationError propertyError) {
        addPropertyError(property, propertyError);
        if (key != null) {
            setPropertyKey(property, key);
        }
    }

    /**
     * Initializes with a property error, with no key mapping
     */
    public ValidationException(final String property, final ValidationError propertyError) {
        this(property, null, propertyError);
    }

    /**
     * Initializes with a general error
     */
    public ValidationException(final ValidationError generalError) {
        addGeneralError(generalError);
    }

    /**
     * Add a general error
     */
    public void addGeneralError(final ValidationError error) {
        generalErrors.add(error);
    }

    /**
     * Add a validation error for a given property
     */
    public void addPropertyError(final String property, final ValidationError error) {
        Collection<ValidationError> propertyErrors = errorsByProperty.get(property);
        if (propertyErrors == null) {
            propertyErrors = new LinkedHashSet<ValidationError>();
            errorsByProperty.put(property, propertyErrors);
        }
        propertyErrors.add(error);
    }

    public Map<String, String> getDisplayNameByProperty() {
        return displayNameByProperty;
    }

    public Map<String, Collection<ValidationError>> getErrorsByProperty() {
        return errorsByProperty;
    }

    public Collection<ValidationError> getGeneralErrors() {
        return generalErrors;
    }

    public Map<String, String> getKeysByProperty() {
        return keysByProperty;
    }

    @Override
    public String getMessage() {
        if (hasErrors() && showDetailMessage) {
            return getErrorMessage();
        } else {
            return null;
        }
    }

    /**
     * Return the mapped display name for the given property
     */
    public String getPropertyDisplayName(final String property) {
        return displayNameByProperty.get(property);
    }

    /**
     * Return the mapped key for the given property
     */
    public String getPropertyKey(final String property) {
        return keysByProperty.get(property);
    }

    /**
     * Return if there are any errors
     */
    public boolean hasErrors() {
        return !generalErrors.isEmpty() || !errorsByProperty.isEmpty();
    }

    /**
     * Validators objects will set its base name to the exc
     * @param validatorBaseName
     */
    public void setBaseName(final String baseName) {
        this.baseName = baseName;
    }

    public void setDisplayNameByProperty(final Map<String, String> displayNameByProperty) {
        this.displayNameByProperty = displayNameByProperty;
    }

    public void setErrorsByProperty(final Map<String, Collection<ValidationError>> errorsByProperty) {
        this.errorsByProperty = errorsByProperty;
    }

    public void setGeneralErrors(final Collection<ValidationError> generalErrors) {
        this.generalErrors = generalErrors;
    }

    public void setKeysByProperty(final Map<String, String> keysByProperty) {
        this.keysByProperty = keysByProperty;
    }

    /**
     * Set the display name for the given property
     */
    public void setPropertyDisplayName(final String property, final String displayName) {
        displayNameByProperty.put(property, displayName);
    }

    /**
     * Set the ApplicationResources key for the given property
     */
    public void setPropertyKey(final String property, final String key) {
        keysByProperty.put(property, key);
    }

    public void setShowDetailMessage(final boolean showDetailMessage) {
        this.showDetailMessage = showDetailMessage;
    }

    /**
     * Throw this exception if there are any validation errors
     * @throws ValidationException
     */
    public void throwIfHasErrors() throws ValidationException {
        if (hasErrors()) {

            if (LOG.isDebugEnabled()) {
                final String error = getErrorMessage();
                LOG.debug(error, this);
            }

            throw this;
        }
    }

    private String getErrorMessage() {
        final StringBuilder sb = new StringBuilder();
        if (baseName != null) {
            sb.append(String.format("%n%n*** Validation Exception: %s ***%n", baseName));
        } else {
            sb.append(String.format("%n%n*** Validation Exception ***%n"));
        }
        for (final ValidationError error : generalErrors) {
            sb.append(String.format("* General error: %s\n", error));
        }
        for (final Map.Entry<String, Collection<ValidationError>> entry : errorsByProperty.entrySet()) {
            sb.append(String.format("* Property errors for [%s]:\n", entry.getKey()));
            for (final ValidationError error : entry.getValue()) {
                sb.append(String.format("   * %s\n", error));
            }
        }
        sb.append("******\n\n");
        return sb.toString();
    }
}
