package nl.strohalm.cyclos.entities.members;

import nl.strohalm.cyclos.entities.converters.IntValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ReferenceLevelAttributeConverter extends IntValuedEnumAttributeConverter<Reference.Level> {
}
