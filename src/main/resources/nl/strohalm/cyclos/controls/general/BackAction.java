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
package nl.strohalm.cyclos.controls.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.utils.Navigation;
import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to go back to a page
 * @author luis
 */
public class BackAction extends Action {

    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        int pos;

        final HttpSession session = request.getSession(false);
        Navigation navigation = Navigation.get(session);
        if (navigation == null) {
            return mapping.findForward("login");
        }

        // Get the currently displayed page
        String currentPage = StringUtils.trimToEmpty(StringHelper.removeMarkupTags(request.getParameter("currentPage")));
        pos = currentPage.indexOf("/do/");
        if (pos >= 0) {
            currentPage = currentPage.substring(pos + 3);
        }

        // Remove the leading marker
        pos = currentPage.indexOf("*");
        if (pos >= 0) {
            currentPage = currentPage.substring(0, pos);
        }

        // Go back, until there is no page or the page is different than the currently displayed one
        String path = null;
        do {
            path = navigation.back();
        } while (path != null && path.contains(currentPage));
        if (path == null) {
            return null;
        }

        String queryString = request.getQueryString();
        pos = queryString.indexOf("*?");
        if (pos >= 0) {
            queryString = queryString.substring(pos + 2);
        }

        final ActionForward forward = new ActionForward();
        forward.setPath("/do" + path + "?" + queryString);
        forward.setRedirect(true);
        return forward;
    }
}
