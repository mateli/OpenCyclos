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

import nl.strohalm.cyclos.entities.accounts.transactions.TransferListener;
import nl.strohalm.cyclos.services.sms.ISmsContext;

/**
 * Handler for custom objects creation, such as {@link TransferListener}, {@link ISmsContext} and so on
 * 
 * @author luis
 */
public interface CustomObjectHandler {

    /**
     * Returns whether there is an object configured with the given class name
     */
    boolean contains(String className);

    /**
     * Returns the object with the given class
     */
    <T> T get(final String className);

    /**
     * Returns an arbitrary bean from the context
     */
    <T> T getBean(Class<T> beanType);

}
