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

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;

/**
 * Local interface. It must be used only from other services.
 */
public interface CustomizedFileServiceLocal extends CustomizedFileService {

    /**
     * Returns true if the logged user can manage system customized files.
     * @return
     */
    public boolean canManageSystemCustomizedFiles();

    /**
     * Returns true if the logged user can view system customized files.
     * @return
     */
    public boolean canViewSystemCustomizedFiles();

    /**
     * Loads a system-wide customized file by type and name
     */
    CustomizedFile load(CustomizedFile.Type type, String name, Relationship... fetch);

    /**
     * Notifies the new version of the customized file by creating/sending a system alert.
     * @param alertType can logically be one of:
     * <ul>
     * <li>SystemAlert.Alerts.NEW_VERSION_OF_APPLICATION_PAGE
     * <li>SystemAlert.Alerts.NEW_VERSION_OF_HELP_FILE
     * <li>SystemAlert.Alerts.NEW_VERSION_OF_STATIC_FILE
     * </ul>
     * However, the method doesn't check this.
     * @param customizedFile
     */
    void notifyNewVersion(SystemAlert.Alerts alertType, CustomizedFile customizedFile);

}
