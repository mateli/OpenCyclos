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

import nl.strohalm.cyclos.utils.RangeConstraint;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Hibernate user type to persist range constraints
 * @author luis
 */
public class RangeConstraintType implements UserType, Serializable {

    private static final long  serialVersionUID = -5905157731458253811L;
    private static final int[] TYPES            = { Types.INTEGER, Types.INTEGER };

    public RangeConstraintType() {
        super();
    }

    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached == null ? null : ((RangeConstraint) cached).clone();
    }

    public Object deepCopy(final Object value) throws HibernateException {
        return value == null ? null : ((RangeConstraint) value).clone();
    }

    public Serializable disassemble(final Object value) throws HibernateException {
        return value == null ? null : ((RangeConstraint) value).clone();
    }

    public boolean equals(final Object x, final Object y) throws HibernateException {
        return ObjectUtils.equals(x, y);
    }

    public int hashCode(final Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    public boolean isMutable() {
        return false;
    }

    public Object nullSafeGet(final ResultSet rs, final String[] names, final Object owner) throws HibernateException, SQLException {
        final int min = rs.getInt(names[0]);
        final int max = rs.getInt(names[1]);
        return RangeConstraint.between(min, max);
    }

    public void nullSafeSet(final PreparedStatement ps, final Object object, final int index) throws HibernateException, SQLException {
        final RangeConstraint range = (RangeConstraint) object;
        if (range == null) {
            ps.setNull(index, Types.INTEGER);
            ps.setNull(index + 1, Types.INTEGER);
        } else {
            ps.setInt(index, range.getMin());
            ps.setInt(index + 1, range.getMax());
        }
    }

    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original == null ? null : ((RangeConstraint) original).clone();
    }

    public Class<?> returnedClass() {
        return RangeConstraint.class;
    }

    public int[] sqlTypes() {
        return TYPES;
    }
}
