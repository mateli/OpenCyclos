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

/**
 * Defines how message import will be handled
 * @author luis
 */
public enum MessageImportType {
    /**
     * Only new keys are imported from the properties file, leaving existing keys untouched
     */
    ONLY_NEW,

    /**
     * New keys and those that have empty keys
     */
    NEW_AND_EMPTY,

    /**
     * New and modified keys are imported from the properties file, leaving keys on database that are not on the properties file untouched
     */
    NEW_AND_MODIFIED,

    /**
     * The database state is made the same as the properties file - all keys not in file are removed and all keys on file are imported
     */
    REPLACE;
}
