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

import nl.strohalm.cyclos.entities.settings.LogSettings;
import nl.strohalm.cyclos.entities.settings.LogSettings.AccountFeeLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.ScheduledTaskLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.TraceLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.TransactionLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.WebServiceLevel;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.LogSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LogSettingsEvent;
import nl.strohalm.cyclos.utils.FileUnits;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Log settings handler
 * @author luis
 */
public class LogSettingsHandler extends BaseSettingsHandler<LogSettings, LogSettingsChangeListener> {

    protected LogSettingsHandler() {
        super(Setting.Type.LOG, LogSettings.class);
    }

    @Override
    protected Map<String, Converter<?>> createConverters() {
        final Map<String, Converter<?>> logConverters = new LinkedHashMap<String, Converter<?>>();
        logConverters.put("traceLevel", CoercionConverter.instance(TraceLevel.class));
        logConverters.put("traceFile", CoercionConverter.instance(String.class));
        logConverters.put("traceWritesOnly", CoercionConverter.instance(Boolean.TYPE));
        logConverters.put("webServiceLevel", CoercionConverter.instance(WebServiceLevel.class));
        logConverters.put("webServiceFile", CoercionConverter.instance(String.class));
        logConverters.put("restLevel", CoercionConverter.instance(WebServiceLevel.class));
        logConverters.put("restFile", CoercionConverter.instance(String.class));
        logConverters.put("transactionLevel", CoercionConverter.instance(TransactionLevel.class));
        logConverters.put("transactionFile", CoercionConverter.instance(String.class));
        logConverters.put("accountFeeLevel", CoercionConverter.instance(AccountFeeLevel.class));
        logConverters.put("accountFeeFile", CoercionConverter.instance(String.class));
        logConverters.put("scheduledTaskLevel", CoercionConverter.instance(ScheduledTaskLevel.class));
        logConverters.put("scheduledTaskFile", CoercionConverter.instance(String.class));
        logConverters.put("maxFilesPerLog", CoercionConverter.instance(Integer.TYPE));
        logConverters.put("maxLengthPerFile", CoercionConverter.instance(Integer.TYPE));
        logConverters.put("maxLengthPerFileUnits", CoercionConverter.instance(FileUnits.class));
        return logConverters;
    }

    @Override
    protected Validator createValidator() {
        final Validator logValidator = new Validator("settings.log");
        logValidator.property("traceLevel").required();
        logValidator.property("traceFile").required();
        logValidator.property("webServiceLevel").required();
        logValidator.property("webServiceFile").required();
        logValidator.property("restLevel").required();
        logValidator.property("restFile").required();
        logValidator.property("transactionLevel").required();
        logValidator.property("transactionFile").required();
        logValidator.property("accountFeeLevel").required();
        logValidator.property("accountFeeFile").required();
        logValidator.property("scheduledTaskLevel").required();
        logValidator.property("scheduledTaskFile").required();
        logValidator.property("maxFilesPerLog").required().positiveNonZero();
        logValidator.property("maxLengthPerFile").required().positiveNonZero();
        logValidator.property("maxLengthPerFileUnits").required();
        return logValidator;
    }

    @Override
    protected void notifyListener(final LogSettingsChangeListener listener, final LogSettings settings) {
        listener.onLogSettingsUpdate(new LogSettingsEvent(settings));
    }
}
