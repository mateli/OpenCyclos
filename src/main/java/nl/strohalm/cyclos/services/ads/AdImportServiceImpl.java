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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.ads.imports.AdImportDAO;
import nl.strohalm.cyclos.dao.ads.imports.ImportedAdCategoryDAO;
import nl.strohalm.cyclos.dao.ads.imports.ImportedAdDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.Ad.TradeType;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.imports.AdImport;
import nl.strohalm.cyclos.entities.ads.imports.AdImportResult;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAd;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdCategory;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdCustomFieldValue;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdQuery;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.AdCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;
import nl.strohalm.cyclos.utils.csv.CSVReader;
import nl.strohalm.cyclos.utils.csv.UnknownColumnException;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class AdImportServiceImpl implements AdImportServiceLocal {

    private FetchServiceLocal         fetchService;
    private ElementServiceLocal       elementService;
    private AdServiceLocal            adService;
    private AdCategoryServiceLocal    adCategoryService;
    private SettingsServiceLocal      settingsService;
    private AdCustomFieldServiceLocal adCustomFieldService;
    private AdImportDAO               adImportDao;
    private ImportedAdDAO             importedAdDao;
    private ImportedAdCategoryDAO     importedAdCategoryDao;

    @Override
    public List<ImportedAdCategory> getNewCategories(final AdImport adImport) {
        return importedAdCategoryDao.getLeafCategories(adImport);
    }

    @Override
    public AdImportResult getSummary(final AdImport adIimport) {
        final AdImportResult result = new AdImportResult();
        final ImportedAdQuery query = new ImportedAdQuery();
        query.setAdImport(adIimport);
        query.setPageForCount();

        // Get the total number of ads
        query.setStatus(ImportedAdQuery.Status.ALL);
        result.setTotal(PageHelper.getTotalCount(importedAdDao.search(query)));

        // Get the number of ads with error
        query.setStatus(ImportedAdQuery.Status.ERROR);
        result.setErrors(PageHelper.getTotalCount(importedAdDao.search(query)));

        // Get the number of new categories
        result.setNewCategories(importedAdCategoryDao.getLeafCategories(adIimport).size());

        return result;
    }

    @Override
    public AdImport importAds(AdImport adImport, final InputStream data) {

        // Validate and save the import
        getValidator().validate(adImport);
        final Currency currency = fetchService.fetch(adImport.getCurrency());
        adImport.setCurrency(currency);
        adImport.setBy(LoggedUser.<Administrator> element());
        adImport.setDate(Calendar.getInstance());
        adImport = adImportDao.insert(adImport);

        // Find out the custom fields
        final List<AdCustomField> customFields = adCustomFieldService.list();
        final Map<String, CustomField> customFieldMap = new HashMap<String, CustomField>(customFields.size());
        for (final AdCustomField customField : customFields) {
            customFieldMap.put(customField.getInternalName().toLowerCase(), fetchService.fetch(customField, CustomField.Relationships.POSSIBLE_VALUES));
        }

        // Find the existing advertisement categories
        final Map<String, AdCategory> existingAdCategoryMap = new LinkedHashMap<String, AdCategory>();
        final List<AdCategory> rootCategories = adCategoryService.listRoot();
        for (final AdCategory adCategory : rootCategories) {
            appendCategory(adCategory, existingAdCategoryMap);
        }
        final Map<String, ImportedAdCategory> importedAdCategoryMap = new LinkedHashMap<String, ImportedAdCategory>();

        // Get the settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final char stringQuote = CoercionHelper.coerce(Character.TYPE, localSettings.getCsvStringQuote().getValue());
        final char valueSeparator = CoercionHelper.coerce(Character.TYPE, localSettings.getCsvValueSeparator().getValue());

        // Read the headers
        BufferedReader in = null;
        List<String> headers;
        try {
            in = new BufferedReader(new InputStreamReader(data, localSettings.getCharset()));
            headers = CSVReader.readLine(in, stringQuote, valueSeparator);
        } catch (final Exception e) {
            throw new ValidationException("adImport.invalidFormat");
        }

        // Import each ad
        try {
            final CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
            int lineNumber = 2; // The first line is the header
            List<String> values;
            while ((values = CSVReader.readLine(in, stringQuote, valueSeparator)) != null) {
                if (values.isEmpty()) {
                    continue;
                }
                importAd(adImport, lineNumber, existingAdCategoryMap, importedAdCategoryMap, customFieldMap, localSettings, headers, values);
                lineNumber++;
                cacheCleaner.clearCache();
            }
        } catch (final IOException e) {
            throw new ValidationException("adImport.errorReading");
        } finally {
            IOUtils.closeQuietly(in);
        }
        return adImport;
    }

    @Override
    public AdImport load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        return adImportDao.load(id, fetch);
    }

    @Override
    public void processImport(AdImport adImport) {
        adImport = fetchService.fetch(adImport, AdImport.Relationships.CURRENCY);

        final Map<ImportedAdCategory, AdCategory> importedCategories = new HashMap<ImportedAdCategory, AdCategory>();
        // Iterate through each ad
        final ImportedAdQuery adQuery = new ImportedAdQuery();
        adQuery.fetch(ImportedAd.Relationships.EXISTING_CATEGORY, ImportedAd.Relationships.IMPORTED_CATEGORY, ImportedAd.Relationships.CUSTOM_VALUES);
        adQuery.setAdImport(adImport);
        adQuery.setStatus(ImportedAdQuery.Status.SUCCESS);
        int count = 0;
        final List<ImportedAd> importedAds = importedAdDao.search(adQuery);
        for (final ImportedAd importedAd : importedAds) {
            processAd(adImport, importedAd, importedCategories);
            if (count % 20 == 0) {
                // Every few records, clear the cache to avoid too many objects in memory
                fetchService.clearCache();
            }
            count++;
        }
        // Delete the import after processing it
        adImportDao.delete(adImport.getId());
    }

    @Override
    public void purgeOld(Calendar time) {
        // Only purge after 1 day of idleness
        time = new TimePeriod(1, TimePeriod.Field.DAYS).remove(time);
        for (final AdImport adImport : adImportDao.listBefore(time)) {
            adImportDao.delete(adImport.getId());
        }
    }

    @Override
    public List<ImportedAd> searchImportedAds(final ImportedAdQuery params) {
        return importedAdDao.search(params);
    }

    public void setAdCategoryServiceLocal(final AdCategoryServiceLocal adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    public void setAdCustomFieldServiceLocal(final AdCustomFieldServiceLocal adCustomFieldService) {
        this.adCustomFieldService = adCustomFieldService;
    }

    public void setAdImportDao(final AdImportDAO adImportDao) {
        this.adImportDao = adImportDao;
    }

    public void setAdServiceLocal(final AdServiceLocal adService) {
        this.adService = adService;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setImportedAdCategoryDao(final ImportedAdCategoryDAO importedAdCategoryDao) {
        this.importedAdCategoryDao = importedAdCategoryDao;
    }

    public void setImportedAdDao(final ImportedAdDAO importedAdDao) {
        this.importedAdDao = importedAdDao;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final AdImport AdImport) throws ValidationException {
        getValidator().validate(AdImport);
    }

    /**
     * Recursively add the category and it's children to the map, keyed by the full name
     */
    private void appendCategory(final AdCategory adCategory, final Map<String, AdCategory> existingAdCategoryMap) {
        // Just being cautious to avoid infinite loops in bad behaved databases
        if (existingAdCategoryMap.values().contains(adCategory)) {
            return;
        }
        existingAdCategoryMap.put(adCategory.getFullName(), adCategory);
        for (final AdCategory child : adCategory.getChildren()) {
            appendCategory(child, existingAdCategoryMap);
        }
    }

    private Validator getValidator() {
        final Validator validator = new Validator();
        validator.property("currency").required();
        return validator;
    }

    private Object handleCategory(final ImportedAd ad, final String value, final Map<String, AdCategory> existingAdCategoryMap, final Map<String, ImportedAdCategory> importedAdCategoryMap) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        final String[] parts = StringUtils.split(value, ':');
        Object category = null;
        String fullPath = null;
        // Validate the max level
        if (parts.length > AdCategory.MAX_LEVEL) {
            ad.setStatus(ImportedAd.Status.TOO_MANY_CATEGORY_LEVELS);
            return null;
        }
        for (String part : parts) {
            part = StringUtils.trimToNull(part);
            if (part == null) {
                ad.setStatus(ImportedAd.Status.INVALID_CATEGORY);
                return null;
            }
            // Calculate the canonical full path (uses colon and a space as separators, as returned by AdCategory.getFullPath())
            if (fullPath == null) {
                fullPath = part;
            } else {
                fullPath += ": " + part;
            }
            // Check whether the category exists
            final AdCategory existingCategory = existingAdCategoryMap.get(fullPath);
            if (existingCategory != null) {
                // There is an existing category
                category = existingCategory;
            } else {
                ImportedAdCategory importedCategory = importedAdCategoryMap.get(fullPath);
                if (importedCategory == null) {
                    // No existing category: create a new imported one
                    importedCategory = new ImportedAdCategory();
                    importedCategory.setAdImport(ad.getImport());
                    importedCategory.setName(part);
                    if (category instanceof AdCategory) {
                        importedCategory.setExistingParent((AdCategory) category);
                    } else if (category instanceof ImportedAdCategory) {
                        importedCategory.setImportedParent((ImportedAdCategory) category);
                    }
                    importedCategory = importedAdCategoryDao.insert(importedCategory);
                    importedAdCategoryMap.put(fullPath, importedCategory);
                }
                category = importedCategory;
            }
        }
        return category;
    }

    private void importAd(final AdImport adImport, final int lineNumber, final Map<String, AdCategory> existingAdCategoryMap, final Map<String, ImportedAdCategory> importedAdCategoryMap, final Map<String, CustomField> customFieldMap, final LocalSettings localSettings, final List<String> headers, final List<String> values) {
        final Map<String, String> customFieldValues = new HashMap<String, String>();

        final CalendarConverter dateConverter = localSettings.getRawDateConverter();
        final NumberConverter<BigDecimal> numberConverter = localSettings.getNumberConverter();

        // Insert the ad
        ImportedAd ad = new ImportedAd();
        ad.setLineNumber(lineNumber);
        ad.setImport(adImport);
        ad.setStatus(ImportedAd.Status.SUCCESS);
        ad = importedAdDao.insert(ad);
        ad.setPublicationPeriod(new Period());
        ad.setExternalPublication(true);
        try {
            ad.setCustomValues(new ArrayList<ImportedAdCustomFieldValue>());

            // Process each field. Field names are lowercased to ignore case
            for (int i = 0; i < headers.size() && i < values.size(); i++) {
                final String field = StringUtils.trimToEmpty(headers.get(i)).toLowerCase();
                final String value = StringUtils.trimToNull(values.get(i));
                final boolean valueIsTrue = "true".equalsIgnoreCase(value) || "1".equals(value);
                if ("owner".equals(field)) {
                    if (value != null) {
                        try {
                            final MemberUser user = (MemberUser) elementService.loadUser(value, RelationshipHelper.nested(User.Relationships.ELEMENT, Element.Relationships.GROUP));
                            ad.setOwner(user.getMember());
                        } catch (final Exception e) {
                            ad.setStatus(ImportedAd.Status.INVALID_OWNER);
                            ad.setErrorArgument1(value);
                        }
                    }
                } else if ("title".equals(field)) {
                    ad.setTitle(value);
                } else if ("description".equals(field)) {
                    ad.setDescription(value);
                } else if ("html".equals(field)) {
                    ad.setHtml(valueIsTrue);
                } else if ("publicationstart".equals(field)) {
                    try {
                        ad.getPublicationPeriod().setBegin(dateConverter.valueOf(value));
                    } catch (final Exception e) {
                        ad.setStatus(ImportedAd.Status.INVALID_PUBLICATION_START);
                        ad.setErrorArgument1(value);
                        break;
                    }
                } else if ("publicationend".equals(field)) {
                    try {
                        ad.getPublicationPeriod().setEnd(dateConverter.valueOf(value));
                    } catch (final Exception e) {
                        ad.setStatus(ImportedAd.Status.INVALID_PUBLICATION_END);
                        ad.setErrorArgument1(value);
                        break;
                    }
                } else if ("tradetype".equals(field)) {
                    // Only search is handled now, as it's the exception. Later, if it's null, offer is implied
                    if ("search".equalsIgnoreCase(value)) {
                        ad.setTradeType(TradeType.SEARCH);
                    }
                } else if ("external".equals(field)) {
                    ad.setExternalPublication(valueIsTrue);
                } else if ("price".equals(field)) {
                    try {
                        ad.setPrice(numberConverter.valueOf(value));
                        if (BigDecimal.ZERO.equals(ad.getPrice())) {
                            ad.setPrice(null);
                        }
                    } catch (final Exception e) {
                        ad.setStatus(ImportedAd.Status.INVALID_PRICE);
                        ad.setErrorArgument1(value);
                        break;
                    }
                } else if ("category".equals(field)) {
                    final Object category = handleCategory(ad, value, existingAdCategoryMap, importedAdCategoryMap);
                    if (category instanceof AdCategory) {
                        ad.setExistingCategory((AdCategory) category);
                    } else if (category instanceof ImportedAdCategory) {
                        ad.setImportedCategory((ImportedAdCategory) category);
                    } else if (ad.getStatus() != null) {
                        // The handleCategory may have set the status. Set the argument and leave
                        ad.setErrorArgument1(value);
                        break;
                    }
                } else if (customFieldMap.containsKey(field)) {
                    // Create a custom field value
                    final ImportedAdCustomFieldValue fieldValue = new ImportedAdCustomFieldValue();
                    fieldValue.setField(customFieldMap.get(field));
                    fieldValue.setValue(value);
                    ad.getCustomValues().add(fieldValue);
                    customFieldValues.put(field, value);
                } else {
                    throw new UnknownColumnException(field);
                }
            }

            // When there was an error, stop processing
            if (ad.getStatus() != ImportedAd.Status.SUCCESS) {
                return;
            }

            // Validate some data
            if (ad.getOwner() == null) {
                ad.setStatus(ImportedAd.Status.MISSING_OWNER);
                return;
            }
            if (ad.getExistingCategory() == null && ad.getImportedCategory() == null) {
                ad.setStatus(ImportedAd.Status.MISSING_CATEGORY);
                return;
            }
            if (ad.getTitle() == null) {
                ad.setStatus(ImportedAd.Status.MISSING_TITLE);
                return;
            }
            if (ad.getDescription() == null) {
                ad.setStatus(ImportedAd.Status.MISSING_DESCRIPTION);
                return;
            }
            // Set some default data
            final MemberGroupSettings groupSettings = ad.getOwner().getMemberGroup().getMemberSettings();
            Calendar begin = ad.getPublicationPeriod().getBegin();
            if (begin == null) {
                // When there's no begin, assume today
                begin = Calendar.getInstance();
                ad.getPublicationPeriod().setBegin(begin);
            }
            final Calendar end = ad.getPublicationPeriod().getEnd();
            if (end == null) {
                // Without end, it's a permanent ad
                // Check whether permanent ads are allowed
                if (!groupSettings.isEnablePermanentAds()) {
                    ad.setStatus(ImportedAd.Status.MISSING_PUBLICATION_PERIOD);
                    return;
                }
                ad.setPermanent(true);
            } else {
                // Validate the publication period
                if (begin.after(end)) {
                    ad.setStatus(ImportedAd.Status.PUBLICATION_BEGIN_AFTER_END);
                    return;
                } else {
                    // Check the max publication time
                    final TimePeriod maxAdPublicationTime = groupSettings.getMaxAdPublicationTime();
                    if (!end.before(maxAdPublicationTime.add(begin))) {
                        ad.setStatus(ImportedAd.Status.MAX_PUBLICATION_EXCEEDED);
                        return;
                    }
                }
            }
            if (ad.getTradeType() == null) {
                ad.setTradeType(TradeType.OFFER);
            }
            switch (groupSettings.getExternalAdPublication()) {
                case DISABLED:
                    ad.setExternalPublication(false);
                    break;
                case ENABLED:
                    ad.setExternalPublication(true);
                    break;
            }

            // Save the custom field values
            try {
                adCustomFieldService.saveValues(ad);
            } catch (final Exception e) {
                ad.setStatus(ImportedAd.Status.INVALID_CUSTOM_FIELD);
                if (e instanceof ValidationException) {
                    final ValidationException vex = (ValidationException) e;
                    final Map<String, Collection<ValidationError>> errorsByProperty = vex.getErrorsByProperty();
                    if (MapUtils.isNotEmpty(errorsByProperty)) {
                        final String fieldName = errorsByProperty.keySet().iterator().next();
                        ad.setErrorArgument1(fieldName);
                        final String fieldValue = StringUtils.trimToNull(customFieldValues.get(fieldName));
                        if (fieldValue == null) {
                            // When validation failed and the field is null, it's actually missing
                            ad.setStatus(ImportedAd.Status.MISSING_CUSTOM_FIELD);
                        } else {
                            ad.setErrorArgument2(fieldValue);
                        }
                    }
                }
                return;
            }

        } catch (final UnknownColumnException e) {
            throw e;
        } catch (final Exception e) {
            ad.setStatus(ImportedAd.Status.UNKNOWN_ERROR);
            ad.setErrorArgument1(e.toString());
        } finally {
            importedAdDao.update(ad);
        }

    }

    private void processAd(final AdImport adImport, final ImportedAd importedAd, final Map<ImportedAdCategory, AdCategory> importedCategories) {
        // Resolve the category first
        AdCategory category = importedAd.getExistingCategory();
        final ImportedAdCategory importedCategory = importedAd.getImportedCategory();
        if (category == null && importedCategory != null) {
            category = processCategory(importedCategory, importedCategories);
        }
        Ad ad = new Ad();
        ad.setCategory(category);
        // Without this fetch, Hibernate Search will bail, because the IsHasImages method is invoked
        final Member owner = fetchService.fetch(importedAd.getOwner(), Member.Relationships.IMAGES, Member.Relationships.CUSTOM_VALUES);
        if (owner != null) {
            owner.setCustomValues(fetchService.fetch(owner.getCustomValues(), CustomFieldValue.Relationships.FIELD, CustomFieldValue.Relationships.POSSIBLE_VALUE));
            ad.setOwner(owner);
        }
        ad.setTradeType(importedAd.getTradeType());
        ad.setTitle(importedAd.getTitle());
        ad.setDescription(importedAd.getDescription());
        ad.setHtml(importedAd.isHtml());
        ad.setPermanent(importedAd.isPermanent());
        ad.setPublicationPeriod(importedAd.getPublicationPeriod());
        ad.setExternalPublication(importedAd.isExternalPublication());
        ad.setPrice(importedAd.getPrice());
        if (ad.getPrice() != null) {
            ad.setCurrency(adImport.getCurrency());
        }

        ad.setCustomValues(new ArrayList<AdCustomFieldValue>());

        // Set the custom values
        final Collection<ImportedAdCustomFieldValue> importedCustomValues = importedAd.getCustomValues();
        if (importedCustomValues != null) {
            for (final ImportedAdCustomFieldValue importedValue : importedCustomValues) {
                final CustomField field = importedValue.getField();
                final AdCustomFieldValue fieldValue = new AdCustomFieldValue();
                fieldValue.setAd(ad);
                fieldValue.setField(field);
                if (field.getType() == CustomField.Type.ENUMERATED) {
                    fieldValue.setPossibleValue(importedValue.getPossibleValue());
                } else if (field.getType() == CustomField.Type.MEMBER) {
                    fieldValue.setMemberValue(importedValue.getMemberValue());
                } else {
                    fieldValue.setStringValue(importedValue.getStringValue());
                }
                ad.getCustomValues().add(fieldValue);
            }
        }

        ad = adService.save(ad);
    }

    private AdCategory processCategory(ImportedAdCategory importedCategory, final Map<ImportedAdCategory, AdCategory> importedCategories) {

        // Lookup in the map the already inserted category
        importedCategory = fetchService.fetch(importedCategory, ImportedAdCategory.Relationships.EXISTING_PARENT, ImportedAdCategory.Relationships.IMPORTED_PARENT);
        AdCategory category = importedCategories.get(importedCategory);
        if (category == null) {
            // Resolve the parent first
            AdCategory existingParent = importedCategory.getExistingParent();
            final ImportedAdCategory importedParent = importedCategory.getImportedParent();
            if (existingParent == null && importedParent != null) {
                existingParent = processCategory(importedParent, importedCategories);
            }

            // The first time this category is being used. Insert it
            category = new AdCategory();
            category.setParent(existingParent);
            category.setActive(true);
            category.setName(importedCategory.getName());
            category = fetchService.fetch(adCategoryService.save(category));
            importedCategories.put(importedCategory, category);
        }
        return category;
    }
}
