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
import nl.strohalm.cyclos.controls.accounts.pos.EditPosForm;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;
import nl.strohalm.cyclos.struts.access.policies.utils.AbstractActionPolicy;
import nl.strohalm.cyclos.utils.access.LoggedUser;

public class MemberPosActionPolicy extends AbstractActionPolicy {
    private final static MemberPosActionPolicy INSTANCE = new MemberPosActionPolicy();

    public static ActionPolicy instance() {
        return INSTANCE;
    }

    private MemberPosActionPolicy() {
    }

    @Override
    protected boolean doCheck(final ActionDescriptor descriptor) {
        final EditPosForm form = getForm();
        final long memberId = form.getMemberId();

        if (memberId > 0 && LoggedUser.element().getId() != memberId) {
            return hasPermission(AdminMemberPermission.POS_VIEW, BrokerPermission.POS_VIEW);
        } else {
            return LoggedUser.isMember(); // or a broker viewing its own POS
        }
    }
}
