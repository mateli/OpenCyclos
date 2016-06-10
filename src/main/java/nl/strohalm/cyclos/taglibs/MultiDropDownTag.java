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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.taglib.html.Constants;

/**
 * Renders a multi-dropdown control
 * @author luis
 */
public class MultiDropDownTag extends TagSupport {

    private static final long serialVersionUID = 7107831808471414750L;
    private String            divId;
    private Integer           maxWidth;
    private Integer           minWidth;
    private String            name;
    private boolean           singleField;
    private boolean           open;
    private boolean           disabled;
    private Integer           size;
    private List<String>      selectedValues;
    private String            emptyLabel;
    private String            emptyLabelKey;
    private String            varName;
    private String            onchange;

    private MessageHelper     messageHelper;

    public MultiDropDownTag() {
        release();
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            // Build the options object
            final StringBuilder options = new StringBuilder();
            options.append('{');
            options.append("'singleField':").append(singleField).append(',');
            options.append("'open':").append(open).append(',');
            options.append("'disabled':").append(disabled);
            if (size != null) {
                options.append(",'size':").append(size);
            }
            if (minWidth != null) {
                options.append(",'minWidth':").append(minWidth);
            }
            if (maxWidth != null) {
                options.append(",'maxWidth':").append(maxWidth);
            }
            if (emptyLabelKey != null) {
                emptyLabel = messageHelper.message(emptyLabelKey);
            }
            if (emptyLabel != null) {
                options.append(",'emptyLabel':\"").append(StringEscapeUtils.escapeJavaScript(emptyLabel)).append('"');
            }
            if (onchange != null) {
                options.append(",'onchange':\"").append(StringEscapeUtils.escapeJavaScript(onchange)).append('"');
            }
            options.append('}');

            // Write the rest of the script
            final JspWriter out = pageContext.getOut();
            if (StringUtils.isNotEmpty(varName)) {
                out.print(varName + "=");
            }
            out.println("new MultiDropDown(" + divId + ", '" + name + "', " + divId + ".values, " + options + ")");
            out.println("</script>");
            return EVAL_PAGE;
        } catch (final IOException e) {
            throw new JspException(e);
        } finally {
            release();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public int doStartTag() throws JspException {
        divId = "_container_" + System.currentTimeMillis() + "_" + new Random().nextInt(Integer.MAX_VALUE);

        if (selectedValues == null) {
            try {
                final Object form = pageContext.findAttribute(Constants.BEAN_KEY);
                selectedValues = CoercionHelper.coerce(List.class, PropertyHelper.get(form, name));
                for (int i = 0; i < selectedValues.size(); i++) {
                    selectedValues.set(i, CoercionHelper.coerce(String.class, selectedValues.get(i)));
                }
            } catch (final Exception e) {
                // Leave selected values empty
            }
        }

        try {
            final JspWriter out = pageContext.getOut();
            out.print("<div");
            if (maxWidth != null) {
                out.print(" style='width:" + maxWidth + "px'");
            }
            out.println(" id='" + divId + "'></div>");
            // Write the start of the script
            out.println("<script>");
            out.println("var mddNoItemsMessage = \"" + StringEscapeUtils.escapeJavaScript(messageHelper.message("multiDropDown.noItemsMessage")) + "\";");
            out.println("var mddSingleItemsMessage = \"" + StringEscapeUtils.escapeJavaScript(messageHelper.message("multiDropDown.singleItemMessage")) + "\";");
            out.println("var mddMultiItemsMessage = \"" + StringEscapeUtils.escapeJavaScript(messageHelper.message("multiDropDown.multiItemsMessage")) + "\";");

            out.println("var " + divId + " = $('" + divId + "');");
            out.println(divId + ".values = [];");
        } catch (final IOException e) {
            throw new JspException(e);
        }

        return EVAL_BODY_INCLUDE;
    }

    public String getDivId() {
        return divId;
    }

    public String getEmptyLabel() {
        return emptyLabel;
    }

    public String getEmptyLabelKey() {
        return emptyLabelKey;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public Integer getMinWidth() {
        return minWidth;
    }

    public String getName() {
        return name;
    }

    public String getOnchange() {
        return onchange;
    }

    public Object getSelected() {
        return selectedValues;
    }

    public List<String> getSelectedValues() {
        return selectedValues;
    }

    public Integer getSize() {
        return size;
    }

    public String getVarName() {
        return varName;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isSingleField() {
        return singleField;
    }

    @Override
    public void release() {
        super.release();
        divId = null;
        maxWidth = null;
        minWidth = null;
        size = null;
        name = null;
        emptyLabel = null;
        emptyLabelKey = null;
        selectedValues = null;
        singleField = false;
        open = false;
        disabled = false;
        varName = null;
        onchange = null;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }

    public void setEmptyLabel(final String emptyLabel) {
        this.emptyLabel = emptyLabel;
    }

    public void setEmptyLabelKey(final String emptyLabelKey) {
        this.emptyLabelKey = emptyLabelKey;
    }

    public void setMaxWidth(final Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMinWidth(final Integer minWidth) {
        this.minWidth = minWidth;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOnchange(final String onchange) {
        this.onchange = onchange;
    }

    public void setOpen(final boolean open) {
        this.open = open;
    }

    @Override
    public void setPageContext(final PageContext pageContext) {
        super.setPageContext(pageContext);
        messageHelper = SpringHelper.bean(pageContext.getServletContext(), MessageHelper.class);
    }

    @SuppressWarnings("unchecked")
    public void setSelected(final Object object) {
        if (object == null) {
            selectedValues = null;
        } else {
            final Iterator<Object> it = IteratorUtils.getIterator(object);
            selectedValues = new ArrayList<String>();
            while (it.hasNext()) {
                selectedValues.add(CoercionHelper.coerce(String.class, it.next()));
            }
        }
    }

    public void setSelectedValues(final List<String> selectedValues) {
        this.selectedValues = selectedValues;
    }

    public void setSingleField(final boolean singleField) {
        this.singleField = singleField;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

    public void setVarName(final String varName) {
        this.varName = varName;
    }
}
