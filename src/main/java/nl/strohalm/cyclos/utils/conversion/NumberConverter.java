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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.commons.lang.StringUtils;

/**
 * Converter for formatted numbers
 * @author luis
 */
public class NumberConverter<T extends Number> implements Converter<T>, Cloneable {

    private static final long   serialVersionUID = -473661346133459797L;
    private final DecimalFormat numberFormat;
    private final Class<T>      numberType;
    private boolean             negativeToAbsoluteValue;
    private volatile BigDecimal delta;

    public NumberConverter(final Class<T> numberType, final DecimalFormat numberFormat) {
        this.numberType = numberType;
        this.numberFormat = numberFormat;
    }

    public DecimalFormat getNumberFormat() {
        return numberFormat;
    }

    public Class<T> getNumberType() {
        return numberType;
    }

    @SuppressWarnings("unchecked")
    public NumberConverter<T> negativeToAbsolute() {
        try {
            final NumberConverter<T> clone = (NumberConverter<T>) clone();
            clone.negativeToAbsoluteValue = true;
            return clone;
        } catch (final CloneNotSupportedException e) {
            return null;
        }
    }

    public String toString(final T number) {
        if (number == null) {
            return null;
        }
        BigDecimal bigDecimal = CoercionHelper.coerce(BigDecimal.class, number);

        // Convert to negative if on negativeToAbsoluteValue mode and number is not zero
        if (negativeToAbsoluteValue) {
            bigDecimal = bigDecimal.abs();
        }

        // For very small negative numbers, like 0.000001, avoid formatting as -0,00
        final BigDecimal delta = getDelta();
        if (bigDecimal.compareTo(BigDecimal.ZERO) < 0 && bigDecimal.compareTo(delta) > 0) {
            bigDecimal = BigDecimal.ZERO;
        }

        return numberFormat.format(bigDecimal);
    }

    public T valueOf(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        final DecimalFormatSymbols symbols = numberFormat.getDecimalFormatSymbols();
        final char minusSign = symbols.getMinusSign();
        final char decimalSeparator = symbols.getDecimalSeparator();
        final char groupingSeparator = symbols.getGroupingSeparator();
        boolean negativeNumber = false;
        if (string.indexOf(minusSign) > -1) {
            string = StringUtils.replace(string, String.valueOf(minusSign), "");
            negativeNumber = true;
        }
        final String[] parts = StringUtils.split(string, String.valueOf(decimalSeparator));
        final String integerPart = StringUtils.replace(parts[0], String.valueOf(groupingSeparator), "");
        final boolean hasDecimalPart = parts.length > 1;
        final String decimalPart = hasDecimalPart ? parts[1] : "";
        String bigDecimalString = integerPart;
        if (hasDecimalPart) {
            bigDecimalString = bigDecimalString + "." + decimalPart;
        }
        if (negativeNumber) {
            bigDecimalString = "-" + bigDecimalString;
        }
        final BigDecimal bigDecimal = new BigDecimal(bigDecimalString);
        T value = CoercionHelper.coerce(numberType, bigDecimal);
        if (negativeToAbsoluteValue && value != null && value.floatValue() < 0) {
            value = CoercionHelper.coerce(numberType, -value.floatValue());
        }
        return value;
    }

    private BigDecimal getDelta() {
        if (delta == null) {
            final int precision = numberFormat.getMaximumFractionDigits();
            delta = BigDecimal.ONE.divide(BigDecimal.TEN.pow(precision), precision, RoundingMode.HALF_UP).negate();
        }
        return delta;
    }

}
