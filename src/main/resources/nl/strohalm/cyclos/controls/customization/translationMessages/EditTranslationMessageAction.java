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
package nl.strohalm.cyclos.controls.customization.translationMessages;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessage;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.services.customization.TranslationMessageService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.UniqueError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to edit a translation message
 * @author luis
 */
public class EditTranslationMessageAction extends BaseFormAction {
    private TranslationMessageService      translationMessageService;
    private DataBinder<TranslationMessage> dataBinder;

    public DataBinder<TranslationMessage> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<TranslationMessage> binder = BeanBinder.instance(TranslationMessage.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("key", PropertyBinder.instance(String.class, "key"));
            binder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance(false)));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setTranslationMessageService(final TranslationMessageService translationMessageService) {
        this.translationMessageService = translationMessageService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditTranslationMessageForm form = context.getForm();
        final TranslationMessage translationMessage = getDataBinder().readFromString(form.getMessage());
        final boolean isInsert = translationMessage.getId() == null;
        try {
            translationMessageService.save(translationMessage);
            context.sendMessage(isInsert ? "translationMessage.inserted" : "translationMessage.modified");
        } catch (final DaoException e) {
            throw new ValidationException("key", "translationMessage.key", new UniqueError(translationMessage.getKey()));
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditTranslationMessageForm form = context.getForm();
        final long id = form.getMessageId();
        final boolean isInsert = id <= 0L;
        if (!isInsert) {
            final TranslationMessage translationMessage = translationMessageService.load(id);
            getDataBinder().writeAsString(form.getMessage(), translationMessage);
            request.setAttribute("message", translationMessage);
        }
        request.setAttribute("isInsert", isInsert);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditTranslationMessageForm form = context.getForm();
        final TranslationMessage translationMessage = getDataBinder().readFromString(form.getMessage());
        translationMessageService.validate(translationMessage);
    }
}
