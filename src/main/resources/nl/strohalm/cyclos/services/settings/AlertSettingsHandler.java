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

import nl.strohalm.cyclos.entities.settings.AlertSettings;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.AlertSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.AlertSettingsEvent;
import nl.strohalm.cyclos.utils.TimePeriod.Field;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Alert settings handler
 * @author luis
 */
public class AlertSettingsHandler extends BaseSettingsHandler<AlertSettings, AlertSettingsChangeListener> {

    protected AlertSettingsHandler() {
        super(Setting.Type.ALERT, AlertSettings.class);
    }

    @Override
    protected Map<String, Converter<?>> createConverters() {
        final Map<String, Converter<?>> alertConverters = new LinkedHashMap<String, Converter<?>>();
        alertConverters.put("givenVeryBadRefs", CoercionConverter.instance(Integer.TYPE));
        alertConverters.put("receivedVeryBadRefs", CoercionConverter.instance(Integer.TYPE));
        alertConverters.put("idleInvoiceExpiration.number", CoercionConverter.instance(Integer.class));
        alertConverters.put("idleInvoiceExpiration.field", CoercionConverter.instance(Field.class));
        alertConverters.put("amountDeniedInvoices", CoercionConverter.instance(Integer.TYPE));
        alertConverters.put("amountIncorrectLogin", CoercionConverter.instance(Integer.TYPE));
        return alertConverters;
    }

    @Override
    protected Validator createValidator() {
        final Validator alertValidator = new Validator("settings.alert");
        alertValidator.property("givenVeryBadRefs").between(0, 999);
        alertValidator.property("receivedVeryBadRefs").between(0, 999);
        alertValidator.property("idleInvoiceExpiration.number").between(0, 999);
        alertValidator.property("idleInvoiceExpiration.field").required();
        alertValidator.property("amountDeniedInvoices").between(0, 999);
        alertValidator.property("amountIncorrectLogin").between(0, 999);
        return alertValidator;
    }

    @Override
    protected void notifyListener(final AlertSettingsChangeListener listener, final AlertSettings settings) {
        listener.onAlertSettingsUpdate(new AlertSettingsEvent(settings));
    }
}
