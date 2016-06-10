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

import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.controls.BaseAjaxAction.ContentType;
import nl.strohalm.cyclos.services.ads.AdCategoryService;
import nl.strohalm.cyclos.utils.ResponseHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to export ad categories into a XML file
 * @author luis
 */
public class ExportAdCategoriesAction extends BaseAction {
    private AdCategoryService adCategoryService;
    protected ResponseHelper  responseHelper;

    @Inject
    public void setAdCategoryService(final AdCategoryService adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final String xml = adCategoryService.exportToXml();
        final HttpServletResponse response = context.getResponse();

        // Prepare the response
        response.setContentType(ContentType.XML.getContentType());
        responseHelper.setDownload(response, "ad_categories.xml");

        // Write the properties file to the output stream
        response.getWriter().write(xml);

        // The response is complete
        return null;
    }

}
