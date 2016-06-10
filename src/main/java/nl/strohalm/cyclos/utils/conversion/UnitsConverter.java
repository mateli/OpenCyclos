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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

/**
 * Converter for units
 * @author luis
 */
public class UnitsConverter extends NumberConverter<BigDecimal> {

    private static final long serialVersionUID = -7257124464780039240L;

    private final String      pattern;
    private final char        decimalSeparator;
    private final char        groupingSeparator;

    public UnitsConverter(final String pattern, final DecimalFormat numberFormat) {
        super(BigDecimal.class, numberFormat);
        this.pattern = StringUtils.replace(pattern, "#amount#", "{0}");
        final DecimalFormatSymbols symbols = numberFormat.getDecimalFormatSymbols();
        decimalSeparator = symbols.getDecimalSeparator();
        groupingSeparator = symbols.getGroupingSeparator();
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString(final BigDecimal object) {
        final String number = super.toString(object);
        if (number == null) {
            return null;
        }
        return MessageFormat.format(pattern, new Object[] { number });
    }

    @Override
    public BigDecimal valueOf(final String string) {
        if (string == null) {
            return null;
        }
        int begin = 0;
        int end = string.length() - 1;
        while (!isValidChar(string.charAt(begin)) && begin < string.length()) {
            begin++;
        }
        while (!isValidChar(string.charAt(end)) && end > 0) {
            end--;
        }
        return super.valueOf(string.substring(begin, end + 1));
    }

    private boolean isValidChar(final char c) {
        return Character.isDigit(c) || c == decimalSeparator || c == groupingSeparator;
    }

}
