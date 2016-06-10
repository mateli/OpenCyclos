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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

/**
 * Custom tag used to render the given customized file path
 * @author luis
 */
public class CustomizedFilePathTag extends AbstractCustomizedFileTag {
    private static final long serialVersionUID = -6765707316018371082L;

    private String            var;
    private int               scope            = PageContext.PAGE_SCOPE;

    @Override
    public int doEndTag() throws JspException {
        final String path = resolvePath();
        try {
            if (var == null) {
                pageContext.getOut().write(path);
            } else {
                pageContext.setAttribute(var, path, scope);
            }
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            release();
        }
        return EVAL_PAGE;
    }

    public String getVar() {
        return var;
    }

    @Override
    public void release() {
        super.release();
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }

    public void setScope(String scope) {
        scope = StringUtils.trimToNull(scope);
        if ("application".equalsIgnoreCase(scope)) {
            this.scope = PageContext.APPLICATION_SCOPE;
        } else if ("session".equalsIgnoreCase(scope)) {
            this.scope = PageContext.SESSION_SCOPE;
        } else if ("request".equalsIgnoreCase(scope)) {
            this.scope = PageContext.REQUEST_SCOPE;
        } else {
            this.scope = PageContext.PAGE_SCOPE;
        }
    }

    public void setVar(final String var) {
        this.var = var;
    }
}
