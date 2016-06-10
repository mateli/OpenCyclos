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
package nl.strohalm.cyclos.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.entities.settings.LocalSettings.DatePattern;
import nl.strohalm.cyclos.entities.settings.LocalSettings.TimePattern;
import nl.strohalm.cyclos.struts.CyclosMessageResources;
import nl.strohalm.cyclos.utils.conversion.BooleanConverter;
import nl.strohalm.cyclos.utils.conversion.MessageConverter;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper class for resource bundle access
 * @author luis
 */
public final class MessageHelper {

    private CyclosMessageResources messageResources;

    private MessageResolver        messageResolver;

    public void addMessageResourcesLoadedListener(final MessageResourcesLoadedListener listener) {
        messageResources.addMessagesLoadedListener(listener);
    }

    /**
     * Retrieve a message for the first error on the validation exception
     */
    public String firstErrorMessage(final ValidationException e) {
        if (e == null || !e.hasErrors()) {
            return "";
        }
        final Collection<ValidationError> generalErrors = e.getGeneralErrors();
        ValidationError error;
        Object[] args;
        if (generalErrors != null && !generalErrors.isEmpty()) {
            error = generalErrors.iterator().next();
            args = error.getArguments().toArray();
        } else {
            final Map<String, Collection<ValidationError>> errorsByProperty = e.getErrorsByProperty();
            final Entry<String, Collection<ValidationError>> entry = errorsByProperty.entrySet().iterator().next();
            final String property = entry.getKey();
            final String propertyKey = e.getPropertyKey(property);
            error = entry.getValue().iterator().next();
            final List<Object> arguments = new ArrayList<Object>();
            arguments.add(StringUtils.isEmpty(propertyKey) ? property : message(propertyKey));
            if (error.getArguments() != null && !error.getArguments().isEmpty()) {
                arguments.addAll(error.getArguments());
            }
            args = arguments.toArray();
        }
        return message(error.getKey(), args);
    }

    public BooleanConverter getBooleanConverter(final ServletContext context) {
        return new BooleanConverter(message("global.yes"), message("global.no"));
    }

    public String getDatePatternDescription(final DatePattern datePattern) {
        final String day = message("global.datePattern.day");
        final String month = message("global.datePattern.month");
        final String year = message("global.datePattern.year");
        String[] parts = null;
        switch (datePattern) {
            case DD_MM_YYYY_SLASH:
            case DD_MM_YYYY_PERIOD:
            case DD_MM_YYYY_DASH:
                parts = new String[] { day, month, year };
                break;
            case MM_DD_YYYY_SLASH:
            case MM_DD_YYYY_DASH:
            case MM_DD_YYYY_PERIOD:
                parts = new String[] { month, day, year };
                break;
            case YYYY_MM_DD_SLASH:
            case YYYY_MM_DD_DASH:
            case YYYY_MM_DD_PERIOD:
                parts = new String[] { year, month, day };
                break;
        }
        return StringUtils.join(parts, datePattern.getSeparator());
    }

    public MessageConverter getMessageConverter(final ServletContext context, final String prefix) {
        return new MessageConverter(messageResolver, prefix);
    }

    public String getTimePatternDescription(final TimePattern timePattern) {
        final String hour = message("global.datePattern.hour");
        final String minute = message("global.datePattern.minute");
        final String second = message("global.datePattern.second");
        String[] parts = null;
        switch (timePattern) {
            case HH12_MM:
            case HH24_MM:
                parts = new String[] { hour, minute };
                break;
            case HH12_MM_SS:
            case HH24_MM_SS:
                parts = new String[] { hour, minute, second };
                break;
        }
        return StringUtils.join(parts, ":");
    }

    /**
     * Retrieve a message from the servlet context
     */
    public String message(final String key, final List<Object> args) {
        return message(key, CollectionUtils.isEmpty(args) ? null : args.toArray());
    }

    /**
     * Retrieve a message from the servlet context
     */
    public String message(final String key, final Object... args) {
        try {
            return messageResources.getMessage(key, args);
        } catch (final Exception e) {
            return "???" + key + "???";
        }
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setMessageResources(final CyclosMessageResources messageResources) {
        this.messageResources = messageResources;
    }
}
