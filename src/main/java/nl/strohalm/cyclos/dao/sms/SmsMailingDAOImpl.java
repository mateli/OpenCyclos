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
package nl.strohalm.cyclos.dao.sms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.dao.JDBCCallback;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.entities.sms.SmsMailingQuery;
import nl.strohalm.cyclos.entities.sms.SmsMailingQuery.Recipient;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * DAO implementation for SMS mailings
 * 
 * @author luis
 */
public class SmsMailingDAOImpl extends BaseDAOImpl<SmsMailing> implements SmsMailingDAO {

    public SmsMailingDAOImpl() {
        super(SmsMailing.class);
    }

    @Override
    public void assignUsersToSend(final SmsMailing smsMailing, final MemberCustomField smsCustomField) {
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                final Collection<MemberGroup> groups = smsMailing.getGroups();
                final Member broker = (Member) (LoggedUser.isBroker() ? LoggedUser.element() : null);

                final List<Object> params = new ArrayList<Object>();
                params.add(smsMailing.getId());
                params.add(smsCustomField.getId());

                final StringBuilder sql = new StringBuilder();
                sql.append(" insert into sms_mailings_pending_to_send (sms_mailing_id, member_id)");
                sql.append(" select ?, m.id ");
                sql.append(" from custom_field_values fv inner join members m on fv.member_id = m.id left join member_sms_status s on m.id = s.member_id inner join groups g on m.group_id = g.id");
                sql.append(" where fv.field_id = ?");
                sql.append("  and length(fv.string_value) > 0"); // Only if the custom field has value
                if (!smsMailing.isSingleMember() && CollectionUtils.isNotEmpty(groups)) {
                    // By admin - set the groups
                    sql.append(" and m.group_id in (");
                    for (final Iterator<MemberGroup> iterator = groups.iterator(); iterator.hasNext();) {
                        final MemberGroup group = iterator.next();
                        sql.append('?');
                        if (iterator.hasNext()) {
                            sql.append(',');
                        }
                        params.add(group.getId());
                    }
                    sql.append(" )");
                }
                if (broker != null) {
                    // By broker - set the broker
                    sql.append(" and m.member_broker_id = ?");
                    params.add(broker.getId());
                }
                if (smsMailing.isSingleMember()) {
                    // Mailing to member - set the member
                    sql.append(" and m.id = ?");
                    params.add(smsMailing.getMember().getId());
                }
                // Individual mailings are always free and delivered no matter the member preferences
                if (!smsMailing.isSingleMember()) {
                    // Ensure there's a MemberSmsStatus which accepts free / paid mailings
                    final String acceptColumn = smsMailing.isFree() ? "accept_free_mailing" : "accept_paid_mailing";
                    sql.append(" and (s.id is null and g.").append("member_default_").append(acceptColumn).append(" = ?").append(" or s.").append(acceptColumn).append(" = ?)");
                    params.add(true);
                    params.add(true);
                }
                jdbc.execute(sql.toString(), params.toArray());

                // Set the sent_sms as the number of members which will receive the message
                final String updateSentSQL = "update sms_mailings m set m.sent_sms = (select count(*) from sms_mailings_pending_to_send p where p.sms_mailing_id = m.id) where m.id = ?";
                jdbc.execute(updateSentSQL, smsMailing.getId());
            }
        });
    }

    public List<SmsMailing> listUnfinishedMailings() {
        return list("from SmsMailing m where exists elements(m.pendingToSend)", null);
    }

    @Override
    public Member nextMemberToSend(final SmsMailing smsMailing) {
        return (Member) getSession().createFilter(smsMailing.getPendingToSend(), "where 1=1").setMaxResults(1).uniqueResult();
    }

    @Override
    public void removeMemberFromPending(final SmsMailing smsMailing, final Member member) {
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                jdbc.execute("delete from sms_mailings_pending_to_send where sms_mailing_id = ? and member_id = ?", smsMailing.getId(), member.getId());
            }
        });
    }

    @Override
    public List<SmsMailing> search(final SmsMailingQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("select m from ").append(SmsMailing.class.getName()).append(" m ");
        HibernateHelper.appendJoinFetch(hql, SmsMailing.class, "m", query.getFetch());
        hql.append("left join fetch m.member mbr where 1=1 ");
        if (query.getRecipient() == Recipient.GROUPS) {
            hql.append(" and mbr is null");
        } else if (query.getRecipient() == Recipient.MEMBER) {
            hql.append(" and mbr is not null");
        }

        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "m.date", query.getPeriod());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "m.by", query.getBroker());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "m.finished", query.getFinished());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "m.member", query.getMember());
        if (CollectionUtils.isNotEmpty(query.getGroups())) {
            hql.append(" and ((mbr is not null");
            HibernateHelper.addInParameterToQuery(hql, namedParameters, "mbr.group", query.getGroups());
            hql.append(")");
            hql.append(" or exists (");
            hql.append("     select g.id");
            hql.append("     from MemberGroup g");
            hql.append("     where (g in elements(m.groups) and g in (:_groups_)) or m.by.group in (:_groups_)");
            namedParameters.put("_groups_", query.getGroups());
            hql.append(" ))");
        }
        HibernateHelper.appendOrder(hql, "m.date desc");
        return list(query, hql.toString(), namedParameters);
    }
}
