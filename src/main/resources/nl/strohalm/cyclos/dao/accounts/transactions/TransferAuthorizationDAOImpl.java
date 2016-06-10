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
package nl.strohalm.cyclos.dao.accounts.transactions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorization;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for TransferAuthorizationDAO
 * @author luis
 */
public class TransferAuthorizationDAOImpl extends BaseDAOImpl<TransferAuthorization> implements TransferAuthorizationDAO {

    public TransferAuthorizationDAOImpl() {
        super(TransferAuthorization.class);
    }

    public List<TransferAuthorization> search(final TransferAuthorizationQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "a", query.getFetch());
        if (query.isByAdministration()) {
            hql.append(" and a.by.class = :admin");
            namedParameters.put("admin", Element.Nature.ADMIN.getValue());
        }
        if (query.getBy() != null) {
            hql.append(" and (a.by = :by or exists (select op.id from Operator op where op = a.by and op.member = :by))");
            namedParameters.put("by", query.getBy());
        }

        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "a.date", query.getPeriod());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "a.action", query.getAction());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "a.transfer.type", query.getTransferType());
        if (query.getMember() != null) {
            hql.append(" and exists (select ma.id from MemberAccount ma where ma.member = :member and (ma = a.transfer.from or ma = a.transfer.to))");
            namedParameters.put("member", query.getMember());
        }
        HibernateHelper.appendOrder(hql, "a.date desc");
        return list(query, hql.toString(), namedParameters);
    }

}
