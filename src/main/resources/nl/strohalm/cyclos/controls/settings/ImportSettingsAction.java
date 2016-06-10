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
package nl.strohalm.cyclos.controls.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.Setting.Type;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.settings.exceptions.SelectedSettingTypeNotInFileException;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to import settings from a XML file
 * @author Jefferson Magno
 */
public class ImportSettingsAction extends BaseAction {
    @Override
    @SuppressWarnings("unchecked")
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ManageSettingsForm form = context.getForm();
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final FormFile upload = form.getUpload();
        final Collection<Setting.Type> types = CoercionHelper.coerceCollection(Setting.Type.class, List.class, form.getType());
        try {
            final List<String> lines = IOUtils.readLines(upload.getInputStream(), localSettings.getCharset());
            final String xml = StringUtils.join(lines.iterator(), '\n');
            settingsService.importFromXml(xml, types);
            context.sendMessage("settings.imported");
        } catch (final PermissionDeniedException e) {
            // Rethrow when permission denied
            throw e;
        } catch (final SelectedSettingTypeNotInFileException e) {
            final List<Type> notImportedTypes = e.getNotImportedTypes();
            final List<String> names = new ArrayList<String>();
            for (final Type type : notImportedTypes) {
                names.add(context.message("settings.type." + type.name()));
            }
            context.sendMessage("settings.error.selectedSettingTypeNotInFile", StringUtils.join(names.iterator(), "\n"));
        } catch (final Exception e) {
            context.sendMessage("settings.error.importing");
        } finally {
            upload.destroy();
        }
        if (types.contains(Setting.Type.MAIL_TRANSLATION) || types.contains(Setting.Type.MESSAGE)) {
            return context.findForward("manageTranslationMessages");
        } else {
            return context.getSuccessForward();
        }
    }
}
