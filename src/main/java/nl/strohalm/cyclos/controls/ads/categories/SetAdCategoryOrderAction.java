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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryQuery;
import nl.strohalm.cyclos.services.ads.AdCategoryService;
import nl.strohalm.cyclos.utils.ActionHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to set the ad category order
 * @author Lucas Geiss
 */
public class SetAdCategoryOrderAction extends BaseFormAction {

    private AdCategoryService adCategoryService;

    public AdCategoryService getAdCategoryService() {
        return adCategoryService;
    }

    @Inject
    public void setAdCategoryService(final AdCategoryService adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {

        final SetAdCategoryOrderForm form = context.getForm();

        adCategoryService.setOrder(form.getAdCategoryIds());
        context.sendMessage("adCategory.orderModified");

        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "currentCategory", form.getCurrentCategory());

    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SetAdCategoryOrderForm form = context.getForm();
        List<? extends AdCategory> categories = null;
        final AdCategoryQuery query = new AdCategoryQuery();
        final long currentId = form.getCurrentCategory();
        AdCategory adCategory = null;
        if (currentId > 0L) {
            adCategory = adCategoryService.load(currentId);
            query.setParent(adCategory);
        }

        query.setReturnDisabled(true);
        query.setOrderAlphabetically(form.isOrderAlpha());
        categories = adCategoryService.search(query);

        request.setAttribute("adCategories", categories);
        request.setAttribute("currentCategory", adCategory);
    }
}
