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
package nl.strohalm.cyclos.controls.customization.translationMessages;

import java.io.InputStream;
import java.util.Properties;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.services.customization.MessageImportType;
import nl.strohalm.cyclos.services.customization.TranslationMessageService;
import nl.strohalm.cyclos.utils.PropertiesHelper;

import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to import messages from a properties file
 * @author luis
 */
public class ImportTranslationMessagesAction extends BaseAction {

    private TranslationMessageService translationMessageService;

    @Inject
    public void setTranslationMessageService(final TranslationMessageService translationMessageService) {
        this.translationMessageService = translationMessageService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ImportTranslationMessagesForm form = context.getForm();

        final FormFile upload = form.getFileUpload();
        if (upload != null && upload.getFileSize() > 0) {
            final InputStream in = upload.getInputStream();
            try {
                final Properties properties = PropertiesHelper.loadFromStream(in);
                final MessageImportType importType = MessageImportType.valueOf(form.getImportType());
                translationMessageService.importFromProperties(properties, importType);
            } catch (final Exception e) {
                return context.sendError("translationMessage.import.error.reading");
            } finally {
                upload.destroy();
            }
        }

        context.sendMessage("translationMessage.imported");
        return context.getSuccessForward();
    }

}
