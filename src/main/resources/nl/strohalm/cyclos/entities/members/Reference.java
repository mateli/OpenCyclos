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
package nl.strohalm.cyclos.entities.members;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.utils.IntValuedEnum;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * A reference is given by a member to another
 * @author luis
 */
public abstract class Reference extends Entity {

    public static enum Level implements IntValuedEnum {
        VERY_GOOD(2), GOOD(1), NEUTRAL(0), BAD(-1), VERY_BAD(-2);
        private final int value;

        private Level(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum Nature implements StringValuedEnum {
        TRANSACTION("T"), GENERAL("G");
        private final String value;

        private Nature(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum Relationships implements Relationship {
        FROM("from"), TO("to");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -1174939978585184311L;

    private Member            from;
    private Member            to;
    private Level             level;
    private Calendar          date;
    private String            comments;

    public String getComments() {
        return comments;
    }

    public Calendar getDate() {
        return date;
    }

    public Member getFrom() {
        return from;
    }

    public Level getLevel() {
        return level;
    }

    public abstract Nature getNature();

    public Member getTo() {
        return to;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setFrom(final Member from) {
        this.from = from;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public void setTo(final Member to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return getId() + " - " + level + ", from " + from + ", to " + to;
    }
}
