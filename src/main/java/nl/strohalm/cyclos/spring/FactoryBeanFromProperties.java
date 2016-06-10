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

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;

/**
 * A {@link FactoryBean} which looks up a class name in cyclos.properties and has a default implementation
 * @author luis
 */
public class FactoryBeanFromProperties<T> extends BaseSingletonFactoryBean<T> {

    protected final String             propertyName;
    protected final Class<? extends T> defaultObjectType;
    protected Properties               cyclosProperties;

    public FactoryBeanFromProperties(final Class<T> objectType, final String propertyName, final Class<? extends T> defaultObjectType) {
        super(objectType);
        this.propertyName = propertyName;
        this.defaultObjectType = defaultObjectType;
    }

    public void setCyclosProperties(final Properties cyclosProperties) {
        this.cyclosProperties = cyclosProperties;
    }

    @Override
    protected final Class<?> resolveImplementationClass() {
        final String className = StringUtils.trimToNull(cyclosProperties.getProperty(propertyName));
        if (className == null) {
            return defaultObjectType;
        }
        Class<?> implementationClass;
        try {
            implementationClass = Class.forName(className);
        } catch (final ClassNotFoundException e) {
            throw new IllegalStateException("The class specified in " + propertyName + " was not found: " + className);
        }
        if (!objectType.isAssignableFrom(implementationClass)) {
            throw new IllegalStateException("The class specified in " + propertyName + " was not assignable to " + objectType.getName() + ": " + className);
        }
        return implementationClass;
    }
}
