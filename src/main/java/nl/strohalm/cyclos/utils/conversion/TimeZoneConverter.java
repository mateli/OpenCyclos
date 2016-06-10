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

import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * Converter for TimeZones. Uses the time zone ID
 * 
 * @author luis
 */
public class TimeZoneConverter implements Converter<TimeZone> {

    private static final long              serialVersionUID = -5712130039783092190L;
    private static final TimeZoneConverter INSTANCE         = new TimeZoneConverter();

    public static TimeZoneConverter instance() {
        return INSTANCE;
    }

    private TimeZoneConverter() {
    }

    public String toString(final TimeZone object) {
        if (object == null) {
            return null;
        }
        return object.getID();
    }

    public TimeZone valueOf(final String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        return TimeZone.getTimeZone(string);
    }
}
