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

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntryQuery;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to view the error log
 * @author luis
 */
public class ViewErrorLogAction extends BaseErrorLogQueryAction {

    @Override
    protected Integer pageSize(final ActionContext context) {
        return null;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchErrorLogForm form = context.getForm();
        // Since there's only the current page and not showing removed, no data binder will be used
        final ErrorLogEntryQuery query = new ErrorLogEntryQuery();
        query.fetch(ErrorLogEntry.Relationships.LOGGED_USER, ErrorLogEntry.Relationships.PARAMETERS);
        query.setShowRemoved(false);
        final int currentPage = CoercionHelper.coerce(Integer.TYPE, form.getQuery("currentPage"));
        query.setPageParameters(new PageParameters(0, currentPage));
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

}
