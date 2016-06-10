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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nl.strohalm.cyclos.entities.accounts.transactions.TransferListener;
import nl.strohalm.cyclos.services.sms.ISmsContext;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Handler for custom objects creation, such as {@link TransferListener}, {@link ISmsContext} and so on Regular Cache is not used, as here we have
 * live instances, maybe they are not serializable, for example, to be on cache
 * @author luis
 */
public class CustomObjectHandlerImpl implements CustomObjectHandler, ApplicationContextAware, DisposableBean {
    private ApplicationContext        applicationContext;
    private final Map<String, Object> beans = new ConcurrentHashMap<String, Object>();

    @Override
    public boolean contains(final String className) {
        return beans.containsKey(className);
    }

    @Override
    public void destroy() throws Exception {
        // Attempt to clean up all cached instances
        for (Object object : beans.values()) {
            if (object instanceof DisposableBean) {
                ((DisposableBean) object).destroy();
            }
        }
        beans.clear();
    }

    /**
     * Returns the object with the given class
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final String className) {
        Object bean = beans.get(className);
        if (bean == null) {
            bean = doGet(className);
        }
        return (T) bean;
    }

    @Override
    public <T> T getBean(final Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private synchronized Object doGet(final String className) {
        // Check again for the cache here, inside the sync method
        Object bean = beans.get(className);
        if (bean != null) {
            return bean;
        }
        try {
            AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
            Class<?> beanClass = Class.forName(className);
            bean = factory.createBean(beanClass, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
            factory.initializeBean(bean, beanClass.getSimpleName() + "#" + System.identityHashCode(bean));
            beans.put(className, bean);
            return bean;
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't instantiate class " + className, e);
        }
    }

}
