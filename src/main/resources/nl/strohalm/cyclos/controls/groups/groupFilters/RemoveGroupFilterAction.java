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
package nl.strohalm.cyclos.controls.groups.groupFilters;

import java.io.File;
import java.util.Collection;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.CustomizationHelper;

import org.apache.struts.action.ActionForward;

public class RemoveGroupFilterAction extends BaseAction {

    private GroupFilterService  groupFilterService;
    private CustomizationHelper customizationHelper;

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveGroupFilterForm form = context.getForm();

        // Remove the group filter
        final long id = form.getGroupFilterId();
        final GroupFilter groupFilter = groupFilterService.load(id, GroupFilter.Relationships.CUSTOMIZED_FILES);
        final Collection<CustomizedFile> customizedFiles = groupFilter.getCustomizedFiles();
        groupFilterService.remove(id);

        // Remove the physical customized files for the group
        for (final CustomizedFile customizedFile : customizedFiles) {
            final File physicalFile = customizationHelper.customizedFileOf(customizedFile);
            customizationHelper.deleteFile(physicalFile);
        }

        context.sendMessage("groupFilter.removed");
        return context.getSuccessForward();
    }

}
