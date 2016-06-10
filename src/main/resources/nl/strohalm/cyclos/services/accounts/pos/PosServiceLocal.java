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

import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Local interface. It must be used only from other services.
 */
public interface PosServiceLocal extends PosService {

    /**
     * Generate a PosLog
     */
    public void generateLog(final Pos pos);

    /**
     * creates a member alert : MemberAlert.Alerts.BLOCKED_POS_USED
     * @param pos
     * @param remoteAddress
     */
    public void notifyBlockedPosUsed(Pos pos, String remoteAddress);

    /**
     * Unassigns all POS from a member
     */
    public void unassignAllMemberPos(Member member);

}
