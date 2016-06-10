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
package nl.strohalm.cyclos.struts.access;

import nl.strohalm.cyclos.controls.AbstractActionContext;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.pos.PosService;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.services.permissions.PermissionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActionAccessMonitor {
    private static final Log  LOG = LogFactory.getLog(ActionAccessMonitor.class);
    // /**
    // * @param path path including prefix and/or query string (e.g.: /do/member/payment?toSystem=true)
    // * @return the action path as declared in struts configuration files (e.g.: /member/payment)
    // */
    // private static String getActionPath(String path) {
    // // check remove the /do part
    // final int beginIndex = path.startsWith("/do") ? 3 : 0;
    // // checks to remove the query string part
    // int endIndex = path.indexOf('?');
    // if (endIndex == -1) {
    // endIndex = path.length();
    // }
    //
    // if (endIndex != -1 || beginIndex != 0) {
    // path = path.substring(beginIndex, endIndex);
    // }
    //
    // return path;
    // }

    private PermissionService permissionService;
    private ElementService    elementService;
    private PosService        posService;
    private GroupService      groupService;

    public ElementService getElementService() {
        return elementService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public PosService getPosService() {
        return posService;
    }

    public void requestAccess(final AbstractActionContext context) {
        final ActionPolicy policy = ActionPolicyRepository.getApplicablePolicy(context.getActionMapping().getType());

        check(policy, context);
    }

    public void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }

    public void setGroupService(final GroupService groupService) {
        this.groupService = groupService;
    }

    public void setPermissionService(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public void setPosService(final PosService posService) {
        this.posService = posService;
    }

    private void check(final ActionPolicy policy, final AbstractActionContext context) {
        if (policy != null && !policy.check(new ActionDescriptor(context, this))) {
            LOG.debug("Access denied for: " + context.getActionMapping().getPath());
            throw new PermissionDeniedException();
        }
    }
}
