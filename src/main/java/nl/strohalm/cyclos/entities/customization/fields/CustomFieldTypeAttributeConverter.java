package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomFieldTypeAttributeConverter extends StringValuedEnumAttributeConverter<CustomField.Type> {
}
