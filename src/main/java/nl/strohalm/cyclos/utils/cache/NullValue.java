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
package nl.strohalm.cyclos.utils.cache;

import java.io.Serializable;

/**
 * Some caches don't support adding null values. So, this class is used
 * 
 * @author luis
 */
public class NullValue implements Serializable {

    private static final long      serialVersionUID = -3479308521144563997L;
    private static final NullValue INSTANCE         = new NullValue();

    public static NullValue getInstance() {
        return INSTANCE;
    }

    /**
     * Returns null if the given object is a NullValue, or the same object otherwise
     */
    public static Object nullIfNullValue(final Object object) {
        if (object instanceof NullValue) {
            return null;
        }
        return object;
    }

    /**
     * Returns a NullValue if the given object is null, or the same object otherwise
     */
    public static Object nullValueIfNull(final Object object) {
        if (object == null) {
            return getInstance();
        }
        return object;
    }

    private NullValue() {
    }

    protected Object readResolve() {
        return INSTANCE;
    }

}
