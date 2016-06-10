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
package nl.strohalm.cyclos.dao.groups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

import org.apache.commons.collections.CollectionUtils;

/**
 * Implementation class for group DAO
 * @author rafael
 * @author luis
 */
public class GroupDAOImpl extends BaseDAOImpl<Group> implements GroupDAO {

    public GroupDAOImpl() {
        super(Group.class);
    }

    @Override
    public int delete(final boolean flush, final Long... ids) {
        final int count = 0;
        for (final Long id : ids) {
            try {
                final Group group = load(id, MemberGroup.Relationships.CAN_VIEW_PROFILE_OF_GROUPS, MemberGroup.Relationships.CAN_VIEW_ADS_OF_GROUPS, MemberGroup.Relationships.POSSIBLE_INITIAL_GROUP_OF);
                if (group instanceof MemberGroup) {
                    final MemberGroup memberGroup = (MemberGroup) group;

                    final List<MemberGroup> otherGroups = list("from MemberGroup g where g.id <> :id", memberGroup);
                    for (final MemberGroup other : otherGroups) {
                        while (other.getCanViewProfileOfGroups().remove(memberGroup)) {
                        }
                        while (other.getCanViewAdsOfGroups().remove(memberGroup)) {
                        }
                    }

                    for (final BrokerGroup brokerGroup : memberGroup.getPossibleInitialGroupOf()) {
                        brokerGroup.getPossibleInitialGroups().remove(memberGroup);
                    }
                }

                for (final GroupFilter groupFilter : this.<GroupFilter> list("from " + GroupFilter.class.getName(), null)) {
                    groupFilter.getGroups().remove(group);
                    groupFilter.getViewableBy().remove(group);
                }

                getHibernateTemplate().delete(group);
            } catch (final EntityNotFoundException e) {
                continue;
            }
        }
        getHibernateTemplate().flush();
        return count;
    }

    @Override
    public SystemGroup findByLoginPageName(final String loginPageName) {
        final SystemGroup group = uniqueResult("from SystemGroup g where g.loginPageName = :name", Collections.singletonMap("name", loginPageName));
        if (group == null) {
            throw new EntityNotFoundException(SystemGroup.class);
        }
        return group;
    }

    @Override
    public Map<String, Integer> getGroupMemberCount() {
        final List<Object[]> list = list("select g.name, count(e.id) from MemberGroup g left join g.elements e group by g.class, g.name order by g.class desc, g.name", null);
        final Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        for (final Object[] row : list) {
            map.put((String) row[0], (Integer) row[1]);
        }
        return map;
    }

    @Override
    public List<OperatorGroup> iterateOperatorGroups(final MemberGroup memberGroup) {
        if (memberGroup == null) {
            return Collections.emptyList();
        }
        final String hql = "from OperatorGroup g where g.member.group = :group";
        final Map<String, ?> params = Collections.singletonMap("group", memberGroup);
        return list(ResultType.ITERATOR, hql, params, null);
    }

    @Override
    public List<MemberGroup> listActiveMemberGroups() {
        final StringBuilder hql = new StringBuilder();
        hql.append("from MemberGroup where size(accountSettings)>0");
        return list(hql.toString(), new HashMap<String, Object>());
    }

    @Override
    public List<? extends Group> search(final GroupQuery query) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "g", query.getFetch());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "g", query.getPossibleGroups());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "g.class", query.getNatureDiscriminators());
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "g.status", query.getStatusCollection());

        // Active groups (member groups and broker groups)
        if (query.isOnlyActive()) {
            hql.append(" and exists (select mg.id from MemberGroup mg where mg = g and mg.active = true) ");
        }

        // Group filters
        if (CollectionUtils.isNotEmpty(query.getGroupFilters())) {
            final Set<Group> groups = new HashSet<Group>();
            for (GroupFilter groupFilter : query.getGroupFilters()) {
                groupFilter = getFetchDao().fetch(groupFilter, GroupFilter.Relationships.GROUPS);
                groups.addAll(groupFilter.getGroups());
            }
            if (CollectionUtils.isNotEmpty(groups)) {
                hql.append(" and g in (:groups) ");
                namedParameters.put("groups", groups);
            }
        }

        // If a broker comes, retrieve the groups of brokered members.
        if (query.getBroker() != null) {
            hql.append("and exists (select mg.id from MemberGroup mg, Member m where mg = g and m.group = mg and m.broker = :broker) ");
            namedParameters.put("broker", query.getBroker());
        }

        // Member that is owner of the operator groups
        if (query.getMember() != null) {
            hql.append("and exists (select og.id from OperatorGroup og where og = g and og.member = :member) ");
            namedParameters.put("member", query.getMember());
        }

        // Groups related to the account type
        if (query.getMemberAccountType() != null) {
            hql.append(" and exists (select s.id from " + MemberGroupAccountSettings.class.getName() + " s where s.accountType = :accountType and s.group = g) ");
            namedParameters.put("accountType", query.getMemberAccountType());
        }

        HibernateHelper.addInElementsParameter(hql, namedParameters, "g.paymentFilters", query.getPaymentFilter());

        if (query.getManagedBy() != null) {
            hql.append(" and ((g.class = :adminGroup) or (g in (select mg from AdminGroup ag join ag.managesGroups mg where ag = :managedBy))) ");
            namedParameters.put("adminGroup", Group.Nature.ADMIN.getDiscriminator());
            namedParameters.put("managedBy", query.getManagedBy());
        }

        // The order is dinamically determined
        final List<String> order = new ArrayList<String>();
        if (query.isSortByNature()) {
            order.add("case g.class when 'A' then 1 when 'M' then 2 else 3 end");
        }
        order.add("g.name");
        HibernateHelper.appendOrder(hql, order.toArray(new String[order.size()]));
        return list(query, hql.toString(), namedParameters);
    }

}
