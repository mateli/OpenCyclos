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
package nl.strohalm.cyclos.dao.sms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.sms.SmsType;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

public class SmsTypeDAOImpl extends BaseDAOImpl<SmsType> implements SmsTypeDAO {
    public SmsTypeDAOImpl() {
        super(SmsType.class);
    }

    @Override
    public Collection<SmsType> list() {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "type");
        HibernateHelper.appendOrder(hql, "type.order asc");

        return list(hql.toString(), namedParameters);
    }

    @Override
    public SmsType loadByCode(final String code) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(getEntityType(), "type");
        HibernateHelper.addParameterToQuery(hql, namedParameters, "code", code);

        return uniqueResult(hql.toString(), namedParameters);
    }
}
