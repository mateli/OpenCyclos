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

/**
 * Renders the url for a custom image
 * @author luis
 */
public class CustomImageUrlTag extends AbstractDynamicAttributesTag {
    private static final long   serialVersionUID = -7999583664245494008L;
    private static final String DEFAULT_TYPE     = "custom";
    private String              name;
    private String              type             = DEFAULT_TYPE;
    private boolean             thumbnail;

    @Override
    public int doEndTag() throws JspException {
        final JspWriter out = pageContext.getOut();
        try {
            out.print(resolveUrl());
            return EVAL_PAGE;
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            release();
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isThumbnail() {
        return thumbnail;
    }

    @Override
    public void release() {
        super.release();
        name = null;
        type = DEFAULT_TYPE;
        thumbnail = false;
    }

    public void setName(final String image) {
        name = image;
    }

    public void setThumbnail(final boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Returns the url
     */
    protected String resolveUrl() {
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        final StringBuilder sb = new StringBuilder();
        sb.append(request.getContextPath()).append(path()).append("?image=").append(name);
        if (thumbnail) {
            sb.append("&thumbnail=true");
        }
        return sb.toString();
    }

    private String path() {
        return "/" + type + "Image";
    }
}
