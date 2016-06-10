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
package nl.strohalm.cyclos.dao.members;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message.Type;
import nl.strohalm.cyclos.entities.members.preferences.NotificationPreference;

/**
 * Implementation for the notification preference Dao.
 * @author jeancarlo
 */
public class NotificationPreferenceDAOImpl extends BaseDAOImpl<NotificationPreference> implements NotificationPreferenceDAO {

    public NotificationPreferenceDAOImpl() {
        super(NotificationPreference.class);
    }

    /**
     * Loads a collection of notification preference for a given member
     */
    public List<NotificationPreference> load(final Member member) throws DaoException, EntityNotFoundException {
        final Map<String, ?> params = Collections.singletonMap("member", member);
        return list("from NotificationPreference as n where n.member = :member", params);
    }

    public NotificationPreference load(final Member member, final Type type) throws EntityNotFoundException {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("member", member);
        params.put("type", type);
        final NotificationPreference preference = uniqueResult(" from NotificationPreference n where n.member = :member and n.type = :type", params);
        if (preference == null) {
            throw new EntityNotFoundException();
        }
        return preference;
    }
}
