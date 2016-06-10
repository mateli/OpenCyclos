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
import nl.strohalm.cyclos.entities.settings.MailTranslation;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;

/**
 * Action used to edit the mail translation
 * @author luis
 */
public class EditMailTranslationAction extends BaseFormAction {
    private DataBinder<MailTranslation> dataBinder;

    public DataBinder<MailTranslation> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<MailTranslation> binder = BeanBinder.instance(MailTranslation.class);
            binder.registerBinder("invitationSubject", PropertyBinder.instance(String.class, "invitationSubject"));
            binder.registerBinder("invitationMessage", PropertyBinder.instance(String.class, "invitationMessage", HtmlConverter.instance()));
            binder.registerBinder("activationSubject", PropertyBinder.instance(String.class, "activationSubject"));
            binder.registerBinder("activationMessageWithPassword", PropertyBinder.instance(String.class, "activationMessageWithPassword", HtmlConverter.instance()));
            binder.registerBinder("activationMessageWithoutPassword", PropertyBinder.instance(String.class, "activationMessageWithoutPassword", HtmlConverter.instance()));
            binder.registerBinder("resetPasswordSubject", PropertyBinder.instance(String.class, "resetPasswordSubject"));
            binder.registerBinder("resetPasswordMessage", PropertyBinder.instance(String.class, "resetPasswordMessage", HtmlConverter.instance()));
            binder.registerBinder("mailValidationSubject", PropertyBinder.instance(String.class, "mailValidationSubject"));
            binder.registerBinder("mailValidationMessage", PropertyBinder.instance(String.class, "mailValidationMessage", HtmlConverter.instance()));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public SettingsService getTranslationService() {
        return settingsService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditMailTranslationForm form = context.getForm();
        MailTranslation settings = getDataBinder().readFromString(form.getSetting());
        settings = settingsService.save(settings);
        context.sendMessage("settings.mailTranslation.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditMailTranslationForm form = context.getForm();
        getDataBinder().writeAsString(form.getSetting(), settingsService.getMailTranslation());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditMailTranslationForm form = context.getForm();
        final MailTranslation settings = getDataBinder().readFromString(form.getSetting());
        settingsService.validate(settings);
    }

}
