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
package nl.strohalm.cyclos.dao.customizations;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessage;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessageQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Interface for message DAO
 * @author rafael
 */
public interface TranslationMessageDAO extends BaseDAO<TranslationMessage>, InsertableDAO<TranslationMessage>, UpdatableDAO<TranslationMessage>, DeletableDAO<TranslationMessage> {

    /**
     * Deletes all messages
     */
    int deleteAll() throws DaoException;

    /**
     * Returns an iterator with all existing keys
     */
    Iterator<String> listAllKeys() throws DaoException;

    /**
     * Lists the translation messages as properties
     */
    Properties listAsProperties();

    /**
     * Returns a list with all translation messages' data, in an array with id, key and value
     */
    Iterator<Object[]> listData() throws DaoException;

    /**
     * Loads the message based on its name, the key that identifies it. Note that <code>key</code> is case sensitive. If no such message can be found,
     * returns null. If any exception is thrown by the underlying implementation it should be wrapped by DaoException.
     * 
     * @param key Case sensitive key
     * @throws EntityNotFoundException When the given key does not exist
     */
    TranslationMessage load(String key) throws EntityNotFoundException, DaoException;

    /**
     * Searches for messages, ordering by key. If no message can be found, returns an empty list. If any exception is thrown by the underlying
     * implementation it should be wrapped by DaoException.
     */
    List<TranslationMessage> search(TranslationMessageQuery query) throws DaoException;
}
