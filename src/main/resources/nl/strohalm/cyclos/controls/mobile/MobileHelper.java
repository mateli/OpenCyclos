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

import nl.strohalm.cyclos.controls.mobile.exceptions.MobileException;
import nl.strohalm.cyclos.entities.access.Channel;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Contains helper methods for mobile access
 * @author luis
 */
public final class MobileHelper {

    /**
     * Clears the exception on the session
     */
    public static void clearException(final HttpServletRequest request) {
        request.getSession().removeAttribute("mobileException");
    }

    /**
     * Return the error forward
     */
    public static ActionForward getErrorForward(final ActionMapping actionMapping, final HttpServletRequest request) {
        final String forward = isWap1Request(request) ? "wapError" : "mobileError";
        return actionMapping.findForward(forward);
    }

    /**
     * Return the error forward
     */
    public static ActionForward getHomeForward(final ActionMapping actionMapping, final HttpServletRequest request) {
        final String forward = isWap1Request(request) ? "wapHome" : "mobileHome";
        return actionMapping.findForward(forward);
    }

    /**
     * Returns if the given request is from wap1
     */
    public static boolean isWap1Request(final HttpServletRequest request) {
        return request.getRequestURI().contains("/wap");
    }

    /**
     * Returns if the given request is from wap2
     */
    public static boolean isWap2Request(final HttpServletRequest request) {
        return request.getRequestURI().contains("/mobile");
    }

    /**
     * Returns the internal name of the channel associated to the request
     */
    public static String mobileChannel(final HttpServletRequest request) {
        if (isWap1Request(request)) {
            return Channel.WAP1;
        } else {
            return Channel.WAP2;
        }
    }

    /**
     * Send the exception to the error page
     */
    public static ActionForward sendException(final ActionMapping actionMapping, final HttpServletRequest request, final MobileException e) {
        request.getSession().setAttribute("mobileException", e);
        return getErrorForward(actionMapping, request);
    }

}
