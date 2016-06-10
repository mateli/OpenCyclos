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
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Records a broker change
 * @author luis
 */
public class BrokerRemark extends Remark {
    public static enum Relationships implements Relationship {
        NEW_BROKER("newBroker"), OLD_BROKER("oldBroker");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = -3209648514982932525L;

    private Member            newBroker;
    private Member            oldBroker;
    private boolean           suspendCommission;

    @Override
    public Nature getNature() {
        return Nature.BROKER;
    }

    public Member getNewBroker() {
        return newBroker;
    }

    public Member getOldBroker() {
        return oldBroker;
    }

    public boolean isSuspendCommission() {
        return suspendCommission;
    }

    public void setNewBroker(final Member newBroker) {
        this.newBroker = newBroker;
    }

    public void setOldBroker(final Member oldBroker) {
        this.oldBroker = oldBroker;
    }

    public void setSuspendCommission(final boolean suspendCommission) {
        this.suspendCommission = suspendCommission;
    }

    @Override
    public String toString() {
        return getId() + " - " + getSubject() + " from " + oldBroker + " to " + newBroker;
    }
}
