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
package nl.strohalm.cyclos.entities.members.remarks;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * A remark is an annotation about a member, or event that happened to a member
 * @author luis
 */
public abstract class Remark extends Entity {

    public static enum Nature implements StringValuedEnum {
        BROKER("B"), GROUP("G");

        private final String value;

        private Nature(final String value) {
            this.value = value;
        }

        public Class<? extends Remark> getType() {
            return this == BROKER ? BrokerRemark.class : GroupRemark.class;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        WRITER("writer"), SUBJECT("subject");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 3495444657368796399L;
    private Element           writer;
    private Element           subject;
    private Calendar          date;
    private String            comments;

    public String getComments() {
        return comments;
    }

    public Calendar getDate() {
        return date;
    }

    public abstract Nature getNature();

    public Element getSubject() {
        return subject;
    }

    public Element getWriter() {
        return writer;
    }

    public void setComments(final String comment) {
        comments = comment;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setSubject(final Element subject) {
        this.subject = subject;
    }

    public void setWriter(final Element writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return getId() + " - Remark for " + subject;
    }

}
