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
package nl.strohalm.cyclos.services.customization;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessage;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessageQuery;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link TranslationMessageService}
 * 
 * @author Rinke
 */
public class TranslationMessageServiceSecurity extends BaseServiceSecurity implements TranslationMessageService {

    private TranslationMessageServiceLocal translationMessageService;

    @Override
    public void addTranslationChangeListener(final TranslationChangeListener listener) {
        // Called by a web initialization - no permission check
        translationMessageService.addTranslationChangeListener(listener);
    }

    @Override
    public Properties exportAsProperties() {
        // called by ExportTranslationMessageAction
        checkFilePermission();
        return translationMessageService.exportAsProperties();
    }

    @Override
    public void importFromProperties(final Properties properties, final MessageImportType importType) {
        checkFilePermission();
        translationMessageService.importFromProperties(properties, importType);
    }

    @Override
    public TranslationMessage load(final Long id) {
        // called by EditTranslationMessage
        checkView();
        return translationMessageService.load(id);
    }

    @Override
    public Properties readFile(final Locale locale) {
        // No permission check
        return translationMessageService.readFile(locale);
    }

    @Override
    public int remove(final Long... ids) {
        checkManage();
        return translationMessageService.remove(ids);
    }

    @Override
    public TranslationMessage save(final TranslationMessage translationMessage) {
        checkManage();
        return translationMessageService.save(translationMessage);
    }

    @Override
    public List<TranslationMessage> search(final TranslationMessageQuery query) {
        checkView();
        return translationMessageService.search(query);
    }

    public void setTranslationMessageServiceLocal(final TranslationMessageServiceLocal translationMessageService) {
        this.translationMessageService = translationMessageService;
    }

    @Override
    public void validate(final TranslationMessage translationMessage) throws ValidationException {
        // no permission check on validation
        translationMessageService.validate(translationMessage);
    }

    private void checkFilePermission() {
        permissionService.permission()
                .admin(AdminSystemPermission.TRANSLATION_FILE)
                .check();
    }

    private void checkManage() {
        permissionService.permission()
                .admin(AdminSystemPermission.TRANSLATION_MANAGE)
                .check();
    }

    private void checkView() {
        permissionService.permission()
                .admin(AdminSystemPermission.TRANSLATION_VIEW)
                .check();
    }

}
