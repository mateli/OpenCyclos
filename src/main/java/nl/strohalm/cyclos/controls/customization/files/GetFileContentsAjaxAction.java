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

import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.services.customization.CustomizedFileService;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;

/**
 * Action used to return the file contents
 * @author luis
 */
public class GetFileContentsAjaxAction extends BaseAjaxAction {

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
    protected ContentType contentType() {
        return ContentType.TEXT;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final HttpServletResponse response = context.getResponse();
        final GetFileContentsAjaxForm form = context.getForm();
        CustomizedFile.Type type;
        Group group = null;
        GroupFilter groupFilter = null;
        String name;
        try {
            final String typeName = form.getType();
            if (StringUtils.isEmpty(typeName)) {
                type = CustomizedFile.Type.STATIC_FILE;
            } else {
                type = CustomizedFile.Type.valueOf(typeName);
            }
            name = form.getFileName();
            if (StringUtils.isEmpty(name)) {
                throw new Exception();
            }
            if (form.getGroupId() > 0L) {
                group = EntityHelper.reference(Group.class, form.getGroupId());
            }
            if (form.getGroupFilterId() > 0L) {
                groupFilter = EntityHelper.reference(GroupFilter.class, form.getGroupFilterId());
            }
        } catch (final Exception e) {
            throw new ValidationException();
        }
        final File file = customizationHelper.findFileOf(type, group, groupFilter, name);
        if (file.exists()) {
            responseHelper.writeFile(response, file);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
