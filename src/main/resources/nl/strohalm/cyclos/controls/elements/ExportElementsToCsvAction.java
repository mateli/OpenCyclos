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
package nl.strohalm.cyclos.controls.elements;

import java.util.List;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseCsvAction;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Action used to export an element search result as csv
 * @author luis
 */
public abstract class ExportElementsToCsvAction extends BaseCsvAction {

    private DataBinder<FullTextMemberQuery> dataBinder;

    public DataBinder<FullTextMemberQuery> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = SearchElementsAction.elementQueryDataBinder(settingsService.getLocalSettings(), FullTextMemberQuery.class, MemberCustomFieldValue.class);
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        super.onLocalSettingsUpdate(event);
        dataBinder = null;
    }

    @Override
    protected List<?> executeQuery(final ActionContext context) {
        final FullTextElementQuery query = resolveQuery(context);
        return elementService.fullTextSearch(query);
    }

    protected FullTextElementQuery resolveQuery(final ActionContext context) {
        final SearchElementsForm form = context.getForm();
        final FullTextElementQuery query = getDataBinder().readFromString(form.getQuery());
        query.setResultType(ResultType.ITERATOR);
        query.fetch(Element.Relationships.USER, Element.Relationships.GROUP);
        return query;
    }
}
