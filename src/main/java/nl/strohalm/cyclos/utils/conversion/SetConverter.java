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

import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * You can use this class to split a string by a separator and return a Set instance to from that split. It also allows giving the set returning the
 * String representation for that Set splited by the specified separator
 * @author Harold Selvaggi
 * @param <T>
 */
public class SetConverter<T extends Set<?>> implements Converter<T> {

    private static final long serialVersionUID = 7393904915691049648L;
    private String            separator;
    private Class<T>          clazz;

    public SetConverter(final Class<T> clazz, final String separator) {
        this.separator = separator;
        this.clazz = clazz;
    }

    public String toString(final T object) {
        return StringUtils.join(object, separator);
    }

    @SuppressWarnings("unchecked")
    public T valueOf(final String string) {
        final String[] split = string.split(separator);
        Set<Object> rs;
        try {
            rs = (Set<Object>) clazz.newInstance();
        } catch (final Exception e) {
            throw new ConversionException("Error converting from String to Set: " + e.getMessage(), e);
        }

        for (final String s : split) {
            rs.add(s.trim());
        }
        return (T) rs;
    }
}
