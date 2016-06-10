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
package nl.strohalm.cyclos.services.customization;

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link CustomizedFileService}
 * 
 * @author jcomas
 */
public class CustomizedFileServiceSecurity extends BaseServiceSecurity implements CustomizedFileService {

    private CustomizedFileServiceLocal customizedFileService;

    @Override
    public boolean canViewOrManageInGroup(final Group group) {
        return customizedFileService.canViewOrManageInGroup(group);
    }

    @Override
    public boolean canViewOrManageInGroupFilters() {
        return customizedFileService.canViewOrManageInGroupFilters();
    }

    @Override
    public CustomizedFile load(final Long id, final Relationship... fetch) {
        CustomizedFile customizedFile = customizedFileService.load(id, fetch);
        if (!canView(customizedFile)) {
            throw new PermissionDeniedException();
        }
        return customizedFile;
    }

    @Override
    public CustomizedFile save(final CustomizedFile customizedFile) {
        if (!canManage(customizedFile)) {
            throw new PermissionDeniedException();
        }
        return customizedFileService.save(customizedFile);
    }

    @Override
    public CustomizedFile saveForTheme(final CustomizedFile customizedFile) {
        permissionService.permission().admin(AdminSystemPermission.THEMES_SELECT).check();
        return customizedFileService.saveForTheme(customizedFile);
    }

    @Override
    public List<CustomizedFile> search(final CustomizedFileQuery query) {
        if (!applyQueryRestrictions(query)) {
            return Collections.emptyList();
        }
        return customizedFileService.search(query);
    }

    public void setCustomizedFileServiceLocal(final CustomizedFileServiceLocal customizedFileService) {
        this.customizedFileService = customizedFileService;
    }

    @Override
    public void stopCustomizing(final CustomizedFile customizedFile) {
        if (!canManage(customizedFile)) {
            throw new PermissionDeniedException();
        }
        customizedFileService.stopCustomizing(customizedFile);
    }

    @Override
    public void validate(final CustomizedFile customizedFile) throws ValidationException {
        // No permissions required to validate.
        customizedFileService.validate(customizedFile);
    }

    private boolean applyQueryRestrictions(final CustomizedFileQuery query) {
        // Only system can query all customized files - and system shouldn't pass through the security layer
        query.setAll(false);

        if (query.getGroup() != null) {
            return canViewOrManageInGroup(query.getGroup());
        } else if (query.getGroupFilter() != null) {
            return canViewOrManageInGroupFilters();
        } else {
            return customizedFileService.canViewSystemCustomizedFiles();
        }
    }

    /**
     * Auxiliary method that returns true if the logged user can manage (or view) the customized file.
     * @param manage Flag to indicate if manage or view check is required.
     * @param customizedFile
     * @return
     */
    private boolean can(final boolean manage, final CustomizedFile customizedFile) {
        final Group group = fetchService.fetch(customizedFile.getGroup());
        GroupFilter groupFilter = fetchService.fetch(customizedFile.getGroupFilter());
        if (group != null) { // Group's customized file
            return canViewOrManageInGroup(group);
        } else if (groupFilter != null) { // Group filter's customized file
            return canViewOrManageInGroupFilters();
        } else { // System wide customized file
            if (manage) {
                return customizedFileService.canManageSystemCustomizedFiles();
            } else {
                return customizedFileService.canViewSystemCustomizedFiles();
            }
        }
    }

    /**
     * Returns true if the logged user can manage the customized file.
     * @param customizedFile
     * @return
     */
    private boolean canManage(final CustomizedFile customizedFile) {
        return can(true, customizedFile);
    }

    /**
     * Returns true if the logged user can view the customized file.
     * @param customizedFile
     * @return
     */
    private boolean canView(final CustomizedFile customizedFile) {
        return can(false, customizedFile);
    }

}
