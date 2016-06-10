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

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.Group;

/**
 * Records a group change
 * @author luis
 */
public class GroupRemark extends Remark {
    public static enum Relationships implements Relationship {
        OLD_GROUP("oldGroup"), NEW_GROUP("newGroup");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 4806071178145432817L;

    private Group             newGroup;
    private Group             oldGroup;

    @Override
    public Nature getNature() {
        return Nature.GROUP;
    }

    public Group getNewGroup() {
        return newGroup;
    }

    public Group getOldGroup() {
        return oldGroup;
    }

    public void setNewGroup(final Group newGroup) {
        this.newGroup = newGroup;
    }

    public void setOldGroup(final Group oldGroup) {
        this.oldGroup = oldGroup;
    }

    @Override
    public String toString() {
        return getId() + " - " + getSubject() + " from " + oldGroup + " to " + newGroup;
    }
}
