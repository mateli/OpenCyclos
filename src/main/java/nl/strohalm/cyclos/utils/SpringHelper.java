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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.ServiceSecurity;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.ConversionException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Contains helper method for spring framework
 * @author luis
 */
public final class SpringHelper {

    /**
     * Retrieves a bean from the factory, casting it
     */
    @SuppressWarnings("unchecked")
    public static <T> T bean(final BeanFactory beanFactory, final Class<T> requiredType) {
        T bean;
        if (Service.class.isAssignableFrom(requiredType)) {
            // in the case of a service there are two implementations (local & security) for each security service type
            // then we must retrieve the bean from the context using its id.
            bean = (T) beanFactory.getBean(StringUtils.uncapitalize(requiredType.getSimpleName()));
        } else {
            bean = beanFactory.getBean(requiredType);
        }

        ensureSecurityService(bean, null);
        return bean;
    }

    /**
     * Retrieves a bean from the factory bound to the given servlet context, casting it
     */
    public static <T> T bean(final ServletContext context, final Class<T> requiredType) {
        return bean(WebApplicationContextUtils.getWebApplicationContext(context), requiredType);
    }

    /**
     * Injects beans on setters using the Inject annotation
     */
    public static void injectBeans(final BeanFactory beanFactory, final Object target) {
        final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
        for (final PropertyDescriptor descriptor : propertyDescriptors) {
            final Method setter = descriptor.getWriteMethod();
            if (setter != null) {
                final Inject inject = setter.getAnnotation(Inject.class);
                if (inject != null) {
                    String beanName = inject.value();
                    // The bean name defaults to the property name
                    if (StringUtils.isEmpty(beanName)) {
                        beanName = descriptor.getName();
                    }
                    // Retrieve the bean from spring
                    Object bean = beanFactory.getBean(beanName);
                    ensureSecurityService(bean, target);
                    try {
                        bean = CoercionHelper.coerce(descriptor.getPropertyType(), bean);
                    } catch (final ConversionException e) {
                        throw new IllegalStateException("Bean " + beanName + " is not of the expected type type: " + descriptor.getPropertyType().getName());
                    }
                    // Set the bean
                    try {
                        setter.invoke(target, bean);
                    } catch (final Exception e) {
                        throw new IllegalStateException("Error setting bean " + bean + " on action " + target + " by injecting property " + descriptor.getName() + ": " + e, e);
                    }
                }
            }
        }
        if (target instanceof InitializingBean) {
            try {
                ((InitializingBean) target).afterPropertiesSet();
            } catch (final Exception e) {
                throw new IllegalStateException(String.format("Error after properties set on %1$s: %2$s", target, e.getMessage()), e);
            }
        }
    }

    /**
     * Injects beans on setters using the Inject annotation
     */
    public static void injectBeans(final ServletContext context, final Object target) {
        final WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        injectBeans(applicationContext, target);
    }

    private static <T> void ensureSecurityService(final T bean, final Object target) {
        if (bean instanceof Service && !(bean instanceof ServiceSecurity)) {
            String msg;
            if (target == null) {
                msg = String.format("It's trying to retrieve a local service (%1$s) from the web layer. You must use the remote service.", bean.getClass().getName(), target.getClass().getName());
            } else {
                msg = String.format("It's trying to inject a local service (%1$s) into a web component (%2$s). You must use the remote service.", bean.getClass().getName(), target.getClass().getName());
            }

            throw new IllegalArgumentException(msg);
        }
    }
}
