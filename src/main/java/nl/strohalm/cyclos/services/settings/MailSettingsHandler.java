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

import nl.strohalm.cyclos.entities.settings.MailSettings;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.MailSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.MailSettingsEvent;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.validation.EmailValidation;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Mail settings handler
 * @author luis
 */
public class MailSettingsHandler extends BaseSettingsHandler<MailSettings, MailSettingsChangeListener> {

    protected MailSettingsHandler() {
        super(Setting.Type.MAIL, MailSettings.class);
    }

    @Override
    protected Map<String, Converter<?>> createConverters() {
        final Map<String, Converter<?>> mailConverters = new LinkedHashMap<String, Converter<?>>();
        mailConverters.put("fromMail", CoercionConverter.instance(String.class));
        mailConverters.put("smtpServer", CoercionConverter.instance(String.class));
        mailConverters.put("smtpPort", CoercionConverter.instance(Integer.TYPE));
        mailConverters.put("smtpUsername", CoercionConverter.instance(String.class));
        mailConverters.put("smtpPassword", CoercionConverter.instance(String.class));
        mailConverters.put("smtpUseTLS", CoercionConverter.instance(boolean.class));
        return mailConverters;
    }

    @Override
    protected Validator createValidator() {
        final Validator mailValidator = new Validator("settings.mail");
        mailValidator.property("fromMail").required().add(EmailValidation.instance());
        mailValidator.property("smtpServer").required();
        mailValidator.property("smtpPort").required().positiveNonZero();
        return mailValidator;
    }

    @Override
    protected void notifyListener(final MailSettingsChangeListener listener, final MailSettings settings) {
        listener.onMailSettingsUpdate(new MailSettingsEvent(settings));
    }
}
