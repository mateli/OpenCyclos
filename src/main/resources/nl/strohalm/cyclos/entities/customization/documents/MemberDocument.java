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
package nl.strohalm.cyclos.entities.customization.documents;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.StringValuedEnum;

public class MemberDocument extends StaticDocument {

    public static enum Relationships implements Relationship {
        MEMBER("member");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static enum Visibility implements StringValuedEnum {
        MEMBER("M"), BROKER("B"), ADMIN("A");

        private String value;

        private Visibility(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    private static final long serialVersionUID = -788736495660804107L;

    private Member            member;
    private Visibility        visibility;

    public Member getMember() {
        return member;
    }

    @Override
    public Nature getNature() {
        return Nature.MEMBER;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setVisibility(final Visibility visibility) {
        this.visibility = visibility;
    }

}
