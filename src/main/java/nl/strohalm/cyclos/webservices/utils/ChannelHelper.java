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
package nl.strohalm.cyclos.webservices.utils;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.webservices.WebServiceContext;

import org.apache.commons.lang.StringUtils;

/**
 * Helper class for channels in web services<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * 
 * @author luis
 */
public class ChannelHelper {

    private ChannelServiceLocal channelServiceLocal;

    /**
     * Returns a channel by internal name, handling empty strings by returning null
     */
    public Channel get(final String channel) {
        if (StringUtils.isEmpty(channel)) {
            return null;
        }
        return channelServiceLocal.loadByInternalName(channel);
    }

    /**
     * Returns a channel by internal name, but when the current request comes from a client restricted to a channel, use that channel. Otherwise, use
     * the parameter
     */
    public Channel getResolved(final String internalName) {
        final String channel = resolveChannel(internalName);
        return channel == null ? null : get(channel);
    }

    /**
     * When the current request comes from a client restricted to a channel, use that channel. Otherwise, use the parameter
     */
    public String resolveChannel(final String channel) {
        final Channel restricted = WebServiceContext.getChannel();
        if (restricted != null) {
            return restricted.getInternalName();
        }
        return channel;
    }

    public PrincipalType resolvePrincipalType(final String principalType) {
        final Channel channel = WebServiceContext.getChannel();
        PrincipalType type;
        if (channel == null) {
            type = channelServiceLocal.resolvePrincipalType(principalType);
        } else {
            type = channelServiceLocal.resolvePrincipalType(channel.getInternalName(), principalType);
        }
        return type;
    }

    /**
     * Returns the channel name for the current client's restricted channel, or null when not restricted
     */
    public String restricted() {
        final Channel restricted = WebServiceContext.getChannel();
        if (restricted != null) {
            return restricted.getInternalName();
        }
        return null;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        channelServiceLocal = channelService;
    }

}
