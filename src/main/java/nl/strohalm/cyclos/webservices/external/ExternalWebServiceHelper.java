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
package nl.strohalm.cyclos.webservices.external;

import java.io.IOException;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import nl.strohalm.cyclos.CyclosConfiguration;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;

public class ExternalWebServiceHelper {
    @SuppressWarnings("unchecked")
    public static <T> T proxyFor(final Class<T> clazz, final String url) throws IOException {
        // Create the proxy
        final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(clazz);
        factory.setAddress(url);
        final Object proxy = factory.create();

        final boolean disableCNCheck = Boolean.valueOf(CyclosConfiguration.getCyclosProperties().getProperty("cyclos.security.disableCNCheck"));
        final boolean trustAllCerts = Boolean.valueOf(CyclosConfiguration.getCyclosProperties().getProperty("cyclos.security.trustAllCerts"));
        long connectionTimeout = -1;
        long receiveTimeout = -1;
        try {
            connectionTimeout = Long.valueOf(CyclosConfiguration.getCyclosProperties().getProperty("cyclos.webservices.connectionTimeout"));
        } catch (final NumberFormatException e) {
            // do nothing, use default value
        }
        try {
            receiveTimeout = Long.valueOf(CyclosConfiguration.getCyclosProperties().getProperty("cyclos.webservices.receiveTimeout"));
        } catch (final NumberFormatException e) {
            // do nothing, use default value
        }

        final Client client = ClientProxy.getClient(proxy);
        final HTTPConduit http = (HTTPConduit) client.getConduit();

        if (disableCNCheck || trustAllCerts) {
            http.setTlsClientParameters(getTLSClientParameters(trustAllCerts, disableCNCheck));
        }
        if (connectionTimeout >= 0) {
            http.getClient().setConnectionTimeout(connectionTimeout);
        }
        if (receiveTimeout >= 0) {
            http.getClient().setReceiveTimeout(receiveTimeout);
        }

        return (T) proxy;
    }

    private static TLSClientParameters getTLSClientParameters(final boolean trustAllCerts, final boolean disableCNCheck) {
        final TLSClientParameters tlsCP = new TLSClientParameters();

        if (trustAllCerts) {
            final TrustManager[] myTrustStoreKeyManagers = getTrustManagers();
            tlsCP.setTrustManagers(myTrustStoreKeyManagers);
        }

        tlsCP.setDisableCNCheck(disableCNCheck);

        return tlsCP;
    }

    private static TrustManager[] getTrustManagers() {
        final TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
            public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
            }

            public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        } };

        return trustManagers;
    }
}
