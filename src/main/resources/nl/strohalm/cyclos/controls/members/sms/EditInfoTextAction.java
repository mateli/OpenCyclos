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
package nl.strohalm.cyclos.controls.members.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.infotexts.InfoText;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.infotexts.InfoTextService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.SetConverter;

import org.apache.struts.action.ActionForward;

public class EditInfoTextAction extends BaseFormAction {
    private DataBinder<InfoText> dataBinder;
    private InfoTextService      infoTextService;

    @Inject
    public void setInfoTextService(final InfoTextService service) {
        infoTextService = service;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditInfoTextForm form = context.getForm();
        final InfoText infoText = getDataBinder().readFromString(form.getValues());
        final boolean isInsert = infoText.getId() == null;

        infoTextService.save(infoText);

        context.sendMessage(isInsert ? "infoText.inserted" : "infoText.modified");

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("infoTextId", infoText.getId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditInfoTextForm form = context.getForm();
        if (form.getInfoTextId() != null) {
            final InfoText infoText = infoTextService.load(form.getInfoTextId());
            context.getRequest().setAttribute("currentInfoText", infoText);
        }
        context.getRequest().setAttribute("hasManagePermissions", permissionService.hasPermission(AdminSystemPermission.INFO_TEXTS_MANAGE));
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditInfoTextForm form = context.getForm();
        final InfoText infoText = getDataBinder().readFromString(form.getValues());
        infoTextService.validate(infoText);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private DataBinder<InfoText> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<InfoText> binder = BeanBinder.instance(InfoText.class);

            final LocalSettings settings = settingsService.getLocalSettings();
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("validity", DataBinderHelper.rawPeriodBinder(settings, "validity"));
            binder.registerBinder("aliases", PropertyBinder.instance(TreeSet.class, "aliases", new SetConverter(TreeSet.class, ",")));
            binder.registerBinder("subject", PropertyBinder.instance(String.class, "subject"));
            binder.registerBinder("body", PropertyBinder.instance(String.class, "body"));
            binder.registerBinder("enabled", PropertyBinder.instance(Boolean.TYPE, "enabled"));
            dataBinder = binder;
        }
        return dataBinder;
    }
}
