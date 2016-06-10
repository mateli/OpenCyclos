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
package nl.strohalm.cyclos.dao.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceClientQuery;
import nl.strohalm.cyclos.utils.InternetAddressHelper;
import nl.strohalm.cyclos.utils.hibernate.HibernateHelper;

/**
 * DAO implementation for service clients
 * @author luis
 */
public class ServiceClientDAOImpl extends BaseDAOImpl<ServiceClient> implements ServiceClientDAO {

    public ServiceClientDAOImpl() {
        super(ServiceClient.class);
    }

    public List<ServiceClient> search(final ServiceClientQuery query) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        final StringBuilder hql = HibernateHelper.getInitialQuery(ServiceClient.class, "c", query.getFetch());
        final String address = query.getAddress();
        if (InternetAddressHelper.isSimpleIp(address)) {
            hql.append(" and :address between c.addressBegin and c.addressEnd");
            namedParameters.put("address", InternetAddressHelper.padAddress(address));
        }

        namedParameters.put("blank", "");

        final String username = query.getUsername();
        if (username != null) {
            if (username.length() == 0) {
                hql.append(" and (c.username is null or c.username = :blank)");
            } else {
                hql.append(" and c.username = :username");
                namedParameters.put("username", username);
            }
        }

        final String password = query.getPassword();
        if (password != null) {
            if (password.length() == 0) {
                hql.append(" and (c.password is null or c.password = :blank)");
            } else {
                hql.append(" and c.password = :password");
                namedParameters.put("password", password);
            }
        }

        HibernateHelper.appendOrder(hql, "c.name");
        return list(query, hql.toString(), namedParameters);
    }
}
