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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.messages.Message.Type;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsLogQuery;
import nl.strohalm.cyclos.entities.sms.SmsLogReportQuery;
import nl.strohalm.cyclos.entities.sms.SmsLogStatus;
import nl.strohalm.cyclos.entities.sms.SmsLogType;
import nl.strohalm.cyclos.entities.sms.SmsMailingType;
import nl.strohalm.cyclos.entities.sms.SmsType;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation class for SMS log DAO
 * @author Jefferson Magno
 */
public class SmsLogDAOImpl extends BaseDAOImpl<SmsLog> implements SmsLogDAO {

    public SmsLogDAOImpl() {
        super(SmsLog.class);
    }

    @Override
    public List<SmsLogReportTotal> getReportTotals(final SmsLogQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        final String typeStr = "case when m is not null then 'MAILING'" +
                " when l.smsType.id is not null then 'SMS_OPERATION'" +
                " else 'NOTIFICATION' end";
        final String statusStr = "case when l.errorType is null then 'DELIVERED' else 'ERROR' end";
        hql.append("select new ").append(SmsLogReportTotal.class.getName()).append('(');
        hql.append(typeStr).append(',').append(statusStr).append(", count(l.id))");
        processQuery(query, hql, namedParameters, false);
        hql.append(" group by ").append(typeStr).append(',').append(statusStr);
        return list(hql.toString(), namedParameters);
    }

    @Override
    public List<SmsLog> search(final SmsLogQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select l");
        processQuery(query, hql, namedParameters, true);
        HibernateHelper.appendOrder(hql, "l.date desc");
        return list(query, hql.toString(), namedParameters);
    }

    private void processQuery(final SmsLogQuery query, final StringBuilder hql, final Map<String, Object> namedParameters, final boolean addFetch) {
        hql.append(" from SmsLog l left join l.smsMailing m left join m.by b");
        if (addFetch) {
            HibernateHelper.appendJoinFetch(hql, entityClass, "l", query.getFetch());
        }
        hql.append(" where 1=1");

        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "l.date", query.getPeriod());

        final Member member = query.getMember();
        if (member != null) {
            hql.append(" and (l.chargedMember = :member or l.targetMember = :member)");
            namedParameters.put("member", member);
        }

        final SmsLogStatus queryStatus = query.getStatus();
        if (queryStatus != null) {
            hql.append(" and l.errorType is ");
            if (queryStatus == SmsLogStatus.ERROR) {
                hql.append("not ");
            }
            hql.append("null");
        }

        final SmsLogType type = query.getType();
        if (type != null) {
            switch (type) {
                case MAILING:
                    Collection<SmsMailingType> mailingTypes = null;
                    if (query instanceof SmsLogReportQuery) {
                        mailingTypes = ((SmsLogReportQuery) query).getMailingTypes();
                    }
                    if (CollectionUtils.isEmpty(mailingTypes)) {
                        hql.append(" and l.smsMailing is not null");
                    } else {
                        namedParameters.put("admin", Element.Nature.ADMIN.getValue());
                        final StringBuilder expr = new StringBuilder();
                        expr.append("(case when m.member is not null then 'INDIVIDUAL' ");
                        expr.append("      when b.class =  :admin and m.free =  true then 'FREE_TO_GROUP' ");
                        expr.append("      when b.class =  :admin and m.free <> true then 'PAID_TO_GROUP' ");
                        expr.append("      when b.class <> :admin and m.free =  true then 'FREE_FROM_BROKER' ");
                        expr.append("      when b.class <> :admin and m.free <> true then 'PAID_FROM_BROKER' ");
                        expr.append("      else 'UNKNOWN' end)");
                        // The expression is string, so we must convert the mailing types to string
                        final Collection<String> mailingTypesStr = new ArrayList<String>(mailingTypes.size());
                        for (final SmsMailingType mailingType : mailingTypes) {
                            mailingTypesStr.add(mailingType.name());
                        }
                        HibernateHelper.addInParameterToQuery(hql, namedParameters, expr.toString(), mailingTypesStr);
                    }
                    break;
                case NOTIFICATION:
                    Collection<Type> messageTypes = null;
                    if (query instanceof SmsLogReportQuery) {
                        messageTypes = ((SmsLogReportQuery) query).getMessageTypes();
                    }
                    if (CollectionUtils.isEmpty(messageTypes)) {
                        hql.append(" and l.messageType is not null");
                    } else {
                        HibernateHelper.addInParameterToQuery(hql, namedParameters, "l.messageType", messageTypes);
                    }
                    break;
                case SMS_OPERATION:
                    Collection<SmsType> smsTypes = null;
                    if (query instanceof SmsLogReportQuery) {
                        smsTypes = ((SmsLogReportQuery) query).getSmsTypes();
                    }
                    if (CollectionUtils.isEmpty(smsTypes)) {
                        hql.append(" and l.smsType is not null");
                    } else {
                        HibernateHelper.addInParameterToQuery(hql, namedParameters, "l.smsType", smsTypes);
                    }
                    break;
            }
        }
    }
}
