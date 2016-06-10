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

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;
import org.springframework.beans.factory.InitializingBean;

/**
 * Base action for queries
 * @author luis
 */
public abstract class BaseQueryAction extends BaseAction implements LocalSettingsChangeListener, InitializingBean {

    private static PageParameters ensurePageParameters(final QueryParameters queryParameters, final Integer pageSize) {
        PageParameters pageParameters = queryParameters.getPageParameters();
        if (pageParameters == null) {
            pageParameters = new PageParameters();
            queryParameters.setPageParameters(pageParameters);
            pageParameters.setCurrentPage(0);
        }
        pageParameters.setPageSize(pageSize);

        return pageParameters;
    }

    private ResponseHelper responseHelper;
    private int            defaultPageSize;
    private int            maxIteratorResult;

    @Override
    public void afterPropertiesSet() throws Exception {
        final LocalSettings settings = settingsService.getLocalSettings();
        defaultPageSize = settings.getMaxPageResults();
        maxIteratorResult = settings.getMaxIteratorResults();
    }

    /**
     * Update the default page size on local settings updates
     */
    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        final LocalSettings settings = event.getSource();
        defaultPageSize = settings.getMaxPageResults();
        maxIteratorResult = settings.getMaxIteratorResults();
    }

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected final ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        if (isValidation(context)) {
            try {
                validateForm(context);
                responseHelper.writeValidationSuccess(context.getResponse());
            } catch (final ValidationException e) {
                responseHelper.writeValidationErrors(context.getResponse(), e);
            }
            return null;
        } else {
            final QueryParameters queryParameters = prepareForm(context);
            handlePageParameters(context, queryParameters);
            final boolean willExecuteQuery = willExecuteQuery(context, queryParameters);
            if (willExecuteQuery) {
                executeQuery(context, queryParameters);
            }
            request.setAttribute("query", queryParameters);
            request.setAttribute("queryExecuted", willExecuteQuery);
        }
        return context.getInputForward();
    }

    /**
     * Execute the query
     */
    protected abstract void executeQuery(ActionContext context, QueryParameters queryParameters);

    /**
     * This action will return the query page size. Returning null means the query is not paged, so, the query {@link ResultType} will be
     * {@link ResultType#ITERATOR}. Otherwise, will be {@link ResultType#PAGE}
     */
    protected Integer pageSize(final ActionContext context) {
        return defaultPageSize;
    }

    /**
     * Prepares the form, returning the query parameters
     */
    protected abstract QueryParameters prepareForm(ActionContext context);

    protected boolean shouldLimit() {
        return true;
    }

    /**
     * Validates the form
     */
    protected void validateForm(final ActionContext context) {
    }

    /**
     * Method used to determine if the query will be performed
     */
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return RequestHelper.isPost(context.getRequest());
    }

    /**
     * This method will prepare the query page parameters
     */
    private void handlePageParameters(final ActionContext context, final QueryParameters queryParameters) {
        final Integer pageSize = pageSize(context);
        if (pageSize == null) {
            queryParameters.setResultType(ResultType.ITERATOR);
            if ((maxIteratorResult > 0) && shouldLimit()) {
                ensurePageParameters(queryParameters, maxIteratorResult + 1);
            }
        } else {
            queryParameters.setResultType(ResultType.PAGE);
            ensurePageParameters(queryParameters, pageSize);
        }
    }

    /**
     * Returns if the form is being validated
     */
    private boolean isValidation(final ActionContext context) {
        return RequestHelper.isValidation(context.getRequest());
    }
}
