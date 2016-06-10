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
 * Tag used to escape a JavaScript string. When var is defined, defines a that var on the specified scope (default = page) instead of writing to
 * response.
 * @author luis
 */
public class EscapeJSTag extends AbstractEscapeTag {
    private static final long serialVersionUID = -7217399899703991813L;

    @Override
    protected String escape(final String string) {
        String out = StringEscapeUtils.escapeJavaScript(string);
        out = StringUtils.replace(out, "\\\\n", "\\n");
        out = StringUtils.replace(out, "\\\\t", "\\t");
        return out;
    }
}
