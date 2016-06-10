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

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Base {@link FactoryBean} for creating singletons
 * @author luis
 */
public abstract class BaseSingletonFactoryBean<T> implements FactoryBean<T>, DisposableBean, ApplicationContextAware {

    protected final Class<T>     objectType;
    protected T                  object;
    protected ApplicationContext applicationContext;

    public BaseSingletonFactoryBean(final Class<T> objectType) {
        this.objectType = objectType;
    }

    @Override
    public void destroy() throws Exception {
        if (object instanceof DisposableBean) {
            ((DisposableBean) object).destroy();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T getObject() throws Exception {
        final Class<?> implementationClass = resolveImplementationClass();
        final AutowireCapableBeanFactory acbf = applicationContext.getAutowireCapableBeanFactory();
        object = (T) acbf.createBean(implementationClass, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Should be implemented in order to instantiate the actual object
     * @return
     */
    protected abstract Class<?> resolveImplementationClass();

}
