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
package nl.strohalm.cyclos.utils.cache.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheReplicator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hazelcast.core.ITopic;

/**
 * Replicates cache events
 * 
 * @author luis
 */
public class HazelcastCacheReplicator implements CacheReplicator {

    private static final Log LOG = LogFactory.getLog(HazelcastCacheReplicator.class);

    private boolean          alive;

    @Override
    public boolean alive() {
        return alive;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // A cache replicator is always cloneable
            return null;
        }
    }

    @Override
    public void dispose() {
        alive = false;
    }

    @Override
    public boolean isReplicateUpdatesViaCopy() {
        return false;
    }

    @Override
    public boolean notAlive() {
        return !alive;
    }

    @Override
    public void notifyElementEvicted(final Ehcache cache, final Element element) {
        publish(cache, element.getKey());
    }

    @Override
    public void notifyElementExpired(final Ehcache cache, final Element element) {
        // Don't publish expiration events
    }

    @Override
    public void notifyElementPut(final Ehcache cache, final Element element) throws CacheException {
        // Don't replicate puts
    }

    @Override
    public void notifyElementRemoved(final Ehcache cache, final Element element) throws CacheException {
        publish(cache, element.getKey());
    }

    @Override
    public void notifyElementUpdated(final Ehcache cache, final Element element) throws CacheException {
        // Don't replicate puts, just evict this element in other nodes
        publish(cache, element.getKey());
    }

    @Override
    public void notifyRemoveAll(final Ehcache cache) {
        publish(cache, null);
    }

    private void publish(final Ehcache cache, final Object key) {
        CacheEvictionEvent event = new CacheEvictionEvent(cache.getName(), key);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Publishing cache eviction event: " + event);
        }
        ITopic<CacheEvictionEvent> topic = HazelcastCacheManagerPeerProvider.getTopic(cache);
        try {
            topic.publish(event);
        } catch (Exception e) {
            LOG.warn("Couldn't publish cache eviction event: " + event, e);
        }
    }

}
