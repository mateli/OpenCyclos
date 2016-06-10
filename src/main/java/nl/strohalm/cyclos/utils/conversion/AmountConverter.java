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
import java.text.ParseException;

import nl.strohalm.cyclos.utils.Amount;

import org.apache.commons.lang.StringUtils;

/**
 * Converter for amounts
 * @author luis
 */
public class AmountConverter implements Converter<Amount> {

    private static final long    serialVersionUID = -6090298605781552907L;
    private final DecimalFormat  numberFormat;
    private final UnitsConverter unitsConverter;

    public AmountConverter(final DecimalFormat numberFormat) {
        this(numberFormat, "#amount#");
    }

    public AmountConverter(final DecimalFormat numberFormat, final String unitsPattern) {
        this.numberFormat = numberFormat;
        unitsConverter = new UnitsConverter(unitsPattern, numberFormat);
    }

    public String toString(final Amount amount) {
        if (amount == null) {
            return null;
        }
        switch (amount.getType()) {
            case FIXED:
                return unitsConverter.toString(amount.getValue());
            case PERCENTAGE:
                return numberFormat.format(amount.getValue()) + "%";
        }
        return null;
    }

    public Amount valueOf(final String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        try {
            final boolean percentage = string.endsWith("%");
            BigDecimal amount;
            Amount.Type type;
            if (percentage) {
                amount = CoercionHelper.coerce(BigDecimal.class, numberFormat.parse(string.substring(0, string.length() - 1)));
                type = Amount.Type.PERCENTAGE;
            } else {
                amount = unitsConverter.valueOf(string.substring(0, string.length() - 1));
                type = Amount.Type.FIXED;
            }
            return new Amount(amount, type);
        } catch (final ParseException e) {
            throw new ConversionException("Invalid number: " + string);
        }
    }

}
