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
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Filter used to apply the correct character encoding
 * @author luis
 */
public class SanitizerFilter extends OncePerRequestFilter {

    private Pattern exclusionPattern = null;

    @Override
    public void destroy() {
    }

    @Override
    public void init(final FilterConfig config) throws ServletException {
        super.init(config);

        String excluded = config.getInitParameter("exclusionPattern");
        if (StringUtils.isNotEmpty(excluded)) {
            excluded = StringUtils.deleteWhitespace(StringUtils.replace(excluded, "\n", "|"));
            if (StringUtils.isNotEmpty(excluded)) {
                exclusionPattern = Pattern.compile(excluded);
            }
        }
    }

    @Override
    protected void execute(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = request;

        if (mustSanitize(httpRequest)) {
            httpRequest = new SanitizedHttpServletRequest(request);
        }

        chain.doFilter(httpRequest, response);
    }

    private boolean mustSanitize(final HttpServletRequest request) {
        return exclusionPattern != null && request.getPathInfo() != null && !exclusionPattern.matcher(request.getPathInfo()).matches();
    }
}
