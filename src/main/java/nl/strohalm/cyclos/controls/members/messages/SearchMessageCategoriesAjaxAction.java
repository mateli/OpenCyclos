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
package nl.strohalm.cyclos.controls.members.messages;

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.messages.MessageCategoryQuery;
import nl.strohalm.cyclos.services.elements.MessageCategoryService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

/**
 * Searches message categories and returns the list as an JSON
 * @author jefferson
 */
public class SearchMessageCategoriesAjaxAction extends BaseAjaxAction {

    private MessageCategoryService                messageCategoryService;
    private BeanBinder<MessageCategoryQuery>      queryDataBinder;
    private BeanCollectionBinder<MessageCategory> messageCategoriesDataBinder;

    public BeanCollectionBinder<MessageCategory> getMessageCategoriesDataBinder() {
        if (messageCategoriesDataBinder == null) {
            final BeanBinder<MessageCategory> beanBinder = BeanBinder.instance(MessageCategory.class);
            beanBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
            beanBinder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            messageCategoriesDataBinder = BeanCollectionBinder.instance(beanBinder);
        }
        return messageCategoriesDataBinder;
    }

    public MessageCategoryService getMessageCategoryService() {
        return messageCategoryService;
    }

    public BeanBinder<MessageCategoryQuery> getQueryDataBinder() {
        if (queryDataBinder == null) {
            final ReferenceConverter<Element> elementConverter = ReferenceConverter.instance(Element.class);
            final ReferenceConverter<Group> groupConverter = ReferenceConverter.instance(Group.class);

            queryDataBinder = BeanBinder.instance(MessageCategoryQuery.class);
            queryDataBinder.registerBinder("fromElement", PropertyBinder.instance(Element.class, "fromElement", elementConverter));
            queryDataBinder.registerBinder("toElement", PropertyBinder.instance(Element.class, "toElement", elementConverter));
            queryDataBinder.registerBinder("groups", SimpleCollectionBinder.instance(Group.class, "groups", groupConverter));
        }
        return queryDataBinder;
    }

    @Inject
    public void setMessageCategoryService(final MessageCategoryService messageCategoryService) {
        this.messageCategoryService = messageCategoryService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final SearchMessageCategoriesAjaxForm form = context.getForm();
        final MessageCategoryQuery query = getQueryDataBinder().readFromString(form);
        final List<MessageCategory> messageCategories = messageCategoryService.search(query);
        final String json = getMessageCategoriesDataBinder().readAsString(messageCategories);
        responseHelper.writeJSON(context.getResponse(), json);
    }

}
