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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.settings.LogSettings;
import nl.strohalm.cyclos.entities.settings.LogSettings.AccountFeeLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.ScheduledTaskLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.TraceLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.TransactionLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.WebServiceLevel;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.FileUnits;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

/**
 * Action used to edit log settings
 * @author luis
 */
public class EditLogSettingsAction extends BaseFormAction {

    private DataBinder<LogSettings> dataBinder;

    public DataBinder<LogSettings> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<LogSettings> binder = BeanBinder.instance(LogSettings.class);
            binder.registerBinder("traceLevel", PropertyBinder.instance(TraceLevel.class, "traceLevel"));
            binder.registerBinder("traceFile", PropertyBinder.instance(String.class, "traceFile"));
            binder.registerBinder("traceWritesOnly", PropertyBinder.instance(Boolean.TYPE, "traceWritesOnly"));
            binder.registerBinder("webServiceLevel", PropertyBinder.instance(WebServiceLevel.class, "webServiceLevel"));
            binder.registerBinder("webServiceFile", PropertyBinder.instance(String.class, "webServiceFile"));
            binder.registerBinder("restLevel", PropertyBinder.instance(WebServiceLevel.class, "restLevel"));
            binder.registerBinder("restFile", PropertyBinder.instance(String.class, "restFile"));
            binder.registerBinder("transactionLevel", PropertyBinder.instance(TransactionLevel.class, "transactionLevel"));
            binder.registerBinder("transactionFile", PropertyBinder.instance(String.class, "transactionFile"));
            binder.registerBinder("accountFeeLevel", PropertyBinder.instance(AccountFeeLevel.class, "accountFeeLevel"));
            binder.registerBinder("accountFeeFile", PropertyBinder.instance(String.class, "accountFeeFile"));
            binder.registerBinder("scheduledTaskLevel", PropertyBinder.instance(ScheduledTaskLevel.class, "scheduledTaskLevel"));
            binder.registerBinder("scheduledTaskFile", PropertyBinder.instance(String.class, "scheduledTaskFile"));
            binder.registerBinder("maxFilesPerLog", PropertyBinder.instance(Integer.TYPE, "maxFilesPerLog"));
            binder.registerBinder("maxLengthPerFile", PropertyBinder.instance(Integer.TYPE, "maxLengthPerFile"));
            binder.registerBinder("maxLengthPerFileUnits", PropertyBinder.instance(FileUnits.class, "maxLengthPerFileUnits"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditLogSettingsForm form = context.getForm();
        LogSettings settings = getDataBinder().readFromString(form.getSetting());
        settings = settingsService.save(settings);
        context.sendMessage("settings.log.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditLogSettingsForm form = context.getForm();
        final LogSettings settings = settingsService.getLogSettings();
        getDataBinder().writeAsString(form.getSetting(), settings);

        RequestHelper.storeEnum(request, TraceLevel.class, "traceLevels");
        RequestHelper.storeEnum(request, WebServiceLevel.class, "webServiceLevels");
        RequestHelper.storeEnum(request, TransactionLevel.class, "transactionLevels");
        RequestHelper.storeEnum(request, AccountFeeLevel.class, "accountFeeLevels");
        RequestHelper.storeEnum(request, ScheduledTaskLevel.class, "scheduledTaskLevels");
        RequestHelper.storeEnum(request, FileUnits.class, "fileUnits");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditLogSettingsForm form = context.getForm();
        final LogSettings settings = getDataBinder().readFromString(form.getSetting());
        settingsService.validate(settings);
    }
}
