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

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.members.messages.Message;

/**
 * DTO for sending a message from system to a single member
 * @author luis
 */
public class SendMessageFromSystemDTO extends SendMessageToMemberDTO {

    private static final long serialVersionUID = -7264196312239829205L;

    private Message.Type      type;
    private Entity            entity;
    private String            sms;
    private String            smsTraceData;

    public Entity getEntity() {
        return entity;
    }

    public String getSms() {
        return sms;
    }

    /**
     * Optional
     * @return the trace data attached to the related entity (e.g.: Transfer, PaymentRequestTicket) that generates this message.
     */
    public String getSmsTraceData() {
        return smsTraceData;
    }

    @Override
    public Message.Type getType() {
        return type;
    }

    @Override
    public boolean isSmsAllowed() {
        return true;
    }

    @Override
    public boolean requiresMemberToReceive() {
        return false;
    }

    public void setEntity(final Entity entity) {
        this.entity = entity;
    }

    public void setSms(final String sms) {
        this.sms = sms;
    }

    public void setSmsTraceData(final String smsTraceData) {
        this.smsTraceData = smsTraceData;
    }

    public void setType(final Message.Type type) {
        this.type = type;
    }

}
