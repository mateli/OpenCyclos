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
package nl.strohalm.cyclos.themes;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Base theme handler implementing the permission's control<br>
 * All theme implementations must extends this
 * @author ameyer
 */
public abstract class BaseThemeHandler implements ThemeHandler {

    private PermissionService permissionService;

    public void export(final Theme theme, final OutputStream out) {
        if (!permissionService.hasPermission(AdminSystemPermission.THEMES_EXPORT)) {
            throw new PermissionDeniedException();
        }
        doExport(theme, out);
    }

    public void importNew(final String fileName, final InputStream in) {
        if (!permissionService.hasPermission(AdminSystemPermission.THEMES_IMPORT)) {
            throw new PermissionDeniedException();
        }
        doImportNew(fileName, in);
    }

    public List<Theme> list() {
        if (!permissionService.hasPermission(AdminSystemPermission.THEMES_SELECT)) {
            throw new PermissionDeniedException();
        }
        return doList();
    }

    public void remove(final String fileName) {
        if (!permissionService.hasPermission(AdminSystemPermission.THEMES_REMOVE)) {
            throw new PermissionDeniedException();
        }
        doRemove(fileName);
    }

    public void select(final String fileName) {
        if (!permissionService.hasPermission(AdminSystemPermission.THEMES_SELECT)) {
            throw new PermissionDeniedException();
        }
        doSelect(fileName);
    }

    public void setPermissionService(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public void validateForExport(final Theme theme) throws ValidationException {
        if (!permissionService.hasPermission(AdminSystemPermission.THEMES_EXPORT)) {
            throw new PermissionDeniedException();
        }
        doValidateForExport(theme);
    }

    protected abstract void doExport(final Theme theme, final OutputStream out);

    protected abstract void doImportNew(final String fileName, final InputStream in);

    protected abstract List<Theme> doList();

    protected abstract void doRemove(final String fileName);

    protected abstract void doSelect(final String fileName);

    protected abstract void doValidateForExport(final Theme theme) throws ValidationException;
}
