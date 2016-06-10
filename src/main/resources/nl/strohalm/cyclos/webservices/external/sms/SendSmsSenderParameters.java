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
package nl.strohalm.cyclos.webservices.external.sms;

import java.io.Serializable;

import nl.strohalm.cyclos.webservices.model.MemberVO;

public class SendSmsSenderParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private MemberVO          member;
    private String            text;
    private String            traceData;

    public MemberVO getMember() {
        return member;
    }

    public String getText() {
        return text;
    }

    /**
     * Optional.
     * @return the trace data set by the client at the moment of request for send a sms.
     * @see {@link nl.strohalm.cyclos.webservices.sms.SendSmsParameters#getTraceData()}
     */
    public String getTraceData() {
        return traceData;
    }

    public void setMember(final MemberVO member) {
        this.member = member;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setTraceData(final String traceData) {
        this.traceData = traceData;
    }
}
