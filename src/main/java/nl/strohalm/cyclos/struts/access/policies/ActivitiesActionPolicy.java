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
package nl.strohalm.cyclos.struts.access.policies;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.controls.members.activities.ActivitiesForm;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.struts.access.policies.utils.AbstractActionPolicy;
import nl.strohalm.cyclos.utils.access.LoggedUser;

public class ActivitiesActionPolicy extends AbstractActionPolicy {
    private final static ActivitiesActionPolicy INSTANCE = new ActivitiesActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private ActivitiesActionPolicy() {
    }

    @Override
    protected boolean doCheck(final ActionDescriptor descriptor) {
        final ActivitiesForm form = getForm();
        if (form.getMemberId() > 0) {
            if (LoggedUser.isBroker()) {
                return hasPermission(BrokerPermission.REPORTS_VIEW);
            } else {
                return hasPermission(AdminMemberPermission.REPORTS_VIEW, MemberPermission.REPORTS_VIEW); // operator use the member permission
            }
        } else { // logged user's activities
            if (LoggedUser.isMember()) {
                return true;
            } else if (LoggedUser.isOperator()) {
                return hasPermission(OperatorPermission.REPORTS_VIEW_MEMBER);
            } else {
                return false;
            }
        }
    }
}
