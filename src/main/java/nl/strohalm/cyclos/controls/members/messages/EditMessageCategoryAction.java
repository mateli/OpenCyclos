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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.services.elements.MessageCategoryService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

/**
 * Action that manage the Message Category.
 * @author jeancarlo
 */
public class EditMessageCategoryAction extends BaseFormAction {

    private MessageCategoryService      messageCategoryService;
    private DataBinder<MessageCategory> dataBinder;

    public DataBinder<MessageCategory> getDataBinder() {

        if (dataBinder == null) {
            final BeanBinder<MessageCategory> binder = BeanBinder.instance(MessageCategory.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public MessageCategoryService getMessageCategoryService() {
        return messageCategoryService;
    }

    @Inject
    public void setMessageCategoryService(final MessageCategoryService messageCategoryService) {
        this.messageCategoryService = messageCategoryService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditMessageCategoryForm form = context.getForm();
        final MessageCategory category = getDataBinder().readFromString(form.getMessageCategory());
        final boolean insert = category.getId() == null;
        messageCategoryService.save(category);
        context.sendMessage(insert ? "messageCategory.inserted" : "messageCategory.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditMessageCategoryForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        MessageCategory messageCategory;
        if (form.getMessageCategoryId() > 0) {
            messageCategory = messageCategoryService.load(form.getMessageCategoryId());
        } else {
            messageCategory = new MessageCategory();
        }

        getDataBinder().writeAsString(form.getMessageCategory(), messageCategory);
        request.setAttribute("messageCategory", messageCategory);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditMessageCategoryForm form = context.getForm();
        final MessageCategory messageCategory = getDataBinder().readFromString(form.getMessageCategory());
        getMessageCategoryService().validate(messageCategory);
    }

}
