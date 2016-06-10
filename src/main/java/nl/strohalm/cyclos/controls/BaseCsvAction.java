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
package nl.strohalm.cyclos.controls;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.fetch.FetchService;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.csv.CSVWriter;

/**
 * Base class for CSV export actions
 * @author luis
 */
public abstract class BaseCsvAction extends BaseAjaxAction implements LocalSettingsChangeListener {

    private CSVWriter<?> cachedCsvWriter = null;

    public CSVWriter<?> getCsvWriter() {
        return cachedCsvWriter;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        cachedCsvWriter = null;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.CSV;
    }

    protected abstract Iterable<?> executeQuery(ActionContext context);

    protected abstract String fileName(ActionContext context);

    @Override
    protected void handleException(final HttpServletRequest request, final HttpServletResponse response, final Exception e) throws Exception {
        throw e;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected final void renderContent(final ActionContext context) throws Exception {
        final CSVWriter csvWriter = resolveCSVWriter(context);
        final HttpServletResponse response = context.getResponse();
        try {
            responseHelper.setDownload(response, fileName(context));
            final PrintWriter out = response.getWriter();
            csvWriter.writeHeader(out);
            final LocalSettings localSettings = settingsService.getLocalSettings();

            final CacheCleaner cacheCleaner = new CacheCleaner(SpringHelper.bean(context.getServletContext(), FetchService.class));
            int remaining = localSettings.getMaxIteratorResults() == 0 ? -1 : localSettings.getMaxIteratorResults();
            final boolean shouldLimit = shouldLimit();
            for (final Object row : executeQuery(context)) {
                if ((remaining-- == 0) && shouldLimit) {
                    out.print(context.message("reports.print.limitation", localSettings.getMaxIteratorResults()));
                    break;
                }
                csvWriter.writeRow(row, out);
                cacheCleaner.clearCache();
            }
        } catch (final Exception e) {
            // Restore the response headers to open on the browser
            try {
                response.resetBuffer();
                response.setContentType("text/html");
                responseHelper.setInline(response);
                throw e;
            } catch (final IllegalStateException e1) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Must be implemented in order to return the {@link CSVWriter} which will render the content
     */
    protected abstract CSVWriter<?> resolveCSVWriter(ActionContext context);

    /**
     * Returns whether the CSV should have the number of rows limited by {@link LocalSettings#getMaxIteratorResults()}. Limited by default.
     */
    protected boolean shouldLimit() {
        return true;
    }

}
