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
package nl.strohalm.cyclos.entities.members.records;

import java.util.Collection;

import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Parameters for member record types queries
 * @author Jefferson Magno
 */
public class MemberRecordTypeQuery extends QueryParameters {

    private static final long serialVersionUID = 8390867603265964679L;
    private Collection<Group> groups;
    private AdminGroup        viewableByAdminGroup;
    private BrokerGroup       viewableByBrokerGroup;
    private boolean           showMenuItem;

    public Collection<Group> getGroups() {
        return groups;
    }

    public AdminGroup getViewableByAdminGroup() {
        return viewableByAdminGroup;
    }

    public BrokerGroup getViewableByBrokerGroup() {
        return viewableByBrokerGroup;
    }

    public boolean isShowMenuItem() {
        return showMenuItem;
    }

    public void setGroups(final Collection<Group> groups) {
        this.groups = groups;
    }

    public void setShowMenuItem(final boolean showMenuItem) {
        this.showMenuItem = showMenuItem;
    }

    public void setViewableByAdminGroup(final AdminGroup viewableByAdminGroup) {
        this.viewableByAdminGroup = viewableByAdminGroup;
    }

    public void setViewableByBrokerGroup(final BrokerGroup viewableByBrokerGroup) {
        this.viewableByBrokerGroup = viewableByBrokerGroup;
    }

}
