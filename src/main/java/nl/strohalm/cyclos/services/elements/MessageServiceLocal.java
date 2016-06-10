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
package nl.strohalm.cyclos.services.elements;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.sms.SmsLog;

/**
 * Local interface. It must be used only from other services.
 */
public interface MessageServiceLocal extends MessageService {

    /**
     * Checks if the message owner is the logged member, returning null if the message doesn't belongs to the logged user
     */
    Message checkMessageOwner(Message message);

    /**
     * Returns the next bulk message to send
     */
    Message nextToSend();

    /**
     * Removes all messages on trash after a time period defined in LocalSettings.deleteMessagesOnTrashAfter
     */
    void purgeExpiredMessagesOnTrash(Calendar time);

    /**
     * Sends the e-mail for the given message
     */
    void sendEmailIfNeeded(Message message);

    /**
     * Sends the given message from a system internal procedure
     */
    void sendFromSystem(SendMessageFromSystemDTO message);

    /**
     * Sends an SMS to a member.
     */
    SmsLog sendSms(SendSmsDTO params);

    /**
     * Sends an SMS to a member in background
     */
    void sendSmsAfterTransactionCommit(SendSmsDTO params);

}
