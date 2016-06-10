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
package nl.strohalm.cyclos.services.accounts.pos;

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.accounts.pos.PosQuery;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;

/**
 * Security implementation for {@link PosService}
 * @author jcomas
 */
public class PosServiceSecurity extends BaseServiceSecurity implements PosService {

    private PosServiceLocal posService;

    @Override
    public Pos assignPos(final Member member, final Long posId) {
        if (posService.load(posId).getStatus() == Pos.Status.DISCARDED) {
            throw new PermissionDeniedException();
        }

        permissionService.permission(member)
                .admin(AdminMemberPermission.POS_ASSIGN)
                .broker(BrokerPermission.POS_ASSIGN)
                .check();

        checkManage(posId);
        return posService.assignPos(member, posId);
    }

    @Override
    public void deletePos(final Long... ids) {
        permissionService.permission()
                .admin(AdminMemberPermission.POS_MANAGE)
                .broker(BrokerPermission.POS_MANAGE)
                .check();

        for (Long posId : ids) {
            checkManage(posId);
        }
        posService.deletePos(ids);
    }

    @Override
    public Pos discardPos(final Long posId) {
        permissionService.permission()
                .admin(AdminMemberPermission.POS_DISCARD)
                .broker(BrokerPermission.POS_DISCARD)
                .member()
                .check();
        checkManage(true, posId);
        return posService.discardPos(posId);
    }

    @Override
    public Pos load(final Long id, final Relationship... fetch) {
        permissionService.permission()
                .admin(AdminMemberPermission.POS_VIEW)
                .broker(BrokerPermission.POS_VIEW)
                .member()
                .check();

        checkManage(true, id);
        return posService.load(id, fetch);
    }

    @Override
    public Pos loadByPosId(final String posId, final Relationship... fetch) {
        final Pos pos = posService.loadByPosId(posId, fetch);

        permissionService.permission()
                .admin(AdminMemberPermission.POS_VIEW)
                .broker(BrokerPermission.POS_VIEW)
                .member()
                .check();

        checkManage(true, pos.getId());
        return pos;
    }

    @Override
    public Pos save(final Pos pos) {
        permissionService.permission()
                .admin(AdminMemberPermission.POS_MANAGE)
                .broker(BrokerPermission.POS_MANAGE)
                .member()
                .check();

        if (!pos.isTransient()) {
            checkManage(true, pos.getId());
        }
        return posService.save(pos);
    }

    @Override
    public List<Pos> search(final PosQuery query) {
        if (!permissionService.hasPermission(AdminMemberPermission.POS_VIEW, BrokerPermission.POS_VIEW)) {
            return Collections.emptyList();
        }

        if (LoggedUser.isAdministrator()) {
            query.setManagedBy((AdminGroup) LoggedUser.group());
        } else if (LoggedUser.isBroker()) {
            query.setBroker((Member) LoggedUser.element());
        }
        return posService.search(query);
    }

    public void setPosServiceLocal(final PosServiceLocal posService) {
        this.posService = posService;
    }

    @Override
    public Pos unassignPos(final Long posId) {
        permissionService.permission()
                .admin(AdminMemberPermission.POS_ASSIGN)
                .broker(BrokerPermission.POS_ASSIGN).check();

        checkManage(posId);
        return posService.unassignPos(posId);
    }

    @Override
    public void validate(final Pos pos) {
        // Nothing to check
        posService.validate(pos);
    }

    private void checkManage(final boolean isMemberRequired, final Long posId) {
        final Pos pos = posService.load(posId, RelationshipHelper.nested(Pos.Relationships.MEMBER_POS, MemberPos.Relationships.MEMBER));
        if (pos.getMemberPos() != null) {
            Member currentMember = pos.getMemberPos().getMember();
            permissionService.checkManages(currentMember);
        } else if (isMemberRequired && LoggedUser.isMember() && !LoggedUser.isBroker()) {
            throw new PermissionDeniedException();
        }
    }

    private void checkManage(final Long posId) {
        checkManage(false, posId);
    }
}
