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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.ads.AdCategoryService;
import nl.strohalm.cyclos.services.ads.AdService;
import nl.strohalm.cyclos.services.customization.AdCustomFieldService;
import nl.strohalm.cyclos.services.customization.ImageService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Base action for ad display
 * @author luis
 */
public abstract class BaseAdAction extends BaseFormAction implements LocalSettingsChangeListener {

    private static final Relationship[] FETCH = { RelationshipHelper.nested(Ad.Relationships.CATEGORY, RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT)), RelationshipHelper.nested(Ad.Relationships.OWNER, Element.Relationships.USER), RelationshipHelper.nested(Ad.Relationships.OWNER, Element.Relationships.GROUP), Ad.Relationships.CUSTOM_VALUES, Ad.Relationships.IMAGES, Ad.Relationships.CURRENCY };
    private AdService                   adService;
    private AdCategoryService           adCategoryService;
    private AdCustomFieldService        adCustomFieldService;
    private ImageService                imageService;
    private DataBinder<Ad>              readDataBinder;
    private ReadWriteLock               lock  = new ReentrantReadWriteLock(true);

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

    public ImageService getImageService() {
        return imageService;
    }

    public DataBinder<Ad> getReadDataBinder() {
        try {
            lock.readLock().lock();
            if (readDataBinder == null) {
                final LocalSettings settings = settingsService.getLocalSettings();

                final BeanBinder<Ad> binder = BeanBinder.instance(Ad.class);
                binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
                binder.registerBinder("owner", PropertyBinder.instance(Member.class, "owner", ReferenceConverter.instance(Member.class)));
                // binder.registerBinder("by", PropertyBinder.instance(Element.class, "by", ReferenceConverter.instance(Element.class)));
                binder.registerBinder("tradeType", PropertyBinder.instance(Ad.TradeType.class, "tradeType"));
                binder.registerBinder("category", PropertyBinder.instance(AdCategory.class, "category", ReferenceConverter.instance(AdCategory.class)));
                binder.registerBinder("title", PropertyBinder.instance(String.class, "title"));
                binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
                binder.registerBinder("html", PropertyBinder.instance(boolean.class, "html"));
                binder.registerBinder("externalPublication", PropertyBinder.instance(Boolean.TYPE, "externalPublication"));
                binder.registerBinder("permanent", PropertyBinder.instance(Boolean.TYPE, "permanent"));
                binder.registerBinder("publicationPeriod", DataBinderHelper.rawPeriodBinder(settings, "publicationPeriod"));
                binder.registerBinder("price", PropertyBinder.instance(BigDecimal.class, "price", settings.getNumberConverter()));
                binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));

                readDataBinder = binder;
            }
            return readDataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            readDataBinder = null;
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
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setImageService(final ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final AdForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final Ad ad = resolveAd(context);

        final boolean editable = adService.isEditable(ad);
        getReadDataBinder().writeAsString(form.getAd(), ad);
        boolean maxImages = false;
        request.setAttribute("ad", ad);
        List<AdCustomField> customFields = adCustomFieldService.list();
        customFields = customFieldHelper.onlyForAdsSearch(customFields);
        request.setAttribute("customFields", customFieldHelper.buildEntries(customFields, ad.getCustomValues()));
        if (ad.isPersistent()) {
            final List<? extends Image> images = imageService.listByOwner(ad);
            final MemberGroupSettings groupSettings = ad.getOwner().getMemberGroup().getMemberSettings();
            maxImages = groupSettings == null ? true : images.size() >= groupSettings.getMaxAdImagesPerMember();
            request.setAttribute("images", images);
        }
        request.setAttribute("maxImages", maxImages);
        request.setAttribute("editable", editable);

        if (editable) {
            // Ensure root categories without children are presented before the nested categories, or the page's select field will be messed up
            final List<AdCategory> leafCategories = new ArrayList<AdCategory>(adCategoryService.listLeaf());
            final List<AdCategory> categories = new ArrayList<AdCategory>();
            for (final Iterator<AdCategory> iterator = leafCategories.iterator(); iterator.hasNext();) {
                final AdCategory category = iterator.next();
                if (category.isRoot() && category.isLeaf()) {
                    iterator.remove();
                    categories.add(category);
                }
            }
            // Now, those categories which are both root and leaf are first. Add others
            categories.addAll(leafCategories);
            request.setAttribute("categories", categories);
            RequestHelper.storeEnum(request, Ad.TradeType.class, "tradeTypes");
        }
    }

    protected Ad resolveAd(final ActionContext context) throws Exception {
        final AdForm form = context.getForm();
        if (form.getId() <= 0) {
            throw new ValidationException();
        }
        return adService.load(form.getId(), FETCH);
    }

}
