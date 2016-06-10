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

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import nl.strohalm.cyclos.webservices.access.AccessWebService;
import nl.strohalm.cyclos.webservices.accounts.AccountWebService;
import nl.strohalm.cyclos.webservices.ads.AdWebService;
import nl.strohalm.cyclos.webservices.fields.FieldWebService;
import nl.strohalm.cyclos.webservices.infotexts.InfoTextWebService;
import nl.strohalm.cyclos.webservices.members.MemberWebService;
import nl.strohalm.cyclos.webservices.payments.PaymentWebService;
import nl.strohalm.cyclos.webservices.pos.PosWebService;
import nl.strohalm.cyclos.webservices.sms.SmsWebService;
import nl.strohalm.cyclos.webservices.webshop.WebShopWebService;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.BusFactory;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.auth.DefaultBasicAuthSupplier;

/**
 * A class capable of generating proxies to the various Cyclos web services
 * 
 * @author luis
 */
public class CyclosWebServicesClientFactory implements Serializable {

    private static final long                  serialVersionUID = 8877667897548825737L;
    private static final Map<Class<?>, String> SERVICES;
    static {
        final Map<Class<?>, String> services = new HashMap<Class<?>, String>();
        services.put(MemberWebService.class, "members");
        services.put(AdWebService.class, "ads");
        services.put(FieldWebService.class, "fields");
        services.put(WebShopWebService.class, "webshop");
        services.put(AccessWebService.class, "access");
        services.put(AccountWebService.class, "account");
        services.put(PaymentWebService.class, "payment");
        services.put(PosWebService.class, "pos");
        services.put(SmsWebService.class, "sms");
        services.put(InfoTextWebService.class, "infoTexts");
        SERVICES = Collections.unmodifiableMap(services);
    }

    public static Class<?> serviceInterfaceForName(final String name) {
        for (final Map.Entry<Class<?>, String> entry : SERVICES.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private transient Map<Class<?>, Object> cachedProxies     = new HashMap<Class<?>, Object>();

    private String                          serverRootUrl;
    private String                          username;
    private String                          password;

    /**
     * Indicates whether that the hostname given in the HTTPS URL will be checked against the service's Common Name (CN) given in its certificate
     * during SOAP client requests NOT recommended for production time
     */
    private boolean                         disableCNCheck;

    /**
     * Used to connect to servers with self-signed certificates (not issued by a CA) You can set this to true or set the following system properties
     * (-D):
     * <ul>
     * <li>javax.net.ssl.trustStore (the path to the keystore containing the certificate)
     * <li>javax.net.ssl.trustStorePassword (the keystore password to open the keystore)
     * </ul>
     */
    private boolean                         trustAllCerts;

    /**
     * Set the read timeout value in milliseconds. A timeout of zero is interpreted as an infinite timeout Defaults to 60000 ms
     */
    private long                            readTimeout       = 60000L;

    /**
     * Set the connection timeout value in milliseconds. A timeout of zero is interpreted as an infinite timeout Defaults to 60000 ms
     */
    private long                            connectionTimeout = 60000L;

    /**
     * Empty constructor
     */
    public CyclosWebServicesClientFactory() {
    }

    /**
     * Constructs the factory with the server root url
     */
    public CyclosWebServicesClientFactory(final String serverRootUrl) {
        setServerRootUrl(serverRootUrl);
    }

    /**
     * Constructs the factory with the server root url and credentials
     */
    public CyclosWebServicesClientFactory(final String serverRootUrl, final String username, final String password) {
        this(serverRootUrl);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Returns a proxy for the accounts web service
     */
    public AccessWebService getAccessWebService() {
        return proxyFor(AccessWebService.class);
    }

    /**
     * Returns a proxy for the account web service
     */
    public AccountWebService getAccountWebService() {
        return proxyFor(AccountWebService.class);
    }

    /**
     * Returns a proxy for the ads web service
     */
    public AdWebService getAdWebService() {
        return proxyFor(AdWebService.class);
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Returns a proxy for the fields web service
     */
    public FieldWebService getFieldWebService() {
        return proxyFor(FieldWebService.class);
    }

    /**
     * Returns a proxy for the info text web service
     */
    public InfoTextWebService getInfoTextWebService() {
        return proxyFor(InfoTextWebService.class);
    }

    /**
     * Returns a proxy for the members web service
     */
    public MemberWebService getMemberWebService() {
        return proxyFor(MemberWebService.class);
    }

    /**
     * Returns a proxy for the payment web service
     */
    public PaymentWebService getPaymentWebService() {
        return proxyFor(PaymentWebService.class);
    }

    /**
     * Returns a proxy for the pos web service
     */
    public PosWebService getPosWebService() {
        return proxyFor(PosWebService.class);
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    /**
     * Returns a proxy for the sms web service
     */
    public SmsWebService getSmsWebService() {
        return proxyFor(SmsWebService.class);
    }

    /**
     * Returns a proxy for the web shop web service
     */
    public WebShopWebService getWebShopWebService() {
        return proxyFor(WebShopWebService.class);
    }

    /**
     * Creates a proxy for the given interface without
     * @param <T> The type bound to the service interface
     * @param serviceInterface The service interface
     * @return The proxy for the given interface
     */
    @SuppressWarnings("unchecked")
    public synchronized <T> T proxyFor(final Class<T> serviceInterface) {
        // Check for a cached instance
        final Object cached = cachedProxies.get(serviceInterface);
        if (cached != null) {
            return (T) cached;
        }
        // Cache miss. Create the proxy
        final String url = resolveUrlFor(serviceInterface);
        if (url == null) {
            throw new IllegalStateException("Cannot resolve url for service " + serviceInterface.getName() + " for server root url " + serverRootUrl);
        }

        // Create a proxy factory
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(serviceInterface);
        factory.setAddress(url);

        // Create the proxy
        final Object proxy = factory.create();

        final Client client = ClientProxy.getClient(proxy);
        final HTTPConduit http = (HTTPConduit) client.getConduit();

        // If the username / password are set, use them
        if (username != null || password != null) {
            final AuthorizationPolicy authorization = new AuthorizationPolicy();
            authorization.setUserName(username);
            authorization.setPassword(password);

            http.setAuthorization(authorization);
            http.setAuthSupplier(new DefaultBasicAuthSupplier());
        }

        http.setTlsClientParameters(getTLSClientParameters());
        http.getClient().setConnectionTimeout(connectionTimeout);
        http.getClient().setReceiveTimeout(readTimeout);

        // The proxy is ready. Store it on the cache
        cachedProxies.put(serviceInterface, proxy);

        return (T) proxy;
    }

    /**
     * Resolves the service url for the given interface
     */
    public String resolveUrlFor(final Class<?> serviceInterface) {
        final String service = SERVICES.get(serviceInterface);
        if (serverRootUrl == null || service == null) {
            throw new IllegalArgumentException("Unknown web service interface: " + serviceInterface.getName());
        }
        return serverRootUrl + "/services/" + service;
    }

    public void setConnectionTimeout(final long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setDisableCNCheck(final boolean disableCNCheck) {
        this.disableCNCheck = disableCNCheck;
    }

    /**
     * Sets the service client password
     */
    public void setPassword(final String password) {
        this.password = StringUtils.trimToNull(password);
        invalidateCache();
    }

    public void setReadTimeout(final long readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Sets the server root url
     */
    public void setServerRootUrl(final String serverRootUrl) {
        this.serverRootUrl = StringUtils.trimToNull(serverRootUrl);
        // Remove the trailing slash, if any
        if (this.serverRootUrl != null && this.serverRootUrl.endsWith("/")) {
            this.serverRootUrl = this.serverRootUrl.substring(0, this.serverRootUrl.length() - 1);
        }
        invalidateCache();
    }

    public void setTrustAllCerts(final boolean trustAllCerts) {
        this.trustAllCerts = trustAllCerts;
    }

    /**
     * Sets the service client username
     */
    public void setUsername(final String username) {
        this.username = StringUtils.trimToNull(username);
        invalidateCache();
    }

    /**
     * 
     */
    public void shutdown() {
        for (final Object proxy : cachedProxies.values()) {
            try {
                final Client client = ClientProxy.getClient(proxy);
                client.destroy();
            } catch (final Exception e) {
                // Ignore
            }
        }
        cachedProxies.clear();
        BusFactory.getDefaultBus().shutdown(true);
    }

    private TLSClientParameters getTLSClientParameters() {
        final TLSClientParameters tlsCP = new TLSClientParameters();

        if (trustAllCerts) {
            final TrustManager[] myTrustStoreKeyManagers = getTrustManagers();
            tlsCP.setTrustManagers(myTrustStoreKeyManagers);
        }

        tlsCP.setDisableCNCheck(disableCNCheck);

        return tlsCP;
    }

    private TrustManager[] getTrustManagers() {
        final TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
            }

            @Override
            public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        } };

        return trustManagers;
    }

    /**
     * Invalidate the cache proxies
     */
    private void invalidateCache() {
        cachedProxies.clear();
    }
}
