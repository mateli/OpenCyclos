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
package nl.strohalm.cyclos.webservices.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.FieldVO.FieldVOControl;
import nl.strohalm.cyclos.webservices.model.FieldVO.FieldVOType;
import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper methods for<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * @author luis
 */
public class FieldHelper {

    private CustomFieldHelper customFieldHelper;

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public <CFV extends CustomFieldValue> void toCustomFieldValues(final Class<CFV> valueClass, final List<? extends CustomField> allowedFields, final Map<String, String> customValues, final Collection<CFV> fieldValues) {
        if (MapUtils.isNotEmpty(customValues)) {
            for (String internalName : customValues.keySet()) {
                String value = customValues.get(internalName);
                if (StringUtils.isNotEmpty(value)) {
                    CustomField field = customFieldHelper.findByInternalName(allowedFields, internalName);
                    if (field == null) {
                        throw new IllegalArgumentException("Couldn't find custom field with internal name: '" + internalName + "' or the field is not searchable");
                    } else {
                        CFV fieldValue;
                        try {
                            fieldValue = valueClass.newInstance();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                        fieldValue.setField(field);
                        fieldValue.setValue(value);
                        fieldValues.add(fieldValue);
                    }
                }
            }
        }
    }

    /**
     * Converts a collection of custom fields into VOs
     */
    public List<FieldVO> toFieldVOs(final Collection<? extends CustomField> customFields) {
        if (customFields == null) {
            return null;
        }
        final List<FieldVO> vos = new ArrayList<FieldVO>(customFields.size());
        for (final CustomField field : customFields) {
            vos.add(toVO(field));
        }
        return vos;
    }

    public List<FieldValueVO> toList(final Collection<? extends CustomField> fields, final Collection<? extends CustomField> requiredFields, final Collection<? extends CustomFieldValue> customValues) {
        if (CollectionUtils.isEmpty(fields) || CollectionUtils.isEmpty(customValues)) {
            return Collections.emptyList();
        }
        final List<FieldValueVO> values = new ArrayList<FieldValueVO>();
        for (final CustomField field : fields) {
            String valueAsString = getFieldValue(field, requiredFields, customValues);
            if (valueAsString != null) {
                if (StringUtils.isNotEmpty(field.getPattern())) {
                    valueAsString = StringHelper.removeMask(field.getPattern(), valueAsString);
                }
                FieldValueVO fieldValueVO = new FieldValueVO(field.getInternalName(), valueAsString);
                final CustomFieldValue value = customFieldHelper.findByField(field, customValues);
                if (value.getPossibleValue() != null) {
                    fieldValueVO.setPossibleValueId(value.getPossibleValue().getId());
                }
                fieldValueVO.setFieldId(field.getId());
                fieldValueVO.setDisplayName(field.getName());
                values.add(fieldValueVO);
            }
        }
        return values;
    }

    /**
     * Builds a map with field values
     */
    public Map<String, String> toMap(final Collection<? extends CustomField> fields, final Collection<? extends CustomField> requiredFields, final Collection<? extends CustomFieldValue> customValues) {
        if (CollectionUtils.isEmpty(fields) || CollectionUtils.isEmpty(customValues)) {
            return Collections.emptyMap();
        }
        final Map<String, String> values = new LinkedHashMap<String, String>();
        for (final CustomField field : fields) {
            final String valueAsString = getFieldValue(field, requiredFields, customValues);
            if (valueAsString != null) {
                values.put(field.getInternalName(), valueAsString);
            }
        }
        return values;
    }

    /**
     * Converts a collection of custom field possible valuess into VOs
     */
    public List<PossibleValueVO> toPossibleValueVOs(final Collection<CustomFieldPossibleValue> possibleValues) {
        final List<PossibleValueVO> vos = new ArrayList<PossibleValueVO>(possibleValues.size());
        for (final CustomFieldPossibleValue possibleValue : possibleValues) {
            vos.add(toVO(possibleValue));
        }
        return vos;
    }

    /**
     * Convert a field to VO
     */
    public FieldVO toVO(final CustomField field) {
        if (field == null) {
            return null;
        }
        final FieldVO vo = new FieldVO();
        vo.setId(field.getId());
        vo.setDisplayName(field.getName());
        vo.setInternalName(field.getInternalName());
        vo.setType(FieldVOType.valueOf(field.getType().name()));
        vo.setMask(field.getPattern());
        vo.setControl(FieldVOControl.valueOf(field.getControl().name()));
        vo.setRequired(field.getValidation() != null && field.getValidation().isRequired());
        vo.setPossibleValues(toPossibleValueVOs(field.getPossibleValues(true)));
        vo.setMinLength(field.getValidation().getLengthConstraint().getMin());
        vo.setMaxLength(field.getValidation().getLengthConstraint().getMax());
        if (field.getParent() != null) {
            vo.setParentId(field.getParent().getId());
        }

        return vo;
    }

    /**
     * Convert a possible value to VO
     */
    public PossibleValueVO toVO(final CustomFieldPossibleValue possibleValue) {
        if (possibleValue == null) {
            return null;
        }
        final PossibleValueVO vo = new PossibleValueVO();
        vo.setId(possibleValue.getId());
        vo.setValue(possibleValue.getValue());
        if (possibleValue.getParent() != null) {
            vo.setParentId(possibleValue.getParent().getId());
        }
        vo.setDefaultValue(possibleValue.isDefaultValue());
        return vo;
    }

    protected String getFieldValue(final CustomField field, final Collection<? extends CustomField> requiredFields, final Collection<? extends CustomFieldValue> customValues) {
        final CustomFieldValue value = customFieldHelper.findByField(field, customValues);
        if (!(requiredFields != null && requiredFields.contains(field)) && (value instanceof MemberCustomFieldValue)) {
            final MemberCustomFieldValue memberValue = (MemberCustomFieldValue) value;
            if (memberValue.isHidden()) {
                // Skip hidden value
                return null;
            }
        }
        if (value != null) {
            String valueAsString = value.getValue();
            if (StringUtils.isNotEmpty(field.getPattern())) {
                valueAsString = StringHelper.applyMask(field.getPattern(), valueAsString);
            }
            return valueAsString;
        }
        return null;
    }

}
