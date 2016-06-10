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
package nl.strohalm.cyclos.services.preferences;

import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreferenceQuery;

/**
 * Local interface. It must be used only from other services.
 */
public interface PreferenceServiceLocal extends PreferenceService {

    /**
     * Lists admins according to the given parameters
     */
    List<Administrator> listAdminsForNotification(AdminNotificationPreferenceQuery query);

    /**
     * Returns the channels a member would receive a notification
     */
    Set<MessageChannel> receivedChannels(Member member, Message.Type type);

    /**
     * Checks whether the given member has chosen to be notified on messages of the given type (no matter which notification kind - internal message /
     * mail / sms)
     */
    boolean receivesMessage(Member member, Message.Type type);

}
