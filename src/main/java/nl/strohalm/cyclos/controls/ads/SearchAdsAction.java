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
package nl.strohalm.cyclos.controls.ads;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.ads.AbstractAdQuery;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.Ad.TradeType;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdCategoryWithCounterQuery;
import nl.strohalm.cyclos.entities.ads.AdCategoryWithCounterVO;
import nl.strohalm.cyclos.entities.ads.AdQuery;
import nl.strohalm.cyclos.entities.ads.FullTextAdQuery;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.ads.AdCategoryService;
import nl.strohalm.cyclos.services.ads.AdService;
import nl.strohalm.cyclos.services.customization.AdCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * Action used to search advertisements
 * 
 * @author luis
 * @author Lucas Geiss
 */
public class SearchAdsAction extends BaseQueryAction implements LocalSettingsChangeListener {

    public static DataBinder<FullTextAdQuery> adFullTextQueryDataBinder(final LocalSettings settings) {

        final BeanBinder<AdCustomFieldValue> adCustomValueBinder = BeanBinder.instance(AdCustomFieldValue.class);
        adCustomValueBinder.registerBinder("field", PropertyBinder.instance(AdCustomField.class, "field", ReferenceConverter.instance(AdCustomField.class)));
        adCustomValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value"));

        final BeanBinder<MemberCustomFieldValue> memberCustomValueBinder = BeanBinder.instance(MemberCustomFieldValue.class);
        memberCustomValueBinder.registerBinder("field", PropertyBinder.instance(MemberCustomField.class, "field", ReferenceConverter.instance(MemberCustomField.class)));
        memberCustomValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value"));

        final BeanBinder<FullTextAdQuery> binder = BeanBinder.instance(FullTextAdQuery.class);
        binder.registerBinder("groupFilters", SimpleCollectionBinder.instance(GroupFilter.class, "groupFilters"));
        binder.registerBinder("groups", SimpleCollectionBinder.instance(MemberGroup.class, "groups"));
        binder.registerBinder("tradeType", PropertyBinder.instance(Ad.TradeType.class, "tradeType"));
        binder.registerBinder("status", PropertyBinder.instance(Ad.Status.class, "status"));
        binder.registerBinder("keywords", PropertyBinder.instance(String.class, "keywords"));
        binder.registerBinder("category", PropertyBinder.instance(AdCategory.class, "category", ReferenceConverter.instance(AdCategory.class)));
        binder.registerBinder("since", DataBinderHelper.timePeriodBinder("since"));
        binder.registerBinder("initialPrice", PropertyBinder.instance(BigDecimal.class, "initialPrice", settings.getNumberConverter()));
        binder.registerBinder("finalPrice", PropertyBinder.instance(BigDecimal.class, "finalPrice", settings.getNumberConverter()));
        binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
        binder.registerBinder("withImagesOnly", PropertyBinder.instance(Boolean.TYPE, "withImagesOnly"));
        binder.registerBinder("adValues", BeanCollectionBinder.instance(adCustomValueBinder, "adValues"));
        binder.registerBinder("memberValues", BeanCollectionBinder.instance(memberCustomValueBinder, "memberValues"));
        binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());

        return binder;
    }

    private ReadWriteLock               lock = new ReentrantReadWriteLock(true);

    protected AdService                 adService;
    protected AdCategoryService         adCategoryService;
    protected AdCustomFieldService      adCustomFieldService;
    protected MemberCustomFieldService  memberCustomFieldService;
    protected GroupFilterService        groupFilterService;
    protected CurrencyService           currencyService;
    private DataBinder<FullTextAdQuery> dataBinder;

    private CustomFieldHelper           customFieldHelper;

    public AdCategoryService getAdCategoryService() {
        return adCategoryService;
    }

    public AdCustomFieldService getAdCustomFieldService() {
        return adCustomFieldService;
    }

    public AdService getAdService() {
        return adService;
    }

    public GroupFilterService getGroupFilterService() {
        return groupFilterService;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            super.onLocalSettingsUpdate(event);
            dataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setAdCategoryService(final AdCategoryService adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    @Inject
    public void setAdCustomFieldService(final AdCustomFieldService adCustomFieldService) {
        this.adCustomFieldService = adCustomFieldService;
    }

    @Inject
    public void setAdService(final AdService adService) {
        this.adService = adService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        List<Ad> ads = null;
        if (queryParameters instanceof AdQuery) {
            final AdQuery query = (AdQuery) queryParameters;
            ads = adService.search(query);
        } else {
            final FullTextAdQuery query = (FullTextAdQuery) queryParameters;
            ads = adService.fullTextSearch(query);
        }
        request.setAttribute("ads", ads);

        final SearchAdsForm form = context.getForm();
        form.setAlreadySearched(true);
    }

    @Override
    protected AbstractAdQuery prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final SearchAdsForm form = context.getForm();

        // Store the attributes we need for the search
        RequestHelper.storeEnum(request, Ad.TradeType.class, "tradeTypes");
        request.setAttribute("lastAdsForTradeType", form.isLastAds());

        if (context.isAdmin()) {
            request.setAttribute("editable", true);
            RequestHelper.storeEnum(request, Ad.Status.class, "status");
        }

        // Store the categories
        final TradeType tradeType = CoercionHelper.coerce(TradeType.class, form.getQuery().get("tradeType"));
        int rootCategoryCount;
        if (settingsService.getLocalSettings().isShowCountersInAdCategories()) {
            final AdCategoryWithCounterQuery counterQuery = new AdCategoryWithCounterQuery();
            counterQuery.setTradeType(tradeType);
            final List<AdCategoryWithCounterVO> categories = adService.getCategoriesWithCounters(counterQuery);
            rootCategoryCount = categories.size();
            request.setAttribute("categories", categories);
            request.setAttribute("showCounters", true);
        } else {
            final List<AdCategory> categories = adCategoryService.listRoot();
            rootCategoryCount = categories.size();
            request.setAttribute("categories", categories);
        }
        request.setAttribute("splitCategoriesAt", rootCategoryCount / 2);

        if (form.isLastAds() || form.isCategoryOnly()) {
            final AdQuery adQuery = new AdQuery();
            adQuery.setStatus(Ad.Status.ACTIVE);
            adQuery.setTradeType(tradeType);
            adQuery.fetch(RelationshipHelper.nested(Ad.Relationships.OWNER, Element.Relationships.USER), Ad.Relationships.CURRENCY, Ad.Relationships.IMAGES);
            form.clearForm();
            form.setQuery("tradeType", tradeType.name());

            return adQuery;
        }

        // Retrieve the query
        final AbstractAdQuery query = getDataBinder().readFromString(form.getQuery());

        query.fetch(RelationshipHelper.nested(Ad.Relationships.OWNER, Element.Relationships.USER), Ad.Relationships.CURRENCY, Ad.Relationships.IMAGES, Ad.Relationships.CUSTOM_VALUES, RelationshipHelper.nested(Ad.Relationships.CATEGORY, RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT)));

        if (!context.isAdmin()) {
            // Search only active ads
            query.setStatus(Ad.Status.ACTIVE);
        }

        // Fetch the selected category recursively
        final AdCategory category = query.getCategory() == null ? null : adCategoryService.load(query.getCategory().getId(), RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT));
        if (category != null) {
            request.setAttribute("categoryPath", category.getPathFromRoot());
            request.setAttribute("category", category);
        }

        if (form.isAdvanced()) {
            // Retrieve the custom fields for members and ads
            final List<MemberCustomField> memberFields = customFieldHelper.onlyForAdSearch(memberCustomFieldService.list());
            final List<AdCustomField> allAdFields = adCustomFieldService.list();
            final List<AdCustomField> adFields = customFieldHelper.onlyForAdsSearch(customFieldHelper.adFieldsForSearch(allAdFields));
            request.setAttribute("memberFields", customFieldHelper.buildEntries(memberFields, query.getMemberValues()));
            request.setAttribute("adFields", customFieldHelper.buildEntries(adFields, query.getAdValues()));

            // Search group filters and send to the jsp page
            final GroupFilterQuery groupFilterQuery = new GroupFilterQuery();
            if (context.isAdmin()) {
                // Admins can search by groups
                AdminGroup adminGroup = context.getGroup();
                adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
                final Collection<MemberGroup> memberGroups = adminGroup.getManagesGroups();
                if (CollectionUtils.isNotEmpty(memberGroups)) {
                    request.setAttribute("memberGroups", memberGroups);
                }
                groupFilterQuery.setAdminGroup(adminGroup);
            } else {
                MemberGroup memberGroup = (MemberGroup) context.getMember().getGroup();
                memberGroup = groupService.load(memberGroup.getId(), MemberGroup.Relationships.CAN_VIEW_ADS_OF_GROUPS);
                groupFilterQuery.setViewableBy(memberGroup);
            }
            final Collection<GroupFilter> groupFilters = groupFilterService.search(groupFilterQuery);
            request.setAttribute("groupFilters", groupFilters);

            // Retrieve the periods for "published since"
            request.setAttribute("sincePeriods", Arrays.asList(TimePeriod.Field.DAYS, TimePeriod.Field.WEEKS, TimePeriod.Field.MONTHS));

            // Retrieve the currencies
            List<Currency> currencies;
            if (context.isAdmin()) {
                currencies = currencyService.listAll();
            } else {
                final Member member = (Member) context.getAccountOwner();
                currencies = currencyService.listByMemberGroup(member.getMemberGroup());
            }
            if (currencies.size() > 1) {
                request.setAttribute("currencies", currencies);
            }
        }

        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        final SearchAdsForm form = context.getForm();
        if (form.isCategoryOnly()) {
            return false;
        }
        return form.isAlreadySearched() || form.isLastAds() || super.willExecuteQuery(context, queryParameters);
    }

    private DataBinder<FullTextAdQuery> getDataBinder() {
        try {
            lock.readLock().lock();
            if (dataBinder == null) {
                final LocalSettings settings = settingsService.getLocalSettings();
                dataBinder = adFullTextQueryDataBinder(settings);
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

}
