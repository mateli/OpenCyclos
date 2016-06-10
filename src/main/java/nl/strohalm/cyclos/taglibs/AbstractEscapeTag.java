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
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Base tag used for escaping content. When var is defined, defines a that var on the specified scope (default = page) instead of writing to response.
 * @author luis
 */
public abstract class AbstractEscapeTag extends BodyTagSupport {
    private static final long serialVersionUID = 3580157956266666665L;
    private String            body;
    private int               scope;
    private String            value;
    private String            var;

    public AbstractEscapeTag() {
        init();
    }

    @Override
    public int doAfterBody() throws JspException {
        if (bodyContent != null) {
            body = StringUtils.trimToNull(bodyContent.getString());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {

        // Retrieve the string form the attribute 'value' or from the body
        String out;
        if (value != null) {
            out = value;
        } else if (body != null) {
            out = body;
        } else {
            out = "";
        }

        // Escape the string as JavaScript
        final String js = escape(out);

        // If var is defined, export the variable instead of writing the result
        if (var != null) {
            pageContext.setAttribute(var, js, scope);
        } else {
            try {
                pageContext.getOut().print(js);
            } catch (final IOException e) {
                throw new JspException(e);
            }
        }

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        init();
    }

    public void setBody(final String body) {
        this.body = body;
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

    public void setValue(final Object value) {
        this.value = StringUtils.trimToNull(ObjectUtils.toString(value, ""));
    }

    public void setVar(final String var) {
        this.var = StringUtils.trimToNull(var);
    }

    protected abstract String escape(String string);

    private void init() {
        value = null;
        body = null;
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }
}
