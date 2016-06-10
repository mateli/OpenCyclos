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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.utils.access.PermissionHelper;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * A Hibernate custom type to store values from differents permission enums
 * @author luis
 */
public class PermissionType implements UserType {

    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        return x == y;
    }

    @Override
    public int hashCode(final Object x) throws HibernateException {
        return x == null ? -1 : x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final Object owner) throws HibernateException, SQLException {
        final String qualifiedPermissionName = rs.getString(names[0]);
        if (!rs.wasNull()) {
            return PermissionHelper.getPermission(qualifiedPermissionName);
        }

        return null;
    }

    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index) throws HibernateException, SQLException {
        if (value == null || !(value instanceof Enum)) {
            st.setNull(index, Types.VARCHAR);
        } else {
            Permission permission = (Permission) value;
            st.setString(index, permission.getQualifiedName());
        }
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }

    @Override
    public Class<?> returnedClass() {
        return Enum.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }
}
