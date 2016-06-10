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
package nl.strohalm.cyclos.entities.accounts.external.filemapping;

import nl.strohalm.cyclos.utils.CustomObjectHandler;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.transactionimport.TransactionFileImport;

/**
 * A file mapping for parsing CSV files
 * @author luis
 */
public class CSVFileMapping extends FileMappingWithFields {

    public static final Character DEFAULT_STRING_QUOTE     = new Character('"');
    public static final Character DEFAULT_COLUMN_SEPARATOR = new Character(',');
    public static final Integer   DEFAULT_HEADER_LINES     = new Integer(0);
    private static final long     serialVersionUID         = 5121926952448162716L;

    private Character             stringQuote;
    private Character             columnSeparator;
    private Integer               headerLines;

    public Character getColumnSeparator() {
        return columnSeparator;
    }

    public Integer getHeaderLines() {
        return headerLines;
    }

    @Override
    public TransactionFileImport getImport(final CustomObjectHandler customObjectHandler) {
        return new CSVImport(this, customObjectHandler.getBean(MessageResolver.class));
    }

    @Override
    public Nature getNature() {
        return Nature.CSV;
    }

    public Character getStringQuote() {
        return stringQuote;
    }

    public void setColumnSeparator(final Character columnSeparator) {
        this.columnSeparator = columnSeparator;
    }

    public void setHeaderLines(final Integer headerLines) {
        this.headerLines = headerLines;
    }

    public void setStringQuote(final Character stringQuote) {
        this.stringQuote = stringQuote;
    }

}
