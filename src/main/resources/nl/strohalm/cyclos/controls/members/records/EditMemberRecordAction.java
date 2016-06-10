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
package nl.strohalm.cyclos.controls.members.records;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminAdminPermission;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType.Layout;
import nl.strohalm.cyclos.services.customization.MemberRecordCustomFieldService;
import nl.strohalm.cyclos.services.elements.MemberRecordService;
import nl.strohalm.cyclos.services.elements.MemberRecordTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldHelper.Entry;
import nl.strohalm.cyclos.utils.Navigation;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a member record
 * @author Jefferson Magno
 */
public class EditMemberRecordAction extends BaseFormAction {

    private MemberRecordCustomFieldService memberRecordCustomFieldService;
    private MemberRecordService            memberRecordService;
    private MemberRecordTypeService        memberRecordTypeService;
    private DataBinder<MemberRecord>       dataBinder;

    private CustomFieldHelper              customFieldHelper;

    public MemberRecordService getMemberRecordService() {
        return memberRecordService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setMemberRecordCustomFieldService(final MemberRecordCustomFieldService memberRecordCustomFieldService) {
        this.memberRecordCustomFieldService = memberRecordCustomFieldService;
    }

    @Inject
    public void setMemberRecordService(final MemberRecordService memberRecordService) {
        this.memberRecordService = memberRecordService;
    }

    @Inject
    public void setMemberRecordTypeService(final MemberRecordTypeService memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditMemberRecordForm form = context.getForm();
        MemberRecord memberRecord = getDataBinder().readFromString(form.getMemberRecord());
        final boolean isInsert = memberRecord.isTransient();

        if (isInsert) {
            memberRecord = memberRecordService.insert(memberRecord);
        } else {
            memberRecord = memberRecordService.update(memberRecord);
        }

        if (isInsert) {
            context.sendMessage("memberRecord.inserted");
        } else {
            context.sendMessage("memberRecord.modified");
        }

        final boolean isFlat = memberRecord.getType().getLayout() == Layout.FLAT;
        ActionForward forward;
        final Navigation navigation = context.getNavigation();
        final String last = navigation.getPrevious();
        if (last != null && last.contains("/search")) {
            forward = context.findForward("successList");
        } else {
            if (isFlat) {
                forward = context.findForward("successFlat");
            } else {
                forward = context.getSuccessForward();
            }
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberRecordId", memberRecord.getId());

        final SearchMemberRecordsForm searchForm = (SearchMemberRecordsForm) context.getSession().getAttribute("searchMemberRecordsForm");
        if (searchForm == null || !searchForm.isGlobal()) {
            params.put("elementId", memberRecord.getElement().getId());
        }

        params.put("typeId", memberRecord.getType().getId());
        forward = ActionHelper.redirectWithParams(context.getRequest(), forward, params);
        if (isFlat) {
            // Go directly to the anchor
            forward.setPath(forward.getPath() + "#memberRecord_" + memberRecord.getId());
        }
        return forward;
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditMemberRecordForm form = context.getForm();

        MemberRecordType type = null;
        Element element = null;

        final long memberRecordId = form.getMemberRecordId();
        MemberRecord memberRecord;
        final boolean isInsert = memberRecordId <= 0L;
        if (isInsert) {
            request.setAttribute("isInsert", true);
            memberRecord = new MemberRecord();
            final long baseMemberRecordId = form.getBaseMemberRecordId();
            if (baseMemberRecordId > 0) {
                // Copying data from an existing member record
                final MemberRecord baseMemberRecord = memberRecordService.load(baseMemberRecordId, MemberRecord.Relationships.TYPE, MemberRecord.Relationships.ELEMENT, MemberRecord.Relationships.CUSTOM_VALUES);
                type = baseMemberRecord.getType();
                element = baseMemberRecord.getElement();

                // Clone custom values
                customFieldHelper.cloneFieldValues(baseMemberRecord, memberRecord);

                request.setAttribute("baseMemberRecord", baseMemberRecord);
            }
            memberRecord.setType(type);
            memberRecord.setElement(element);
        } else {
            memberRecord = memberRecordService.load(memberRecordId, MemberRecord.Relationships.BY, MemberRecord.Relationships.MODIFIED_BY, MemberRecord.Relationships.CUSTOM_VALUES, MemberRecord.Relationships.ELEMENT);
            type = memberRecord.getType();
            element = memberRecord.getElement();
            request.setAttribute("memberRecord", memberRecord);
        }

        if (type == null) {
            final long typeId = form.getTypeId();
            if (typeId <= 0L) {
                throw new ValidationException();
            }
            type = memberRecordTypeService.load(typeId);
        }

        if (element == null) {
            final long elementId = form.getElementId();
            if (elementId <= 0L) {
                throw new ValidationException();
            }
            element = elementService.load(elementId);
        }

        final boolean byBroker = (element instanceof Member) && context.isBrokerOf((Member) element);

        request.setAttribute("element", element);
        request.setAttribute("byBroker", byBroker);
        request.setAttribute("type", type);
        request.setAttribute("global", form.isGlobal());

        // Get custom field entries
        final List<MemberRecordCustomField> customFields = memberRecordCustomFieldService.list(type);
        if (byBroker) {
            final Collection<MemberRecordCustomField> readOnlyFields = new HashSet<MemberRecordCustomField>();
            for (final Iterator<MemberRecordCustomField> iterator = customFields.iterator(); iterator.hasNext();) {
                final MemberRecordCustomField field = iterator.next();
                switch (field.getBrokerAccess()) {
                    case NONE:
                        iterator.remove();
                        break;
                    case READ_ONLY:
                        if (isInsert) {
                            iterator.remove();
                        } else {
                            readOnlyFields.add(field);
                        }
                        break;
                }
            }
            request.setAttribute("readOnlyFields", readOnlyFields);
        }
        request.setAttribute("customFields", customFields);
        final Collection<Entry> entries = customFieldHelper.buildEntries(customFields, memberRecord.getCustomValues());
        request.setAttribute("customFieldEntries", entries);

        // Check permissions for logged user
        final Group group = context.getGroup();
        boolean canCreate = false;
        boolean canModify = false;
        boolean canDelete = false; // This attribute is used on FlatMemberRecordsAction that inherits from this class
        if (context.isAdmin()) {
            AdminGroup adminGroup = (AdminGroup) group;
            adminGroup = groupService.load(adminGroup.getId(), AdminGroup.Relationships.CREATE_MEMBER_RECORD_TYPES, AdminGroup.Relationships.MODIFY_MEMBER_RECORD_TYPES, AdminGroup.Relationships.DELETE_MEMBER_RECORD_TYPES, AdminGroup.Relationships.CREATE_ADMIN_RECORD_TYPES, AdminGroup.Relationships.MODIFY_ADMIN_RECORD_TYPES, AdminGroup.Relationships.DELETE_ADMIN_RECORD_TYPES);
            if (element instanceof Member) {
                canCreate = permissionService.hasPermission(AdminMemberPermission.RECORDS_CREATE) && adminGroup.getCreateMemberRecordTypes().contains(type);
                canModify = permissionService.hasPermission(AdminMemberPermission.RECORDS_MODIFY) && adminGroup.getModifyMemberRecordTypes().contains(type);
                canDelete = permissionService.hasPermission(AdminMemberPermission.RECORDS_DELETE) && adminGroup.getDeleteMemberRecordTypes().contains(type);
            } else if (element instanceof Administrator) {
                canCreate = permissionService.hasPermission(AdminAdminPermission.RECORDS_CREATE) && adminGroup.getCreateAdminRecordTypes().contains(type);
                canModify = permissionService.hasPermission(AdminAdminPermission.RECORDS_MODIFY) && adminGroup.getModifyAdminRecordTypes().contains(type);
                canDelete = permissionService.hasPermission(AdminAdminPermission.RECORDS_DELETE) && adminGroup.getDeleteAdminRecordTypes().contains(type);
            }
        } else if ((element instanceof Member) && context.isBrokerOf((Member) element)) {
            BrokerGroup brokerGroup = (BrokerGroup) group;
            brokerGroup = groupService.load(brokerGroup.getId(), BrokerGroup.Relationships.BROKER_CREATE_MEMBER_RECORD_TYPES, BrokerGroup.Relationships.BROKER_MODIFY_MEMBER_RECORD_TYPES);
            canCreate = permissionService.hasPermission(BrokerPermission.MEMBER_RECORDS_CREATE) && brokerGroup.getBrokerCreateMemberRecordTypes().contains(type);
            canModify = permissionService.hasPermission(BrokerPermission.MEMBER_RECORDS_MODIFY) && brokerGroup.getBrokerModifyMemberRecordTypes().contains(type);
            canDelete = permissionService.hasPermission(BrokerPermission.MEMBER_RECORDS_DELETE) && brokerGroup.getBrokerDeleteMemberRecordTypes().contains(type);
        }
        request.setAttribute("canCreate", canCreate);
        request.setAttribute("canModify", canModify);
        request.setAttribute("canDelete", canDelete);
    }

    private DataBinder<MemberRecord> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<? extends CustomFieldValue> customValueBinder = BeanBinder.instance(MemberRecordCustomFieldValue.class);
            customValueBinder.registerBinder("field", PropertyBinder.instance(MemberRecordCustomField.class, "field"));
            customValueBinder.registerBinder("value", PropertyBinder.instance(String.class, "value", HtmlConverter.instance()));

            final BeanBinder<MemberRecord> binder = BeanBinder.instance(MemberRecord.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("type", PropertyBinder.instance(MemberRecordType.class, "type"));
            binder.registerBinder("element", PropertyBinder.instance(Element.class, "element"));
            binder.registerBinder("customValues", BeanCollectionBinder.instance(customValueBinder, "customValues"));

            dataBinder = binder;
        }
        return dataBinder;
    }

}
