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
package nl.strohalm.cyclos.controls.groups.groupFilters.customizedFiles;

import java.io.File;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to stop customizing a file for a given group filter
 * @author luis
 */
public class StopCustomizingGroupFilterFileAction extends BaseAction {

    private CustomizedFileService customizedFileService;
    private CustomizationHelper   customizationHelper;

    public CustomizedFileService getCustomizedFileService() {
        return customizedFileService;
    }

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Inject
    public void setCustomizedFileService(final CustomizedFileService customizedFileService) {
        this.customizedFileService = customizedFileService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final StopCustomizingGroupFilterFileForm form = context.getForm();
        final long id = form.getFileId();
        final long groupFilterId = form.getGroupFilterId();
        if (id <= 0L || groupFilterId <= 0L) {
            throw new ValidationException();
        }
        final CustomizedFile file = customizedFileService.load(id);
        if (file.getGroupFilter() == null || !file.getGroupFilter().getId().equals(groupFilterId)) {
            throw new ValidationException();
        }
        customizedFileService.stopCustomizing(file);
        context.sendMessage("groupFilter.customizedFiles.removed");

        // Remove the physical file
        final File physicalFile = customizationHelper.customizedFileOf(file);
        customizationHelper.deleteFile(physicalFile);

        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "groupFilterId", groupFilterId);
    }

}
