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
package nl.strohalm.cyclos.controls.ads.categories;

import org.apache.struts.action.ActionForm;

/**
 * Form used to set the ad category order
 * @author Lucas Geiss
 */
public class SetAdCategoryOrderForm extends ActionForm {

    private static final long serialVersionUID = -3918299366981297865L;
    private long              currentCategory;
    private Long[]            adCategoryIds;
    private boolean           orderAlpha;

    public Long[] getAdCategoryIds() {
        return adCategoryIds;
    }

    public long getCurrentCategory() {
        return currentCategory;
    }

    public boolean isOrderAlpha() {
        return orderAlpha;
    }

    public void setAdCategoryIds(final Long[] adCategoryIds) {
        this.adCategoryIds = adCategoryIds;
    }

    public void setCurrentCategory(final long currentCategory) {
        this.currentCategory = currentCategory;
    }

    public void setOrderAlpha(final boolean orderAlpha) {
        this.orderAlpha = orderAlpha;
    }

}
