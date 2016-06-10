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
package nl.strohalm.cyclos.dao.accounts.guarantees;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeTypeQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation class for guarantee type DAO
 * @author Jefferson Magno
 */
public class GuaranteeTypeDAOImpl extends BaseDAOImpl<GuaranteeType> implements GuaranteeTypeDAO {

    public GuaranteeTypeDAOImpl() {
        super(GuaranteeType.class);
    }

    public List<GuaranteeType> search(final GuaranteeTypeQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "gt", fetch);
        if (query.isEnabled()) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "gt.enabled", query.isEnabled());
        }

        HibernateHelper.addInParameterToQuery(hql, namedParameters, "gt.currency", query.getCurrencies());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "gt.model", query.getModels());
        HibernateHelper.appendOrder(hql, "gt.name ASC");

        return list(query, hql.toString(), namedParameters);
    }

}
