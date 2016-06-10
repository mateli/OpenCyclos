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
package nl.strohalm.cyclos.services.elements;

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordTypeQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for member record types
 * @author Jefferson Magno
 */
public interface MemberRecordTypeService extends Service {

    /**
     * Loads a member record type fetching the specified relationships
     * @param id Id of the member record type to be loaded
     * @param fetch array of relationships to be fetched
     * @return The loaded reference
     */
    MemberRecordType load(Long id, Relationship... fetch);

    /**
     * Removes the specified member record types
     * @return The number of member record types removed
     * @throws PermissionDeniedException
     */
    int remove(Long... id) throws PermissionDeniedException;

    /**
     * Saves the given member record type
     * @param memberRecordType member record type to be saved
     * @return The member record type saved
     * @throws PermissionDeniedException
     */
    MemberRecordType save(MemberRecordType memberRecordType) throws PermissionDeniedException;

    /**
     * Searches for member record types
     */
    List<MemberRecordType> search(MemberRecordTypeQuery query);

    /**
     * Validates the specified member record type
     * @param memberRecordType member record type to be validated
     * @throws ValidationException if validation fails
     */
    void validate(MemberRecordType memberRecordType) throws ValidationException;

}
