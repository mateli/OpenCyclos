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
package nl.strohalm.cyclos.struts;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.SecurePlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * Custom SecurePlugIn that reads the configuration from cyclos.properties instead of struts-config.xml
 * @author luis
 */
public class CyclosSecurePlugIn extends SecurePlugIn {

    @Override
    public void init(final ActionServlet servlet, final ModuleConfig config) throws ServletException {
        super.init(servlet, config);

        // Load the properties file
        final Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/cyclos.properties"));
        } catch (final IOException e) {
            throw new ServletException("Error reading cyclos.properties");
        }
        final boolean httpEnabled = "true".equalsIgnoreCase(properties.getProperty("cyclos.security.enable", "false"));
        setEnable(String.valueOf(httpEnabled));
        setHttpPort(properties.getProperty("cyclos.security.port.http", "80"));
        setHttpsPort(properties.getProperty("cyclos.security.port.https", "443"));
        setAddSession("false");
        servlet.getServletContext().setAttribute("cyclos.httpEnabled", httpEnabled);
    }
}
