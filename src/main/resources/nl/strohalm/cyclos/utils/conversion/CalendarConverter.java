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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * Converter for Calendar type
 * @author luis
 */
public class CalendarConverter implements Converter<Calendar> {
    private static final long serialVersionUID = 7608682322645824105L;
    private final DateFormat  dateFormat;

    public CalendarConverter(final DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        this.dateFormat.setLenient(false);
    }

    public CalendarConverter(final String mask) {
        this(mask, null);
    }

    public CalendarConverter(final String mask, final TimeZone timeZone) {
        this(new SimpleDateFormat(mask));
        if (timeZone != null) {
            dateFormat.setTimeZone(timeZone);
        }
    }

    public String toString(final Calendar object) {
        if (object == null) {
            return null;
        }
        return dateFormat.format(object.getTime());
    }

    public Calendar valueOf(final String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        try {
            final Date date = dateFormat.parse(string);
            final Calendar cal = Calendar.getInstance(dateFormat.getTimeZone());
            cal.setTime(date);
            return cal;
        } catch (final ParseException e) {
            throw new ConversionException("Invalid date: " + string);
        }
    }
}
