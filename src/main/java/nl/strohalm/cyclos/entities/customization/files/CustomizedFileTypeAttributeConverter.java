package nl.strohalm.cyclos.entities.customization.files;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.customization.documents.Document;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomizedFileTypeAttributeConverter extends StringValuedEnumAttributeConverter<CustomizedFile.Type> {
}
