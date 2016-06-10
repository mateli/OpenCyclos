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
package nl.strohalm.cyclos.webservices.utils;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.QueryParseException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.utils.logging.LoggingHandler;
import nl.strohalm.cyclos.utils.logging.WebServiceLogDTO;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.WebServiceContext.ContextType;
import nl.strohalm.cyclos.webservices.WebServiceFault;
import nl.strohalm.cyclos.webservices.WebServiceFaultsEnum;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

/**
 * Contains helper methods for web services
 * @author luis
 */
public class WebServiceHelper {

    private static final String CODE_PREFIX = "cyclos";

    /**
     * Returns a SOAP fault
     */
    public static SoapFault fault(final Throwable exception) {
        WebServiceFault fault;
        if ((exception instanceof ValidationException) || (exception instanceof IllegalArgumentException)) {
            fault = WebServiceFaultsEnum.INVALID_PARAMETERS;
        } else if (exception instanceof EntityNotFoundException) {
            final Class<? extends Entity> entityType = ((EntityNotFoundException) exception).getEntityType();
            if (entityType != null && (Element.class.isAssignableFrom(entityType) || User.class.isAssignableFrom(entityType))) {
                fault = WebServiceFaultsEnum.MEMBER_NOT_FOUND;
            } else {
                fault = WebServiceFaultsEnum.INVALID_PARAMETERS;
            }
        } else if (exception instanceof QueryParseException) {
            fault = WebServiceFaultsEnum.QUERY_PARSE_ERROR;
        } else if (exception instanceof InvalidCredentialsException) {
            fault = WebServiceFaultsEnum.INVALID_CREDENTIALS;
        } else if (exception instanceof BlockedCredentialsException) {
            fault = WebServiceFaultsEnum.BLOCKED_CREDENTIALS;
        } else if (exception instanceof AccessDeniedException || exception instanceof PermissionDeniedException) {
            fault = WebServiceFaultsEnum.UNAUTHORIZED_ACCESS;
        } else {
            fault = WebServiceFaultsEnum.UNEXPECTED_ERROR;
        }

        return fault(fault, exception);
    }

    public static SoapFault fault(final WebServiceFault fault) {
        return fault(fault.code(), null);
    }

    public static SoapFault fault(final WebServiceFault fault, final String serverDetailsMessage) {
        return fault(fault, new Exception(serverDetailsMessage));
    }

    /**
     * Throw a SoapFault with the specified fault code and the specified Throwable as the cause
     */
    public static SoapFault fault(final WebServiceFault fault, final Throwable cause) {
        return fault(fault.code(), cause);
    }

    /**
     * Extract the username and password from the Authorization HTTP header, supporting both BASIC and USER authentications, or null when not informed
     */
    public static String[] getCredentials(final HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        if (StringUtils.isEmpty(header)) {
            return null;
        }
        final String[] parts = header.split("\\s");
        // There should be 2 parts separated by a blank space: the method and the username:password section
        if (parts.length != 2) {
            return null;
        }
        final String method = parts[0];
        String credentials = parts[1];
        if (method.equalsIgnoreCase("basic")) {
            credentials = new String(Base64.decodeBase64(credentials.getBytes()));
        } else if (!method.equalsIgnoreCase("user")) {
            // Unsupported method
            return null;
        }
        // Return the credentials parts
        return credentials.split(":", 2);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T getParameter(final SoapMessage message) {
        final List parameterValues = message.getContent(List.class);
        if (CollectionUtils.isNotEmpty(parameterValues)) {
            return (T) parameterValues.iterator().next();
        } else {
            return (T) message.getContent(Object.class);
        }
    }

    /**
     * Initialize the POS Web Service Context.
     */
    public static void initializeContext(final Pos pos, final SoapMessage message) {
        WebServiceContext.set(pos, servletContextOf(message), requestOf(message), message);
    }

    /**
     * Initialize the Web Service Context for all WS using Services Clients.
     */
    public static void initializeContext(final ServiceClient client, final SoapMessage message) {
        WebServiceContext.set(client, servletContextOf(message), requestOf(message), message);
    }

    /**
     * Initialize the context with a minimal information. Used when there's neither a POS or a Service client.
     * @param message
     */
    public static void initializeContext(final SoapMessage message) {
        WebServiceContext.set(servletContextOf(message), requestOf(message), message);
    }

    /**
     * @return true if the specified client's id is equals to the restricted (used in this request) client's id
     */
    public static boolean isCurrentClient(final Long clientId) {
        if (WebServiceContext.getContextType() != ContextType.SERVICE_CLIENT) {
            return false;
        } else {
            return ObjectUtils.equals(WebServiceContext.getClient().getId(), clientId);
        }
    }

    /**
     * Checks whether the given fault was generated by Cyclos
     */
    public static boolean isFromCyclos(final Fault fault) {
        return CODE_PREFIX.equals(fault.getFaultCode().getNamespaceURI());
    }

    /**
     * Returns the HttpServletRequest instance for the given SOAP message
     */
    public static HttpServletRequest requestOf(final SoapMessage message) {
        return (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
    }

    /**
     * Returns the HttpServletRequest instance for the given SOAP message
     */
    public static ServletContext servletContextOf(final SoapMessage message) {
        return (ServletContext) message.get(AbstractHTTPDestination.HTTP_CONTEXT);
    }

    /**
     * Returns a SOAP fault
     */
    private static SoapFault fault(final String code, final Throwable th) throws SoapFault {
        return new SoapFault("Server error: " + code, th, faultCode(code));
    }

    /**
     * Returns a qualified name for a fault code
     */
    private static QName faultCode(final String code) {
        return new QName(CODE_PREFIX, code);
    }

    private LoggingHandler loggingHandler;

    public void error(final String error) {
        error(new Exception(error), false);
    }

    public void error(final Throwable th) {
        ValidationException valExc = null;
        try {
            if (th instanceof ValidationException) {
                valExc = (ValidationException) th;
                valExc.setShowDetailMessage(true);
            }
            error(th, true);
        } finally {
            if (valExc != null) {
                valExc.setShowDetailMessage(false);
            }
        }
    }

    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

    /**
     * Generates a log message
     */
    public void trace(final String message) {
        final WebServiceLogDTO log = WebServiceContext.newLog();
        log.setMessage(message);
        loggingHandler.traceWebService(log);
    }

    /**
     * @param th
     * @param logStackTrace if true logs the exception's stack trace.
     */
    private void error(final Throwable th, final boolean logStackTrace) {
        try {
            /* the context could not be initialized, for example, if there was an error in the unmarshalling phase */
            final WebServiceLogDTO log = WebServiceContext.newLog();
            if (logStackTrace) {
                log.setError(th);
            } else {
                log.setErrorMessage(th.getMessage());
            }
            loggingHandler.traceWebService(log);
        } finally {
            // in case of a Fault we are interested in the cause to be set as the error in the TxData
            if (th instanceof Fault && th.getCause() != null) {
                CurrentTransactionData.setError(th.getCause());
            } else {
                CurrentTransactionData.setError(th);
            }
        }
    }
}
