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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * Abstract tag that implements DynamicAttributes
 * @author luis
 */
public class AbstractDynamicAttributesTag extends TagSupport implements DynamicAttributes {

    private static final long   serialVersionUID = 4597376430986872637L;

    private Map<String, Object> attributes       = defaultAttributes();

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public void release() {
        super.release();
        attributes = defaultAttributes();
    }

    public void setAttributes(final Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setDynamicAttribute(final String ns, final String name, final Object value) throws JspException {
        attributes.put(name, value);
    }

    protected String attributesForTag() {
        return attributesForTag(null);
    }

    protected String attributesForTag(final Map<String, String> replaceNames) {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, Object> entry : attributes.entrySet()) {
            final String value = StringUtils.trimToEmpty(entry.getValue() == null ? null : entry.getValue().toString());
            String name = entry.getKey();
            if (replaceNames != null && replaceNames.containsKey(name)) {
                name = replaceNames.get(name);
            }
            sb.append(" ").append(name).append("=\"");
            sb.append(StringUtils.replace(StringUtils.replace(value, "\"", "&quot;"), "\n", "\\n"));
            sb.append("\"");
        }
        return sb.toString();
    }

    protected Map<String, Object> defaultAttributes() {
        return new HashMap<String, Object>();
    }
}
