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
package nl.strohalm.cyclos.services.customization;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import nl.strohalm.cyclos.dao.customizations.CustomFieldDAO;
import nl.strohalm.cyclos.dao.customizations.CustomFieldPossibleValueDAO;
import nl.strohalm.cyclos.dao.customizations.CustomFieldValueDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomField.Type;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.Validation;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Element.Nature;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomFieldsContainer;
import nl.strohalm.cyclos.utils.CustomObjectHandler;
import nl.strohalm.cyclos.utils.ElementVO;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.StringHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.conversion.CalendarConverter;
import nl.strohalm.cyclos.utils.conversion.ConversionException;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.NumberConverter;
import nl.strohalm.cyclos.utils.lock.UniqueObjectHandler;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.LengthValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.UniqueError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.utils.validation.Validator.Property;
import nl.strohalm.cyclos.utils.validation.Validator.PropertyRetrieveStrategy;
import nl.strohalm.cyclos.webservices.model.FieldVO;
import nl.strohalm.cyclos.webservices.model.PossibleValueVO;
import nl.strohalm.cyclos.webservices.utils.FieldHelper;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.support.PropertyComparator;

/**
 * Base implementation for custom field services
 * @author luis
 */
public abstract class BaseCustomFieldServiceImpl<CF extends CustomField> implements BaseCustomFieldServiceLocal<CF> {

    /**
     * Validator for decimal fields
     * 
     * @author luis
     */
    public final class BigDecimalValidator implements PropertyValidation {
        private static final long serialVersionUID = -7933981104151866154L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final String str = (String) value;
            final NumberConverter<BigDecimal> numberConverter = settingsService.getLocalSettings().getNumberConverter();
            try {
                numberConverter.valueOf(str);
                return null;
            } catch (final ConversionException e) {
                return new InvalidError();
            }
        }
    }

    /**
     * Retrieving strategy for validating properties
     * @author luis
     */
    public class CustomFieldRetrievingStrategy implements PropertyRetrieveStrategy {

        private static final long serialVersionUID = 8667919404137289046L;
        private final CustomField field;

        public CustomFieldRetrievingStrategy(final CustomField field) {
            this.field = field;
        }

        @Override
        public Object description(final Object object, final String name) {
            return field;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object get(final Object object) {
            final Collection<? extends CustomFieldValue> values = (Collection<? extends CustomFieldValue>) PropertyHelper.get(object, "customValues");
            final CustomFieldValue fieldValue = customFieldHelper.findByField(field, values);
            String value = fieldValue == null ? null : fieldValue.getValue();
            if (StringUtils.isNotEmpty(field.getPattern())) {
                value = StringHelper.removeMask(field.getPattern(), value);
            }
            return value;
        }
    }

    /**
     * Validator for date fields
     * 
     * @author luis
     */
    public class DateValidator implements PropertyValidation {
        private static final long serialVersionUID = 5145399976834903999L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final String str = (String) value;
            final CalendarConverter dateConverter = settingsService.getLocalSettings().getRawDateConverter();
            try {
                final Calendar date = dateConverter.valueOf(str);
                if (date != null) {
                    final int year = date.get(Calendar.YEAR);
                    if (year < 1900 || year > 2100) {
                        return new InvalidError();
                    }
                }
                return null;
            } catch (final ConversionException e) {
                return new InvalidError();
            }
        }
    }

    /**
     * Validator for enumerated fields
     * 
     * @author luis
     */
    public class EnumeratedValidator implements PropertyValidation {
        private static final long serialVersionUID = 5145399976834903999L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final String str = (String) value;
            if (StringUtils.isEmpty(str)) {
                return null;
            }
            final CustomField field = (CustomField) property;
            CustomFieldPossibleValue possibleValue = null;

            possibleValue = loadPossibleValue(str, field);

            // Return error if not found
            return possibleValue == null ? new InvalidError() : null;
        }
    }

    /**
     * Validator for integer fields
     * 
     * @author luis
     */
    public class IntegerValidator implements PropertyValidation {
        private static final long serialVersionUID = 5145399976834903999L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final String str = (String) value;
            if (StringUtils.isNotEmpty(str) && !StringUtils.isNumeric(str)) {
                return new InvalidError();
            }
            return null;
        }
    }

    /**
     * Validates a java identifier
     * @author Jefferson Magno
     */
    public class JavaIdentifierValidation implements PropertyValidation {
        private static final long serialVersionUID = 259170291118675512L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final String string = (String) value;
            if (StringUtils.isNotEmpty(string) && !StringHelper.isValidJavaIdentifier(string)) {
                return new InvalidError();
            }
            return null;
        }
    }

    /**
     * Validator for member fields
     * 
     * @author luis
     */
    public class MemberValidator implements PropertyValidation {
        private static final long serialVersionUID = 5145399976834903999L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final String idStr = (String) value;
            if (StringUtils.isEmpty(idStr)) {
                return null;
            }
            if (StringUtils.isNotEmpty(idStr)) {
                Long id;
                try {
                    id = Long.valueOf(idStr);
                    ElementVO elementVO = elementService.getElementVO(id);
                    if (elementVO.getNature() != Nature.MEMBER) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    return new InvalidError();
                }
            }
            return null;
        }
    }

    /**
     * Validates the parent field
     * @author luis
     */
    public final class ParentValidator implements PropertyValidation {

        private static final long serialVersionUID = -6383825246336857857L;

        @Override
        @SuppressWarnings("unchecked")
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final CF field = (CF) object;
            final CustomField parent = (CustomField) value;
            if (parent != null) {
                final List<CF> possibleParents = listPossibleParentFields(field);
                if (!possibleParents.contains(parent)) {
                    return new InvalidError();
                }
            }
            return null;
        }

    }

    /**
     * Validate that an enum field value contains a possible value according to its parent value
     * @author jcomas
     */
    public class ParentValueValidation implements PropertyValidation {
        private static final long serialVersionUID = 6222393116036296454L;

        @Override
        public ValidationError validate(final Object object, final Object data, final Object value) {
            CustomFieldsContainer<?, ?> c;
            if (!(object instanceof CustomFieldsContainer)) {
                return null;
            } else {
                c = (CustomFieldsContainer<?, ?>) object;
            }
            final CustomField field = (CustomField) data;

            CustomFieldValue customFieldValue = null;
            CustomFieldPossibleValue possibleValue = null;

            CustomFieldValue parentCustomFieldValue = null;
            CustomFieldPossibleValue parentPossibleValue = null;

            // Get the custom field values for actual field and its parent
            for (CustomFieldValue cfv : c.getCustomValues()) {
                if (customFieldValue == null && cfv.getField().getId() == field.getId()) {
                    customFieldValue = cfv;
                } else if (parentCustomFieldValue == null && cfv.getField().getId() == field.getParent().getId()) {
                    parentCustomFieldValue = cfv;
                }
                if (customFieldValue != null && parentCustomFieldValue != null) {
                    break;
                }
            }

            // If no customFieldValue, there is nothing to check
            if (customFieldValue == null) {
                return null;
            }

            // Get the possible values for actual and parent
            possibleValue = loadPossibleValue(customFieldValue);

            // If no possible value, there is nothing to check
            if (possibleValue == null) {
                return null;
            }

            parentPossibleValue = loadPossibleValue(parentCustomFieldValue);

            if (parentPossibleValue == null || !parentPossibleValue.equals(possibleValue.getParent())) {
                return new ValidationError("expected value " + possibleValue.getParent() + " in parent of field " + field);
            }

            return null;
        }
    }

    /**
     * A cache key for possible values. Cannot use the id itself to differentiate it from the field id
     * 
     * @author luis
     */
    public static class PossibleValueKey implements Serializable {
        private static final long serialVersionUID = 6220627534414217532L;
        private final long        id;

        public PossibleValueKey(final long id) {
            this.id = id;
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof PossibleValueKey)) {
                return false;
            }
            PossibleValueKey key = (PossibleValueKey) obj;
            return id == key.id;
        }

        @Override
        public int hashCode() {
            return (int) id;
        }

    }

    /**
     * Validator to ensure the internal name is unique
     * 
     * @author luis
     */
    public class UniqueCustomFieldInternalNameValidation implements PropertyValidation {
        private static final long serialVersionUID = 1L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            final CustomField field = (CustomField) object;

            if (field.getInternalName() == null || field.getInternalName().equals("")) {
                return null;
            }

            return customFieldDao.isInternalNameUsed(field) ? new UniqueError(field.getInternalName()) : null;
        }
    }

    /**
     * Validates an unique field value
     * @author luis
     */
    public class UniqueFieldValueValidation implements PropertyValidation {
        private static final long serialVersionUID = 6222393116036296454L;

        @Override
        public ValidationError validate(final Object object, final Object data, final Object value) {
            if (!(object instanceof CustomFieldsContainer<?, ?>)) {
                return null;
            }
            if (object instanceof Transfer && ((Transfer) object).getScheduledPayment() != null) {
                // We cannot validate unique on scheduled payment installments, as all installments share the same values, and would always fail
                return null;
            }
            final CustomField field = (CustomField) data;
            final String string = (String) value;
            if (StringUtils.isNotEmpty(string)) {
                // Build a field value
                CustomFieldValue fieldValue;
                try {
                    fieldValue = field.getNature().getValueType().newInstance();
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
                fieldValue.setField(field);
                fieldValue.setOwner(object);
                fieldValue.setStringValue(string);

                // Check uniqueness
                if (customFieldValueDao.valueExists(fieldValue)) {
                    return new UniqueError(fieldValue.getStringValue());
                }
            }
            return null;
        }
    }

    private Validator                     possibleValueNavigator;

    protected static final String         ALL_KEY = "_ALL_";

    protected static final List<String>   EXCLUDED_PROPERTIES_FOR_DEPENDENT_FIELDS;

    static {
        final List<String> excluded = new ArrayList<String>();
        excluded.add("class");
        excluded.add("id");
        excluded.add("name");
        excluded.add("internalName");
        excluded.add("parent");
        excluded.add("description");
        excluded.add("allSelectedLabel");
        excluded.add("type");
        excluded.add("control");
        excluded.add("size");
        excluded.add("description");
        excluded.add("possibleValues");
        excluded.add("children");
        EXCLUDED_PROPERTIES_FOR_DEPENDENT_FIELDS = Collections.unmodifiableList(excluded);
    }

    protected final Class<CF>             customFieldType;
    protected FetchServiceLocal           fetchService;
    protected PermissionServiceLocal      permissionService;
    protected ElementServiceLocal         elementService;
    protected SettingsServiceLocal        settingsService;
    protected CustomFieldDAO              customFieldDao;
    protected CustomFieldValueDAO         customFieldValueDao;
    protected CustomFieldPossibleValueDAO customFieldPossibleValueDao;
    private CacheManager                  cacheManager;
    protected final Relationship[]        fetch;
    private Validator                     validator;
    protected CustomObjectHandler         customObjectHandler;
    protected FieldHelper                 fieldHelper;
    protected CustomFieldHelper           customFieldHelper;
    private UniqueObjectHandler           uniqueObjectHandler;

    protected BaseCustomFieldServiceImpl(final Class<CF> customFieldType) {
        this.customFieldType = customFieldType;
        Collection<Relationship> fetch = new ArrayList<Relationship>();
        fetch.addAll(Arrays.asList(CustomField.Relationships.POSSIBLE_VALUES, CustomField.Relationships.CHILDREN, RelationshipHelper.nested(CustomField.Relationships.PARENT, CustomField.Relationships.POSSIBLE_VALUES)));
        fetch.addAll(resolveAdditionalFetch());
        this.fetch = fetch.toArray(new Relationship[fetch.size()]);
    }

    @Override
    public void clearCache() {
        getCache().clear();
    }

    @Override
    public FieldVO getFieldVO(final Long customFieldId) {
        if (customFieldId == null) {
            return null;
        }
        CustomField cf = load(customFieldId);
        return fieldHelper.toVO(cf);
    }

    @Override
    public List<FieldVO> getFieldVOs(final List<Long> customFieldIds) {
        if (customFieldIds == null) {
            return null;
        }
        List<CustomField> customFields = new ArrayList<CustomField>(customFieldIds.size());
        for (Long id : customFieldIds) {
            if (id != null) {
                CustomField cf = load(id);
                customFields.add(cf);
            }
        }
        return fieldHelper.toFieldVOs(customFields);
    }

    @Override
    public List<PossibleValueVO> getPossibleValueVOs(final Long customFieldId, final Long possibleValueParentId) {
        if (customFieldId == null) {
            return null;
        }
        CF cf = load(customFieldId);

        if (cf.getType() != CustomField.Type.ENUMERATED) {
            return null;
        }
        FieldVO fieldVO = fieldHelper.toVO(cf);
        List<PossibleValueVO> possibleValues = fieldVO.getPossibleValues();
        if (possibleValues != null) {
            if (possibleValueParentId != null) {
                // Remove all the possible values that doesn't have parentValueId as parent
                for (PossibleValueVO pvo : possibleValues) {
                    if (!pvo.getParentId().equals(possibleValueParentId)) {
                        possibleValues.remove(pvo);
                    }
                }
            }
            return possibleValues;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<CF> listPossibleParentFields(final CF field) {
        if (field == null || (field.isPersistent() && field.getType() != CustomField.Type.ENUMERATED)) {
            return new ArrayList<CF>();
        }
        final List<CF> fields = new ArrayList<CF>(list(field));

        // Remove the field itself, those which are not enumerated and those who already have a parent (don't allow multiple levels)
        for (final Iterator<CF> iterator = fields.iterator(); iterator.hasNext();) {
            final CF current = iterator.next();
            if (field.equals(current) || current.getType() != CustomField.Type.ENUMERATED || current.getControl() != CustomField.Control.SELECT || current.getParent() != null) {
                iterator.remove();
            }
        }
        return fields;
    }

    @Override
    public List<CF> load(final Collection<Long> ids) {
        List<CF> result = new ArrayList<CF>(ids.size());
        for (Long id : ids) {
            result.add(load(id));
        }
        return result;
    }

    @Override
    public CF load(final Long id) {
        return getCache().<CF> get(id, new CacheCallback() {
            @Override
            public Object retrieve() {
                return loadChecked(id);
            }
        });
    }

    @Override
    public CustomFieldPossibleValue loadPossibleValue(final Long id) {
        return getCache().get("_POSSIBLE_VALUE_" + id, new CacheCallback() {
            @Override
            public Object retrieve() {
                return loadCheckedPossibleValue(id);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CustomFieldPossibleValue> loadPossibleValues(final Collection<Long> ids) {
        List<CustomFieldPossibleValue> result = new ArrayList<CustomFieldPossibleValue>(ids.size());
        for (Long id : ids) {
            result.add(loadPossibleValue(id));
        }
        Collections.sort(result, new BeanComparator("value"));
        return result;
    }

    @Override
    public int remove(final Long... ids) {
        for (Long id : ids) {
            CustomField field = customFieldDao.load(id);
            if (!customFieldType.isInstance(field)) {
                throw new EntityNotFoundException();
            }
        }
        getCache().clear();
        return customFieldDao.delete(ids);
    }

    @Override
    public int removePossibleValue(final Long... ids) {
        for (Long id : ids) {
            loadCheckedPossibleValue(id);
        }
        getCache().clear();
        return customFieldPossibleValueDao.delete(ids);
    }

    @Override
    public int replacePossibleValues(CustomFieldPossibleValue oldValue, CustomFieldPossibleValue newValue) {
        oldValue = fetchService.fetch(oldValue);
        newValue = fetchService.fetch(newValue);
        if (!oldValue.getField().equals(newValue.getField())) {
            throw new ValidationException();
        }
        return customFieldValueDao.moveValues(oldValue, newValue);
    }

    @Override
    public CF save(CF field) {
        // Special handling for fields with a parent field
        CustomField parent = null;
        if (field.getParent() != null) {
            // When the field has a parent, several settings are copied from it
            parent = fetchService.fetch(field.getParent());
            copyParentProperties(parent, field);
        }
        validate(field);
        if (field.isTransient()) {
            field.setChildren(new ArrayList<CustomField>());

            if (parent == null) {
                int maxOrder = 0;
                for (CF cf : list(field)) {
                    if (cf.getOrder() > maxOrder) {
                        maxOrder = cf.getOrder();
                    }
                }
                // Top level fields: set the order after other fields
                field.setOrder(maxOrder + 1);
            } else {
                parent.getChildren().add(field);
            }

            // Save the field
            field = customFieldDao.insert(field);

            if (parent != null) {
                // Nested fields: position the field just after his parent
                final List<Long> order = new ArrayList<Long>();
                List<CF> allFields = list(field);
                for (int i = 0; i < allFields.size(); i++) {
                    CF cf = allFields.get(i);
                    if (cf.getParent() != null) {
                        continue;
                    }
                    order.add(cf.getId());
                }
                setOrder(order);
            }
        } else {
            // Keep the order
            final CustomField oldversion = customFieldDao.load(field.getId());
            // in case of member custom fields, if set unhidden, all existing values must be unhidden
            // TODO RINKE 1: TEST
            if (oldversion instanceof MemberCustomField) {
                final MemberCustomField oldversionMemberField = (MemberCustomField) oldversion;
                final MemberCustomField newversion = (MemberCustomField) field;
                if (oldversionMemberField.isMemberCanHide() && !newversion.isMemberCanHide()) {
                    // set all present field values to unhide
                    customFieldValueDao.unHideValues(newversion);
                }
            }
            field.setOrder(oldversion.getOrder());

            field = customFieldDao.update(field);

            // Update the dependent properties for child fields
            if (field.getType() == CustomField.Type.ENUMERATED) {
                field = fetchService.reload(field, CustomField.Relationships.CHILDREN);
                for (final CustomField child : field.getChildren()) {
                    copyParentProperties(field, child);
                }
            }
        }
        getCache().clear();
        return field;
    }

    @Override
    public CustomFieldPossibleValue save(CustomFieldPossibleValue possibleValue) throws ValidationException {
        validate(possibleValue);
        try {
            if (possibleValue.isTransient()) {
                possibleValue = customFieldPossibleValueDao.insert(possibleValue);
            } else {
                possibleValue = customFieldPossibleValueDao.update(possibleValue);
            }
            customFieldPossibleValueDao.ensureDefault(possibleValue);
        } finally {
            getCache().clear();
        }
        return possibleValue;
    }

    public final void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public final void setCustomFieldDao(final CustomFieldDAO customFieldDao) {
        this.customFieldDao = customFieldDao;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public final void setCustomFieldPossibleValueDao(final CustomFieldPossibleValueDAO customFieldPossibleValueDao) {
        this.customFieldPossibleValueDao = customFieldPossibleValueDao;
    }

    public final void setCustomFieldValueDao(final CustomFieldValueDAO customFieldValueDao) {
        this.customFieldValueDao = customFieldValueDao;
    }

    public final void setCustomObjectHandler(final CustomObjectHandler customObjectHandler) {
        this.customObjectHandler = customObjectHandler;
    }

    public final void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public final void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public final void setFieldHelper(final FieldHelper fieldHelper) {
        this.fieldHelper = fieldHelper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setOrder(final List<Long> ids) {
        int order = 0;
        for (Long id : ids) {
            CF field = loadChecked(id);
            field.setOrder(++order);
            List<CustomField> children = new ArrayList<CustomField>(field.getChildren());
            if (CollectionUtils.isNotEmpty(children)) {
                Collections.sort(children, new PropertyComparator("name", true, true));
                for (CustomField child : children) {
                    child.setOrder(++order);
                }
            }
        }
        getCache().clear();
    }

    public final void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public final void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setUniqueObjectHandler(final UniqueObjectHandler uniqueObjectHandler) {
        this.uniqueObjectHandler = uniqueObjectHandler;
    }

    @Override
    public void validate(final CF field) {
        getValidator().validate(field);
    }

    @Override
    public void validate(final CustomFieldPossibleValue possibleValue) throws ValidationException {
        getPossibleValueValidator().validate(possibleValue);
    }

    /**
     * May be overridden in order to append any custom validations
     */
    protected void appendValidations(final Validator validator) {
    }

    protected void doSaveValues(final CustomFieldsContainer<?, ?> owner) {
        final Collection<? extends CustomFieldValue> customValues = owner.getCustomValues();
        if (customValues == null || customValues.isEmpty()) {
            return;
        }

        for (final CustomFieldValue value : customValues) {
            // Retrieve the field value
            final CustomField field = fetchService.fetch(value.getField());
            lockUniqueFieldValue(field, value);
            value.setField(field);
            CustomFieldPossibleValue possibleValue = null;
            Member memberValue = null;
            String stringValue = null;
            switch (field.getType()) {
                case ENUMERATED:
                    // Load the possible value
                    Long possibleValueId = null;
                    if (value.getPossibleValue() != null) {
                        possibleValueId = value.getPossibleValue().getId();
                    } else {
                        possibleValueId = IdConverter.instance().valueOf(value.getValue());
                    }
                    boolean tryByValue = possibleValueId == null;
                    boolean invalidPossibleValue = false;
                    if (possibleValueId != null) {
                        // Try by id
                        try {
                            possibleValue = customFieldPossibleValueDao.load(possibleValueId);
                            invalidPossibleValue = !possibleValue.isEnabled();
                        } catch (final EntityNotFoundException e) {
                            tryByValue = true;
                        }
                    }
                    if (tryByValue && StringUtils.isNotEmpty(value.getValue())) {
                        // Try by field id + value
                        try {
                            possibleValue = customFieldPossibleValueDao.load(field.getId(), value.getValue());
                            invalidPossibleValue = !possibleValue.isEnabled();
                        } catch (final EntityNotFoundException e) {
                            invalidPossibleValue = true;
                        }
                    }
                    if (invalidPossibleValue) {
                        throw createValidationException(field);
                    }
                    break;
                case MEMBER:
                    Long memberId = null;
                    if (value.getMemberValue() != null) {
                        memberId = value.getMemberValue().getId();
                    } else {
                        memberId = IdConverter.instance().valueOf(value.getValue());
                    }
                    if (memberId != null) {
                        boolean invalidMember = false;
                        try {
                            final Long mid = memberId;
                            memberValue = LoggedUser.runAsSystem(new Callable<Member>() {
                                @Override
                                public Member call() throws Exception {
                                    Element element = elementService.load(mid);
                                    if (!(element instanceof Member)) {
                                        throw new EntityNotFoundException();
                                    }
                                    return (Member) element;
                                }
                            });
                        } catch (final EntityNotFoundException e) {
                            invalidMember = true;
                        }
                        if (invalidMember) {
                            throw createValidationException(field);
                        }
                    }
                    break;
                default:
                    if ((field.getType() != CustomField.Type.STRING) || (field.getControl() != CustomField.Control.RICH_EDITOR)) {
                        stringValue = StringHelper.removeMarkupTags(value.getValue());
                    } else {
                        stringValue = value.getValue();
                    }

                    // A String value
                    stringValue = StringUtils.trimToNull(stringValue);
                    if (StringUtils.isNotEmpty(field.getPattern())) {
                        stringValue = StringHelper.removeMask(field.getPattern(), stringValue);
                    }
                    break;
            }
            // Check if the value exists for the given owner
            try {
                final CustomFieldValue existing = customFieldValueDao.load(field, owner);
                // Exists - just update the value
                existing.setStringValue(stringValue);
                existing.setPossibleValue(possibleValue);
                existing.setMemberValue(memberValue);
                if (value instanceof MemberCustomFieldValue) {
                    ((MemberCustomFieldValue) existing).setHidden(((MemberCustomFieldValue) value).isHidden());
                }
                customFieldValueDao.update(existing);
            } catch (final EntityNotFoundException e) {
                // Does not exists yet - insert a new value
                value.setOwner(owner);
                value.setStringValue(stringValue);
                value.setPossibleValue(possibleValue);
                value.setMemberValue(memberValue);
                if (value.isTransient()) {
                    customFieldValueDao.insert(value);
                } else {
                    customFieldValueDao.update(value);
                }
            }
        }
    }

    protected Cache getCache() {
        return cacheManager.getCache("cyclos.cache.CustomFields." + customFieldType.getSimpleName());
    }

    protected Validator getValueValidator(final Collection<? extends CustomField> fields) {
        final Validator validator = new Validator();

        for (CustomField field : fields) {
            field = fetchService.fetch(field);
            final Property property = validator.property(field.getInternalName(), new CustomFieldRetrievingStrategy(field));
            property.displayName(field.getName());

            switch (field.getType()) {
                case BOOLEAN:
                    property.anyOf("true", "false");
                    break;
                case INTEGER:
                    property.add(new IntegerValidator());
                    break;
                case DATE:
                    property.add(new DateValidator());
                    break;
                case ENUMERATED:
                    property.add(new EnumeratedValidator());
                    break;
                case MEMBER:
                    property.add(new MemberValidator());
                    break;
                case DECIMAL:
                    property.add(new BigDecimalValidator());
                    break;
                case URL:
                    property.url(true);
                    break;
            }

            final Validation validation = field.getValidation();
            if (validation != null) {
                // Check required
                boolean ignoreRequired = false;
                if (field instanceof MemberCustomField) {
                    ServiceClient client = LoggedUser.serviceClient();
                    ignoreRequired = client != null && client.isIgnoreRegistrationValidations();
                }
                if (validation.isRequired() && !ignoreRequired) {
                    property.required();
                }
                // Check length constraint
                final RangeConstraint lengthConstraint = validation.getLengthConstraint();
                if (lengthConstraint != null) {
                    property.add(new LengthValidation(lengthConstraint));
                }
                // Check unique
                if (validation.isUnique()) {
                    property.add(new UniqueFieldValueValidation());
                }
                // Custom validator class
                if (StringUtils.isNotEmpty(validation.getValidatorClass())) {
                    final PropertyValidation validatorClass = customObjectHandler.get(validation.getValidatorClass());
                    property.add(validatorClass);
                }
                // Check that if enumerated, its value is consistent with the parent value
                if (field.getType() == Type.ENUMERATED && field.getParent() != null) {
                    property.add(new ParentValueValidation());
                }
            }
        }
        return validator;
    }

    /**
     * Should be implemented in order to list all fields like the given one. When fields are not dependent on other entities (like member / ads /
     * admin / loan group) should return all. For others, like member record, should return all fields in the same member record as the given field
     */
    protected abstract List<CF> list(CF field);

    /**
     * It locks the custom field value only if it's set as unique.
     * @param value
     */
    protected void lockUniqueFieldValue(final CustomField field, final CustomFieldValue value) {
        if (field.getValidation().isUnique()) {
            final Pair<Object, Object> pair = Pair.<Object, Object> of(field, value.getValue());
            if (!uniqueObjectHandler.tryAcquire(pair)) {
                throw new ValidationException(new UniqueError(field.getName()));
            }
        }
    }

    /**
     * Must be implemented in order to resolve additional fetch to be applied before fields are stored on the cache
     */
    protected Collection<? extends Relationship> resolveAdditionalFetch() {
        return Collections.emptySet();
    }

    @SuppressWarnings("unchecked")
    private void copyParentProperties(final CustomField parent, final CustomField child) {
        final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(parent);
        for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            final String name = propertyDescriptor.getName();
            final boolean isWritable = propertyDescriptor.getWriteMethod() != null;
            final boolean isReadable = propertyDescriptor.getReadMethod() != null;
            if (isReadable && isWritable && !EXCLUDED_PROPERTIES_FOR_DEPENDENT_FIELDS.contains(name)) {
                Object value = PropertyHelper.get(parent, name);
                if (value instanceof Collection) {
                    value = new ArrayList<Object>((Collection<Object>) value);
                }
                PropertyHelper.set(child, name, value);
            }
        }
    }

    private ValidationException createValidationException(final CustomField field) {
        final ValidationException vex = new ValidationException(field.getInternalName(), new InvalidError());
        vex.setDisplayNameByProperty(Collections.singletonMap(field.getInternalName(), field.getName()));
        return vex;
    }

    private Validator getPossibleValueValidator() {
        if (possibleValueNavigator == null) {
            final Validator validator = new Validator("customField.possibleValue");
            validator.property("field").required();
            validator.property("value").required().maxLength(255);
            possibleValueNavigator = validator;
        }
        return possibleValueNavigator;
    }

    private Validator getValidator() {
        if (validator == null) {
            // We use a separate variable name to avoid concurrency problems with 2 threads modifying the same reference
            Validator val = new Validator("customField");
            val.property("internalName").required().maxLength(50).add(new JavaIdentifierValidation()).add(new UniqueCustomFieldInternalNameValidation());
            val.property("name").required().maxLength(100);
            val.property("type").required();
            val.property("control").required();
            val.property("size").required();
            val.property("parent").add(new ParentValidator());
            val.property("validation.validatorClass").instanceOf(PropertyValidation.class);
            appendValidations(val);
            validator = val;
        }
        return validator;
    }

    /**
     * Loads a field, but only if it is of the expected type. Otherwise, throws an {@link EntityNotFoundException}
     */
    private CF loadChecked(final Long id) {
        CF field = customFieldDao.<CF> load(id, fetch);
        if (!customFieldType.isInstance(field)) {
            throw new EntityNotFoundException();
        }
        return field;
    }

    /**
     * Loads a possible value, fetching both parent and field relationships, but throws an {@link EntityNotFoundException} if the field is not of the
     * expected type
     */
    private CustomFieldPossibleValue loadCheckedPossibleValue(final Long id) {
        CustomFieldPossibleValue possibleValue = customFieldPossibleValueDao.load(id, CustomFieldPossibleValue.Relationships.PARENT, CustomFieldPossibleValue.Relationships.FIELD);
        if (!customFieldType.isInstance(possibleValue.getField())) {
            throw new EntityNotFoundException();
        }
        return possibleValue;
    }

    private CustomFieldPossibleValue loadPossibleValue(final CustomFieldValue v) {
        if (v.getPossibleValue() != null) {
            return v.getPossibleValue();
        } else if (StringUtils.isEmpty(v.getValue())) {
            return null;
        } else {
            return loadPossibleValue(v.getValue(), v.getField());
        }
    }

    private CustomFieldPossibleValue loadPossibleValue(final String str, final CustomField field) {
        CustomFieldPossibleValue possibleValue = null;
        boolean byValue = true;
        try {
            if (StringUtils.isNumeric(str)) {
                try {
                    possibleValue = customFieldPossibleValueDao.load(new Long(str));
                    if (field.equals(possibleValue.getField())) {
                        byValue = false;
                    }
                } catch (final EntityNotFoundException e) {
                    // Not found - try by value
                }
            }
            if (byValue) {
                // Try by value
                possibleValue = customFieldPossibleValueDao.load(field.getId(), str);
            }
        } catch (final EntityNotFoundException e) {
            possibleValue = null;
        }
        return possibleValue;
    }
}
