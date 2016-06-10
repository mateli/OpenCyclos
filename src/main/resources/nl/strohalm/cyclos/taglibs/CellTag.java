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
 * Layout cell
 * @author luis
 */
public class CellTag extends AbstractDynamicAttributesTag {

    private static final long serialVersionUID = -7905345351301614529L;
    private int               colspan          = 1;

    @Override
    public int doEndTag() throws JspException {
        final LayoutTag layoutTag = (LayoutTag) findAncestorWithClass(this, LayoutTag.class);
        if (layoutTag == null) {
            throw new JspException("Cell tag should be nested in a Layout tag");
        }
        final JspWriter out = pageContext.getOut();
        try {
            out.print("</td>");
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            layoutTag.finishCell(colspan);
            release();
        }
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        final JspWriter out = pageContext.getOut();
        try {
            out.print("<td colspan='" + colspan + "' " + attributesForTag() + ">");
        } catch (final IOException e) {
            throw new JspException(e);
        }
        return EVAL_BODY_INCLUDE;
    }

    public int getColspan() {
        return colspan;
    }

    @Override
    public void release() {
        super.release();
        colspan = 1;
    }

    public void setColspan(final int colspan) {
        this.colspan = colspan;
    }

    @Override
    protected String attributesForTag() {
        final Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("className", "class");
        replacements.put("elementId", "id");
        return super.attributesForTag(replacements);
    }
}
