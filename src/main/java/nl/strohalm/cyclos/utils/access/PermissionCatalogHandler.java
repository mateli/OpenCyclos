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
package nl.strohalm.cyclos.utils.access;

import java.util.Set;

import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.utils.EntityVO;

/**
 * Handler for all multivalued group's permissions (those having a relationship not null, that is there are a list of possible values).
 * @author ameyer
 */
public interface PermissionCatalogHandler {
    /**
     * @return the group's current values for the specified permission
     */
    Set<EntityVO> currentValues(final Permission permission);

    /**
     * @return the possible values for the specified permission
     */
    Set<EntityVO> possibleValues(final Permission permission);
}
