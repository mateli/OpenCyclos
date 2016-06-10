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
package nl.strohalm.cyclos.controls.mobile;

import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.MemberAccountType;

import org.apache.struts.action.ActionForward;

public class MobileChangeAccountAction extends MobileBaseAction {

    @SuppressWarnings("unchecked")
    @Override
    protected ActionForward executeAction(final MobileActionContext context) throws Exception {
        final Collection<MemberAccountType> accountTypes = (Collection<MemberAccountType>) context.getSession().getAttribute("accountTypes");
        context.getRequest().setAttribute("accountTypes", accountTypes);
        return context.getInputForward();
    }

}
