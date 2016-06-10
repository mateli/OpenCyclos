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
package nl.strohalm.cyclos.utils.hibernate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import nl.strohalm.cyclos.utils.StringValuedEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.type.Type;

/**
 * A Hibernate custom type to store enums with a string value
 * @author luis
 */
public class StringValuedEnumType<EnumType> extends AbstractEnumType<EnumType> {

    private static final long serialVersionUID = 641293149816868313L;
    private static final Log  LOG              = LogFactory.getLog(StringValuedEnumType.class);

    public static <T extends Enum<?>> Type getType(final Class<T> enumClass) {
        return getType(StringValuedEnumType.class, enumClass);
    }

    public Object nullSafeGet(final ResultSet rs, final String[] names, final Object owner) throws HibernateException, SQLException {
        final String value = rs.getString(names[0]);
        if (!rs.wasNull()) {
            for (final EnumType item : getEnumValues()) {
                final StringValuedEnum stringValuedEnum = (StringValuedEnum) item;
                if (value.equals(stringValuedEnum.getValue())) {
                    return item;
                }
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Returning " + value + " as column " + names[0]);
        }

        return null;
    }

    public void nullSafeSet(final PreparedStatement st, final Object value, final int index) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            String str;
            if (value instanceof StringValuedEnum) {
                str = ((StringValuedEnum) value).getValue();
            } else {
                str = value.toString();
            }
            st.setString(index, str);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Binding " + value + " to parameter: " + index);
        }
    }

    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }
}
