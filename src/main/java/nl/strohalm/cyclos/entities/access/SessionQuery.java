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
package nl.strohalm.cyclos.entities.access;

import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query for user sessions
 * 
 * @author luis
 */
public class SessionQuery extends QueryParameters {

    private static final long        serialVersionUID = 8363290518080175586L;
    private Collection<Group.Nature> natures;
    private Collection<Group>        groups;
    private Member                   member;

    public Collection<Group> getGroups() {
        return groups;
    }

    public Member getMember() {
        return member;
    }

    public Collection<Group.Nature> getNatures() {
        return natures;
    }

    public void setGroups(final Collection<Group> groups) {
        this.groups = groups;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setNatures(final Collection<Group.Nature> natures) {
        this.natures = natures;
    }

}
