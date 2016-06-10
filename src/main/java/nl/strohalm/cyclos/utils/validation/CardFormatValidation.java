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
package nl.strohalm.cyclos.utils.validation;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author rodrigo
 */
public class CardFormatValidation implements PropertyValidation {

    private static final int                  MIN_CARD_DIGITS        = 4;
    private static final String               ALLOWED_BOUNDARY_CHARS = "#0123456789";
    private static final String               ALLOWED_CHARS          = ALLOWED_BOUNDARY_CHARS + " .\\/-_";
    private static final long                 serialVersionUID       = 8420105883132278906L;
    private static final CardFormatValidation INSTANCE               = new CardFormatValidation();

    public static CardFormatValidation instance() {
        return INSTANCE;
    }

    /**
     * @see nl.strohalm.cyclos.utils.validation.PropertyValidation#validate(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public ValidationError validate(final Object object, final Object property, final Object value) {

        if (value == null || "".equals(value)) {
            return null;
        }
        final String format = (String) value;
        boolean valid = StringUtils.containsOnly(format, ALLOWED_CHARS);
        if (valid) {
            valid = ALLOWED_BOUNDARY_CHARS.indexOf(format.charAt(0)) >= 0 && ALLOWED_BOUNDARY_CHARS.indexOf(format.charAt(format.length() - 1)) >= 0;
        }
        if (valid) {
            int cardDigits = 0;
            boolean wasSeparator = false;
            for (int i = 0; i < format.length(); i++) {
                final char c = format.charAt(i);
                if (ALLOWED_BOUNDARY_CHARS.indexOf(c) >= 0) {
                    cardDigits++;
                    wasSeparator = false;
                } else if (wasSeparator) {
                    valid = false;
                    break;
                } else {
                    wasSeparator = true;
                }
            }
            if (valid) {
                valid = cardDigits >= MIN_CARD_DIGITS;
            }
        }
        if (valid) {
            return null;
        } else {
            return new InvalidError();
        }
    }

}
