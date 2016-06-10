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
package nl.strohalm.cyclos.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.SettingsHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Filter used to apply the correct character encoding
 * @author luis
 */
public class RequestProcessingFilter extends OncePerRequestFilter {

    private SettingsService settingsService;
    private MessageHelper   messageHelper;

    @Inject
    public void setMessageHelper(final MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Inject
    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    protected void execute(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // For performance reasons, these settings which will be used on every request are stored in the request context, not relying to the proxy
        // stored on the application context. See SettingsHelper.initialize()
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final AccessSettings accessSettings = settingsService.getAccessSettings();
        request.setAttribute(SettingsHelper.LOCAL_KEY, localSettings);
        request.setAttribute(SettingsHelper.ACCESS_KEY, accessSettings);
        request.setAttribute("datePattern", messageHelper.getDatePatternDescription(localSettings.getDatePattern()));
        request.setAttribute("timePattern", messageHelper.getTimePatternDescription(localSettings.getTimePattern()));

        final String requestEncoding = request.getCharacterEncoding();
        if (StringUtils.isEmpty(requestEncoding)) {
            request.setCharacterEncoding(localSettings.getCharset());
        }
        response.setCharacterEncoding(localSettings.getCharset());

        chain.doFilter(request, response);
    }
}
