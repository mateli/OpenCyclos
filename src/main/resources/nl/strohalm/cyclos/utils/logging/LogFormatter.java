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
package nl.strohalm.cyclos.utils.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;

/**
 * Custom log formatter
 * @author luis
 */
public class LogFormatter extends Formatter implements LocalSettingsChangeListener {

    public static final String   SEPARATOR = "\t";

    private CalendarConverter    dateTimeConverter;
    private SettingsServiceLocal settingsService;

    @Override
    public String format(final LogRecord record) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(record.getMillis());

        final StringBuilder sb = new StringBuilder();
        sb.append(getDateTimeConverter().toString(cal));
        sb.append(SEPARATOR);
        sb.append(record.getMessage());
        final Throwable thrown = record.getThrown();
        if (thrown != null) {
            sb.append('\n');
            final StringWriter w = new StringWriter();
            thrown.printStackTrace(new PrintWriter(w));
            sb.append(w);
        }
        sb.append('\n');
        return sb.toString();
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dateTimeConverter = null;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    private CalendarConverter getDateTimeConverter() {
        if (dateTimeConverter == null) {
            if (settingsService == null) {
                dateTimeConverter = new CalendarConverter("yyyy-MM-dd HH:mm:ss");
            } else {
                dateTimeConverter = settingsService.getLocalSettings().getDateTimeConverter();
            }
        }
        return dateTimeConverter;
    }
}
