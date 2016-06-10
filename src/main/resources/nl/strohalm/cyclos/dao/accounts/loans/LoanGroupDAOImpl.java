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
package nl.strohalm.cyclos.dao.accounts.loans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateCustomFieldHandler;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implamentation DAO class for Loan Groups
 * @author rafael
 */
public class LoanGroupDAOImpl extends BaseDAOImpl<LoanGroup> implements LoanGroupDAO {

    private HibernateCustomFieldHandler hibernateCustomFieldHandler;

    public LoanGroupDAOImpl() {
        super(LoanGroup.class);
    }

    @Override
    public List<LoanGroup> search(final LoanGroupQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select lg");
        hql.append(" from ").append(getEntityType().getName()).append(" lg ");
        hibernateCustomFieldHandler.appendJoins(hql, "lg.customValues", query.getCustomValues());
        HibernateHelper.appendJoinFetch(hql, getEntityType(), "lg", query.getFetch());
        hql.append(" where 1=1");
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "lg.description", query.getDescription());
        HibernateHelper.addLikeParameterToQuery(hql, namedParameters, "lg.name", query.getName());
        if (query.getMember() != null) {
            if (query.isNotOfMember()) {
                hql.append(" and :notMember not in elements(lg.members) ");
            } else {
                hql.append(" and (:member in elements(lg.members) or exists ( ");
                hql.append("     select 1 from Member m where m.broker = :member and m in elements(lg.members) ");
                hql.append("))");
            }
            namedParameters.put("member", query.getMember());
        }
        if (query.getNoLoans() != null) {
            if (query.getNoLoans()) {
                hql.append(" and not exists (select 1 from " + Loan.class.getName() + " l where l.loanGroup = lg) ");
            } else {
                hql.append(" and exists (select 1 from " + Loan.class.getName() + " l where l.loanGroup = lg) ");
            }
        }
        hibernateCustomFieldHandler.appendConditions(hql, namedParameters, query.getCustomValues());
        HibernateHelper.appendOrder(hql, "lg.name");
        return list(query, hql.toString(), namedParameters);
    }

    public void setHibernateCustomFieldHandler(final HibernateCustomFieldHandler hibernateCustomFieldHandler) {
        this.hibernateCustomFieldHandler = hibernateCustomFieldHandler;
    }

}
