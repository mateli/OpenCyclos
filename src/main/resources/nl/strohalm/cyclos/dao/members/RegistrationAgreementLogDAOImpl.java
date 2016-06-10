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
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.RegistrationAgreementLog;

/**
 * Implementation for {@link RegistrationAgreementLogDAO}
 * 
 * @author luis
 */
public class RegistrationAgreementLogDAOImpl extends BaseDAOImpl<RegistrationAgreementLog> implements RegistrationAgreementLogDAO {

    public RegistrationAgreementLogDAOImpl() {
        super(RegistrationAgreementLog.class);
    }

    public List<RegistrationAgreementLog> listByMember(final Member member) {
        final Map<String, ?> namedParameters = Collections.singletonMap("member", member);
        final String hql = "from " + getEntityType().getName() + " l where l.member = :member order by l.date";
        return list(hql, namedParameters);
    }
}
