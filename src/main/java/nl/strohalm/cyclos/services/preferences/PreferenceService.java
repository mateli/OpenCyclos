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

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreference;
import nl.strohalm.cyclos.entities.members.preferences.NotificationPreference;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatus;
import nl.strohalm.cyclos.services.Service;

/**
 * Service Interface that handles the broker preferences and notification preferences
 */
public interface PreferenceService extends Service {

    public MemberSmsStatus saveSmsStatusPreferences(final Member member, final boolean isAcceptFreeMailing, final boolean isAcceptPaidMailing, final boolean isAllowChargingSms, boolean hasNotificationsBySms);

    /**
     * Returns the SMS status for the given member
     */
    MemberSmsStatus getMemberSmsStatus(Member member);

    /**
     * Lists the possible notification types for the given member's notification preferences
     */
    List<Message.Type> listNotificationTypes(Member member);

    /**
     * Loads the notification preferences for the given administrator
     */
    AdminNotificationPreference load(Administrator admin, Relationship... fetch);

    /**
     * Loads a collection of Notification Preferences for a given member
     */
    Collection<NotificationPreference> load(Member member);

    /**
     * Loads a specific notification preference
     */
    NotificationPreference load(Member member, Message.Type type);

    /**
     * Saves the notification preferences, returning the updated instance
     */
    AdminNotificationPreference save(AdminNotificationPreference preference);

    /**
     * Save a collection of notification preferences for a given member
     */
    void save(Member member, Collection<NotificationPreference> prefs);

}
