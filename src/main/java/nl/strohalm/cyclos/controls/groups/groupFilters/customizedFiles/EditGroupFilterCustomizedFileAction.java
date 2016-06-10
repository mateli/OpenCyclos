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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a customized file for a group filter
 * @author luis
 */
public class EditGroupFilterCustomizedFileAction extends BaseFormAction {

    private CustomizedFileService      customizedFileService;
    private GroupFilterService         groupFilterService;
    private DataBinder<CustomizedFile> dataBinder;
    private CustomizationHelper        customizationHelper;

    public DataBinder<CustomizedFile> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<CustomizedFile> binder = BeanBinder.instance(CustomizedFile.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("type", PropertyBinder.instance(CustomizedFile.Type.class, "type"));
            binder.registerBinder("groupFilter", PropertyBinder.instance(GroupFilter.class, "groupFilter"));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("contents", PropertyBinder.instance(String.class, "contents", CoercionConverter.instance(String.class)));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Inject
    public void setCustomizedFileService(final CustomizedFileService customizedFileService) {
        this.customizedFileService = customizedFileService;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditGroupFilterCustomizedFileForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        CustomizedFile file = getDataBinder().readFromString(form.getFile());
        final GroupFilter groupFilter = groupFilterService.load(file.getGroupFilter().getId());
        // Ensure the file has a group filter
        if (groupFilter == null) {
            throw new ValidationException();
        }

        final boolean isInsert = file.isTransient();
        file = customizedFileService.save(file);

        // Physically update the file
        final File physicalFile = customizationHelper.customizedFileOf(file);
        customizationHelper.updateFile(physicalFile, file);

        context.sendMessage(isInsert ? "groupFilter.customizedFiles.customized" : "groupFilter.customizedFiles.modified");
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("fileId", file.getId());
        params.put("groupFilterId", groupFilter.getId());
        return ActionHelper.redirectWithParams(request, context.getSuccessForward(), params);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditGroupFilterCustomizedFileForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        final boolean editable = permissionService.hasPermission(AdminSystemPermission.GROUP_FILTERS_MANAGE_CUSTOMIZED_FILES);

        // Retrieve the group filter
        final long groupFilterId = form.getGroupFilterId();
        if (groupFilterId <= 0L) {
            throw new ValidationException();
        }
        final GroupFilter groupFilter = groupFilterService.load(groupFilterId);

        final long id = form.getFileId();
        final boolean isInsert = id <= 0L;
        CustomizedFile file;
        if (isInsert) {
            file = new CustomizedFile();
            file.setGroupFilter(groupFilter);
            // Prepare the possible types
            request.setAttribute("types", Arrays.asList(CustomizedFile.Type.STATIC_FILE, CustomizedFile.Type.STYLE));
        } else {
            // Retrieve the file
            file = customizedFileService.load(id);
            if (file.getGroupFilter() == null || !file.getGroupFilter().equals(groupFilter)) {
                // Wrong group filter passed
                throw new ValidationException();
            }
        }
        request.setAttribute("file", file);
        getDataBinder().writeAsString(form.getFile(), file);
        request.setAttribute("group", groupFilter);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", editable);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditGroupFilterCustomizedFileForm form = context.getForm();
        final CustomizedFile file = getDataBinder().readFromString(form.getFile());
        customizedFileService.validate(file);
    }
}
