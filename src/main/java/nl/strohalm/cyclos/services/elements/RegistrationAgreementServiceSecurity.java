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
package nl.strohalm.cyclos.services.elements;

import java.util.List;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.PermissionCheck;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link RegistrationAgreementService}
 * 
 * @author Rinke
 */
public class RegistrationAgreementServiceSecurity extends BaseServiceSecurity implements RegistrationAgreementService {

    private RegistrationAgreementServiceLocal registrationAgreementService;

    @Override
    public List<RegistrationAgreement> listAll() {
        // called by listRegistrationAgreementAction and EditGroupAction
        checkView(true);
        return registrationAgreementService.listAll();
    }

    @Override
    public RegistrationAgreement load(final Long id, final Relationship... fetch) {
        checkView(false);
        return registrationAgreementService.load(id, fetch);
    }

    @Override
    public boolean remove(final Long id) {
        checkManage();
        return registrationAgreementService.remove(id);
    }

    @Override
    public RegistrationAgreement save(final RegistrationAgreement registrationAgreement) {
        checkManage();
        return registrationAgreementService.save(registrationAgreement);
    }

    public void setRegistrationAgreementServiceLocal(final RegistrationAgreementServiceLocal registrationAgreementService) {
        this.registrationAgreementService = registrationAgreementService;
    }

    @Override
    public void validate(final RegistrationAgreement registrationAgreement) throws ValidationException {
        // no permission check on validation
        registrationAgreementService.validate(registrationAgreement);
    }

    private void checkManage() {
        permissionService.permission()
                .admin(AdminSystemPermission.REGISTRATION_AGREEMENTS_MANAGE)
                .check();
    }

    private void checkView(final boolean isForList) {
        PermissionCheck check = permissionService.permission();
        check.admin(AdminSystemPermission.REGISTRATION_AGREEMENTS_VIEW);
        if (isForList) {
            check.admin(AdminSystemPermission.GROUPS_MANAGE_MEMBER, AdminSystemPermission.GROUPS_MANAGE_BROKER);
        }
        check.check();
    }

}
