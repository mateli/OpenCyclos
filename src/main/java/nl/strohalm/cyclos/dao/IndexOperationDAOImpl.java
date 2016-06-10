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
package nl.strohalm.cyclos.dao;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.IndexOperation;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for {@link IndexOperationDAO}
 * 
 * @author luis
 */
public class IndexOperationDAOImpl extends BaseDAOImpl<IndexOperation> implements IndexOperationDAO {

    public IndexOperationDAOImpl() {
        super(IndexOperation.class);
    }

    @Override
    public int deleteBefore(final Calendar limit) {
        Map<String, ?> params = Collections.singletonMap("limit", limit);
        return bulkUpdate("delete from " + entityClass.getName() + " where date < :limit", params);
    }

    @Override
    public IndexOperation last() {
        return uniqueResult("from IndexOperation o order by o.date desc, o.id desc", null);
    }

    @Override
    public IndexOperation next(final Calendar lastTime, final Long lastId) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder hql = HibernateHelper.getInitialQuery(entityClass, "o");
        if (lastTime != null && lastId != null) {
            hql.append(" and (o.date > :date or (o.date = :date and o.id > :id))");
            params.put("date", lastTime);
            params.put("id", lastId);
        }
        HibernateHelper.appendOrder(hql, "o.date", "o.id");
        return uniqueResult(hql.toString(), params);
    }

}
