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
package nl.strohalm.cyclos.webservices.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

/**
 * Registers converters used by REST controllers
 * @author luis
 */
public class RestFormatterRegistrar implements FormatterRegistrar {

    private static final String DATE_FORMAT                 = "yyyy-MM-dd";
    private static final int    DATE_FORMAT_LENGTH          = DATE_FORMAT.length();

    private static final String DATE_TIME_FORMAT            = "yyyy-MM-dd'T'HH:mm";
    private static final int    DATE_TIME_FORMAT_LENGTH     = DATE_TIME_FORMAT.length() - 2;    // Minus the 2 single quotes

    private static final String DATE_TIME_SEC_FORMAT        = "yyyy-MM-dd'T'HH:mm:ss";
    private static final int    DATE_TIME_SEC_FORMAT_LENGTH = DATE_TIME_SEC_FORMAT.length() - 2; // Minus the 2 single quotes

    private static final String FULL_DATE_TIME_FORMAT       = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static void main(final String[] args) throws ParseException {
        String text = "2001-11-27";

        Date date = parse(text);
        System.out.println(date);
    }

    private static Date parse(final String text) {
        try {
            String format;
            int length = text.length();
            if (length == DATE_FORMAT_LENGTH) {
                format = DATE_FORMAT;
            } else if (length == DATE_TIME_FORMAT_LENGTH) {
                format = DATE_TIME_FORMAT;
            } else if (length == DATE_TIME_SEC_FORMAT_LENGTH) {
                format = DATE_TIME_SEC_FORMAT;
            } else {
                format = FULL_DATE_TIME_FORMAT;
            }
            SimpleDateFormat fmt = new SimpleDateFormat(format);
            Date date = fmt.parse(text);
            return date;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Cannot convert '" + text + "' to date", e);
        }
    }

    @Override
    public void registerFormatters(final FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Calendar>() {
            @Override
            public Calendar convert(final String source) {
                if (StringUtils.isEmpty(source)) {
                    return null;
                }
                Date date = parse(source);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar;
            }
        });
        registry.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(final String source) {
                if (StringUtils.isEmpty(source)) {
                    return null;
                }
                return parse(source);
            }
        });
    }

}
