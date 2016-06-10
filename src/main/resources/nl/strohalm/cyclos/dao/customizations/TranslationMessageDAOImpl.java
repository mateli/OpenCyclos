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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessage;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessageQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.SortedProperties;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation class for message DAO
 * @author rafael
 */
public class TranslationMessageDAOImpl extends BaseDAOImpl<TranslationMessage> implements TranslationMessageDAO {

    public TranslationMessageDAOImpl() {
        super(TranslationMessage.class);
    }

    public int deleteAll() {
        return getHibernateTemplate().bulkUpdate("delete from " + getEntityType().getName());
    }

    public Iterator<String> listAllKeys() throws DaoException {
        return iterate("select m.key from " + getEntityType().getName() + " m", null);
    }

    public Properties listAsProperties() {
        final Properties properties = new SortedProperties();
        final Iterator<Object[]> iterator = iterate("select m.key, m.value from " + getEntityType().getName() + " m", null);
        while (iterator.hasNext()) {
            final Object[] row = iterator.next();
            final String key = StringUtils.trimToEmpty((String) row[0]);
            final String value = StringUtils.trimToEmpty((String) row[1]);
            properties.setProperty(key, value);
        }
        DataIteratorHelper.close(iterator);
        return properties;
    }

    public Iterator<Object[]> listData() throws DaoException {
        return iterate("select m.id, m.key, m.value from " + getEntityType().getName() + " m", null);
    }

    public TranslationMessage load(final String key) {
        final Map<String, ?> params = Collections.singletonMap("key", key);
        final TranslationMessage translationMessage = uniqueResult("from " + getEntityType().getName() + " e where e.key = :key", params);
        if (translationMessage == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        return translationMessage;
    }

    public List<TranslationMessage> search(final TranslationMessageQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "m");
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "m.key", query.getKey());
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "m.value", query.getValue());
        if (query.isShowOnlyEmpty()) {
            hql.append(" and (m.value is null or length(m.value) = 0)");
        }
        HibernateHelper.appendOrder(hql, "m.key");
        return list(query, hql.toString(), namedParameters);
    }

}
