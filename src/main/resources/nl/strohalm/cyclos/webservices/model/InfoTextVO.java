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
package nl.strohalm.cyclos.webservices.model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlType;

/**
 * Info text data for web services
 * 
 * @author luis
 */
@XmlType(name = "infoText")
public class InfoTextVO extends EntityVO {

    private static final long serialVersionUID = 7631744957525170384L;
    private String            subject;
    private String            body;
    private Calendar          validFrom;
    private Calendar          validTo;

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public Calendar getValidFrom() {
        return validFrom;
    }

    public Calendar getValidTo() {
        return validTo;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public void setValidFrom(final Calendar validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(final Calendar validTo) {
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        return "InfoTextVO(subject=" + subject + ", body=" + body + ")";
    }

}
