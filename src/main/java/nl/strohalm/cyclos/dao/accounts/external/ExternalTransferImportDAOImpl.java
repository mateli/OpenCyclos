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
package nl.strohalm.cyclos.dao.accounts.external;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImport;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferImportQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for data access object for external transfer imports
 * @author luis
 */
public class ExternalTransferImportDAOImpl extends BaseDAOImpl<ExternalTransferImport> implements ExternalTransferImportDAO {

    public ExternalTransferImportDAOImpl() {
        super(ExternalTransferImport.class);
    }

    public boolean hasCheckedTransfers(final Long... ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select count(*)");
        hql.append(" from ").append(ExternalTransfer.class.getName()).append(" t");
        hql.append(" where t.transferImport.id in (:ids)");
        hql.append("   and t.status <> :pending");
        namedParameters.put("ids", Arrays.asList(ids));
        namedParameters.put("pending", ExternalTransfer.Status.PENDING);
        final Number count = uniqueResult(hql.toString(), namedParameters);
        return count.intValue() > 0;
    }

    public List<ExternalTransferImport> search(final ExternalTransferImportQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(ExternalTransferImport.class, "i", query.getFetch());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "i.account", query.getAccount());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "i.date", query.getPeriod());
        HibernateHelper.appendOrder(hql, "i.date desc");
        return list(query, hql.toString(), namedParameters);
    }
}
