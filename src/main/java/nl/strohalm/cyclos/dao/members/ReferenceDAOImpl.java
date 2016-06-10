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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentAwaitingFeedbackDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.GeneralReference;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PaymentsAwaitingFeedbackQuery;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.entities.members.ReferenceQuery;
import nl.strohalm.cyclos.entities.members.TransactionFeedback;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.IteratorListImpl;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.ScrollableResultsIterator;
import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.query.PageImpl;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;

/**
 * Implementation class for reference DAO
 * @author rafael
 */
public class ReferenceDAOImpl extends BaseDAOImpl<Reference> implements ReferenceDAO {

    public ReferenceDAOImpl() {
        super(Reference.class);
    }

    @Override
    public Map<Level, Integer> countGivenReferencesByLevel(final Reference.Nature nature, final Collection<MemberGroup> memberGroups) {
        return countReferencesByLevel(nature, null, null, memberGroups, false);
    }

    @Override
    public Map<Level, Integer> countReferencesByLevel(final Reference.Nature nature, final Period period, final Member member, final boolean received) {
        return countReferencesByLevel(nature, period, member, null, received);
    }

    public Map<Level, Integer> countReferencesByLevel(final Reference.Nature nature, final Period period, final Member member, final Collection<MemberGroup> memberGroups, final boolean received) {
        final Map<Level, Integer> countGivenReferences = new EnumMap<Level, Integer>(Level.class);
        for (final Level level : Level.values()) {
            countGivenReferences.put(level, 0);
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Class<? extends Reference> type = typeForNature(nature);
        final StringBuilder hql = new StringBuilder("select r.level, count(r.id) from ").append(type.getName()).append(" r where 1=1 ");
        HibernateHelper.addParameterToQuery(hql, namedParameters, (received ? "r.to" : "r.from"), member);
        if (memberGroups != null && !memberGroups.isEmpty()) {
            hql.append(" and " + (received ? "r.to" : "r.from") + ".group in (:memberGroups) ");
            namedParameters.put("memberGroups", memberGroups);
        }
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "r.date", period);
        hql.append(" group by r.level order by r.level");
        final List<Object[]> rows = list(hql.toString(), namedParameters);
        for (final Object[] row : rows) {
            countGivenReferences.put((Level) row[0], (Integer) row[1]);
        }
        return countGivenReferences;
    }

    @Override
    public List<? extends Reference> search(final ReferenceQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final Nature nature = query.getNature();
        final Class<? extends Reference> type = typeForNature(nature);
        final StringBuilder hql = HibernateHelper.getInitialQuery(type, "r", fetch);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "r.from", query.getFrom());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "r.to", query.getTo());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "r.date", query.getPeriod());
        if (nature == Nature.TRANSACTION) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "r.transfer", query.getTransfer());
            HibernateHelper.addParameterToQuery(hql, namedParameters, "r.scheduledPayment", query.getScheduledPayment());
        }

        if (query.getGroups() != null) {
            hql.append(" and (r.from.group in (:groups) or r.to.group in (:groups)) ");
            namedParameters.put("groups", query.getGroups());
        }

        HibernateHelper.appendOrder(hql, "r.id desc");
        return list(query, hql.toString(), namedParameters);
    }

    @Override
    public List<PaymentAwaitingFeedbackDTO> searchPaymentsAwaitingFeedback(final PaymentsAwaitingFeedbackQuery query) {

        final ResultType resultType = query.getResultType();
        final PageParameters pageParameters = query.getPageParameters();
        final boolean countOnly = resultType == ResultType.PAGE && pageParameters != null && pageParameters.getMaxResults() == 0;

        // There are 2 tables which contains payments that can have feedback: transfers and scheduled payments
        // As we need an union, we need a native SQL

        final Member member = query.getMember();
        Boolean expired = query.getExpired();

        final StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        if (countOnly) {
            sql.append(" count(*) as row_count");
        } else {
            sql.append(" * ");
        }
        sql.append(" from ( ");
        {
            sql.append(" select t.id, t.type_id as transferTypeId, false as scheduled, t.date, t.amount, tm.id as memberId, tm.name as memberName, ta.owner_name as memberUsername");
            sql.append(" from transfers t inner join transfer_types tt on t.type_id = tt.id inner join accounts ta on t.to_account_id = ta.id inner join members tm on ta.member_id = tm.id");
            if (member != null) {
                sql.append(" inner join accounts a on t.from_account_id = a.id");
            }
            sql.append(" left join refs tf on tf.transfer_id = t.id");
            sql.append(" where tt.requires_feedback = true");
            sql.append(" and t.date >= tt.feedback_enabled_since");
            sql.append(" and t.parent_id is null");
            sql.append(" and t.chargeback_of_id is null");
            sql.append(" and t.scheduled_payment_id is null");
            sql.append(" and t.process_date is not null");
            if (expired != null) {
                sql.append(" and t.feedback_deadline " + (expired ? "<" : ">=") + " now()");
            }
            sql.append(" and tf.id is null");
            if (member != null) {
                sql.append(" and a.member_id = :memberId");
            }

            sql.append(" union ");

            sql.append(" select sp.id, sp.type_id, true, sp.date, sp.amount, tm.id, tm.name, ta.owner_name");
            sql.append(" from scheduled_payments sp inner join transfer_types tt on sp.type_id = tt.id inner join accounts ta on sp.to_account_id = ta.id inner join members tm on ta.member_id = tm.id");
            if (member != null) {
                sql.append(" inner join accounts a on sp.from_account_id = a.id");
            }
            sql.append(" left join refs tf on tf.scheduled_payment_id = sp.id");
            sql.append(" where tt.requires_feedback = true");
            if (expired != null) {
                sql.append(" and sp.feedback_deadline " + (expired ? "<" : ">=") + " now()");
            }
            sql.append(" and sp.date >= tt.feedback_enabled_since");
            sql.append(" and tf.id is null");
            if (member != null) {
                sql.append(" and a.member_id = :memberId");
            }
        }
        sql.append(") as awaiting ");
        if (!countOnly) {
            sql.append("order by date");
        }

        SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
        if (member != null) {
            sqlQuery.setLong("memberId", member.getId());
        }
        if (countOnly) {
            // Handle the special case for count only
            sqlQuery.addScalar("row_count", StandardBasicTypes.INTEGER);
            int count = ((Number) sqlQuery.uniqueResult()).intValue();
            return new PageImpl<PaymentAwaitingFeedbackDTO>(pageParameters, count, Collections.<PaymentAwaitingFeedbackDTO> emptyList());
        } else {
            // Execute the search
            sqlQuery.addScalar("id", StandardBasicTypes.LONG);
            sqlQuery.addScalar("transferTypeId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("scheduled", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("date", StandardBasicTypes.CALENDAR);
            sqlQuery.addScalar("amount", StandardBasicTypes.BIG_DECIMAL);
            sqlQuery.addScalar("memberId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("memberName", StandardBasicTypes.STRING);
            sqlQuery.addScalar("memberUsername", StandardBasicTypes.STRING);
            getHibernateQueryHandler().applyPageParameters(pageParameters, sqlQuery);

            // We'll always use an iterator, even if it is for later adding it to a list
            Iterator<PaymentAwaitingFeedbackDTO> iterator = new ScrollableResultsIterator<PaymentAwaitingFeedbackDTO>(sqlQuery, new Transformer<Object[], PaymentAwaitingFeedbackDTO>() {
                @Override
                public PaymentAwaitingFeedbackDTO transform(final Object[] input) {
                    PaymentAwaitingFeedbackDTO dto = new PaymentAwaitingFeedbackDTO();
                    dto.setId((Long) input[0]);
                    dto.setTransferTypeId((Long) input[1]);
                    dto.setScheduled(Boolean.TRUE.equals(input[2]));
                    dto.setDate((Calendar) input[3]);
                    dto.setAmount((BigDecimal) input[4]);
                    dto.setMemberId((Long) input[5]);
                    dto.setMemberName((String) input[6]);
                    dto.setMemberUsername((String) input[7]);

                    TransferType transferType = (TransferType) getSession().load(TransferType.class, dto.getTransferTypeId());
                    dto.setCurrency(getFetchDao().fetch(transferType.getCurrency()));

                    return dto;
                }
            });
            if (resultType == ResultType.ITERATOR) {
                return new IteratorListImpl<PaymentAwaitingFeedbackDTO>(iterator);
            } else {
                List<PaymentAwaitingFeedbackDTO> list = new ArrayList<PaymentAwaitingFeedbackDTO>();
                CollectionUtils.addAll(list, iterator);
                DataIteratorHelper.close(iterator);

                if (resultType == ResultType.PAGE) {
                    // For page, we need another search for the total count
                    query.setPageForCount();
                    int totalCount = PageHelper.getTotalCount(searchPaymentsAwaitingFeedback(query));

                    return new PageImpl<PaymentAwaitingFeedbackDTO>(pageParameters, totalCount, list);
                } else {
                    return list;
                }
            }
        }
    }

    private Class<? extends Reference> typeForNature(final Nature nature) {
        Class<? extends Reference> type;
        if (nature == Reference.Nature.TRANSACTION) {
            type = TransactionFeedback.class;
        } else if (nature == Reference.Nature.GENERAL) {
            type = GeneralReference.class;
        } else {
            type = Reference.class;
        }
        return type;
    }

}
