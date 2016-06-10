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
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.ContactVO;

/**
 * Service interface for contacts (members and/or brokers)
 * @author luis
 */
public interface ContactService extends Service {
    /**
     * Returns the ContactVO for the given contactId
     * @param contactId the contact internal identifier
     * @param useFields if true, the member custom fields will be added
     * @param useImages if true, the member images will be added
     */
    ContactVO getContactVO(Long contactId, boolean useFields, boolean useImages);

    /**
     * Return ContactVOs for the given contacts
     * @param contacts
     * @param useFields if true, the member custom fields will be added
     * @param useImages if true, the member images will be added
     */
    List<ContactVO> getContactVOs(List<Contact> contacts, boolean useFields, boolean useImages);

    /**
     * Lists all contacts of the given member
     * @param owner The member
     * @return The contact list
     */
    List<Contact> list(Member owner);

    /**
     * Load the specified contact, fetching the specified relationships
     */
    Contact load(Long id, Relationship... fetch);

    /**
     * Loads a contact by owner and contact member. If the contact isn't found a NullPointerException is thrown.
     */
    Contact load(Member owner, Member contact, Relationship... fetch);

    /**
     * Loads a contact by owner and contact member. If the contact isn't found null ir returned.
     * @param owner
     * @param member
     * @param fetch
     * @return
     */
    Contact loadIfExists(Member owner, Member member, Relationship... fetch);

    /**
     * Removes the specified contacts
     * @param id List of contact ids to be removed
     * @return The number of contacts removed
     */
    int remove(Long... id);

    /**
     * Saves the given contact
     * @param contact The given contact
     * @return Saved contact
     */
    Contact save(Contact contact);

    /**
     * Validates the given contact. Both owner and contact are required and cannot be the same. Notes are not required, and it's max length depends on
     * the underlying database column
     */
    void validate(Contact contact) throws ValidationException;

}
