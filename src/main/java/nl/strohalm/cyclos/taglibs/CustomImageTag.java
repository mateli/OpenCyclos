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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * Renders a custom image &lt;img&gt; element
 * @author luis
 */
public class CustomImageTag extends CustomImageUrlTag {
    private static final long serialVersionUID = -7999583664245494008L;

    @Override
    public int doEndTag() throws JspException {
        final JspWriter out = pageContext.getOut();
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append("<img src=\"").append(resolveUrl()).append("\" ").append(attributesForTag()).append('>');
            out.print(sb.toString());
            return EVAL_PAGE;
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            release();
        }
    }

    @Override
    protected String attributesForTag() {
        final Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("className", "class");
        replacements.put("elementId", "id");
        return super.attributesForTag(replacements);
    }
}
