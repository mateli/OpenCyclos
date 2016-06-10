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

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.elements.exceptions.NoInitialGroupException;
import nl.strohalm.cyclos.services.elements.exceptions.UsernameAlreadyInUseException;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.PasswordsDontMatchError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to create an element
 * @author luis
 */
public abstract class CreateElementAction<E extends Element> extends BaseFormAction {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <E extends Element> DataBinder<E> getDataBinder(final LocalSettings localSettings, final AccessSettings accessSettings, final Class<E> elementClass, final Class userClass, final Class groupClass, final Class customField, final Class customFieldValue) {
        final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(customFieldValue);
        customValueBinder.registerBinder("field", PropertyBinder.instance(customField, "field", ReferenceConverter.instance(customField)));
        customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));
        if (MemberCustomFieldValue.class.isAssignableFrom(customFieldValue)) {
            customValueBinder.registerBinder("hidden", PropertyBinder.instance(Boolean.TYPE, "hidden"));
        }

        final BeanBinder<E> elementBinder = BeanBinder.instance(elementClass);
        elementBinder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        elementBinder.registerBinder("email", PropertyBinder.instance(String.class, "email"));
        if (Member.class.isAssignableFrom(elementClass)) {
            elementBinder.registerBinder("hideEmail", PropertyBinder.instance(Boolean.TYPE, "hideEmail"));
        }
        elementBinder.registerBinder("group", PropertyBinder.instance(groupClass, "group", ReferenceConverter.instance(groupClass)));
        elementBinder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));

        final BeanBinder<? extends User> userBinder = BeanBinder.instance(userClass, "user");
        if (!(Member.class.isAssignableFrom(elementClass) && accessSettings.isUsernameGenerated())) {
            userBinder.registerBinder("username", PropertyBinder.instance(String.class, "username"));
        }
        userBinder.registerBinder("password", PropertyBinder.instance(String.class, "password"));
        elementBinder.registerBinder("user", userBinder);

        return elementBinder;
    }

    protected DataBinder<? extends Element> dataBinder;

    public DataBinder<? extends Element> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = getBaseBinder();
        }
        return dataBinder;
    }

    /**
     * Should be overriden to create the element
     */
    protected abstract ActionForward create(Element element, ActionContext context);

    protected DataBinder<? extends Element> getBaseBinder() {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final AccessSettings accessSettings = settingsService.getAccessSettings();
        return getDataBinder(localSettings, accessSettings, getElementClass(), getUserClass(), getGroupClass(), getCustomFieldClass(), getCustomFieldValueClass());
    }

    protected abstract <CF extends CustomField> Class<CF> getCustomFieldClass();

    protected abstract <CFV extends CustomFieldValue> Class<CFV> getCustomFieldValueClass();

    protected abstract Class<E> getElementClass();

    protected abstract <G extends Group> Class<G> getGroupClass();

    protected abstract <U extends User> Class<U> getUserClass();

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        try {
            prepareForm(context);
        } catch (final NoInitialGroupException e) {
            return context.sendError("createMember.error.noInitialGroup");
        }
        return context.getInputForward();
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final BaseBindingForm form = context.getForm();
        final Element element = getDataBinder().readFromString(form.getValues());
        element.setGroup(groupService.load(element.getGroup().getId()));
        ActionForward forward;
        try {
            forward = create(element, context);
        } catch (final NoInitialGroupException e) {
            return context.sendError("createMember.error.noInitialGroup");
        } catch (final UsernameAlreadyInUseException e) {
            return context.sendError("createMember.error.usernameAlreadyInUse", e.getUsername());
        }
        return forward == null ? context.getSuccessForward() : forward;
    }

    protected abstract void runValidation(final ActionContext context, final Element element);

    @Override
    protected void validateForm(final ActionContext context) {
        final CreateElementForm form = context.getForm();
        final Element element = getDataBinder().readFromString(form.getValues());

        ValidationException exc;
        try {
            runValidation(context, element);
            exc = new ValidationException();
        } catch (final ValidationException e) {
            exc = e;
        }

        String password;
        try {
            password = StringUtils.trimToNull(element.getUser().getPassword());
        } catch (final Exception e) {
            password = null;
        }

        final String confirmPassword = StringUtils.trimToNull(form.getConfirmPassword());
        if (password != null && (confirmPassword == null || !ObjectUtils.equals(confirmPassword, element.getUser().getPassword()))) {
            exc.addGeneralError(new PasswordsDontMatchError());
        }

        exc.throwIfHasErrors();
    }
}
