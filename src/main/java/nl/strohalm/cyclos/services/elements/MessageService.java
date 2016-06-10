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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.MessageQuery;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for messages
 * @author luis
 */
public interface MessageService extends Service {

    /**
     * Returns true if the logged user can manage the given message.
     */
    public boolean canManage(final Message message);

    /**
     * Returns true if the logged user can send a message to administration.
     * @return
     */
    boolean canSendToAdmin();

    /**
     * Returns true if the logged user can send a message to the given member
     * @return
     */
    boolean canSendToMember(Member member);

    /**
     * Loads a message
     */
    Message load(Long id, Relationship... fetch);

    /**
     * Perform a given action on multiple messages
     */
    void performAction(MessageAction action, Long... ids);

    /**
     * Loads a message, enforcing it is marked as read
     */
    Message read(Long id, Relationship... fetch);

    /**
     * Searches for messages
     */
    List<Message> search(MessageQuery query);

    /**
     * Sends a message
     */
    Message send(SendMessageDTO message);

    /**
     * Validates the given message
     */
    void validate(SendMessageDTO message) throws ValidationException;
}
