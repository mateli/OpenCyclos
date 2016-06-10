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
package nl.strohalm.cyclos.controls.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.settings.MessageSettings.MessageSettingsEnum;

import org.apache.struts.action.ActionForward;

/**
 * Action used to list the message settings
 * @author luis
 */
public class ListMessageSettingsAction extends BaseAction {

    private static final List<String> GENERAL;
    private static final List<String> MEMBER_NOTIFICATIONS;
    private static final List<String> ADMIN_NOTIFICATIONS;
    static {
        final List<String> general = new ArrayList<String>();
        final List<String> member = new ArrayList<String>();
        final List<String> admin = new ArrayList<String>();

        for (final MessageSettingsEnum messageSetting : MessageSettingsEnum.values()) {
            switch (messageSetting.getCategory()) {
                case GENERAL:
                    general.add(messageSetting.settingName());
                    break;
                case MEMBER:
                    member.add(messageSetting.settingName());
                    break;
                case ADMIN:
                    admin.add(messageSetting.settingName());
                    break;
                default:
                    throw new IllegalAccessError("Unknown message setting category: " + messageSetting.getCategory());
            }
        }

        GENERAL = Collections.unmodifiableList(general);
        MEMBER_NOTIFICATIONS = Collections.unmodifiableList(member);
        ADMIN_NOTIFICATIONS = Collections.unmodifiableList(admin);
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final boolean editable = permissionService.hasPermission(AdminSystemPermission.TRANSLATION_MANAGE_NOTIFICATION);
        request.setAttribute("editable", editable);
        request.setAttribute("general", GENERAL);
        request.setAttribute("memberNotifications", MEMBER_NOTIFICATIONS);
        request.setAttribute("adminNotifications", ADMIN_NOTIFICATIONS);
        return context.getInputForward();
    }
}
