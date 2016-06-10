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
package nl.strohalm.cyclos.controls.groups;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a group
 * @author luis
 */
public class RemoveGroupAction extends BaseAction {
    private CustomizationHelper customizationHelper;

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveGroupForm form = context.getForm();
        final long id = form.getGroupId();
        if (id <= 0) {
            throw new ValidationException();
        }
        final Collection<File> toRemove = new ArrayList<File>();
        try {
            final Group group = groupService.load(id, Group.Relationships.CUSTOMIZED_FILES);
            final Collection<CustomizedFile> customizedFiles = group.getCustomizedFiles();
            // Before removing the group, get the customized files
            for (final CustomizedFile customizedFile : customizedFiles) {
                final File physicalFile = customizationHelper.customizedFileOf(customizedFile);
                toRemove.add(physicalFile);
            }
            groupService.remove(id);
            // Physically remove the customized files
            for (final File file : toRemove) {
                customizationHelper.deleteFile(file);
            }
            context.sendMessage("group.removed");
        } catch (final Exception e) {
            context.sendMessage("group.error.removing");
        }
        return context.getSuccessForward();
    }

}
