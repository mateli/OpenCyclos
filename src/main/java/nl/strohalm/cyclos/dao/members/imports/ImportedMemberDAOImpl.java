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
package nl.strohalm.cyclos.dao.members.imports;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberQuery;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberQuery.Status;
import nl.strohalm.cyclos.entities.members.imports.MemberImport;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

import org.apache.commons.lang.StringUtils;

public class ImportedMemberDAOImpl extends BaseDAOImpl<ImportedMember> implements ImportedMemberDAO {

    public ImportedMemberDAOImpl() {
        super(ImportedMember.class);
    }

    public TransactionSummaryVO getTransactions(final MemberImport memberImport, final boolean credits) {
        final StringBuilder hql = new StringBuilder();
        hql.append(" select new nl.strohalm.cyclos.services.transactions.NegativeAllowedTransactionSummaryVO(count(*), sum(m.initialBalance))");
        hql.append(" from " + getEntityType().getName() + " m");
        hql.append(" where m.status = :success");
        hql.append("   and m.import = :import");
        hql.append("   and m.initialBalance" + (credits ? ">=" : "<") + " :zero");
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("success", ImportedMember.Status.SUCCESS);
        namedParameters.put("import", memberImport);
        namedParameters.put("zero", BigDecimal.ZERO);
        return uniqueResult(hql.toString(), namedParameters);
    }

    public List<ImportedMember> search(final ImportedMemberQuery params) {
        final MemberImport memberImport = params.getMemberImport();
        if (memberImport == null || memberImport.isTransient()) {
            return Collections.emptyList();
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "m", params.getFetch());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "m.import", memberImport);
        final Status status = params.getStatus();
        if (status != null && status != Status.ALL) {
            final String operator = status == Status.ERROR ? "<>" : "=";
            HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "m.status", operator, ImportedMember.Status.SUCCESS);
        }
        HibernateHelper.addParameterToQuery(hql, namedParameters, "m.lineNumber", params.getLineNumber());
        final String nameOrUsername = StringUtils.trimToNull(params.getNameOrUsername());
        if (nameOrUsername != null) {
            hql.append(" and (upper(m.name) like :nameOrUsername or upper(m.username) like :nameOrUsername)");
            namedParameters.put("nameOrUsername", "%" + nameOrUsername.toUpperCase() + "%");
        }
        HibernateHelper.appendOrder(hql, "m.lineNumber");
        return list(params, hql.toString(), namedParameters);
    }
}
