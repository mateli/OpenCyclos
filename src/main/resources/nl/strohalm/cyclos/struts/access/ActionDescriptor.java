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
import nl.strohalm.cyclos.services.accounts.pos.PosService;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.services.permissions.PermissionService;

/**
 * Descriptor containing information about the requested action<br>
 * Used by policies to grant/deny access
 * @author ameyer
 */
public class ActionDescriptor {
    private AbstractActionContext context;
    private ActionAccessMonitor   accessMonitor;

    ActionDescriptor(final AbstractActionContext context, final ActionAccessMonitor accessMonitor) {
        this.context = context;
        this.accessMonitor = accessMonitor;
    }

    public AbstractActionContext getContext() {
        return context;
    }

    public ElementService getElementService() {
        return accessMonitor.getElementService();
    }

    public GroupService getGroupService() {
        return accessMonitor.getGroupService();
    }

    public PermissionService getPermissionService() {
        return accessMonitor.getPermissionService();
    }

    public PosService getPosService() {
        return accessMonitor.getPosService();
    }
}
