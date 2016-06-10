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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.utils.RequestHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to build the import / export settings
 * @author Jefferson Magno
 */
public class ManageSettingsAction extends BaseAction {

    public static enum Action {
        IMPORT, EXPORT;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final Setting.Type[] settingTypes = { Setting.Type.ACCESS, Setting.Type.ALERT, Setting.Type.LOCAL, Setting.Type.LOG, Setting.Type.MAIL };
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("settingTypes", settingTypes);
        RequestHelper.storeEnum(request, Action.class, "actions");
        return context.getInputForward();
    }

}
