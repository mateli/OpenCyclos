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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to display the manual
 * @author luis
 */
public class ManualAction extends BasePublicAction {

    public static final Collection<String> ADMIN_HELPS;
    public static final Collection<String> MEMBER_HELPS;
    public static final Collection<String> BROKER_HELPS;
    static {
        // TODO Put all help files
        final List<String> adminHelps = new ArrayList<String>();
        adminHelps.add("help_howto");
        adminHelps.add("access_devices");
        adminHelps.add("account_management");
        adminHelps.add("ads_interest");
        adminHelps.add("advertisements");
        adminHelps.add("alerts_logs");
        adminHelps.add("bookkeeping");
        adminHelps.add("brokering");
        adminHelps.add("content_management");
        adminHelps.add("custom_fields");
        adminHelps.add("documents");
        adminHelps.add("groups");
        adminHelps.add("guarantees");
        adminHelps.add("home");
        adminHelps.add("invoices");
        adminHelps.add("loan_groups");
        adminHelps.add("loans");
        adminHelps.add("member_records");
        adminHelps.add("messages");
        adminHelps.add("preferences");
        adminHelps.add("operators");
        adminHelps.add("passwords");
        adminHelps.add("payments");
        adminHelps.add("profiles");
        adminHelps.add("references");
        adminHelps.add("reports");
        adminHelps.add("settings");
        adminHelps.add("statistics");
        adminHelps.add("transaction_feedback");
        adminHelps.add("translation");
        adminHelps.add("user_management");
        ADMIN_HELPS = Collections.unmodifiableList(adminHelps);

        final List<String> memberHelps = new ArrayList<String>();

        memberHelps.add("help_howto");
        memberHelps.add("access_devices");
        memberHelps.add("ads_interest");
        memberHelps.add("advertisements");
        memberHelps.add("brokering");
        memberHelps.add("documents");
        memberHelps.add("home");
        memberHelps.add("invoices");
        memberHelps.add("loans");
        // memberHelps.add("member_records");
        memberHelps.add("messages");
        memberHelps.add("preferences");
        memberHelps.add("operators");
        memberHelps.add("passwords");
        memberHelps.add("payments");
        memberHelps.add("profiles");
        memberHelps.add("guarantees");
        memberHelps.add("references");
        memberHelps.add("reports");
        memberHelps.add("transaction_feedback");
        memberHelps.add("user_management");

        MEMBER_HELPS = Collections.unmodifiableList(memberHelps);

        final List<String> brokerHelps = new ArrayList<String>();
        brokerHelps.addAll(memberHelps);
        brokerHelps.add("member_records");

        BROKER_HELPS = Collections.unmodifiableList(brokerHelps);
    }

    @Override
    protected ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession(false);
        boolean isAdmin, isBroker;
        try {
            isAdmin = (Boolean) session.getAttribute("isAdmin");
        } catch (final Exception e) {
            isAdmin = false;
        }
        final boolean printVersion = mapping.getPath().startsWith("/print");
        if (!printVersion) {
            final User loggedUser = LoginHelper.getLoggedUser(request);
            if (loggedUser == null) {
                return mapping.findForward("login");
            }
        }
        final List<String> helps;
        if (!isAdmin) {
            isBroker = (Boolean) session.getAttribute("isBroker");
            helps = new ArrayList<String>(isBroker ? BROKER_HELPS : MEMBER_HELPS);
        } else {
            helps = new ArrayList<String>(ADMIN_HELPS);
        }
        // Sort the helps according to the translation messages
        Collections.sort(helps, new Comparator<String>() {
            @Override
            public int compare(final String help1, final String help2) {
                if ("help_howto".equals(help1)) {
                    return -1;
                }
                final String message1 = messageHelper.message("help.title." + help1);
                final String message2 = messageHelper.message("help.title." + help2);
                return message1.compareTo(message2);
            }
        });
        request.setAttribute("helps", helps);
        final String page = StringUtils.trimToNull(StringHelper.removeMarkupTags(request.getParameter("page")));
        request.setAttribute("page", page);
        request.setAttribute("printVersion", printVersion);
        request.setAttribute("pagePrefix", request.getContextPath() + session.getAttribute("pathPrefix") + "/manual?page=");

        // When there's no specific page (section), get the global manual
        if (page == null) {
            return mapping.getInputForward();
        } else {
            return mapping.findForward("section");
        }
    }

}
