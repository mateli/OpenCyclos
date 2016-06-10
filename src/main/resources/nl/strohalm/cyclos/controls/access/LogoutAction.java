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
package nl.strohalm.cyclos.controls.access;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to logout the logged user
 * @author luis
 */
public class LogoutAction extends Action {

    private SettingsService settingsService;

    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        request.getSession().invalidate();

        // When there is an 'afterLogout' cookie, it means we will redirect back to another site after logout
        final Cookie afterLogout = RequestHelper.getCookie(request, "afterLogout");
        if (afterLogout != null && StringUtils.isNotEmpty(afterLogout.getValue())) {
            String url = afterLogout.getValue();
            // Clear the cookie value
            afterLogout.setValue("");
            response.addCookie(afterLogout);
            try {
                final LocalSettings settings = settingsService.getLocalSettings();
                url = URLDecoder.decode(url, settings.getCharset());
            } catch (final UnsupportedEncodingException e) {
            }
            response.sendRedirect(url);
            return null;
        }

        ActionForward forward = mapping.findForward("success");

        // It may have a queryString to be passed again to the login page (ie: in case of customized login pages)...
        String queryString = RequestHelper.getCookieValue(request, "loginQueryString");
        if (StringUtils.isNotEmpty(queryString)) {
            queryString = StringHelper.decodeUrl(queryString);

            // Remove some variables we want to clear after a logout
            queryString = StringHelper.removeQueryStringVariable(queryString, "returnTo");

            // Remove a trailing &
            if (queryString.endsWith("&")) {
                queryString = queryString.substring(0, queryString.length() - 1);
            }

            // Apply the queryString
            String path = forward.getPath();
            if (StringUtils.isNotEmpty(queryString)) {
                path += "?" + queryString;
            }

            // Then set the forward
            forward = new ActionForward(path, true);
        }
        return forward;
    }

    @Inject
    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }
}
