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

import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.messages.MessageCategoryQuery;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for Message Category operations like crud (create, remove, update, delete) search and list.
 * @author jeancarlo
 */
public interface MessageCategoryService extends Service {

    /**
     * Loads a Message Category by a given id.
     * @param id
     */
    MessageCategory load(Long id);

    /**
     * Remove Message Category using the ids, returning the number of the removed.
     * @param ids
     */
    int remove(Long... ids);

    /**
     * Save the Message Category.
     * @param messageCategory
     */
    MessageCategory save(MessageCategory messageCategory);

    /**
     * Search message categories
     * @param query parameters used in the search
     */
    List<MessageCategory> search(MessageCategoryQuery query);

    /**
     * Validate the Message Category
     * @param messageCategory
     * @throws ValidationException
     */
    void validate(MessageCategory messageCategory) throws ValidationException;

}
