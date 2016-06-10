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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.dao.JDBCCallback;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.MessageBox;
import nl.strohalm.cyclos.entities.members.messages.MessageQuery;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for MessageDAO
 * @author luis
 */
public class MessageDAOImpl extends BaseDAOImpl<Message> implements MessageDAO {

    public MessageDAOImpl() {
        super(Message.class);
    }

    @Override
    public void assignPendingToSendByBroker(final Message message, final Member broker) {
        if (broker == null) {
            return;
        }
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                final StringBuilder sql = new StringBuilder();
                sql.append("insert into messages");
                sql.append(" (date,subject,type,direction,is_read,is_replied,is_html,from_member_id,body,email_sent,to_member_id)");
                sql.append(" select ?,?,?,?,?,?,?,?,?,?,id");
                sql.append(" from members m");
                sql.append(" where m.member_broker_id = ?");

                final List<Object> params = new ArrayList<Object>();
                params.add(message.getDate());
                params.add(message.getSubject());
                params.add(message.getType().getValue());
                params.add(Message.Direction.INCOMING.getValue());
                params.add(false);
                params.add(false);
                params.add(message.isHtml());
                params.add(broker.getId());
                params.add(message.getBody());
                params.add(false);
                params.add(broker.getId());

                jdbc.execute(sql.toString(), params.toArray());
            }
        });
    }

    @Override
    public void assignPendingToSendByGroups(final Message message, final Collection<MemberGroup> groups) {
        final Long[] groupIds = EntityHelper.toIds(groups);
        if (ArrayUtils.isEmpty(groupIds)) {
            return;
        }
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                final StringBuilder sql = new StringBuilder();

                sql.append("insert into messages");
                sql.append(" (date,subject,type,direction,is_read,is_replied,is_html,from_member_id,category_id,body,email_sent,to_member_id)");
                sql.append(" select ?,?,?,?,?,?,?,?,?,?,?,id");
                sql.append(" from members m");
                sql.append(" where m.group_id in (");
                final String[] placeHolders = new String[groupIds.length];
                Arrays.fill(placeHolders, "?");
                sql.append(StringUtils.join(placeHolders, ","));
                sql.append(")");

                final List<Object> params = new ArrayList<Object>();
                params.add(message.getDate());
                params.add(message.getSubject());
                params.add(message.getType().getValue());
                params.add(Message.Direction.INCOMING.getValue());
                params.add(false);
                params.add(false);
                params.add(message.isHtml());
                params.add(null);
                params.add(message.getCategory().getId());
                params.add(message.getBody());
                params.add(false);
                params.addAll(Arrays.asList(groupIds));

                jdbc.execute(sql.toString(), params.toArray());
            }
        });
    }

    @Override
    public Message nextToSend() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("incoming", Message.Direction.INCOMING);
        params.put("sent", false);
        return uniqueResult("from Message m where m.direction = :incoming and m.emailSent = :sent", params);
    }

    @Override
    public void removeMessagesOnTrashBefore(final Calendar limit) {
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                // First, we need to remove all rows in messages_to_groups for messages which would be deleted, to avoid constraint errors
                final String deleteMessagesGroups = "delete from messages_to_groups " +
                        " where exists ( " +
                        "     select 1 " +
                        "     from messages m " +
                        "     where m.removed_at < ? " +
                        "       and m.id = messages_to_groups.message_id) ";
                jdbc.execute(deleteMessagesGroups, limit);

                // Then, delete the messages
                final String deleteMessages = "delete from messages where removed_at < ?";
                jdbc.execute(deleteMessages, limit);
            }
        });
    }

    @Override
    public List<Message> search(final MessageQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "m", query.getFetch());

        // Apply the getter member (null when admin)
        Element getter = query.getGetter();
        if (getter instanceof Operator) {
            final Operator operator = getFetchDao().fetch((Operator) getter, Operator.Relationships.MEMBER);
            getter = operator.getMember();
        }
        MessageBox messageBox = query.getMessageBox();
        if (messageBox == null) {
            messageBox = MessageBox.INBOX;
        }
        switch (messageBox) {
            case SENT:
                if (getter instanceof Administrator) {
                    hql.append(" and m.fromMember is null ");
                } else if (getter instanceof Member) {
                    hql.append(" and m.fromMember= :getter ");
                }
                if (query.getRootType() == Message.RootType.ADMIN) {
                    hql.append(" and m.toMember is null ");
                } else if (query.getRootType() == Message.RootType.MEMBER) {
                    hql.append(" and m.toMember is not null ");
                } else if (query.getRootType() == Message.RootType.SYSTEM) {
                    HibernateHelper.addInParameterToQuery(hql, namedParameters, "m.type", Message.Type.listByRootType(Message.RootType.SYSTEM));
                }
                hql.append(" and m.direction = :outgoing and m.removedAt is null");
                break;
            case INBOX:
                if (getter instanceof Administrator) {
                    hql.append(" and m.toMember is null");
                    hql.append(" and (m.category is null or exists (select ag.id from AdminGroup ag where ag = :adminGroup and m.category in elements (ag.messageCategories)))");
                    namedParameters.put("adminGroup", getter.getGroup());
                } else if (getter instanceof Member) {
                    hql.append(" and m.toMember = :getter");
                }
                hql.append(" and m.direction = :incoming and m.removedAt is null");
                HibernateHelper.addInParameterToQuery(hql, namedParameters, "m.type", Message.Type.listByRootType(query.getRootType()));
                break;
            case TRASH:
                hql.append(" and m.removedAt is not null");
                if (getter instanceof Administrator) {
                    hql.append(" and ( ");
                    hql.append("       (m.direction =:outgoing and m.fromMember is null) or ");
                    hql.append("       (m.direction =:incoming and m.toMember is null) ");
                    hql.append(" ) ");
                } else {
                    hql.append(" and ( ");
                    hql.append("       (m.direction =:outgoing and m.fromMember = :getter ) or ");
                    hql.append("       (m.direction =:incoming and m.toMember = :getter) ");
                    hql.append(" ) ");
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown message box: " + messageBox);
        }
        namedParameters.put("getter", getter);
        namedParameters.put("incoming", Message.Direction.INCOMING);
        namedParameters.put("outgoing", Message.Direction.OUTGOING);

        // Apply the related member
        final Member relatedMember = query.getRelatedMember();
        if (relatedMember != null) {
            hql.append(" and ((m.toMember = :relatedMember and m.direction = :outgoing) or (m.fromMember = :relatedMember and m.direction = :incoming))");
            namedParameters.put("relatedMember", relatedMember);
        }

        // Apply keywords
        if (StringUtils.isNotEmpty(query.getKeywords())) {
            hql.append(" and ((upper(m.subject) like :keywords) or (upper(m.body) like :keywords))");
            namedParameters.put("keywords", "%" + query.getKeywords().toUpperCase() + "%");
        }

        // Apply simple parameters
        HibernateHelper.addParameterToQuery(hql, namedParameters, "m.read", query.getRead());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "m.category", query.getCategories());
        HibernateHelper.appendOrder(hql, "m.date desc");
        return list(query, hql.toString(), namedParameters);
    }

}
