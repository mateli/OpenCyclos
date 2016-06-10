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
package nl.strohalm.cyclos.controls.members.messages;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.elements.MessageService;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to view a given message
 * @author luis
 */
public class ViewMessageAction extends BaseAction {

    private static final Relationship[] FETCH = { Message.Relationships.FROM_MEMBER, Message.Relationships.TO_MEMBER, Message.Relationships.TO_GROUPS, Message.Relationships.CATEGORY };
    private MessageService              messageService;

    @Inject
    public void setMessageService(final MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ViewMessageForm form = context.getForm();
        long id = form.getMessageId();
        if (id <= 0L) {
            final Long lastMessageId = (Long) context.getSession().getAttribute("lastMessageId");
            if (lastMessageId == null) {
                throw new ValidationException();
            } else {
                id = lastMessageId;
            }
        }
        final Message message = messageService.read(id, FETCH);
        context.getSession().setAttribute("lastMessageId", message.getId());

        // Ensure the message is not being viewed by someone else
        final Member owner = message.getOwner();
        final Element element = context.getElement();
        if ((owner == null && !context.isAdmin()) || (context.isMember() && !element.equals(owner))) {
            throw new PermissionDeniedException();
        }

        context.getRequest().setAttribute("message", message);
        context.getRequest().setAttribute("canManageMessage", messageService.canManage(message));
        if (message.getFromMember() == null) { // the message came from administration
            context.getRequest().setAttribute("canReplyMessage", messageService.canSendToAdmin());
        } else { // the message came from another member
            context.getRequest().setAttribute("canReplyMessage", messageService.canSendToMember(message.getFromMember()));
        }
        return context.getInputForward();
    }

}
