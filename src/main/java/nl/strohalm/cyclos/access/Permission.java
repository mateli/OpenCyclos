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
package nl.strohalm.cyclos.access;

import nl.strohalm.cyclos.entities.Relationship;

/**
 * Permissions can be of two types: LIST or BOOLEAN.
 * 
 * A BOOLEAN permission represents the allowance of making or not an action. e.g: a permission to manage static documents.
 * 
 * A LIST permission represents a collection of entities of the same type with which a group is allowed to work. This collection is associated to a group
 * linked by the {@link #relationship()} relationship. e.g: the collection of allowed static documents for a specific group of administrators.
 * 
 * If the {@link #relationship()} method returns null, the permission is of type BOOLEAN. Otherwise, the permission is of type LIST.
 * 
 */
public interface Permission {

    Module getModule();

    String getQualifiedName();

    String getValue();

    String name();

    /**
     * The relationship used to get the allowed values from a group for a permission of type list.<br>
     * A boolean permission will return null.
     */
    Relationship relationship();
}
