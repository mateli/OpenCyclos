package nl.strohalm.cyclos.entities.customization.files;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomizedFileTypeAttributeConverter extends StringValuedEnumAttributeConverter<CustomizedFile.Type> implements AttributeConverter<CustomizedFile.Type, String> {
}
