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

import nl.strohalm.cyclos.utils.TimePeriod;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

/**
 * Hibernate user type to persist time periods
 * @author luis
 */
public class TimePeriodType implements CompositeUserType, Serializable {

    private static final long     serialVersionUID = -9056395153634021069L;
    private static final Log      LOG              = LogFactory.getLog(TimePeriodType.class);
    private static final Type[]   TYPES            = { StandardBasicTypes.INTEGER, IntValuedEnumType.getType(TimePeriod.Field.class) };
    private static final int      NUMBER           = 0;
    private static final int      FIELD            = 1;
    private static final String[] NAMES            = { "number", "field" };

    public Object assemble(final Serializable value, final SessionImplementor session, final Object owner) throws HibernateException {
        return value == null ? null : ((TimePeriod) value).clone();
    }

    public Object deepCopy(final Object value) throws HibernateException {
        return value == null ? null : ((TimePeriod) value).clone();
    }

    public Serializable disassemble(final Object value, final SessionImplementor session) throws HibernateException {
        return value == null ? null : ((TimePeriod) value).clone();
    }

    public boolean equals(final Object x, final Object y) throws HibernateException {
        return ObjectUtils.equals(x, y);
    }

    public String[] getPropertyNames() {
        return NAMES;
    }

    public Type[] getPropertyTypes() {
        return TYPES;
    }

    public Object getPropertyValue(final Object component, final int property) throws HibernateException {
        final TimePeriod period = (TimePeriod) component;
        switch (property) {
            case NUMBER:
                return period.getNumber();
            case FIELD:
                return period.getField();
        }
        return null;
    }

    public int hashCode(final Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }

    public boolean isMutable() {
        return true;
    }

    public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session, final Object owner) throws HibernateException, SQLException {
        Integer number = rs.getInt(names[NUMBER]);
        if (rs.wasNull()) {
            number = null;
        }
        Integer field = rs.getInt(names[FIELD]);
        if (rs.wasNull()) {
            field = null;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Returning " + number + " as column " + names[NUMBER]);
            LOG.debug("Returning " + field + " as column " + names[FIELD]);
        }
        if (number == null || field == null) {
            return null;
        }
        final TimePeriod timePeriod = new TimePeriod();
        timePeriod.setNumber(number);
        timePeriod.setField(TimePeriod.Field.findByCalendarField(field));
        return timePeriod;
    }

    public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session) throws HibernateException, SQLException {
        final TimePeriod timePeriod = (TimePeriod) value;
        Integer number = null;
        TimePeriod.Field field = null;
        if (timePeriod == null || timePeriod.getField() == null) {
            st.setNull(index + NUMBER, Types.INTEGER);
            st.setNull(index + FIELD, Types.INTEGER);
        } else {
            number = timePeriod.getNumber();
            field = timePeriod.getField();
            st.setInt(index + NUMBER, number);
            st.setInt(index + FIELD, field.getCalendarValue());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Binding " + number + " to parameter: " + (index + NUMBER));
            LOG.debug("Binding " + field + " to parameter: " + (index + FIELD));
        }
    }

    public Object replace(final Object original, final Object target, final SessionImplementor session, final Object owner) throws HibernateException {
        return original == null ? null : ((TimePeriod) original).clone();
    }

    public Class<?> returnedClass() {
        return TimePeriod.class;
    }

    public void setPropertyValue(final Object component, final int property, final Object value) throws HibernateException {
        final TimePeriod timePeriod = (TimePeriod) component;
        switch (property) {
            case NUMBER:
                timePeriod.setNumber((Integer) value);
                break;
            case FIELD:
                timePeriod.setField((TimePeriod.Field) value);
                break;
        }
    }
}
