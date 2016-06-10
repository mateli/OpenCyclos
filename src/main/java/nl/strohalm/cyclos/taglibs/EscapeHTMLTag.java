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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Tag used to escape a HTML portion. When var is defined, defines a that var on the specified scope (default = page) instead of writing to response.
 * @author luis
 */
public class EscapeHTMLTag extends AbstractEscapeTag {

    private static final long serialVersionUID = 5179023307642711559L;

    /**
     * Escapes the string
     */
    public static String escape(final String string, boolean brOnly) {
        String out = string;
        if (!brOnly) {
            out = StringEscapeUtils.escapeHtml(out);
        }
        out = StringUtils.replace(out, "\n", "<br />");
        out = StringUtils.replace(out, "\\n", "<br />");
        return out;
    }

    private boolean brOnly = false;

    public boolean isBrOnly() {
        return brOnly;
    }

    @Override
    public void release() {
        super.release();
        brOnly = false;
    }

    public void setBrOnly(final boolean brOnly) {
        this.brOnly = brOnly;
    }

    @Override
    protected String escape(final String string) {
        return escape(string, brOnly);
    }
}
