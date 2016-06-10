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
package nl.strohalm.cyclos.utils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.apache.commons.lang.ArrayUtils;

/**
 * Contains helper methods for JDBC access
 * @author luis
 */
public class JDBCWrapper {

    /**
     * Closes the given connection, ignoring any exceptions
     */
    public static void closeQuietly(final Connection conn) {
        try {
            conn.close();
        } catch (final Exception e) {
            // Ignore
        }
    }

    /**
     * Closes the given result set AND it's statement, ignoring any exceptions
     */
    public static void closeQuietly(final ResultSet rs) {
        Statement st = null;
        try {
            st = rs.getStatement();
        } catch (final Exception e) {
            // Ignore
        }
        try {
            rs.close();
        } catch (final Exception e) {
            // Ignore
        }
        closeQuietly(st);
    }

    /**
     * Closes the given statement, ignoring any exceptions
     */
    public static void closeQuietly(final Statement st) {
        try {
            st.close();
        } catch (final Exception e) {
            // Ignore
        }
    }

    /**
     * Set the given positional parameters on a prepared statement, guessing the argument types
     */
    private static void setParameters(final PreparedStatement ps, final Object... parameters) throws SQLException {
        if (ps == null || ArrayUtils.isEmpty(parameters)) {
            return;
        }
        for (int i = 0; i < parameters.length; i++) {
            final Object object = parameters[i];
            final int index = i + 1;
            if (object instanceof Number) {
                ps.setBigDecimal(index, CoercionHelper.coerce(BigDecimal.class, object));
            } else if ((object instanceof Calendar) || (object instanceof Date)) {
                final Calendar cal = CoercionHelper.coerce(Calendar.class, object);
                ps.setTimestamp(index, new Timestamp(cal.getTimeInMillis()));
            } else if (object instanceof Boolean) {
                ps.setBoolean(index, (Boolean) object);
            } else {
                ps.setString(index, CoercionHelper.coerce(String.class, object));
            }
        }
    }

    private final Connection connection;

    public JDBCWrapper(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Closes the underlying connection
     */
    public void close() {
        closeQuietly(connection);
    }

    /**
     * Commits the underlying connection
     */
    public void commit() throws SQLException {
        connection.commit();
    }

    /**
     * Execute a single statement
     */
    public int execute(final String statement, final Object... parameters) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(statement);
        try {
            setParameters(ps, parameters);
            return ps.executeUpdate();
        } finally {
            closeQuietly(ps);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isHSQLDB() throws SQLException {
        return connection.getMetaData().getDatabaseProductName().toLowerCase().startsWith("hsql");
    }

    /**
     * Execute a sql query, returning the open result set
     */
    public ResultSet query(final String sql, final Object... parameters) throws SQLException {
        return doQuery(sql, ResultSet.CONCUR_READ_ONLY, parameters);
    }

    /**
     * Execute a sql query, returning the first row / column as Long
     */
    public Long readScalarAsLong(final String sql, final Object... parameters) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = null;
        try {
            setParameters(ps, parameters);
            rs = ps.executeQuery();
            if (rs.next()) {
                final long data = rs.getLong(1);
                if (rs.wasNull()) {
                    return null;
                }
                return data;
            } else {
                return null;
            }
        } finally {
            closeQuietly(rs);
        }
    }

    /**
     * Execute a sql query, returning the first column as long in a list
     */
    public List<Long> readScalarAsLongList(final String sql, final Object... parameters) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = null;
        try {
            setParameters(ps, parameters);
            rs = ps.executeQuery();
            final List<Long> list = new LinkedList<Long>();
            while (rs.next()) {
                list.add(rs.getLong(1));
            }
            return list;
        } finally {
            closeQuietly(rs);
        }
    }

    /**
     * Execute a sql query, returning the first row / column as string
     */
    public String readScalarAsString(final String sql, final Object... parameters) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = null;
        try {
            setParameters(ps, parameters);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } finally {
            closeQuietly(rs);
        }
    }

    /**
     * Execute a sql query, returning the first column as string in a list
     */
    public List<String> readScalarAsStringList(final String sql, final Object... parameters) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = null;
        try {
            setParameters(ps, parameters);
            rs = ps.executeQuery();
            final List<String> list = new LinkedList<String>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } finally {
            closeQuietly(rs);
        }
    }

    /**
     * Rollbacks the underlying connection
     */
    public void rollback() throws SQLException {
        connection.rollback();
    }

    /**
     * Execute a sql query, returning an updatable, open result set
     */
    public ResultSet updatableQuery(final String sql, final Object... parameters) throws SQLException {
        return doQuery(sql, ResultSet.CONCUR_UPDATABLE, parameters);
    }

    private ResultSet doQuery(final String sql, final int concurrency, final Object... parameters) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, concurrency);
        setParameters(ps, parameters);
        return ps.executeQuery();
    }
}
