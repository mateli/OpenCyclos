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

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A filter which is guaranteed to execute only once per request. Subclasses should implement the execute method
 * @author luis
 */
public abstract class OncePerRequestFilter extends BaseFilter {

    private String requestKey;

    @Override
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        // Check if the filter was already executed
        if (request.getAttribute(requestKey) != null) {
            // Already executed: just proceed the chain
            chain.doFilter(request, response);
        } else {
            // Not yet: set the control attribute and invoke the execute() method
            request.setAttribute(requestKey, Boolean.TRUE);
            execute(request, response, chain);
        }
    }

    @Override
    public void init(final FilterConfig config) throws ServletException {
        super.init(config);
        requestKey = "alreadyExecuted." + getClass().getName();
    }

    protected abstract void execute(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;
}
