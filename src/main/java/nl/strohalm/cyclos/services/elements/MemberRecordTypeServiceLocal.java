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

import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;

/**
 * Local interface. It must be used only from other services.
 */
public interface MemberRecordTypeServiceLocal extends MemberRecordTypeService {

    /**
     * Returns true if the logged user can view the member record type.
     * @param memberRecordType member record type to be considered.
     * @param nature the nature of the element of which the member Record type is to be viewed. May be null, in which any (admin or member) is good.
     * Note that this param only makes sense for admins, not for brokers. It is ignored in case of brokers.
     */
    boolean canView(MemberRecordType memberRecordType, final Element.Nature nature);

}
