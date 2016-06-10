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

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Action used to send a message
 * @author luis
 */
public class SendMessageForm extends BaseBindingForm {

    private static final long serialVersionUID = 8545202895427568019L;
    private long              toMemberId;
    private long              inReplyTo;
    private String            sendTo;
    private boolean           toBrokeredMembers;

    public SendMessageForm() {
        setMessage("toGroups", Collections.emptyList());
    }

    public long getInReplyTo() {
        return inReplyTo;
    }

    public Map<String, Object> getMessage() {
        return values;
    }

    public Object getMessage(final String key) {
        return values.get(key);
    }

    public String getSendTo() {
        return sendTo;
    }

    public long getToMemberId() {
        return toMemberId;
    }

    public boolean isToBrokeredMembers() {
        return toBrokeredMembers;
    }

    public void setInReplyTo(final long inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public void setMessage(final Map<String, Object> message) {
        values = message;
    }

    public void setMessage(final String key, final Object value) {
        values.put(key, value);
    }

    public void setSendTo(final String sendTo) {
        this.sendTo = sendTo;
    }

    public void setToBrokeredMembers(final boolean toBrokeredMembers) {
        this.toBrokeredMembers = toBrokeredMembers;
    }

    public void setToMemberId(final long toMemberId) {
        this.toMemberId = toMemberId;
    }
}
