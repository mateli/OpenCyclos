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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.Converter;

import org.apache.commons.lang.StringUtils;

/**
 * Class used to write objects as CSV lines
 * @author luis
 */
public class CSVWriter<E> extends BaseCSVHandler<E> {

    /**
     * Creates a new instance
     */
    public static <E> CSVWriter<E> instance(final Class<E> elementClass, final LocalSettings localSettings) {
        final CSVWriter<E> writer = new CSVWriter<E>(elementClass);
        writer.setValueSeparator(CoercionHelper.coerce(Character.TYPE, localSettings.getCsvValueSeparator().getValue()));
        writer.setStringQuote(CoercionHelper.coerce(Character.TYPE, localSettings.getCsvStringQuote().getValue()));
        writer.setRowSeparator(localSettings.getCsvRecordSeparator().getValue());
        writer.setUseHeader(localSettings.isCsvUseHeader());
        return writer;
    }

    protected String rowSeparator;
    private boolean  useHeader;

    public CSVWriter(final Class<E> elementClass) {
        super(elementClass);
    }

    public String getRowSeparator() {
        return rowSeparator;
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    public void setRowSeparator(final String rowSeparator) {
        this.rowSeparator = rowSeparator;
    }

    public void setUseHeader(final boolean useHeader) {
        this.useHeader = useHeader;
    }

    /**
     * Writes an object into a {@link PrintWriter} object
     */
    public void write(final List<E> results, final PrintWriter out) {
        writeHeader(out);
        for (final E element : results) {
            writeRow(element, out);
        }
    }

    /**
     * Writes the header to the output, if header is used
     */
    public void writeHeader(final PrintWriter out) {
        List<String> row;
        if (useHeader) {
            row = new ArrayList<String>();
            for (final Column column : columns) {
                row.add(stringQuote + column.getHeader() + stringQuote);
            }
            out.print(StringUtils.join(row.iterator(), valueSeparator) + rowSeparator);
        }
    }

    /**
     * Writes a row element to the output
     */
    @SuppressWarnings("unchecked")
    public void writeRow(final E element, final PrintWriter out) {
        final List<String> row = new ArrayList<String>();
        for (final Column column : columns) {
            final Object objectValue = PropertyHelper.get(element, column.getProperty());
            final String stringValue = PropertyHelper.getAsString(objectValue, (Converter<Object>) column.getConverter());
            row.add(escape(stringValue, stringQuote));
        }
        out.print(StringUtils.join(row.iterator(), valueSeparator) + rowSeparator);
    }

    private String escape(final String value, final char stringQuote) {
        final String string = StringUtils.trimToEmpty(value);
        final char[] chars = string.toCharArray();
        final int length = chars.length;
        final StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            final char c = chars[i];
            if (c == stringQuote) {
                sb.append(stringQuote).append(stringQuote);
            } else {
                sb.append(c);
            }
        }
        return stringQuote + sb.toString() + stringQuote;
    }
}
