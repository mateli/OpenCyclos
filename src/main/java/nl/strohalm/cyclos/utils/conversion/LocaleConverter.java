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

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * Converter for Locale instances
 * @author luis
 */
public class LocaleConverter implements Converter<Locale> {

    private static final long            serialVersionUID = -6076434697689630259L;
    private static final LocaleConverter INSTANCE         = new LocaleConverter();

    public static LocaleConverter instance() {
        return INSTANCE;
    }

    public String toString(final Locale locale) {
        if (locale == null) {
            return null;
        }
        final String variant = locale.getVariant();
        if (StringUtils.isEmpty(variant)) {
            return locale.getLanguage() + "_" + locale.getCountry();
        } else {
            return locale.getLanguage() + "_" + locale.getCountry() + "_" + locale.getVariant();
        }
    }

    public Locale valueOf(final String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        final String[] parts = StringUtils.split(string, "_");
        if (parts.length == 1) {
            return new Locale(parts[0]);
        } else if (parts.length == 2) {
            return new Locale(parts[0], parts[1]);
        } else {
            return new Locale(parts[0], parts[1], parts[2]);
        }
    }
}
