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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.printsettings.ReceiptPrinterSettings;
import nl.strohalm.cyclos.services.preferences.ReceiptPrinterSettingsService;
import nl.strohalm.cyclos.utils.JSONBuilder;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

import org.apache.commons.lang.StringUtils;

/**
 * Base action used to get details for a receipt print
 * 
 * @author luis
 */
public abstract class BaseReceiptPrintAjaxAction extends BaseAjaxAction {

    private static final Pattern ASCII = Pattern.compile("(\\#\\d{1,3})");

    private static String replaceASCII(final String string) {
        if (StringUtils.isEmpty(string)) {
            return StringUtils.EMPTY;
        }
        final Matcher matcher = ASCII.matcher(string);
        final StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            final char c = (char) Integer.parseInt(matcher.group(1).substring(1));
            matcher.appendReplacement(sb, String.valueOf(c));
        }
        matcher.appendTail(sb);
        return StringUtils.replace(sb.toString(), "\\n", "\n");
    }

    protected ReceiptPrinterSettingsService receiptPrinterSettingsService;

    @Inject
    public final void setReceiptPrinterSettingsService(final ReceiptPrinterSettingsService receiptPrinterSettingsService) {
        this.receiptPrinterSettingsService = receiptPrinterSettingsService;
    }

    @Override
    protected final ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final HttpServletResponse response = context.getResponse();
        final ReceiptPrinterSettings receiptPrinterSettings = resolveReceiptPrinterSettings(context);
        String json = null;
        if (receiptPrinterSettings != null) {
            final String beginCommand = replaceASCII(receiptPrinterSettings.getBeginOfDocCommand());
            final String endCommand = replaceASCII(receiptPrinterSettings.getEndOfDocCommand());
            final String text = replaceASCII(resolveText(context, receiptPrinterSettings));
            final String fullText = StringUtils.replace(beginCommand + text + endCommand, "\r\n", "\n");
            json = new JSONBuilder()
                    .set("printerName", receiptPrinterSettings.getPrinterName())
                    .set("text", fullText)
                    .toString();
        }
        responseHelper.writeJSON(response, json);
    }

    /**
     * Should be implemented in order to resolve the text to print. Do not append the begin and end of document commands - they will be append
     * automatically
     */
    protected abstract String resolveText(ActionContext context, ReceiptPrinterSettings receiptPrinterSettings);

    private ReceiptPrinterSettings resolveReceiptPrinterSettings(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final String cookie = RequestHelper.getCookieValue(request, "receiptPrinterId");
        Long id;
        try {
            id = IdConverter.instance().valueOf(cookie);
        } catch (final Exception e) {
            id = null;
        }
        if (id == null) {
            return null;
        }
        try {
            return receiptPrinterSettingsService.load(id);
        } catch (final EntityNotFoundException e) {
            return null;
        }
    }

}
