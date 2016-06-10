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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer.SummaryStatus;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferQuery;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for external transfer DAO
 * @author luis
 */
public class ExternalTransferDAOImpl extends BaseDAOImpl<ExternalTransfer> implements ExternalTransferDAO {

    public ExternalTransferDAOImpl() {
        super(ExternalTransfer.class);
    }

    @Override
    public List<ExternalTransfer> search(final ExternalTransferQuery query) {
        final ExternalAccount account = query.getAccount();
        if (account == null) {
            // The account is required. If none given, ensure nothing will be returned
            return Collections.emptyList();
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(ExternalTransfer.class, "t", query.getFetch());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.account", account);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.type", query.getType());
        if (query.isOnlyWithValidTypes()) {
            HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "t.type.action", "<>", ExternalTransferType.Action.IGNORE);
        }
        namedParameters.put("ignore", ExternalTransferType.Action.IGNORE);
        if (query.getStatus() != null) {
            switch (query.getStatus()) {
                case COMPLETE_PENDING:
                case INCOMPLETE_PENDING:
                    boolean complete = query.getStatus() == SummaryStatus.COMPLETE_PENDING;
                    HibernateHelper.addParameterToQuery(hql, namedParameters, "t.status", ExternalTransfer.Status.PENDING);
                    hql.append("and " + (complete ? "not" : "") + "(t.type is null or (t.type.action != :ignore and t.member is null) or t.date is null or t.amount is null)");
                    break;
                case CHECKED:
                    HibernateHelper.addParameterToQuery(hql, namedParameters, "t.status", ExternalTransfer.Status.CHECKED);
                    break;
                case PROCESSED:
                    HibernateHelper.addParameterToQuery(hql, namedParameters, "t.status", ExternalTransfer.Status.PROCESSED);
                    break;
            }
        }
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.transferImport", query.getTransferImport());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "t.date", query.getPeriod());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.member", query.getMember());
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "t.amount", ">=", query.getInitialAmount());
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "t.amount", "<=", query.getFinalAmount());
        HibernateHelper.appendOrder(hql, "t.date desc", "t.amount");
        return list(query, hql.toString(), namedParameters);
    }
}
