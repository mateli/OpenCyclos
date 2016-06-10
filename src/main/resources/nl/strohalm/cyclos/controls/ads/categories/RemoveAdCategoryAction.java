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
package nl.strohalm.cyclos.controls.ads.categories;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.services.ads.AdCategoryService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove an advertisement category
 * @author luis
 */
public class RemoveAdCategoryAction extends BaseAction {

    private AdCategoryService adCategoryService;

    public AdCategoryService getAdCategoryService() {
        return adCategoryService;
    }

    @Inject
    public void setAdCategoryService(final AdCategoryService adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final AdCategoryForm form = context.getForm();
        if (form.getId() <= 0) {
            throw new ValidationException();
        }
        try {
            final AdCategory adCategory = adCategoryService.load(form.getId(), AdCategory.Relationships.PARENT);
            final AdCategory parent = adCategory.getParent();
            adCategoryService.remove(form.getId());
            context.sendMessage("adCategory.removed");
            if (parent != null) {
                return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "id", parent.getId());
            } else {
                return context.findForward("list");
            }
        } catch (final DaoException e) {
            return context.sendError("adCategory.error.removing");
        }
    }
}
