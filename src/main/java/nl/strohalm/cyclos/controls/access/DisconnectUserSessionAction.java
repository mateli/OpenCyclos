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
package nl.strohalm.cyclos.controls.access;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.access.Session;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to disconnect a logged user session
 * @author luis
 */
public class DisconnectUserSessionAction extends BaseAction {

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final DisconnectUserSessionForm form = context.getForm();
        final long sessionId = form.getSessionId();
        if (sessionId <= 0) {
            throw new ValidationException();
        }
        try {
            final Session session = EntityHelper.reference(Session.class, sessionId);
            accessService.disconnect(session);
            context.sendMessage("disconnect.disconnected");
            return context.getSuccessForward();
        } catch (final EntityNotFoundException e) {
            throw new ValidationException();
        } catch (final NotConnectedException e) {
            return context.sendError("disconnect.error.notConnected");
        }
    }

}
