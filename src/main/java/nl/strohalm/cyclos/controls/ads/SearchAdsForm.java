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

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.BaseQueryForm;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.Ad.TradeType;
import nl.strohalm.cyclos.utils.Navigation;
import nl.strohalm.cyclos.utils.binding.MapBean;

import org.apache.struts.action.ActionMapping;

/**
 * Form used to search advertisements
 * 
 * @author luis
 * @author Lucas
 */
public class SearchAdsForm extends BaseQueryForm {

    private static final long serialVersionUID = -5275865610699846428L;
    private boolean           advanced;
    private boolean           forceShowFields;
    private boolean           categoryOnly;
    private boolean           lastAds;
    private boolean           alreadySearched;

    public SearchAdsForm() {
        clearForm();
    }

    public void clearForm() {
        getQuery().clear();
        setQuery("since", new MapBean("number", "period"));
        setQuery("adValues", new MapBean(true, "field", "value"));
        setQuery("memberValues", new MapBean(true, "field", "value"));
        setQuery("tradeType", TradeType.OFFER.name());
        setQuery("status", Ad.Status.ACTIVE.name());
        setQuery("groups", Collections.emptyList());
        setQuery("groupFilters", Collections.emptyList());
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public boolean isAlreadySearched() {
        return alreadySearched;
    }

    public boolean isCategoryOnly() {
        return categoryOnly;
    }

    public boolean isForceShowFields() {
        return forceShowFields;
    }

    public boolean isLastAds() {
        return lastAds;
    }

    @Override
    public void reset(final ActionMapping mapping, final HttpServletRequest request) {
        super.reset(mapping, request);
        advanced = false;
        setQuery("withImagesOnly", "false");
        setCategoryOnly(false);
        setLastAds(false);
        setForceShowFields(false);
        setAdvanced(false);

        // Clear the group filters on each request only if not in a nested path
        if (request != null) {
            final Navigation navigation = Navigation.get(request.getSession());
            if (navigation == null || navigation.size() == 1) {
                setQuery("groupFilters", Collections.emptyList());
            }
        }
    }

    public void setAdvanced(final boolean advanced) {
        this.advanced = advanced;
    }

    public void setAlreadySearched(final boolean alreadySearched) {
        this.alreadySearched = alreadySearched;
    }

    public void setCategoryOnly(final boolean categoryOnly) {
        this.categoryOnly = categoryOnly;
    }

    public void setForceShowFields(final boolean forceShowFields) {
        this.forceShowFields = forceShowFields;
    }

    public void setLastAds(final boolean lastAds) {
        this.lastAds = lastAds;
    }

}
