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
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.ads.imports.AdImport;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.ads.AdImportService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.csv.UnknownColumnException;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to import advertisements
 * 
 * @author luis
 */
public class ImportAdsAction extends BaseFormAction {
    private AdImportService      adImportService;
    private CurrencyService      currencyService;
    private DataBinder<AdImport> dataBinder;

    public DataBinder<AdImport> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<AdImport> binder = BeanBinder.instance(AdImport.class);
            binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setAdImportService(final AdImportService adImportService) {
        this.adImportService = adImportService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ImportAdsForm form = context.getForm();
        final FormFile upload = form.getUpload();
        if (upload == null || upload.getFileSize() == 0) {
            throw new ValidationException("upload", "adImport.file", new RequiredError());
        }
        AdImport adImport = getDataBinder().readFromString(form.getImport());
        try {
            adImport = adImportService.importAds(adImport, upload.getInputStream());
            return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "importId", adImport.getId());
        } catch (final UnknownColumnException e) {
            return context.sendError("general.error.csv.unknownColumn", e.getColumn());
        } finally {
            upload.destroy();
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final List<Currency> currencies = currencyService.listAll();
        if (currencies.size() == 1) {
            request.setAttribute("singleCurrency", currencies.get(0));
        }
        request.setAttribute("currencies", currencies);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ImportAdsForm form = context.getForm();
        final AdImport adImport = getDataBinder().readFromString(form.getImport());
        adImportService.validate(adImport);
    }

}
