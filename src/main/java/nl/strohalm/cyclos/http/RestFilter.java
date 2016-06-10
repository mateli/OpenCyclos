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
package nl.strohalm.cyclos.http;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.application.ApplicationService;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.logging.LoggingHandler;
import nl.strohalm.cyclos.utils.logging.RestLogDTO;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.model.ServerErrorVO;
import nl.strohalm.cyclos.webservices.rest.RestHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

/**
 * Filter which handles the REST services context
 * @author luis
 */
public class RestFilter extends BaseWebServiceTransactionFilter {

    private static final Log   LOG = LogFactory.getLog(RestFilter.class);
    private ApplicationService applicationService;
    private LoggingHandler     loggingHandler;
    private boolean            restDisabled;

    @Inject
    public void setApplicationService(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Inject
    public void setCyclosProperties(final Properties properties) {
        final String disabled = properties.getProperty("cyclos.disableRestServices", "false");
        restDisabled = Boolean.parseBoolean(disabled);
    }

    @Inject
    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

    @Override
    protected boolean applyResponseStateOnRollback() {
        return false;
    }

    @Override
    protected void execute(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // If Rest is disabled by configuration, always send a 404 error
        if (restDisabled) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Check if the application is online
        if (!applicationService.isOnline()) {
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }

        // Add no cache control
        response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");

        // Check non-secure access when HTTP is enabled
        if (Boolean.TRUE.equals(getServletContext().getAttribute("cyclos.httpEnabled"))) {
            if (!"https".equalsIgnoreCase(request.getProtocol())) {
                response.sendError(HttpStatus.UPGRADE_REQUIRED.value(), HttpStatus.UPGRADE_REQUIRED.getReasonPhrase());
                response.addHeader("Upgrade", "TLS/1.0, HTTP/1.1");
                response.addHeader("Connection", "Upgrade");
                return;
            }
        }

        // When logging REST parameters, we need to wrap the request with StringBodyRequest in order to have the body available
        final boolean logParameters = loggingHandler.isRestParametersLogEnabled();
        super.execute(logParameters ? new StringBodyRequest(request) : request, response, chain);
    }

    @Override
    protected Log getLog() {
        return LOG;
    }

    @Override
    protected String getServiceName() {
        return "REST web service";
    }

    @Override
    protected boolean handleSilencedErrors() {
        return true;
    }

    @Override
    protected void onBeforeRunInTransaction(final HttpServletRequest request, final HttpServletResponse response) {
        WebServiceContext.set((Member) null, getServletContext(), request, response);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onError(final HttpServletRequest request, final HttpServletResponse response, final Throwable t) throws IOException {
        log(request, t);
        LOG.error("Error on REST call", t);
        final Pair<ServerErrorVO, Integer> error = RestHelper.resolveError(t);
        final ServerErrorVO vo = error.getFirst();
        final JSONObject json = new JSONObject();
        if (StringUtils.isNotEmpty(vo.getErrorCode())) {
            json.put("errorCode", vo.getErrorCode());
        }
        if (StringUtils.isNotEmpty(vo.getErrorDetails())) {
            json.put("errorDetails", vo.getErrorDetails());
        }
        response.setStatus(error.getSecond());
        response.setContentType("application/json");
        json.writeJSONString(response.getWriter());
        response.flushBuffer();
    }

    @Override
    protected void onTransactionEnd(final HttpServletRequest request, final HttpServletResponse response) {
        // Generate the trace log
        log(request, null);
    }

    private RestLogDTO buildLogDTO(final HttpServletRequest request, final Throwable error) {
        String body;
        if (request instanceof StringBodyRequest) {
            try {
                body = ((StringBodyRequest) request).getBody();
            } catch (final Exception e) {
                body = "<Error obtaining request body: " + e.toString() + ">";
            }
        } else {
            body = null;
        }

        final RestLogDTO dto = new RestLogDTO();
        dto.setRemoteAddress(request.getRemoteAddr());
        dto.setMember(LoggedUser.hasUser() ? LoggedUser.member() : null);
        dto.setMethod(request.getMethod());
        dto.setUri(request.getRequestURI());
        dto.setQueryString(request.getQueryString());
        dto.setRequestBody(body);
        return dto;
    }

    private void log(final HttpServletRequest request, final Throwable error) {
        final RestLogDTO dto = buildLogDTO(request, error);
        loggingHandler.traceRest(dto);
    }
}
