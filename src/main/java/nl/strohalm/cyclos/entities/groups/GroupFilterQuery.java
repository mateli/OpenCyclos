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
package nl.strohalm.cyclos.entities.groups;

import nl.strohalm.cyclos.utils.query.QueryParameters;

public class GroupFilterQuery extends QueryParameters {

    private static final long serialVersionUID = 8236482092368339542L;
    private String            description;
    private String            name;
    private MemberGroup       group;
    private MemberGroup       viewableBy;
    private AdminGroup        adminGroup;

    public AdminGroup getAdminGroup() {
        return adminGroup;
    }

    public String getDescription() {
        return description;
    }

    public MemberGroup getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public MemberGroup getViewableBy() {
        return viewableBy;
    }

    public void setAdminGroup(final AdminGroup adminGroup) {
        this.adminGroup = adminGroup;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setGroup(final MemberGroup group) {
        this.group = group;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setViewableBy(final MemberGroup viewableBy) {
        this.viewableBy = viewableBy;
    }

}
