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
package nl.strohalm.cyclos.dao.access;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.setup.CreateBasicData;
import nl.strohalm.cyclos.setup.Setup;

/**
 * Implementation for ChannelDAOImpl
 * @author luis
 */
public class ChannelDAOImpl extends BaseDAOImpl<Channel> implements ChannelDAO {

    public ChannelDAOImpl() {
        super(Channel.class);
    }

    @Override
    public boolean existsChannel(final String internalName) {
        try {
            loadByInternalName(internalName);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public void importNewBuiltin(final Locale locale) {
        CreateBasicData.createChannels(getSession(), Setup.getResourceBundle(locale));
    }

    @Override
    public List<Channel> listAll(final Relationship... fetch) {
        List<Channel> channels = list("from Channel c order by c.displayName", null);
        for (int i = 0; i < channels.size(); i++) {
            channels.set(i, getFetchDao().fetch(channels.get(i), fetch));
        }
        return channels;
    }

    @Override
    public Channel loadByInternalName(final String name, final Relationship... fetch) throws EntityNotFoundException {
        Map<String, String> params = Collections.singletonMap("name", name);
        Channel channel = uniqueResult("from Channel c where c.internalName = :name", params);
        if (channel == null) {
            throw new EntityNotFoundException(Channel.class);
        }
        return getFetchDao().fetch(channel, fetch);
    }
}
