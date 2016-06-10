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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryQuery;
import nl.strohalm.cyclos.services.ads.AdCategoryService;

import org.apache.struts.action.ActionForward;

/**
 * Action used to list all categories
 * @author luis
 * @author jefferson
 * @author Lucas Geiss
 */
public class ListAdCategoriesAction extends BaseAction {

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
        final HttpServletRequest request = context.getRequest();
        final AdCategoryQuery query = new AdCategoryQuery();
        query.setReturnDisabled(true);
        query.setParent(null);
        query.fetch(AdCategory.Relationships.CHILDREN);
        request.setAttribute("categories", adCategoryService.search(query));
        request.setAttribute("editable", permissionService.hasPermission(AdminSystemPermission.AD_CATEGORIES_MANAGE));
        return context.getInputForward();
    }

}
