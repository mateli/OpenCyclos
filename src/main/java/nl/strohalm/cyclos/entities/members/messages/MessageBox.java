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
package nl.strohalm.cyclos.entities.members.messages;

import static nl.strohalm.cyclos.services.elements.MessageAction.DELETE;
import static nl.strohalm.cyclos.services.elements.MessageAction.MARK_AS_READ;
import static nl.strohalm.cyclos.services.elements.MessageAction.MARK_AS_UNREAD;
import static nl.strohalm.cyclos.services.elements.MessageAction.MOVE_TO_TRASH;
import static nl.strohalm.cyclos.services.elements.MessageAction.RESTORE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.services.elements.MessageAction;

/**
 * A logical message box that can be resolved throug the message status. If the status is REMOVED, it's on the trash. Otherwise, from or to will
 * determine if it's the inbox or sent items.
 * @author luis
 */
public enum MessageBox {
    INBOX(MARK_AS_READ, MARK_AS_UNREAD, MOVE_TO_TRASH), SENT(MARK_AS_READ, MARK_AS_UNREAD, MOVE_TO_TRASH), TRASH(MARK_AS_READ, MARK_AS_UNREAD, RESTORE, DELETE);

    private List<MessageAction> possibleActions;

    private MessageBox(final MessageAction... posibleActions) {
        possibleActions = Collections.unmodifiableList(Arrays.asList(posibleActions));
    }

    public List<MessageAction> getPossibleActions() {
        return possibleActions;
    }
}
