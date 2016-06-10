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
package nl.strohalm.cyclos.dao.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.access.PasswordHistoryLog;
import nl.strohalm.cyclos.entities.access.PasswordHistoryLog.PasswordType;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

import org.apache.commons.lang.StringUtils;

public class PasswordHistoryLogDAOImpl extends BaseDAOImpl<PasswordHistoryLog> implements PasswordHistoryLogDAO {

    public PasswordHistoryLogDAOImpl() {
        super(PasswordHistoryLog.class);
    }

    public boolean wasAlreadyUsed(final User user, final PasswordType type, final String password) {

        final Map<String, Object> namedParameters = new HashMap<String, Object>();

        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "h");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "h.user", user);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "h.type", type);
        HibernateHelper.addParameterToQuery(hql, namedParameters, "upper(h.password)", StringUtils.trimToEmpty(password).toUpperCase());

        final List<Object> list = list(ResultType.PAGE, hql.toString(), namedParameters, PageParameters.count());

        return PageHelper.getTotalCount(list) > 0;
    }

}
