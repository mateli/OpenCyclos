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
package nl.strohalm.cyclos.controls.customization.translationMessages;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.controls.settings.ManageSettingsAction.Action;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.services.customization.MessageImportType;
import nl.strohalm.cyclos.utils.RequestHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to manage messages
 * @author luis
 */
public class ManageTranslationMessagesAction extends BaseAction {

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        RequestHelper.storeEnum(request, MessageImportType.class, "importTypes");
        final Setting.Type[] settingTypes = { Setting.Type.MESSAGE, Setting.Type.MAIL_TRANSLATION };
        request.setAttribute("settingTypes", settingTypes);
        RequestHelper.storeEnum(request, Action.class, "actions");
        return context.getInputForward();
    }

}
