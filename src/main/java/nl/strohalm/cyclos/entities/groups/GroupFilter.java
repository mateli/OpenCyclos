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

import java.util.Collection;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;

/**
 * This entity groups several related permission groups. May also be seen as a "community".
 * @author luis
 */
public class GroupFilter extends Entity {

    public static enum Relationships implements Relationship {
        GROUPS("groups"), VIEWABLE_BY("viewableBy"), CUSTOMIZED_FILES("customizedFiles");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long          serialVersionUID = 2784535628459241343L;
    private String                     name;
    private String                     description;
    private String                     loginPageName;
    private String                     rootUrl;
    private String                     containerUrl;
    private boolean                    showInProfile;
    private Collection<MemberGroup>    groups;
    private Collection<MemberGroup>    viewableBy;
    private Collection<CustomizedFile> customizedFiles;

    public String getContainerUrl() {
        return containerUrl;
    }

    public Collection<CustomizedFile> getCustomizedFiles() {
        return customizedFiles;
    }

    public String getDescription() {
        return description;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public String getLoginPageName() {
        return loginPageName;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public Collection<MemberGroup> getViewableBy() {
        return viewableBy;
    }

    public boolean isShowInProfile() {
        return showInProfile;
    }

    public void setContainerUrl(final String containerUrl) {
        this.containerUrl = containerUrl;
    }

    public void setCustomizedFiles(final Collection<CustomizedFile> customizedFiles) {
        this.customizedFiles = customizedFiles;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setLoginPageName(final String loginPageName) {
        this.loginPageName = loginPageName;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRootUrl(final String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public void setShowInProfile(final boolean showInProfile) {
        this.showInProfile = showInProfile;
    }

    public void setViewableBy(final Collection<MemberGroup> viewableBy) {
        this.viewableBy = viewableBy;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }

}
