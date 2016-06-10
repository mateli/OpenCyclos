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

import java.io.FilenameFilter;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;

/**
 * This entity is the customization of a display file
 * @author luis
 */
public class CustomizedFile extends File {

    public static enum Relationships implements Relationship {
        GROUP("group"), GROUP_FILTER("groupFilter");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * A customized file type
     * @author luis
     */
    public static enum Type implements StringValuedEnum {
        STATIC_FILE("S", "jsp"), STYLE("C", "css"), HELP("H", "jsp"), APPLICATION_PAGE("A", "jsp");
        private final String         value;
        private final FilenameFilter filter;

        private Type(final String value, final String extension) {
            this.value = value;
            filter = new SuffixFileFilter("." + extension);
        }

        public FilenameFilter getFilter() {
            return filter;
        }

        public String getValue() {
            return value;
        }
    }

    private static final long serialVersionUID = 3264704140933610339L;
    private Type              type;
    private Group             group;
    private GroupFilter       groupFilter;
    private String            originalContents;
    private String            newContents;

    public Group getGroup() {
        return group;
    }

    public GroupFilter getGroupFilter() {
        return groupFilter;
    }

    public String getNewContents() {
        return newContents;
    }

    public String getOriginalContents() {
        return originalContents;
    }

    public String getRelativePath() {
        final String name = getName();
        if (name == null) {
            return null;
        }
        final int index = name.lastIndexOf('/');
        return name.substring(0, index);
    }

    public Type getType() {
        return type;
    }

    public boolean isConflict() {
        return StringUtils.isNotEmpty(newContents);
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public void setGroupFilter(final GroupFilter groupFilter) {
        this.groupFilter = groupFilter;
    }

    public void setNewContents(final String newContents) {
        this.newContents = StringHelper.removeCarriageReturnCharater(newContents);
    }

    public void setOriginalContents(final String originalContents) {
        this.originalContents = StringHelper.removeCarriageReturnCharater(originalContents);
    }

    public void setType(final Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final Group group = getGroup();
        if (group == null && groupFilter == null) {
            return super.toString();
        }
        return getId() + " - " + getName() + " for " + (group == null ? groupFilter : group);
    }

}
