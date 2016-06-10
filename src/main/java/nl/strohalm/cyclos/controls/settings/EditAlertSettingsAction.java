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
import nl.strohalm.cyclos.entities.settings.AlertSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

/**
 * Action used to edit the alert settings
 * @author luis
 */
public class EditAlertSettingsAction extends BaseFormAction {
    private DataBinder<AlertSettings> dataBinder;

    public DataBinder<AlertSettings> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<AlertSettings> binder = BeanBinder.instance(AlertSettings.class);
            binder.registerBinder("givenVeryBadRefs", PropertyBinder.instance(Integer.TYPE, "givenVeryBadRefs"));
            binder.registerBinder("receivedVeryBadRefs", PropertyBinder.instance(Integer.TYPE, "receivedVeryBadRefs"));
            binder.registerBinder("idleInvoiceExpiration", DataBinderHelper.timePeriodBinder("idleInvoiceExpiration"));
            binder.registerBinder("amountDeniedInvoices", PropertyBinder.instance(Integer.TYPE, "amountDeniedInvoices"));
            binder.registerBinder("amountIncorrectLogin", PropertyBinder.instance(Integer.TYPE, "amountIncorrectLogin"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditAlertSettingsForm form = context.getForm();
        AlertSettings settings = getDataBinder().readFromString(form.getSetting());
        settings = settingsService.save(settings);
        context.sendMessage("settings.alert.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditAlertSettingsForm form = context.getForm();
        getDataBinder().writeAsString(form.getSetting(), settingsService.getAlertSettings());
        request.setAttribute("timePeriodFields", Arrays.asList(TimePeriod.Field.DAYS, TimePeriod.Field.WEEKS, TimePeriod.Field.MONTHS));
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditAlertSettingsForm form = context.getForm();
        final AlertSettings settings = getDataBinder().readFromString(form.getSetting());
        settingsService.validate(settings);
    }

}
