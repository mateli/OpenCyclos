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

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberQuery;
import nl.strohalm.cyclos.entities.members.imports.MemberImport;
import nl.strohalm.cyclos.entities.members.imports.MemberImportResult;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link MemberImportService}
 * 
 * @author jcomas
 */
public class MemberImportServiceSecurity extends BaseServiceSecurity implements MemberImportService {

    private MemberImportServiceLocal memberImportService;

    @Override
    public MemberImportResult getSummary(final MemberImport memberImport) {
        checkMemberImportPermissions(memberImport.getGroup());
        return memberImportService.getSummary(memberImport);
    }

    @Override
    public MemberImport importMembers(final MemberImport memberImport, final InputStream data) {
        checkMemberImportPermissions(memberImport.getGroup());
        return memberImportService.importMembers(memberImport, data);
    }

    @Override
    public MemberImport load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        MemberImport memberImport = memberImportService.load(id, fetch);
        checkMemberImportPermissions(memberImport.getGroup());
        return memberImport;
    }

    @Override
    public void processImport(final MemberImport memberImport, final boolean sendActivationMail) {
        checkMemberImportPermissions(memberImport.getGroup());
        memberImportService.processImport(memberImport, sendActivationMail);
    }

    @Override
    public List<ImportedMember> searchImportedMembers(final ImportedMemberQuery params) {
        MemberImport memberImport = memberImportService.load(params.getMemberImport().getId());
        if (!hasMemberImportPermissions(memberImport.getGroup())) {
            return Collections.emptyList();
        }
        return memberImportService.searchImportedMembers(params);
    }

    public void setMemberImportServiceLocal(final MemberImportServiceLocal memberImportService) {
        this.memberImportService = memberImportService;
    }

    @Override
    public void validate(final MemberImport memberImport) throws ValidationException {
        memberImportService.validate(memberImport);
    }

    /**
     * Checks if the logged user has the members import permission and manages the members group.
     * @param group
     */
    private void checkMemberImportPermissions(final MemberGroup group) {
        if (!hasMemberImportPermissions(group)) {
            throw new PermissionDeniedException();
        }
    }

    private boolean hasMemberImportPermissions(final MemberGroup group) {
        if (group == null) {
            return false;
        }
        return permissionService.permission().admin(AdminMemberPermission.MEMBERS_IMPORT).hasPermission() && permissionService.manages(group);
    }

}
