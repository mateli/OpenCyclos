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
package nl.strohalm.cyclos.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import nl.strohalm.cyclos.entities.customization.fields.AdCustomField;
import nl.strohalm.cyclos.entities.customization.fields.AdminCustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField.Type;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Access;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.webservices.model.FieldValueVO;
import nl.strohalm.cyclos.webservices.model.RegistrationFieldValueVO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper class for custom field manipulation
 * @author luis
 */
public final class CustomFieldHelper {

    /**
     * Contains a relationship between custom field and its respective value
     * @author luis
     */
    public class Entry implements Serializable {
        private static final long      serialVersionUID = 1629234603383130863L;
        private final CustomField      field;
        private final CustomFieldValue value;

        public Entry(final CustomField field, final CustomFieldValue value) {
            this.field = field;
            this.value = value;
        }

        public CustomField getField() {
            return field;
        }

        public CustomFieldValue getValue() {
            return value;
        }

        @Override
        public String toString() {
            String field, value;
            try {
                field = this.field.getName();
            } catch (final NullPointerException e) {
                field = "null";
            }
            try {
                value = this.value.getValue();
            } catch (final NullPointerException e) {
                value = "null";
            }
            return field + "=" + value;
        }
    }

    private SettingsService settingsService;

    private ElementService  elementService;

    /**
     * Filters ad custom fields to be used on advanced search
     */
    public List<AdCustomField> adFieldsForSearch(final List<AdCustomField> fields) {
        final List<AdCustomField> adFields = new ArrayList<AdCustomField>();
        for (final AdCustomField field : fields) {
            if (field.isShowInSearch()) {
                adFields.add(field);
            }
        }
        return adFields;
    }

    /**
     * Builds a collection using all custom fields and their respective values, if any
     */
    public Collection<Entry> buildEntries(final Collection<? extends CustomField> fields, final Collection<? extends CustomFieldValue> values) {
        if (fields == null) {
            return null;
        }
        final Collection<Entry> entries = new ArrayList<Entry>(fields.size());
        for (final CustomField field : fields) {
            final CustomFieldValue fieldValue = findByField(field, values);
            if (fieldValue != null) {
                if (field.getType() == Type.MEMBER) {
                    final Long id = IdConverter.instance().valueOf(fieldValue.getValue());
                    if (id != null) {
                        fieldValue.setMemberValue(loadMember(id));
                    }
                } else if (StringUtils.isNotEmpty(field.getPattern())) {
                    fieldValue.setValue(StringHelper.removeMask(field.getPattern(), fieldValue.getValue()));
                }
            }
            entries.add(new Entry(field, fieldValue));
        }
        return entries;
    }

    /**
     * Builds a collection of field values given a value class, a collection of fields and a map of names/values
     */
    public <V extends CustomFieldValue> Collection<V> buildValues(final Class<V> valueClass, final Collection<? extends CustomField> fields, final Map<String, String> values) {
        if (valueClass != null && fields != null && values != null) {
            final Collection<V> fieldValues = new ArrayList<V>();
            for (final CustomField field : fields) {
                final String value = values.get(field.getInternalName());
                final V fieldValue = ClassHelper.instantiate(valueClass);
                fieldValue.setField(field);
                if (StringUtils.isNotEmpty(value)) {
                    final CustomField.Type type = field.getType();
                    if (type == CustomField.Type.ENUMERATED) {
                        fieldValue.setPossibleValue(findPossibleValue(value, field.getPossibleValues(false)));
                    } else if (type == CustomField.Type.MEMBER) {
                        fieldValue.setMemberValue(loadMember(IdConverter.instance().valueOf(value)));
                    } else {
                        fieldValue.setStringValue(value);
                    }
                }
                fieldValues.add(fieldValue);
            }
            return fieldValues;
        }
        return null;
    }

    /**
     * Clones the custom field values of a container and copy them to the other container setting the to container as the clone's owner
     */
    public <CF extends CustomField, CFV extends CustomFieldValue> void cloneFieldValues(final CustomFieldsContainer<CF, CFV> from, final CustomFieldsContainer<CF, CFV> to) {
        cloneFieldValues(from, to, false);
    }

    /**
     * Clones the custom field values of a container and copy them to the other container
     * @param resetOwner if true sets the owner as null for each clone otherwise sets the "to container" as the owner.
     */
    @SuppressWarnings("unchecked")
    public <CF extends CustomField, CFV extends CustomFieldValue> void cloneFieldValues(final CustomFieldsContainer<CF, CFV> from, final CustomFieldsContainer<CF, CFV> to, final boolean resetOwner) {
        final List<CFV> newCustomValues = new ArrayList<CFV>();
        final Collection<CFV> customValues = from.getCustomValues();
        if (customValues != null) {
            for (final CFV customValue : customValues) {
                final Object clone = customValue.clone();
                final CFV newCustomValue = (CFV) clone;
                if (resetOwner) {
                    newCustomValue.setOwner(null);
                } else {
                    newCustomValue.setOwner(to);
                }
                newCustomValue.setId(null);
                newCustomValues.add(newCustomValue);
            }
        }
        to.setCustomValues(newCustomValues);
    }

    /**
     * Finds the value of the given field inside the collection
     */
    public <V extends CustomFieldValue> V findByField(final CustomField field, final Collection<V> values) {
        if (values != null && field != null) {
            for (final V value : values) {
                if (field.equals(value.getField())) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Finds the value of the given field inside the collection
     */
    public <V extends CustomFieldValue> V findByFieldId(final Long fieldId, final Collection<V> values) {
        if (values != null && fieldId != null) {
            for (final V value : values) {
                if (value.getField().getId().equals(fieldId)) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Finds the value of the given field inside the collection
     */
    public <V extends CustomFieldValue> V findByFieldName(final String fieldName, final Collection<V> values) {
        if (values != null && StringUtils.isNotEmpty(fieldName)) {
            for (final V value : values) {
                if (value.getField().getInternalName().equals(fieldName)) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Finds a custom field in a collection by it's identifier
     */
    public <F extends CustomField> F findById(final Collection<F> fields, final Long id) {
        if (fields != null && id != null) {
            for (final F f : fields) {
                if (ObjectUtils.equals(f.getId(), id)) {
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * Attempts to find the field first by its id, then by its internal name.
     * @return
     */
    public <F extends CustomField> F findByIdOrInternalName(final Collection<F> fields, final Long id, final String internalName) {
        F result = null;
        result = findById(fields, id);
        if (result == null) {
            result = findByInternalName(fields, internalName);
        }
        return result;
    }

    /**
     * Finds a custom field in a collection by it's internal name
     */
    public <F extends CustomField> F findByInternalName(final Collection<F> fields, final String internalName) {
        if (fields != null && internalName != null) {
            for (final F f : fields) {
                if (ObjectUtils.equals(f.getInternalName(), internalName)) {
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * Finds a possible value reference on the collection
     */
    public CustomFieldPossibleValue findPossibleValue(final String value, final Collection<CustomFieldPossibleValue> possibleValues) {
        if (StringUtils.isNotEmpty(value)) {
            for (final CustomFieldPossibleValue possibleValue : possibleValues) {
                if (value.equals(possibleValue.getValue())) {
                    return possibleValue;
                }
            }
        }
        return null;
    }

    /**
     * Finds a possible value reference on the collection
     */
    public CustomFieldPossibleValue findPossibleValueById(final Long id, final Collection<CustomFieldPossibleValue> possibleValues) {
        if (EntityHelper.isValidId(id) && CollectionUtils.isNotEmpty(possibleValues)) {
            for (final CustomFieldPossibleValue possibleValue : possibleValues) {
                if (id.equals(possibleValue.getId())) {
                    return possibleValue;
                }
            }
        }
        return null;
    }

    /**
     * Finds a possible value label by id
     */
    public String findPossibleValueById(final Object value, final Collection<CustomFieldPossibleValue> possibleValues) {
        long id;
        try {
            id = CoercionHelper.coerce(Long.TYPE, value);
            for (final CustomFieldPossibleValue possibleValue : possibleValues) {
                if (id == possibleValue.getId()) {
                    return possibleValue.getValue();
                }
            }
        } catch (final Exception e) {
            // Keep on
        }
        return null;
    }

    /**
     * Returns a Map keyed by the field internal name of field values as string
     */
    public Map<String, String> getFields(final CustomFieldsContainer<?, ?> container) {
        final Map<String, String> values = new LinkedHashMap<String, String>();
        for (final CustomFieldValue value : container.getCustomValues()) {
            values.put(value.getField().getInternalName(), value.getValue());
        }
        return values;
    }

    /**
     * Returns the value of the field with the given internal name on the collection
     */
    public <FV extends CustomFieldValue> FV getValue(final String internalName, final Collection<FV> customValues) {
        for (final FV value : customValues) {
            if (value.getField().getInternalName().equals(internalName)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Returns a Map keyed by the custom field of custom field values
     */
    @SuppressWarnings("unchecked")
    public <CF extends CustomField, CFV extends CustomFieldValue> Map<CF, CFV> getValuesByField(final CustomFieldsContainer<CF, CFV> container) {
        final Map<CF, CFV> values = new LinkedHashMap<CF, CFV>();
        for (final CFV value : container.getCustomValues()) {
            values.put((CF) value.getField(), value);
        }
        return values;
    }

    /**
     * Returns a new collection with the result of merging the old custom field values and the new custom field values. The resulting field values
     * contains a subset of the allowed fields.
     * @param customFieldContainer the entity having the custom fields.
     * @param fieldValueVOs the new field values.
     * @param allowedFields the allowed fields that can be modified.
     */
    public <T extends CustomFieldValue> Collection<T> mergeFieldValues(final CustomFieldsContainer<?, T> customFieldContainer, final List<? extends FieldValueVO> pFieldValueVOs, final List<? extends CustomField> allowedFields) {
        final Collection<T> currentFieldValues = new ArrayList<T>(customFieldContainer.getCustomValues());

        // Clone the original list cause will make modifications.
        List<FieldValueVO> fieldValueVOs = null;
        if (pFieldValueVOs != null) {
            fieldValueVOs = new ArrayList<FieldValueVO>();
            for (final FieldValueVO fv : pFieldValueVOs) {
                fieldValueVOs.add((FieldValueVO) fv.clone());
            }

            // If the possible value or the value are not assigned then the value shouldn't be modified.
            for (final FieldValueVO fv : fieldValueVOs) {
                if (fv.getValue() == null && fv.getPossibleValueId() == null) {
                    final CustomField cf = findByIdOrInternalName(allowedFields, fv.getFieldId(), fv.getInternalName());
                    if (cf != null) {
                        final CustomFieldValue cfv = getValue(cf.getInternalName(), currentFieldValues);
                        if (cfv != null) {
                            fv.setValue(cfv.getValue());
                            if (cfv.getPossibleValue() != null) {
                                fv.setPossibleValueId(cfv.getPossibleValue().getId());
                            }
                            if (cfv.getMemberValue() != null) {
                                fv.setMemberValueId(cfv.getMemberValue().getId());
                            }
                        }
                    }
                }
                if (fv instanceof RegistrationFieldValueVO) {
                    final RegistrationFieldValueVO rfv = (RegistrationFieldValueVO) fv;
                    if (rfv.getHidden() == null) {
                        final CustomField cf = findByIdOrInternalName(allowedFields, rfv.getFieldId(), rfv.getInternalName());
                        if (cf != null) {
                            final MemberCustomFieldValue cfv = (MemberCustomFieldValue) getValue(cf.getInternalName(), currentFieldValues);
                            if (cfv != null) {
                                rfv.setHidden(cfv.isHidden());
                            }
                        }
                    }
                }
            }
        }

        final Collection<T> newFieldValues = toValueCollection(allowedFields, fieldValueVOs);
        if (CollectionUtils.isEmpty(newFieldValues)) {
            return currentFieldValues;
        }

        // Add all the current values that weren't modified
        for (final T cv : currentFieldValues) {
            if (allowedFields.contains(cv.getField())) {
                final boolean modifiedFieldValue = getValue(cv.getField().getInternalName(), newFieldValues) != null;
                if (!modifiedFieldValue) {
                    newFieldValues.add(cv);
                }
            }
        }

        return newFieldValues;
    }

    /**
     * Returns the basic fields only, that is, strings with control = text box, or integers or enums
     */
    @SuppressWarnings("unchecked")
    public <T extends CustomField> List<T> onlyBasic(final List<T> customFields) {
        final List<T> result = new ArrayList<T>(customFields.size());
        for (final CustomField field : customFields) {
            final CustomField.Type type = field.getType();
            final CustomField.Control control = field.getControl();
            boolean useField = false;
            if (type == CustomField.Type.STRING && control == CustomField.Control.TEXT) {
                useField = true;
            } else if (type == CustomField.Type.ENUMERATED || type == CustomField.Type.INTEGER) {
                useField = true;
            }
            if (useField) {
                result.add((T) field);
            }
        }
        return result;
    }

    /**
     * Filters the member custom field list, returning only those for ad search
     */
    public List<MemberCustomField> onlyForAdSearch(final List<MemberCustomField> fields) {
        final List<MemberCustomField> memberFields = new ArrayList<MemberCustomField>();
        final boolean unrestricted = LoggedUser.isSystemOrUnrestrictedClient();
        final Group group = unrestricted ? null : LoggedUser.group();
        for (final MemberCustomField field : fields) {
            final Access access = field.getAdSearchAccess();
            if (unrestricted || access != null && access.granted(group, true, LoggedUser.isBroker(), false, LoggedUser.isWebService())) {
                memberFields.add(field);
            }
        }
        return memberFields;
    }

    /**
     * Filters the ad custom field list, returning only those for ad search
     */
    public List<AdCustomField> onlyForAdsSearch(final List<AdCustomField> fields) {
        final Group.Nature nature = LoggedUser.hasUser() ? LoggedUser.group().getNature() : Group.Nature.MEMBER;
        final List<AdCustomField> adFields = new ArrayList<AdCustomField>();
        for (final AdCustomField field : fields) {
            final AdCustomField.Visibility visibility = field.getVisibility();
            if (visibility.granted(nature) || LoggedUser.isWebService() && visibility == AdCustomField.Visibility.WEB_SERVICE) {
                adFields.add(field);
            }
        }
        return adFields;
    }

    /**
     * Filters the admin custom field list, returning only those used for the given group
     */
    public List<AdminCustomField> onlyForGroup(final List<AdminCustomField> fields, final AdminGroup group) {
        final List<AdminCustomField> adminFields = new ArrayList<AdminCustomField>();
        for (final AdminCustomField field : fields) {
            if (field.getGroups().contains(group)) {
                adminFields.add(field);
            }
        }
        return adminFields;
    }

    /**
     * Filters the member custom field list, returning only those used for the given group
     */
    public List<MemberCustomField> onlyForGroup(final List<MemberCustomField> fields, final MemberGroup group) {
        final List<MemberCustomField> memberFields = new ArrayList<MemberCustomField>(fields.size());
        for (final MemberCustomField field : fields) {
            if (field.getGroups().contains(group)) {
                memberFields.add(field);
            }
        }
        return memberFields;
    }

    public List<MemberCustomField> onlyForGroups(final List<MemberCustomField> fields, final Collection<MemberGroup> groups) {
        final Set<MemberCustomField> memberFields = new HashSet<MemberCustomField>();
        for (final MemberGroup group : groups) {
            memberFields.addAll(onlyForGroup(fields, group));
        }
        return new ArrayList<MemberCustomField>(memberFields);
    }

    /**
     * Filters the member custom field list, returning only those for loan search
     */
    public List<MemberCustomField> onlyForLoanSearch(final List<MemberCustomField> fields) {
        final List<MemberCustomField> memberFields = new ArrayList<MemberCustomField>();
        final Group group = LoggedUser.group();
        for (final MemberCustomField field : fields) {
            final Access access = field.getLoanSearchAccess();
            if (access != null && access.granted(group, true, false, false, false)) {
                memberFields.add(field);
            }
        }
        return memberFields;
    }

    /**
     * Filters the member custom field list, returning only those for member search
     */
    public List<MemberCustomField> onlyForMemberSearch(final List<MemberCustomField> fields) {
        final List<MemberCustomField> memberFields = new ArrayList<MemberCustomField>(fields.size());
        if (LoggedUser.isSystemOrUnrestrictedClient()) {
            memberFields.addAll(fields);
            return memberFields;
        }
        final Group group = LoggedUser.group();
        for (final MemberCustomField field : fields) {
            final Access access = field.getMemberSearchAccess();
            if (access != null && access.granted(group, true, LoggedUser.isBroker(), false, LoggedUser.isWebService())) {
                memberFields.add(field);
            }
        }
        return memberFields;
    }

    public List<MemberCustomField> onlyInAllGroups(final List<MemberCustomField> fields, final Collection<MemberGroup> groups) {
        final Set<MemberCustomField> memberFields = new HashSet<MemberCustomField>();
        for (final MemberCustomField f : fields) {
            if (f.getGroups().containsAll(groups)) {
                memberFields.add(f);
            }
        }
        return new ArrayList<MemberCustomField>(memberFields);
    }

    /**
     * Lists only fields which are owned by the given group
     */
    public List<MemberCustomField> onlyOwnedFields(final List<MemberCustomField> fields, final MemberGroup group) {
        return doListVisibleFields(fields, group, true);
    }

    /**
     * Lists only fields which are visible by the given group
     */
    public List<MemberCustomField> onlyVisibleFields(final List<MemberCustomField> fields, final MemberGroup group) {
        return doListVisibleFields(fields, group, false);
    }

    public void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * Convert an array of FieldValue instances to a collection of CustomFieldValue
     */
    @SuppressWarnings("unchecked")
    public <T extends CustomFieldValue> Collection<T> toValueCollection(final Collection<? extends CustomField> fields, final List<? extends FieldValueVO> fieldValues) {
        if (CollectionUtils.isEmpty(fields) || CollectionUtils.isEmpty(fieldValues)) {
            return Collections.emptySet();
        }
        final List<T> customValues = new ArrayList<T>();
        for (final FieldValueVO fieldValue : fieldValues) {
            final CustomField field = findByIdOrInternalName(fields, fieldValue.getFieldId(), fieldValue.getInternalName());

            if (field == null) {
                throw new IllegalArgumentException("Couldn't find custom field for this field: " + fieldValue);
            }
            final T value = (T) ClassHelper.instantiate(field.getNature().getValueType());
            value.setField(field);

            if (field.getType() == CustomField.Type.ENUMERATED) {
                if (EntityHelper.isValidId(fieldValue.getPossibleValueId())) {
                    // Load the possible value and set its string representation.
                    final CustomFieldPossibleValue possibleValue = findPossibleValueById(fieldValue.getPossibleValueId(), field.getPossibleValues(true));
                    if (possibleValue == null) {
                        throw new IllegalArgumentException("Expected one of this values: " + field.getPossibleValues(true) + " for field: " + field);
                    }
                    value.setPossibleValue(possibleValue);
                } else {
                    // Multiple values are allowed. However, we need to pass as ids to the inner layers, and expect here to get ids or names
                    final Set<Long> possibleValueIds = new HashSet<Long>();
                    final String[] parts = StringUtils.split(fieldValue.getValue(), ',');
                    for (String part : parts) {
                        part = StringUtils.trimToNull(part);
                        if (part == null) {
                            continue;
                        }
                        CustomFieldPossibleValue possibleValue;
                        if (EntityHelper.isValidId(fieldValue.getValue())) {
                            possibleValue = findPossibleValueById(Long.parseLong(part), field.getPossibleValues(true));
                        } else {
                            possibleValue = findPossibleValue(part, field.getPossibleValues(true));
                        }
                        if (possibleValue == null) {
                            throw new IllegalArgumentException("Expected one of this values: " + field.getPossibleValues(true) + " for field: " + field);
                        }
                        possibleValueIds.add(possibleValue.getId());
                    }
                    value.setValue(StringUtils.join(possibleValueIds.iterator(), ','));
                }
            } else if (field.getType() == CustomField.Type.MEMBER) {
                Member memberValue = null;
                final Long memberValueId = fieldValue.getMemberValueId();
                boolean setMember = false;
                if (EntityHelper.isValidId(memberValueId)) {
                    // Load the member
                    memberValue = loadMember(memberValueId);
                    setMember = true;
                } else if (!StringUtils.isEmpty(fieldValue.getValue())) {
                    // Attempt by username
                    memberValue = loadMember(fieldValue.getValue());
                    setMember = true;
                }
                if (setMember && memberValue == null) {
                    throw new EntityNotFoundException(Member.class);
                }
                value.setMemberValue(memberValue);
            } else {
                // if it's a date value in ISO 8601 format, convert it to Cyclos date representation.
                Calendar parsedDateTime = null;
                if (field.getType() == CustomField.Type.DATE) {
                    parsedDateTime = parseISO8601Date(fieldValue.getValue());
                }
                if (parsedDateTime != null) {
                    final LocalSettings localSettings = settingsService.getLocalSettings();
                    value.setValue(localSettings.getDateConverter().toString(parsedDateTime));
                } else {
                    value.setValue(fieldValue.getValue());
                }
                if (StringUtils.isNotEmpty(field.getPattern())) {
                    value.setValue(StringHelper.removeMask(field.getPattern(), value.getValue()));
                }
            }
            if (fieldValue instanceof RegistrationFieldValueVO && value instanceof MemberCustomFieldValue) {
                final RegistrationFieldValueVO reg = (RegistrationFieldValueVO) fieldValue;
                final MemberCustomFieldValue memberValue = (MemberCustomFieldValue) value;
                memberValue.setHidden(reg.getHidden() == null ? Boolean.FALSE : reg.getHidden());
            }
            customValues.add(value);
        }
        return customValues;
    }

    private List<MemberCustomField> doListVisibleFields(List<MemberCustomField> fields, final MemberGroup group, final boolean byOwner) {
        fields = onlyForGroup(fields, group);
        for (final Iterator<MemberCustomField> iterator = fields.iterator(); iterator.hasNext();) {
            final MemberCustomField field = iterator.next();
            if (!field.getVisibilityAccess().granted(group, byOwner, false, false, LoggedUser.isWebService())) {
                iterator.remove();
            }
        }
        return fields;
    }

    private Member loadMember(final Long id) {
        try {
            return LoggedUser.runAsSystem(new Callable<Member>() {
                @Override
                public Member call() throws Exception {
                    return elementService.<Member> load(id, Element.Relationships.GROUP);
                }
            });
        } catch (final Exception e) {
            return null;
        }
    }

    private Member loadMember(final String username) {
        try {
            return (Member) elementService.loadUser(username).getElement();
        } catch (final Exception e) {
            return null;
        }
    }

    private Calendar parseISO8601Date(final String date) {
        try {
            return javax.xml.bind.DatatypeConverter.parseDateTime(date);
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }

}
