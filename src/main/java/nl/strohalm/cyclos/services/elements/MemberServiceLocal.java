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

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatus;
import nl.strohalm.cyclos.utils.query.IteratorList;

/**
 * Local interface for {@link MemberService}
 * 
 * @author luis
 */
public interface MemberServiceLocal extends MemberService {

    /**
     * Returns the number of members by group The keys are the names of the groups The values are the number of members of the corresponding group
     * @return map containing the number of members by group
     */
    public Map<MemberGroup, Integer> getGroupMemberCount(Collection<MemberGroup> groups, Calendar timePoint);

    /**
     * Returns the SMS status, optionally updating the same record according to the current state
     */
    public MemberSmsStatus getSmsStatus(Member member, boolean update);

    /**
     * Returns whether the given member has value for the given field
     */
    public boolean hasValueForField(Member member, MemberCustomField field);

    /**
     * Iterates the members on the given groups, optionally ordering by name
     */
    public IteratorList<Member> iterateByGroup(boolean ordered, MemberGroup... groups);

    /**
     * Iterates the members on the given groups with no expected order
     */
    public IteratorList<Member> iterateByGroup(MemberGroup... groups);

    /**
     * Updates the given MemberSmsStatus
     */
    public MemberSmsStatus updateSmsStatus(MemberSmsStatus memberSmsStatus);

    /**
     * Ensure the sms status has the "allow charging sms" flag set correctly.<br>
     * If there isn't free sms, the additional charged package's size is one and the member accept any feature by sms then the flag is set to true.
     */
    void ensureAllowChargingSms(final MemberSmsStatus memberSmsStatus, boolean hasNotificationsBySms);
}
