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

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFileQuery;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for managing customized files (save, load, edit)
 * @author luis
 */
public interface CustomizedFileService extends Service {

    /**
     * Returns true if the logged user can view or manage the group's customized files
     * @param group The group of the customized files
     * @return
     */
    public boolean canViewOrManageInGroup(Group group);

    /**
     * Returns true if the logged user can view or manage the customized files declared in group filters.
     * @return
     */
    public boolean canViewOrManageInGroupFilters();

    /**
     * Stops customizing the file, leaving the file as it is on the file system.
     * @param customizedFile
     */
    public void stopCustomizing(final CustomizedFile customizedFile);

    /**
     * Loads a customized file by id
     */
    CustomizedFile load(Long id, Relationship... fetch);

    /**
     * Saves a customized file with no permission check. Should be used on internal procedures
     */
    CustomizedFile save(CustomizedFile customizedFile);

    /**
     * Saves a customized file.
     */
    CustomizedFile saveForTheme(CustomizedFile customizedFile);

    /**
     * Searches for customized files, according to the given parameters
     */
    List<CustomizedFile> search(CustomizedFileQuery query);

    /**
     * Validates a customized file
     */
    void validate(CustomizedFile customizedFile) throws ValidationException;

}
