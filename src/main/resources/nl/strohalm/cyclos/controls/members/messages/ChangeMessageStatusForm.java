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

import org.apache.struts.action.ActionForm;

/**
 * Form used to change the status for a message list
 * @author luis
 */
public class ChangeMessageStatusForm extends ActionForm {

    private static final long serialVersionUID = -1653146495451062395L;
    private Long[]            messageId;
    private String            action;

    public String getAction() {
        return action;
    }

    public Long[] getMessageId() {
        return messageId;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public void setMessageId(final Long[] messageId) {
        this.messageId = messageId;
    }
}
