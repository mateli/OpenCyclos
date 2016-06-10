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
package nl.strohalm.cyclos.dao.settings;

import java.util.List;
import java.util.Locale;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.Setting.Type;

/**
 * Interface for setting DAO
 * @author rafael
 */
public interface SettingDAO extends BaseDAO<Setting>, InsertableDAO<Setting>, UpdatableDAO<Setting>, DeletableDAO<Setting> {

    /**
     * Delete all settings for the given types
     */
    void deleteByType(Type... types);

    /**
     * Import new setting from the Setup resource bundle, using the given locale
     * @param locale
     */
    void importNew(Locale locale);

    /**
     * Returns a list of all settings of the given type. If no setting can be found, returns an empty List. If any exception is thrown by the
     * underlying implementation, wrapped it in a DaoException.
     * 
     * @throws DaoException
     */
    List<Setting> listByType(Setting.Type type) throws DaoException;

    /**
     * Load a setting using it's type and name. If any exception is thrown by the underlying implementation, wrapped it in a DaoException.
     * 
     * @throws EntityNotFoundException The given setting does not exist
     * @throws DaoException
     */
    Setting load(Setting.Type type, String name) throws EntityNotFoundException, DaoException;
}
