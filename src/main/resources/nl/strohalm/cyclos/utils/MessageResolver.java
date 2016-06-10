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

import java.util.List;

/**
 * Interface used for retrieving messages
 * @author luis
 */
public interface MessageResolver {

    /**
     * A message resolver that just returns the key
     * @author luis
     */
    public static class NoOpMessageResolver implements MessageResolver {

        /**
         * 
         * @see nl.strohalm.cyclos.utils.MessageResolver#addMessageResourcesLoadedListener(nl.strohalm.cyclos.utils.MessageResourcesLoadedListener)
         */
        @Override
        public void addMessageResourcesLoadedListener(final MessageResourcesLoadedListener listener) {

        }

        /**
         * Returns the key itself
         */
        @Override
        public String message(final String key, final List<?> args) {
            return key;
        }

        /**
         * Returns the key itself
         */
        @Override
        public String message(final String key, final Object... args) {
            return key;
        }
    }

    /**
     * Add a listener for events signalising that the translation resource bundles have been loaded.
     * 
     * @param listener
     */
    void addMessageResourcesLoadedListener(final MessageResourcesLoadedListener listener);

    /**
     * Returns the message for the given key / arguments
     */
    String message(String key, List<?> args);

    /**
     * Returns the message for the given key / arguments
     */
    String message(String key, Object... args);
}
