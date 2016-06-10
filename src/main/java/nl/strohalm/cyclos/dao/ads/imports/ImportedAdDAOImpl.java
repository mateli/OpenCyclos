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
package nl.strohalm.cyclos.dao.ads.imports;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.ads.imports.AdImport;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAd;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdQuery;
import nl.strohalm.cyclos.entities.ads.imports.ImportedAdQuery.Status;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

public class ImportedAdDAOImpl extends BaseDAOImpl<ImportedAd> implements ImportedAdDAO {

    public ImportedAdDAOImpl() {
        super(ImportedAd.class);
    }

    public List<ImportedAd> search(final ImportedAdQuery params) {
        final AdImport adImport = params.getAdImport();
        if (adImport == null || adImport.isTransient()) {
            return Collections.emptyList();
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "a", params.getFetch());
        HibernateHelper.addParameterToQuery(hql, namedParameters, "a.import", adImport);
        final Status status = params.getStatus();
        if (status != null && status != Status.ALL) {
            final String operator = status == Status.ERROR ? "<>" : "=";
            HibernateHelper.addParameterToQueryOperator(hql, namedParameters, "a.status", operator, ImportedMember.Status.SUCCESS);
        }
        HibernateHelper.addParameterToQuery(hql, namedParameters, "a.lineNumber", params.getLineNumber());
        HibernateHelper.appendOrder(hql, "a.lineNumber");
        return list(params, hql.toString(), namedParameters);
    }

}
