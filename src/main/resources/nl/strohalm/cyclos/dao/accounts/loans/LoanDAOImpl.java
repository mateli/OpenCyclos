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
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.loans.LoanQuery;
import nl.strohalm.cyclos.entities.accounts.loans.LoanQuery.QueryStatus;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.hibernate.HibernateCustomFieldHandler;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation DAO for loans
 * @author rafael
 * @author fireblade
 * @author luis
 */
public class LoanDAOImpl extends BaseDAOImpl<Loan> implements LoanDAO {

    private HibernateCustomFieldHandler hibernateCustomFieldHandler;

    public LoanDAOImpl() {
        super(Loan.class);
    }

    @Override
    public Loan getByTransfer(final Transfer transfer) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append(" from Loan l ");
        hql.append(" where 1=1 ");
        hql.append(" and l.transfer = :transfer ");
        params.put("transfer", transfer);
        return uniqueResult(hql.toString(), params);
    }

    public HibernateCustomFieldHandler getHibernateCustomFieldHandler() {
        return hibernateCustomFieldHandler;
    }

    @Override
    public List<Loan> search(final LoanQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("openPaymentStatus", LoanPayment.Status.OPEN);
        namedParameters.put("expiredPaymentStatus", LoanPayment.Status.EXPIRED);
        namedParameters.put("repaidPaymentStatus", LoanPayment.Status.REPAID);
        namedParameters.put("discardedPaymentStatus", LoanPayment.Status.DISCARDED);
        namedParameters.put("inProcessPaymentStatus", LoanPayment.Status.IN_PROCESS);
        namedParameters.put("recoveredPaymentStatus", LoanPayment.Status.RECOVERED);
        namedParameters.put("unrecoverablePaymentStatus", LoanPayment.Status.UNRECOVERABLE);
        namedParameters.put("pendingStatus", Payment.Status.PENDING);
        namedParameters.put("deniedStatus", Payment.Status.DENIED);

        final StringBuilder hql = new StringBuilder();
        hql.append(" select l ");
        hql.append(" from Loan l inner join l.transfer t ");
        hibernateCustomFieldHandler.appendJoins(hql, "t.customValues", query.getLoanCustomValues());
        HibernateHelper.appendJoinFetch(hql, getEntityType(), "l", query.getFetch());
        hql.append(", MemberAccount a inner join a.member m ");
        hibernateCustomFieldHandler.appendJoins(hql, "m.customValues", query.getMemberCustomValues());
        hql.append(" where t.to = a ");

        // Translate the status into the query status
        if (query.getStatus() != null) {
            QueryStatus queryStatus = null;
            switch (query.getStatus()) {
                case OPEN:
                    queryStatus = QueryStatus.ANY_OPEN;
                    break;
                case CLOSED:
                    queryStatus = QueryStatus.ANY_CLOSED;
                    break;
                case PENDING_AUTHORIZATION:
                    queryStatus = QueryStatus.PENDING_AUTHORIZATION;
                    break;
                case AUTHORIZATION_DENIED:
                    queryStatus = QueryStatus.AUTHORIZATION_DENIED;
                    break;
            }
            query.setQueryStatus(queryStatus);
            query.setStatus(null);
        }

        // Handle the query status
        if (query.getQueryStatus() != null) {
            switch (query.getQueryStatus()) {
                case OPEN:
                    hql.append(" and not exists (select lp.id from l.payments lp where lp.status in (:expiredPaymentStatus, :inProcessPaymentStatus, :recoveredPaymentStatus, :unrecoverablePaymentStatus))");
                    hql.append(" and exists (select lp.id from l.payments lp where lp.status = :openPaymentStatus)");
                    hql.append(" and t.processDate is not null");
                    break;
                case ANY_OPEN:
                case ANY_CLOSED:
                    final String condition = query.getQueryStatus() == QueryStatus.ANY_CLOSED ? " and not " : " and ";
                    hql.append(condition + "exists (select lp.id from l.payments lp where lp.status in (:openPaymentStatus, :expiredPaymentStatus, :inProcessPaymentStatus))");
                    hql.append(" and t.processDate is not null");
                    break;
                case CLOSED:
                    hql.append(" and not exists (select lp.id from l.payments lp where lp.status in (:openPaymentStatus, :expiredPaymentStatus, :inProcessPaymentStatus, :recoveredPaymentStatus, :unrecoverablePaymentStatus))");
                    break;
                case PENDING_AUTHORIZATION:
                    hql.append(" and t.status = :pendingStatus");
                    break;
                case AUTHORIZATION_DENIED:
                    hql.append(" and t.status = :deniedStatus");
                    break;
                case EXPIRED:
                    hql.append(" and exists (select lp.id from l.payments lp where lp.status = :expiredPaymentStatus)");
                    break;
                case IN_PROCESS:
                    hql.append(" and exists (select lp.id from l.payments lp where lp.status = :inProcessPaymentStatus)");
                    break;
                case RECOVERED:
                    hql.append(" and exists (select lp.id from l.payments lp where lp.status = :recoveredPaymentStatus)");
                    break;
                case UNRECOVERABLE:
                    hql.append(" and exists (select lp.id from l.payments lp where lp.status = :unrecoverablePaymentStatus)");
                    break;
            }
        }

        if (query.isHideAuthorizationRelated()) {
            hql.append(" and t.status not in (:pendingStatus, :deniedStatus)");
        }

        Member member = query.getMember();
        if (member != null) {
            member = getFetchDao().fetch(member, Element.Relationships.GROUP);
            final MemberGroup group = member.getMemberGroup();
            if (group.getMemberSettings().isViewLoansByGroup()) {
                hql.append(" and (a.member = :member or :member in elements(l.toMembers))");
                namedParameters.put("member", member);
            } else {
                HibernateHelper.addParameterToQuery(hql, namedParameters, "a.member", member);
            }
        }
        HibernateHelper.addParameterToQuery(hql, namedParameters, "a.member.broker", query.getBroker());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "l.loanGroup", query.getLoanGroup());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.status", query.getTransferStatus());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.type", query.getTransferType());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.to.type", query.getAccountType());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.to.type.currency", query.getCurrency());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "t.transactionNumber", query.getTransactionNumber());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "t.date", query.getGrantPeriod());
        final Period expirationPeriod = query.getExpirationPeriod();
        if (expirationPeriod != null && (expirationPeriod.getBegin() != null || expirationPeriod.getEnd() != null)) {
            hql.append(" and exists (select lp.id from l.payments lp where (lp.status = :openPaymentStatus or lp.status = :expiredPaymentStatus) ");
            HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "lp.expirationDate", expirationPeriod);
            hql.append(')');
        }
        final Period paymentPeriod = query.getPaymentPeriod();
        if (paymentPeriod != null && (paymentPeriod.getBegin() != null || paymentPeriod.getEnd() != null)) {
            hql.append(" and exists (select lp.id from l.payments lp where (lp.status = :repaidPaymentStatus or lp.status = :discardedPaymentStatus) ");
            HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "lp.repaymentDate", paymentPeriod);
            hql.append(')');
        }
        if (query.getGroups() != null && !query.getGroups().isEmpty()) {
            hql.append(" and a.member.group in (:groups) ");
            namedParameters.put("groups", query.getGroups());
        }
        hibernateCustomFieldHandler.appendConditions(hql, namedParameters, query.getMemberCustomValues());
        hibernateCustomFieldHandler.appendConditions(hql, namedParameters, query.getLoanCustomValues());
        return list(query, hql.toString(), namedParameters);
    }

    public void setHibernateCustomFieldHandler(final HibernateCustomFieldHandler hibernateCustomFieldHandler) {
        this.hibernateCustomFieldHandler = hibernateCustomFieldHandler;
    }

}
