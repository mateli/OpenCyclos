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

import org.apache.commons.lang.StringUtils;

/**
 * A converter that converts a resource bundle key (like monkey.fool.cow.bear) to a helpfile name (like monkeyFoolCowBear). So, the dots are stripped,
 * and all tokens are capitalized, except the first. Used in statistics.
 * @author rinke
 * 
 */
public class KeyToHelpNameConverter extends FormatOnlyConverter<String> {

    private static final long serialVersionUID = 4796055097831961200L;

    /**
     * default constructor
     * 
     */
    public KeyToHelpNameConverter() {
    }

    @Override
    public String toString(final String object) {
        return convert(object);
    }

    private String convert(final String string) {
        final String[] array = StringUtils.split(string, '.');
        final StringBuffer converted = new StringBuffer(string.length());
        for (int i = 0; i < array.length; i++) {
            final String token = (i == 0) ? array[i] : StringUtils.capitalize(array[i]);
            converted.append(token);
        }
        return converted.toString();
    }

}
