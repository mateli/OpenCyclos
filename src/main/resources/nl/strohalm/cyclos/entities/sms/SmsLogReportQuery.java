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
package nl.strohalm.cyclos.entities.sms;

import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.messages.Message;

public class SmsLogReportQuery extends SmsLogQuery {
    private static final long          serialVersionUID = -1104624603346955181L;
    private boolean                    returnTotals;
    private Collection<MemberGroup>    memberGroups;
    private Collection<Message.Type>   messageTypes;
    private Collection<SmsType>        smsTypes;
    private Collection<SmsMailingType> mailingTypes;

    public Collection<SmsMailingType> getMailingTypes() {
        return mailingTypes;
    }

    public Collection<MemberGroup> getMemberGroups() {
        return memberGroups;
    }

    public Collection<Message.Type> getMessageTypes() {
        return messageTypes;
    }

    public Collection<SmsType> getSmsTypes() {
        return smsTypes;
    }

    public boolean isReturnTotals() {
        return returnTotals;
    }

    public void setMailingTypes(final Collection<SmsMailingType> mailingTypes) {
        this.mailingTypes = mailingTypes;
    }

    public void setMemberGroups(final Collection<MemberGroup> memberGroups) {
        this.memberGroups = memberGroups;
    }

    public void setMessageTypes(final Collection<Message.Type> messageTypes) {
        this.messageTypes = messageTypes;
    }

    public void setReturnTotals(final boolean returnTotals) {
        this.returnTotals = returnTotals;
    }

    public void setSmsTypes(final Collection<SmsType> smsTypes) {
        this.smsTypes = smsTypes;
    }
}
