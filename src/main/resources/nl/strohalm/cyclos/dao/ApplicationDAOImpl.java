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
package nl.strohalm.cyclos.dao;

import java.sql.Connection;
import java.sql.SQLException;

import nl.strohalm.cyclos.entities.Application;
import nl.strohalm.cyclos.utils.JDBCWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementation for application dao
 * @author luis
 */
public class ApplicationDAOImpl extends BaseDAOImpl<Application> implements ApplicationDAO {

    private static final Log LOG = LogFactory.getLog(ApplicationDAOImpl.class);

    public ApplicationDAOImpl() {
        super(Application.class);
    }

    @Override
    public Application read() {
        return getHibernateTemplate().execute(new HibernateCallback<Application>() {
            @Override
            public Application doInHibernate(final Session session) throws HibernateException, SQLException {
                return (Application) session.createCriteria(Application.class).uniqueResult();
            }
        });
    }

    @Override
    public void shutdownDBIfNeeded() {
        SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) getSessionFactory();
        ConnectionProvider connectionProvider = sessionFactory.getConnectionProvider();
        try {
            Connection connection = connectionProvider.getConnection();
            try {
                String dbName = connection.getMetaData().getDatabaseProductName();
                if (dbName.startsWith("HSQL")) {
                    new JDBCWrapper(connection).execute("SHUTDOWN");
                    LOG.info("Shutdown on HSQL Database was successful");
                }
            } finally {
                connectionProvider.closeConnection(connection);
            }
        } catch (SQLException e) {
            LOG.warn("Error shutting down database connection", e);
        }
    }
}
