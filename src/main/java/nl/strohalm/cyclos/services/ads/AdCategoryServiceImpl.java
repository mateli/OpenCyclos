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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.ads.AdCategoryDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryQuery;
import nl.strohalm.cyclos.entities.ads.AdQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.XmlHelper;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.webservices.model.AdCategoryHierarchicalVO;
import nl.strohalm.cyclos.webservices.utils.AdHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Implementation class for the Advertisement service interface
 * @author rafael
 * @author luis
 * @author Lucas Geiss
 */
public class AdCategoryServiceImpl implements AdCategoryServiceLocal {

    private final String         ROOT_ELEMENT     = "ad-categories";
    private final String         CATEGORY_ELEMENT = "ad-category";
    private final String         NAME_ATTRIBUTE   = "name";

    private final String         LEAF_CACHE_KEY   = "_LEAF_";
    private final String         ROOT_CACHE_KEY   = "_ROOT_";

    private AdServiceLocal       adService;
    private SettingsServiceLocal settingsService;
    private AdCategoryDAO        adCategoryDao;
    private FetchServiceLocal    fetchService;
    private CacheManager         cacheManager;
    private AdHelper             adHelper;

    @Override
    public String exportToXml() {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"").append(localSettings.getCharset()).append("\"?>\n");
        xml.append('<').append(ROOT_ELEMENT).append(">\n");
        final List<AdCategory> categories = listRoot();
        for (final AdCategory adCategory : categories) {
            appendXml(xml, adCategory);
        }
        xml.append("</").append(ROOT_ELEMENT).append(">\n");
        return xml.toString();
    }

    @Override
    public List<Long> getActiveCategoriesId() {
        return adCategoryDao.getActiveCategoriesId();
    }

    @Override
    public AdCategoryHierarchicalVO getHierarchicalVO(final AdCategory category) {
        return adHelper.toHierarchicalVO(category);
    }

    @Override
    public void importFromXml(final String xml) {
        final Document doc = XmlHelper.readDocument(xml);
        final Element root = doc.getDocumentElement();
        // Find the order where the new root categories will start
        int rootOrder = 0;
        final List<AdCategory> rootCategories = listRoot();
        for (final AdCategory adCategory : rootCategories) {
            final Integer order = adCategory.getOrder();
            if (order != null && order > rootOrder) {
                rootOrder = order;
            }
        }
        // Insert the root categories
        final List<Element> childen = XmlHelper.getChilden(root, CATEGORY_ELEMENT);
        for (final Element elem : childen) {
            importCategory(null, ++rootOrder, elem);
        }
        invalidateCache();
    }

    @Override
    public List<AdCategory> listLeaf() {
        return getCache().get(LEAF_CACHE_KEY, new CacheCallback() {
            @Override
            public Object retrieve() {
                AdCategoryQuery query = new AdCategoryQuery();
                List<AdCategory> categories = new ArrayList<AdCategory>();
                for (AdCategory category : adCategoryDao.searchLeafAdCategories(query)) {
                    categories.add(fetch(category));
                }
                return categories;
            }
        });
    }

    @Override
    public List<AdCategory> listRoot() {
        return getCache().get(ROOT_CACHE_KEY, new CacheCallback() {
            @Override
            public Object retrieve() {
                AdCategoryQuery query = new AdCategoryQuery();
                List<AdCategory> raw = adCategoryDao.search(query);
                List<AdCategory> list = new ArrayList<AdCategory>(raw.size());
                for (AdCategory category : raw) {
                    category = fetch(category);
                    if (category != null) {
                        list.add(category);
                    }
                }
                return list;
            }
        });
    }

    @Override
    public AdCategory load(final Long id, final Relationship... fetch) {
        return adCategoryDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        final AdQuery adQuery = new AdQuery();
        adQuery.setPageForCount();
        for (final Long id : ids) {
            adQuery.setCategory(EntityHelper.reference(AdCategory.class, id));
            if (PageHelper.getTotalCount(adService.search(adQuery)) > 0) {
                throw new DaoException(new DataIntegrityViolationException("category"));
            }
        }
        invalidateCache();
        return adCategoryDao.delete(ids);
    }

    @Override
    public AdCategory save(final AdCategory category) {
        // Validates whether the ad category is valid or not
        validate(category);
        AdCategory current = null;
        if (category.isTransient()) {
            Integer order = category.getOrder();
            if (order == null || order <= 0) {
                // Get the next order
                AdCategoryQuery query = new AdCategoryQuery();
                query.setParent(category.getParent());
                int maxOrder = 0;
                for (AdCategory cat : adCategoryDao.search(query)) {
                    if (cat.getOrder() > maxOrder) {
                        maxOrder = cat.getOrder();
                    }
                }
                category.setOrder(maxOrder + 1);
            }
            current = adCategoryDao.insert(category);
            if (category.getParent() != null) {
                category.getParent().getChildren().add(current);
            }
        } else {
            current = adCategoryDao.load(category.getId(), AdCategory.Relationships.CHILDREN);

            // Only the name and active status can be updated
            current.setName(category.getName());

            // When the category is deactivated, we should also deactivate all children
            final boolean deactivated = (current.isActive() && !category.isActive());
            if (deactivated) {
                // this method calls the adCategoryDao.update(current) so the above changes (like name) are also stored.
                deactivateRecursively(current);
            } else {
                final boolean changedActive = current.isActive() != category.isActive();
                if (changedActive) {
                    current.setActive(category.isActive());
                }
                current = adCategoryDao.update(current);
            }
        }
        invalidateCache();
        return current;
    }

    @Override
    public List<AdCategory> search(final AdCategoryQuery query) {
        return adCategoryDao.search(query);
    }

    @Override
    public List<AdCategory> searchLeafAdCategories(final AdCategoryQuery query) {
        return adCategoryDao.searchLeafAdCategories(query);
    }

    public void setAdCategoryDao(final AdCategoryDAO adCategoryDao) {
        this.adCategoryDao = adCategoryDao;
    }

    public void setAdHelper(final AdHelper adHelper) {
        this.adHelper = adHelper;
    }

    public void setAdServiceLocal(final AdServiceLocal adService) {
        this.adService = adService;
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    /**
     * Set ad category order
     */
    @Override
    public void setOrder(final Long[] ids) {
        int index = 0;
        for (final Long id : ids) {
            final AdCategory adCategory = load(id);
            adCategory.setOrder(++index);
            adCategoryDao.update(adCategory);
        }
        invalidateCache();
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final AdCategory category) throws ValidationException {
        getValidator().validate(category);
    }

    private void appendXml(final StringBuilder xml, final AdCategory adCategory) {
        final String indent = StringUtils.repeat("    ", adCategory.getLevel());
        xml.append(String.format("%s<%s %s=\"%s\"", indent, CATEGORY_ELEMENT, NAME_ATTRIBUTE, StringEscapeUtils.escapeXml(adCategory.getName())));
        final Collection<AdCategory> children = adCategory.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            xml.append(" />\n");
        } else {
            xml.append(">\n");
            for (final AdCategory child : children) {
                appendXml(xml, child);
            }
            xml.append(indent).append("</").append(CATEGORY_ELEMENT).append(">\n");
        }
    }

    private void deactivateRecursively(final AdCategory adCategory) {
        adCategory.setActive(false);
        adCategoryDao.update(adCategory);
        // Recursively deactivate children
        for (final AdCategory child : adCategory.getChildren()) {
            deactivateRecursively(child);
        }
    }

    private AdCategory fetch(AdCategory category) {
        category = fetchService.fetch(category, RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT), AdCategory.Relationships.CHILDREN);
        if (!category.isActive()) {
            return null;
        }
        List<AdCategory> children = new ArrayList<AdCategory>();
        for (AdCategory child : category.getChildren()) {
            child = fetch(child);
            if (child != null) {
                children.add(child);
            }
        }
        category.setChildren(children);
        return category;
    }

    private Cache getCache() {
        return cacheManager.getCache("cyclos.AdCategories");
    }

    private Validator getValidator() {
        final Validator validator = new Validator("adCategory");
        validator.property("name").required().maxLength(100);
        validator.general(new GeneralValidation() {
            private static final long serialVersionUID = -8975710041548036332L;

            @Override
            public ValidationError validate(final Object object) {
                final AdCategory category = (AdCategory) object;
                if (category.isActive()) {
                    // Ensure that an active category has no inactive parents
                    AdCategory current = fetchService.fetch(category.getParent(), RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT));
                    while (current != null) {
                        if (!current.isActive()) {
                            return new ValidationError("adCategory.error.cantActivateCategoryWithInactiveParent");
                        }
                        current = current.getParent();
                    }
                }
                return null;
            }
        });
        return validator;
    }

    private AdCategory importCategory(final AdCategory parent, final int order, final Element elem) {
        Collection<AdCategory> toCheck = null;
        if (parent == null) {
            toCheck = listRoot();
        } else {
            final AdCategory cat = load(parent.getId(), AdCategory.Relationships.CHILDREN);
            toCheck = cat.getChildren();
        }

        AdCategory matchedChild = null;
        if (toCheck != null) {
            for (final AdCategory cat : toCheck) {
                if (cat.getName().equals(StringUtils.trimToNull(elem.getAttribute(NAME_ATTRIBUTE))) && cat.isActive()) {
                    matchedChild = cat;
                }
            }
        }

        AdCategory category = null;

        if (matchedChild != null) {
            category = matchedChild;
        } else {
            category = new AdCategory();
            category.setName(StringUtils.trimToNull(elem.getAttribute(NAME_ATTRIBUTE)));
            category.setParent(parent);
            category.setOrder(order);
            category.setActive(true);
            category = adCategoryDao.insert(category);
        }

        int childOrder = 0;
        final List<AdCategory> children = new ArrayList<AdCategory>();
        final List<Element> childCategories = XmlHelper.getChilden(elem, CATEGORY_ELEMENT);
        for (final Element child : childCategories) {
            final AdCategory cat = importCategory(category, ++childOrder, child);
            if (cat != null) {
                children.add(cat);
            }
        }
        category.setChildren(children);

        return category;
    }

    private void invalidateCache() {
        getCache().clear();
        adService.invalidateCountersCache();
    }

}
