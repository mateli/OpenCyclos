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
package nl.strohalm.cyclos.controls.posweb;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.utils.RequestHelper;

/**
 * Helper class for posweb controllers
 * 
 * @author luis
 */
public class PosWebHelper {

    public static enum Action {
        BOTH, PAY, RECEIVE;

        public boolean canPay() {
            return this == BOTH || this == PAY;
        }

        public boolean canReceive() {
            return this == BOTH || this == RECEIVE;
        }
    }

    private static final String IS_OPERATOR_COOKIE = "posweb_is_operator";

    private static final String ACTION_COOKIE      = "posweb_action";

    /**
     * Checks which action a member is allowed to perform in posweb
     */
    public static Action getAction(final HttpServletRequest request) {
        Action action;
        // Try on request
        try {
            action = (Action) request.getAttribute(ACTION_COOKIE);
            if (action != null) {
                return action;
            }
        } catch (final Exception e) {
            // ignore
        }
        // try on session
        try {
            action = (Action) request.getSession().getAttribute(ACTION_COOKIE);
            if (action != null) {
                return action;
            }
        } catch (final Exception e) {
            // ignore
        }
        // try on cookie
        try {
            final Cookie cookie = RequestHelper.getCookie(request, ACTION_COOKIE);
            if (cookie != null) {
                return Action.valueOf(cookie.getName());
            }
        } catch (final Exception e) {
            // ignore
        }
        // Fallback to both
        return Action.BOTH;
    }

    /**
     * Checks on a cookie whether the posweb is on operator mode
     */
    public static boolean isOperator(final HttpServletRequest request) {
        Boolean attr;
        // Try on request
        try {
            attr = (Boolean) request.getAttribute(IS_OPERATOR_COOKIE);
            if (attr != null) {
                return attr.booleanValue();
            }
        } catch (final Exception e) {
            // ignore
        }
        // Try on session
        try {
            final HttpSession session = request.getSession();
            if (session != null) {
                attr = (Boolean) session.getAttribute(IS_OPERATOR_COOKIE);
                if (attr != null) {
                    return attr.booleanValue();
                }
            }
        } catch (final Exception e) {
            // ignore
        }
        // Try on cookie
        try {
            final Cookie cookie = RequestHelper.getCookie(request, IS_OPERATOR_COOKIE);
            if (cookie != null) {
                return Boolean.parseBoolean(cookie.getValue());
            }
        } catch (final Exception e) {
            // ignore
        }
        // Fallback to a default result
        return false;
    }

    /**
     * Resolve the login url (not including root/do)
     */
    public static String loginUrl(final HttpServletRequest request) {
        if (isOperator(request)) {
            return "/posweb/operator";
        } else {
            switch (getAction(request)) {
                case PAY:
                    return "/posweb/pay";
                case RECEIVE:
                    return "/posweb/receive";
                default:
                    return "/posweb";
            }
        }
    }

    /**
     * Sets which action a member is allowed to perform in posweb
     */
    public static void setAction(final HttpServletRequest request, final HttpServletResponse response, final Action action) {
        request.setAttribute(ACTION_COOKIE, action);
        request.getSession().setAttribute(ACTION_COOKIE, action);
        response.addCookie(new Cookie(ACTION_COOKIE, action.name()));
    }

    /**
     * Sets a cookie in the response, indicating whether the posweb mode is operator or not (member)
     */
    public static void setOperator(final HttpServletRequest request, final HttpServletResponse response, final boolean isOperator) {
        request.setAttribute(IS_OPERATOR_COOKIE, isOperator);
        request.getSession().setAttribute(IS_OPERATOR_COOKIE, isOperator);
        response.addCookie(new Cookie(IS_OPERATOR_COOKIE, String.valueOf(isOperator)));
    }
}
