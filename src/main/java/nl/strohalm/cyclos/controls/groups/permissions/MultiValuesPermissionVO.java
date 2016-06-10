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
package nl.strohalm.cyclos.controls.groups.permissions;

import java.util.Collection;

import nl.strohalm.cyclos.utils.EntityVO;

public class MultiValuesPermissionVO {
    private PermissionCollectionProperty property;

    private Collection<EntityVO>         possibleValues;
    private Collection<EntityVO>         currentValues;

    public MultiValuesPermissionVO(final PermissionCollectionProperty property, final Collection<EntityVO> currentValues, final Collection<EntityVO> possibleValues) {
        super();
        this.property = property;
        this.possibleValues = possibleValues;
        this.currentValues = currentValues;
    }

    public String getCssClassName() {
        return property.cssClassName();
    }

    public Collection<EntityVO> getCurrentValues() {
        return currentValues;
    }

    public String getOnChangeListener() {
        return property.onChangeListener();
    }

    public Collection<EntityVO> getPossibleValues() {
        return possibleValues;
    }

    public String getProperty() {
        return property.name();
    }
}
