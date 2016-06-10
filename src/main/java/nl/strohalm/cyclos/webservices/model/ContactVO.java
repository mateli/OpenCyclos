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

/**
 * Contains data about contacts for web services
 * @author luis
 */
public class ContactVO extends EntityVO {

    private static final long serialVersionUID = 1L;

    private MemberVO          member;
    private String            notes;

    public MemberVO getMember() {
        return member;
    }

    public String getNotes() {
        return notes;
    }

    public void setMember(final MemberVO member) {
        this.member = member;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

}
