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

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.imports.AdImport;
import nl.strohalm.cyclos.entities.ads.imports.AdImportResult;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAd;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdCategory;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface to handle ad imports
 * 
 * @author luis
 */
public interface AdImportService extends Service {
    /**
     * Returns the new categories that will be created when the given import is processed
     */
    List<ImportedAdCategory> getNewCategories(AdImport adImport);

    /**
     * Returns the summarized results for the given advertisement import
     */
    AdImportResult getSummary(AdImport adIimport);

    /**
     * Import Ads from the given stream
     */
    AdImport importAds(AdImport adImport, InputStream data);

    /**
     * Loads an import with the specified id
     */
    AdImport load(Long id, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Process the import, creating the advertisements and transactions, only for advertisements without errors
     */
    void processImport(AdImport adImport);

    /**
     * Searches for imported advertisements
     */
    List<ImportedAd> searchImportedAds(ImportedAdQuery params);

    /**
     * Validates an advertisements import
     */
    void validate(AdImport AdImport) throws ValidationException;

}
