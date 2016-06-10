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
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to view the import summary and to confirm the import
 * 
 * @author luis
 */
public class ImportedAdsSummaryAction extends BaseFormAction {

    private AdImportService adImportService;
    private CurrencyService currencyService;

    @Inject
    public void setAdImportService(final AdImportService adImportService) {
        this.adImportService = adImportService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final AdImport adImport = getImport(context);
        adImportService.processImport(adImport);
        context.sendMessage("adImport.processed");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final AdImport adImport = getImport(context);
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("adImport", adImport);
        request.setAttribute("summary", adImportService.getSummary(adImport));

        // We need to know if there's a single currency. In this case, the currency won't be shown
        final List<Currency> currencies = currencyService.listAll();
        if (currencies.size() == 1) {
            request.setAttribute("singleCurrency", currencies.get(0));
        }
    }

    private AdImport getImport(final ActionContext context) {
        final ImportedAdsSummaryForm form = context.getForm();
        try {
            return adImportService.load(form.getImportId(), AdImport.Relationships.CURRENCY);
        } catch (final Exception e) {
            throw new ValidationException();
        }
    }
}
