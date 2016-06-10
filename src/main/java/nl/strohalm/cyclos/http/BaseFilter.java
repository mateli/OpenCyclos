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

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import nl.strohalm.cyclos.utils.SpringHelper;

/**
 * Base implementation for filters which inject beans on itself using {@link SpringHelper#injectBeans(ServletContext, Object)}
 * 
 * @author luis
 */
public abstract class BaseFilter implements Filter {

    protected FilterConfig config;

    @Override
    public void destroy() {
    }

    @Override
    public void init(final FilterConfig config) throws ServletException {
        this.config = config;
        final ServletContext servletContext = config.getServletContext();
        SpringHelper.injectBeans(servletContext, this);
    }

    protected ServletContext getServletContext() {
        return config.getServletContext();
    }

}
