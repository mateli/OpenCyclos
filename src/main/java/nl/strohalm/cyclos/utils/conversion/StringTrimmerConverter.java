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
package nl.strohalm.cyclos.utils.conversion;

import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;

/**
 * A converter that trims strings and remove all html/xml markup tags
 * @author luis
 */
public class StringTrimmerConverter implements Converter<String> {

    public static String                        DEFAULT_TRIM         = " \t\n\r";
    public static String                        PRESERVE_BLANKS_TRIM = "\t\n\r";
    private static final long                   serialVersionUID     = 2683361873400418066L;

    private static final StringTrimmerConverter INSTANCE             = new StringTrimmerConverter();

    public static StringTrimmerConverter instance() {
        return INSTANCE;
    }

    public static StringTrimmerConverter instance(final String trimBoth) {
        return new StringTrimmerConverter(trimBoth, null, null);
    }

    private final String trimBoth;
    private final String trimLeft;
    private final String trimRight;

    public StringTrimmerConverter() {
        this(null, null, null);
    }

    public StringTrimmerConverter(final String trimBoth) {
        this(trimBoth, null, null);
    }

    public StringTrimmerConverter(final String trimBoth, final String trimLeft, final String trimRight) {
        this.trimBoth = trimBoth == null ? DEFAULT_TRIM : trimBoth;
        this.trimLeft = trimLeft == null ? "" : trimLeft;
        this.trimRight = trimRight == null ? "" : trimRight;
    }

    public String getTrimBoth() {
        return trimBoth;
    }

    public String getTrimLeft() {
        return trimLeft;
    }

    public String getTrimRight() {
        return trimRight;
    }

    public String toString(final String object) {
        return object;
    }

    public String valueOf(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        // This is to avoid html/script injection
        string = StringHelper.removeMarkupTags(string);

        int start = 0;
        int end = string.length() - 1;
        final String leftChars = trimBoth + trimLeft;
        if (leftChars.length() > 0) {
            while (start < string.length() && leftChars.indexOf(string.charAt(start)) >= 0) {
                start++;
            }
        }
        final String rightChars = trimBoth + trimRight;
        if (rightChars.length() > 0) {
            while (end > 0 && rightChars.indexOf(string.charAt(end)) >= 0) {
                end--;
            }
        }
        // this happens because all string's chars are equals to some of the chars to trim (trimRight / trimRightLeft / trimBoth)
        if (start > end) {
            return null;
        } else {
            final String result = string.substring(start, end + 1);
            return result.length() == 0 ? null : result;
        }
    }
}
