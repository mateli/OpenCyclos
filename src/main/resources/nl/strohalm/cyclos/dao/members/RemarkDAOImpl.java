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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.remarks.Remark;
import nl.strohalm.cyclos.entities.members.remarks.Remark.Nature;
import nl.strohalm.cyclos.entities.members.remarks.RemarkQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation class for remark DAO
 * @author rafael
 * @author Jefferson Magno
 */
public class RemarkDAOImpl extends BaseDAOImpl<Remark> implements RemarkDAO {

    public RemarkDAOImpl() {
        super(Remark.class);
    }

    public List<? extends Remark> search(final RemarkQuery query) {
        final Nature nature = query.getNature();
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(nature.getType(), "r", fetch);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "r.writer", query.getWriter());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "r.subject", query.getSubject());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "r.date", query.getPeriod());

        HibernateHelper.appendOrder(hql, "r.date desc");
        return list(query, hql.toString(), namedParameters);
    }
}
