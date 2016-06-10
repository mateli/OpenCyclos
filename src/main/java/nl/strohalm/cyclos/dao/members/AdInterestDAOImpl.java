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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterest;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterestQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * Implementation for ad interests dao
 * @author jefferson
 */
public class AdInterestDAOImpl extends BaseDAOImpl<AdInterest> implements AdInterestDAO {

    public AdInterestDAOImpl() {
        super(AdInterest.class);
    }

    public Iterator<Member> resolveMembersToNotify(final Ad ad) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("ad", ad);
        namedParameters.put("ownerGroup", ad.getOwner().getGroup());

        final StringBuilder hql = new StringBuilder();
        hql.append(" select m ");
        hql.append(" from Member m, Ad ad ");
        hql.append(" where ad = :ad ");
        hql.append("   and :ownerGroup in elements(m.group.canViewAdsOfGroups) ");
        hql.append("   and ad.owner <> m ");
        hql.append("   and exists ( ");
        hql.append("     select ai.id ");
        hql.append("     from AdInterest ai ");
        hql.append("     where ai.owner = m ");
        hql.append("       and ai.type = ad.tradeType ");
        hql.append("       and (ai.category is null or ai.category = ad.category) ");
        hql.append("       and (ai.member is null or ai.member = ad.owner) ");
        hql.append("       and (ai.initialPrice is null or ai.initialPrice <= ad.price) ");
        hql.append("       and (ai.finalPrice is null or ai.finalPrice >= ad.price) ");
        hql.append("       and (ai.currency is null or ai.currency = ad.currency) ");
        hql.append("       and (ai.keywords is null or (upper(ad.title) like upper(concat('%', ai.keywords, '%'))  ");
        hql.append("                                    or (upper(ad.description) like upper(concat('%', ai.keywords, '%')) )) ");
        hql.append("       ) ");
        hql.append("       and (ai.groupFilter is null or exists ( ");
        hql.append("           select gf.id ");
        hql.append("           from GroupFilter gf ");
        hql.append("           where m.group in elements(gf.groups) ");
        hql.append("             and gf = ai.groupFilter ");
        hql.append("          ) ");
        hql.append("       ) ");
        hql.append("    ) ");
        return iterate(hql.toString(), namedParameters);
    }

    public List<AdInterest> search(final AdInterestQuery query) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final Set<Relationship> fetch = query.getFetch();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "ai", fetch);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "ai.owner", query.getOwner());
        return list(query, hql.toString(), namedParameters);
    }

}
