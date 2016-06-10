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

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.services.BaseServiceSecurity;

/**
 * Security implementation for {@link MemberPosService}
 * 
 * @author jcomas
 */
public class MemberPosServiceSecurity extends BaseServiceSecurity implements MemberPosService {

    private MemberPosServiceLocal memberPosService;

    @Override
    public MemberPos blockMemberPos(final MemberPos memberPos) {
        permissionService.permission(memberPos.getMember())
                .admin(AdminMemberPermission.POS_BLOCK)
                .broker(BrokerPermission.POS_BLOCK)
                .member()
                .check();
        return memberPosService.blockMemberPos(memberPos);
    }

    @Override
    public MemberPos changePin(final MemberPos memberPos, final String pin) {
        permissionService.permission(memberPos.getMember())
                .admin(AdminMemberPermission.POS_CHANGE_PIN)
                .broker(BrokerPermission.POS_CHANGE_PIN)
                .member()
                .check();
        return memberPosService.changePin(memberPos, pin);
    }

    @Override
    public MemberPos load(final Long id, final Relationship... fetch) {
        MemberPos memberPos = memberPosService.load(id, fetch);
        permissionService.permission(memberPos.getMember())
                .admin(AdminMemberPermission.POS_VIEW)
                .broker(BrokerPermission.POS_VIEW)
                .member()
                .check();
        return memberPos;
    }

    @Override
    public void save(final MemberPos memberPos) {
        permissionService.permission(memberPos.getMember())
                .admin(AdminMemberPermission.POS_MANAGE)
                .broker(BrokerPermission.POS_MANAGE)
                .member()
                .check();
        memberPosService.save(memberPos);
    }

    public void setMemberPosServiceLocal(final MemberPosServiceLocal memberPosService) {
        this.memberPosService = memberPosService;
    }

    @Override
    public MemberPos unblockMemberPos(final MemberPos memberPos) {
        permissionService.permission(memberPos.getMember())
                .admin(AdminMemberPermission.POS_BLOCK)
                .broker(BrokerPermission.POS_BLOCK)
                .member()
                .check();
        return memberPosService.unblockMemberPos(memberPos);
    }

    @Override
    public MemberPos unblockPosPin(final MemberPos memberPos) {
        permissionService.permission(memberPos.getMember())
                .admin(AdminMemberPermission.POS_UNBLOCK_PIN)
                .broker(BrokerPermission.POS_UNBLOCK_PIN)
                .member()
                .check();
        return memberPosService.unblockPosPin(memberPos);
    }

}
