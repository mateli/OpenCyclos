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
package nl.strohalm.cyclos.controls.members.adinterests;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterest;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.ads.AdCategoryService;
import nl.strohalm.cyclos.services.elements.AdInterestService;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

public class EditAdInterestAction extends BaseFormAction implements LocalSettingsChangeListener {

    private AdInterestService      adInterestService;
    private AdCategoryService      adCategoryService;
    private GroupFilterService     groupFilterService;
    private CurrencyService        currencyService;
    private AccountTypeService     accountTypeService;
    private DataBinder<AdInterest> dataBinder;

    public DataBinder<AdInterest> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            final BeanBinder<AdInterest> binder = BeanBinder.instance(AdInterest.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("owner", PropertyBinder.instance(Member.class, "owner", ReferenceConverter.instance(Member.class)));
            binder.registerBinder("title", PropertyBinder.instance(String.class, "title"));
            binder.registerBinder("type", PropertyBinder.instance(Ad.TradeType.class, "type"));
            binder.registerBinder("category", PropertyBinder.instance(AdCategory.class, "category", ReferenceConverter.instance(AdCategory.class)));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member", ReferenceConverter.instance(Member.class)));
            binder.registerBinder("groupFilter", PropertyBinder.instance(GroupFilter.class, "groupFilter", ReferenceConverter.instance(GroupFilter.class)));
            binder.registerBinder("initialPrice", PropertyBinder.instance(BigDecimal.class, "initialPrice", settings.getNumberConverter()));
            binder.registerBinder("finalPrice", PropertyBinder.instance(BigDecimal.class, "finalPrice", settings.getNumberConverter()));
            binder.registerBinder("currency", PropertyBinder.instance(Currency.class, "currency"));
            binder.registerBinder("keywords", PropertyBinder.instance(String.class, "keywords"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setAdCategoryService(final AdCategoryService adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    @Inject
    public void setAdInterestService(final AdInterestService adInterestService) {
        this.adInterestService = adInterestService;
    }

    @Inject
    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final AdInterest adInterest = resolveAdInterest(context);
        final boolean isInsert = adInterest.isTransient();
        adInterestService.save(adInterest);
        context.sendMessage(isInsert ? "adInterest.inserted" : "adInterest.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        // Send ad interest to JSP
        final EditAdInterestForm form = context.getForm();
        AdInterest adInterest = resolveAdInterest(context);
        final Long id = adInterest.getId();
        if (id != null) {
            adInterest = adInterestService.load(adInterest.getId(), RelationshipHelper.nested(AdInterest.Relationships.MEMBER, Element.Relationships.USER));
        }
        getDataBinder().writeAsString(form.getAdInterest(), adInterest);
        request.setAttribute("adInterest", adInterest);

        // Send trade types to JSP
        RequestHelper.storeEnum(request, Ad.TradeType.class, "tradeTypes");

        // Send categories to JSP
        request.setAttribute("adCategories", adCategoryService.listLeaf());

        // Send group filters to JSP
        final MemberGroup memberGroup = context.getGroup();
        final GroupFilterQuery groupFilterQuery = new GroupFilterQuery();
        groupFilterQuery.setViewableBy(memberGroup);
        final List<GroupFilter> groupFilters = groupFilterService.search(groupFilterQuery);
        if (groupFilters.size() > 0) {
            request.setAttribute("groupFilters", groupFilters);
        }

        // Send currencies to JSP
        final List<Currency> currencies = currencyService.listByMemberGroup(memberGroup);
        request.setAttribute("currencies", currencies);
        if (currencies.size() == 1) {
            // Set a single currency variable when there's only one option
            request.setAttribute("singleCurrency", currencies.get(0));
        } else if (currencies.size() > 1 && adInterest.getCurrency() == null) {
            // When there's multiple currencies, pre select the one of the default account
            final MemberAccountType defaultAccountType = accountTypeService.getDefault(memberGroup, AccountType.Relationships.CURRENCY);
            if (defaultAccountType != null) {
                form.setAdInterest("currency", CoercionHelper.coerce(String.class, defaultAccountType.getCurrency()));
            }
        }

    }

    @Override
    protected void validateForm(final ActionContext context) {
        final AdInterest adInterest = resolveAdInterest(context);
        adInterestService.validate(adInterest);
    }

    private AdInterest resolveAdInterest(final ActionContext context) {
        final EditAdInterestForm form = context.getForm();
        final AdInterest adInterest = getDataBinder().readFromString(form.getAdInterest());
        if (adInterest.getOwner() == null && context.isMember()) {
            adInterest.setOwner((Member) context.getElement());
        }
        if (adInterest.getType() == null) {
            adInterest.setType(Ad.TradeType.OFFER);
        }
        return adInterest;
    }

}
