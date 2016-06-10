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
package nl.strohalm.cyclos.utils.conversion;

/**
 * A converter that uses coercion to convert data
 * @author luis
 */
public class CoercionConverter<T> implements Converter<T> {

    private static final long serialVersionUID = 317613353267203912L;

    public static <T> CoercionConverter<T> instance(final Class<T> toType) {
        return new CoercionConverter<T>(toType);
    }

    private final Class<T> toType;

    private CoercionConverter(final Class<T> toType) {
        this.toType = toType;
    }

    public String toString(final T object) {
        if (toType == null) {
            throw new ConversionException("Undefined type");
        }
        return CoercionHelper.coerce(String.class, object);
    }

    public T valueOf(final String string) {
        if (toType == null) {
            throw new ConversionException("Undefined type");
        }
        return CoercionHelper.coerce(toType, string);
    }
}
