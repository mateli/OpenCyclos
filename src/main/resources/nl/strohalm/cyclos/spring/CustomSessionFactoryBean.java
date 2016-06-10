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
package nl.strohalm.cyclos.spring;

import nl.strohalm.cyclos.setup.DataBaseConfiguration;
import nl.strohalm.cyclos.utils.tasks.TaskRunner;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.function.ClassicAvgFunction;
import org.hibernate.dialect.function.ClassicCountFunction;
import org.hibernate.dialect.function.ClassicSumFunction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * Custom session factory bean used to setup Hibernate
 * @author luis
 */
public class CustomSessionFactoryBean extends LocalSessionFactoryBean {
    private SessionFactoryImplementor sessionFactoryImplementor;
    private TaskRunner                taskRunner;
    private DataBaseConfiguration     dataBaseConfiguration;

    @Override
    public void destroy() throws HibernateException {
        super.destroy();
        if (dataBaseConfiguration != null) {
            dataBaseConfiguration.release();
        }
    }

    public SessionFactoryImplementor getSessionFactoryImplementor() {
        return sessionFactoryImplementor;
    }

    public void setTaskRunner(final TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    @Override
    protected SessionFactory newSessionFactory(final Configuration config) throws HibernateException {
        // Configure the database
        dataBaseConfiguration = new DataBaseConfiguration(config, taskRunner);
        dataBaseConfiguration.run();

        // The SessionFactory may have been already configured
        SessionFactory sessionFactory = dataBaseConfiguration.getSessionFactory();
        if (sessionFactory == null) {
            // Probably the database tests were skipped... Build the session factory using the default mechanism
            sessionFactory = super.newSessionFactory(config);
        }

        sessionFactoryImplementor = (SessionFactoryImplementor) sessionFactory;

        return sessionFactory;
    }

    @Override
    protected void postProcessConfiguration(final Configuration config) throws HibernateException {
        // Set classic functions to return, ie, Integer on count, not Long
        // New to Hibernate 3.2, and would affect a large number of classes
        config.addSqlFunction("count", new ClassicCountFunction());
        config.addSqlFunction("avg", new ClassicAvgFunction());
        config.addSqlFunction("sum", new ClassicSumFunction());
    }

}
