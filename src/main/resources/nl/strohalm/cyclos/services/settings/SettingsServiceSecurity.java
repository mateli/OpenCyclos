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

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.AlertSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LogSettings;
import nl.strohalm.cyclos.entities.settings.MailSettings;
import nl.strohalm.cyclos.entities.settings.MailTranslation;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.SettingsChangeListener;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

/**
 * Security implementation for {@link SettingsService}
 * 
 * @author ameyer
 */
public class SettingsServiceSecurity extends BaseServiceSecurity implements SettingsService {

    private SettingsServiceLocal settingsService;

    @Override
    public void addListener(final SettingsChangeListener listener) {
        settingsService.addListener(listener);
    }

    @Override
    public String exportToXml(final Collection<Setting.Type> types) {
        permissionService.permission()
                .admin(AdminSystemPermission.SETTINGS_FILE)
                .check();
        return settingsService.exportToXml(types);
    }

    @Override
    public AccessSettings getAccessSettings() {
        return settingsService.getAccessSettings();
    }

    @Override
    public AlertSettings getAlertSettings() {
        return settingsService.getAlertSettings();
    }

    @Override
    public LocalSettings getLocalSettings() {
        return settingsService.getLocalSettings();
    }

    @Override
    public LogSettings getLogSettings() {
        return settingsService.getLogSettings();
    }

    @Override
    public MailSettings getMailSettings() {
        return settingsService.getMailSettings();
    }

    @Override
    public MailTranslation getMailTranslation() {
        return settingsService.getMailTranslation();
    }

    @Override
    public MessageSettings getMessageSettings() {
        return settingsService.getMessageSettings();
    }

    /**
     * Import settings from the given xml string. The expected format is:
     * 
     * <pre>
     * &lt;cyclos-settings&gt;
     *     &lt;settings type=&quot;...&quot;&gt;
     *         &lt;setting name=&quot;...&quot;&gt;
     *             value
     *         &lt;/setting&gt;
     *         &lt;setting name=&quot;...&quot;&gt;
     *             value
     *         &lt;/setting&gt;
     *     &lt;/settings;&gt;
     *     &lt;settings type=&quot;...&quot;&gt;
     *         &lt;setting name=&quot;...&quot;&gt;
     *             value
     *         &lt;/setting&gt;
     *         &lt;setting name=&quot;...&quot;&gt;
     *             value
     *         &lt;/setting&gt;
     *         &lt;setting name=&quot;...&quot;&gt;
     *             value
     *         &lt;/setting&gt;
     *     &lt;/settings;&gt;
     * &lt;/cyclos-settings&gt;
     * </pre>
     * 
     * @param types type of settings to import
     */
    @Override
    public List<?> importFromXml(final String xml, final Collection<Setting.Type> types) {
        permissionService.permission()
                .admin(AdminSystemPermission.SETTINGS_FILE)
                .check();
        return settingsService.importFromXml(xml, types);
    }

    @Override
    public void reloadTranslation() {
        permissionService.permission()
                .admin(AdminSystemPermission.SETTINGS_MANAGE_LOCAL)
                .check();
        settingsService.reloadTranslation();
    }

    @Override
    public void removeListener(final SettingsChangeListener listener) {
        settingsService.removeListener(listener);
    }

    @Override
    public AccessSettings save(final AccessSettings settings) {
        permissionService.permission()
                .admin(AdminSystemPermission.SETTINGS_MANAGE_ACCESS)
                .check();

        return settingsService.save(settings);
    }

    @Override
    public AlertSettings save(final AlertSettings settings) {
        permissionService.permission()
                .admin(AdminSystemPermission.SETTINGS_MANAGE_ALERT)
                .check();

        return settingsService.save(settings);
    }

    @Override
    public LocalSettings save(final LocalSettings settings) {
        permissionService.permission()
                .admin(AdminSystemPermission.SETTINGS_MANAGE_LOCAL)
                .check();

        return settingsService.save(settings);
    }

    @Override
    public LogSettings save(final LogSettings settings) {
        permissionService.permission()
                .admin(AdminSystemPermission.SETTINGS_MANAGE_LOG)
                .check();

        return settingsService.save(settings);
    }

    @Override
    public MailSettings save(final MailSettings settings) {
        permissionService.permission()
                .admin(AdminSystemPermission.SETTINGS_MANAGE_MAIL)
                .check();

        return settingsService.save(settings);
    }

    @Override
    public MailTranslation save(final MailTranslation settings) {
        permissionService.permission()
                .admin(AdminSystemPermission.TRANSLATION_MANAGE_MAIL_TRANSLATION)
                .check();

        return settingsService.save(settings);
    }

    @Override
    public MessageSettings save(final MessageSettings settings) {
        permissionService.permission()
                .admin(AdminSystemPermission.TRANSLATION_MANAGE_NOTIFICATION)
                .check();

        return settingsService.save(settings);
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final AccessSettings settings) {
        settingsService.validate(settings);
    }

    @Override
    public void validate(final AlertSettings settings) {
        settingsService.validate(settings);
    }

    @Override
    public void validate(final LocalSettings settings) {
        settingsService.validate(settings);
    }

    @Override
    public void validate(final LogSettings settings) {
        settingsService.validate(settings);
    }

    @Override
    public void validate(final MailSettings settings) {
        settingsService.validate(settings);
    }

    @Override
    public void validate(final MailTranslation settings) {
        settingsService.validate(settings);
    }

    @Override
    public void validate(final MessageSettings settings) {
        settingsService.validate(settings);
    }
}
