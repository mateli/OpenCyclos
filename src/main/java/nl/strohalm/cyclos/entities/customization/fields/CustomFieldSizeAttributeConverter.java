package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomFieldSizeAttributeConverter extends StringValuedEnumAttributeConverter<CustomField.Size> implements AttributeConverter<CustomField.Size, String> {
}
