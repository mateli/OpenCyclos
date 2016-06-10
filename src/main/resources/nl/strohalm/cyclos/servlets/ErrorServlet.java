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
package nl.strohalm.cyclos.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.webservices.WebServiceContext.ContextType;

/**
 * Displays error pages, or handles specific web services error messages
 * @author luis
 */
public class ErrorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final Integer statusCode = CoercionHelper.coerce(Integer.class, req.getParameter("statusCode"));
        final ContextType contextType = (ContextType) req.getAttribute(ContextType.class.getName());
        if (contextType == ContextType.REST) {
            // Is a web service. Don't send HTML pages
            resp.setContentType("text/plain");
            resp.setContentLength(0);
        } else {
            // Normal web access
            RequestDispatcher dispatcher = null;
            if (statusCode != null) {
                switch (statusCode) {
                    case 404:
                        dispatcher = req.getRequestDispatcher("/pages/general/error404.jsp");
                        break;
                }
            }
            if (dispatcher == null) {
                dispatcher = req.getRequestDispatcher("/pages/general/runtimeError.jsp");
            }
            dispatcher.forward(req, resp);
        }
    }

}
