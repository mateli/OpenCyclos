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
package nl.strohalm.cyclos.taglibs;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * Renders a script tag, appending the cyclos version as a parameter to avoid browser cache between versions
 * @author luis
 */
public class ScriptTag extends TagSupport {
    private static final long serialVersionUID = 5044196774749124533L;
    private String            src;

    @Override
    public int doEndTag() throws JspException {
        src = StringUtils.trimToNull(src);
        if (src != null) {
            try {
                pageContext.getOut().println(generateScriptTag());
            } catch (final IOException e) {
                throw new JspException(e);
            } finally {
                release();
            }
        }
        return EVAL_PAGE;
    }

    public String generateScriptTag() {
        final StringBuilder sb = new StringBuilder();
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        final String fullPath = request.getContextPath() + src;
        final Object version = pageContext.getServletContext().getAttribute("cyclosVersion");
        sb.append("<script language=\"javascript\" src=\"").append(fullPath).append("?version=").append(version).append("\"></script>");
        return sb.toString();
    }

    public String getSrc() {
        return src;
    }

    @Override
    public void release() {
        super.release();
        src = null;
    }

    public void setSrc(final String src) {
        this.src = src;
    }

}
