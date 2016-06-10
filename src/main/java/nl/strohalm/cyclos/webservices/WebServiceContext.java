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
package nl.strohalm.cyclos.webservices;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.logging.WebServiceLogDTO;
import nl.strohalm.cyclos.webservices.utils.WebServiceHelper;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The context for a given web service call
 * @author luis
 */
public class WebServiceContext {

    /**
     * Contains the type of web service being accessed
     * @author luis
     */
    public enum ContextType {
        POS, SERVICE_CLIENT, REST;
    }

    private static final ThreadLocal<WebServiceContext> HOLDER = new ThreadLocal<WebServiceContext>();

    /**
     * Removes any state for this request
     */
    public static void cleanup() {
        final WebServiceContext context = HOLDER.get();
        if (context != null) {
            context.client = null;
            context.pos = null;
            context.member = null;
            context.channel = null;
            context.request = null;
            context.servletContext = null;
            context.soapMessage = null;
        }

        HOLDER.remove();
        LoggedUser.cleanup();
    }

    /**
     * Returns the {@link Channel} for which the current {@link ServiceClient} is restricted, or null when none
     */
    public static Channel getChannel() {
        return assertContext(null).channel;

    }

    /**
     * Returns the {@link ServiceClient} for this request
     */
    public static ServiceClient getClient() {
        return assertContext(ContextType.SERVICE_CLIENT).client;
    }

    /**
     * Returns the current context type, or null if none
     */
    public static ContextType getContextType() {
        final WebServiceContext context = HOLDER.get();
        return context == null ? null : context.contextType;
    }

    /**
     * Returns the {@link Member} for which the current {@link ServiceClient} is restricted, or null when none
     */
    public static Member getMember() {
        return assertContext(null).member;
    }

    /**
     * Returns the web service method name
     */
    public static String getMethodName() {
        return assertContext(null).methodName;
    }

    /**
     * Returns the first method parameter
     */
    @SuppressWarnings("unchecked")
    public static <T> T getParameter() {
        return (T) assertContext(null).parameter;
    }

    /**
     * Returns the {@link MemberPos} for this request
     */
    public static Pos getPos() {
        return assertContext(ContextType.POS).pos;
    }

    /**
     * Returns the {@link HttpServletRequest} for this request
     */
    public static HttpServletRequest getRequest() {
        return assertContext(null).request;
    }

    /**
     * Returns the {@link HttpServletResponse}
     */
    public static HttpServletResponse getResponse() {
        return assertContext(null).response;
    }

    /**
     * Returns the web service name
     */
    public static String getServiceName() {
        return assertContext(null).serviceName;
    }

    /**
     * Returns the {@link ServletContext} for this request
     */
    public static ServletContext getServletContext() {
        return assertContext(null).servletContext;
    }

    /**
     * Returns the {@link SoapMessage} for this request
     */
    public static SoapMessage getSoapMessage() {
        return assertContext(null).soapMessage;
    }

    /**
     * Checks whether the current client has the given permission
     */
    public static boolean hasPermission(final ServiceOperation operation) {
        return getClient().getPermissions().contains(operation);
    }

    /**
     * Returns true if the web service context has been initialized. This method doesn't ensure that the context is ready for use.
     * @see #isReadyForUse() por ejemplo, cuando ocurre un error en un in-interceptor necesitamos inicializarlo con el soap message de entrada para
     * que los fault interceptors puedan trabajar.
     * @return
     */
    public static boolean isInitialized() {
        return HOLDER.get() != null;
    }

    public static boolean isPosContext() {
        return assertContext(null).isPosContextType();
    }

    /**
     * Returns true if the web service context has been initialized and is ready to be used by POS or Web Service Client.
     * @return
     */
    public static boolean isReadyForUse() {
        return isInitialized() && HOLDER.get().contextType != null;
    }

    /**
     * Returns data for logging the current execution
     */
    public static WebServiceLogDTO newLog() {
        final WebServiceLogDTO log = new WebServiceLogDTO();
        final WebServiceContext context = HOLDER.get();
        if (context != null) {
            log.setPos(context.pos);
            log.setServiceClient(context.client);
            log.setRemoteAddress(context.request == null ? null : context.request.getRemoteAddr());
            log.setServiceName(context.serviceName);
            log.setMethodName(context.methodName);
            log.setParameter(context.parameter);
        }
        return log;
    }

    /**
     * Sets the thread local's current context.<br>
     * The context for a REST web service operation is initialized by this method<br>
     */
    public static void set(final Member member, final ServletContext servletContext, final HttpServletRequest request, final HttpServletResponse response) {
        HOLDER.set(new WebServiceContext(member, servletContext, request, response));
    }

    /**
     * Sets the thread local's current context.<br>
     * The context for a POS web service operation is initialized by this method<br>
     * In case of POS web service the web services clients are not used.
     */
    public static void set(final Pos pos, final ServletContext servletContext, final HttpServletRequest request, final SoapMessage soapMessage) {
        HOLDER.set(new WebServiceContext(pos, servletContext, request, soapMessage));
    }

    /**
     * Sets the thread local's current context.<br>
     * It's invoked for those web services using a web service client.
     */
    public static void set(final ServiceClient client, final ServletContext servletContext, final HttpServletRequest request, final SoapMessage soapMessage) {
        HOLDER.set(new WebServiceContext(client, servletContext, request, soapMessage));
    }

    /**
     * Sets the thread local's current context.<br>
     * It's invoked when there's neither a POS or web service client, but minimal information like the request.
     */
    public static void set(final ServletContext servletContextOf, final HttpServletRequest requestOf, final SoapMessage message) {
        HOLDER.set(new WebServiceContext((Member) null, servletContextOf, requestOf, message));
    }

    /**
     * Only allowed for REST context. Updates the member
     */
    public static void setRestMember(final Member member) {
        assertContext(ContextType.REST).member = member;
    }

    private static WebServiceContext assertContext(final ContextType requiredCtx) {
        final WebServiceContext context = HOLDER.get();
        if (context == null) {
            throw new IllegalStateException("The web service context was not initialized yet");
        } else if (requiredCtx != null && requiredCtx != context.contextType) {
            throw new IllegalStateException(String.format("Invalid invocation: context type: %1$s", context.contextType));
        }

        return context;
    }

    private Member              member;
    private ServiceClient       client;
    private Pos                 pos;
    /* In case of POS access it's the POS build-in channel otherwise it's the client's channel */
    private Channel             channel;
    private HttpServletRequest  request;
    private HttpServletResponse response;
    private ServletContext      servletContext;
    private SoapMessage         soapMessage;
    private ContextType         contextType;
    private Object              parameter;
    private String              serviceName;

    private String              methodName;

    /**
     * Constructor for a REST services context
     * @param response
     */
    private WebServiceContext(final Member member, final ServletContext servletContext, final HttpServletRequest request, final HttpServletResponse response) {
        this.member = member;
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
        contextType = ContextType.REST;
        channel = loadChannel(Channel.REST);
        final String uri = request.getRequestURI();
        final String contextPath = request.getContextPath();
        final int pos = StringUtils.isEmpty(contextPath) ? -1 : uri.indexOf(contextPath);
        serviceName = pos < 0 ? uri : uri.substring(contextPath.length() + 1);
    }

    /**
     * Shared constructor
     */
    private WebServiceContext(final Member member, final ServletContext servletContext, final HttpServletRequest request, final SoapMessage soapMessage) {
        this.member = member;
        this.request = request;
        this.servletContext = servletContext;
        this.soapMessage = soapMessage;
        initParameter();
        initServiceName();
        initOperationName();
    }

    /**
     * Constructor for POS web service
     */
    private WebServiceContext(final Pos pos, final ServletContext servletContext, final HttpServletRequest request, final SoapMessage soapMessage) {
        this(pos.getMemberPos().getMember(), servletContext, request, soapMessage);
        this.pos = pos;
        channel = loadChannel(Channel.POS);
        contextType = ContextType.POS;
    }

    /**
     * Constructor for SOAP web service
     */
    private WebServiceContext(final ServiceClient client, final ServletContext servletContext, final HttpServletRequest request, final SoapMessage soapMessage) {
        this(client.getMember(), servletContext, request, soapMessage);
        this.client = client;
        channel = client.getChannel();
        contextType = ContextType.SERVICE_CLIENT;
    }

    private void initOperationName() {
        final MessageInfo messageInfo = soapMessage.get(MessageInfo.class);
        final OperationInfo operation = messageInfo.getOperation();
        final QName operationQName = operation.getName();
        methodName = operationQName.getLocalPart();
    }

    private void initParameter() {
        parameter = WebServiceHelper.getParameter(soapMessage);
    }

    private void initServiceName() {
        if (request != null) {
            final String uri = request.getRequestURI();
            serviceName = uri.substring(uri.lastIndexOf('/') + 1);
        }
    }

    private boolean isPosContextType() {
        return contextType == ContextType.POS;
    }

    private Channel loadChannel(final String internalName) {
        final ChannelService channelService = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("channelService", ChannelService.class);
        return channelService.loadByInternalName(Channel.REST);
    }
}
