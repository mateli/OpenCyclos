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
package nl.strohalm.cyclos.controls.customization.themes;

import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.themes.Theme;
import nl.strohalm.cyclos.themes.ThemeHandler;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;

import org.apache.struts.action.ActionForward;

public class ExportThemeAction extends BaseFormAction {

    private DataBinder<Theme> dataBinder;
    private ThemeHandler      themeHandler;

    public DataBinder<Theme> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<Theme> binder = BeanBinder.instance(Theme.class);
            binder.registerBinder("title", PropertyBinder.instance(String.class, "title"));
            binder.registerBinder("filename", PropertyBinder.instance(String.class, "filename"));
            binder.registerBinder("author", PropertyBinder.instance(String.class, "author"));
            binder.registerBinder("version", PropertyBinder.instance(String.class, "version"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("styles", SimpleCollectionBinder.instance(Theme.Style.class, "styles"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setThemeHandler(final ThemeHandler themeHandler) {
        this.themeHandler = themeHandler;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final HttpServletResponse response = context.getResponse();
        final ExportThemeForm form = context.getForm();
        final Theme theme = getDataBinder().readFromString(form.getTheme());
        String filename = theme.getFilename();
        if (!filename.endsWith(".theme")) {
            filename = filename + ".theme";
        }
        // Set the download headers and content
        responseHelper.setDownload(response, filename);
        response.setContentType("application/x-cyclos-theme");
        themeHandler.export(theme, response.getOutputStream());
        // Response is complete
        return null;
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ExportThemeForm form = context.getForm();
        final Theme theme = getDataBinder().readFromString(form.getTheme());
        themeHandler.validateForExport(theme);
    }

}
