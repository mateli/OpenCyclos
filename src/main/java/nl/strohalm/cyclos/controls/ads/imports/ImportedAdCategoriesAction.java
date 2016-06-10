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
package nl.strohalm.cyclos.controls.ads.imports;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.ads.imports.AdImport;
import nl.strohalm.cyclos.services.ads.AdImportService;
import nl.strohalm.cyclos.utils.EntityHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to show the new categories
 * @author luis
 */
public class ImportedAdCategoriesAction extends BaseAction {

    private AdImportService adImportService;

    @Inject
    public void setAdImportService(final AdImportService adImportService) {
        this.adImportService = adImportService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ImportedAdCategoriesForm form = context.getForm();
        final AdImport adImport = EntityHelper.reference(AdImport.class, form.getImportId());
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("categories", adImportService.getNewCategories(adImport));
        return context.getInputForward();
    }

}
