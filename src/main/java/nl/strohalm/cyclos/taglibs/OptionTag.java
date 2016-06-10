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
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Possible option for MultiDropDown tag
 * @author luis
 */
public class OptionTag extends TagSupport {
    private static final long serialVersionUID = -1746655832154706505L;
    private Boolean           selected;
    private String            text;
    private String            value;

    @Override
    public int doEndTag() throws JspException {
        try {
            final MultiDropDownTag mdd = (MultiDropDownTag) findAncestorWithClass(this, MultiDropDownTag.class);
            if (mdd == null) {
                throw new IllegalStateException("cyclos:option tag must be nested in a cyclos:muldiDropDown tag");
            }
            final JspWriter out = pageContext.getOut();
            final String text = StringEscapeUtils.escapeJavaScript(StringUtils.trimToEmpty(this.text));
            final String value = StringEscapeUtils.escapeJavaScript(StringUtils.trimToEmpty(this.value));
            boolean selected;
            if (this.selected == null) {
                final List<String> selectedValues = mdd.getSelectedValues();
                selected = selectedValues != null && selectedValues.contains(value);
            } else {
                selected = this.selected;
            }
            out.println(mdd.getDivId() + ".values.push({text:'" + text + "', value:'" + value + "', selected:" + selected + "})");
            return EVAL_PAGE;
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            release();
        }
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public Boolean isSelected() {
        return selected;
    }

    @Override
    public void release() {
        super.release();
        selected = null;
        text = null;
        value = null;
    }

    public void setSelected(final Boolean selected) {
        this.selected = selected;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
