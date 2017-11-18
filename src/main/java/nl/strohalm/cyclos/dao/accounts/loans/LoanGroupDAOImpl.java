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

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.utils.jpa.JpaCustomFieldHandler;
import nl.strohalm.cyclos.utils.jpa.JpaQueryHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implamentation DAO class for Loan Groups
 * @author rafael
 */
public class LoanGroupDAOImpl extends BaseDAOImpl<LoanGroup> implements LoanGroupDAO {

    private JpaCustomFieldHandler jpaCustomFieldHandler;

    public LoanGroupDAOImpl() {
        super(LoanGroup.class);
    }

    @Override
    public List<LoanGroup> search(final LoanGroupQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select lg");
        hql.append(" from ").append(getEntityType().getName()).append(" lg ");
        jpaCustomFieldHandler.appendJoins(hql, "lg.customValues", query.getCustomValues());
        JpaQueryHelper.appendJoinFetch(hql, getEntityType(), "lg", query.getFetch());
        hql.append(" where 1=1");
        JpaQueryHelper.addLikeParameterToQuery(hql, namedParameters, "lg.description", query.getDescription());
        JpaQueryHelper.addLikeParameterToQuery(hql, namedParameters, "lg.name", query.getName());
        if (query.getMember() != null) {
            if (query.isNotOfMember()) {
                hql.append(" and :notMember not member of lg.members ");
            } else {
                hql.append(" and (:member member of lg.members or exists ( ");
                hql.append("     select 1 from Member m where m.broker = :member and m member of lg.members ");
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
        jpaCustomFieldHandler.appendConditions(hql, namedParameters, query.getCustomValues());
        JpaQueryHelper.appendOrder(hql, "lg.name");
        return list(query, hql.toString(), namedParameters);
    }

    public void setJpaCustomFieldHandler(final JpaCustomFieldHandler jpaCustomFieldHandler) {
        this.jpaCustomFieldHandler = jpaCustomFieldHandler;
    }

}
