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

import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.dao.customizations.CustomizedFileDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.alerts.SystemAlert.Alerts;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile.Type;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.services.alerts.AlertServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for customized file service
 * @author luis
 */
public class CustomizedFileServiceImpl implements CustomizedFileServiceLocal {

    private FetchServiceLocal      fetchService;
    private CustomizedFileDAO      customizedFileDao;
    private AlertServiceLocal      alertService;
    private PermissionServiceLocal permissionService;

    @Override
    public boolean canManageSystemCustomizedFiles() {
        return permissionService.hasPermission(AdminSystemPermission.CUSTOMIZED_FILES_MANAGE);
    }

    @Override
    public boolean canViewOrManageInGroup(Group group) {
        group = fetchService.fetch(group);
        switch (group.getNature()) {
            case ADMIN:
                return permissionService.hasPermission(AdminSystemPermission.ADMIN_GROUPS_MANAGE_ADMIN_CUSTOMIZED_FILES);
            case MEMBER:
            case BROKER:
                return permissionService.hasPermission(AdminMemberPermission.GROUPS_MANAGE_MEMBER_CUSTOMIZED_FILES);
            case OPERATOR:
                return permissionService.permission(((OperatorGroup) group).getMember()).member(MemberPermission.OPERATORS_MANAGE).hasPermission();
        }
        return false;
    }

    @Override
    public boolean canViewOrManageInGroupFilters() {
        return permissionService.hasPermission(AdminSystemPermission.GROUP_FILTERS_MANAGE_CUSTOMIZED_FILES);
    }

    @Override
    public boolean canViewSystemCustomizedFiles() {
        return permissionService.hasPermission(AdminSystemPermission.CUSTOMIZED_FILES_VIEW);
    }

    @Override
    public CustomizedFile load(final Long id, final Relationship... fetch) {
        return customizedFileDao.load(id, fetch);
    }

    @Override
    public CustomizedFile load(final Type type, final String name, final Relationship... fetch) {
        return customizedFileDao.load(type, name, fetch);
    }

    @Override
    public void notifyNewVersion(final Alerts alertType, final CustomizedFile customizedFile) {
        alertService.create(alertType, customizedFile.getName());
    }

    @Override
    public CustomizedFile save(final CustomizedFile customizedFile) {
        if (customizedFile.getGroup() != null) {
            customizedFile.setGroup(fetchService.fetch(customizedFile.getGroup()));
        }

        if (customizedFile.getGroupFilter() != null) {
            customizedFile.setGroupFilter(fetchService.fetch(customizedFile.getGroupFilter()));
        }

        return doSave(customizedFile);
    }

    @Override
    public CustomizedFile saveForTheme(final CustomizedFile customizedFile) {
        return doSave(customizedFile);
    }

    @Override
    public List<CustomizedFile> search(final CustomizedFileQuery query) {
        return customizedFileDao.search(query);
    }

    public void setAlertServiceLocal(final AlertServiceLocal alertService) {
        this.alertService = alertService;
    }

    public void setCustomizedFileDao(final CustomizedFileDAO customizedFileDAO) {
        customizedFileDao = customizedFileDAO;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void stopCustomizing(CustomizedFile customizedFile) {
        customizedFile = fetchService.fetch(customizedFile, CustomizedFile.Relationships.GROUP);
        customizedFileDao.delete(customizedFile.getId());
    }

    @Override
    public void validate(final CustomizedFile customizedFile) {
        getValidator().validate(customizedFile);
    }

    private CustomizedFile doSave(final CustomizedFile customizedFile) {
        validate(customizedFile);
        if (customizedFile.isTransient() && customizedFile.getGroup() == null && customizedFile.getGroupFilter() == null) {
            // Check a file with that name and type is already customized
            try {
                final CustomizedFile current = load(customizedFile.getType(), customizedFile.getName());
                // The file exists - We shall update it's contents only
                current.setLastModified(Calendar.getInstance());
                current.setContents(customizedFile.getContents());
                current.setOriginalContents(customizedFile.getOriginalContents());
                current.setNewContents(customizedFile.getNewContents());
                return customizedFileDao.update(current);
            } catch (final EntityNotFoundException e) {
                // Ok - Not already customized
            }
        }
        if (customizedFile.isTransient()) {
            customizedFile.setLastModified(Calendar.getInstance());
            return customizedFileDao.insert(customizedFile);
        } else {
            // Load the current version to update the contents
            final CustomizedFile current = load(customizedFile.getId(), CustomizedFile.Relationships.GROUP);
            current.setLastModified(Calendar.getInstance());
            current.setContents(customizedFile.getContents());
            current.setOriginalContents(customizedFile.getOriginalContents());
            current.setNewContents(customizedFile.getNewContents());
            return customizedFileDao.update(current);
        }
    }

    private Validator getValidator() {
        final Validator validator = new Validator("customizedFile");
        validator.property("name").required().maxLength(100);
        validator.property("type").required();
        return validator;
    }

}
