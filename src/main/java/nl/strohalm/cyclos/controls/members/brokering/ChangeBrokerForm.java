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
package nl.strohalm.cyclos.controls.members.brokering;

import org.apache.struts.action.ActionForm;

/**
 * Form used to change the broker of a given member
 * @author luis
 */
public class ChangeBrokerForm extends ActionForm {
    private static final long serialVersionUID = -2151029163706669560L;
    private String            comments;
    private long              memberId;
    private long              brokerId;
    private long              newBrokerId;
    private boolean           suspendCommission;

    public long getBrokerId() {
        return brokerId;
    }

    public String getComments() {
        return comments;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getNewBrokerId() {
        return newBrokerId;
    }

    public boolean isSuspendCommission() {
        return suspendCommission;
    }

    public void setBrokerId(final long brokerId) {
        this.brokerId = brokerId;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setNewBrokerId(final long newBrokerId) {
        this.newBrokerId = newBrokerId;
    }

    public void setSuspendCommission(final boolean suspendCommission) {
        this.suspendCommission = suspendCommission;
    }
}
