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
package nl.strohalm.cyclos.controls.members;

import java.util.List;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.AdminQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;

/**
 * Searches admins and returns the list as an JSON
 * @author Jefferson Magno
 */
public class SearchAdminsAjaxAction extends BaseAjaxAction {

    private DataBinder<?> dataBinder;

    public DataBinder<?> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = BeanCollectionBinder.instance(DataBinderHelper.simpleElementBinder());
        }
        return dataBinder;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final SearchAdminsAjaxForm form = context.getForm();
        final AdminGroup adminGroup = groupService.load(context.getGroup().getId(), AdminGroup.Relationships.VIEW_CONNECTED_ADMINS_OF);
        final AdminQuery adminQuery = new AdminQuery();
        adminQuery.setName(form.getName());
        adminQuery.setUsername(form.getUsername());
        adminQuery.setGroups(adminGroup.getViewConnectedAdminsOf());
        adminQuery.fetch(Element.Relationships.USER);
        adminQuery.limitResults(localSettings.getMaxAjaxResults());
        final List<? extends Element> admins = elementService.search(adminQuery);
        final String json = getDataBinder().readAsString(admins);
        responseHelper.writeJSON(context.getResponse(), json);
    }

}
