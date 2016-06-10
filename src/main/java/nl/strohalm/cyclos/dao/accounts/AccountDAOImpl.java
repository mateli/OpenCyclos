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
package nl.strohalm.cyclos.dao.accounts;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.dao.JDBCCallback;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountLock;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountQuery;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccount.Action;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberTransactionDetailsReportData;
import nl.strohalm.cyclos.entities.members.MemberTransactionSummaryVO;
import nl.strohalm.cyclos.entities.members.MembersTransactionsReportParameters;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.BulkUpdateAccountDTO;
import nl.strohalm.cyclos.services.accounts.GetTransactionsDTO;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.IteratorListImpl;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.ScrollableResultsIterator;
import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper.QueryParameter;
import nl.strohalm.cyclos.utils.query.IteratorList;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

/**
 * Implementation DAO for accounts
 * @author rafael, Jefferson Magno, luis
 */
public class AccountDAOImpl extends BaseDAOImpl<Account> implements AccountDAO {

    private class DiffsIterator implements Iterator<AccountDailyDifference>, Closeable {
        private final ScrollableResults results;
        private AccountDailyDifference  diff;

        public DiffsIterator(final ScrollableResults results) {
            this.results = results;
            advance();
        }

        @Override
        public void close() throws IOException {
            results.close();
        }

        @Override
        public boolean hasNext() {
            return diff != null;
        }

        @Override
        public AccountDailyDifference next() {
            AccountDailyDifference result = diff;
            advance();
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void advance() {
            if (!results.next()) {
                diff = null;
                return;
            }
            diff = new AccountDailyDifference();
            diff.setDay(results.getCalendar(1));
            diff.setBalance(BigDecimal.ZERO);
            diff.setReserved(BigDecimal.ZERO);
            readAmount();

            // We have to try to iterate once, as there could be 2 records by day: one for balance and other for reserved
            boolean shouldRewind = true;
            if (results.next()) {
                Calendar day = results.getCalendar(1);
                if (day.equals(diff.getDay())) {
                    shouldRewind = false;
                    readAmount();
                }
            }
            // We've peeked the next one to get the other data, but it was another record. Rewind.
            if (shouldRewind) {
                results.previous();
            }
        }

        private void readAmount() {
            String type = results.getString(0);
            BigDecimal amount = results.getBigDecimal(2);
            if ("R".equals(type)) {
                diff.setReserved(amount);
            } else {
                diff.setBalance(amount);
            }
        }
    }

    private static final char[] COLUMN_DELIMITERS = new char[] { '_' };

    public AccountDAOImpl() {
        super(Account.class);
    }

    @Override
    public void bulkUpdateCreditLimites(final BulkUpdateAccountDTO dto) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append("update MemberAccount ma set ");
        hql.append(" ma.creditLimit = :limit, ");
        namedParameters.put("limit", dto.getCreditLimit());
        hql.append(" ma.upperCreditLimit = :upperLimit ");
        namedParameters.put("upperLimit", dto.getUpperCreditLimit());
        hql.append(" where ma.type = :type ");
        namedParameters.put("type", dto.getType());
        // because joins in bulk deletes are not supported, we must do it via this tricky subQuery
        hql.append(" and ma.member in ");
        hql.append("      (from Member m where 1 = 1 ");
        hql.append("       and m.group = :group ) ");
        namedParameters.put("group", dto.getGroup());
        bulkUpdate(hql.toString(), namedParameters);
    }

    @Override
    public int countAccounts(final MemberGroup group, final MemberAccountType accountType, final Action action) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("group", group);
        params.put("type", accountType);
        params.put("action", action);

        StringBuilder hql = new StringBuilder();
        hql.append(" select count(*) ");
        hql.append(" from MemberAccount ma");
        hql.append(" where ma.member.group = :group ");
        hql.append(" and ma.type = :type ");
        hql.append(" and ma.action = :action ");

        return this.<Integer> uniqueResult(hql.toString(), params);
    }

    @Override
    public int delete(final boolean flush, final Long... ids) {
        getSession()
                .createQuery("delete from AccountLock l where l.id in (:ids)")
                .setParameterList("ids", ids)
                .executeUpdate();
        return super.delete(flush, ids);
    };

    @Override
    public TransactionSummaryVO getBrokerCommissions(final GetTransactionsDTO dto) throws EntityNotFoundException, DaoException {
        final Account account = load(dto.getOwner(), dto.getType());
        final Period period = dto.getPeriod();
        final StringBuilder hql = new StringBuilder();
        final Map<String, Object> namedParams = new HashMap<String, Object>();
        hql.append(" select count(*), sum(t.amount)");
        hql.append(" from " + Transfer.class.getName() + " t, " + BrokerCommission.class.getName() + " f");
        hql.append(" where t.accountFeeLog.accountFee = f ");
        // Here we use just one payment filter
        final Collection<PaymentFilter> paymentFilters = dto.getPaymentFilters();
        if (CollectionUtils.isNotEmpty(paymentFilters)) {
            final PaymentFilter paymentFilter = paymentFilters.iterator().next();
            if (paymentFilter != null) {
                hql.append(" and t.type in (select pf.transferTypes from " + PaymentFilter.class.getName() + " pf where pf = :pf) ");
                namedParams.put("pf", paymentFilter);
            }
        }
        hql.append("   and t.to = :account ");
        namedParams.put("account", account);
        HibernateHelper.addPeriodParameterToQuery(hql, namedParams, "ifnull(t.processDate, t.date)", period);
        return buildSummary(uniqueResult(hql.toString(), namedParams));
    }

    @Override
    public TransactionSummaryVO getCredits(final GetTransactionsDTO dto) {
        return getSummary(dto, true, Transfer.Status.PROCESSED);
    }

    @Override
    public TransactionSummaryVO getDebits(final GetTransactionsDTO dto) {
        return getSummary(dto, false, Transfer.Status.PROCESSED);
    }

    @Override
    public TransactionSummaryVO getLoans(final GetTransactionsDTO dto) throws EntityNotFoundException, DaoException {
        final Account account = load(dto.getOwner(), dto.getType());
        final Period period = dto.getPeriod();
        final StringBuilder hql = new StringBuilder();
        final Map<String, Object> namedParams = new HashMap<String, Object>();
        hql.append(" select count(*), sum(t.amount)");
        hql.append(" from " + Loan.class.getName() + " l join l.transfer t");
        hql.append(" where t.to = :account ");
        // Here we use just one payment filter
        final Collection<PaymentFilter> paymentFilters = dto.getPaymentFilters();
        if (CollectionUtils.isNotEmpty(paymentFilters)) {
            final PaymentFilter paymentFilter = paymentFilters.iterator().next();
            if (paymentFilter != null) {
                hql.append(" and t.type in (select pf.transferTypes from " + PaymentFilter.class.getName() + " pf where pf = :pf) ");
                namedParams.put("pf", paymentFilter);
            }
        }
        namedParams.put("account", account);
        HibernateHelper.addPeriodParameterToQuery(hql, namedParams, "ifnull(t.processDate, t.date)", period);
        return buildSummary(uniqueResult(hql.toString(), namedParams));
    }

    @Override
    public MemberAccount getNextPendingProcessing() {
        final StringBuilder hql = new StringBuilder();
        hql.append("from MemberAccount ");
        hql.append(" where action is not null");
        return (MemberAccount) uniqueResult(hql.toString(), null);
    }

    @Override
    public TransactionSummaryVO getPendingCredits(final GetTransactionsDTO dto) throws EntityNotFoundException, DaoException {
        return getSummary(dto, true, Transfer.Status.PENDING);
    }

    @Override
    public TransactionSummaryVO getPendingDebits(final GetTransactionsDTO dto) throws EntityNotFoundException, DaoException {
        return getSummary(dto, false, Transfer.Status.PENDING);
    }

    @Override
    public <T extends Account> T insert(final T entity, final boolean flush) throws UnexpectedEntityException, DaoException {
        final T account = super.insert(entity, false);
        getSession().persist(new AccountLock(account));
        if (flush) {
            getSession().flush();
        }
        return account;
    }

    @Override
    public IteratorList<AccountDailyDifference> iterateDailyDifferences(final MemberAccount account, final Period period) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", account.getId());
        QueryParameter beginParameter = HibernateHelper.getBeginParameter(period);
        QueryParameter endParameter = HibernateHelper.getEndParameter(period);
        if (beginParameter != null) {
            params.put("begin", beginParameter.getValue());
        }
        if (endParameter != null) {
            params.put("end", endParameter.getValue());
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" select type, date(d.date) as date, sum(amount) as amount ");
        sql.append(" from ( ");
        sql.append("     select 'B' as type, t.process_date as date, ");
        sql.append("         case when t.chargeback_of_id is null then ");
        sql.append("             case when t.from_account_id = :accountId then -t.amount else t.amount end ");
        sql.append("         else ");
        sql.append("             case when t.to_account_id = :accountId then t.amount else -t.amount end ");
        sql.append("         end as amount ");
        sql.append("      from transfers t ");
        sql.append("      where (t.from_account_id = :accountId or t.to_account_id = :accountId) ");
        sql.append("      and t.process_date is not null ");
        if (beginParameter != null) {
            sql.append("  and t.process_date " + beginParameter.getOperator() + " :begin");
        }
        if (endParameter != null) {
            sql.append("  and t.process_date " + endParameter.getOperator() + " :end");
        }
        sql.append("      union ");
        sql.append("      select 'R', r.date, r.amount ");
        sql.append("      from amount_reservations r ");
        sql.append("      where r.account_id = :accountId ");
        if (beginParameter != null) {
            sql.append("  and r.date " + beginParameter.getOperator() + " :begin");
        }
        if (endParameter != null) {
            sql.append("  and r.date " + endParameter.getOperator() + " :end");
        }
        sql.append(" ) d ");
        sql.append(" group by type, date(d.date) ");
        sql.append(" order by date(d.date) ");
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.addScalar("type", StandardBasicTypes.STRING);
        query.addScalar("date", StandardBasicTypes.CALENDAR_DATE);
        query.addScalar("amount", StandardBasicTypes.BIG_DECIMAL);
        getHibernateQueryHandler().setQueryParameters(query, params);
        ScrollableResults results = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
        return new IteratorListImpl<AccountDailyDifference>(new DiffsIterator(results));
    }

    @Override
    public IteratorList<Account> iterateUnclosedAccounts(final Calendar day, final int maxResults) {
        Map<String, Calendar> params = Collections.singletonMap("day", day);
        StringBuilder hql = new StringBuilder();
        hql.append(" from Account a ");
        hql.append(" where (last_closing_date is null or last_closing_date < :day)");
        List<Account> accounts = list(ResultType.ITERATOR, hql.toString(), params, PageParameters.max(maxResults));
        return (IteratorList<Account>) accounts;
    }

    @Override
    public Account load(final AccountOwner owner, final AccountType type, final Relationship... fetch) throws EntityNotFoundException, DaoException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        String hql;
        if (owner instanceof SystemAccountOwner) {
            hql = "from SystemAccount a where a.type = :type";
        } else if (owner instanceof Member) {
            hql = "from MemberAccount a where a.member = :member and a.type = :type";
            params.put("member", owner);
        } else {
            throw new EntityNotFoundException(Account.class);
        }
        Account account = uniqueResult(hql, params);
        if (account == null) {
            throw new EntityNotFoundException(Account.class);
        }
        return getFetchDao().fetch(account, fetch);
    }

    @Override
    public List<Account> loadAll(final List<AccountDTO> dtos, final Relationship... fetch) throws EntityNotFoundException, DaoException {
        final List<Account> accounts = new ArrayList<Account>();
        for (final AccountDTO dto : dtos) {
            accounts.add(load(dto.getOwner(), dto.getType(), fetch));
        }
        return accounts;
    }

    @Override
    public void markForActivation(final BulkUpdateAccountDTO dto) {
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                final StringBuilder sql = new StringBuilder();
                Calendar date = Calendar.getInstance();
                Long typeId = dto.getType().getId();
                BigDecimal limit = dto.getCreditLimit();
                BigDecimal upperLimit = dto.getUpperCreditLimit();
                Long groupId = dto.getGroup().getId();

                // Fist, mark for activation all accounts which where already there but are inactive
                if (jdbc.isHSQLDB()) {
                    // this is because HSQLDB (e.g.: used by Cyclos Standalone) doesn't support join in update statements
                    sql.append("update accounts a");
                    sql.append(" set member_action = 'A'");
                    sql.append(" where a.member_status = 'I'");
                    sql.append("   and a.member_action is null");
                    sql.append("   and a.type_id = ?");
                    sql.append("   and exists (select 1 from members m");
                    sql.append("              where a.member_id = m.id and");
                    sql.append("              m.group_id = ?)");

                    jdbc.execute(sql.toString(), groupId, typeId);
                } else {
                    sql.append("update accounts a inner join members m on a.member_id = m.id");
                    sql.append(" set member_action = 'A'");
                    sql.append(" where a.member_status = 'I'");
                    sql.append("   and a.member_action is null");
                    sql.append("   and m.group_id = ?");
                    sql.append("   and a.type_id = ?");

                    jdbc.execute(sql.toString(), groupId, typeId);
                }

                // Then insert the missing accounts
                sql.setLength(0);
                sql.append("insert into accounts ");
                sql.append("(subclass, creation_date, owner_name, type_id, credit_limit, ");
                sql.append(" upper_credit_limit, member_id, member_status, member_action) ");
                sql.append(" select ");
                sql.append(" 'M', ?, u.username, ?, ?, ?, m.id, 'I', 'A' ");
                sql.append(" from members m, users u ");
                sql.append(" where m.id = u.id and m.group_id = ? ");
                sql.append("   and not exists (");
                sql.append("       select 1");
                sql.append("       from accounts a");
                sql.append("       where a.member_id = m.id");
                sql.append("         and a.type_id = ?");
                sql.append("   )");
                jdbc.execute(sql.toString(), date, typeId, limit, upperLimit, groupId, typeId);
            }
        });
    }

    @Override
    public void markForDeactivation(final MemberAccountType type, final MemberGroup group) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        StringBuilder hql = new StringBuilder();
        hql.append("update MemberAccount ma set ");
        hql.append(" ma.action = :action ");
        namedParameters.put("action", MemberAccount.Action.REMOVE);
        hql.append(" where ma.type = :type ");
        namedParameters.put("type", type);
        // because joins in bulk deletes are not supported, we must do it via this tricky subQuery
        hql.append(" and ma.member in ");
        hql.append("      (from Member m where 1 = 1 ");
        hql.append("       and m.group = :group ) ");
        namedParameters.put("group", group);
        bulkUpdate(hql.toString(), namedParameters);
    }

    @Override
    public Iterator<MemberTransactionDetailsReportData> membersTransactionsDetailsReport(final MembersTransactionsReportParameters params) {
        final StringBuilder sql = new StringBuilder();
        final Map<String, Object> parameters = new HashMap<String, Object>();

        // Find the transfer types ids
        Set<Long> ttIds = null;
        if (CollectionUtils.isNotEmpty(params.getPaymentFilters())) {
            ttIds = new HashSet<Long>();
            for (PaymentFilter pf : params.getPaymentFilters()) {
                pf = getFetchDao().fetch(pf, PaymentFilter.Relationships.TRANSFER_TYPES);
                final Long[] ids = EntityHelper.toIds(pf.getTransferTypes());
                CollectionUtils.addAll(ttIds, ids);
            }
        }

        // Get the member group ids
        Set<Long> groupIds = null;
        if (CollectionUtils.isNotEmpty(params.getMemberGroups())) {
            groupIds = new HashSet<Long>();
            CollectionUtils.addAll(groupIds, EntityHelper.toIds(params.getMemberGroups()));
        }

        // Get the period
        final Period period = params.getPeriod();
        final QueryParameter beginParameter = HibernateHelper.getBeginParameter(period);
        final QueryParameter endParameter = HibernateHelper.getEndParameter(period);

        // Set the parameters
        final boolean useTT = CollectionUtils.isNotEmpty(ttIds);
        if (useTT) {
            parameters.put("ttIds", ttIds);
        }
        if (beginParameter != null) {
            parameters.put("beginDate", beginParameter.getValue());
        }
        if (endParameter != null) {
            parameters.put("endDate", endParameter.getValue());
        }
        parameters.put("processed", Payment.Status.PROCESSED.getValue());

        // Build the sql string
        sql.append(" select u.username, m.name, bu.username broker_username, b.name broker_name, h.account_type_name, h.date, h.amount, h.description, h.related_username, h.related_name, h.transfer_type_name, h.transaction_number");
        sql.append(" from members m inner join users u on m.id = u.id left join members b on m.member_broker_id = b.id left join users bu on b.id = bu.id,");
        sql.append(" (");
        if (params.isCredits()) {
            appendMembersTransactionsDetailsReportSqlPart(sql, useTT, beginParameter, endParameter, true, true);
            sql.append(" union");
            appendMembersTransactionsDetailsReportSqlPart(sql, useTT, beginParameter, endParameter, true, false);
            if (params.isDebits()) {
                sql.append(" union");
            }
        }
        if (params.isDebits()) {
            appendMembersTransactionsDetailsReportSqlPart(sql, useTT, beginParameter, endParameter, false, true);
            sql.append(" union");
            appendMembersTransactionsDetailsReportSqlPart(sql, useTT, beginParameter, endParameter, false, false);
        }
        sql.append(" ) h");
        sql.append(" where m.id = h.member_id");
        if (groupIds != null) {
            parameters.put("groupIds", groupIds);
            sql.append(" and m.group_id in (:groupIds)");
        }
        sql.append(" order by m.name, u.username, h.account_type_name, h.date desc, h.transfer_id desc");

        // Prepare the query
        final SQLQuery query = getSession().createSQLQuery(sql.toString());
        final Map<String, Type> columns = new LinkedHashMap<String, Type>();
        columns.put("username", StandardBasicTypes.STRING);
        columns.put("name", StandardBasicTypes.STRING);
        columns.put("broker_username", StandardBasicTypes.STRING);
        columns.put("broker_name", StandardBasicTypes.STRING);
        columns.put("account_type_name", StandardBasicTypes.STRING);
        columns.put("date", StandardBasicTypes.CALENDAR);
        columns.put("amount", StandardBasicTypes.BIG_DECIMAL);
        columns.put("description", StandardBasicTypes.STRING);
        columns.put("related_username", StandardBasicTypes.STRING);
        columns.put("related_name", StandardBasicTypes.STRING);
        columns.put("transfer_type_name", StandardBasicTypes.STRING);
        columns.put("transaction_number", StandardBasicTypes.STRING);
        for (final Map.Entry<String, Type> entry : columns.entrySet()) {
            query.addScalar(entry.getKey(), entry.getValue());
        }
        getHibernateQueryHandler().setQueryParameters(query, parameters);

        // Create a transformer, which will read rows as Object[] and transform them to MemberTransactionDetailsReportData
        final Transformer<Object[], MemberTransactionDetailsReportData> transformer = new Transformer<Object[], MemberTransactionDetailsReportData>() {
            @Override
            public MemberTransactionDetailsReportData transform(final Object[] input) {
                final MemberTransactionDetailsReportData data = new MemberTransactionDetailsReportData();
                int i = 0;
                for (final Map.Entry<String, Type> entry : columns.entrySet()) {
                    final String columnName = entry.getKey();
                    // Column names are transfer_type_name, property is transferTypeName
                    String propertyName = WordUtils.capitalize(columnName, COLUMN_DELIMITERS);
                    propertyName = Character.toLowerCase(propertyName.charAt(0)) + propertyName.substring(1);
                    propertyName = StringUtils.replace(propertyName, "_", "");
                    PropertyHelper.set(data, propertyName, input[i]);
                    i++;
                }
                return data;
            }
        };

        return new ScrollableResultsIterator<MemberTransactionDetailsReportData>(query, transformer);
    }

    @Override
    public Iterator<MemberTransactionSummaryVO> membersTransactionSummaryReport(final Collection<MemberGroup> memberGroups, final PaymentFilter paymentFilter, final Period period, final boolean credits, final MemberResultDisplay order) {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        final StringBuilder sql = new StringBuilder();

        // Get the transfer types ids
        final List<Long> ttIds = paymentFilter == null ? null : Arrays.asList(EntityHelper.toIds(paymentFilter.getTransferTypes()));

        // Get the member group ids
        List<Long> groupIds = null;
        if (CollectionUtils.isNotEmpty(memberGroups)) {
            groupIds = Arrays.asList(EntityHelper.toIds(memberGroups));
        }

        // Get the period
        final QueryParameter beginParameter = HibernateHelper.getBeginParameter(period);
        final QueryParameter endParameter = HibernateHelper.getEndParameter(period);

        // Set the parameters
        final boolean useGroups = CollectionUtils.isNotEmpty(groupIds);
        final boolean useTT = CollectionUtils.isNotEmpty(ttIds);
        if (useGroups) {
            parameters.put("groupIds", groupIds);
        }
        if (useTT) {
            parameters.put("ttIds", ttIds);
        }
        if (beginParameter != null) {
            parameters.put("beginDate", beginParameter.getValue());
        }
        if (endParameter != null) {
            parameters.put("endDate", endParameter.getValue());
        }
        parameters.put("processed", Payment.Status.PROCESSED.getValue());

        // Create the SQL query
        sql.append(" select member_id, sum(count) as count, sum(amount) as amount");
        sql.append(" from (");
        appendMembersTransactionsSummaryReportSqlPart(sql, useGroups, useTT, beginParameter, endParameter, credits, true);
        sql.append(" union");
        appendMembersTransactionsSummaryReportSqlPart(sql, useGroups, useTT, beginParameter, endParameter, credits, false);
        sql.append(" ) ts");
        sql.append(" group by member_id");
        sql.append(" order by ").append(order == MemberResultDisplay.NAME ? "member_name, member_id" : "username");

        final SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.addScalar("member_id", StandardBasicTypes.LONG);
        query.addScalar("count", StandardBasicTypes.INTEGER);
        query.addScalar("amount", StandardBasicTypes.BIG_DECIMAL);
        getHibernateQueryHandler().setQueryParameters(query, parameters);

        final Transformer<Object[], MemberTransactionSummaryVO> transformer = new Transformer<Object[], MemberTransactionSummaryVO>() {
            @Override
            public MemberTransactionSummaryVO transform(final Object[] input) {
                final MemberTransactionSummaryVO vo = new MemberTransactionSummaryVO();
                vo.setMemberId((Long) input[0]);
                vo.setCount((Integer) input[1]);
                vo.setAmount((BigDecimal) input[2]);
                return vo;
            }
        };

        return new ScrollableResultsIterator<MemberTransactionSummaryVO>(query, transformer);
    }

    @Override
    public List<Account> search(final AccountQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        Class<? extends Account> entityClass = getEntityType();
        if (query.getOwner() != null) {
            if (query.getOwner() instanceof SystemAccountOwner) {
                entityClass = SystemAccount.class;
            } else {
                entityClass = MemberAccount.class;
            }
        }
        final StringBuilder hql = HibernateHelper.getInitialQuery(entityClass, "a", fetch);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "a.type", query.getType());
        if (query.getOwner() instanceof Member) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "a.member", query.getOwner());
        }
        HibernateHelper.appendOrder(hql, "a.type.name");
        return list(query, hql.toString(), namedParameters);
    }

    private void appendMembersTransactionsDetailsReportSqlPart(final StringBuilder sql, final boolean useTT, final QueryParameter beginParameter, final QueryParameter endParameter, final boolean credits, final boolean notChargeBack) {
        final boolean flag = notChargeBack ? credits : !credits;
        final String account = flag ? "to_account_id" : "from_account_id";
        final String related = flag ? "from_account_id" : "to_account_id";
        sql.append(" select a.member_id, at.id as account_type_id, at.name account_type_name, t.id transfer_id, t.process_date date, " + (credits ? "" : "-1 * ") + "abs(t.amount) amount, t.description, ra.owner_name related_username, rm.name related_name, tt.name transfer_type_name, t.transaction_number");
        sql.append(" from transfers t inner join accounts a on t.").append(account).append(" = a.id inner join accounts ra on t.").append(related).append(" = ra.id inner join transfer_types tt on t.type_id = tt.id inner join account_types at on a.type_id = at.id left join members rm on ra.member_id = rm.id");
        sql.append(" where t.status = :processed");
        sql.append("   and t.chargeback_of_id is ").append(notChargeBack ? "" : "not ").append("null");
        if (useTT) {
            sql.append("   and t.type_id in (:ttIds)");
        }
        if (beginParameter != null) {
            sql.append("   and t.process_date " + beginParameter.getOperator() + " :beginDate");
        }
        if (endParameter != null) {
            sql.append("   and t.process_date " + endParameter.getOperator() + " :endDate");
        }
    }

    private void appendMembersTransactionsSummaryReportSqlPart(final StringBuilder sql, final boolean useGroups, final boolean useTT, final QueryParameter beginParameter, final QueryParameter endParameter, final boolean credits, final boolean notChargeBack) {
        final boolean flag = notChargeBack ? credits : !credits;
        final String account = flag ? "to_account_id" : "from_account_id";

        sql.append(" select m.id as member_id, m.name as member_name, u.username, count(t.id) as count, sum(abs(t.amount)) as amount");
        sql.append(" from transfers t inner join accounts a on t.").append(account).append(" = a.id inner join members m on a.member_id = m.id inner join users u on m.id = u.id");
        sql.append(" where t.status = :processed");
        sql.append("   and t.chargeback_of_id is ").append(notChargeBack ? "null" : "not null");
        if (useGroups) {
            sql.append("   and m.group_id in (:groupIds)");
        }
        if (useTT) {
            sql.append("   and t.type_id in (:ttIds)");
        }
        if (beginParameter != null) {
            sql.append("   and t.process_date " + beginParameter.getOperator() + " :beginDate");
        }
        if (endParameter != null) {
            sql.append("   and t.process_date " + endParameter.getOperator() + " :endDate");
        }
        sql.append(" group by m.id, m.name, u.username");
    }

    private TransactionSummaryVO buildSummary(final Object object) {
        final Object[] row = (Object[]) object;
        final int count = row[0] == null ? 0 : (Integer) row[0];
        final BigDecimal amount = row[1] == null ? BigDecimal.ZERO : (BigDecimal) row[1];
        return new TransactionSummaryVO(count, amount);
    }

    private TransactionSummaryVO getSummary(final GetTransactionsDTO dto, final boolean credits, final Transfer.Status status) {
        final Account account = load(dto.getOwner(), dto.getType());
        final Member relatedToMember = dto.getRelatedToMember();
        final Element by = dto.getBy();
        final Period period = dto.getPeriod();
        final Collection<PaymentFilter> paymentFilters = dto.getPaymentFilters();

        final StringBuilder hql = new StringBuilder();
        final Map<String, Object> namedParams = new HashMap<String, Object>();
        hql.append(" select count(*), sum(abs(t.amount))");
        hql.append(" from " + Transfer.class.getName() + " t");
        hql.append(" where ((t.amount > 0 and t.").append(credits ? "to" : "from").append(" = :account) ");
        hql.append("  or (t.amount < 0 and t.").append(credits ? "from" : "to").append(" = :account)) ");
        namedParams.put("account", account);
        HibernateHelper.addParameterToQuery(hql, namedParams, "t.status", status);

        // Count root transfers only
        if (dto.isRootOnly()) {
            hql.append(" and t.parent is null");
        }

        // Get only transfers related to (from or to) the specified member
        if (relatedToMember != null) {
            hql.append(" and exists (");
            hql.append("     select ma.id from MemberAccount ma ");
            hql.append("     where ma.member = :relatedToMember ");
            hql.append("     and (t.from = ma or t.to = ma) ");
            hql.append(" )");
            namedParams.put("relatedToMember", relatedToMember);
        }

        // Apply the payments filters
        if (CollectionUtils.isNotEmpty(paymentFilters)) {
            final Set<TransferType> transferTypes = new HashSet<TransferType>();
            for (PaymentFilter paymentFilter : paymentFilters) {
                if (paymentFilter == null || paymentFilter.isTransient()) {
                    continue;
                }
                paymentFilter = getFetchDao().fetch(paymentFilter, PaymentFilter.Relationships.TRANSFER_TYPES);
                if (paymentFilter.getTransferTypes() != null) {
                    transferTypes.addAll(paymentFilter.getTransferTypes());
                }
            }
            if (CollectionUtils.isNotEmpty(transferTypes)) {
                hql.append(" and t.type in (:transferTypes) ");
                namedParams.put("transferTypes", transferTypes);
            }
        }

        // Apply the operated by
        if (by != null) {
            hql.append(" and (t.by = :by or t.receiver = :by)");
            namedParams.put("by", by);
        }

        HibernateHelper.addPeriodParameterToQuery(hql, namedParams, "ifnull(t.processDate,t.date)", period);
        return buildSummary(uniqueResult(hql.toString(), namedParams));
    }
}
