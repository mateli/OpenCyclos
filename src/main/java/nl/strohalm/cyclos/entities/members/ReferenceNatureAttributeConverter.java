package nl.strohalm.cyclos.entities.members;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ReferenceNatureAttributeConverter extends StringValuedEnumAttributeConverter<Reference.Nature> implements AttributeConverter<Reference.Nature, String> {
}
