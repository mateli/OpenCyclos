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
import java.math.BigInteger;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

/**
 * Converts a string to number with fixed position decimals. Example: 1234.56 -&gt; 000123456 with precision = 9 and scale = 2
 * @author luis
 */
public class FixedLengthNumberConverter<T extends Number> implements Converter<T> {

    private static final int  DEFAULT_PRECISION = 15;
    private static final long serialVersionUID  = 2163122907372592999L;

    private Class<T>          numberType;
    private int               precision         = DEFAULT_PRECISION;
    private int               scale;

    private BigDecimal        divisor;

    public FixedLengthNumberConverter(final Class<T> numberType, final int scale) {
        this.numberType = numberType;
        this.scale = scale;
        divisor = BigDecimal.TEN.pow(scale);
    }

    public FixedLengthNumberConverter(final Class<T> numberType, final int precision, final int scale) {
        this(numberType, scale);
        this.precision = precision;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public void setPrecision(final int precision) {
        this.precision = precision;
    }

    public void setScale(final int scale) {
        this.scale = scale;
    }

    public String toString(final T object) {
        final BigDecimal number = CoercionHelper.coerce(BigDecimal.class, object);
        if (number == null) {
            return null;
        }
        return StringUtils.leftPad(number.unscaledValue().toString(), precision, '0');
    }

    public T valueOf(final String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        if (!StringUtils.isNumeric(string)) {
            throw new ConversionException("Invalid string. Expecting only numbers, but received " + string);
        }
        final BigDecimal number = new BigDecimal(new BigInteger(string)).divide(divisor, scale, RoundingMode.HALF_UP);
        return CoercionHelper.coerce(numberType, number);
    }
}
