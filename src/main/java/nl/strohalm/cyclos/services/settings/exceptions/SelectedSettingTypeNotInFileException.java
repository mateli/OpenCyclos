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
package nl.strohalm.cyclos.services.settings.exceptions;

import java.util.List;

import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.exceptions.ApplicationException;

/**
 * This exception is thrown when the user selects to import settings of type that is not in the xml
 * @author Jefferson Magno
 */
public class SelectedSettingTypeNotInFileException extends ApplicationException {

    private static final long  serialVersionUID = -4731874174800937514L;
    private List<Setting.Type> notImportedTypes;

    public SelectedSettingTypeNotInFileException(final List<Setting.Type> notImportedTypes) {
        setNotImportedTypes(notImportedTypes);
    }

    public List<Setting.Type> getNotImportedTypes() {
        return notImportedTypes;
    }

    public void setNotImportedTypes(final List<Setting.Type> notImportedTypes) {
        this.notImportedTypes = notImportedTypes;
    }

}
