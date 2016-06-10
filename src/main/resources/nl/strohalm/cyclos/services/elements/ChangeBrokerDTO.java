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
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains data for a broker change
 * @author luis
 */
public class ChangeBrokerDTO extends DataObject {

    private static final long serialVersionUID = 8987330610104052020L;
    private String            comments;
    private Member            member;
    private Member            newBroker;
    private boolean           suspendCommission;

    public String getComments() {
        return comments;
    }

    public Member getMember() {
        return member;
    }

    public Member getNewBroker() {
        return newBroker;
    }

    public boolean isSuspendCommission() {
        return suspendCommission;
    }

    public void setComments(final String description) {
        comments = description;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setNewBroker(final Member newBroker) {
        this.newBroker = newBroker;
    }

    public void setSuspendCommission(final boolean suspendCommission) {
        this.suspendCommission = suspendCommission;
    }
}
