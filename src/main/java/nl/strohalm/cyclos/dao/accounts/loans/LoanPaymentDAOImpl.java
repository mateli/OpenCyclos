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
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment.Status;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPaymentQuery;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.jpa.JpaCustomFieldHandler;
import nl.strohalm.cyclos.utils.jpa.JpaQueryHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation DAO for loan payments
 * @author rafael
 */
public class LoanPaymentDAOImpl extends BaseDAOImpl<LoanPayment> implements LoanPaymentDAO {

    private JpaCustomFieldHandler jpaCustomFieldHandler;

    public LoanPaymentDAOImpl() {
        super(LoanPayment.class);
    }

    public JpaCustomFieldHandler getJpaCustomFieldHandler() {
        return jpaCustomFieldHandler;
    }

    public TransactionSummaryVO paymentsSummary(final LoanPaymentQuery query) throws DaoException {
        final Status status = query.getStatus();
        String amountPath = null;
        if (status == Status.REPAID) {
            amountPath = "lp.repaidAmount";
        } else {
            amountPath = "lp.amount - lp.repaidAmount";
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append("select new " + TransactionSummaryVO.class.getName() + "(count(lp.id), sum(" + amountPath + "))");
        buildQuery(query, namedParameters, hql);
        return uniqueResult(hql.toString(), namedParameters);
    }

    public List<LoanPayment> search(final LoanPaymentQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append("select lp");
        buildQuery(query, namedParameters, hql);
        JpaQueryHelper.appendOrder(hql, "lp.expirationDate");
        return list(query, hql.toString(), namedParameters);
    }

    public void setJpaCustomFieldHandler(final JpaCustomFieldHandler jpaCustomFieldHandler) {
        this.jpaCustomFieldHandler = jpaCustomFieldHandler;
    }

    /**
     * Builds a query for the given LoanPaymentQuery
     */
    private void buildQuery(final LoanPaymentQuery query, final Map<String, Object> namedParameters, final StringBuilder hql) {
        hql.append(" from LoanPayment lp inner join lp.loan l inner join l.transfer t");
        jpaCustomFieldHandler.appendJoins(hql, "t.customValues", query.getLoanCustomValues());
        JpaQueryHelper.appendJoinFetch(hql, getEntityType(), "lp", query.getFetch());
        hql.append(", MemberAccount a inner join a.member m ");
        jpaCustomFieldHandler.appendJoins(hql, "m.customValues", query.getMemberCustomValues());
        hql.append(" where t.to = a ");
        JpaQueryHelper.addInParameterToQuery(hql, namedParameters, "lp.status", query.getStatusList());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "a.member", query.getMember());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "a.member.broker", query.getBroker());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "l.loanGroup", query.getLoanGroup());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "t.type", query.getTransferType());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "t.status", query.getTransferStatus());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "t.to.type", query.getAccountType());
        JpaQueryHelper.addParameterToQuery(hql, namedParameters, "t.transactionNumber", query.getTransactionNumber());
        JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "lp.expirationDate", query.getExpirationPeriod());
        JpaQueryHelper.addPeriodParameterToQuery(hql, namedParameters, "lp.repaymentDate", query.getRepaymentPeriod());
        if (query.getGroups() != null && !query.getGroups().isEmpty()) {
            hql.append(" and a.member.group in :groups ");
            namedParameters.put("groups", query.getGroups());
        }
        jpaCustomFieldHandler.appendConditions(hql, namedParameters, query.getMemberCustomValues());
        jpaCustomFieldHandler.appendConditions(hql, namedParameters, query.getLoanCustomValues());
    }

}
