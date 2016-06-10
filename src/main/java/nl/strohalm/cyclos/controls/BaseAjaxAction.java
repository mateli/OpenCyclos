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
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.exceptions.LoggedOutException;
import nl.strohalm.cyclos.utils.ResponseHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Abstract action to AJAX responses
 * @author luis
 */
public abstract class BaseAjaxAction extends BaseAction {
    /**
     * An enum with possible content types
     * @author luis
     */
    public static enum ContentType {
        CSV("application/msexcel"), JSON("application/json"), TEXT("text/plain"), HTML("text/html"), XML("text/xml");
        private final String contentType;

        private ContentType(final String contentType) {
            this.contentType = contentType;
        }

        public String getContentType() {
            return contentType;
        }

        public void processResponse(final HttpServletResponse response) {
            response.setContentType(contentType);
        }
    }

    private static final Class<?> CLIENT_ABORT_EXCEPTION;
    static {
        Class<?> clientAbortException;
        try {
            clientAbortException = Class.forName("org.apache.catalina.connector.ClientAbortException");
        } catch (final Exception e) {
            clientAbortException = null;
        }
        CLIENT_ABORT_EXCEPTION = clientAbortException;
    }

    protected ResponseHelper      responseHelper;

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    /**
     * Return the response content type
     */
    protected abstract ContentType contentType();

    @Override
    protected final ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final HttpServletResponse response = context.getResponse();

        // Prepare the response headers
        final ContentType contentType = contentType();
        contentType.processResponse(response);
        responseHelper.setEncoding(response);
        responseHelper.setNoCache(response);

        // Render the response
        try {
            renderContent(context);
            response.flushBuffer();
        } catch (final Exception e) {
            if (CLIENT_ABORT_EXCEPTION != null && CLIENT_ABORT_EXCEPTION.isInstance(e)) {
                // Nothing to do - the client has just cancelled the connection
            } else {
                handleException(request, response, e);
            }
        }

        // The response is now complete - return null
        return null;
    }

    // Send the error to response
    protected void handleException(final HttpServletRequest request, final HttpServletResponse response, final Exception e) throws Exception {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, StringUtils.trimToEmpty(e.getMessage()));
    }

    /**
     * Render the AJAX result
     */
    protected abstract void renderContent(ActionContext context) throws Exception;

    /**
     * Ajax actions are never stored on navigation path
     */
    @Override
    protected boolean storePath(final ActionMapping actionMapping, final HttpServletRequest request) {
        return false;
    }

    @Override
    protected User validate(final HttpServletRequest request, final HttpServletResponse response, final ActionMapping actionMapping) throws Exception {
        try {
            return super.validate(request, response, actionMapping);
        } catch (final LoggedOutException e) {
            request.getSession().invalidate();
            // Create a new session
            final HttpSession session = request.getSession();
            session.setAttribute("loggedOut", true);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;

        }
    }
}
