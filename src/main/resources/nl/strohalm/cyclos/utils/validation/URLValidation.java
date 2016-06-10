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
package nl.strohalm.cyclos.utils.validation;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * A validation for urls
 * @author luis
 */
public class URLValidation implements PropertyValidation {
    private static final long          serialVersionUID                   = -7933981104151866154L;
    private static final URLValidation INSTANCE_REQUIRING_DOT_ON_HOSTNAME = new URLValidation(true);
    private static final URLValidation INSTANCE                           = new URLValidation(false);

    public static URLValidation instance(final boolean requireDotOnHostName) {
        return requireDotOnHostName ? INSTANCE_REQUIRING_DOT_ON_HOSTNAME : INSTANCE;
    }

    private final boolean requireDotOnHostname;

    private URLValidation(final boolean requireDotOnHostname) {
        this.requireDotOnHostname = requireDotOnHostname;
    }

    @Override
    public ValidationError validate(final Object object, final Object property, final Object value) {
        String str = (String) value;
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            // Use http as the default protocol
            if (!str.contains("://")) {
                str = "http://" + str;
            }
            final URL url = new URL(str);
            final String protocol = url.getProtocol();
            // Only allow http or https
            if (!protocol.equalsIgnoreCase("http") && !protocol.equalsIgnoreCase("https")) {
                throw new MalformedURLException();
            }
            if (requireDotOnHostname) {
                // Ensure there is at least one dot on the hostname
                if (url.getHost().indexOf('.') < 0) {
                    throw new MalformedURLException();
                }
            }
            // The conversion to URI will enforce the RFC2396 compliance
            url.toURI();
            return null;
        } catch (final MalformedURLException e) {
            return new InvalidError();
        } catch (final URISyntaxException e) {
            return new InvalidError();
        }
    }
}
