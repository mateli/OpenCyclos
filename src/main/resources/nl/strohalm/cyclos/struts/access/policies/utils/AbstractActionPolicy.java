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
package nl.strohalm.cyclos.struts.access.policies.utils;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.struts.access.ActionDescriptor;
import nl.strohalm.cyclos.struts.access.ActionPolicy;

import org.apache.struts.action.ActionForm;

/**
 * Base class for action policies with utilities methods
 * @author ameyer
 */
public abstract class AbstractActionPolicy implements ActionPolicy {
    private ActionDescriptor desriptor;

    @Override
    public final boolean check(final ActionDescriptor descriptor) {
        desriptor = descriptor;

        return doCheck(descriptor);
    }

    protected abstract boolean doCheck(ActionDescriptor descriptor);

    @SuppressWarnings("unchecked")
    protected <F extends ActionForm> F getForm() {
        return (F) desriptor.getContext().getForm();
    }

    protected final String getParameter(final String parameter) {
        return desriptor.getContext().getRequest().getParameter(parameter);
    }

    protected final boolean hasParameter(final String parameter) {
        return getParameter(parameter) != null;
    }

    protected final boolean hasPermission(final Permission... permissions) {
        return desriptor.getPermissionService().hasPermission(permissions);
    }

}
