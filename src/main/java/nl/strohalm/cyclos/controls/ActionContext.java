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
package nl.strohalm.cyclos.controls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.Navigation;
import nl.strohalm.cyclos.utils.SpringHelper;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Contains all objects used on the Struts context
 * @author luis
 */
public class ActionContext extends AbstractActionContext {

    private static final long serialVersionUID = 1L;
    private PermissionService permissionService;

    public ActionContext(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response, final User user, final MessageHelper messageHelper) {
        super(actionMapping, actionForm, request, response, messageHelper, user);
        permissionService = SpringHelper.bean(getServletContext(), PermissionService.class);
    }

    /**
     * Returns a forward to the last visited action
     */
    public ActionForward back() {
        return ActionHelper.back(getActionMapping());
    }

    /**
     * Returns the member if the logged user is either a member or an operator
     */
    public Member getMember() {
        final AccountOwner owner = getAccountOwner();
        return (Member) (owner instanceof Member ? owner : null);
    }

    /**
     * Returns the navigation object
     */
    public Navigation getNavigation() {
        return Navigation.get(getSession());
    }

    /**
     * Checks whether the logged member is the given member's broker
     */
    public boolean isBrokerOf(final Member member) {
        if (member == null || !isBroker() || member.equals(getElement())) {
            return false;
        }

        return permissionService.manages(member);
    }

    /**
     * Checks whether the logged member is the given operator's member
     */
    public boolean isMemberOf(final Operator operator) {
        if (operator == null || !isMember()) {
            return false;
        }

        return permissionService.manages(operator);
    }

    /**
     * Returns an action forward for the error page, showing the message of the given key / arguments
     */
    public ActionForward sendError(final String key, final Object... arguments) {
        return ActionHelper.sendError(getActionMapping(), getRequest(), getResponse(), key, arguments);
    }
}
