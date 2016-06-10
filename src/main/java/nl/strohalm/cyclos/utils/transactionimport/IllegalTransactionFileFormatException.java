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
package nl.strohalm.cyclos.utils.transactionimport;

import nl.strohalm.cyclos.exceptions.ApplicationException;

/**
 * Exception thrown when a transaction file had an invalid format
 * @author luis
 */
public class IllegalTransactionFileFormatException extends ApplicationException {

    private static final long serialVersionUID = -6772561365400831610L;
    private int               line             = -1;
    private int               column           = -1;
    private String            field;
    private String            value;

    public IllegalTransactionFileFormatException() {
    }

    public IllegalTransactionFileFormatException(final int line, final int column, final String field, final String value) {
        this.line = line;
        this.column = column;
        this.field = field;
        this.value = value;
    }

    public IllegalTransactionFileFormatException(final String message) {
        super(message);
    }

    public int getColumn() {
        return column;
    }

    public String getField() {
        return field;
    }

    public int getLine() {
        return line;
    }

    public String getValue() {
        return value;
    }
}
