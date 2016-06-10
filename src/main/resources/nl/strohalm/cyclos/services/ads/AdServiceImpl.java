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

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.ads.AdDAO;
import nl.strohalm.cyclos.entities.IndexOperation;
import nl.strohalm.cyclos.entities.IndexOperation.EntityType;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.AbstractAdQuery;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.Ad.Status;
import nl.strohalm.cyclos.entities.ads.Ad.TradeType;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryWithCounterQuery;
import nl.strohalm.cyclos.entities.ads.AdCategoryWithCounterVO;
import nl.strohalm.cyclos.entities.ads.AdQuery;
import nl.strohalm.cyclos.entities.ads.FullTextAdQuery;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings.ExternalAdPublication;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.services.customization.AdCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.elements.MemberServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.utils.lucene.IndexOperationListener;
import nl.strohalm.cyclos.utils.lucene.IndexOperationRunner;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.DelegatingValidator;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.LengthValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.webservices.ads.AdResultPage;
import nl.strohalm.cyclos.webservices.ads.FullTextAdSearchParameters;
import nl.strohalm.cyclos.webservices.model.AdVO;
import nl.strohalm.cyclos.webservices.model.GeneralAdVO;
import nl.strohalm.cyclos.webservices.model.MyAdVO;
import nl.strohalm.cyclos.webservices.utils.AdHelper;
import nl.strohalm.cyclos.webservices.utils.QueryHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation class for the Advertisement service interface
 * @author rafael
 * @author luis
 */
public class AdServiceImpl implements AdServiceLocal {

    /**
     * Validates max description size
     * @author Jefferson Magno
     */
    public class MaxAdDescriptionSizeValidation implements PropertyValidation {
        private static final long serialVersionUID = -5580051445666373995L;

        @Override
        public ValidationError validate(final Object object, final Object name, final Object value) {
            final Ad ad = (Ad) object;
            Element element = fetchService.fetch(ad.getOwner(), Element.Relationships.GROUP);
            if (element == null) {
                if (!LoggedUser.hasUser()) {
                    return null;
                }
                element = LoggedUser.element();
            }
            final Group group = element.getGroup();
            if (group instanceof MemberGroup) {
                final MemberGroup memberGroup = fetchService.fetch((MemberGroup) group);
                final int maxAdDescriptionSize = memberGroup.getMemberSettings().getMaxAdDescriptionSize();
                String description = value == null ? null : value.toString();
                if (ad.isHtml()) {
                    description = StringHelper.removeMarkupTagsAndUnescapeEntities(description);
                }
                return new LengthValidation(RangeConstraint.to(maxAdDescriptionSize)).validate(object, name, description);
            }
            return null;
        }
    }

    /**
     * Validates max publication period
     * @author luis
     */
    public class MaxPublicationTimeValidation implements GeneralValidation {
        private static final long serialVersionUID = 1616929350799341483L;

        @Override
        public ValidationError validate(final Object object) {
            final Ad ad = (Ad) object;
            Element element = fetchService.fetch(ad.getOwner(), Element.Relationships.GROUP);
            if (element == null) {
                if (!LoggedUser.hasUser()) {
                    return null;
                }
                element = LoggedUser.element();
            }
            final Group group = fetchService.fetch(element.getGroup());
            Calendar begin = null;
            Calendar end = null;
            try {
                begin = ad.getPublicationPeriod().getBegin();
                end = ad.getPublicationPeriod().getEnd();
            } catch (final NullPointerException e) {
            }
            // Check max publication time
            if (begin != null && end != null && !ad.isPermanent() && (group instanceof MemberGroup)) {
                final TimePeriod maxAdPublicationTime = ((MemberGroup) group).getMemberSettings().getMaxAdPublicationTime();
                final Calendar maxEnd = maxAdPublicationTime.add(begin);
                if (end.after(maxEnd)) {
                    return new ValidationError("ad.error.maxPublicationTimeExceeded");
                }
            }
            return null;
        }
    }

    /**
     * Validates an ad publication period
     * @author luis
     */
    public class PublicationPeriodValidation implements PropertyValidation {
        private static final long serialVersionUID = -6352683891570105522L;

        @Override
        public ValidationError validate(final Object object, final Object name, final Object value) {
            final Ad ad = (Ad) object;
            if (!ad.isPermanent()) {
                final ValidationError required = RequiredValidation.instance().validate(object, name, value);
                if (required != null) {
                    return required;
                }
                if (name.toString().endsWith(".end") && ad.getPublicationPeriod() != null) {
                    final Calendar beginDate = ad.getPublicationPeriod().getBegin();
                    final Calendar endDate = (Calendar) value;
                    // Check if end is after begin
                    if (beginDate != null && endDate != null && !endDate.after(beginDate)) {
                        return new InvalidError();
                    }
                }
            }
            return null;
        }
    }

    private AdDAO                         adDao;
    private AdCustomFieldServiceLocal     adCustomFieldService;
    private FetchServiceLocal             fetchService;
    private AdCategoryServiceLocal        adCategoryService;
    private MemberNotificationHandler     memberNotificationHandler;
    private PermissionServiceLocal        permissionService;
    private SettingsServiceLocal          settingsService;
    private CacheManager                  cacheManager;
    private AdHelper                      adHelper;
    private MemberCustomFieldServiceLocal memberCustomFieldServiceLocal;
    private QueryHelper                   queryHelper;
    private MemberServiceLocal            memberServiceLocal;
    private CustomFieldHelper             customFieldHelper;

    @Override
    public int countExternalAds(final Long adCategoryId, final TradeType type) {
        final AdQuery query = new AdQuery();
        query.setStatus(Ad.Status.ACTIVE);
        query.setTradeType(type);
        query.setCategory(EntityHelper.reference(AdCategory.class, adCategoryId));
        query.setExternalPublication(true);
        query.setPageForCount();
        return PageHelper.getTotalCount(search(query));
    }

    @Override
    public Integer countMembersWithAds(final Collection<MemberGroup> memberGroups, final Calendar timePoint) {
        return adDao.getNumberOfMembersWithAds(timePoint, memberGroups);
    }

    @Override
    public List<Ad> fullTextSearch(final FullTextAdQuery query) throws DaoException {
        if (query.getCategory() != null && !adCategoryService.getActiveCategoriesId().contains(query.getCategory().getId())) {
            return Collections.emptyList();
        }

        if (query.getCategory() == null) {
            query.setCategoriesIds(adCategoryService.getActiveCategoriesId());
        } else {
            query.setCategoriesIds(new LinkedList<Long>());
            query.getCategoriesIds().add(query.getCategory().getId());
        }
        setupGroupFilters(query);
        if (!applyLoggedUserFilters(query)) {
            return Collections.emptyList();
        }
        query.setAnalyzer(settingsService.getLocalSettings().getLanguage().getAnalyzer());
        return adDao.fullTextSearch(query);
    }

    @Override
    public AdResultPage getAdResultPage(final FullTextAdSearchParameters params, final String memberPrincipal) {
        FullTextAdQuery query = adHelper.toFullTextQuery(params);

        // The memberPrincipal doesn't exist on the original FullTextAdSearchParameters, and must be handled here
        if (query.getOwner() == null && StringUtils.isNotEmpty(memberPrincipal)) {
            query.setOwner(memberServiceLocal.loadByIdOrPrincipal(null, null, memberPrincipal));
        }

        // Execute the search
        List<Ad> ads = fullTextSearch(query);
        return queryHelper.toResultPage(AdResultPage.class, ads, new Transformer<Ad, AdVO>() {
            @Override
            public AdVO transform(final Ad ad) {
                return getAdVO(GeneralAdVO.class, ad, params.getShowAdFields(), params.getShowMemberFields(), true);
            }
        });
    }

    @Override
    public <VO extends AdVO> VO getAdVO(final Class<VO> voType, final Ad ad, final boolean useAdFields, final boolean useMemberFields, final boolean onlyForAdSearchMemberFields) {
        final List<AdCustomField> allAdFields = useAdFields ? adCustomFieldService.list() : null;
        List<MemberCustomField> memberFields = null;
        if (useMemberFields) {
            if (onlyForAdSearchMemberFields) {
                memberFields = customFieldHelper.onlyForAdSearch(memberCustomFieldServiceLocal.list());
            } else {
                memberFields = memberCustomFieldServiceLocal.list();
                if (!LoggedUser.isUnrestrictedClient()) {
                    MemberGroup group = LoggedUser.member().getMemberGroup();
                    memberFields = customFieldHelper.onlyVisibleFields(memberFields, group);
                }
            }
        }
        return adHelper.toVO(voType, ad, allAdFields, memberFields);
    }

    @Override
    public List<AdCategoryWithCounterVO> getCategoriesWithCounters(final AdCategoryWithCounterQuery query) {
        return getCountersCache().get(query, new CacheCallback() {
            @Override
            public Object retrieve() {
                List<AdCategory> categories = adCategoryService.listRoot();
                return adDao.getCategoriesWithCounters(categories, query);
            }
        });
    }

    @Override
    public MyAdVO getMyVO(final Ad ad) {
        return adHelper.toMyVO(ad);
    }

    @Override
    public Ad getNextAdToNotify() {
        AdQuery query = new AdQuery();
        query.setUniqueResult();
        query.setStatus(Status.ACTIVE);
        query.setMembersNotified(false);
        query.setSkipOrder(true);
        List<Ad> list = search(query);
        Iterator<Ad> iterator = list.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    @Override
    public Map<Ad.Status, Integer> getNumberOfAds(final Calendar date, final Member member) {
        final Map<Ad.Status, Integer> numberOfAds = new EnumMap<Ad.Status, Integer>(Ad.Status.class);
        final AdQuery query = new AdQuery();
        query.setOwner(member);
        query.setPageForCount();
        // date is for history
        if (date != null) {
            query.setHistoryDate(date);
            query.setIncludeDeleted(true);
        }
        for (final Ad.Status status : Ad.Status.values()) {
            query.setStatus(status);
            final int totalCount = PageHelper.getTotalCount(search(query));
            numberOfAds.put(status, totalCount);
        }
        return numberOfAds;
    }

    @Override
    public int getNumberOfAds(final Collection<MemberGroup> memberGroups, final Status status, final Calendar timePoint) {
        return adDao.getNumberOfAds(timePoint, memberGroups, status);
    }

    @Override
    public void invalidateCountersCache() {
        // Invalidate the counters cache
        if (settingsService.getLocalSettings().isShowCountersInAdCategories()) {
            getCountersCache().clear();
        }
    }

    @Override
    public boolean isEditable(final Ad ad) {
        return permissionService.permission(ad.getOwner())
                .admin(AdminMemberPermission.ADS_MANAGE)
                .broker(BrokerPermission.ADS_MANAGE)
                .member(MemberPermission.ADS_PUBLISH)
                .operator(OperatorPermission.ADS_PUBLISH)
                .hasPermission();
    }

    @Override
    public Ad load(final Long id, final Relationship... fetch) {
        return adDao.load(id, fetch);
    }

    @Override
    public void markMembersNotified(Ad ad) {
        ad = load(ad.getId());
        ad.setMembersNotified(true);
        adDao.update(ad);
    }

    @Override
    public void notifyExpiredAds(final Calendar time) {
        final AdQuery searchParams = new AdQuery();
        searchParams.setResultType(ResultType.ITERATOR);
        searchParams.setEndDate(time);
        CacheCleaner cleaner = new CacheCleaner(fetchService);
        List<Ad> ads = search(searchParams);
        try {
            for (Ad ad : ads) {
                memberNotificationHandler.expiredAdNotification(ad);
                cleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(ads);
        }
    }

    @Override
    public void remove(final Long id) {
        doRemove(id);
    }

    @Override
    public int remove(final Long[] ids) {
        return doRemove(ids);
    }

    @Override
    public Ad save(Ad ad) {
        // If the price is null, set currency to null too
        if (ad.getPrice() == null) {
            ad.setCurrency(null);
        }
        // Validates whether the Ad is valid or not
        validate(ad);

        // Store the custom values out of the ad
        final Collection<AdCustomFieldValue> customValues = ad.getCustomValues();
        ad.setCustomValues(null);

        // Check for limited input
        final Member owner = fetchService.fetch(ad.getOwner(), Element.Relationships.GROUP);
        final MemberGroup group = owner.getMemberGroup();
        final MemberGroupSettings memberSettings = group.getMemberSettings();
        if (!memberSettings.isEnablePermanentAds()) {
            ad.setPermanent(false);
        }
        final ExternalAdPublication externalAdPublication = memberSettings.getExternalAdPublication();
        if (externalAdPublication == ExternalAdPublication.DISABLED) {
            ad.setExternalPublication(false);
        } else if (externalAdPublication == ExternalAdPublication.ENABLED) {
            ad.setExternalPublication(true);
        }

        final boolean isInsert = ad.isTransient();
        if (isInsert) {
            final int maxAds = owner.getMemberGroup().getMemberSettings().getMaxAdsPerMember();

            // Check if the member can publish more advertisements
            final AdQuery adQuery = new AdQuery();
            adQuery.setPageForCount();
            adQuery.setOwner(ad.getOwner());
            final int currentAds = PageHelper.getTotalCount(adDao.search(adQuery));
            if (currentAds >= maxAds) {
                throw new ValidationException("ad.error.maxAds", ad.getOwner().getUsername());
            }

            final Calendar now = Calendar.getInstance();
            if (ad.isPermanent()) {
                final Period p = new Period(now, null);
                ad.setPublicationPeriod(p);
            }
            ad.setCreationDate(now);
            ad = adDao.insert(ad);
        } else {
            final Ad old = load(ad.getId());

            // Keep some properties
            ad.setMembersNotified(old.isMembersNotified());
            ad.setCreationDate(old.getCreationDate());

            // Force the publication period for permanent ads to be the creation date. This avoids creating a
            // scheduled ad in the future, then changing it to permanent, to make it appear first in the results
            if (ad.isPermanent()) {
                final Period p = new Period(ad.getCreationDate(), null);
                ad.setPublicationPeriod(p);
            }
            ad = adDao.update(ad);
        }

        // Save the custom field values
        ad.setCustomValues(customValues);
        adCustomFieldService.saveValues(ad);

        // Update the full text index
        adDao.addToIndex(ad);

        return ad;
    }

    @Override
    public List<Ad> search(final AdQuery query) {
        if (!applyLoggedUserFilters(query)) {
            return Collections.emptyList();
        }
        setupGroupFilters(query);
        return adDao.search(query);
    }

    public void setAdCategoryServiceLocal(final AdCategoryServiceLocal adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    public void setAdCustomFieldServiceLocal(final AdCustomFieldServiceLocal adCustomFieldService) {
        this.adCustomFieldService = adCustomFieldService;
    }

    public void setAdDao(final AdDAO adDao) {
        this.adDao = adDao;
    }

    public void setAdHelper(final AdHelper adHelper) {
        this.adHelper = adHelper;
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setIndexOperationRunner(final IndexOperationRunner indexOperationRunner) {
        // Whenever something in ads change, invalidate the counters cache
        indexOperationRunner.addIndexOperationListener(new IndexOperationListener() {
            @Override
            public void onComplete(final IndexOperation operation) {
                EntityType entityType = operation.getEntityType();
                if (entityType == null || entityType == EntityType.ADVERTISEMENT) {
                    invalidateCountersCache();
                }
            }
        });
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldServiceLocal) {
        this.memberCustomFieldServiceLocal = memberCustomFieldServiceLocal;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setMemberServiceLocal(final MemberServiceLocal memberServiceLocal) {
        this.memberServiceLocal = memberServiceLocal;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setQueryHelper(final QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final Ad ad) throws ValidationException {
        getValidator().validate(ad);
    }

    @Override
    public Collection<MemberGroup> visibleGroupsForAds() {
        Collection<MemberGroup> visibleGroups;
        if ((LoggedUser.isMember() || LoggedUser.isOperator())) {
            // Members and their operators have an specific setting of which groups they see ads
            MemberGroup memberGroup = ((Member) LoggedUser.accountOwner()).getMemberGroup();
            visibleGroups = fetchService.fetch(memberGroup, MemberGroup.Relationships.CAN_VIEW_ADS_OF_GROUPS).getCanViewAdsOfGroups();
        } else {
            visibleGroups = permissionService.getVisibleMemberGroups();
        }
        return visibleGroups;
    }

    private boolean applyLoggedUserFilters(final AbstractAdQuery query) {
        if (LoggedUser.hasUser()) {
            if (LoggedUser.isAdministrator()) {
                // The logged user is an admin
                AdminGroup adminGroup = LoggedUser.group();
                adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.MANAGES_GROUPS);
                final Collection<MemberGroup> managesGroups = adminGroup.getManagesGroups();
                if (CollectionUtils.isEmpty(managesGroups)) {
                    return false;
                }
                if (CollectionUtils.isEmpty(query.getGroups())) {
                    query.setGroups(managesGroups);
                } else {
                    for (final Iterator<? extends Group> iter = query.getGroups().iterator(); iter.hasNext();) {
                        final Group group = iter.next();
                        if (!managesGroups.contains(group)) {
                            iter.remove();
                        }
                    }
                }
            } else {
                // If it´s a member viewing his/her own ads or it´s an operator viewing his/her member's ads
                if (query.isMyAds()) {
                    query.setOwner((Member) LoggedUser.accountOwner());
                    return true;
                }
                // If there's a member logged on, ensure to constrain the groups he can view
                MemberGroup group;
                if (LoggedUser.isOperator()) {
                    final Operator operator = LoggedUser.element();
                    group = operator.getMember().getMemberGroup();
                } else {
                    group = LoggedUser.group();
                }
                group = fetchService.fetch(group, MemberGroup.Relationships.CAN_VIEW_ADS_OF_GROUPS);
                final Collection<MemberGroup> canViewAdsOfGroups = group.getCanViewAdsOfGroups();
                if (CollectionUtils.isEmpty(canViewAdsOfGroups)) {
                    return false;
                }
                if (CollectionUtils.isEmpty(query.getGroups())) {
                    query.setGroups(canViewAdsOfGroups);
                } else {
                    for (final Iterator<? extends Group> iter = query.getGroups().iterator(); iter.hasNext();) {
                        final Group currentGroup = iter.next();
                        if (!canViewAdsOfGroups.contains(currentGroup)) {
                            iter.remove();
                        }
                    }
                }
            }
        }
        return true;
    }

    private int doRemove(final Long... ids) {

        // Physically remove
        final int count = adDao.delete(ids);

        // Update the search index
        adDao.removeFromIndex(Ad.class, ids);

        return count;
    }

    private Cache getCountersCache() {
        return cacheManager.getCache("cyclos.AdCategoriesWithCounters");
    }

    private Validator getValidator() {
        final Validator validator = new Validator("ad");
        validator.general(new MaxPublicationTimeValidation());
        validator.property("title").required().maxLength(100);
        validator.property("description").required().add(new MaxAdDescriptionSizeValidation());
        validator.property("price").positiveNonZero();
        validator.property("category").required();
        validator.property("tradeType").required();
        validator.property("owner").required();
        validator.property("publicationPeriod.begin").add(new PublicationPeriodValidation());
        validator.property("publicationPeriod.end").add(new PublicationPeriodValidation());

        // Custom fields
        validator.chained(new DelegatingValidator(new DelegatingValidator.DelegateSource() {
            @Override
            public Validator getValidator() {
                return adCustomFieldService.getValueValidator();
            }
        }));
        return validator;
    }

    private void setupGroupFilters(final AbstractAdQuery query) {
        Collection<GroupFilter> groupFilters = query.getGroupFilters();
        if (CollectionUtils.isNotEmpty(groupFilters)) {
            // The full text search cannot handle group filters directly. Groups must be assigned
            groupFilters = fetchService.fetch(groupFilters, GroupFilter.Relationships.GROUPS);
            Set<MemberGroup> groups = new HashSet<MemberGroup>();
            Set<MemberGroup> xGroups = new HashSet<MemberGroup>();
            if (query.getGroups() != null) {
                groups.addAll(query.getGroups());
            }
            for (final GroupFilter groupFilter : groupFilters) {
                xGroups.addAll(groupFilter.getGroups());
            }
            if (groups.isEmpty()) {
                groups = xGroups;
            } else {
                groups.retainAll(xGroups);
            }
            query.setGroupFilters(null);
            query.setGroups(groups);
        }
    }
}
