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
package nl.strohalm.cyclos.controls.settings;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

/**
 * Action used to edit the access settings
 * @author luis
 */
public class EditAccessSettingsAction extends BaseFormAction {
    private DataBinder<AccessSettings> dataBinder;

    public DataBinder<AccessSettings> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<AccessSettings> binder = BeanBinder.instance(AccessSettings.class);
            binder.registerBinder("virtualKeyboard", PropertyBinder.instance(Boolean.TYPE, "virtualKeyboard"));
            binder.registerBinder("virtualKeyboardTransactionPassword", PropertyBinder.instance(Boolean.TYPE, "virtualKeyboardTransactionPassword"));
            binder.registerBinder("numericPassword", PropertyBinder.instance(Boolean.TYPE, "numericPassword"));
            binder.registerBinder("allowMultipleLogins", PropertyBinder.instance(Boolean.TYPE, "allowMultipleLogins"));
            binder.registerBinder("allowOperatorLogin", PropertyBinder.instance(Boolean.TYPE, "allowOperatorLogin"));
            binder.registerBinder("adminTimeout", DataBinderHelper.timePeriodBinder("adminTimeout"));
            binder.registerBinder("administrationWhitelist", PropertyBinder.instance(String.class, "administrationWhitelist"));
            binder.registerBinder("memberTimeout", DataBinderHelper.timePeriodBinder("memberTimeout"));
            binder.registerBinder("poswebTimeout", DataBinderHelper.timePeriodBinder("poswebTimeout"));
            binder.registerBinder("usernameLength", DataBinderHelper.rangeConstraintBinder("usernameLength"));
            binder.registerBinder("usernameGeneration", PropertyBinder.instance(AccessSettings.UsernameGeneration.class, "usernameGeneration"));
            binder.registerBinder("generatedUsernameLength", PropertyBinder.instance(Integer.TYPE, "generatedUsernameLength"));
            binder.registerBinder("transactionPasswordChars", PropertyBinder.instance(String.class, "transactionPasswordChars"));
            binder.registerBinder("usernameRegex", PropertyBinder.instance(String.class, "usernameRegex"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditAccessSettingsForm form = context.getForm();
        AccessSettings settings = getDataBinder().readFromString(form.getSetting());
        settings = settingsService.save(settings);
        context.sendMessage("settings.access.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditAccessSettingsForm form = context.getForm();
        getDataBinder().writeAsString(form.getSetting(), settingsService.getAccessSettings());
        RequestHelper.storeEnum(request, AccessSettings.UsernameGeneration.class, "usernameGenerations");
        request.setAttribute("timePeriodFields", Arrays.asList(TimePeriod.Field.SECONDS, TimePeriod.Field.MINUTES, TimePeriod.Field.HOURS, TimePeriod.Field.DAYS));
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditAccessSettingsForm form = context.getForm();
        final AccessSettings settings = getDataBinder().readFromString(form.getSetting());
        settingsService.validate(settings);
    }

}
