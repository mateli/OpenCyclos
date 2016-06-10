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
package nl.strohalm.cyclos.utils.cache;

import java.io.Serializable;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.NotificationScope;
import nl.strohalm.cyclos.utils.CollectionHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A {@link Cache} implementation which uses EHCache as backend
 * 
 * @author luis
 */
public class EhCacheCache implements Cache {

    /**
     * Adapts the EHCache events listener into our event listener
     * 
     * @author luis
     */
    private class EhCacheListenerAdapter extends CacheListenerAdapter implements CacheEventListener {

        private EhCacheListenerAdapter(final CacheListener listener) {
            super(listener);
        }

        @Override
        public void dispose() {
            // We have no equivalent to this in Cyclos' cache
        }

        @Override
        public void notifyElementEvicted(final Ehcache cache, final Element element) {
            listener.onValueRemoved(EhCacheCache.this, element.getKey(), element.getValue());
        }

        @Override
        public void notifyElementExpired(final Ehcache cache, final Element element) {
            listener.onValueRemoved(EhCacheCache.this, element.getKey(), element.getValue());
        }

        @Override
        public void notifyElementPut(final Ehcache cache, final Element element) throws CacheException {
            listener.onValueAdded(EhCacheCache.this, element.getKey(), element.getValue());
        }

        @Override
        public void notifyElementRemoved(final Ehcache cache, final Element element) throws CacheException {
            listener.onValueRemoved(EhCacheCache.this, element.getKey(), element.getValue());
        }

        @Override
        public void notifyElementUpdated(final Ehcache cache, final Element element) throws CacheException {
            listener.onValueAdded(EhCacheCache.this, element.getKey(), element.getValue());
        }

        @Override
        public void notifyRemoveAll(final Ehcache cache) {
            listener.onCacheCleared(EhCacheCache.this);
        }

    }

    private static final Log LOG = LogFactory.getLog(EhCacheCache.class.getName());
    private final Ehcache    ehcache;

    public EhCacheCache(final Ehcache ehcache) {
        this.ehcache = ehcache;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating EhCacheCache <" + getName() + ">");
        }
    }

    @Override
    public synchronized boolean addListener(final CacheListener listener) {
        EhCacheListenerAdapter ehCacheListener = new EhCacheListenerAdapter(listener);
        return ehcache.getCacheEventNotificationService().registerListener(ehCacheListener, NotificationScope.ALL);
    }

    @Override
    public void clear() {
        ehcache.removeAll();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Cache <" + getName() + "> cleared");
        }
    }

    @Override
    public <T> T get(final Serializable key, final CacheCallback callback) {
        Element element = ehcache.get(key);
        if (element == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Cache <" + getName() + "> miss for key <" + key + ">");
            }
            element = new Element(key, callback.retrieve());
            ehcache.put(element);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Cache <" + getName() + "> hit for key <" + key + ">");
            }
        }
        return CollectionHelper.<T> defensiveCopy(element.getValue());
    }

    @Override
    public String getName() {
        return ehcache.getName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T remove(final Serializable key) {
        Element element = ehcache.get(key);
        if (element != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Cache <" + getName() + "> removing key <" + key + ">");
            }
            ehcache.remove(key);
            return (T) element.getValue();
        }
        return null;
    }

    @Override
    public void shutdown() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Shutting down cache <" + getName() + ">");
        }
        ehcache.getCacheManager().removeCache(ehcache.getName());
    }

}
