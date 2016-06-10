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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import nl.strohalm.cyclos.utils.conversion.Converter;

public abstract class BaseCSVHandler<E> {

    /**
     * Entry for a column
     * @author luis
     */
    protected static class Column implements Serializable {
        private static final long  serialVersionUID = 2620085284405140210L;
        private final String       header;
        private final String       property;
        private final Converter<?> converter;

        public Column(final String header, final String property, final Converter<?> converter) {
            this.header = header;
            this.property = property;
            this.converter = converter;
        }

        public Converter<?> getConverter() {
            return converter;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

    protected List<Column>   columns        = new LinkedList<Column>();
    protected final Class<E> elementClass;
    protected char           stringQuote    = '"';
    protected char           valueSeparator = ',';

    protected BaseCSVHandler(final Class<E> elementClass) {
        this.elementClass = elementClass;
    }

    public void addColumn(final String header, final String property) {
        addColumn(header, property, null);
    }

    public void addColumn(final String header, final String property, final Converter<?> converter) {
        this.columns.add(new Column(header, property, converter));
    }

    public Class<E> getElementClass() {
        return elementClass;
    }

    public char getStringQuote() {
        return stringQuote;
    }

    public char getValueSeparator() {
        return valueSeparator;
    }

    public void setStringQuote(final char stringQuote) {
        this.stringQuote = stringQuote;
    }

    public void setValueSeparator(final char valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

}
