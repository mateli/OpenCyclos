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
package nl.strohalm.cyclos.webservices.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;
import nl.strohalm.cyclos.services.services.ServiceClientServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.CyclosWebServicesClientFactory;
import nl.strohalm.cyclos.webservices.Permission;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.WebServiceContext.ContextType;
import nl.strohalm.cyclos.webservices.WebServiceFaultsEnum;
import nl.strohalm.cyclos.webservices.utils.WebServiceHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

/**
 * A CXF interceptor that will process authentication & authorization for web services
 * 
 * @author luis
 */
public class AuthInterceptor extends AbstractSoapInterceptor {

    private static final String[]                BLANK_CREDENTIALS = { "", "" };

    private ServiceClientServiceLocal            serviceClientServiceLocal;
    private ApplicationServiceLocal              applicationServiceLocal;

    private final Map<QName, ServiceOperation[]> cachedOperations  = new HashMap<QName, ServiceOperation[]>();

    public AuthInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(final SoapMessage message) throws Fault {
        final HttpServletRequest request = WebServiceHelper.requestOf(message);
        request.setAttribute(ContextType.class.getName(), ContextType.SERVICE_CLIENT);
        ServiceClient client = null;
        final ServletContext servletContext = servletContextOf(message);
        try {
            if (!applicationServiceLocal.isOnline()) {
                throw WebServiceHelper.fault(WebServiceFaultsEnum.APPLICATION_OFFLINE);
            }

            // Check non-secure access when HTTP is enabled
            if (Boolean.TRUE.equals(servletContext.getAttribute("cyclos.httpEnabled"))) {
                final String protocol = StringUtils.split(request.getRequestURL().toString(), "://")[0];
                if (!"https".equalsIgnoreCase(protocol)) {
                    throw WebServiceHelper.fault(WebServiceFaultsEnum.SECURE_ACCESS_REQUIRED);
                }
            }

            boolean allowed = false;
            // Find the service client
            client = resolveClient(request);
            if (client != null) {
                // Find the requested operation
                final ServiceOperation[] operations = resolveOperations(message);
                if (operations.length == 0) {
                    // When there are no operations, access is granted to anyone
                    allowed = true;
                } else {
                    // Check whether the client has access to the requested operation
                    final Set<ServiceOperation> permissions = client.getPermissions();
                    for (final ServiceOperation serviceOperation : operations) {
                        if (permissions.contains(serviceOperation)) {
                            allowed = true;
                            break;
                        }
                    }
                }
            }
            if (!allowed) {
                throw WebServiceHelper.fault(WebServiceFaultsEnum.UNAUTHORIZED_ACCESS);
            }

            // Initialize the logged user
            LoggedUser.init(client, request.getRemoteAddr(), null);

            // Initialize the context
            WebServiceContext.set(client, servletContext, request, message);
        } catch (Exception e) {
            WebServiceHelper.initializeContext(message);
            if (e instanceof SoapFault) {
                throw (SoapFault) e;
            } else {
                throw WebServiceHelper.fault(e);
            }
        }
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        applicationServiceLocal = applicationService;
    }

    public void setServiceClientServiceLocal(final ServiceClientServiceLocal serviceClientService) {
        serviceClientServiceLocal = serviceClientService;
    }

    /**
     * Find a matching {@link ServiceClient} for the given request
     */
    private ServiceClient resolveClient(final HttpServletRequest request) {
        final String address = request.getRemoteAddr();
        String[] credentials = WebServiceHelper.getCredentials(request);
        if (credentials == null) {
            credentials = BLANK_CREDENTIALS;
        }
        try {
            return serviceClientServiceLocal.findByAddressAndCredentials(address, credentials[0], credentials[1]);
        } catch (final EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * Resolve the possible operations for the current request
     */
    private ServiceOperation[] resolveOperations(final SoapMessage message) {
        final MessageInfo messageInfo = message.get(MessageInfo.class);
        final OperationInfo operation = messageInfo.getOperation();
        final QName operationQName = operation.getName();
        // Try to find the operations in the cache
        ServiceOperation[] operations = cachedOperations.get(operationQName);
        if (operations == null) {
            // Cache miss... find the interface method
            final String operationName = operationQName.getLocalPart();
            final String serviceName = operation.getInterface().getService().getName().getLocalPart();
            final Class<?> serviceInterface = CyclosWebServicesClientFactory.serviceInterfaceForName(serviceName);
            for (final Method m : serviceInterface.getMethods()) {
                if (m.getName().equals(operationName)) {
                    final Permission permission = m.getAnnotation(Permission.class);
                    operations = permission == null ? new ServiceOperation[0] : permission.value();
                    break;
                }
            }
            // Store the operations on the cache for further access
            cachedOperations.put(operationQName, operations);
        }
        return operations;
    }

    /**
     * Returns the SessionContext instance for the given SOAP message
     */
    private ServletContext servletContextOf(final SoapMessage message) {
        return (ServletContext) message.get(AbstractHTTPDestination.HTTP_CONTEXT);
    }
}
