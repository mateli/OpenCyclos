package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.customization.documents.Document;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomFieldNatureAttributeConverter extends StringValuedEnumAttributeConverter<CustomField.Nature> {
}
