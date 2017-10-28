package nl.strohalm.cyclos.entities;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class EntityTypeAttributeConverter extends StringValuedEnumAttributeConverter<IndexOperation.EntityType> {
}
