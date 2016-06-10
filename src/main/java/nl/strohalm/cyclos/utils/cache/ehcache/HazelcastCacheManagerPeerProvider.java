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

import java.util.Collections;
import java.util.List;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import nl.strohalm.cyclos.utils.CurrentApplicationContext;
import nl.strohalm.cyclos.utils.HazelcastHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

/**
 * Listens to a Hazelcast topic for remote cache operations and apply it in the local cache
 * 
 * @author luis
 */
public class HazelcastCacheManagerPeerProvider implements CacheManagerPeerProvider, MessageListener<CacheEvictionEvent> {

    public static final String SCHEME_NAME = "Hazelcast";

    private static final Log   LOG         = LogFactory.getLog(HazelcastCacheManagerPeerProvider.class);

    /**
     * Given an {@link CacheManager} get the corresponding instance of this class.
     */
    public static HazelcastCacheManagerPeerProvider getCachePeerProvider(final CacheManager cacheManager) {
        final CacheManagerPeerProvider provider = cacheManager.getCacheManagerPeerProvider(SCHEME_NAME);
        if (provider == null) {
            LOG.warn("No CacheManagerPeerProvider registered for " + SCHEME_NAME + " scheme.");
            return null;
        }
        if (!(provider instanceof HazelcastCacheManagerPeerProvider)) {
            LOG.warn("CacheManagerPeerProvider for scheme " + SCHEME_NAME + " cannot be cast to " + HazelcastCacheManagerPeerProvider.class.getName());
            return null;
        }
        return (HazelcastCacheManagerPeerProvider) provider;
    }

    /**
     * Given an {@link Ehcache} get the corresponding instance of this class.
     */
    public static HazelcastCacheManagerPeerProvider getCachePeerProvider(final Ehcache cache) {
        final CacheManager cacheManager = cache.getCacheManager();
        return getCachePeerProvider(cacheManager);
    }

    /**
     * Returns the topic for the given cache
     */
    public static ITopic<CacheEvictionEvent> getTopic(final Ehcache cache) {
        return getCachePeerProvider(cache).getTopic();
    }

    private final CacheManager         cacheManager;
    private ITopic<CacheEvictionEvent> topic;
    private Status                     status = Status.STATUS_UNINITIALISED;

    public HazelcastCacheManagerPeerProvider(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void dispose() throws CacheException {
        // Topic is already disposed on hazelcast shutdown
        status = Status.STATUS_SHUTDOWN;
    }

    @Override
    public String getScheme() {
        return SCHEME_NAME;
    }

    /**
     * @return the {@link Status} of the manager
     */
    public Status getStatus() {
        return status;
    }

    @Override
    public long getTimeForClusterToForm() {
        return 0;
    }

    public ITopic<CacheEvictionEvent> getTopic() {
        return topic;
    }

    @Override
    public void init() {
        ApplicationContext applicationContext = CurrentApplicationContext.CURRENT.get();
        HazelcastInstance hazelcastInstance = HazelcastHelper.getHazelcastInstance(applicationContext);
        topic = hazelcastInstance.getTopic("cyclos.EhCacheEvictionReplication");
        topic.addMessageListener(this);
        status = Status.STATUS_ALIVE;
        LOG.info("EhCache replication has started using Hazelcast");
    }

    @Override
    public List<CachePeer> listRemoteCachePeers(final Ehcache cache) throws CacheException {
        // Ignore, only used for RMI
        return Collections.emptyList();
    }

    @Override
    public void onMessage(final Message<CacheEvictionEvent> message) {
        CacheEvictionEvent event = message.getMessageObject();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received cache eviction event: " + event);
        }
        Ehcache cache = cacheManager.getEhcache(event.getName());
        // No need to evict something in a cache which was not yet created
        if (cache != null) {
            Object key = event.getKey();
            if (key == null) {
                cache.removeAll(true);
            } else {
                cache.remove(key, true);
            }
        }
    }

    @Override
    public void registerPeer(final String rmiUrl) {
        // Ignore, only used for RMI
    }

    @Override
    public void unregisterPeer(final String rmiUrl) {
        // Ignore, only used for RMI
    }
}
