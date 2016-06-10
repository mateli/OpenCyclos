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
package nl.strohalm.cyclos.controls.customization.fields;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.LoanGroupCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.Validation;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.services.customization.AdCustomFieldService;
import nl.strohalm.cyclos.services.customization.AdminCustomFieldService;
import nl.strohalm.cyclos.services.customization.BaseCustomFieldService;
import nl.strohalm.cyclos.services.customization.LoanGroupCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.customization.MemberRecordCustomFieldService;
import nl.strohalm.cyclos.services.customization.OperatorCustomFieldService;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldService;
import nl.strohalm.cyclos.services.elements.MemberRecordTypeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a custom field
 * @author luis
 */
public class EditCustomFieldAction extends BaseFormAction {

    private static DataBinder<? extends CustomField> getBasicDataBinder(final CustomField.Nature nature) {

        final BeanBinder<Validation> validationBinder = BeanBinder.instance(Validation.class, "validation");
        validationBinder.registerBinder("required", PropertyBinder.instance(Boolean.TYPE, "required"));
        validationBinder.registerBinder("unique", PropertyBinder.instance(Boolean.TYPE, "unique"));
        validationBinder.registerBinder("lengthConstraint", DataBinderHelper.rangeConstraintBinder("lengthConstraint"));
        validationBinder.registerBinder("validatorClass", PropertyBinder.instance(String.class, "validatorClass"));

        final BeanBinder<? extends CustomField> binder = BeanBinder.instance(nature.getEntityType());
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("internalName", PropertyBinder.instance(String.class, "internalName"));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        binder.registerBinder("pattern", PropertyBinder.instance(String.class, "pattern"));
        binder.registerBinder("parent", PropertyBinder.instance(CustomField.class, "parent"));
        binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
        binder.registerBinder("type", PropertyBinder.instance(CustomField.Type.class, "type"));
        binder.registerBinder("control", PropertyBinder.instance(CustomField.Control.class, "control"));
        binder.registerBinder("size", PropertyBinder.instance(CustomField.Size.class, "size"));
        binder.registerBinder("allSelectedLabel", PropertyBinder.instance(String.class, "allSelectedLabel"));
        binder.registerBinder("validation", validationBinder);
        return binder;
    }

    private AdCustomFieldService                                       adCustomFieldService;
    private AdminCustomFieldService                                    adminCustomFieldService;
    private LoanGroupCustomFieldService                                loanGroupCustomFieldService;
    private MemberCustomFieldService                                   memberCustomFieldService;
    private MemberRecordCustomFieldService                             memberRecordCustomFieldService;
    private OperatorCustomFieldService                                 operatorCustomFieldService;
    private PaymentCustomFieldService                                  paymentCustomFieldService;
    private MemberRecordTypeService                                    memberRecordTypeService;
    private TransferTypeService                                        transferTypeService;
    private Map<CustomField.Nature, DataBinder<? extends CustomField>> dataBinders;

    @SuppressWarnings("unchecked")
    public DataBinder<AdCustomField> getAdCustomFieldBinder() {
        final BeanBinder<AdCustomField> adFieldBinder = (BeanBinder<AdCustomField>) getBasicDataBinder(CustomField.Nature.AD);
        adFieldBinder.registerBinder("showInSearch", PropertyBinder.instance(Boolean.TYPE, "showInSearch"));
        adFieldBinder.registerBinder("indexed", PropertyBinder.instance(Boolean.TYPE, "indexed"));
        adFieldBinder.registerBinder("visibility", PropertyBinder.instance(AdCustomField.Visibility.class, "visibility"));
        return adFieldBinder;
    }

    @SuppressWarnings("unchecked")
    public DataBinder<AdminCustomField> getAdminCustomFieldBinder() {
        final BeanBinder<AdminCustomField> adminFieldBinder = (BeanBinder<AdminCustomField>) getBasicDataBinder(CustomField.Nature.ADMIN);
        adminFieldBinder.registerBinder("groups", SimpleCollectionBinder.instance(AdminGroup.class, "groups"));
        return adminFieldBinder;
    }

    public DataBinder<? extends CustomField> getDataBinder(final CustomField.Nature nature) {
        if (dataBinders == null) {
            dataBinders = new EnumMap<CustomField.Nature, DataBinder<? extends CustomField>>(CustomField.Nature.class);
            dataBinders.put(CustomField.Nature.MEMBER, getMemberCustomFieldBinder());
            dataBinders.put(CustomField.Nature.ADMIN, getAdminCustomFieldBinder());
            dataBinders.put(CustomField.Nature.OPERATOR, getOperatorCustomFieldBinder());
            dataBinders.put(CustomField.Nature.AD, getAdCustomFieldBinder());
            dataBinders.put(CustomField.Nature.PAYMENT, getPaymentCustomFieldBinder());
            dataBinders.put(CustomField.Nature.LOAN_GROUP, getLoanGroupCustomFieldBinder());
            dataBinders.put(CustomField.Nature.MEMBER_RECORD, getMemberRecordCustomFieldBinder());
        }
        return dataBinders.get(nature);
    }

    @SuppressWarnings("unchecked")
    public DataBinder<LoanGroupCustomField> getLoanGroupCustomFieldBinder() {
        final BeanBinder<LoanGroupCustomField> loanGroupFieldBinder = (BeanBinder<LoanGroupCustomField>) getBasicDataBinder(CustomField.Nature.LOAN_GROUP);
        loanGroupFieldBinder.registerBinder("showInSearch", PropertyBinder.instance(Boolean.TYPE, "showInSearch"));
        return loanGroupFieldBinder;
    }

    @SuppressWarnings("unchecked")
    public DataBinder<MemberCustomField> getMemberCustomFieldBinder() {
        final BeanBinder<MemberCustomField> memberFieldBinder = (BeanBinder<MemberCustomField>) getBasicDataBinder(CustomField.Nature.MEMBER);
        memberFieldBinder.registerBinder("visibilityAccess", PropertyBinder.instance(MemberCustomField.Access.class, "visibilityAccess"));
        memberFieldBinder.registerBinder("updateAccess", PropertyBinder.instance(MemberCustomField.Access.class, "updateAccess"));
        memberFieldBinder.registerBinder("memberSearchAccess", PropertyBinder.instance(MemberCustomField.Access.class, "memberSearchAccess"));
        memberFieldBinder.registerBinder("adSearchAccess", PropertyBinder.instance(MemberCustomField.Access.class, "adSearchAccess"));
        memberFieldBinder.registerBinder("indexing", PropertyBinder.instance(MemberCustomField.Indexing.class, "indexing"));
        memberFieldBinder.registerBinder("loanSearchAccess", PropertyBinder.instance(MemberCustomField.Access.class, "loanSearchAccess"));
        memberFieldBinder.registerBinder("memberCanHide", PropertyBinder.instance(Boolean.TYPE, "memberCanHide"));
        memberFieldBinder.registerBinder("showInPrint", PropertyBinder.instance(Boolean.TYPE, "showInPrint"));
        memberFieldBinder.registerBinder("groups", SimpleCollectionBinder.instance(MemberGroup.class, "groups"));
        return memberFieldBinder;
    }

    @SuppressWarnings("unchecked")
    public DataBinder<MemberRecordCustomField> getMemberRecordCustomFieldBinder() {
        final BeanBinder<MemberRecordCustomField> memberRecordFieldBinder = (BeanBinder<MemberRecordCustomField>) getBasicDataBinder(CustomField.Nature.MEMBER_RECORD);
        memberRecordFieldBinder.registerBinder("memberRecordType", PropertyBinder.instance(MemberRecordType.class, "memberRecordType"));
        memberRecordFieldBinder.registerBinder("showInSearch", PropertyBinder.instance(Boolean.TYPE, "showInSearch"));
        memberRecordFieldBinder.registerBinder("showInList", PropertyBinder.instance(Boolean.TYPE, "showInList"));
        memberRecordFieldBinder.registerBinder("brokerAccess", PropertyBinder.instance(MemberRecordCustomField.Access.class, "brokerAccess"));
        return memberRecordFieldBinder;
    }

    @SuppressWarnings("unchecked")
    public DataBinder<OperatorCustomField> getOperatorCustomFieldBinder() {
        final BeanBinder<OperatorCustomField> operatorFieldBinder = (BeanBinder<OperatorCustomField>) getBasicDataBinder(CustomField.Nature.OPERATOR);
        operatorFieldBinder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
        operatorFieldBinder.registerBinder("visibility", PropertyBinder.instance(OperatorCustomField.Visibility.class, "visibility"));
        return operatorFieldBinder;
    }

    @SuppressWarnings("unchecked")
    public DataBinder<PaymentCustomField> getPaymentCustomFieldBinder() {
        final BeanBinder<PaymentCustomField> paymentFieldBinder = (BeanBinder<PaymentCustomField>) getBasicDataBinder(CustomField.Nature.PAYMENT);
        paymentFieldBinder.registerBinder("enabled", PropertyBinder.instance(Boolean.TYPE, "enabled"));
        paymentFieldBinder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType"));
        paymentFieldBinder.registerBinder("searchAccess", PropertyBinder.instance(PaymentCustomField.Access.class, "searchAccess"));
        paymentFieldBinder.registerBinder("listAccess", PropertyBinder.instance(PaymentCustomField.Access.class, "listAccess"));
        return paymentFieldBinder;
    }

    @Inject
    public void setAdCustomFieldService(final AdCustomFieldService adCustomFieldService) {
        this.adCustomFieldService = adCustomFieldService;
    }

    @Inject
    public void setAdminCustomFieldService(final AdminCustomFieldService adminCustomFieldService) {
        this.adminCustomFieldService = adminCustomFieldService;
    }

    @Inject
    public void setLoanGroupCustomFieldService(final LoanGroupCustomFieldService loanGroupCustomFieldService) {
        this.loanGroupCustomFieldService = loanGroupCustomFieldService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Inject
    public void setMemberRecordCustomFieldService(final MemberRecordCustomFieldService memberRecordCustomFieldService) {
        this.memberRecordCustomFieldService = memberRecordCustomFieldService;
    }

    @Inject
    public void setMemberRecordTypeService(final MemberRecordTypeService memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    @Inject
    public void setOperatorCustomFieldService(final OperatorCustomFieldService operatorCustomFieldService) {
        this.operatorCustomFieldService = operatorCustomFieldService;
    }

    @Inject
    public void setPaymentCustomFieldService(final PaymentCustomFieldService paymentCustomFieldService) {
        this.paymentCustomFieldService = paymentCustomFieldService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditCustomFieldForm form = context.getForm();
        final CustomField.Nature nature = getNature(form);
        CustomField field = getDataBinder(nature).readFromString(form.getField());
        final boolean isInsert = field.getId() == null;
        if (isInsert && field instanceof OperatorCustomField) {
            ((OperatorCustomField) field).setMember((Member) context.getElement());
        }
        field = resolveService(nature).save(field);

        // Forward with correct parameters
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("fieldId", field.getId());
        params.put("nature", field.getNature().name());
        switch (nature) {
            case MEMBER_RECORD:
                params.put("memberRecordTypeId", form.getField("memberRecordType"));
                break;
            case PAYMENT:
                final PaymentCustomField paymentField = (PaymentCustomField) field;
                params.put("accountTypeId", paymentField.getTransferType().getFrom().getId());
                params.put("transferTypeId", paymentField.getTransferType().getId());
                break;
        }
        context.sendMessage(isInsert ? "customField.inserted" : "customField.modified");
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditCustomFieldForm form = context.getForm();
        final long id = form.getFieldId();
        final CustomField.Nature nature = getNature(form);
        CustomField field;
        final BaseCustomFieldService<CustomField> service = resolveService(nature);
        if (id <= 0L) {
            field = nature.getEntityType().newInstance();
            switch (nature) {
                case OPERATOR:
                    ((OperatorCustomField) field).setMember((Member) context.getElement());
                    break;
                case MEMBER_RECORD:
                    final MemberRecordType memberRecordType = memberRecordTypeService.load(form.getMemberRecordTypeId());
                    ((MemberRecordCustomField) field).setMemberRecordType(memberRecordType);
                    break;
                case PAYMENT:
                    final TransferType transferType = transferTypeService.load(form.getTransferTypeId());
                    ((PaymentCustomField) field).setTransferType(transferType);
                    break;
            }
            // Retrieve the possible parent fields
            final List<? extends CustomField> possibleParentFields = service.listPossibleParentFields(field);
            request.setAttribute("possibleParentFields", possibleParentFields);
        } else {
            field = service.load(id);

            // Get the possible values according to the selected parent value, if any
            Collection<CustomFieldPossibleValue> possibleValues;
            final CustomField parent = field.getParent();
            if (parent == null) {
                // No parent - use all
                possibleValues = field.getPossibleValues(false);
            } else {
                final long parentValueId = form.getParentValueId();
                CustomFieldPossibleValue parentValue = null;
                if (parentValueId > 0L) {
                    // There's a parent value - load it
                    parentValue = service.loadPossibleValue(parentValueId);
                } else {
                    // No parent value selected - check whether the parent has at least one and select it
                    if (parent != null && CollectionUtils.isNotEmpty(parent.getPossibleValues(false))) {
                        parentValue = parent.getPossibleValues(false).iterator().next();
                        form.setParentValueId(parentValue.getId());
                    }
                }
                possibleValues = field.getPossibleValuesByParent(parentValue, false);
            }
            request.setAttribute("possibleValues", possibleValues);
        }

        getDataBinder(nature).writeAsString(form.getField(), field);
        request.setAttribute("field", field);
        request.setAttribute("nature", nature.name());
        RequestHelper.storeEnum(request, CustomField.Control.class, "controls");
        RequestHelper.storeEnum(request, CustomField.Type.class, "types");
        RequestHelper.storeEnum(request, CustomField.Size.class, "sizes");

        // Check the whether the field can be managed
        boolean canManage = false;
        switch (nature) {
            case OPERATOR:
                canManage = permissionService.hasPermission(MemberPermission.OPERATORS_MANAGE);
                break;
            case MEMBER_RECORD:
                canManage = permissionService.hasPermission(AdminSystemPermission.MEMBER_RECORD_TYPES_MANAGE);
                break;
            case PAYMENT:
                final PaymentCustomField paymentField = (PaymentCustomField) field;
                final TransferType backToTransferType = transferTypeService.load(paymentField.getTransferType().getId());
                if (paymentField.getTransferType().equals(backToTransferType)) {
                    // When should back to another TT, the form is not editable
                    canManage = permissionService.hasPermission(AdminSystemPermission.ACCOUNTS_MANAGE);
                }
                request.setAttribute("backToTransferType", backToTransferType);
                break;
            default:
                canManage = permissionService.hasPermission(AdminSystemPermission.CUSTOM_FIELDS_MANAGE);
                break;
        }
        request.setAttribute("canManage", canManage);

        // Store specific nature values
        final GroupQuery groupQuery = new GroupQuery();
        groupQuery.setStatus(Group.Status.NORMAL);
        switch (nature) {
            case MEMBER:
                // View may be any access level but WEB_SERVICE
                request.setAttribute("accessForView", EnumSet.complementOf(EnumSet.of(MemberCustomField.Access.WEB_SERVICE)));
                // Edit access level can be any but OTHER and WEB_SERVICE
                request.setAttribute("accessForEdit", EnumSet.complementOf(EnumSet.of(MemberCustomField.Access.OTHER, MemberCustomField.Access.WEB_SERVICE)));
                // The member search and ads search includes WEB_SERVICE
                request.setAttribute("memberAndAdsAccess", EnumSet.of(MemberCustomField.Access.NONE, MemberCustomField.Access.WEB_SERVICE, MemberCustomField.Access.ADMIN, MemberCustomField.Access.BROKER, MemberCustomField.Access.MEMBER));
                // The others (searches, etc.) are only NONE, ADMIN, BROKER or MEMBER
                request.setAttribute("access", EnumSet.of(MemberCustomField.Access.NONE, MemberCustomField.Access.ADMIN, MemberCustomField.Access.BROKER, MemberCustomField.Access.MEMBER));
                groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
                request.setAttribute("groups", groupService.search(groupQuery));
                RequestHelper.storeEnum(request, MemberCustomField.Indexing.class, "indexings");
                break;
            case ADMIN:
                groupQuery.setNatures(Group.Nature.ADMIN);
                request.setAttribute("groups", groupService.search(groupQuery));
                break;
            case OPERATOR:
                RequestHelper.storeEnum(request, OperatorCustomField.Visibility.class, "visibilities");
                break;
            case AD:
                RequestHelper.storeEnum(request, AdCustomField.Visibility.class, "visibilities");
                break;
            case MEMBER_RECORD:
                final MemberRecordCustomField memberRecordCustomField = (MemberRecordCustomField) field;
                request.setAttribute("memberRecordType", memberRecordCustomField.getMemberRecordType());
                RequestHelper.storeEnum(request, MemberRecordCustomField.Access.class, "accesses");
                break;
            case PAYMENT:
                final PaymentCustomField paymentCustomField = (PaymentCustomField) field;
                final TransferType transferType = paymentCustomField.getTransferType();
                request.setAttribute("transferType", transferType);
                final Set<PaymentCustomField.Access> accesses = EnumSet.allOf(PaymentCustomField.Access.class);
                if (paymentCustomField.getTransferType().getFixedDestinationMember() == null) {
                    accesses.remove(PaymentCustomField.Access.DESTINATION_MEMBER);
                }
                request.setAttribute("accesses", accesses);
                break;
        }
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditCustomFieldForm form = context.getForm();
        final CustomField.Nature nature = getNature(form);
        final CustomField field = getDataBinder(nature).readFromString(form.getField());
        resolveService(nature).validate(field);
    }

    private CustomField.Nature getNature(final EditCustomFieldForm form) {
        CustomField.Nature nature;
        try {
            nature = CustomField.Nature.valueOf(form.getNature());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        return nature;
    }

    @SuppressWarnings("unchecked")
    private <CF extends CustomField> BaseCustomFieldService<CF> resolveService(final CustomField.Nature nature) {
        switch (nature) {
            case AD:
                return (BaseCustomFieldService<CF>) adCustomFieldService;
            case ADMIN:
                return (BaseCustomFieldService<CF>) adminCustomFieldService;
            case LOAN_GROUP:
                return (BaseCustomFieldService<CF>) loanGroupCustomFieldService;
            case MEMBER:
                return (BaseCustomFieldService<CF>) memberCustomFieldService;
            case MEMBER_RECORD:
                return (BaseCustomFieldService<CF>) memberRecordCustomFieldService;
            case OPERATOR:
                return (BaseCustomFieldService<CF>) operatorCustomFieldService;
            case PAYMENT:
                return (BaseCustomFieldService<CF>) paymentCustomFieldService;
        }
        return null;
    }

}
