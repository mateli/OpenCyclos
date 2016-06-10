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
package nl.strohalm.cyclos.dao.members;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Member;

/**
 * Data access object interface for contact
 * @author rafael
 */
public interface ContactDAO extends BaseDAO<Contact>, InsertableDAO<Contact>, UpdatableDAO<Contact>, DeletableDAO<Contact> {

    /**
     * Returns a list of all contacts of the given member, fetching the related member (contact) and ordering by its username. If such member does not
     * contain any contacts, returns an empty list. If any exception is thrown by the underlying implementation it should be wrapped by DaoException.
     * 
     * @throws DaoException
     */
    public List<Contact> listByMember(Member owner) throws DaoException;

    /**
     * Loads a contact by owner and contact members
     */
    public Contact load(Member owner, Member contact, Relationship... fetch);

}
