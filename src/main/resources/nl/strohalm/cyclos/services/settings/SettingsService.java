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

import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.AlertSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LogSettings;
import nl.strohalm.cyclos.entities.settings.MailSettings;
import nl.strohalm.cyclos.entities.settings.MailTranslation;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.SettingsChangeListener;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for application settings handling
 * @author luis
 */
public interface SettingsService extends Service {

    /**
     * Adds a local settings change listener
     */
    void addListener(SettingsChangeListener listener);

    /**
     * Exports settings into a xml string. For format, see {@link #importFromXml(String, Collection)}
     * @param types types of settings to be exported
     */
    String exportToXml(final Collection<Setting.Type> types);

    /**
     * Retrieve the access settings
     */
    AccessSettings getAccessSettings();

    /**
     * Retrieve the alert settings
     */
    AlertSettings getAlertSettings();

    /**
     * Retrieve the local settings
     */
    LocalSettings getLocalSettings();

    /**
     * Retrieve the log settings
     */
    LogSettings getLogSettings();

    /**
     * Retrieve the mail settings
     */
    MailSettings getMailSettings();

    /**
     * Retrieve the mail translation
     */
    MailTranslation getMailTranslation();

    /**
     * Retrieve the message settings
     */
    MessageSettings getMessageSettings();

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
    List<?> importFromXml(final String xml, final Collection<Setting.Type> types);

    /**
     * Recreates settings based on translation
     */
    void reloadTranslation();

    /**
     * Removes a local settings change listener
     */
    void removeListener(SettingsChangeListener listener);

    /**
     * Save the access settings
     */
    AccessSettings save(AccessSettings settings);

    /**
     * Save the alert settings
     */
    AlertSettings save(AlertSettings settings);

    /**
     * Save the local settings
     */
    LocalSettings save(LocalSettings settings);

    /**
     * Save the log settings
     */
    LogSettings save(LogSettings settings);

    /**
     * Save the mail settings
     */
    MailSettings save(MailSettings settings);

    /**
     * Save the mail translation
     */
    MailTranslation save(MailTranslation settings);

    /**
     * Save the message settings / notifications translation
     */
    MessageSettings save(MessageSettings settings);

    /**
     * Validate the access settings
     */
    void validate(AccessSettings settings);

    /**
     * Validate the alert settings
     */
    void validate(AlertSettings settings);

    /**
     * Validate the local settings
     */
    void validate(LocalSettings settings);

    /**
     * Validate the log settings
     */
    void validate(LogSettings settings);

    /**
     * Validate the mail settings
     */
    void validate(MailSettings settings);

    /**
     * Validate the mail translation
     */
    void validate(MailTranslation settings);

    /**
     * Validate the message settings
     */
    void validate(MessageSettings settings);
}
