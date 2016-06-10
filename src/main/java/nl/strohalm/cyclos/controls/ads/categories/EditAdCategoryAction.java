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
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.services.ads.AdCategoryService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to edit an advertisement category
 * @author luis
 * @author Lucas Geiss
 */
public class EditAdCategoryAction extends BaseFormAction {

    private AdCategoryService      adCategoryService;
    private DataBinder<AdCategory> dataBinder;

    public AdCategoryService getAdCategoryService() {
        return adCategoryService;
    }

    public DataBinder<AdCategory> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<AdCategory> binder = BeanBinder.instance(AdCategory.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("parent", PropertyBinder.instance(AdCategory.class, "parent", ReferenceConverter.instance(AdCategory.class)));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("active", PropertyBinder.instance(Boolean.TYPE, "active"));
            binder.registerBinder("order", PropertyBinder.instance(Integer.class, "order"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setAdCategoryService(final AdCategoryService adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final AdCategoryForm form = context.getForm();
        final AdCategory category = getDataBinder().readFromString(form.getCategory());
        final boolean insert = category.getId() == null;
        long id = 0;
        if (insert) {
            // Actually, there might be several categories, one per line
            final String[] names = category.getName().split("\\n");
            final int count = names.length;
            int nextOrder = -1;
            for (String name : names) {
                name = StringUtils.trimToNull(name);
                if (name == null) {
                    continue;
                }
                AdCategory cat = (AdCategory) category.clone();
                cat.setName(name);
                if (nextOrder >= 0) {
                    cat.setOrder(++nextOrder);
                }
                cat = adCategoryService.save(cat);
                if (nextOrder < 0) {
                    nextOrder = cat.getOrder();
                }
                if (count == 1) {
                    id = cat.getId();
                }
            }
            if (count > 1 && category.getParent() != null) {
                id = category.getParent().getId();
            }
        } else {
            id = adCategoryService.save(category).getId();
        }
        context.sendMessage(insert ? "adCategory.inserted" : "adCategory.modified");
        if (id > 0) {
            return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "id", id);
        } else {
            return context.findForward("list");
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final AdCategoryForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        // Load parent category
        final long parentId = form.getParent();
        AdCategory parent = null;
        if (parentId > 0) {
            parent = adCategoryService.load(parentId, RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT));
        }

        AdCategory category;
        int level = 0;
        final boolean isInsert = form.getId() <= 0;
        if (!isInsert) {
            // Edit an existing category
            category = adCategoryService.load(form.getId(), RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT), AdCategory.Relationships.CHILDREN);
            request.setAttribute("categoryPath", category.getPathFromRoot());
            request.setAttribute("categories", category.getChildren());
            level = category.getLevel();
        } else {
            // Insert a new category
            category = new AdCategory();
            category.setParent(parent);
            category.setActive(true);
            level = (parent == null) ? 1 : parent.getLevel() + 1;
            if (parent != null) {
                request.setAttribute("categoryPath", parent.getPathFromRoot());
            }
        }
        final boolean isMaxLevel = (level >= AdCategory.MAX_LEVEL);
        getDataBinder().writeAsString(form.getCategory(), category);
        request.setAttribute("category", category);
        request.setAttribute("editable", permissionService.hasPermission(AdminSystemPermission.AD_CATEGORIES_MANAGE));
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("isMaxLevel", isMaxLevel);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final AdCategoryForm form = context.getForm();
        final AdCategory category = getDataBinder().readFromString(form.getCategory());
        adCategoryService.validate(category);
    }

}
