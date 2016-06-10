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
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search elements
 * @author luis
 */
public abstract class SearchElementsAction<Q extends FullTextElementQuery> extends BaseQueryAction {

    public static <Q extends FullTextElementQuery> DataBinder<Q> elementQueryDataBinder(final LocalSettings settings, final Class<Q> queryClass, final Class<? extends CustomFieldValue> customFieldValueClass) {
        final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(customFieldValueClass);
        customValueBinder.registerBinder("field", PropertyBinder.instance(MemberCustomField.class, "field", ReferenceConverter.instance(MemberCustomField.class)));
        customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value"));

        final BeanBinder<Q> binder = BeanBinder.instance(queryClass);
        if (FullTextMemberQuery.class.isAssignableFrom(queryClass)) {
            binder.registerBinder("activationPeriod", DataBinderHelper.periodBinder(settings, "activationPeriod"));
            binder.registerBinder("groupFilters", SimpleCollectionBinder.instance(GroupFilter.class, "groupFilters"));
            binder.registerBinder("broker", PropertyBinder.instance(Member.class, "broker"));
        }
        binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));
        binder.registerBinder("groups", SimpleCollectionBinder.instance(Group.class, "groups"));
        binder.registerBinder("keywords", PropertyBinder.instance(String.class, "keywords"));
        binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());

        return binder;
    }

    private DataBinder<Q> dataBinder;

    public DataBinder<Q> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            dataBinder = elementQueryDataBinder(settings, getQueryClass(), getCustomFieldValueClass());
        }
        return dataBinder;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final Q query = (Q) queryParameters;
        final List<? extends Element> list = elementService.fullTextSearch(query);
        context.getRequest().setAttribute("elements", list);
    }

    protected abstract Class<? extends CustomFieldValue> getCustomFieldValueClass();

    protected abstract Class<Q> getQueryClass();

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchElementsForm form = context.getForm();
        final Q query = getDataBinder().readFromString(form.getQuery());
        query.fetch(Element.Relationships.USER, Element.Relationships.GROUP);
        return query;
    }

}
