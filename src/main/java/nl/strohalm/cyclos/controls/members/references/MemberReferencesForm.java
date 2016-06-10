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
package nl.strohalm.cyclos.controls.members.references;

import nl.strohalm.cyclos.controls.BaseQueryForm;

/**
 * Form used to view a member's references and edit the reference to that member
 * @author luis
 */
public class MemberReferencesForm extends BaseQueryForm {

    private static final long serialVersionUID = -4025114831557363612L;
    private String            nature;
    private String            direction;
    private long              memberId;

    public String getDirection() {
        return direction;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getNature() {
        return nature;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setNature(final String nature) {
        this.nature = nature;
    }

}
