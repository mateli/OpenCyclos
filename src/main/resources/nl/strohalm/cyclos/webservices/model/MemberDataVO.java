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
package nl.strohalm.cyclos.webservices.model;

import java.io.Serializable;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

public class MemberDataVO implements Serializable {

    private static final long serialVersionUID      = 1L;

    private MemberVO          member;
    private Boolean           canAddMemberAsContact = false;

    public MemberDataVO(final MemberVO memberVO, final Boolean canAddMemberAsContact) {
        super();
        member = memberVO;
        this.canAddMemberAsContact = canAddMemberAsContact;
    }

    public boolean getCanAddMemberAsContact() {
        return ObjectHelper.valueOf(canAddMemberAsContact);
    }

    public MemberVO getMember() {
        return member;
    }

    @Override
    public String toString() {
        return "MemberDataVO [member=" + member + ", canAddMemberAsContact=" + canAddMemberAsContact + "]";
    }

}
