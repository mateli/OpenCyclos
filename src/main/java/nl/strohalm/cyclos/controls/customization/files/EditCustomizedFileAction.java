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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.io.FileUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to customize a file
 * @author luis
 */
public class EditCustomizedFileAction extends BaseFormAction {

    private CustomizedFileService      customizedFileService;
    private DataBinder<CustomizedFile> dataBinder;
    private CustomizationHelper        customizationHelper;

    public CustomizedFileService getCustomizedFileService() {
        return customizedFileService;
    }

    public DataBinder<CustomizedFile> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<CustomizedFile> binder = BeanBinder.instance(CustomizedFile.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("type", PropertyBinder.instance(CustomizedFile.Type.class, "type"));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("contents", PropertyBinder.instance(String.class, "contents", CoercionConverter.instance(String.class)));
            binder.registerBinder("originalContents", PropertyBinder.instance(String.class, "originalContents", CoercionConverter.instance(String.class)));
            binder.registerBinder("newContents", PropertyBinder.instance(String.class, "newContents", CoercionConverter.instance(String.class)));

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

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditCustomizedFileForm form = context.getForm();
        CustomizedFile customizedFile = getDataBinder().readFromString(form.getFile());

        if (customizedFile.isConflict() && form.isResolveConflicts()) {
            customizedFile.setOriginalContents(customizedFile.getNewContents());
            customizedFile.setNewContents(null);
        }
        final File originalFile = customizationHelper.originalFileOf(customizedFile.getType(), customizedFile.getName());
        final String originalContents = FileUtils.readFileToString(originalFile);

        final File physicalFile = customizationHelper.customizedFileOf(customizedFile.getType(), customizedFile.getName());
        // FIXME If this code is really not needed, delete those comments
        // if (customizedFile.getType() == CustomizedFile.Type.APPLICATION_PAGE) {
        // physicalFile.delete();
        // }

        final boolean isInsert = customizedFile.getId() == null;
        if (isInsert) {
            customizedFile.setOriginalContents(originalContents);
        }
        customizedFile = customizedFileService.save(customizedFile);

        // Physically update the file
        customizationHelper.updateFile(physicalFile, customizedFile);

        context.sendMessage(isInsert ? "customizedFile.customized" : "customizedFile.modified");

        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "fileId", customizedFile.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditCustomizedFileForm form = context.getForm();
        final long id = form.getFileId();
        CustomizedFile file;
        if (id > 0L) {
            file = customizedFileService.load(id);
        } else {
            file = new CustomizedFile();
            CustomizedFile.Type type;
            try {
                type = CustomizedFile.Type.valueOf(form.getType());
            } catch (final Exception e) {
                throw new ValidationException();
            }
            file.setType(type);

            // Application pages shows a specific control to select a file, not a list of options
            if (type != CustomizedFile.Type.APPLICATION_PAGE) {
                request.setAttribute("filesNotYetCustomized", filesNotYetCustomized(type));
            }
        }
        getDataBinder().writeAsString(form.getFile(), file);
        request.setAttribute("file", file);
        request.setAttribute("type", file.getType().name());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditCustomizedFileForm form = context.getForm();
        final CustomizedFile file = getDataBinder().readFromString(form.getFile());
        customizedFileService.validate(file);
    }

    /**
     * Returns the files that were not yet customized
     */
    private List<String> filesNotYetCustomized(final CustomizedFile.Type type) {
        final CustomizedFileQuery query = new CustomizedFileQuery();
        query.setType(type);
        final List<CustomizedFile> customizedFiles = customizedFileService.search(query);
        return customizationHelper.onlyNotAlreadyCustomized(type, customizedFiles);
    }
}
