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

import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.SettingsChangeListener;

/**
 * Handles details for an specific settings type
 * @author luis
 */
public interface SettingsHandler<T, L extends SettingsChangeListener> {

    /**
     * Adds a settings listener
     */
    void addListener(L listener);

    /**
     * Returns the settings bean
     */
    T get();

    /**
     * Import settings from a Map of strings, returning the new bean
     */
    T importFrom(Map<String, String> values);

    /**
     * Returns a list of settings of the given type
     */
    List<Setting> listSettings();

    /**
     * Reloads the settings from database
     */
    void refresh();

    /**
     * Removes a settings listener
     */
    void removeListener(L listener);

    /**
     * Updates the settings bean
     */
    T update(T newSettings);

    /**
     * Validate the settings bean
     */
    void validate(T settings);
}
