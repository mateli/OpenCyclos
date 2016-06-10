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
package nl.strohalm.cyclos.webservices.fields;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Access;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.customization.AdCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.BaseCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.services.ServiceClientServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;
import nl.strohalm.cyclos.webservices.utils.FieldHelper;

/**
 * Web service implementation
 * @author luis
 */
@WebService(name = "fields", serviceName = "fields")
public class FieldWebServiceImpl implements FieldWebService {

    private FieldHelper                    fieldHelper;
    private MemberCustomFieldServiceLocal  memberCustomFieldServiceLocal;
    private AdCustomFieldServiceLocal      adCustomFieldServiceLocal;
    private PaymentCustomFieldServiceLocal paymentCustomFieldServiceLocal;
    private ServiceClientServiceLocal      serviceClientServiceLocal;
    private CustomFieldHelper              customFieldHelper;

    @Override
    public List<FieldVO> adFieldsForAdSearch() {
        final List<FieldVO> vos = new ArrayList<FieldVO>();
        final List<AdCustomField> customFields = adCustomFieldServiceLocal.list();
        for (final AdCustomField field : customFields) {
            if (field.isShowInSearch()) {
                vos.add(fieldHelper.toVO(field));
            }
        }
        return vos;
    }

    @Override
    public List<FieldVO> allAdFields() {
        return fieldHelper.toFieldVOs(adCustomFieldServiceLocal.list());
    }

    @Override
    public List<FieldVO> allMemberFields() {
        return fieldHelper.toFieldVOs(memberCustomFieldServiceLocal.list());
    }

    @Override
    public List<FieldVO> memberFields(final Long groupId) {
        final ServiceClient client = serviceClientServiceLocal.load(WebServiceContext.getClient().getId(), ServiceClient.Relationships.MANAGE_GROUPS);
        final Set<MemberGroup> groups = client.getManageGroups();
        if (groups.isEmpty()) {
            throw new PermissionDeniedException();
        }
        // Find the group
        MemberGroup group = null;
        if (groupId == null || groupId.intValue() <= 0) {
            group = groups.iterator().next();
        } else {
            for (final MemberGroup memberGroup : groups) {
                if (memberGroup.getId().equals(groupId)) {
                    group = memberGroup;
                    break;
                }
            }
            if (group == null) {
                throw new PermissionDeniedException();
            }
        }
        final List<MemberCustomField> fields = customFieldHelper.onlyForGroup(memberCustomFieldServiceLocal.list(), group);
        return fieldHelper.toFieldVOs(fields);
    }

    @Override
    public List<FieldVO> memberFieldsForAdSearch() {
        final List<FieldVO> vos = new ArrayList<FieldVO>();
        for (final MemberCustomField field : memberCustomFieldServiceLocal.list()) {
            final Access adSearchAccess = field.getAdSearchAccess();
            if (adSearchAccess != null && adSearchAccess == MemberCustomField.Access.MEMBER) {
                vos.add(fieldHelper.toVO(field));
            }
        }
        return vos;
    }

    @Override
    public List<FieldVO> memberFieldsForMemberSearch() {
        final List<FieldVO> vos = new ArrayList<FieldVO>();
        for (final MemberCustomField field : memberCustomFieldServiceLocal.list()) {
            final Access memberSearchAccess = field.getMemberSearchAccess();
            if (memberSearchAccess != null && memberSearchAccess == MemberCustomField.Access.MEMBER) {
                vos.add(fieldHelper.toVO(field));
            }
        }
        return vos;
    }

    @Override
    public List<FieldVO> paymentFields(final Long transferTypeId) {
        final TransferType transferType = EntityHelper.reference(TransferType.class, transferTypeId);
        final List<PaymentCustomField> customFields = paymentCustomFieldServiceLocal.list(transferType, true);
        return fieldHelper.toFieldVOs(customFields);
    }

    @Override
    public List<PossibleValueVO> possibleValuesForAdField(final String name) {
        return possibleValuesForAdFieldGivenParent(name, null);
    }

    @Override
    public List<PossibleValueVO> possibleValuesForAdFieldGivenParent(final String name, final Long parentValueId) {
        return possibleValues(adCustomFieldServiceLocal, name, adCustomFieldServiceLocal.list(), parentValueId);
    }

    @Override
    public List<PossibleValueVO> possibleValuesForMemberField(final String name) {
        return possibleValuesForMemberFieldGivenParent(name, null);
    }

    @Override
    public List<PossibleValueVO> possibleValuesForMemberFieldGivenParent(final String name, final Long parentValueId) {
        return possibleValues(memberCustomFieldServiceLocal, name, memberCustomFieldServiceLocal.list(), parentValueId);
    }

    @Override
    public List<PossibleValueVO> possibleValuesForPaymentFields(final Long transferTypeId, final String name) {
        final TransferType transferType = EntityHelper.reference(TransferType.class, transferTypeId);
        final List<PaymentCustomField> fields = paymentCustomFieldServiceLocal.list(transferType, true);
        return possibleValues(paymentCustomFieldServiceLocal, name, fields, null);
    }

    public void setAdCustomFieldServiceLocal(final AdCustomFieldServiceLocal adCustomFieldService) {
        adCustomFieldServiceLocal = adCustomFieldService;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setFieldHelper(final FieldHelper fieldHelper) {
        this.fieldHelper = fieldHelper;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        memberCustomFieldServiceLocal = memberCustomFieldService;
    }

    public void setPaymentCustomFieldServiceLocal(final PaymentCustomFieldServiceLocal paymentCustomFieldService) {
        paymentCustomFieldServiceLocal = paymentCustomFieldService;
    }

    public void setServiceClientServiceLocal(final ServiceClientServiceLocal serviceClientService) {
        serviceClientServiceLocal = serviceClientService;
    }

    private List<PossibleValueVO> possibleValues(final BaseCustomFieldServiceLocal<?> service, final String internalName, final List<? extends CustomField> fields, final Long parentValueId) {
        if (internalName == null) {
            return null;
        }

        final CustomField field = customFieldHelper.findByInternalName(fields, internalName);
        if (field == null || field.getType() != CustomField.Type.ENUMERATED) {
            return null;
        }
        final List<PossibleValueVO> values = new ArrayList<PossibleValueVO>();
        Collection<CustomFieldPossibleValue> possibleValues;
        if (parentValueId == null) {
            possibleValues = field.getPossibleValues(true);
        } else {
            possibleValues = field.getPossibleValuesByParent(service.loadPossibleValue(parentValueId), true);
        }
        for (final CustomFieldPossibleValue possibleValue : possibleValues) {
            values.add(fieldHelper.toVO(possibleValue));
        }
        return values;
    }
}
