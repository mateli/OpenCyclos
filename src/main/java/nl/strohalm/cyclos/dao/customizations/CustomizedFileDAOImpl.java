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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile.Type;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation class for customized files
 * @author rafael
 */
public class CustomizedFileDAOImpl extends BaseDAOImpl<CustomizedFile> implements CustomizedFileDAO {

    public CustomizedFileDAOImpl() {
        super(CustomizedFile.class);
    }

    public CustomizedFile load(final Type type, final String name, final Relationship... fetch) throws DaoException {
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "f", fetch == null ? null : Arrays.asList(fetch));
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        HibernateHelper.addParameterToQuery(hql, namedParameters, "f.type", type);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "f.name", name);
        hql.append(" and f.group is null and f.groupFilter is null");
        final CustomizedFile file = uniqueResult(hql.toString(), namedParameters);
        if (file == null) {
            throw new EntityNotFoundException(CustomizedFile.class);
        }
        return file;
    }

    public List<CustomizedFile> search(final CustomizedFileQuery query) {
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "f", query.getFetch());
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        if (!query.isAll()) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "f.type", query.getType());
            final Group group = query.getGroup();
            if (group == null) {
                hql.append(" and f.group is null");
            } else {
                HibernateHelper.addParameterToQuery(hql, namedParameters, "f.group", group);
            }
            final GroupFilter groupFilter = query.getGroupFilter();
            if (groupFilter == null) {
                hql.append(" and f.groupFilter is null");
            } else {
                HibernateHelper.addParameterToQuery(hql, namedParameters, "f.groupFilter", groupFilter);
            }
        }
        HibernateHelper.appendOrder(hql, "f.type", "f.name");
        return list(query, hql.toString(), namedParameters);
    }

}
