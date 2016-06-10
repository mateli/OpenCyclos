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
package nl.strohalm.cyclos.controls.alerts;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.services.alerts.ErrorLogService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to view an error log entry's details
 * @author luis
 */
public class ViewErrorLogEntryAction extends BaseAction {

    private ErrorLogService errorLogService;

    @Inject
    public void setErrorLogService(final ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ViewErrorLogEntryForm form = context.getForm();
        final long id = form.getEntryId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        final HttpServletRequest request = context.getRequest();
        final ErrorLogEntry entry = errorLogService.load(id, ErrorLogEntry.Relationships.PARAMETERS, RelationshipHelper.nested(ErrorLogEntry.Relationships.LOGGED_USER, User.Relationships.ELEMENT));
        final User loggedUser = entry.getLoggedUser();
        if (loggedUser instanceof MemberUser) {
            request.setAttribute("loggedMember", loggedUser.getElement());
        } else if (loggedUser instanceof AdminUser) {
            request.setAttribute("loggedAdmin", loggedUser.getElement());
        }
        request.setAttribute("errorLogEntry", entry);
        return context.getInputForward();
    }
}
