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
import nl.strohalm.cyclos.services.elements.MessageAction;
import nl.strohalm.cyclos.services.elements.MessageService;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to change the status for a message list
 * @author luis
 */
public class ChangeMessageStatusAction extends BaseAction {

    private MessageService messageService;

    @Inject
    public void setMessageService(final MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ChangeMessageStatusForm form = context.getForm();

        final MessageAction action = CoercionHelper.coerce(MessageAction.class, form.getAction());
        final Long[] ids = form.getMessageId();
        if (action == null || ids == null || ids.length == 0) {
            throw new ValidationException();
        }

        messageService.performAction(action, ids);

        switch (action) {
            case DELETE:
            case MOVE_TO_TRASH:
            case RESTORE:
                context.sendMessage("message.actionPerformed." + action);
        }

        return context.getSuccessForward();
    }

}
