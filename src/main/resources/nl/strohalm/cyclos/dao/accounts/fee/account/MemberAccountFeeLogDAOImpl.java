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
package nl.strohalm.cyclos.dao.accounts.fee.account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.dao.JDBCCallback;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLogQuery;
import nl.strohalm.cyclos.entities.accounts.fees.account.MemberAccountFeeLogQuery.Status;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings.MemberResultDisplay;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.query.PageParameters;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;

/**
 * Implementation for {@link MemberAccountFeeLogDAO}
 * 
 * @author luis
 */
public class MemberAccountFeeLogDAOImpl extends BaseDAOImpl<MemberAccountFeeLog> implements MemberAccountFeeLogDAO {

    public MemberAccountFeeLogDAOImpl() {
        super(MemberAccountFeeLog.class);
    }

    @Override
    public int countSkippedMembers(final AccountFeeLog log) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("log", log);
        StringBuilder hql = new StringBuilder();
        hql.append(" select count(*) ");
        hql.append(" from MemberAccountFeeLog l");
        hql.append(" where l.accountFeeLog = :log");
        hql.append("   and l.success = true");
        hql.append("   and l.transfer.id is null");
        hql.append("   and l.invoice.id is null");
        return this.<Integer> uniqueResult(hql.toString(), params);
    }

    @Override
    public TransactionSummaryVO getAcceptedInvoicesSummary(final AccountFeeLog log) {
        return doGetInvoicesSummary(log, true);
    }

    @Override
    public TransactionSummaryVO getInvoicesSummary(final AccountFeeLog log) {
        return doGetInvoicesSummary(log, false);
    }

    @Override
    public AccountFeeLog getLastChargedLog(final Member member, final AccountFee fee) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("member", member);
        params.put("fee", fee);
        String hql = "select ml.accountFeeLog from MemberAccountFeeLog ml where ml.member = :member and ml.accountFeeLog.accountFee = :fee order by ml.accountFeeLog.date desc, ml.id";
        return this.<AccountFeeLog> uniqueResult(hql, params);
    }

    @Override
    public TransactionSummaryVO getTransfersSummary(final AccountFeeLog log) {
        Map<String, ?> params = Collections.singletonMap("log", log);
        StringBuilder hql = new StringBuilder();
        hql.append(" select new nl.strohalm.cyclos.services.transactions.TransactionSummaryVO(count(*), sum(l.amount)) ");
        hql.append(" from MemberAccountFeeLog l");
        hql.append(" where l.accountFeeLog = :log");
        hql.append(" and l.transfer.id is not null");
        return this.<TransactionSummaryVO> uniqueResult(hql.toString(), params);
    }

    @Override
    public MemberAccountFeeLog load(final AccountFeeLog log, final Member member) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("log", log);
        params.put("member", member);
        return this.<MemberAccountFeeLog> uniqueResult("from MemberAccountFeeLog ml where ml.accountFeeLog = :log and ml.member = :member", params);
    }

    @Override
    public List<Member> nextFailedToCharge(final AccountFeeLog feeLog, final int count) {
        final Collection<MemberGroup> groups = feeLog.getAccountFee().getGroups();
        if (groups.isEmpty()) {
            // Ensure no members are charged, as there are no groups
            return Collections.emptyList();
        }
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("log", feeLog);
        final StringBuilder hql = new StringBuilder();
        hql.append(" select m");
        hql.append(" from MemberAccountFeeLog ml join ml.accountFeeLog l join ml.member m left join fetch m.user");
        hql.append(" where ml.success = false");
        hql.append("   and ml.rechargeAttempt < l.rechargeAttempt");
        hql.append("   and l = :log");
        return getHibernateQueryHandler().simpleList(null, hql.toString(), parameters, PageParameters.max(count));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Member> nextToCharge(final AccountFeeLog feeLog, final int count) {
        return getSession().createFilter(feeLog.getPendingToCharge(), "where 1=1").setMaxResults(count).list();
    }

    @Override
    public int prepareCharge(final AccountFeeLog log) {
        final Long[] groupIds = EntityHelper.toIds(log.getAccountFee().getGroups());
        if (ArrayUtils.isEmpty(groupIds)) {
            // No groups to charge
            return 0;
        }
        final MutableInt result = new MutableInt();
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                String[] placeHolders = new String[groupIds.length];
                Arrays.fill(placeHolders, "?");

                StringBuilder sql = new StringBuilder();
                sql.append(" insert into members_pending_charge");
                sql.append(" (account_fee_log_id, member_id)");
                sql.append(" select ?, id");
                sql.append(" from members m");
                sql.append(" where m.subclass = ?");
                sql.append(" and m.group_id in (").append(StringUtils.join(placeHolders, ",")).append(')');

                List<Object> args = new ArrayList<Object>(groupIds.length + 2);
                args.add(log.getId());
                args.add("M");
                CollectionUtils.addAll(args, groupIds);
                int totalMembers = jdbc.execute(sql.toString(), args.toArray());
                result.setValue(totalMembers);
            }
        });
        return result.intValue();
    }

    @Override
    public void remove(final AccountFeeLog log, final Member member) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("log", log);
        params.put("member", member);
        bulkUpdate("delete from MemberAccountFeeLog ml where ml.accountFeeLog = :log and ml.member = :member", params);
    }

    @Override
    public void removePendingCharge(final AccountFeeLog feeLog, final Member member) {
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                jdbc.execute("delete from members_pending_charge where account_fee_log_id = ? and member_id = ?", feeLog.getId(), member.getId());
            }
        });
    }

    @Override
    public List<MemberAccountFeeLog> search(final MemberAccountFeeLogQuery query, final MemberResultDisplay sort) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "m", query.getFetch());
        HibernateHelper.addParameterToQuery(hql, params, "m.accountFeeLog", query.getAccountFeeLog());
        HibernateHelper.addInParameterToQuery(hql, params, "m.member.group", query.getGroups());
        HibernateHelper.addParameterToQuery(hql, params, "m.member", query.getMember());
        Status status = query.getStatus();
        if (status != null) {
            // Status == ERROR will filter for success = false. All other statuses will add success = true
            HibernateHelper.addParameterToQuery(hql, params, "m.success", status != Status.ERROR);
            // Status.PROCESSED will not add any other filter, as it is only success = true
            switch (status) {
                case SKIPPED:
                    hql.append(" and m.transfer.id is null and m.invoice.id is null");
                    break;
                case TRANSFER:
                    hql.append(" and m.transfer.id is not null");
                    break;
                case INVOICE:
                    hql.append(" and m.invoice.id is not null");
                    break;
                case ACCEPTED_INVOICE:
                    HibernateHelper.addParameterToQuery(hql, params, "m.invoice.status", Invoice.Status.ACCEPTED);
                    break;
                case OPEN_INVOICE:
                    HibernateHelper.addParameterToQueryOperator(hql, params, "m.invoice.status", "<>", Invoice.Status.ACCEPTED);
                    break;
            }
        }
        HibernateHelper.appendOrder(hql, sort == MemberResultDisplay.NAME ? "m.member.name" : "m.member.user.username");
        return list(query, hql.toString(), params);
    }

    private TransactionSummaryVO doGetInvoicesSummary(final AccountFeeLog log, final boolean accepted) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("log", log);
        StringBuilder hql = new StringBuilder();
        hql.append(" select new nl.strohalm.cyclos.services.transactions.TransactionSummaryVO(count(*), sum(l.amount)) ");
        hql.append(" from MemberAccountFeeLog l");
        if (accepted) {
            hql.append(" join l.invoice i ");
        }
        hql.append(" where l.accountFeeLog = :log");
        if (accepted) {
            hql.append(" and i.status = :accepted");
            params.put("accepted", Invoice.Status.ACCEPTED);
        } else {
            hql.append(" and l.invoice.id is not null");
        }
        return this.<TransactionSummaryVO> uniqueResult(hql.toString(), params);
    }

}
