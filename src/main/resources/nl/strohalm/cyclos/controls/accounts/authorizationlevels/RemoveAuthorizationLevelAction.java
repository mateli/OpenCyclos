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
package nl.strohalm.cyclos.controls.accounts.authorizationlevels;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.services.transfertypes.AuthorizationLevelService;
import nl.strohalm.cyclos.utils.ActionHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove an authorization level
 * @author Jefferson Magno
 */
public class RemoveAuthorizationLevelAction extends BaseAction {

    private AuthorizationLevelService authorizationLevelService;

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {

        final RemoveAuthorizationLevelForm form = context.getForm();
        try {
            authorizationLevelService.remove(form.getAuthorizationLevelId());
            context.sendMessage("authorizationLevel.removed");
        } catch (final Exception e) {
            context.sendMessage("authorizationLevel.error.removing");
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountTypeId", form.getAccountTypeId());
        params.put("transferTypeId", form.getTransferTypeId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Inject
    public void setAuthorizationLevelService(final AuthorizationLevelService authorizationLevelService) {
        this.authorizationLevelService = authorizationLevelService;
    }

}
