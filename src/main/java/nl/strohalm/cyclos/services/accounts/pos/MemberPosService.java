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
package nl.strohalm.cyclos.services.accounts.pos;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.services.Service;

/**
 * @author rodrigo
 */
public interface MemberPosService extends Service {

    /**
     * Block the given MemberPos
     */
    public MemberPos blockMemberPos(MemberPos memberPos);

    /**
     * Change MemberPOS Pin
     * @param memberPos
     * @param pin
     */
    public MemberPos changePin(MemberPos memberPos, final String pin);

    /**
     * Loads a MemberPos, fetching the specified relationships
     */
    public MemberPos load(Long id, Relationship... fetch);

    /**
     * Persist the memberPos
     * @param memberPos
     */
    public void save(MemberPos memberPos);

    /**
     * Unblock given MemberPos
     */
    public MemberPos unblockMemberPos(MemberPos memberPos);

    /**
     * Unblock the MemberPos pin
     * @param memberPos
     */
    public MemberPos unblockPosPin(final MemberPos memberPos);
}
