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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Contains utility methods to process messages
 * @author luis
 */
public class MessageProcessingHelper {

    /**
     * Process a message, using the string between # and # as date pattern for today, example: "We are on #MM/yyyy#" on january, 2006, would result in
     * "We are on 01/2006"
     */
    public static String processDate(final String message) {
        return processDate(message, null);
    }

    /**
     * Process a message, using the string between # and # as date pattern for the specified date, example: "We are on #MM/yyyy#" on january, 2006,
     * would result in "We are on 01/2006"
     */
    public static String processDate(final String message, Calendar date) {
        if (message == null) {
            return "";
        }
        if (date == null) {
            date = Calendar.getInstance();
        }
        final StringBuilder sb = new StringBuilder();
        boolean escape = false;
        boolean inDate = false;
        final StringBuilder datePart = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            final char c = message.charAt(i);
            if (escape) {
                sb.append(c);
                escape = false;
            } else {
                switch (c) {
                    case '\\':
                        escape = true;
                        break;
                    case '#':
                        if (inDate) {
                            final DateFormat df = new SimpleDateFormat(datePart.toString());
                            sb.append(df.format(date.getTime()));
                            datePart.setLength(0);
                        }
                        inDate = !inDate;
                        break;
                    default:
                        (inDate ? datePart : sb).append(c);
                        break;
                }
            }
        }
        if (inDate) {
            sb.append('#').append(datePart.toString());
        }
        return sb.toString();
    }

    /**
     * Process variables, getting the Map from the entity The target entity should override the appendVariableValues() providing the required fields
     */
    public static String processVariables(final String message, final Entity entity, final LocalSettings localSettings) {
        return processVariables(message, entity.getVariableValues(localSettings));
    }

    /**
     * Process variables, getting the Map from the account owner and from the entities
     */
    public static String processVariables(final String content, final LocalSettings localSettings, final AccountOwner accountOwner, final AccountStatus status, final Entity... entities) {
        final Map<String, Object> values = new HashMap<String, Object>();
        if (entities != null) {
            for (final Entity entity : entities) {
                if (entity != null) {
                    values.putAll(entity.getVariableValues(localSettings));
                }
            }
        }
        if (accountOwner != null) {
            values.putAll(accountOwner.getVariableValues(localSettings));
        }
        if (status != null) {
            values.putAll(status.getVariableValues(localSettings));
        }
        return processVariables(content, values);
    }

    /**
     * Process variables, getting the Map from the account owner and from the entities
     */
    public static String processVariables(final String content, final LocalSettings localSettings, final AccountOwner accountOwner, final Entity... entities) {
        return processVariables(content, localSettings, accountOwner, null, entities);
    }

    /**
     * Process a message, replacing the specified variables between # and # example: "The member #member# paid you #amount# units." with the values
     * map containing member =&gt; John; amount =&gt; 4 will result in "The member John paid you 4 units".
     */
    public static String processVariables(final String message, final Map<String, ?> values) {
        if (message == null) {
            return "";
        }
        if (values == null || values.isEmpty()) {
            return message;
        }
        final StringBuilder sb = new StringBuilder();
        final StringBuilder variableName = new StringBuilder();
        boolean escape = false;
        boolean inVariable = false;
        for (int i = 0; i < message.length(); i++) {
            final char c = message.charAt(i);
            if (escape) {
                sb.append(c);
                escape = false;
            } else {
                switch (c) {
                    case '\\':
                        escape = true;
                        break;
                    case '#':
                        if (inVariable) {
                            final String name = variableName.toString();
                            final Object value = values.get(name);
                            if (value == null) {
                                sb.append('#').append(name).append('#');
                            } else {
                                sb.append(CoercionHelper.coerce(String.class, value));
                            }
                            variableName.setLength(0);
                        }
                        inVariable = !inVariable;
                        break;
                    default:
                        (inVariable ? variableName : sb).append(c);
                        break;
                }
            }
        }
        if (inVariable) {
            sb.append('#').append(variableName.toString());
        }
        return sb.toString();
    }
}
