package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OperatorCustomFieldVisibilityAttributeConverter extends StringValuedEnumAttributeConverter<OperatorCustomField.Visibility> implements AttributeConverter<OperatorCustomField.Visibility, String> {
}
