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
package nl.strohalm.cyclos.struts;

import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;

import org.apache.struts.config.ControllerConfig;

/**
 * Controller configuration class
 * @author luis
 */
public class CyclosControllerConfig extends ControllerConfig implements LocalSettingsChangeListener {

    private static final long serialVersionUID = -3043431514296306666L;

    public void initialize(final LocalSettings localSettings) {
        update(localSettings);
    }

    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        update(event.getSource());
    }

    private void update(final LocalSettings settings) {
        maxFileSize = String.valueOf(settings.getMaxUploadBytes());
    }
}
