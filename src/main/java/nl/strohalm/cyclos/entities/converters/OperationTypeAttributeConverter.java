package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.entities.IndexOperation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OperationTypeAttributeConverter extends StringValuedEnumAttributeConverter<IndexOperation.OperationType> implements AttributeConverter<IndexOperation.OperationType, String> {
}
