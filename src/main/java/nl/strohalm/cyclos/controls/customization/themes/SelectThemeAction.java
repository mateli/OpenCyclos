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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.themes.Theme;
import nl.strohalm.cyclos.themes.ThemeHandler;
import nl.strohalm.cyclos.utils.RequestHelper;

/**
 * Action used to select a theme
 * @author luis
 */
public class SelectThemeAction extends BaseFormAction {

    private ThemeHandler themeHandler;

    @Inject
    public void setThemeHandler(final ThemeHandler themeHandler) {
        this.themeHandler = themeHandler;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final ThemeForm form = context.getForm();
        final String selected = form.getFilename();
        themeHandler.select(selected);
        context.sendMessage("theme.selected");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("themes", themeHandler.list());
        RequestHelper.storeEnum(request, Theme.Style.class, "styles");
    }
}
