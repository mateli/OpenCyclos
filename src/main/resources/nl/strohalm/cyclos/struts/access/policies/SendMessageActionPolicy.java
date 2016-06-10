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
package nl.strohalm.cyclos.struts.access.policies;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.controls.members.messages.SendMessageAction.SendTo;
import nl.strohalm.cyclos.controls.members.messages.SendMessageForm;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.struts.access.policies.utils.AbstractActionPolicy;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

public class SendMessageActionPolicy extends AbstractActionPolicy {
    private final static SendMessageActionPolicy INSTANCE = new SendMessageActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private SendMessageActionPolicy() {
    }

    @Override
    protected boolean doCheck(final ActionDescriptor descriptor) {
        final SendMessageForm form = getForm();
        SendTo sendTo = null;

        if (form.getToMemberId() > 0) {
            sendTo = SendTo.MEMBER;
        } else {
            sendTo = CoercionHelper.coerce(SendTo.class, form.getSendTo());
        }

        if (sendTo == null) { // to allow show the form page
            return true;
        }
        switch (sendTo) {
            case MEMBER:
                return hasPermission(AdminMemberPermission.MESSAGES_SEND_TO_MEMBER, MemberPermission.MESSAGES_SEND_TO_MEMBER, OperatorPermission.MESSAGES_SEND_TO_MEMBER);
            case GROUP:
                return hasPermission(AdminMemberPermission.MESSAGES_SEND_TO_GROUP);
            case BROKERED_MEMBERS:
                return hasPermission(BrokerPermission.MESSAGES_SEND_TO_MEMBERS);
            case ADMIN:
                return hasPermission(MemberPermission.MESSAGES_SEND_TO_ADMINISTRATION, OperatorPermission.MESSAGES_SEND_TO_ADMINISTRATION);
            default:
                return false;
        }
    }
}
