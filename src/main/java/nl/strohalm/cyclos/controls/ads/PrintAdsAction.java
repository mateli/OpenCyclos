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

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.ads.AbstractAdQuery;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;

/**
 * Action used to print advertisements
 * @author luis
 * @author Lucas
 */
public class PrintAdsAction extends SearchAdsAction {
    @Override
    protected Integer pageSize(final ActionContext context) {
        return null;
    }

    @Override
    protected AbstractAdQuery prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();

        final AbstractAdQuery query = super.prepareForm(context);
        query.fetch(RelationshipHelper.nested(Ad.Relationships.CATEGORY, RelationshipHelper.nested(AdCategory.MAX_LEVEL, AdCategory.Relationships.PARENT)), Ad.Relationships.CUSTOM_VALUES);

        // Store the ad custom values
        request.setAttribute("adFields", adCustomFieldService.list());

        // Calculate since date
        final TimePeriod since = query.getSince();
        Calendar sinceDate = null;
        if (since != null && since.isValid()) {
            sinceDate = since.remove(Calendar.getInstance());
        }
        request.setAttribute("sinceDate", sinceDate);
        return query;
    }
}
