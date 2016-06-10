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
package nl.strohalm.cyclos.services.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.settings.SettingDAO;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.AlertSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LogSettings;
import nl.strohalm.cyclos.entities.settings.MailSettings;
import nl.strohalm.cyclos.entities.settings.MailTranslation;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.Setting.Type;
import nl.strohalm.cyclos.entities.settings.events.AccessSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.AlertSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LogSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.MailSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.MailTranslationChangeListener;
import nl.strohalm.cyclos.entities.settings.events.MessageSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.SettingsChangeListener;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.exceptions.SelectedSettingTypeNotInFileException;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.XmlHelper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Implementation class for settings service
 * @author luis
 * @author Jefferson Magno
 */
public class SettingsServiceImpl implements SettingsServiceLocal, InitializingService, InitializingBean {

    private static final String                                             ROOT_ELEMENT          = "cyclos-settings";
    private static final String                                             SETTINGS_ELEMENT      = "settings";
    private static final String                                             SETTING_ELEMENT       = "setting";
    private static final List<String>                                       IGNORED_SETTINGS      = Arrays.asList("applicationName", "language");

    private FetchServiceLocal                                               fetchService;
    private SettingDAO                                                      settingDao;

    // Handlers for each settings type
    private Map<Setting.Type, SettingsHandler<?, ?>>                        handlersMap;
    private SettingsHandler<AccessSettings, AccessSettingsChangeListener>   accessSettingsHandler;
    private SettingsHandler<AlertSettings, AlertSettingsChangeListener>     alertSettingsHandler;
    private SettingsHandler<LocalSettings, LocalSettingsChangeListener>     localSettingsHandler;
    private SettingsHandler<LogSettings, LogSettingsChangeListener>         logSettingsHandler;
    private SettingsHandler<MailSettings, MailSettingsChangeListener>       mailSettingsHandler;
    private SettingsHandler<MailTranslation, MailTranslationChangeListener> mailTranslationHandler;
    private SettingsHandler<MessageSettings, MessageSettingsChangeListener> messageSettingsHandler;
    private Set<SettingsChangeListener>                                     listenersPendingToAdd = new HashSet<SettingsChangeListener>();

    @Override
    public void addListener(final SettingsChangeListener listener) {
        // Before completing initialization, other components might want to register listeners, but handlers are not set yet.
        // So, we'll just add them after the initialization is complete
        if (listenersPendingToAdd != null) {
            listenersPendingToAdd.add(listener);
            return;
        }
        if (listener instanceof AccessSettingsChangeListener) {
            accessSettingsHandler.addListener((AccessSettingsChangeListener) listener);
        }
        if (listener instanceof AlertSettingsChangeListener) {
            alertSettingsHandler.addListener((AlertSettingsChangeListener) listener);
        }
        if (listener instanceof LocalSettingsChangeListener) {
            localSettingsHandler.addListener((LocalSettingsChangeListener) listener);
        }
        if (listener instanceof LogSettingsChangeListener) {
            logSettingsHandler.addListener((LogSettingsChangeListener) listener);
        }
        if (listener instanceof MailSettingsChangeListener) {
            mailSettingsHandler.addListener((MailSettingsChangeListener) listener);
        }
        if (listener instanceof MailTranslationChangeListener) {
            mailTranslationHandler.addListener((MailTranslationChangeListener) listener);
        }
        if (listener instanceof MessageSettingsChangeListener) {
            messageSettingsHandler.addListener((MessageSettingsChangeListener) listener);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Create a map with all handlers by type
        handlersMap = new HashMap<Setting.Type, SettingsHandler<?, ?>>();
        handlersMap.put(Setting.Type.ACCESS, accessSettingsHandler);
        handlersMap.put(Setting.Type.ALERT, alertSettingsHandler);
        handlersMap.put(Setting.Type.LOCAL, localSettingsHandler);
        handlersMap.put(Setting.Type.LOG, logSettingsHandler);
        handlersMap.put(Setting.Type.MAIL, mailSettingsHandler);
        handlersMap.put(Setting.Type.MAIL_TRANSLATION, mailTranslationHandler);
        handlersMap.put(Setting.Type.MESSAGE, messageSettingsHandler);

        // Add any listeners which were registered before the initialization was completed
        Set<SettingsChangeListener> listeners = listenersPendingToAdd;
        listenersPendingToAdd = null;
        for (SettingsChangeListener listener : listeners) {
            addListener(listener);
        }
    }

    @Override
    public String exportToXml(final Collection<Type> types) {
        final LocalSettings localSettings = getLocalSettings();
        final StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"").append(localSettings.getCharset()).append("\"?>\n");
        xml.append('<').append(ROOT_ELEMENT).append(">\n");
        for (final Type type : types) {
            appendSettingType(xml, type);
        }
        xml.append("</").append(ROOT_ELEMENT).append(">\n");
        return xml.toString();
    }

    @Override
    public AccessSettings getAccessSettings() {
        return accessSettingsHandler.get();
    }

    @Override
    public AlertSettings getAlertSettings() {
        return alertSettingsHandler.get();
    }

    @Override
    public LocalSettings getLocalSettings() {
        return localSettingsHandler.get();
    }

    @Override
    public LogSettings getLogSettings() {
        return logSettingsHandler.get();
    }

    @Override
    public MailSettings getMailSettings() {
        return mailSettingsHandler.get();
    }

    @Override
    public MailTranslation getMailTranslation() {
        return mailTranslationHandler.get();
    }

    @Override
    public MessageSettings getMessageSettings() {
        return messageSettingsHandler.get();
    }

    @Override
    public MemberCustomField getSmsCustomField() {
        final Long id = getLocalSettings().getSmsCustomFieldId();
        if (id == null) {
            return null;
        }
        final MemberCustomField reference = EntityHelper.reference(MemberCustomField.class, id);
        return fetchService.fetch(reference);
    }

    @Override
    public List<?> importFromXml(final String xml, final Collection<Setting.Type> types) {
        final Document doc = XmlHelper.readDocument(xml);
        final Element root = doc.getDocumentElement();
        final List<Element> settingTypesNodes = XmlHelper.getChilden(root, SETTINGS_ELEMENT);
        final Set<Setting.Type> typesImported = new HashSet<Setting.Type>();
        final List<Object> settings = new ArrayList<Object>();
        for (final Element settingTypeNode : settingTypesNodes) {
            final String settingTypeName = settingTypeNode.getAttribute("type");
            final Setting.Type type = CoercionHelper.coerce(Setting.Type.class, settingTypeName);
            if (!types.contains(type)) {
                continue;
            }
            final Map<String, String> values = new HashMap<String, String>();
            final List<Element> settingsNodes = XmlHelper.getChilden(settingTypeNode, SETTING_ELEMENT);
            for (final Element settingNode : settingsNodes) {
                final String settingName = settingNode.getAttribute("name");
                // Setting in ignore list, don't import it
                if (type == Setting.Type.LOCAL && IGNORED_SETTINGS.contains(settingName)) {
                    continue;
                }
                String settingValue;
                try {
                    settingValue = StringUtils.trimToNull(settingNode.getChildNodes().item(0).getNodeValue());
                } catch (final Exception e) {
                    settingValue = null;
                }
                values.put(settingName, settingValue);
            }
            final SettingsHandler<?, ?> settingsHandler = handlersMap.get(type);
            final Object setting = settingsHandler.importFrom(values);
            typesImported.add(type);
            settings.add(setting);
        }
        final List<Setting.Type> notImportedTypes = new ArrayList<Setting.Type>();
        for (final Setting.Type type : types) {
            if (!typesImported.contains(type)) {
                notImportedTypes.add(type);
            }
        }
        if (CollectionUtils.isNotEmpty(notImportedTypes)) {
            throw new SelectedSettingTypeNotInFileException(notImportedTypes);
        }

        return settings;
    }

    @Override
    public void initializeService() {
        importNew();
    }

    @Override
    public void reloadTranslation() {
        // Delete all mail and message translations
        settingDao.deleteByType(Setting.Type.MAIL_TRANSLATION, Setting.Type.MESSAGE);
        // Delete some local settings which are also 'translatable'
        for (final Setting setting : settingDao.listByType(Setting.Type.LOCAL)) {
            final String name = setting.getName();
            if ("applicationUsername".equals(name) || "chargebackDescription".equals(name)) {
                settingDao.delete(setting.getId());
            }
        }
        // Now import them all again, with the current language
        importNew();

        // Then, refresh the handler's state
        refreshTranslationRelatedHandlers();
    }

    @Override
    public void removeListener(final SettingsChangeListener listener) {
        if (listener instanceof AccessSettingsChangeListener) {
            accessSettingsHandler.removeListener((AccessSettingsChangeListener) listener);
        }
        if (listener instanceof AlertSettingsChangeListener) {
            alertSettingsHandler.removeListener((AlertSettingsChangeListener) listener);
        }
        if (listener instanceof LocalSettingsChangeListener) {
            localSettingsHandler.removeListener((LocalSettingsChangeListener) listener);
        }
        if (listener instanceof LogSettingsChangeListener) {
            logSettingsHandler.removeListener((LogSettingsChangeListener) listener);
        }
        if (listener instanceof MailSettingsChangeListener) {
            mailSettingsHandler.removeListener((MailSettingsChangeListener) listener);
        }
        if (listener instanceof MailTranslationChangeListener) {
            mailTranslationHandler.removeListener((MailTranslationChangeListener) listener);
        }
        if (listener instanceof MessageSettingsChangeListener) {
            messageSettingsHandler.removeListener((MessageSettingsChangeListener) listener);
        }
    }

    @Override
    public AccessSettings save(final AccessSettings settings) {
        return accessSettingsHandler.update(settings);
    }

    @Override
    public AlertSettings save(final AlertSettings settings) {
        return alertSettingsHandler.update(settings);
    }

    @Override
    public LocalSettings save(final LocalSettings settings) {
        return localSettingsHandler.update(settings);
    }

    @Override
    public LogSettings save(final LogSettings settings) {
        return logSettingsHandler.update(settings);
    }

    @Override
    public MailSettings save(final MailSettings settings) {
        return mailSettingsHandler.update(settings);
    }

    @Override
    public MailTranslation save(final MailTranslation settings) {
        return mailTranslationHandler.update(settings);
    }

    @Override
    public MessageSettings save(final MessageSettings settings) {
        return messageSettingsHandler.update(settings);
    }

    public void setAccessSettingsHandler(final SettingsHandler<AccessSettings, AccessSettingsChangeListener> accessSettingsHandler) {
        this.accessSettingsHandler = accessSettingsHandler;
    }

    public void setAlertSettingsHandler(final SettingsHandler<AlertSettings, AlertSettingsChangeListener> alertSettingsHandler) {
        this.alertSettingsHandler = alertSettingsHandler;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setLocalSettingsHandler(final SettingsHandler<LocalSettings, LocalSettingsChangeListener> localSettingsHandler) {
        this.localSettingsHandler = localSettingsHandler;
    }

    public void setLogSettingsHandler(final SettingsHandler<LogSettings, LogSettingsChangeListener> logSettingsHandler) {
        this.logSettingsHandler = logSettingsHandler;
    }

    public void setMailSettingsHandler(final SettingsHandler<MailSettings, MailSettingsChangeListener> mailSettingsHandler) {
        this.mailSettingsHandler = mailSettingsHandler;
    }

    public void setMailTranslationHandler(final SettingsHandler<MailTranslation, MailTranslationChangeListener> mailTranslationHandler) {
        this.mailTranslationHandler = mailTranslationHandler;
    }

    public void setMessageSettingsHandler(final SettingsHandler<MessageSettings, MessageSettingsChangeListener> messageSettingsHandler) {
        this.messageSettingsHandler = messageSettingsHandler;
    }

    public void setSettingDao(final SettingDAO settingDao) {
        this.settingDao = settingDao;
    }

    @Override
    public void validate(final AccessSettings settings) {
        accessSettingsHandler.validate(settings);
    }

    @Override
    public void validate(final AlertSettings settings) {
        alertSettingsHandler.validate(settings);
    }

    @Override
    public void validate(final LocalSettings settings) {
        localSettingsHandler.validate(settings);
    }

    @Override
    public void validate(final LogSettings settings) {
        logSettingsHandler.validate(settings);
    }

    @Override
    public void validate(final MailSettings settings) {
        mailSettingsHandler.validate(settings);
    }

    @Override
    public void validate(final MailTranslation settings) {
        mailTranslationHandler.validate(settings);
    }

    @Override
    public void validate(final MessageSettings settings) {
        messageSettingsHandler.validate(settings);
    }

    private void appendSetting(final StringBuilder xml, final Setting setting) {
        final String indent2Levels = StringUtils.repeat("    ", 2);
        final String indent3Levels = StringUtils.repeat("    ", 3);
        xml.append(indent2Levels).append(String.format("<setting name=\"%s\" >\n", setting.getName()));
        xml.append(indent3Levels).append(StringEscapeUtils.escapeXml(setting.getValue()));
        xml.append(indent2Levels).append("</setting>\n");
    }

    private void appendSettingType(final StringBuilder xml, final Setting.Type type) {
        final String indent = StringUtils.repeat("    ", 1);
        xml.append(String.format("%s<settings type=\"%s\" >\n", indent, type.getValue()));
        final SettingsHandler<?, ?> handler = handlersMap.get(type);
        final List<Setting> settings = handler.listSettings();
        for (final Setting setting : settings) {
            // Setting in ignore list, don't export it
            if (type == Setting.Type.LOCAL && IGNORED_SETTINGS.contains(setting.getName())) {
                continue;
            }
            appendSetting(xml, setting);
        }
        xml.append(indent).append("</settings>\n");
    }

    private void importNew() {
        settingDao.importNew(getLocalSettings().getLocale());
        refreshTranslationRelatedHandlers();
    }

    private void refreshTranslationRelatedHandlers() {
        localSettingsHandler.refresh();
        messageSettingsHandler.refresh();
        mailTranslationHandler.refresh();
    }

}
