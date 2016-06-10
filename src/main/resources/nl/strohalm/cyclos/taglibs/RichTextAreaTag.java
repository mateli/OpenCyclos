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
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;

/**
 * Renders a rich text area
 * @author luis
 */
public class RichTextAreaTag extends AbstractDynamicAttributesTag {
    private static final long serialVersionUID = -1526709179401334789L;
    private String            name;
    private String            value;
    private boolean           disabled;
    private String            styleClass;
    private String            styleId;

    public RichTextAreaTag() {
        release();
    }

    @Override
    public int doEndTag() throws JspException {

        final JspWriter out = pageContext.getOut();
        final String value = this.value;
        final String id = StringUtils.isEmpty(styleId) ? name : styleId;
        try {
            final StringBuilder sb = new StringBuilder();
            if (disabled) {
                // When a rich text field is not enabled, render a div and the text inside.
                // Only when enabling it the editor will be rendered
                sb.append("<div style='position:relative;padding-right:6px;'><div id='textOfField_").append(id).append("' class='fakeFieldDisabled' style='width:479px;height:200px;overflow:auto;'><table cellpadding='0' cellspacing='0' border='0' width='100%'><tr><td style='padding:0px'>");
                sb.append(StringUtils.isEmpty(value) ? "&nbsp;" : value);
                sb.append("</td></tr></table></div></div>");
                sb.append("<div id='envelopeOfField_").append(id).append("' style='display:none'>");
            }

            final StringBuilder className = new StringBuilder();
            if (StringUtils.isNotEmpty(styleClass)) {
                className.append(styleClass);
            }
            className.append(" full");
            className.append(disabled ? " richEditorDisabled" : " richEditor");
            final String containerId = "container_" + id;
            sb.append("<div class='richTextAreaContainer' id=\"").append(containerId).append("\">");
            sb.append("<textarea rows='6' name='").append(name).append('\'');
            if (StringUtils.isNotEmpty(styleId)) {
                sb.append(" id='" + styleId + '\'');
                sb.append(" fieldId='" + styleId + '\'');
            }
            sb.append(" class='").append(className).append('\'');
            sb.append(' ').append(attributesForTag()).append('>');
            sb.append(value);
            sb.append("</textarea>");
            sb.append("</div>");
            if (disabled) {
                sb.append("</div>");
            } else {
                // Eager init if not disabled
                sb.append("<script>\n");
                sb.append("richEditorsToInitialize.push($('").append(containerId).append("').firstChild);\n");
                sb.append("</script>\n");
            }
            out.print(sb.toString());
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

    public String getStyleClass() {
        return styleClass;
    }

    public String getStyleId() {
        return styleId;
    }

    public String getValue() {
        return value;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void release() {
        value = null;
        disabled = false;
        name = null;
        styleClass = null;
        styleId = null;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStyleClass(final String styleClass) {
        this.styleClass = styleClass;
    }

    public void setStyleId(final String styleId) {
        this.styleId = styleId;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
