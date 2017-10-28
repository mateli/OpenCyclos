package nl.strohalm.cyclos.entities.accounts.external.filemapping;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class NumberFormatAttributeConverter extends StringValuedEnumAttributeConverter<FileMappingWithFields.NumberFormat> {
}
