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
package nl.strohalm.cyclos.dao.accounts.external;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

public class ExternalTransferTypeDAOImpl extends BaseDAOImpl<ExternalTransferType> implements ExternalTransferTypeDAO {

    public ExternalTransferTypeDAOImpl() {
        super(ExternalTransferType.class);
    }

    public List<ExternalTransferType> listAll() {
        return list("from ExternalTransferType ett order by ett.name", null);
    }

    public List<ExternalTransferType> listByAccount(final ExternalAccount account) {
        final Map<String, ExternalAccount> params = Collections.singletonMap("account", account);
        return list("from ExternalTransferType ett where ett.account = :account order by ett.name", params);
    }

    public ExternalTransferType load(final ExternalAccount account, final String code, final Relationship[] fetch) throws EntityNotFoundException {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", account);
        params.put("code", code);
        final List<ExternalTransferType> list = list("from ExternalTransferType ett where ett.account = :account and ett.code = :code", params);
        if (list.isEmpty()) {
            throw new EntityNotFoundException(ExternalTransferType.class);
        }
        ExternalTransferType type = list.iterator().next();
        if (fetch != null && fetch.length > 0) {
            type = load(type.getId(), fetch);
        }
        return type;
    }

}
