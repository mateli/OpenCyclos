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
package nl.strohalm.cyclos.services.stats.exceptions;

import nl.strohalm.cyclos.exceptions.ApplicationException;

/**
 * This exception is thrown if in the statistics, the dimensions of the 2 dimensional data array do not match with the length of the columnHeaders or
 * rowHeaders arrays. For example: if the data is an array of 2 x 5, then columnHeaders and rowHeaders arrays should have 2 and 5 elements. If not,
 * this exception is thrown.
 * @author rinke
 * 
 */
public class InconsistentDataDimensionsException extends ApplicationException {

    private static final long serialVersionUID = 9139978971810956563L;

    public InconsistentDataDimensionsException() {
    }

    public InconsistentDataDimensionsException(final String message) {
        super(message);
    }
}
