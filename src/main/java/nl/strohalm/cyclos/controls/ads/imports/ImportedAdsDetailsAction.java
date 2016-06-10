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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.ads.imports.AdImport;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAd;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdQuery;
import nl.strohalm.cyclos.services.ads.AdImportService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to show details for imported ads
 * @author luis
 */
public class ImportedAdsDetailsAction extends BaseQueryAction {

    private AdImportService             adImportService;
    private DataBinder<ImportedAdQuery> dataBinder;

    public DataBinder<ImportedAdQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ImportedAdQuery> binder = BeanBinder.instance(ImportedAdQuery.class);
            binder.registerBinder("adImport", PropertyBinder.instance(AdImport.class, "adImport"));
            binder.registerBinder("status", PropertyBinder.instance(ImportedAdQuery.Status.class, "status"));
            binder.registerBinder("lineNumber", PropertyBinder.instance(Integer.class, "lineNumber"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            return binder;
        }
        return dataBinder;
    }

    @Inject
    public void setAdImportService(final AdImportService adImportService) {
        this.adImportService = adImportService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final ImportedAdQuery query = (ImportedAdQuery) queryParameters;
        final List<ImportedAd> ads = adImportService.searchImportedAds(query);
        request.setAttribute("importedAds", ads);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final ImportedAdsDetailsForm form = context.getForm();
        final ImportedAdQuery query = getDataBinder().readFromString(form.getQuery());
        final AdImport adImport = adImportService.load(query.getAdImport().getId(), AdImport.Relationships.CURRENCY);
        if (adImport == null || query.getStatus() == null) {
            throw new ValidationException();
        }
        query.setAdImport(adImport);
        request.setAttribute("lowercaseStatus", query.getStatus().name().toLowerCase());
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }
}
