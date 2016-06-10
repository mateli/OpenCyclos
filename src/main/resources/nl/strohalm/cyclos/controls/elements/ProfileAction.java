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

import java.util.Calendar;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

/**
 * Action used to retrieve and store a member or admin profile
 * @author luis
 */
public abstract class ProfileAction<E extends Element> extends BaseFormAction implements LocalSettingsChangeListener {

    private DataBinder<E> readDataBinder;
    private DataBinder<E> writeDataBinder;

    public DataBinder<E> getReadDataBinder(final ActionContext context) {
        if (readDataBinder == null) {
            readDataBinder = initDataBinderForRead(context);
        }
        return readDataBinder;
    }

    public DataBinder<E> getWriteDataBinder(final ActionContext context) {
        if (writeDataBinder == null) {
            writeDataBinder = initDataBinderForWrite(context);
        }
        return writeDataBinder;
    }

    /**
     * We must re-create the DataBinders when the settings are saved
     */
    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        readDataBinder = null;
    }

    protected abstract <CFV extends CustomFieldValue> Class<CFV> getCustomFieldValueClass();

    protected abstract Class<E> getElementClass();

    protected abstract <G extends Group> Class<G> getGroupClass();

    protected abstract <U extends User> Class<U> getUserClass();

    protected DataBinder<E> initDataBinderForRead(final ActionContext context) {
        final LocalSettings settings = settingsService.getLocalSettings();
        final BeanBinder<? extends User> userBinder = BeanBinder.instance(getUserClass(), "user");
        userBinder.registerBinder("username", PropertyBinder.instance(String.class, "username"));
        userBinder.registerBinder("lastLogin", PropertyBinder.instance(Calendar.class, "lastLogin", settings.getDateConverter()));

        final BeanBinder<? extends Group> groupBinder = BeanBinder.instance(getGroupClass(), "group");
        groupBinder.registerBinder("name", PropertyBinder.instance(String.class, "name"));

        final BeanBinder<E> elementBinder = BeanBinder.instance(getElementClass());
        elementBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        elementBinder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        elementBinder.registerBinder("user", userBinder);
        elementBinder.registerBinder("email", PropertyBinder.instance(String.class, "email"));
        elementBinder.registerBinder("creationDate", PropertyBinder.instance(Calendar.class, "creationDate", settings.getDateConverter()));
        elementBinder.registerBinder("group", groupBinder);
        return elementBinder;
    }

    protected DataBinder<E> initDataBinderForWrite(final ActionContext context) {
        final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(getCustomFieldValueClass());
        customValueBinder.registerBinder("field", PropertyBinder.instance(CustomField.class, "field", ReferenceConverter.instance(CustomField.class)));
        customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));

        final BeanBinder<? extends User> userBinder = BeanBinder.instance(getUserClass(), "user");
        userBinder.registerBinder("username", PropertyBinder.instance(String.class, "username"));

        final BeanBinder<E> elementBinder = BeanBinder.instance(getElementClass());
        elementBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id"));
        elementBinder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        elementBinder.registerBinder("email", PropertyBinder.instance(String.class, "email"));
        elementBinder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));

        elementBinder.registerBinder("user", userBinder);

        return elementBinder;
    }
}
