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
import java.sql.Timestamp;
import java.util.Calendar;

import nl.strohalm.cyclos.utils.Period;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

public abstract class BasePeriodType implements CompositeUserType, Serializable {

    private static final long     serialVersionUID = 7090490642092717202L;
    private static final int      BEGIN            = 0;
    private static final int      END              = 1;
    private static final String[] NAMES            = { "begin", "end" };

    public BasePeriodType() {
    }

    public Object assemble(final Serializable value, final SessionImplementor session, final Object owner) throws HibernateException {
        return value == null ? null : ((Period) value).clone();
    }

    public Object deepCopy(final Object value) throws HibernateException {
        return value == null ? null : ((Period) value).clone();
    }

    public Serializable disassemble(final Object value, final SessionImplementor session) throws HibernateException {
        return value == null ? null : ((Period) value).clone();
    }

    public boolean equals(final Object x, final Object y) throws HibernateException {
        return ObjectUtils.equals(x, y);
    }

    public String[] getPropertyNames() {
        return NAMES;
    }

    public abstract Type[] getPropertyTypes();

    public Object getPropertyValue(final Object component, final int property) throws HibernateException {
        final Period period = (Period) component;
        switch (property) {
            case BEGIN:
                return period.getBegin();
            case END:
                return period.getEnd();
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
        final Timestamp begin = rs.getTimestamp(names[BEGIN]);
        final Timestamp end = rs.getTimestamp(names[END]);
        if (getLog().isDebugEnabled()) {
            getLog().debug("Returning " + begin + " as column " + names[BEGIN]);
            getLog().debug("Returning " + end + " as column " + names[END]);
        }
        if (begin == null && end == null) {
            return null;
        }
        final Period period = new Period();
        if (begin == null) {
            period.setBegin(null);
        } else {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(begin);
            period.setBegin(cal);
        }
        if (end == null) {
            period.setEnd(null);
        } else {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(end);
            period.setEnd(cal);
        }
        return period;
    }

    public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session) throws HibernateException, SQLException {
        final Period period = (Period) value;
        Timestamp begin = null;
        Timestamp end = null;
        if (period != null && period.getBegin() != null) {
            begin = new Timestamp(period.getBegin().getTimeInMillis());
        }
        if (period != null && period.getEnd() != null) {
            end = new Timestamp(period.getEnd().getTimeInMillis());
        }
        st.setTimestamp(index + BEGIN, begin);
        st.setTimestamp(index + END, end);
        if (getLog().isDebugEnabled()) {
            getLog().debug("Binding " + begin + " to parameter: " + (index + BEGIN));
            getLog().debug("Binding " + end + " to parameter: " + (index + END));
        }
    }

    public Object replace(final Object original, final Object target, final SessionImplementor session, final Object owner) throws HibernateException {
        return original == null ? null : ((Period) original).clone();
    }

    public Class<?> returnedClass() {
        return Period.class;
    }

    public void setPropertyValue(final Object component, final int property, final Object value) throws HibernateException {
        final Period period = (Period) component;
        switch (property) {
            case BEGIN:
                period.setBegin((Calendar) value);
                break;
            case END:
                period.setEnd((Calendar) value);
                break;
        }
    }

    protected abstract Log getLog();

}
