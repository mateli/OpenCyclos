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
package nl.strohalm.cyclos.entities.groups;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.Period;

public class GroupHistoryLog extends Entity {

    public static enum Relationships implements Relationship {
        ELEMENT("element"), GROUP("group");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 6840703744916377438L;
    private Element           element;
    private Group             group;
    private Period            period;

    public Element getElement() {
        return element;
    }

    public Group getGroup() {
        return group;
    }

    public Period getPeriod() {
        return period;
    }

    public void setElement(final Element element) {
        this.element = element;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    @Override
    public String toString() {
        String string = getId() + ": " + getGroup().getName() + " - begin: " + period.getBegin();
        if (period.getEnd() != null) {
            string += " - end: " + period.getEnd();
        }
        return string;
    }

}
