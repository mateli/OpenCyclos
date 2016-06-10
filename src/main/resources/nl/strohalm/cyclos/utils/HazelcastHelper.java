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

import nl.strohalm.cyclos.utils.instance.HazelcastInstanceHandler;

import org.springframework.context.ApplicationContext;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;

/**
 * Helper methods for hazelcast usage
 * @author luis
 */
public class HazelcastHelper {

    /**
     * Returns the {@link HazelcastInstance} associated with the given {@link ApplicationContext}
     */
    public static HazelcastInstance getHazelcastInstance(final ApplicationContext applicationContext) {
        Object bean = applicationContext.getBean("instanceHandler");
        if (!(bean instanceof HazelcastInstanceHandler)) {
            throw new IllegalStateException("Make sure that cyclos.instanceHandler = " + HazelcastInstanceHandler.class.getName() + " in cyclos.properties");
        }
        HazelcastInstanceHandler instanceHandler = (HazelcastInstanceHandler) bean;
        return instanceHandler.getHazelcastInstance();
    }

    /**
     * Unlocks and destroy the given lock
     * @param lock
     */
    public static void release(final ILock lock) {
        try {
            lock.unlock();
        } catch (final Exception e) {
            // Ignore
        } finally {
            try {
                lock.destroy();
            } catch (final Exception e) {
                // Ignore
            }
        }
    }

}
