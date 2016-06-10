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

import java.util.List;
import java.util.Locale;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Data access interface for channels
 * @author luis
 */
public interface ChannelDAO extends BaseDAO<Channel>, InsertableDAO<Channel>, UpdatableDAO<Channel>, DeletableDAO<Channel> {

    /**
     * Returns true if there is a channel with the given internal name.
     */
    boolean existsChannel(String internalName);

    /**
     * Imports the new built-in channels
     */
    void importNewBuiltin(Locale locale);

    /**
     * Lists all channels
     */
    List<Channel> listAll(Relationship... fetch);

    /**
     * Loads a channel by it's internal name
     */
    Channel loadByInternalName(String name, Relationship... fetch) throws EntityNotFoundException;

}
