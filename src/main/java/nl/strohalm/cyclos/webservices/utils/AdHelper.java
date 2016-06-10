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
package nl.strohalm.cyclos.webservices.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.ads.AbstractAdQuery;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.Ad.Status;
import nl.strohalm.cyclos.entities.ads.Ad.TradeType;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.ads.AdQuery;
import nl.strohalm.cyclos.entities.ads.FullTextAdQuery;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField.Visibility;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.ads.AdServiceLocal;
import nl.strohalm.cyclos.services.customization.AdCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.webservices.ads.AbstractAdSearchParameters;
import nl.strohalm.cyclos.webservices.ads.AbstractAdSearchParameters.AdVOStatus;
import nl.strohalm.cyclos.webservices.ads.AbstractAdSearchParameters.AdVOTradeType;
import nl.strohalm.cyclos.webservices.ads.AdResultPage;
import nl.strohalm.cyclos.webservices.ads.AdSearchParameters;
import nl.strohalm.cyclos.webservices.ads.FullTextAdSearchParameters;
import nl.strohalm.cyclos.webservices.model.AdCategoryHierarchicalVO;
import nl.strohalm.cyclos.webservices.model.AdCategoryVO;
import nl.strohalm.cyclos.webservices.model.AdVO;
import nl.strohalm.cyclos.webservices.model.DetailedAdCategoryVO;
import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.MyAdVO;
import nl.strohalm.cyclos.webservices.model.TimePeriodVO;

/**
 * Utility class for ads<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * @author luis
 */
public class AdHelper {
    private CurrencyHelper                currencyHelper;
    private FieldHelper                   fieldHelper;
    private ImageHelper                   imageHelper;
    private MemberHelper                  memberHelper;
    private SettingsServiceLocal          settingsService;
    private QueryHelper                   queryHelper;
    private AdCustomFieldServiceLocal     adCustomFieldService;
    private MemberCustomFieldServiceLocal memberCustomFieldService;
    private AdServiceLocal                adServiceLocal;
    private CustomFieldHelper             customFieldHelper;

    public void setAdCustomFieldServiceLocal(final AdCustomFieldServiceLocal adCustomFieldService) {
        this.adCustomFieldService = adCustomFieldService;
    }

    public void setAdServiceLocal(final AdServiceLocal adService) {
        adServiceLocal = adService;
    }

    public void setCurrencyHelper(final CurrencyHelper currencyHelper) {
        this.currencyHelper = currencyHelper;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setFieldHelper(final FieldHelper fieldHelper) {
        this.fieldHelper = fieldHelper;
    }

    public void setImageHelper(final ImageHelper imageHelper) {
        this.imageHelper = imageHelper;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setQueryHelper(final QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public DetailedAdCategoryVO toDetailedVO(final AdCategory category) {
        if (category == null) {
            return null;
        }
        final DetailedAdCategoryVO vo = new DetailedAdCategoryVO();
        vo.setId(category.getId());
        vo.setName(category.getName());
        vo.setFullName(category.getFullName());
        vo.setLevel(category.getLevel());

        // count only ads with external publication = true
        vo.setCountOffer(adServiceLocal.countExternalAds(category.getId(), TradeType.OFFER));
        vo.setCountSearch(adServiceLocal.countExternalAds(category.getId(), TradeType.SEARCH));

        final Collection<AdCategory> childrenList = category.getChildren();
        final List<DetailedAdCategoryVO> children = new ArrayList<DetailedAdCategoryVO>();

        for (final AdCategory child : childrenList) {
            children.add(toDetailedVO(child));
        }
        vo.setChildren(children);
        return vo;
    }

    public FullTextAdQuery toFullTextQuery(final FullTextAdSearchParameters params) {
        if (params == null) {
            return null;
        }
        final FullTextAdQuery query = new FullTextAdQuery();
        fill(params, query);
        return query;
    }

    /**
     * Converts the given ad into VO, using all custom fields and member fields
     */
    public AdVO toFullVO(final Ad ad) {
        List<AdCustomField> adFields = adCustomFieldService.list();
        List<MemberCustomField> memberFields = memberCustomFieldService.list();
        if (!LoggedUser.isUnrestrictedClient()) {
            MemberGroup group = LoggedUser.member().getMemberGroup();
            memberFields = customFieldHelper.onlyVisibleFields(memberFields, group);
        }
        return toVO(ad, adFields, memberFields);
    }

    /**
     * Converts a category into a hierarchical representation
     * @param category
     * @return
     */
    public AdCategoryHierarchicalVO toHierarchicalVO(final AdCategory category) {
        if (category == null || !category.isActive()) {
            return null;
        }
        AdCategoryHierarchicalVO vo = new AdCategoryHierarchicalVO();
        vo.setId(category.getId());
        vo.setName(category.getName());
        List<AdCategoryHierarchicalVO> children = new ArrayList<AdCategoryHierarchicalVO>();
        for (AdCategory child : category.getChildren()) {
            AdCategoryHierarchicalVO childVO = toHierarchicalVO(child);
            if (childVO != null) {
                children.add(childVO);
            }
        }
        vo.setChildren(children);
        return vo;
    }

    /**
     * Converts the given ad into a {@link MyAdVO} with all ad fields and no member fields
     */
    public MyAdVO toMyVO(final Ad ad) {
        List<AdCustomField> adFields = adCustomFieldService.list();
        MyAdVO vo = toVO(MyAdVO.class, ad, adFields, null);
        vo.setStatus(AdVOStatus.valueOf(ad.getStatus().name()));
        return vo;
    }

    public AdQuery toQuery(final AdSearchParameters params) {
        if (params == null) {
            return null;
        }
        final AdQuery query = new AdQuery();
        fill(params, query);
        query.setRandomOrder(params.getRandomOrder());
        return query;
    }

    /**
     * Converts a list or page of ads into an AdResultPage
     */
    public AdResultPage toResultPage(final AbstractAdSearchParameters params, final List<Ad> ads) {
        final List<AdCustomField> adFields = params.getShowAdFields() ? adCustomFieldService.list() : null;
        final List<MemberCustomField> allMemberFields = params.getShowMemberFields() ? memberCustomFieldService.list() : null;
        return queryHelper.toResultPage(AdResultPage.class, ads, new Transformer<Ad, AdVO>() {
            @Override
            public AdVO transform(final Ad ad) {
                List<MemberCustomField> memberFields = null;
                if (allMemberFields != null) {
                    MemberGroup group = EntityHelper.reference(MemberGroup.class, ad.getOwner().getGroup().getId());
                    memberFields = customFieldHelper.onlyForGroup(allMemberFields, group);
                }
                return toVO(ad, adFields, memberFields);
            }
        });
    }

    /**
     * Convert an advertisement to VO
     */
    public AdVO toVO(final Ad ad) {
        return toVO(ad, null);
    }

    /**
     * Convert an advertisement to VO, filling the given ad custom fields
     */
    public AdVO toVO(final Ad ad, final List<AdCustomField> fields) {
        return toVO(ad, fields, null);
    }

    /**
     * Convert an advertisement to VO, filling the given ad and owner custom fields
     */
    public AdVO toVO(final Ad ad, final List<AdCustomField> adFields, final List<MemberCustomField> memberFields) {
        return toVO(AdVO.class, ad, adFields, memberFields);
    }

    /**
     * Convert a category to VO
     */
    public AdCategoryVO toVO(final AdCategory category) {
        if (category == null) {
            return null;
        }
        final AdCategoryVO vo = new AdCategoryVO();
        vo.setId(category.getId());
        vo.setName(category.getFullName());
        return vo;
    }

    /**
     * Convert an advertisement to VO, filling the given ad and owner custom fields
     */
    public <VO extends AdVO> VO toVO(final Class<VO> voType, final Ad ad, final List<AdCustomField> adFields, final List<MemberCustomField> memberFields) {
        if (ad == null) {
            return null;
        }
        final LocalSettings localSettings = settingsService.getLocalSettings();

        final VO vo;
        try {
            vo = voType.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        vo.setId(ad.getId());
        vo.setCategory(toVO(ad.getCategory()));
        vo.setTitle(ad.getTitle());
        vo.setDescription(ad.getDescription());
        final Currency currency = ad.getCurrency();
        vo.setCurrency(currencyHelper.toVO(currency));
        vo.setPrice(ad.getPrice());
        if (currency == null) {
            vo.setFormattedPrice(localSettings.getNumberConverter().toString(ad.getPrice()));
        } else {
            vo.setFormattedPrice(localSettings.getUnitsConverter(currency.getPattern()).toString(ad.getPrice()));
        }
        vo.setPermanent(ad.isPermanent());
        vo.setSearching(ad.getTradeType() == Ad.TradeType.SEARCH);
        vo.setHtml(ad.isHtml());
        final Period publicationPeriod = ad.getPublicationPeriod();
        if (publicationPeriod != null) {
            final Calendar begin = publicationPeriod.getBegin();
            final Calendar end = publicationPeriod.getEnd();
            vo.setPublicationStart(begin);
            vo.setFormattedPublicationStart(localSettings.getRawDateConverter().toString(begin));
            vo.setPublicationEnd(end);
            vo.setFormattedPublicationEnd(localSettings.getRawDateConverter().toString(end));
        }
        vo.setOwner(memberHelper.toVO(ad.getOwner(), memberFields, false));
        vo.setImages(imageHelper.toVOs(ad.getImages()));
        if (adFields != null) {
            List<AdCustomField> visibleAdFields = new ArrayList<AdCustomField>();
            List<Visibility> allowedVisibilities = Arrays.asList(AdCustomField.Visibility.WEB_SERVICE, AdCustomField.Visibility.MEMBER);
            // filter by visibility
            for (AdCustomField adCustomField : adFields) {
                if (allowedVisibilities.contains(adCustomField.getVisibility())) {
                    visibleAdFields.add(adCustomField);
                }
            }
            vo.setFields(fieldHelper.toList(visibleAdFields, null, ad.getCustomValues()));
        }
        return vo;
    }

    private void fill(final AbstractAdSearchParameters params, final AbstractAdQuery query) {
        query.fetch(Ad.Relationships.OWNER, Ad.Relationships.CUSTOM_VALUES, Ad.Relationships.IMAGES, Ad.Relationships.CATEGORY);
        queryHelper.fill(params, query);
        query.setExternalPublication(true);
        if (LoggedUser.isUnrestrictedClient()) {
            AdVOStatus status = params.getStatus();
            if (status == null) {
                status = AdVOStatus.ACTIVE;
            }
            switch (status) {
                case PERMANENT:
                    query.setStatus(Ad.Status.PERMANENT);
                    break;
                case SCHEDULED:
                    query.setStatus(Ad.Status.SCHEDULED);
                    break;
                case EXPIRED:
                    query.setStatus(Ad.Status.EXPIRED);
                    break;
                default:
                    query.setStatus(Ad.Status.ACTIVE);
                    break;
            }
        } else {
            // Restricted clients can only search for active ads
            query.setStatus(Status.ACTIVE);
        }
        query.setCategory(params.getCategoryId() == null ? null : EntityHelper.reference(AdCategory.class, params.getCategoryId()));
        query.setKeywords(params.getKeywords());
        query.setInitialPrice(params.getInitialPrice());
        query.setFinalPrice(params.getFinalPrice());
        final TimePeriodVO since = params.getSince();
        if (since != null && since.getNumber() != null && since.getField() != null) {
            final TimePeriod timePeriod = new TimePeriod(since.getNumber(), TimePeriod.Field.valueOf(since.getField().name()));
            query.setSince(timePeriod);
        }
        if (params.getBeginDate() != null || params.getEndDate() != null) {
            query.setPeriod(Period.between(params.getBeginDate(), params.getEndDate()));
        }
        final AdVOTradeType tradeType = params.getTradeType();
        if (tradeType != null) {
            switch (tradeType) {
                case OFFER:
                    query.setTradeType(Ad.TradeType.OFFER);
                    break;
                case SEARCH:
                    query.setTradeType(Ad.TradeType.SEARCH);
                    break;
            }
        }
        query.setOwner(CoercionHelper.coerce(Member.class, params.getMemberId()));
        final MemberGroup[] groups = EntityHelper.references(MemberGroup.class, params.getMemberGroupIds());
        if (groups == null || groups.length > 0) {
            query.setGroups(Arrays.asList(groups));
        }
        final GroupFilter[] groupFilters = EntityHelper.references(GroupFilter.class, params.getMemberGroupFilterIds());
        if (groupFilters == null || groupFilters.length > 0) {
            query.setGroupFilters(Arrays.asList(groupFilters));
        }
        final List<FieldValueVO> adFields = params.getAdFields();
        if (adFields != null && adFields.size() > 0) {
            List<AdCustomField> adCustomFields = adCustomFieldService.list();
            adCustomFields = customFieldHelper.onlyForAdsSearch(adCustomFields);
            query.setAdValues(customFieldHelper.<AdCustomFieldValue> toValueCollection(adCustomFields, adFields));
        }
        final List<FieldValueVO> memberFields = params.getMemberFields();
        if (memberFields != null && memberFields.size() > 0) {
            List<MemberCustomField> memberCustomFields = memberCustomFieldService.list();
            memberCustomFields = customFieldHelper.onlyForAdSearch(memberCustomFields);
            query.setMemberValues(customFieldHelper.<MemberCustomFieldValue> toValueCollection(memberCustomFields, memberFields));
        }
        query.setWithImagesOnly(params.getWithImagesOnly());
    }
}
