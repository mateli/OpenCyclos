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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag that will generate a help link
 * @author luis
 */
public class HelpTag extends TagSupport {
    private static final long serialVersionUID = -8461642380556423065L;
    private String            page;
    private String            width;
    private boolean           td;

    public HelpTag() {
        release();
    }

    @Override
    public int doStartTag() throws JspException {
        final JspWriter out = pageContext.getOut();
        try {
            if (td) {
                out.println("<td class=\"tdHelpIcon\" nowrap=\"nowrap\" align=\"right\" width=\"" + width + "\" valign=\"middle\">");
            }
            final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            out.println("<img class=\"help\" helpPage=\"" + page + "\" src=\"" + request.getContextPath() + "/pages/images/help.gif\">");
            if (td) {
                out.println("</td>");
            }
        } catch (final IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }

    public String getPage() {
        return page;
    }

    public String getWidth() {
        return width;
    }

    public boolean isTd() {
        return td;
    }

    @Override
    public void release() {
        super.release();
        page = null;
        td = true;
        width = "4%";
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public void setTd(final boolean td) {
        this.td = td;
    }

    public void setWidth(final String width) {
        this.width = width;
    }
}
