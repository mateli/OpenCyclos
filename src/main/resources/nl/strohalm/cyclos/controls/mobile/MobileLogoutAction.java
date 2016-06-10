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
package nl.strohalm.cyclos.controls.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Logout the user, redirecting to login
 * @author luis
 */
public class MobileLogoutAction extends Action {

    @Override
    public ActionForward execute(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession(false);
        String queryString = null;

        if (session != null) {
            queryString = StringUtils.trimToNull((String) session.getAttribute("loginQueryString"));
            session.invalidate();
        }
        ActionForward actionForward = actionMapping.findForward("success");

        if (queryString != null) {
            actionForward = new ActionForward(actionForward);
            actionForward.setPath(actionForward.getPath() + "?" + queryString);
        }

        return actionForward;
    }

}
