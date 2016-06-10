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
package nl.strohalm.cyclos.controls.ads.categories;

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.ads.AdCategoryService;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to import ad categories from a XML file
 * @author luis
 */
public class ImportAdCategoriesAction extends BaseAction {

    private AdCategoryService adCategoryService;

    @Inject
    public void setAdCategoryService(final AdCategoryService adCategoryService) {
        this.adCategoryService = adCategoryService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ImportAdCategoriesForm form = context.getForm();
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final FormFile upload = form.getUpload();
        try {
            final List<String> lines = IOUtils.readLines(upload.getInputStream(), localSettings.getCharset());
            final String xml = StringUtils.join(lines.iterator(), '\n');
            adCategoryService.importFromXml(xml);
            context.sendMessage("adCategory.imported");
        } catch (final PermissionDeniedException e) {
            // Rethrow when permission denied
            throw e;
        } catch (final Exception e) {
            context.sendMessage("adCategory.error.importing");
        } finally {
            upload.destroy();
        }
        return context.getSuccessForward();
    }

}
