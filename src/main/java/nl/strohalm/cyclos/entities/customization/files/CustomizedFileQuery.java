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
package nl.strohalm.cyclos.entities.customization.files;

import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for customized files
 * @author luis
 */
public class CustomizedFileQuery extends QueryParameters {

    private static final long   serialVersionUID = 5709363175118973316L;
    private CustomizedFile.Type type;
    private Group               group;
    private GroupFilter         groupFilter;
    private boolean             all;

    public Group getGroup() {
        return group;
    }

    public GroupFilter getGroupFilter() {
        return groupFilter;
    }

    public CustomizedFile.Type getType() {
        return type;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(final boolean all) {
        this.all = all;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public void setGroupFilter(final GroupFilter groupFilter) {
        this.groupFilter = groupFilter;
    }

    public void setType(final CustomizedFile.Type type) {
        this.type = type;
    }
}
