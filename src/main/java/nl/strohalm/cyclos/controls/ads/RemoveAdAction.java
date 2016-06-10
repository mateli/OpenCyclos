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
package nl.strohalm.cyclos.controls.ads;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.services.ads.AdService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove an advertisement
 * @author luis
 */
public class RemoveAdAction extends BaseAction {

    AdService adService;

    public AdService getAdService() {
        return adService;
    }

    @Inject
    public void setAdService(final AdService adService) {
        this.adService = adService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final AdForm form = context.getForm();
        if (form.getId() <= 0) {
            throw new ValidationException();
        }
        // Remove the advertisement
        adService.remove(form.getId());
        context.sendMessage("ad.removed");
        if (context.isAdmin()) {
            if (form.getMemberId() > 0) {
                return ActionHelper.redirectWithParam(context.getRequest(), context.findForward("memberAds"), "memberId", form.getMemberId());
            } else {
                return context.findForward("searchAds");
            }
        } else {
            return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "memberId", form.getMemberId());
        }
    }

}
