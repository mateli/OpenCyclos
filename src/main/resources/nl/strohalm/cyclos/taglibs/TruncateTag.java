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
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Truncates a string. Useful on lists where the description may contain large data
 * @author luis
 */
public class TruncateTag extends TagSupport {
    public static final int      DEFAULT_LENGTH            = 60;
    public static final int      DEFAULT_LINES             = 1;
    private static final Pattern LINE_BREAK_PATTERN        = Pattern.compile("[\\r\\n]+", Pattern.CASE_INSENSITIVE);
    private static final Pattern BREAK_TAG_PATTERN         = Pattern.compile("<(p|br)[^>]*>", Pattern.CASE_INSENSITIVE);
    private static final Pattern PARAGRAPH_END_TAG_PATTERN = Pattern.compile("<\\/p[^>]*>", Pattern.CASE_INSENSITIVE);
    private static final Pattern DOUBLE_SPACE_PATTERN      = Pattern.compile("\\s\\s+");
    private static final Pattern SPACE_BREAK_PATTERN       = Pattern.compile("\\s[\\n|\\s]+");
    private static final long    serialVersionUID          = 7763882160354732177L;

    public static String truncate(final Object object, final int length) {
        return truncate(object, length, 1);
    }

    public static String truncate(final Object object, final int length, final int lines) {
        return truncate(object, length, lines, false);
    }

    public static String truncate(final Object object, final int length, final int lines, final boolean html) {
        if (object == null) {
            return "";
        }
        String string = StringUtils.trimToEmpty(object.toString());

        // HTML code is changed into plain text...
        if (html) {
            // First remove normal line breaks
            string = LINE_BREAK_PATTERN.matcher(string).replaceAll("\n");
            // Change break tags into normal line breaks
            string = BREAK_TAG_PATTERN.matcher(string).replaceAll("\n");
            string = PARAGRAPH_END_TAG_PATTERN.matcher(string).replaceAll("");
            // Then remove all other tags
            string = StringHelper.removeMarkupTagsAndUnescapeEntities(string);
            // Remove double spaces with line breaks by a single break
            string = SPACE_BREAK_PATTERN.matcher(string).replaceAll("\n");
            // Remove double spaces
            string = DOUBLE_SPACE_PATTERN.matcher(string).replaceAll(" ");
        } else {
            string = StringUtils.replace(string, "\r", "");
        }
        final String[] stringLines = StringUtils.split(string, '\n');
        if (lines > 0) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lines && i < stringLines.length; i++) {
                if (i > 0) {
                    sb.append('\n');
                }
                sb.append(stringLines[i]);
            }
            string = sb.toString();
        }
        if (string.length() > length) {
            int pos = length - 3;
            for (int i = 0; i < Math.min(length - 3, 10); i++) {
                final char c = string.charAt(pos - i);
                if (Character.isWhitespace(c) || ".,()!?".indexOf(c) >= 0) {
                    pos -= i;
                    break;
                }
            }
            string = string.substring(0, pos).trim() + "...";
        }
        return string;
    }

    private Object  value;
    private int     length = DEFAULT_LENGTH;
    private int     lines  = DEFAULT_LINES;
    private boolean html;

    @Override
    public int doEndTag() throws JspException {
        final JspWriter out = pageContext.getOut();
        try {
            out.write(truncate(value, length, lines, html));
        } catch (final IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }

    public int getLength() {
        return length;
    }

    public int getLines() {
        return lines;
    }

    public Object getValue() {
        return value;
    }

    public boolean isHtml() {
        return html;
    }

    @Override
    public void release() {
        super.release();
        value = null;
        lines = DEFAULT_LINES;
        length = DEFAULT_LENGTH;
    }

    public void setHtml(final boolean html) {
        this.html = html;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public void setLines(final int maxRows) {
        lines = maxRows;
    }

    public void setValue(final Object value) {
        this.value = value;
    }
}
