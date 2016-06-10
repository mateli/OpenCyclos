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
package nl.strohalm.cyclos.services.ads;

import java.io.InputStream;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.imports.AdImport;
import nl.strohalm.cyclos.entities.ads.imports.AdImportResult;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAd;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdCategory;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link AdImportService}
 * 
 * @author Rinke
 */
public class AdImportServiceSecurity extends BaseServiceSecurity implements AdImportService {

    private AdImportServiceLocal adImportService;

    @Override
    public List<ImportedAdCategory> getNewCategories(final AdImport adImport) {
        check();
        return adImportService.getNewCategories(adImport);
    }

    @Override
    public AdImportResult getSummary(final AdImport adIimport) {
        check();
        return adImportService.getSummary(adIimport);
    }

    @Override
    public AdImport importAds(final AdImport adImport, final InputStream data) {
        check();
        return adImportService.importAds(adImport, data);
    }

    @Override
    public AdImport load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        check();
        return adImportService.load(id, fetch);
    }

    @Override
    public void processImport(final AdImport adImport) {
        check();
        adImportService.processImport(adImport);
    }

    @Override
    public List<ImportedAd> searchImportedAds(final ImportedAdQuery params) {
        check();
        return adImportService.searchImportedAds(params);
    }

    public void setAdImportServiceLocal(final AdImportServiceLocal adImportService) {
        this.adImportService = adImportService;
    }

    @Override
    public void validate(final AdImport AdImport) throws ValidationException {
        adImportService.validate(AdImport);
    }

    /**
     * checks on the AdminMemberPermission.ADS_IMPORT
     */
    private void check() {
        permissionService.permission().admin(AdminMemberPermission.ADS_IMPORT).check();
    }

}
