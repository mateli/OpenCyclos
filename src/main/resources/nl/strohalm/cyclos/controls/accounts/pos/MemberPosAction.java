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
package nl.strohalm.cyclos.controls.accounts.pos;

import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

/**
 * 
 * @author rodrigo
 */
public class MemberPosAction extends BaseFormAction {

    private DataBinder<MemberPos> dataBinder;

    // Used to get data and save to database
    public DataBinder<MemberPos> getDataBinder() {
        if (dataBinder == null) {

            final BeanBinder<MemberPos> binder = BeanBinder.instance(MemberPos.class);
            binder.registerBinder("posId", PropertyBinder.instance(String.class, "posId"));
            binder.registerBinder("posName", PropertyBinder.instance(String.class, "posName"));
            binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date"));

            dataBinder = binder;
        }
        return dataBinder;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {

        final EditPosForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final long memberId = form.getMemberId();
        if (memberId <= 0) {
            throw new PermissionDeniedException();
        }
        final Member member = elementService.load(memberId);
        final Collection<MemberPos> memberPos = member.getPosDevices();
        request.setAttribute("memberPos", memberPos);
        request.setAttribute("memberId", memberId);
        request.setAttribute("isOwnUser", member.equals(context.getElement()));
        final Permission permission = context.isBroker() ? BrokerPermission.POS_ASSIGN : AdminMemberPermission.POS_ASSIGN;
        request.setAttribute("canAssign", permissionService.hasPermission(permission));

    }
}
