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
package nl.strohalm.cyclos.services.elements;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.BrokeringQuery.Status;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.elements.exceptions.CircularBrokeringException;
import nl.strohalm.cyclos.services.elements.exceptions.MemberAlreadyInBrokeringsException;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link BrokeringService}
 * 
 * @author Rinke
 */
public class BrokeringServiceSecurity extends BaseServiceSecurity implements BrokeringService {

    private BrokeringServiceLocal brokeringService;

    @SuppressWarnings("unchecked")
    @Override
    public BulkMemberActionResultVO bulkChangeMemberBroker(final FullTextMemberQuery query, final Member newBroker, final boolean suspendCommission, final String comments) {
        permissionService.permission(newBroker).admin(AdminMemberPermission.BULK_ACTIONS_CHANGE_BROKER).check();
        Collection<MemberGroup> queryGroups = (Collection<MemberGroup>) query.getGroups();
        queryGroups = fetchService.fetch(queryGroups);
        Collection<MemberGroup> allowedGroups = PermissionHelper.checkSelection(permissionService.getManagedMemberGroups(), queryGroups);
        // drop the removed groups.
        for (Iterator<MemberGroup> it = allowedGroups.iterator(); it.hasNext();) {
            if (it.next().isRemoved()) {
                it.remove();
            }
        }
        query.setGroups(allowedGroups);
        return brokeringService.bulkChangeMemberBroker(query, newBroker, suspendCommission, comments);
    }

    @Override
    public Brokering changeBroker(final ChangeBrokerDTO dto) throws MemberAlreadyInBrokeringsException, CircularBrokeringException {
        if (dto.getNewBroker() != null) {
            permissionService.checkManages(dto.getNewBroker());
        }
        permissionService.permission(dto.getMember()).admin(AdminMemberPermission.BROKERINGS_CHANGE_BROKER).check();
        return brokeringService.changeBroker(dto);
    }

    @Override
    public Brokering getBrokering(final Member broker, final Member member) throws ValidationException {
        permissionService.checkRelatesTo(broker);
        permissionService.permission(member)
                .admin(AdminMemberPermission.BROKERINGS_VIEW_MEMBERS)
                .broker()
                .check();
        return brokeringService.getBrokering(broker, member);
    }

    @Override
    public Collection<Status> listPossibleStatuses(final BrokerGroup brokerGroup) {
        permissionService.checkManages(brokerGroup);
        return brokeringService.listPossibleStatuses(brokerGroup);
    }

    @Override
    public List<Brokering> search(final BrokeringQuery query) {
        if (query.getBrokered() != null) {
            permissionService.checkRelatesTo(query.getBrokered());
        }
        if (query.getBroker() == null) {
            throw new ValidationException();
        }
        query.setGroups(PermissionHelper.checkSelection(permissionService.getVisibleMemberGroups(), query.getGroups()));
        permissionService.permission(query.getBroker())
                .admin(AdminMemberPermission.BROKERINGS_VIEW_MEMBERS)
                .member() // Here, member means the broker himself. We can't use broker() because he's not his own broker
                .check();
        return brokeringService.search(query);
    }

    public void setBrokeringServiceLocal(final BrokeringServiceLocal brokeringService) {
        this.brokeringService = brokeringService;
    }

    @Override
    public void validate(final ChangeBrokerDTO dto) throws ValidationException {
        // no permissions for validations
        brokeringService.validate(dto);
    }

}
