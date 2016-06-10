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

import java.lang.reflect.Proxy;

import nl.strohalm.cyclos.services.ServiceSecurity;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * {@link BeanPostProcessor} which wraps security services in proxies, used to store exceptions, which could be suppressed, but would still need to be
 * processed
 * 
 * @author luis
 */
public class SecurityProxyPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean instanceof ServiceSecurity) {
            ServiceSecurityProxyInvocationHandler ih = new ServiceSecurityProxyInvocationHandler(bean);
            return Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    ih.getServiceInterfaces(),
                    ih);
        } else {
            return bean;
        }
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

}
