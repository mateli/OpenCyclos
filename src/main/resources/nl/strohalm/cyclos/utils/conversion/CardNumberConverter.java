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

import java.math.BigInteger;

import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;

/**
 * A converter for card numbers
 * @author luis
 */
public class CardNumberConverter implements Converter<BigInteger> {

    private static final long   serialVersionUID       = 5645558428738888967L;
    private final String        pattern;
    private static final String ALLOWED_BOUNDARY_CHARS = "#0123456789";

    public CardNumberConverter(final String cardNumberPattern) {
        pattern = cardNumberPattern;
    }

    public String toString(final BigInteger number) {
        if (number == null) {
            return null;
        }

        int numbers = 0;
        for (int i = 0; i < pattern.length(); i++) {
            final char c = pattern.charAt(i);
            if (ALLOWED_BOUNDARY_CHARS.indexOf(c) >= 0) {
                numbers++;
            }
        }

        final String data = StringUtils.leftPad(number.toString(), numbers, '0');
        final StringBuffer formatedCardNumber = new StringBuffer();

        int numberDigit = 0;
        for (int i = 0; i < pattern.length(); i++) {
            final char c = pattern.charAt(i);
            if (ALLOWED_BOUNDARY_CHARS.indexOf(c) >= 0) {
                formatedCardNumber.append(data.charAt(numberDigit));
                numberDigit++;
            } else {
                formatedCardNumber.append(c);
            }
        }

        return formatedCardNumber.toString();
    }

    public BigInteger valueOf(final String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        return new BigInteger(StringHelper.onlyNumbers(string));
    }

}
