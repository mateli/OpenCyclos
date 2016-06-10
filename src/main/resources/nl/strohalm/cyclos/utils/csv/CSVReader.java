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
package nl.strohalm.cyclos.utils.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Class used to read objects from a CSV line
 * @author luis
 */
public class CSVReader<E> extends BaseCSVHandler<E> {

    /**
     * Creates a new instance
     */
    public static <E> CSVReader<E> instance(final Class<E> elementClass, final LocalSettings localSettings) {
        final CSVReader<E> reader = new CSVReader<E>(elementClass);
        reader.setValueSeparator(CoercionHelper.coerce(Character.TYPE, localSettings.getCsvValueSeparator().getValue()));
        reader.setStringQuote(CoercionHelper.coerce(Character.TYPE, localSettings.getCsvStringQuote().getValue()));
        return reader;
    }

    /**
     * Given a reader, reads a line from it, according to the given string quote and value separator, or null when the stream was exhausted
     * @throws IOException Error reading from the stream
     */
    public static List<String> readLine(final Reader reader, final char stringQuote, final char valueSeparator) throws IOException {
        final List<String> values = new ArrayList<String>();
        int read;
        boolean inQuotes = false;
        boolean previousWasQuote = false;
        final StringBuilder value = new StringBuilder();
        while ((read = reader.read()) > 0) {
            final char c = (char) read;
            if (c == stringQuote) {
                if (previousWasQuote) {
                    value.append(stringQuote);
                    previousWasQuote = false;
                } else {
                    previousWasQuote = true;
                }
                inQuotes = !inQuotes;
                continue;
            } else {
                previousWasQuote = false;
            }
            if (c == valueSeparator) {
                if (inQuotes) {
                    value.append(c);
                } else {
                    values.add(value.toString().trim());
                    value.setLength(0);
                }
            } else if (c == '\r' || c == '\n') {
                if (inQuotes) {
                    // A line break inside quotes: append it on the current valye
                    value.append('\n');
                } else if (value.length() == 0 && values.isEmpty()) {
                    // An unquoted line break at the beginning of the line - return empty
                    return Collections.emptyList();
                } else {
                    // An unquoted line break: end the current row and return it
                    values.add(value.toString().trim());
                    return values;
                }
            } else {
                value.append(c);
            }
        }

        if (value.length() > 0) {
            values.add(value.toString().trim());
        }

        return values.isEmpty() ? null : values;
    }

    private int headerLines;

    public CSVReader(final Class<E> elementClass) {
        super(elementClass);
    }

    public int getHeaderLines() {
        return headerLines;
    }

    /**
     * Reads a single object from a SCV line
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<E> read(final BufferedReader in) throws CSVParseException {
        int linesIndex = 0;
        final List<E> list = new LinkedList<E>();
        try {

            List<String> values;
            while ((values = readLine(in)) != null) {
                linesIndex++;
                // Ignore the header lines
                if (headerLines >= linesIndex) {
                    continue;
                }
                final E object = elementClass.newInstance();
                final int size = Math.min(columns.size(), values.size());
                for (int i = 0; i < size; i++) {
                    final Column column = columns.get(i);
                    final String property = column.getProperty();
                    if (property == null) {
                        continue;
                    }
                    final Class type = PropertyUtils.getPropertyType(object, property);
                    final String stringValue = values.get(i);
                    final Object objectValue = PropertyHelper.getAsObject(type, stringValue, column.getConverter());
                    PropertyHelper.set(object, property, objectValue);
                }
                list.add(object);
            }
            return list;
        } catch (final Exception e) {
            throw new CSVParseException(linesIndex);
        }
    }

    /**
     * Reads a line into an array of values
     * @throws IOException Error reading from stream
     */
    public List<String> readLine(final Reader reader) throws IOException {
        return readLine(reader, stringQuote, valueSeparator);
    }

    public void setHeaderLines(final int headerLines) {
        this.headerLines = headerLines;
    }
}
