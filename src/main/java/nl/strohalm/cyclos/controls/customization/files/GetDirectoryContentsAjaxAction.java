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
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

/**
 * Action used to return the directory contents
 * @author jefferson
 */
public class GetDirectoryContentsAjaxAction extends BaseAjaxAction {

    private DataBinder<Collection<File>> fileDirCollectionBinder;
    private CustomizationHelper          customizationHelper;

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        final HttpServletResponse response = context.getResponse();
        final GetDirectoryContentsAjaxForm form = context.getForm();
        final String path = form.getPath();
        final List<File> filesAndDirs = customizationHelper.getDirectoryContents(path);
        final String json = getFileDirBinder().readAsString(filesAndDirs);
        responseHelper.writeJSON(response, json);
    }

    private DataBinder<Collection<File>> getFileDirBinder() {
        if (fileDirCollectionBinder == null) {
            final BeanBinder<File> fileDirBinder = BeanBinder.instance(File.class);
            fileDirBinder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            fileDirBinder.registerBinder("directory", PropertyBinder.instance(Boolean.TYPE, "directory"));
            fileDirCollectionBinder = BeanCollectionBinder.instance(fileDirBinder);
        }
        return fileDirCollectionBinder;
    }

}
