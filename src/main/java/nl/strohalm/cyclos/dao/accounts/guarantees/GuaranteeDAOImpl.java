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
package nl.strohalm.cyclos.dao.accounts.guarantees;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.hibernate.HibernateCustomFieldHandler;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

public class GuaranteeDAOImpl extends BaseDAOImpl<Guarantee> implements GuaranteeDAO {

    private HibernateCustomFieldHandler hibernateCustomFieldHandler;

    public GuaranteeDAOImpl() {
        super(Guarantee.class);
    }

    @Override
    public Collection<MemberGroup> getBuyers(final Group seller) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = HibernateHelper.getInitialQuery(Group.class, "buyer");
        HibernateHelper.addInElementsParameter(hql, namedParameters, "buyer.canBuyWithPaymentObligationsFromGroups", seller);

        return list(hql.toString(), namedParameters);
    }

    @Override
    public Collection<MemberGroup> getIssuers(final GuaranteeType guaranteeType) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = HibernateHelper.getInitialQuery(Group.class, "issuer");
        HibernateHelper.addInElementsParameter(hql, namedParameters, "issuer.guaranteeTypes", guaranteeType);

        return list(hql.toString(), namedParameters);

    }

    @Override
    public Collection<GuaranteeType.Model> getRelatedGuaranteeModels(final Member member) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("member_", member);

        final String hql = "select distinct(g.guaranteeType.model) from Guarantee g where g.issuer = :member_ or g.seller = :member_ or g.buyer = :member_";

        return list(hql, namedParameters);
    }

    @Override
    public Collection<MemberGroup> getSellers(final Group issuer) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("issuer_", issuer);

        final StringBuilder hql = new StringBuilder("select distinct(seller) from Group seller, Group buyer, Group issuer where buyer in elements(issuer.canIssueCertificationToGroups) and seller in elements(buyer.canBuyWithPaymentObligationsFromGroups) and issuer = :issuer_");

        return list(hql.toString(), namedParameters);
    }

    @Override
    public Guarantee loadFromTransfer(final Transfer rootTransfer) {
        Map<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "g");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "g.loan.transfer", rootTransfer);

        return uniqueResult(hql.toString(), namedParameters);
    }

    @Override
    public List<Guarantee> search(final GuaranteeQuery queryParameters) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = new StringBuilder();
        hql.append(" select g");
        hql.append(" from ").append(getEntityType().getName()).append(" g ");
        hibernateCustomFieldHandler.appendJoins(hql, "g.customValues", queryParameters.getCustomValues());
        HibernateHelper.appendJoinFetch(hql, getEntityType(), "g", queryParameters.getFetch());
        hql.append(" left join g.buyer buyer left join g.seller seller ");
        hql.append(" where 1=1");
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "g.status", queryParameters.getStatusList());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "g.issuer", queryParameters.getIssuer());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "g.buyer", queryParameters.getBuyer());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "g.seller", queryParameters.getSeller());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "g.validity.begin", queryParameters.getStartIn());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "g.validity.end", queryParameters.getEndIn());
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "g.amount", ">=", queryParameters.getAmountLowerLimit());
        HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "g.amount", "<=", queryParameters.getAmountUpperLimit());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "g.certification", queryParameters.getCertification());
        if (queryParameters.getGuaranteeType() != null) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "g.guaranteeType", queryParameters.getGuaranteeType());
        } else { // search for (if not null) only the allowed guarantee types
            HibernateHelper.addInParameterToQuery(hql, namedParameters, "g.guaranteeType", queryParameters.getAllowedGuaranteeTypes());
        }
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "g.registrationDate", queryParameters.getRegisteredIn());

        // if hasn't got loan filter or if it's ALL we don't add filter
        if (queryParameters.getLoanFilter() != null && queryParameters.getLoanFilter() != GuaranteeQuery.LoanFilter.ALL) {
            hql.append(" and g.loan is ");
            if (queryParameters.getLoanFilter() == GuaranteeQuery.LoanFilter.WITH_LOAN) {
                hql.append("not null");
            } else { // without loan
                hql.append("null");
            }
        }

        // this was added to support a member who has the two roles buyer and seller at the same time
        if (queryParameters.getLoggedMember() != null) {
            hql.append(" and (g.buyer = :logged_ or g.seller = :logged_)");
            namedParameters.put("logged_", queryParameters.getLoggedMember());
        }
        // this was added to support search by any guarantee type
        if (queryParameters.getMember() != null) {
            hql.append(" and (g.buyer = :member_ or g.seller = :member_)");
            namedParameters.put("member_", queryParameters.getMember());
        }
        // Custom fields
        hibernateCustomFieldHandler.appendConditions(hql, namedParameters, queryParameters.getCustomValues());

        if (queryParameters.isWithBuyerOnly()) {
            HibernateHelper.addParameterToQuery(hql, namedParameters, "g.guaranteeType.model", GuaranteeType.Model.WITH_BUYER_ONLY);
        }

        if (CollectionUtils.isNotEmpty(queryParameters.getManagedMemberGroups())) {
            hql.append(" and (buyer.group in (:groups_) and (seller is null or seller.group in (:groups_)))");
            namedParameters.put("groups_", queryParameters.getManagedMemberGroups());
        }

        HibernateHelper.appendOrder(hql, "g.id desc");
        return list(queryParameters, hql.toString(), namedParameters);
    }

    public void setHibernateCustomFieldHandler(final HibernateCustomFieldHandler hibernateCustomFieldHandler) {
        this.hibernateCustomFieldHandler = hibernateCustomFieldHandler;
    }
}
