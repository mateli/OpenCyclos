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
package nl.strohalm.cyclos.controls.customization.files;

import java.io.File;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.io.FileUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to stop customizing a file
 * @author luis
 */
public class StopCustomizingFileAction extends BaseAction {

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
        final StopCustomizingFileForm form = context.getForm();
        final long id = form.getFileId();
        if (id <= 0L) {
            throw new ValidationException();
        }

        final CustomizedFile file = customizedFileService.load(id);

        String originalContents = null;
        if (file.isConflict()) {
            originalContents = file.getNewContents();
        } else {
            originalContents = file.getOriginalContents();
        }
        customizedFileService.stopCustomizing(file);

        final CustomizedFile.Type type = file.getType();
        final File customized = customizationHelper.customizedFileOf(type, file.getName());
        final File original = customizationHelper.originalFileOf(type, file.getName());
        switch (type) {
            case APPLICATION_PAGE:
                customizationHelper.updateFile(original, System.currentTimeMillis(), originalContents);
                break;
            case STYLE:
                // For style sheet files, we must copy the original back.
                customized.getParentFile().mkdirs();
                customizationHelper.updateFile(customized, System.currentTimeMillis(), FileUtils.readFileToString(original));
                break;
            default:
                // Remove the physical file
                customizationHelper.deleteFile(customized);
                break;
        }

        context.sendMessage("customizedFile.removed");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "type", file.getType());
    }

}
