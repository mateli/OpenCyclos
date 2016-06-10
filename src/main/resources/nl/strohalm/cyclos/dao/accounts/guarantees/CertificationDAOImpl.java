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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;

public class CertificationDAOImpl extends BaseDAOImpl<Certification> implements CertificationDAO {

    public CertificationDAOImpl() {
        super(Certification.class);
    }

    @Override
    public List<Certification> getActiveCertificationsForBuyer(final Member buyer, final Currency currency) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "cert");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "cert.buyer", buyer);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "cert.status", Certification.Status.ACTIVE);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "cert.guaranteeType.currency", currency);

        return list(hql.toString(), namedParameters);
    }

    @Override
    public BigDecimal getUsedAmount(final Certification certification) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder("SELECT SUM(g.amount) FROM Guarantee g WHERE 1=1");

        HibernateHelper.addParameterToQuery(hql, namedParameters, "g.certification", certification);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "g.status", Guarantee.Status.ACCEPTED);

        return uniqueResult(hql.toString(), namedParameters);
    }

    @Override
    public List<Certification> seach(final CertificationQuery queryParameters) throws DaoException {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "cert", queryParameters.getFetch());

        if (queryParameters.getViewer() != null) { // restricts the result to this viewer
            hql.append("and (cert.buyer = :viewer or cert.issuer = :viewer) ");
            namedParameters.put("viewer", queryParameters.getViewer());
        }

        HibernateHelper.addInParameterToQuery(hql, namedParameters, "cert.status", queryParameters.getStatusList());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "cert.buyer", queryParameters.getBuyer());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "cert.issuer", queryParameters.getIssuer());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "cert.validity.begin", queryParameters.getStartIn());
        HibernateHelper.addPeriodParameterToQuery(hql, namedParameters, "cert.validity.end", queryParameters.getEndIn());

        if (CollectionUtils.isNotEmpty(queryParameters.getManagedMemberGroups())) {
            hql.append(" and (cert.buyer.group in (:groups_) and cert.issuer.group in (:groups_))");
            namedParameters.put("groups_", queryParameters.getManagedMemberGroups());
        }
        HibernateHelper.appendOrder(hql, "cert.validity.end asc");
        return list(queryParameters, hql.toString(), namedParameters);
    }
}
