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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.dao.groups.GroupDAO;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordTypeQuery;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation for member record type dao
 * @author Jefferson Magno
 */
public class MemberRecordTypeDAOImpl extends BaseDAOImpl<MemberRecordType> implements MemberRecordTypeDAO {

    private GroupDAO groupDao;

    public MemberRecordTypeDAOImpl() {
        super(MemberRecordType.class);
    }

    @Override
    public int delete(final boolean flush, final Long... ids) {
        int count = 0;
        for (final Long id : ids) {
            try {
                final MemberRecordType type = load(id, MemberRecordType.Relationships.values());

                // Remove this record type from permissions
                for (final Group group : groupDao.search(new GroupQuery())) {
                    group.getMemberRecordTypes().remove(type);
                    if (group instanceof AdminGroup) {
                        final AdminGroup adminGroup = (AdminGroup) group;
                        adminGroup.getViewMemberRecordTypes().remove(type);
                        adminGroup.getCreateMemberRecordTypes().remove(type);
                        adminGroup.getModifyMemberRecordTypes().remove(type);
                        adminGroup.getDeleteMemberRecordTypes().remove(type);
                        adminGroup.getViewAdminRecordTypes().remove(type);
                        adminGroup.getCreateAdminRecordTypes().remove(type);
                        adminGroup.getModifyAdminRecordTypes().remove(type);
                        adminGroup.getDeleteAdminRecordTypes().remove(type);
                    } else if (group instanceof BrokerGroup) {
                        final BrokerGroup brokerGroup = (BrokerGroup) group;
                        brokerGroup.getBrokerMemberRecordTypes().remove(type);
                        brokerGroup.getBrokerCreateMemberRecordTypes().remove(type);
                        brokerGroup.getBrokerModifyMemberRecordTypes().remove(type);
                        brokerGroup.getBrokerDeleteMemberRecordTypes().remove(type);
                    }
                }

                for (final AdminGroup group : type.getViewableByAdminGroups()) {
                    group.getMemberRecordTypes().remove(type);
                }
                for (final AdminGroup group : type.getCreatableByAdminGroups()) {
                    group.getCreateMemberRecordTypes().remove(type);
                }

                getHibernateTemplate().delete(type);
                count++;
            } catch (final EntityNotFoundException e) {
                continue;
            }
        }
        if (flush) {
            getHibernateTemplate().flush();
        }
        return count;
    }

    @Override
    public List<MemberRecordType> search(final MemberRecordTypeQuery query) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "mrt", query.getFetch());
        if (query.getGroups() != null) {
            if (CollectionUtils.isNotEmpty(query.getGroups())) {
                List<Long> groupIds = Arrays.asList(EntityHelper.toIds(query.getGroups()));
                hql.append(" and exists (select g from Group g where g in elements(mrt.groups) and g.id in (:groupIds)) ");
                namedParameters.put("groupIds", groupIds);
            } else {
                return Collections.emptyList();
            }
        }
        if (query.getViewableByAdminGroup() != null) {
            hql.append(" and exists (select ag.id from AdminGroup ag where ag = :adminGroup and mrt in elements(ag.viewMemberRecordTypes) or mrt in elements(ag.viewAdminRecordTypes)) ");
            namedParameters.put("adminGroup", query.getViewableByAdminGroup());
        }
        if (query.getViewableByBrokerGroup() != null) {
            hql.append(" and exists (select bg.id from BrokerGroup bg where bg = :brokerGroup and mrt in elements(bg.brokerMemberRecordTypes)) ");
            namedParameters.put("brokerGroup", query.getViewableByBrokerGroup());
        }
        if (query.isShowMenuItem()) {
            hql.append(" and mrt.showMenuItem = true");
        }
        return list(query, hql.toString(), namedParameters);
    }

    public void setGroupDao(final GroupDAO groupDao) {
        this.groupDao = groupDao;
    }
}
