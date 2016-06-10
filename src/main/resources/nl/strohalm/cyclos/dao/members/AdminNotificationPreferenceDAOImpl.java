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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreference;
import nl.strohalm.cyclos.entities.members.preferences.AdminNotificationPreferenceQuery;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * Implementation for
 * @author luis
 * @author Lucas Geiss
 */
public class AdminNotificationPreferenceDAOImpl extends BaseDAOImpl<AdminNotificationPreference> implements AdminNotificationPreferenceDAO {

    public AdminNotificationPreferenceDAOImpl() {
        super(AdminNotificationPreference.class);
    }

    @Override
    public AdminNotificationPreference load(final Administrator admin, final Relationship... fetch) throws DaoException {
        AdminNotificationPreference preference = uniqueResult("select p from " + getEntityType().getName() + " p where p.admin = :admin", Collections.singletonMap("admin", admin));
        if (preference == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        if (!ArrayUtils.isEmpty(fetch)) {
            preference = getFetchDao().fetch(preference, fetch);
        }
        return preference;
    }

    @Override
    public List<Administrator> searchAdmins(final AdminNotificationPreferenceQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = new StringBuilder();
        hql.append(" select a");
        hql.append(" from AdminNotificationPreference p join p.admin a where 1=1");
        HibernateHelper.addInParameterToQuery(hql, namedParameters, "a.group", query.getAdminGroups());
        if (query.isApplicationErrors()) {
            hql.append(" and p.applicationErrors = true");
        }
        if (query.isSystemInvoices()) {
            hql.append(" and p.systemInvoices = true");
        }
        if (query.getTransferType() != null) {
            hql.append(" and :transferType in elements(p.transferTypes)");
            namedParameters.put("transferType", query.getTransferType());
        }
        if (query.getNewMemberGroup() != null) {
            hql.append(" and :newMemberGroup in elements(p.newMembers)");
            namedParameters.put("newMemberGroup", query.getNewMemberGroup());
        }
        if (query.getNewPendingPayment() != null) {
            hql.append(" and :newPendingPayment in elements(p.newPendingPayments)");
            namedParameters.put("newPendingPayment", query.getNewPendingPayment());
        }
        if (query.getGuaranteeType() != null) {
            hql.append(" and :guaranteeType in elements (p.guaranteeTypes)");
            namedParameters.put("guaranteeType", query.getGuaranteeType());
        }
        if (query.getMessageCategory() != null) {
            hql.append(" and :messageCategory in elements(p.messageCategories)");
            hql.append(" and exists (select ag.id from AdminGroup ag where ag.id = a.group.id and :messageCategory in elements(ag.messageCategories))");
            namedParameters.put("messageCategory", query.getMessageCategory());
        }
        if (query.getSystemAlert() != null) {
            hql.append(" and :systemAlertType in elements(p.systemAlerts)");
            namedParameters.put("systemAlertType", query.getSystemAlert().getValue());
        }
        if (query.getMemberAlert() != null) {
            hql.append(" and :memberAlertType in elements(p.memberAlerts)");
            namedParameters.put("memberAlertType", query.getMemberAlert().getValue());
        }
        if (query.getMemberGroup() != null) {
            hql.append(" and exists (select ag.id from AdminGroup ag where ag.id = a.group.id and :memberGroup in elements(ag.managesGroups))");
            namedParameters.put("memberGroup", query.getMemberGroup());
        }
        if (CollectionUtils.isNotEmpty(query.getAccountTypes())) {
            int i = 0;
            for (final SystemAccountType accountType : query.getAccountTypes()) {
                final String paramName = "accountType" + ++i;
                hql.append(" and exists (select ag.id from AdminGroup ag where ag.id = a.group.id and :" + paramName + " in elements (ag.viewInformationOf))");
                namedParameters.put(paramName, accountType);
            }
        }
        return list(query, hql.toString(), namedParameters);
    }
}
