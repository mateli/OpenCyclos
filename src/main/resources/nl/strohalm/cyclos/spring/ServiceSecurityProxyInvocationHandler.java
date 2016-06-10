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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;

import org.apache.commons.lang.ClassUtils;

/**
 * Invocation handler for service security proxies
 * 
 * @author luis
 */
public class ServiceSecurityProxyInvocationHandler implements InvocationHandler {
    private final Object     bean;
    private final Class<?>[] serviceInterfaces;

    @SuppressWarnings("unchecked")
    public ServiceSecurityProxyInvocationHandler(final Object bean) {
        this.bean = bean;
        Class<? extends Object> clazz = bean.getClass();
        List<Class<?>> interfaces = ClassUtils.getAllInterfaces(clazz);
        Collection<Class<?>> serviceInterfaces = new HashSet<Class<?>>();
        for (Class<?> iface : interfaces) {
            if (Service.class.isAssignableFrom(clazz)) {
                serviceInterfaces.add(iface);
            }
        }
        this.serviceInterfaces = serviceInterfaces.toArray(new Class<?>[serviceInterfaces.size()]);
    }

    public Class<?>[] getServiceInterfaces() {
        return serviceInterfaces;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        try {
            try {
                return method.invoke(bean, args);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        } catch (Throwable t) {
            CurrentTransactionData.setError(t);
            throw t;
        }
    }
}
