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
package nl.strohalm.cyclos.dao.accounts;

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Implementation DAO for member group account settings
 * @author rafael
 * @author luis
 */
public class MemberGroupAccountSettingsDAOImpl extends BaseDAOImpl<MemberGroupAccountSettings> implements MemberGroupAccountSettingsDAO {

    public MemberGroupAccountSettingsDAOImpl() {
        super(MemberGroupAccountSettings.class);
    }

    public void delete(final long groupId, final long accountTypeId) {
        getHibernateTemplate().delete(load(groupId, accountTypeId));
    }

    public MemberGroupAccountSettings load(final long groupId, final long accountTypeId, final Relationship... fetch) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("accountTypeId", accountTypeId);
        final MemberGroupAccountSettings mgas = uniqueResult("from " + getEntityType().getName() + " where group.id = :groupId and accountType.id = :accountTypeId", params);
        if (mgas == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        if (fetch != null && fetch.length > 0) {
            return load(mgas.getId(), fetch);
        } else {
            return mgas;
        }
    }

}
