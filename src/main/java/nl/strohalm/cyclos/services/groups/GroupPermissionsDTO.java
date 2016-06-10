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
package nl.strohalm.cyclos.services.groups;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import nl.strohalm.cyclos.access.ModuleType;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.collections.CollectionUtils;

/**
 * Class used to store a group's permissions
 * @author luis
 */
public abstract class GroupPermissionsDTO<G extends Group> extends DataObject {
    private static final long         serialVersionUID = 6176708478971547330L;

    private G                         group;
    private Collection<Permission>    operations;
    private Collection<GuaranteeType> guaranteeTypes;
    private Collection<TransferType>  conversionSimulationTTs;

    public Collection<TransferType> getConversionSimulationTTs() {
        return conversionSimulationTTs;
    }

    public G getGroup() {
        return group;
    }

    public Collection<GuaranteeType> getGuaranteeTypes() {
        return guaranteeTypes;
    }

    public Collection<Permission> getOperations() {
        return operations;
    }

    public void setConversionSimulationTTs(final Collection<TransferType> conversionSimulationTTs) {
        this.conversionSimulationTTs = conversionSimulationTTs;
    }

    public void setGroup(final G group) {
        this.group = group;
    }

    public void setGuaranteeTypes(final Collection<GuaranteeType> guaranteeTypes) {
        this.guaranteeTypes = guaranteeTypes;
    }

    public void setOperations(final Collection<Permission> operations) {
        this.operations = operations;
    }

    public void update(final G group) {
        updateOperations();
        checkOperations(group);
        updateCollections(group);
    }

    /**
     * Checks if all the permissions are of the appropriate module type.
     * @param group
     */
    protected void checkOperations(final G group) {
        final Collection<Permission> permissions = getOperations();
        final List<ModuleType> moduleTypes = ModuleType.getModuleTypes(group.getNature());
        for (final Permission permission : permissions) {
            if (!(moduleTypes.contains(permission.getModule().getType()))) {
                throw new PermissionDeniedException();
            }
        }
    }

    /**
     * Updates the operations collection adding or removing the permission according to the permissionCollection
     * @param permission the permission to be added/removed
     * @param permissionCollection it it's empty the permission will be removed if not it will be added
     */
    protected void update(final Permission permission, final Collection<? extends Entity> permissionCollection) {
        if (CollectionUtils.isEmpty(permissionCollection)) {
            operations.remove(permission);
        } else {
            operations.add(permission);
        }
    }

    protected void updateCollections(final G group) {
        group.setPermissions(new HashSet<Permission>(getOperations()));

        group.setGuaranteeTypes(getGuaranteeTypes());
        group.setConversionSimulationTTs(getConversionSimulationTTs());
    }

    /**
     * Updates the dto before saving permissions. Basically, check each collection to set the specific permission.
     */
    protected abstract void updateOperations();
}
