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
package nl.strohalm.cyclos.utils.instance;

import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Properties;

import org.hibernate.util.StringHelper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.hazelcast.config.Config;
import com.hazelcast.config.UrlXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;

/**
 * An instance handler which uses Hazelcast
 * 
 * @author luis
 */
public class HazelcastInstanceHandler implements InstanceHandler, InitializingBean, DisposableBean {

    private Properties        cyclosProperties;
    private HazelcastInstance hazelcastInstance;
    private Member            member;

    @Override
    public void afterPropertiesSet() throws Exception {
        URL xml = getClass().getResource("/hazelcast.xml");
        if (xml == null) {
            throw new FileNotFoundException("Configuration file hazelcast.xml was not found");
        }
        Config config = new UrlXmlConfig(xml);
        String instanceName = cyclosProperties == null ? null : cyclosProperties.getProperty("cyclos.instanceHandler.instanceName");
        if (StringHelper.isEmpty(instanceName)) {
            instanceName = "cyclos";
        }
        config.setInstanceName(instanceName);
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        member = hazelcastInstance.getCluster().getLocalMember();
    }

    @Override
    public void destroy() throws Exception {
        hazelcastInstance.getLifecycleService().shutdown();
    }

    public HazelcastInstance getHazelcastInstance() {
        return hazelcastInstance;
    }

    @Override
    public String getId() {
        InetSocketAddress addr = member.getInetSocketAddress();
        return addr.getAddress().getHostAddress() + ":" + addr.getPort();
    }

    public void setCyclosProperties(final Properties cyclosProperties) {
        this.cyclosProperties = cyclosProperties;
    }

}
