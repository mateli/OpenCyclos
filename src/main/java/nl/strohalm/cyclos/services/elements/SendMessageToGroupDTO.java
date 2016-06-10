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

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.messages.Message.Type;

/**
 * Base DTO for sending a message
 * @author luis
 */
public class SendMessageToGroupDTO extends SendMessageDTO {
    private static final long serialVersionUID = -5378893526982599215L;
    private List<MemberGroup> toGroups;

    public List<MemberGroup> getToGroups() {
        return toGroups;
    }

    @Override
    public Type getType() {
        return Type.FROM_ADMIN_TO_GROUP;
    }

    @Override
    public boolean isBulk() {
        return true;
    }

    @Override
    public boolean isSmsAllowed() {
        return false;
    }

    public void setToGroups(final List<MemberGroup> group) {
        toGroups = group;
    }
}
