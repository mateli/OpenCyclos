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
package nl.strohalm.cyclos.services.sms;

import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.SmsMailing;

/**
 * Local interface. It must be used only from other services.
 */
public interface SmsMailingServiceLocal extends SmsMailingService {

    /**
     * Returns SMS text variables with their names, or with their values.
     * @param groups Used only with onlyVariableName in true. Only the common custom fields between the specified groups will be taken into account to
     * determine the custom field variables.
     * @param member Element and custom fields variable values will be taken from this member. Used only with onlyVariableNames in false.
     * @param onlyVariableNames If true, the method will return a description for each variable.
     * @return
     */
    public Map<String, String> getSmsTextVariables(final List<MemberGroup> groups, final Member member, final boolean onlyVariableNames);

    /**
     * Returns the next member to send this sms mailing to
     */
    Member nextMemberToSend(SmsMailing mailing);

    /**
     * Returns the next sms mailing to send
     */
    SmsMailing nextToSend();

    /**
     * Removes the given member from the pending to send list of the given sms mailing
     */
    void removeMemberFromPending(SmsMailing smsMailing, Member member);

}
