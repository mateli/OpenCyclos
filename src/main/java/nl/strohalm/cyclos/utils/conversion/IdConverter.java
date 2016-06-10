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

import org.apache.commons.lang.StringUtils;

/**
 * Converter for entity ids, converting non-positive numbers to null
 * @author luis
 */
public class IdConverter implements Converter<Long> {

    private static final long        serialVersionUID = -3236552411048400756L;
    private static final IdConverter INSTANCE         = new IdConverter();

    public static IdConverter instance() {
        return INSTANCE;
    }

    public String toString(final Long object) {
        return object == null ? null : object.toString();
    }

    public Long valueOf(final String string) {
        Long id;
        try {
            id = Long.valueOf(StringUtils.trimToNull(string));
        } catch (final Exception e) {
            id = null;
        }
        return id == null || id.intValue() <= 0 ? null : id;
    }

}
