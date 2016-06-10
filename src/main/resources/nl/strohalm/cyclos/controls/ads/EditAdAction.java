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
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TextFormat;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.conversion.StringTrimmerConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to edit an advertisement
 * @author luis
 */
public class EditAdAction extends BaseAdAction {

    private AccountTypeService accountTypeService;
    private CurrencyService    currencyService;
    private DataBinder<Ad>     writeDataBinder;
    private ReadWriteLock      lock = new ReentrantReadWriteLock(true);

    // Used to get data and save to database
    public DataBinder<Ad> getWriteDataBinder() {
        try {
            lock.readLock().lock();
            if (writeDataBinder == null) {
                final LocalSettings settings = settingsService.getLocalSettings();
                final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(AdCustomFieldValue.class);
                customValueBinder.registerBinder("field", PropertyBinder.instance(AdCustomField.class, "field", ReferenceConverter.instance(AdCustomField.class)));
                customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));

                final BeanBinder<Ad> binder = BeanBinder.instance(Ad.class);
                binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
                binder.registerBinder("owner", PropertyBinder.instance(Member.class, "owner", ReferenceConverter.instance(Member.class)));
                binder.registerBinder("tradeType", PropertyBinder.instance(Ad.TradeType.class, "tradeType"));
                binder.registerBinder("category", PropertyBinder.instance(AdCategory.class, "category", ReferenceConverter.instance(AdCategory.class)));
                binder.registerBinder("title", PropertyBinder.instance(String.class, "title"));
                binder.registerBinder("externalPublication", PropertyBinder.instance(Boolean.TYPE, "externalPublication"));
                binder.registerBinder("permanent", PropertyBinder.instance(Boolean.TYPE, "permanent"));
                binder.registerBinder("publicationPeriod", DataBinderHelper.rawPeriodBinder(settings, "publicationPeriod"));
                binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
                binder.registerBinder("price", PropertyBinder.instance(BigDecimal.class, "price", settings.getNumberConverter()));
                binder.registerBinder("html", PropertyBinder.instance(Boolean.TYPE, "html"));
                binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));

                writeDataBinder = binder;
            }
            return writeDataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            super.onLocalSettingsUpdate(event);
            writeDataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * gets the member
     */
    protected Member getMember(final ActionContext context) {
        Member member;
        final AdForm form = context.getForm();

        final long adId = form.getId();
        if (adId > 0) {
            return getAdService().load(adId, Ad.Relationships.OWNER).getOwner();
        }

        final Element loggedElement = context.getElement();
        if (form.getMemberId() <= 0 || form.getMemberId() == loggedElement.getId()) {
            if (context.isAdmin()) {
                throw new ValidationException();
            }
            if (context.isOperator()) {
                member = ((Operator) context.getElement()).getMember();
            } else { // context.isMember()
                member = context.getElement();
            }
        } else {
            final Element element = elementService.load(form.getMemberId(), Element.Relationships.USER);
            if (!(element instanceof Member)) {
                throw new ValidationException();
            }
            member = (Member) element;
        }
        member = elementService.load(member.getId(), Element.Relationships.GROUP);
        return member;
    }

    /**
     * gets the number of ads for this member
     * @return an int indicating the number of ads
     */
    protected int getNumberOfAds(final Member member) {
        final Map<Ad.Status, Integer> adMap = getAdService().getNumberOfAds(null, member);
        final Collection<Integer> values = adMap.values();
        int totalAds = 0;
        for (final Integer i : values) {
            totalAds += i;
        }
        return totalAds;
    }

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final AdForm form = context.getForm();
        final ActionForward forward = super.handleDisplay(context);
        final Ad ad = (Ad) request.getAttribute("ad");
        TextFormat descriptionFormat;
        if (ad.isTransient()) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            descriptionFormat = localSettings.getAdDescriptionFormat();
            form.setAd("html", descriptionFormat == TextFormat.RICH);
        } else {
            descriptionFormat = ad.isHtml() ? TextFormat.RICH : TextFormat.PLAIN;
        }
        request.setAttribute("descriptionFormat", descriptionFormat);
        final boolean editable = (Boolean) request.getAttribute("editable");
        final MemberGroup memberGroup = ad.getOwner().getMemberGroup();
        final List<Currency> currencies = currencyService.listByMemberGroup(memberGroup);
        request.setAttribute("currencies", currencies);
        if (editable) {

            if (currencies.size() == 1) {
                // Set a single currency variable when there's only one option
                request.setAttribute("singleCurrency", currencies.get(0));
            } else if (currencies.size() > 1 && ad.getCurrency() == null) {
                // When there's multiple currencies, pre select the one of the default account
                final MemberAccountType defaultAccountType = accountTypeService.getDefault(memberGroup, AccountType.Relationships.CURRENCY);
                if (defaultAccountType != null) {
                    form.setAd("currency", CoercionHelper.coerce(String.class, defaultAccountType.getCurrency()));
                }
            }
            final Member member = getMember(context);
            final MemberGroupSettings memberSettings = member.getMemberGroup().getMemberSettings();

            // Check if more ads can be added
            final int adCount = getNumberOfAds(member);
            final int maxAdsPerMember = memberSettings.getMaxAdsPerMember();
            final boolean maxAds = (adCount >= maxAdsPerMember);
            request.setAttribute("maxAds", maxAds);

            // Store the restrictions
            request.setAttribute("enablePermanent", memberSettings.isEnablePermanentAds());
            request.setAttribute("enableExternalPublication", memberSettings.getExternalAdPublication() == MemberGroupSettings.ExternalAdPublication.ALLOW_CHOICE);
            return forward;
        } else {
            // Non-editable ads cannot change currency: use the first one
            request.setAttribute("singleCurrency", currencies.isEmpty() ? null : currencies.get(0));
            return ActionHelper.redirectWithParam(request, context.findForward("view"), "id", form.getId());
        }
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final AdForm form = context.getForm();
        Ad ad = readAd(context);
        final boolean isInsert = ad.isTransient();

        // Save the advertisement
        ad = getAdService().save(ad);

        // Save the uploaded image
        final FormFile upload = form.getPicture();
        if (upload != null && upload.getFileSize() > 0) {
            try {
                StringBuffer newFileName = new StringBuffer(upload.getFileName());
                if (upload.getFileName().length() > 100) {
                    // File name cannot be greater than 100 characters
                    newFileName = new StringBuffer();
                    final String name = upload.getFileName();
                    final int extensionPos = name.lastIndexOf(".");
                    final String extension = name.substring(extensionPos);
                    newFileName.append(name.substring(0, 100 - extension.length()));
                    newFileName.append(extension);
                }
                getImageService().save(ad, form.getPictureCaption(), ImageType.getByContentType(upload.getContentType()), newFileName.toString(), upload.getInputStream());
            } finally {
                upload.destroy();
            }
        }

        context.sendMessage(isInsert ? "ad.inserted" : "ad.modified");
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", ad.getId());
        params.put("memberId", ad.getOwner().getId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected Ad resolveAd(final ActionContext context) throws Exception {
        final AdForm form = context.getForm();
        if (form.getId() > 0L) {
            // Edit an ad - the superclass will read an existing ad
            return super.resolveAd(context);
        } else {
            // Insert a new ad
            Member member;
            final Element loggedElement = context.getElement();
            if (form.getMemberId() > 0L && form.getMemberId() != loggedElement.getId()) {
                final Element element = elementService.load(form.getMemberId(), Element.Relationships.GROUP);
                if (!(element instanceof Member)) {
                    throw new ValidationException();
                }
                member = (Member) element;
            } else {
                if (context.isAdmin()) {
                    throw new ValidationException();
                } else if (context.isMember()) {
                    member = context.getElement();
                } else { // context.isOperator()
                    member = ((Operator) context.getElement()).getMember();
                }
            }

            final MemberGroup group = member.getMemberGroup();
            final MemberGroupSettings settings = group.getMemberSettings();
            final TimePeriod defaultPublicationTime = (settings == null ? null : settings.getDefaultAdPublicationTime());

            // Set the default values
            final Ad ad = new Ad();
            ad.setOwner(member);
            ad.setTradeType(Ad.TradeType.OFFER);
            final Calendar today = Calendar.getInstance();
            ad.setPublicationPeriod(Period.between(today, defaultPublicationTime.add(today)));
            return ad;
        }
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final Ad ad = readAd(context);
        getAdService().validate(ad);
    }

    private Ad readAd(final ActionContext context) {
        final AdForm form = context.getForm();
        final Ad ad = getWriteDataBinder().readFromString(form.getAd());
        if (ad.isHtml()) {
            ad.setDescription(HtmlConverter.instance().valueOf("" + form.getAd("description")));
        } else {
            ad.setDescription(StringTrimmerConverter.instance().valueOf("" + form.getAd("description")));
        }
        if (ad.getOwner() == null) {
            if (context.isMember()) {
                ad.setOwner((Member) context.getElement());
            } else if (context.isOperator()) {
                final Operator operator = (Operator) context.getElement();
                ad.setOwner(operator.getMember());
            }
        }
        return ad;
    }
}
