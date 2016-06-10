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

import java.util.List;

import nl.strohalm.cyclos.utils.CurrentApplicationContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Custom application context used to set the allowRawInjectionDespiteWrapping property
 * @author luis
 */
public class CustomApplicationContext extends ClassPathXmlApplicationContext {

    public CustomApplicationContext(final List<String> paths, final Class<?> clazz) throws BeansException {
        this(paths.toArray(new String[paths.size()]), clazz);
    }

    public CustomApplicationContext(final String... configLocations) throws BeansException {
        super(configLocations);
    }

    public CustomApplicationContext(final String[] paths, final Class<?> clazz) throws BeansException {
        super(paths, clazz);
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        // Keep the current application context statically accessible during refresh
        CurrentApplicationContext.CURRENT.set(this);
        try {
            super.refresh();
        } finally {
            CurrentApplicationContext.CURRENT.remove();
        }
    }

    @Override
    protected DefaultListableBeanFactory createBeanFactory() {
        final DefaultListableBeanFactory beanFactory = super.createBeanFactory();
        beanFactory.setAllowRawInjectionDespiteWrapping(true);
        return beanFactory;
    }

}
