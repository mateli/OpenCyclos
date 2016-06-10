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

import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Base DTO for sending a message
 * @author luis
 */
public abstract class SendMessageDTO extends DataObject {

    private static final long serialVersionUID = -4977393814427339354L;
    private MessageCategory   category;
    private boolean           html;
    private String            subject;
    private String            body;
    private Message           inReplyTo;

    public String getBody() {
        return body;
    }

    public MessageCategory getCategory() {
        return category;
    }

    public Message getInReplyTo() {
        return inReplyTo;
    }

    public String getSubject() {
        return subject;
    }

    public abstract Message.Type getType();

    public abstract boolean isBulk();

    public boolean isHtml() {
        return html;
    }

    public abstract boolean isSmsAllowed();

    public void setBody(final String body) {
        this.body = body;
    }

    public void setCategory(final MessageCategory category) {
        this.category = category;
    }

    public void setHtml(final boolean html) {
        this.html = html;
    }

    public void setInReplyTo(final Message inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

}
