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

import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.AccessSettings.UsernameGeneration;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.AccessSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.AccessSettingsEvent;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Access settings handler
 * @author luis
 */
public class AccessSettingsHandler extends BaseSettingsHandler<AccessSettings, AccessSettingsChangeListener> {

    private GroupServiceLocal groupService;

    protected AccessSettingsHandler() {
        super(Setting.Type.ACCESS, AccessSettings.class);
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    @Override
    protected Map<String, Converter<?>> createConverters() {
        final Map<String, Converter<?>> accessConverters = new LinkedHashMap<String, Converter<?>>();
        accessConverters.put("virtualKeyboard", CoercionConverter.instance(Boolean.TYPE));
        accessConverters.put("virtualKeyboardTransactionPassword", CoercionConverter.instance(Boolean.TYPE));
        accessConverters.put("numericPassword", CoercionConverter.instance(Boolean.TYPE));
        accessConverters.put("allowOperatorLogin", CoercionConverter.instance(Boolean.TYPE));
        accessConverters.put("allowMultipleLogins", CoercionConverter.instance(Boolean.TYPE));
        accessConverters.put("usernameLength.min", CoercionConverter.instance(Integer.TYPE));
        accessConverters.put("usernameLength.max", CoercionConverter.instance(Integer.TYPE));
        accessConverters.put("adminTimeout.number", CoercionConverter.instance(Integer.class));
        accessConverters.put("adminTimeout.field", CoercionConverter.instance(Field.class));
        accessConverters.put("administrationWhitelist", CoercionConverter.instance(String.class));
        accessConverters.put("usernameGeneration", CoercionConverter.instance(UsernameGeneration.class));
        accessConverters.put("generatedUsernameLength", CoercionConverter.instance(Integer.TYPE));
        accessConverters.put("memberTimeout.number", CoercionConverter.instance(Integer.class));
        accessConverters.put("memberTimeout.field", CoercionConverter.instance(Field.class));
        accessConverters.put("poswebTimeout.number", CoercionConverter.instance(Integer.class));
        accessConverters.put("poswebTimeout.field", CoercionConverter.instance(Field.class));
        accessConverters.put("transactionPasswordChars", CoercionConverter.instance(String.class));
        accessConverters.put("usernameRegex", CoercionConverter.instance(String.class));
        return accessConverters;
    }

    @Override
    protected Validator createValidator() {
        final Validator accessValidator = new Validator("settings.access");
        accessValidator.property("virtualKeyboard").key("settings.access.virtualKeyboardLogin").add(new PropertyValidation() {
            private static final long serialVersionUID = 1503822395572865451L;

            @Override
            public ValidationError validate(final Object object, final Object property, final Object value) {
                final boolean used = Boolean.TRUE.equals(value);
                if (used) {
                    // If there are any groups which require special characters on passwords, we cannot activate the virtual password
                    if (groupService.hasGroupsWhichRequiresSpecialOnPassword()) {
                        return new ValidationError("settings.access.error.virtualKeyboard.groupsRequireSpecial");
                    }
                }
                return null;
            }
        });
        accessValidator.property("numericPassword").add(new PropertyValidation() {

            private static final long serialVersionUID = 1763257236548237123L;

            @Override
            public ValidationError validate(final Object object, final Object property, final Object value) {
                final boolean used = Boolean.TRUE.equals(value);
                if (used) {
                    // If there are any groups which require special characters on passwords, we cannot activate the virtual password
                    if (groupService.hasMemberGroupsWhichEnforcesCharactersOnPassword()) {
                        return new ValidationError("settings.access.error.numericPassword.groupsRequireLetters");
                    }
                }
                return null;
            }
        });
        accessValidator.property("usernameLength.min").key("settings.access.usernameLength").required().between(1, 10);
        accessValidator.property("usernameLength.max").key("settings.access.usernameLength").required().between(3, 30);
        accessValidator.property("adminTimeout.number").key("settings.access.adminTimeout").between(1, 999);
        accessValidator.property("adminTimeout.field").key("settings.access.adminTimeout").required();
        accessValidator.property("usernameGeneration").required();
        accessValidator.property("generatedUsernameLength").between(3, 30);
        accessValidator.property("memberTimeout.number").key("settings.access.memberTimeout").between(1, 999);
        accessValidator.property("memberTimeout.field").key("settings.access.memberTimeout").required();
        accessValidator.property("poswebTimeout.number").key("settings.access.poswebTimeout").between(1, 999);
        accessValidator.property("poswebTimeout.field").key("settings.access.poswebTimeout").required();
        accessValidator.property("transactionPasswordChars").required().length(3, 50);
        accessValidator.property("usernameRegex").required();
        return accessValidator;
    }

    @Override
    protected void notifyListener(final AccessSettingsChangeListener listener, final AccessSettings settings) {
        listener.onAccessSettingsUpdate(new AccessSettingsEvent(settings));
    }
}
