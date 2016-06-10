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

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.MessageQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link MessageService}
 * 
 * @author jcomas
 */
public class MessageServiceSecurity extends BaseServiceSecurity implements MessageService {

    private MessageServiceLocal         messageService;
    private MessageCategoryServiceLocal messageCategoryService;

    @Override
    public boolean canManage(final Message message) {
        return messageService.canManage(message);
    }

    @Override
    public boolean canSendToAdmin() {
        return messageService.canSendToAdmin();
    }

    @Override
    public boolean canSendToMember(final Member member) {
        return messageService.canSendToMember(member);
    }

    @Override
    public Message load(final Long id, final Relationship... fetch) {
        Message message = messageService.load(id, fetch);
        checkView(message);
        return message;
    }

    @Override
    public void performAction(final MessageAction action, final Long... ids) {
        for (Long id : ids) {
            Message message = messageService.load(id);
            checkManage(message);
        }
        messageService.performAction(action, ids);
    }

    @Override
    public Message read(final Long id, final Relationship... fetch) {
        Message message = messageService.read(id, fetch);
        checkView(message);
        return message;
    }

    @Override
    public List<Message> search(final MessageQuery query) {
        if (!applyQueryRestrictions(query)) {
            return Collections.emptyList();
        }
        return messageService.search(query);
    }

    @Override
    public Message send(final SendMessageDTO message) {
        checkSend(message);
        return messageService.send(message);
    }

    public void setMessageCategoryServiceLocal(final MessageCategoryServiceLocal messageCategoryService) {
        this.messageCategoryService = messageCategoryService;
    }

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        this.messageService = messageService;
    }

    @Override
    public void validate(final SendMessageDTO message) throws ValidationException {
        // Nothing to check
        messageService.validate(message);
    }

    private boolean applyQueryRestrictions(final MessageQuery query) {
        if (!permissionService.permission()
                .admin(AdminMemberPermission.MESSAGES_VIEW)
                .member(MemberPermission.MESSAGES_VIEW)
                .operator(OperatorPermission.MESSAGES_VIEW).hasPermission()) {
            return false;
        }
        query.setGetter(LoggedUser.element());

        return true;
    }

    private void checkManage(final Message message) {
        if (!canManage(message)) {
            throw new PermissionDeniedException();
        }
    }

    private void checkSend(final SendMessageDTO message) {
        if (message instanceof SendMessageToGroupDTO) {
            permissionService.permission().admin(AdminMemberPermission.MESSAGES_SEND_TO_GROUP).check();

            // Check if the to groups are visible to the logged user.
            if (!permissionService.getVisibleMemberGroups().containsAll(((SendMessageToGroupDTO) message).getToGroups())) {
                throw new PermissionDeniedException();
            }
        } else if (message instanceof SendMessageFromBrokerToMembersDTO) {
            permissionService.permission().broker(BrokerPermission.MESSAGES_SEND_TO_MEMBERS).check();
        } else if (message instanceof SendMessageToAdminDTO) {
            if (!canSendToAdmin()) {
                throw new PermissionDeniedException();
            }
        } else if (message instanceof SendDirectMessageToMemberDTO) {
            if (!canSendToMember(((SendDirectMessageToMemberDTO) message).getToMember())) {
                throw new PermissionDeniedException();
            }
        } else {
            throw new IllegalArgumentException("Unexpected message class received " + message.getClass().getName());
        }

        // Check if the category is visible to the logged user.
        if (message.getCategory() != null && !messageCategoryService.canView(message.getCategory())) {
            throw new PermissionDeniedException();
        }

        // If it's in reply to another message, check that the reply message is visible to the logged user.
        if (message.getInReplyTo() != null) {
            checkView(message.getInReplyTo());
        }
    }

    private void checkView(final Message message) {
        permissionService.permission()
                .admin(AdminMemberPermission.MESSAGES_VIEW)
                .member(MemberPermission.MESSAGES_VIEW)
                .operator(OperatorPermission.MESSAGES_VIEW).check();

        if (messageService.checkMessageOwner(message) == null) {
            throw new PermissionDeniedException();
        }
    }

}
