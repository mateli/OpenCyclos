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
package nl.strohalm.cyclos.controls.groups.customizedFiles;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a customized file for a group
 * @author luis
 */
public class EditGroupCustomizedFileAction extends BaseFormAction {

    private CustomizedFileService      customizedFileService;
    private DataBinder<CustomizedFile> memberDataBinder;
    private DataBinder<CustomizedFile> adminDataBinder;
    private CustomizationHelper        customizationHelper;

    public DataBinder<CustomizedFile> getAdminDataBinder() {
        if (adminDataBinder == null) {
            final BeanBinder<CustomizedFile> binder = getCommonBinderElements();
            binder.registerBinder("contents", PropertyBinder.instance(String.class, "contents", CoercionConverter.instance(String.class)));
            adminDataBinder = binder;
        }
        return adminDataBinder;
    }

    public CustomizedFileService getCustomizedFileService() {
        return customizedFileService;
    }

    public DataBinder<CustomizedFile> getMemberDataBinder() {
        if (memberDataBinder == null) {
            final BeanBinder<CustomizedFile> binder = getCommonBinderElements();
            binder.registerBinder("contents", PropertyBinder.instance(String.class, "contents", HtmlConverter.instance()));
            memberDataBinder = binder;
        }
        return memberDataBinder;
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
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditGroupCustomizedFileForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        DataBinder<CustomizedFile> dataBinder;
        if (context.isAdmin()) {
            dataBinder = getAdminDataBinder();
        } else {
            dataBinder = getMemberDataBinder();
        }
        CustomizedFile file = dataBinder.readFromString(form.getFile());
        Group group = file.getGroup();
        // Ensure the file has a group
        if (group == null) {
            throw new ValidationException();
        } else {
            group = groupService.load(group.getId());
            file.setGroup(group);
        }

        final boolean isInsert = file.isTransient();
        if (group instanceof OperatorGroup) {
            file.setType(CustomizedFile.Type.STATIC_FILE);
        }
        file = customizedFileService.save(file);

        // Physically update the file
        final File physicalFile = customizationHelper.customizedFileOf(file);
        customizationHelper.updateFile(physicalFile, file);

        context.sendMessage(isInsert ? "group.customizedFiles.customized" : "group.customizedFiles.modified");
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("fileId", file.getId());
        params.put("groupId", group.getId());
        return ActionHelper.redirectWithParams(request, context.getSuccessForward(), params);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditGroupCustomizedFileForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();

        boolean editable = false;

        // Retrieve the group
        final long groupId = form.getGroupId();
        if (groupId <= 0L) {
            throw new ValidationException();
        }
        final Group group = groupService.load(groupId);

        final long id = form.getFileId();
        final boolean isInsert = id <= 0L;
        CustomizedFile file;
        if (isInsert) {
            file = new CustomizedFile();
            file.setGroup(group);
            // Prepare the possible types
            request.setAttribute("types", Arrays.asList(CustomizedFile.Type.STATIC_FILE, CustomizedFile.Type.STYLE));
            editable = true;
        } else {
            // Retrieve the file
            file = customizedFileService.load(id);
            if (file.getGroup() == null || !file.getGroup().equals(group)) {
                // Wrong group passed
                throw new ValidationException();
            }
            editable = customizedFileService.canViewOrManageInGroup(group);

        }
        request.setAttribute("file", file);
        DataBinder<CustomizedFile> dataBinder;
        if (context.isAdmin()) {
            dataBinder = getAdminDataBinder();
        } else {
            dataBinder = getMemberDataBinder();
        }
        dataBinder.writeAsString(form.getFile(), file);
        request.setAttribute("group", group);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editable", editable);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditGroupCustomizedFileForm form = context.getForm();
        DataBinder<CustomizedFile> dataBinder;
        if (context.isAdmin()) {
            dataBinder = getAdminDataBinder();
        } else {
            dataBinder = getMemberDataBinder();
        }
        final CustomizedFile file = dataBinder.readFromString(form.getFile());
        if (context.isMember()) {
            file.setType(CustomizedFile.Type.STATIC_FILE);
        }
        customizedFileService.validate(file);
    }

    private BeanBinder<CustomizedFile> getCommonBinderElements() {
        final BeanBinder<CustomizedFile> binder = BeanBinder.instance(CustomizedFile.class);
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("type", PropertyBinder.instance(CustomizedFile.Type.class, "type"));
        binder.registerBinder("group", PropertyBinder.instance(Group.class, "group", ReferenceConverter.instance(Group.class)));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));

        return binder;
    }
}
