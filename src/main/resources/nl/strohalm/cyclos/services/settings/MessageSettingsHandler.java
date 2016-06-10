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

import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.entities.settings.MessageSettings.MessageSettingsEnum;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.MessageSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.MessageSettingsEvent;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Message settings handler
 * @author luis
 */
public class MessageSettingsHandler extends BaseSettingsHandler<MessageSettings, MessageSettingsChangeListener> {

    public static final int MAX_SUBJECT_SIZE = 400;
    public static final int MAX_BODY_SIZE    = 4000;
    public static final int MAX_SMS_SIZE     = 256;

    protected MessageSettingsHandler() {
        super(Setting.Type.MESSAGE, MessageSettings.class);
    }

    @Override
    protected Map<String, Converter<?>> createConverters() {
        final Map<String, Converter<?>> messageConverters = new LinkedHashMap<String, Converter<?>>();

        for (final MessageSettingsEnum messageSetting : MessageSettingsEnum.values()) {
            if (messageSetting.defaultSubject() != null) {
                messageConverters.put(messageSetting.subjectSettingName(), CoercionConverter.instance(String.class));
            }

            if (messageSetting.defaultMessage() != null) {
                messageConverters.put(messageSetting.messageSettingName(), CoercionConverter.instance(String.class));
            }

            if (messageSetting.defaultSms() != null) {
                messageConverters.put(messageSetting.smsSettingName(), CoercionConverter.instance(String.class));
            }
        }

        return messageConverters;
    }

    @Override
    protected Validator createValidator() {
        final Validator messageValidator = new Validator("settings.mail");
        for (final MessageSettingsEnum messageSetting : MessageSettingsEnum.values()) {
            if (messageSetting.defaultSubject() != null) {
                messageValidator.property(messageSetting.subjectSettingName()).required().maxLength(MAX_SUBJECT_SIZE);
            }
            if (messageSetting.defaultMessage() != null) {
                messageValidator.property(messageSetting.messageSettingName()).required().maxLength(MAX_BODY_SIZE);
            }

            if (messageSetting.defaultSms() != null) {
                messageValidator.property(messageSetting.smsSettingName()).required().maxLength(MAX_SMS_SIZE);
            }
        }
        return messageValidator;
    }

    @Override
    protected void notifyListener(final MessageSettingsChangeListener listener, final MessageSettings settings) {
        listener.onMessageSettingsUpdate(new MessageSettingsEvent(settings));
    }

}
