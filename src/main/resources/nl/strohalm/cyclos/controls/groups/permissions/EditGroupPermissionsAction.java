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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.ModuleType;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.controls.groups.ListGroupsAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.services.groups.AdminGroupPermissionsDTO;
import nl.strohalm.cyclos.services.groups.BrokerGroupPermissionsDTO;
import nl.strohalm.cyclos.services.groups.GroupPermissionsDTO;
import nl.strohalm.cyclos.services.groups.MemberGroupPermissionsDTO;
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
 * Action used to edit a group's permissions
 * @author luis
 */
public class EditGroupPermissionsAction extends BaseFormAction {

    enum AdminAdminProperty implements PermissionCollectionProperty {
        createAdminRecordTypes(MemberRecordType.class),
        deleteAdminRecordTypes(MemberRecordType.class),
        modifyAdminRecordTypes(MemberRecordType.class),
        viewAdminRecordTypes(MemberRecordType.class);

        /**
         * The class of the collection element
         */
        private Class<?> elementClass;

        private AdminAdminProperty(final Class<?> elementClass) {
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

    enum AdminMemberProperty implements PermissionCollectionProperty {
        grantLoanTTs(TransferType.class),
        memberChargebackTTs(TransferType.class),
        systemToMemberTTs(TransferType.class),
        asMemberToMemberTTs(TransferType.class),
        asMemberToSelfTTs(TransferType.class),
        asMemberToSystemTTs(TransferType.class),
        createMemberRecordTypes(MemberRecordType.class),
        deleteMemberRecordTypes(MemberRecordType.class),
        modifyMemberRecordTypes(MemberRecordType.class),
        viewMemberRecordTypes(MemberRecordType.class),
        conversionSimulationTTs(TransferType.class),
        managesGroups(MemberGroup.class, "managedGroupsChanged()");

        private String   onChangeListener;
        private Class<?> elementClass;

        private AdminMemberProperty(final Class<?> elementClass) {
            this(elementClass, null);
        }

        private AdminMemberProperty(final Class<?> elementClass, final String onChangeListener) {
            this.onChangeListener = onChangeListener;
            this.elementClass = elementClass;
        }

        @Override
        public String cssClassName() {
            return null;
        }

        @Override
        public String onChangeListener() {
            return onChangeListener;
        }
    }

    enum AdminSystemProperty implements PermissionCollectionProperty {
        viewInformationOf(SystemAccountType.class, "systemAccountTypesChanged()"),
        systemChargebackTTs(TransferType.class),
        systemToSystemTTs(TransferType.class),
        viewConnectedAdminsOf(AdminGroup.class);

        private String   onChangeListener;

        /**
         * The class of the collection element
         */
        private Class<?> elementClass;

        private AdminSystemProperty(final Class<?> elementClass) {
            this(elementClass, null);
        }

        private AdminSystemProperty(final Class<?> elementClass, final String onChangeListener) {
            this.onChangeListener = onChangeListener;
            this.elementClass = elementClass;
        }

        @Override
        public String cssClassName() {
            return null;
        }

        @Override
        public String onChangeListener() {
            return onChangeListener;
        }
    }

    enum BrokerProperty implements PermissionCollectionProperty {

        brokerConversionSimulationTTs(TransferType.class),
        brokerDocuments(Document.class),
        asMemberToMemberTTs(TransferType.class),
        asMemberToSelfTTs(TransferType.class),
        asMemberToSystemTTs(TransferType.class),
        brokerCreateMemberRecordTypes(MemberRecordType.class),
        brokerDeleteMemberRecordTypes(MemberRecordType.class),
        brokerModifyMemberRecordTypes(MemberRecordType.class),
        brokerMemberRecordTypes(MemberRecordType.class),
        brokerCanViewInformationOf(AccountType.class);

        private Class<?> elementClass;

        private BrokerProperty(final Class<?> elementClass) {
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

    enum CommonProperty implements PermissionCollectionProperty {
        documents(Document.class),
        guaranteeTypes(GuaranteeType.class),
        messageCategories(MessageCategory.class);

        /**
         * The class of the collection element
         */
        private Class<?> elementClass;

        private CommonProperty(final Class<?> elementClass) {
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

    enum MemberProperty implements PermissionCollectionProperty {
        canViewAdsOfGroups(Group.class),
        canBuyWithPaymentObligationsFromGroups(Group.class, null, "showHideIssuersPermissions()"),
        canIssueCertificationToGroups(Group.class, null, "showHideBuyersAndSellersPermissions()"),
        chargebackTTs(TransferType.class),
        memberToMemberTTs(TransferType.class),
        selfPaymentTTs(TransferType.class),
        memberToSystemTTs(TransferType.class),
        requestPaymentByChannels(Channel.class),
        canViewProfileOfGroups(Group.class, null, "canViewProfileOfGroupsChanged()"),
        canViewInformationOf(AccountType.class),
        conversionSimulationTTs(TransferType.class);

        private String   cssClassName;
        private String   onChangeListener;

        /**
         * The class of the collection element
         */
        private Class<?> elementClass;

        private MemberProperty(final Class<?> elementClass) {
            this(elementClass, null, null);
        }

        private MemberProperty(final Class<?> elementClass, final String cssClassName, final String onChangeListener) {
            this.cssClassName = cssClassName;
            this.onChangeListener = onChangeListener;
            this.elementClass = elementClass;
        }

        @Override
        public String cssClassName() {
            return cssClassName;
        }

        @Override
        public String onChangeListener() {
            return onChangeListener;
        }
    }

    public static Map<ModuleType, List<Module>> resolveModules(final ActionContext context, final Group group) {
        final List<ModuleType> moduleTypes = ModuleType.getModuleTypes(group.getNature());

        // Get the modules, sorted according to the translation
        final Map<ModuleType, List<Module>> modulesByType = new LinkedHashMap<ModuleType, List<Module>>();
        for (final ModuleType moduleType : moduleTypes) {
            final List<Module> modules = moduleType.getModules();
            Collections.sort(modules, new Comparator<Module>() {
                @Override
                public int compare(final Module o1, final Module o2) {
                    final String label1 = context.message("permission." + o1.getValue());
                    final String label2 = context.message("permission." + o2.getValue());
                    return label1.compareTo(label2);
                }
            });
            modulesByType.put(moduleType, modules);
        }
        return modulesByType;
    }

    private DataBinder<AdminGroupPermissionsDTO>               adminDataBinder;
    private DataBinder<MemberGroupPermissionsDTO<MemberGroup>> memberDataBinder;
    private DataBinder<BrokerGroupPermissionsDTO>              brokerDataBinder;

    public DataBinder<AdminGroupPermissionsDTO> getAdminDataBinder() {
        if (adminDataBinder == null) {
            final BeanBinder<AdminGroupPermissionsDTO> binder = BeanBinder.instance(AdminGroupPermissionsDTO.class);
            initBasic(binder);
            for (final AdminSystemProperty property : AdminSystemProperty.values()) {
                binder.registerBinder(property.name(), SimpleCollectionBinder.instance(property.elementClass, property.name()));
            }

            for (final AdminMemberProperty property : AdminMemberProperty.values()) {
                binder.registerBinder(property.name(), SimpleCollectionBinder.instance(property.elementClass, property.name()));
            }

            for (final AdminAdminProperty property : AdminAdminProperty.values()) {
                binder.registerBinder(property.name(), SimpleCollectionBinder.instance(property.elementClass, property.name()));
            }

            adminDataBinder = binder;
        }
        return adminDataBinder;
    }

    public DataBinder<BrokerGroupPermissionsDTO> getBrokerDataBinder() {
        if (brokerDataBinder == null) {
            final BeanBinder<BrokerGroupPermissionsDTO> binder = BeanBinder.instance(BrokerGroupPermissionsDTO.class);
            initBasic(binder);
            initMember(binder);

            for (final BrokerProperty property : BrokerProperty.values()) {
                binder.registerBinder(property.name(), SimpleCollectionBinder.instance(property.elementClass, property.name()));
            }

            brokerDataBinder = binder;
        }
        return brokerDataBinder;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public DataBinder<MemberGroupPermissionsDTO<MemberGroup>> getMemberDataBinder() {
        if (memberDataBinder == null) {
            final BeanBinder<MemberGroupPermissionsDTO<MemberGroup>> binder = (BeanBinder) BeanBinder.instance(MemberGroupPermissionsDTO.class);
            initBasic(binder);
            initMember(binder);
            memberDataBinder = binder;
        }
        return memberDataBinder;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditGroupPermissionsForm form = context.getForm();
        final long id = form.getGroupId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        final Group group = groupService.load(id);
        GroupPermissionsDTO<?> permissions;
        if (group instanceof AdminGroup) {
            permissions = getAdminDataBinder().readFromString(form.getPermission());
        } else if (group instanceof BrokerGroup) {
            permissions = getBrokerDataBinder().readFromString(form.getPermission());
        } else if (group instanceof MemberGroup) {
            permissions = getMemberDataBinder().readFromString(form.getPermission());
        } else {
            throw new ValidationException();
        }
        groupService.setPermissions(permissions);
        context.sendMessage("permission.modified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "groupId", id);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditGroupPermissionsForm form = context.getForm();
        boolean editable = false;
        final long groupId = form.getGroupId();
        if (groupId <= 0) {
            throw new ValidationException();
        }

        final Group group = groupService.load(groupId);
        final PermissionCatalogHandler permissionCatalogHandler = permissionService.getPermissionCatalogHandler(group);

        request.setAttribute("group", group);
        request.setAttribute("modulesByType", resolveModules(context, group));

        // Put in the request the name of permission used to manage a type of group
        final Map<Group.Nature, Permission> permissionByNature = ListGroupsAction.getManageGroupPermissionByNatureMap();

        AdminGroup adminGroup = context.getGroup();
        adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.MANAGES_GROUPS);
        request.setAttribute("managesGroups", adminGroup.getManagesGroups());
        if (permissionService.hasPermission(permissionByNature.get(group.getNature())) && (Group.Nature.ADMIN.equals(group.getNature()) || adminGroup.getManagesGroups().contains(group))) {
            editable = true;
        }
        request.setAttribute("editable", editable);
        request.setAttribute("multiValuesPermissions", createMultiValuesPermissionsMap(permissionCatalogHandler, group));
    }

    private void addToMap(final Map<Permission, MultiValuesPermissionVO> map, final Permission permission, final PermissionCollectionProperty property, final PermissionCatalogHandler permissionCatalogHandler) {
        if (map.containsKey(permission)) {
            throw new IllegalArgumentException("Permission already added to the multivalues permissions map: " + permission);
        }
        map.put(permission, new MultiValuesPermissionVO(property, permissionCatalogHandler.currentValues(permission), permissionCatalogHandler.possibleValues(permission)));
    }

    /**
     * Creates a map containing all permissions which UI control is a multi drop down or a selection list
     * @param group
     * @return
     */
    private Map<Permission, MultiValuesPermissionVO> createMultiValuesPermissionsMap(final PermissionCatalogHandler permissionCatalogHandler, final Group group) {
        final Map<Permission, MultiValuesPermissionVO> map = new HashMap<Permission, MultiValuesPermissionVO>();

        switch (group.getNature()) {
            case ADMIN:
                addToMap(map, AdminSystemPermission.ACCOUNTS_INFORMATION, AdminSystemProperty.viewInformationOf, permissionCatalogHandler);
                addToMap(map, AdminSystemPermission.PAYMENTS_CHARGEBACK, AdminSystemProperty.systemChargebackTTs, permissionCatalogHandler);
                addToMap(map, AdminSystemPermission.PAYMENTS_PAYMENT, AdminSystemProperty.systemToSystemTTs, permissionCatalogHandler);
                addToMap(map, AdminSystemPermission.STATUS_VIEW_CONNECTED_ADMINS, AdminSystemProperty.viewConnectedAdminsOf, permissionCatalogHandler);

                addToMap(map, AdminAdminPermission.RECORDS_CREATE, AdminAdminProperty.createAdminRecordTypes, permissionCatalogHandler);
                addToMap(map, AdminAdminPermission.RECORDS_DELETE, AdminAdminProperty.deleteAdminRecordTypes, permissionCatalogHandler);
                addToMap(map, AdminAdminPermission.RECORDS_MODIFY, AdminAdminProperty.modifyAdminRecordTypes, permissionCatalogHandler);
                addToMap(map, AdminAdminPermission.RECORDS_VIEW, AdminAdminProperty.viewAdminRecordTypes, permissionCatalogHandler);

                addToMap(map, AdminMemberPermission.MEMBERS_VIEW, AdminMemberProperty.managesGroups, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.DOCUMENTS_DETAILS, CommonProperty.documents, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.GUARANTEES_REGISTER_GUARANTEES, CommonProperty.guaranteeTypes, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.LOANS_GRANT, AdminMemberProperty.grantLoanTTs, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.MESSAGES_VIEW, CommonProperty.messageCategories, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.PAYMENTS_CHARGEBACK, AdminMemberProperty.memberChargebackTTs, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.PAYMENTS_PAYMENT, AdminMemberProperty.systemToMemberTTs, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER, AdminMemberProperty.asMemberToMemberTTs, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF, AdminMemberProperty.asMemberToSelfTTs, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM, AdminMemberProperty.asMemberToSystemTTs, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.RECORDS_CREATE, AdminMemberProperty.createMemberRecordTypes, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.RECORDS_DELETE, AdminMemberProperty.deleteMemberRecordTypes, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.RECORDS_MODIFY, AdminMemberProperty.modifyMemberRecordTypes, permissionCatalogHandler);
                addToMap(map, AdminMemberPermission.RECORDS_VIEW, AdminMemberProperty.viewMemberRecordTypes, permissionCatalogHandler);
                break;
            case BROKER:
                addToMap(map, BrokerPermission.DOCUMENTS_VIEW, BrokerProperty.brokerDocuments, permissionCatalogHandler);
                addToMap(map, BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER, BrokerProperty.asMemberToMemberTTs, permissionCatalogHandler);
                addToMap(map, BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF, BrokerProperty.asMemberToSelfTTs, permissionCatalogHandler);
                addToMap(map, BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM, BrokerProperty.asMemberToSystemTTs, permissionCatalogHandler);
                addToMap(map, BrokerPermission.MEMBER_RECORDS_CREATE, BrokerProperty.brokerCreateMemberRecordTypes, permissionCatalogHandler);
                addToMap(map, BrokerPermission.MEMBER_RECORDS_DELETE, BrokerProperty.brokerDeleteMemberRecordTypes, permissionCatalogHandler);
                addToMap(map, BrokerPermission.MEMBER_RECORDS_MODIFY, BrokerProperty.brokerModifyMemberRecordTypes, permissionCatalogHandler);
                addToMap(map, BrokerPermission.MEMBER_RECORDS_VIEW, BrokerProperty.brokerMemberRecordTypes, permissionCatalogHandler);
                addToMap(map, BrokerPermission.REPORTS_SHOW_ACCOUNT_INFORMATION, BrokerProperty.brokerCanViewInformationOf, permissionCatalogHandler);
            case MEMBER:
                addToMap(map, MemberPermission.ADS_VIEW, MemberProperty.canViewAdsOfGroups, permissionCatalogHandler);
                addToMap(map, MemberPermission.DOCUMENTS_VIEW, CommonProperty.documents, permissionCatalogHandler);
                addToMap(map, MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS, MemberProperty.canBuyWithPaymentObligationsFromGroups, permissionCatalogHandler);
                addToMap(map, MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS, MemberProperty.canIssueCertificationToGroups, permissionCatalogHandler);
                addToMap(map, MemberPermission.GUARANTEES_ISSUE_GUARANTEES, CommonProperty.guaranteeTypes, permissionCatalogHandler);
                addToMap(map, MemberPermission.MESSAGES_SEND_TO_ADMINISTRATION, CommonProperty.messageCategories, permissionCatalogHandler);
                addToMap(map, MemberPermission.PAYMENTS_CHARGEBACK, MemberProperty.chargebackTTs, permissionCatalogHandler);
                addToMap(map, MemberPermission.PAYMENTS_PAYMENT_TO_MEMBER, MemberProperty.memberToMemberTTs, permissionCatalogHandler);
                addToMap(map, MemberPermission.PAYMENTS_PAYMENT_TO_SELF, MemberProperty.selfPaymentTTs, permissionCatalogHandler);
                addToMap(map, MemberPermission.PAYMENTS_PAYMENT_TO_SYSTEM, MemberProperty.memberToSystemTTs, permissionCatalogHandler);
                addToMap(map, MemberPermission.PAYMENTS_REQUEST, MemberProperty.requestPaymentByChannels, permissionCatalogHandler);
                addToMap(map, MemberPermission.PROFILE_VIEW, MemberProperty.canViewProfileOfGroups, permissionCatalogHandler);
                addToMap(map, MemberPermission.REPORTS_SHOW_ACCOUNT_INFORMATION, MemberProperty.canViewInformationOf, permissionCatalogHandler);
                break;
            default:
                throw new IllegalArgumentException("Illegal group: " + group.getNature());
        }
        // operatorAccounts ACCOUNTS_SIMULATE_CONVERSION que hacemos con esto????

        return map;
    }

    private void initBasic(final BeanBinder<? extends GroupPermissionsDTO<?>> binder) {
        binder.registerBinder("group", PropertyBinder.instance(Group.class, "group", ReferenceConverter.instance(Group.class)));
        binder.registerBinder("operations", SimpleCollectionBinder.instance(Permission.class, "operations", PermissionConverter.instance()));

        for (final CommonProperty property : CommonProperty.values()) {
            binder.registerBinder(property.name(), SimpleCollectionBinder.instance(property.elementClass, property.name()));
        }
    }

    private void initMember(final BeanBinder<? extends MemberGroupPermissionsDTO<?>> binder) {
        for (final MemberProperty property : MemberProperty.values()) {
            binder.registerBinder(property.name(), SimpleCollectionBinder.instance(property.elementClass, property.name()));
        }
    }
}
