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
package nl.strohalm.cyclos.controls.groups.permissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.services.elements.ReferenceService;
import nl.strohalm.cyclos.services.groups.OperatorGroupPermissionsDTO;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.access.PermissionCatalogHandler;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.PermissionConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit an operator group's permissions
 * @author jefferson
 */
public class EditOperatorGroupPermissionsAction extends BaseFormAction {

    enum OperatorProperty implements PermissionCollectionProperty {
        canViewInformationOf(MemberAccountType.class),
        conversionSimulationTTs(TransferType.class),
        guaranteeTypes(GuaranteeType.class);

        private Class<?> elementClass;

        private OperatorProperty(final Class<?> elementClass) {
            this.elementClass = elementClass;
        }

        @Override
        public String cssClassName() {
            return null;
        }

        @Override
        public String onChangeListener() {
            return null;
        }
    }

    private ReferenceService                        referenceService;

    private DataBinder<OperatorGroupPermissionsDTO> dataBinder;

    public DataBinder<OperatorGroupPermissionsDTO> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<OperatorGroupPermissionsDTO> binder = BeanBinder.instance(OperatorGroupPermissionsDTO.class);
            binder.registerBinder("group", PropertyBinder.instance(Group.class, "group", ReferenceConverter.instance(Group.class)));
            binder.registerBinder("operations", SimpleCollectionBinder.instance(Permission.class, "operations", PermissionConverter.instance()));

            for (final OperatorProperty property : OperatorProperty.values()) {
                binder.registerBinder(property.name(), SimpleCollectionBinder.instance(property.elementClass, property.name()));
            }

            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setReferenceService(final ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditGroupPermissionsForm form = context.getForm();
        final long id = form.getGroupId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        final OperatorGroupPermissionsDTO dto = getDataBinder().readFromString(form.getPermission());
        groupService.setPermissions(dto);

        context.sendMessage("permission.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "groupId", id);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final HttpSession session = request.getSession();
        final EditGroupPermissionsForm form = context.getForm();
        final long groupId = form.getGroupId();
        if (groupId <= 0) {
            throw new ValidationException();
        }
        final Group group = groupService.load(groupId, Group.Relationships.PERMISSIONS, Group.Relationships.TRANSFER_TYPES);

        final PermissionCatalogHandler permissionCatalogHandler = permissionService.getPermissionCatalogHandler(group);

        final MemberGroup loggedMemberGroup = (MemberGroup) context.getGroup();
        // Just filter permissions that the member doesn't has
        final Map<Module, List<Permission>> notAllowedPermissionsMap = new HashMap<Module, List<Permission>>();

        final Collection<Nature> referenceNatures = referenceService.getNaturesByGroup(loggedMemberGroup);
        final boolean supportReferences = referenceNatures.contains(Nature.GENERAL);
        final boolean supportTransactionFeedbacks = referenceNatures.contains(Nature.TRANSACTION);

        for (final OperatorPermission opPerm : OperatorPermission.values()) {
            boolean allowed = true;
            // Special operations
            if (opPerm.getModule() == Module.OPERATOR_ACCOUNT && !Boolean.TRUE.equals(session.getAttribute("loggedMemberHasAccounts")) ||
                    opPerm == OperatorPermission.REFERENCES_MANAGE_MEMBER_REFERENCES && !supportReferences ||
                    opPerm == OperatorPermission.REFERENCES_MANAGE_MEMBER_TRANSACTION_FEEDBACKS && !supportTransactionFeedbacks) {
                allowed = false;
            }

            if (opPerm.getParentPermissions().length > 0 && !permissionService.hasPermission(opPerm.getParentPermissions())) {
                allowed = false;
            }
            if (!allowed) {
                addNotAllowedPermission(notAllowedPermissionsMap, opPerm);
            }
        }

        request.setAttribute("group", group);
        request.setAttribute("modulesByType", EditGroupPermissionsAction.resolveModules(context, group));
        request.setAttribute("notAllowedPermissionsMap", notAllowedPermissionsMap);
        request.setAttribute("multiValuesPermissions", createMultiValuesPermissionsMap(permissionCatalogHandler, group));
    }

    private void addNotAllowedPermission(final Map<Module, List<Permission>> notAllowedPermissionsMap, final OperatorPermission opPerm) {
        List<Permission> notAllowedPermissions = notAllowedPermissionsMap.get(opPerm.getModule());
        if (notAllowedPermissions == null) {
            notAllowedPermissions = new ArrayList<Permission>();
            notAllowedPermissionsMap.put(opPerm.getModule(), notAllowedPermissions);
        }

        notAllowedPermissions.add(opPerm);
    }

    private void addToMap(final Map<Permission, MultiValuesPermissionVO> map, final Permission permission, final PermissionCollectionProperty property, final PermissionCatalogHandler permissionCatalogHandler) {
        if (map.containsKey(permission)) {
            throw new IllegalArgumentException("Permission already added to the multivalues permissions map: " + permission);
        }
        map.put(permission, new MultiValuesPermissionVO(property, permissionCatalogHandler.currentValues(permission), permissionCatalogHandler.possibleValues(permission)));
    }

    private Map<Permission, MultiValuesPermissionVO> createMultiValuesPermissionsMap(final PermissionCatalogHandler permissionCatalogHandler, final Group group) {
        final Map<Permission, MultiValuesPermissionVO> map = new HashMap<Permission, MultiValuesPermissionVO>();

        addToMap(map, OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION, OperatorProperty.canViewInformationOf, permissionCatalogHandler);
        addToMap(map, OperatorPermission.GUARANTEES_ISSUE_GUARANTEES, OperatorProperty.guaranteeTypes, permissionCatalogHandler);

        return map;
    }
}
