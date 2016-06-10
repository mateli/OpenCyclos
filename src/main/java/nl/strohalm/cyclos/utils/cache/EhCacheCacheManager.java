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

import net.sf.ehcache.Ehcache;

import org.apache.commons.lang.reflect.FieldUtils;
import org.hibernate.cache.RegionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * A cache manager which uses EhCache
 * 
 * @author luis
 */
public class EhCacheCacheManager extends BaseCacheManager implements InitializingBean, DisposableBean {

    private net.sf.ehcache.CacheManager ehCacheManager;
    private SessionFactoryImplementor   sessionFactory;
    private boolean                     cleanUpEhCache;

    @Override
    public void afterPropertiesSet() throws Exception {
        // Attempt to get the same EHCache cache manager from the session factory
        try {
            // Dirty little trick using reflection
            RegionFactory regionFactory = sessionFactory.getSettings().getRegionFactory();
            ehCacheManager = (net.sf.ehcache.CacheManager) FieldUtils.readField(regionFactory, "manager", true);
        } catch (Exception e) {
            // It was not possible. Fallback to the default cache manager
            ehCacheManager = new net.sf.ehcache.CacheManager();
            cleanUpEhCache = true;
        }
    }

    @Override
    public void destroy() throws Exception {
        super.destroy();
        if (cleanUpEhCache) {
            ehCacheManager.shutdown();
        }
    }

    public void setSessionFactory(final SessionFactoryImplementor sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected Cache createCache(final String name) {
        Ehcache ehcache = ehCacheManager.getEhcache(name);
        if (ehcache == null) {
            ehCacheManager.addCache(name);
            ehcache = ehCacheManager.getEhcache(name);
        }
        return new EhCacheCache(ehcache);
    }

}
