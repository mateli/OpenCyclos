package nl.strohalm.cyclos.entities;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EntityTypeAttributeConverter extends StringValuedEnumAttributeConverter<IndexOperation.EntityType>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<IndexOperation.EntityType, String> {
}
