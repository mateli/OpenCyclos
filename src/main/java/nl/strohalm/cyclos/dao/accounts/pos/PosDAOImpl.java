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
package nl.strohalm.cyclos.dao.accounts.pos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.accounts.pos.PosQuery;
import nl.strohalm.cyclos.entities.accounts.pos.PosQuery.QueryStatus;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author rodrigo
 */
public class PosDAOImpl extends BaseDAOImpl<Pos> implements PosDAO {

    public PosDAOImpl() {
        super(Pos.class);
    }

    public List<Pos> getAllMemberPos(final Member member) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("select p from " + getEntityType().getName() + " p");
        hql.append(" left join p.memberPos mp  ");
        hql.append(" where mp.member=:member");
        namedParameters.put("member", member);
        return list(hql.toString(), namedParameters);
    }

    public Pos loadByPosId(final String posId, final Relationship... fetch) throws EntityNotFoundException {
        if (!StringUtils.isEmpty(posId)) {
            final Map<String, Object> namedParameters = new HashMap<String, Object>();
            final StringBuilder hql = new StringBuilder("select p from " + getEntityType().getName() + " p");
            hql.append(" where 1 = 1");
            HibernateHelper.addParameterToQuery(hql, namedParameters, "p.posId", posId);
            final Pos pos = uniqueResult(hql.toString(), namedParameters);
            if (pos == null) {
                throw new EntityNotFoundException(Pos.class);
            }
            return getFetchDao().fetch(pos, fetch);
        } else {
            throw new EntityNotFoundException(Pos.class);
        }
    }

    public List<Pos> search(final PosQuery query) throws DaoException {
        final Collection<Pos.Status> posStatuses = new ArrayList<Pos.Status>();
        final Collection<MemberPos.Status> memberPosStatuses = new ArrayList<MemberPos.Status>();

        final Collection<QueryStatus> statuses = query.getStatuses();
        if (statuses != null) {
            for (final QueryStatus status : statuses) {
                if (status.getPosStatus() != null) {
                    posStatuses.add(status.getPosStatus());
                } else {
                    memberPosStatuses.add(status.getMemberPosStatus());
                }
            }
        }

        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("select p from " + getEntityType().getName() + " p");
        hql.append(" left join p.memberPos mp");
        hql.append(" left join mp.member m");
        hql.append(" left join m.group g");
        hql.append(" where 1 = 1");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "p.posId", query.getPosId());
        if (!posStatuses.isEmpty() && !memberPosStatuses.isEmpty()) {
            hql.append(" and (p.status in (:posStatuses) or (mp.status in (:memberPosStatuses)))");
            namedParameters.put("posStatuses", posStatuses);
            namedParameters.put("memberPosStatuses", memberPosStatuses);
        } else if (posStatuses.isEmpty() && !memberPosStatuses.isEmpty()) {
            HibernateHelper.addInParameterToQuery(hql, namedParameters, "mp.status", memberPosStatuses);
        } else if (!posStatuses.isEmpty() && memberPosStatuses.isEmpty()) {
            HibernateHelper.addInParameterToQuery(hql, namedParameters, "p.status", posStatuses);
        }
        HibernateHelper.addParameterToQuery(hql, namedParameters, "mp.member", query.getMember());
        if (query.getBroker() != null) {
            hql.append(" and (m.broker = :broker or p.memberPos is null or p.status = :status) ");
            namedParameters.put("status", Pos.Status.UNASSIGNED);
            namedParameters.put("broker", query.getBroker());
        }

        if (query.getManagedBy() != null) {
            hql.append(" and( p.memberPos is null or p.status = :status or " +
                    "  g in (select mg from AdminGroup ag join ag.managesGroups mg where ag = :managedBy)) ");
            namedParameters.put("managedBy", query.getManagedBy());
        }

        HibernateHelper.appendOrder(hql, "p.posId", "m.name");

        return list(query, hql.toString(), namedParameters);
    }

}
