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

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.entities.sms.SmsType;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains the parameters for sending an SMS message
 * 
 * @author luis
 */
public class SendSmsDTO extends DataObject {
    private static final long serialVersionUID = 2830217909392051018L;
    private Member            targetMember;
    private Member            chargedMember;
    private Message.Type      messageType;
    private SmsType           smsType;
    private String[]          smsTypeArgs;
    private SmsMailing        smsMailing;
    private String            text;
    private String            traceData;

    public Member getChargedMember() {
        return chargedMember;
    }

    public Message.Type getMessageType() {
        return messageType;
    }

    public SmsMailing getSmsMailing() {
        return smsMailing;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public String[] getSmsTypeArgs() {
        return smsTypeArgs;
    }

    public Member getTargetMember() {
        return targetMember;
    }

    public String getText() {
        return text;
    }

    public String getTraceData() {
        return traceData;
    }

    public void setChargedMember(final Member chargedMember) {
        this.chargedMember = chargedMember;
    }

    public void setMessageType(final Message.Type messageType) {
        this.messageType = messageType;
    }

    public void setSmsMailing(final SmsMailing smsMailing) {
        this.smsMailing = smsMailing;
    }

    public void setSmsType(final SmsType smsType) {
        this.smsType = smsType;
    }

    public void setSmsTypeArgs(final String[] smsTypeArgs) {
        this.smsTypeArgs = smsTypeArgs;
    }

    public void setTargetMember(final Member targetMember) {
        this.targetMember = targetMember;
    }

    public void setText(final String text) {
        this.text = text;
    }

    /**
     * This identifier will be copied to the outgoing sms.
     */
    public void setTraceData(final String traceIdentifier) {
        traceData = traceIdentifier;
    }
}
