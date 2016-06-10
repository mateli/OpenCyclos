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
package nl.strohalm.cyclos.utils.binding;

import java.io.Serializable;

/**
 * Has the capacity to read/store values from/to an object
 * @author luis
 */
public abstract class DataBinder<T> implements Serializable {
    private static final long serialVersionUID = 4492061720058225496L;
    private String            path;
    private Class<T>          type;

    public String getPath() {
        return path;
    }

    public Class<T> getType() {
        return type;
    }

    /**
     * Read the object from the given object, with no conversions
     */
    public abstract T read(Object object);

    /**
     * Read the object as a single string, on the JSON notation
     */
    public abstract String readAsString(Object object);

    /**
     * Read the object from the given object, expecting data as string, converting to the real object
     */
    public abstract T readFromString(Object object);

    public void setPath(final String propertyName) {
        this.path = propertyName;
    }

    public void setType(final Class<T> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getPath() + " (" + getType().getName() + ")";
    }

    /**
     * Write the value into the given object, with no conversions
     */
    public abstract void write(Object object, T value);

    /**
     * Write the value into the given object, expecting the value data as string, converting to the real object
     * @param object form
     * @param value real object
     */
    public abstract void writeAsString(Object object, Object value);
}
