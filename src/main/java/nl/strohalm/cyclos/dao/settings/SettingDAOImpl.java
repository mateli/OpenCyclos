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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.Setting.Type;
import nl.strohalm.cyclos.setup.CreateBasicData;
import nl.strohalm.cyclos.setup.Setup;

/**
 * Implementation class for settings DAO
 * @author rafael
 * @author Ivan "Fireblade" Diana
 */
public class SettingDAOImpl extends BaseDAOImpl<Setting> implements SettingDAO {

    private Properties cyclosProperties;

    public SettingDAOImpl() {
        super(Setting.class);
    }

    public void deleteByType(final Type... types) {
        if (types == null || types.length == 0) {
            return;
        }
        bulkUpdate("delete from " + getEntityType().getName() + " e where e.type in (:types)", Collections.singletonMap("types", Arrays.asList(types)));
    }

    public void importNew(final Locale locale) {
        CreateBasicData.createSettings(getSession(), Setup.getResourceBundle(locale), locale, cyclosProperties);
    }

    public List<Setting> listByType(final Type type) {
        final Map<String, ?> params = Collections.singletonMap("type", type);
        return list("from " + getEntityType().getName() + " e where e.type = :type", params);
    }

    public Setting load(final Type type, final String name) throws EntityNotFoundException, DaoException {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("name", name);
        final Setting setting = uniqueResult("from " + getEntityType().getName() + " e where e.type = :type and e.name = :name", params);
        if (setting == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        return setting;
    }

    public void setCyclosProperties(final Properties cyclosProperties) {
        this.cyclosProperties = cyclosProperties;
    }

}
