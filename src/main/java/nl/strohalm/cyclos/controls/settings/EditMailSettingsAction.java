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

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.settings.MailSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

/**
 * Action used to edit the mail settings
 * @author luis
 */
public class EditMailSettingsAction extends BaseFormAction {
    private DataBinder<MailSettings> dataBinder;

    public DataBinder<MailSettings> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<MailSettings> binder = BeanBinder.instance(MailSettings.class);
            binder.registerBinder("fromMail", PropertyBinder.instance(String.class, "fromMail"));
            binder.registerBinder("smtpServer", PropertyBinder.instance(String.class, "smtpServer"));
            binder.registerBinder("smtpPort", PropertyBinder.instance(Integer.TYPE, "smtpPort"));
            binder.registerBinder("smtpUsername", PropertyBinder.instance(String.class, "smtpUsername"));
            binder.registerBinder("smtpPassword", PropertyBinder.instance(String.class, "smtpPassword"));
            binder.registerBinder("smtpUseTLS", PropertyBinder.instance(boolean.class, "smtpUseTLS"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditMailSettingsForm form = context.getForm();
        MailSettings settings = getDataBinder().readFromString(form.getSetting());
        settings = settingsService.save(settings);
        context.sendMessage("settings.mail.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditMailSettingsForm form = context.getForm();
        getDataBinder().writeAsString(form.getSetting(), settingsService.getMailSettings());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditMailSettingsForm form = context.getForm();
        final MailSettings settings = getDataBinder().readFromString(form.getSetting());
        settingsService.validate(settings);
    }

}
