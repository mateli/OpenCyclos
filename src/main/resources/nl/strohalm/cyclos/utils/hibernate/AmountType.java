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
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import nl.strohalm.cyclos.utils.Amount;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Hibernate user type to persist amounts
 * @author luis
 */
public class AmountType implements UserType, Serializable {

    private static final long serialVersionUID = 5484455100860932944L;
    private static final Log  LOG              = LogFactory.getLog(AmountType.class);

    public AmountType() {
        super();
    }

    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }

    public Object deepCopy(final Object value) throws HibernateException {
        return value == null ? null : ((Amount) value).clone();
    }

    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) value;
    }

    public boolean equals(final Object arg0, final Object arg1) throws HibernateException {
        return ObjectUtils.equals(arg0, arg1);
    }

    public int hashCode(final Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

    public boolean isMutable() {
        return true;
    }

    public Object nullSafeGet(final ResultSet rs, final String[] names, final Object owner) throws HibernateException, SQLException {
        final Amount amount = new Amount();
        // If value or type are null, return null
        final BigDecimal value = rs.getBigDecimal(names[0]);
        if (rs.wasNull()) {
            return null;
        }
        amount.setValue(value);
        final String type = rs.getString(names[1]);
        if (type == null) {
            return null;
        }
        amount.setType(Amount.Type.getFromValue(type));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Returning " + value + " as column " + names[0]);
            LOG.debug("Returning " + type + " as column " + names[1]);
        }

        return amount;
    }

    public void nullSafeSet(final PreparedStatement ps, final Object object, final int index) throws HibernateException, SQLException {
        final Amount amount = (Amount) object;
        BigDecimal value = null;
        Amount.Type type = null;
        if (amount != null) {
            value = amount.getValue();
            type = amount.getType();
        }
        if (value == null) {
            ps.setNull(index, Types.NUMERIC);
        } else {
            ps.setBigDecimal(index, value);
        }
        if (type == null) {
            ps.setNull(index + 1, Types.CHAR);
        } else {
            ps.setString(index + 1, type.getValue());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Binding " + value + " to parameter: " + (index));
            LOG.debug("Binding " + (type == null ? null : type.getValue()) + " to parameter: " + (index + 1));
        }

    }

    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return deepCopy(original);
    }

    public Class<?> returnedClass() {
        return Amount.class;
    }

    public int[] sqlTypes() {
        final int[] columns = { Types.NUMERIC, Types.CHAR };
        return columns;
    }

}
