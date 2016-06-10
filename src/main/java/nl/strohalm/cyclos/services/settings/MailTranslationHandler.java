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
package nl.strohalm.cyclos.services.settings;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.settings.MailTranslation;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.MailTranslationChangeListener;
import nl.strohalm.cyclos.entities.settings.events.MailTranslationEvent;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Mail settings handler
 * @author luis
 */
public class MailTranslationHandler extends BaseSettingsHandler<MailTranslation, MailTranslationChangeListener> {
    private static final int MAX_SUBJECT_SIZE = 255;
    private static final int MAX_BODY_SIZE    = 8000;

    protected MailTranslationHandler() {
        super(Setting.Type.MAIL_TRANSLATION, MailTranslation.class);
    }

    @Override
    protected Map<String, Converter<?>> createConverters() {
        final Map<String, Converter<?>> mailConverters = new LinkedHashMap<String, Converter<?>>();
        mailConverters.put("invitationSubject", CoercionConverter.instance(String.class));
        mailConverters.put("invitationMessage", CoercionConverter.instance(String.class));
        mailConverters.put("activationSubject", CoercionConverter.instance(String.class));
        mailConverters.put("activationMessageWithPassword", CoercionConverter.instance(String.class));
        mailConverters.put("activationMessageWithoutPassword", CoercionConverter.instance(String.class));
        mailConverters.put("resetPasswordSubject", CoercionConverter.instance(String.class));
        mailConverters.put("resetPasswordMessage", CoercionConverter.instance(String.class));
        mailConverters.put("mailValidationSubject", CoercionConverter.instance(String.class));
        mailConverters.put("mailValidationMessage", CoercionConverter.instance(String.class));
        return mailConverters;
    }

    @Override
    protected Validator createValidator() {
        final Validator mailValidator = new Validator("settings.mail");
        mailValidator.property("invitationSubject").key("settings.mail.subject").required().maxLength(MAX_SUBJECT_SIZE);
        mailValidator.property("invitationMessage").key("settings.mail.message").required().maxLength(MAX_BODY_SIZE);
        mailValidator.property("activationSubject").key("settings.mail.subject").required().maxLength(MAX_SUBJECT_SIZE);
        mailValidator.property("activationMessageWithPassword").key("settings.mail.message").required().maxLength(MAX_BODY_SIZE);
        mailValidator.property("activationMessageWithoutPassword").key("settings.mail.message").required().maxLength(MAX_BODY_SIZE);
        mailValidator.property("resetPasswordSubject").key("settings.mail.subject").required().maxLength(MAX_SUBJECT_SIZE);
        mailValidator.property("resetPasswordMessage").key("settings.mail.message").required().maxLength(MAX_BODY_SIZE);
        mailValidator.property("mailValidationSubject").key("settings.mail.subject").required().maxLength(MAX_SUBJECT_SIZE);
        mailValidator.property("mailValidationMessage").key("settings.mail.message").required().maxLength(MAX_BODY_SIZE);
        return mailValidator;
    }

    @Override
    protected void notifyListener(final MailTranslationChangeListener listener, final MailTranslation settings) {
        listener.onMailSettingsUpdate(new MailTranslationEvent(settings));
    }

}
