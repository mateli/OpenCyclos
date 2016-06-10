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
package nl.strohalm.cyclos.entities.groups;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Settings of a broker group. Due to a Hibernate limitation to handle subclasses of components, this class cannot extend MemberGroupSettings
 * @author luis
 */
public class BrokerGroupSettings extends DataObject {

    private static final long serialVersionUID = -7300302603022512475L;
    private MemberGroup       initialGroupForRegisteredMembers;

    public MemberGroup getInitialGroupForRegisteredMembers() {
        return initialGroupForRegisteredMembers;
    }

    public void setInitialGroupForRegisteredMembers(final MemberGroup initialGroupForRegisteredMembers) {
        this.initialGroupForRegisteredMembers = initialGroupForRegisteredMembers;
    }
}
