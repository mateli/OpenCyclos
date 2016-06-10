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
package nl.strohalm.cyclos.taglibs;

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.TagSupport;

import nl.strohalm.cyclos.entities.customization.files.CustomizedFile.Type;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;

/**
 * Abstract customized file tag
 * @author luis
 */
public abstract class AbstractCustomizedFileTag extends TagSupport {
    private static final long serialVersionUID = 7545420264637927211L;
    private String            type;
    private String            name;
    private Long              groupId;
    private Long              groupFilterId;

    public Long getGroupFilterId() {
        return groupFilterId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public void release() {
        super.release();
        type = null;
        name = null;
        groupId = null;
        groupFilterId = null;
    }

    public void setGroupFilterId(Long groupFilterId) {
        if (groupFilterId != null && groupFilterId == 0L) {
            groupFilterId = null;
        }
        this.groupFilterId = groupFilterId;
    }

    public void setGroupId(Long groupId) {
        if (groupId != null && groupId == 0L) {
            groupId = null;
        }
        this.groupId = groupId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setType(final String type) {
        this.type = type;
    }

    protected String resolvePath() {
        final ServletContext servletContext = pageContext.getServletContext();
        final CustomizationHelper customizationHelper = SpringHelper.bean(servletContext, CustomizationHelper.class);

        final Type type = resolveType();
        final Group group = resolveGroup();
        final GroupFilter groupFilter = resolveGroupFilter();
        return customizationHelper.findPathOf(type, group, groupFilter, name);
    }

    protected Type resolveType() {
        if (type != null) {
            type = type.toLowerCase();
            if (type.startsWith("static")) {
                return Type.STATIC_FILE;
            } else if (type.startsWith("help")) {
                return Type.HELP;
            } else if (type.startsWith("style")) {
                return Type.STYLE;
            }
        }
        throw new IllegalArgumentException("Invalid customized file type '" + type + "'. Valid are 'static', 'help' or 'style'");
    }

    private Group resolveGroup() {
        // If a group is set, use it...
        if (groupId != null) {
            return EntityHelper.reference(Group.class, groupId);
        }
        // ..., else, get the logged user's one, if any
        return LoggedUser.hasUser() ? LoggedUser.group() : null;
    }

    private GroupFilter resolveGroupFilter() {
        if (groupFilterId != null) {
            return EntityHelper.reference(GroupFilter.class, groupFilterId);
        }
        return null;
    }
}
