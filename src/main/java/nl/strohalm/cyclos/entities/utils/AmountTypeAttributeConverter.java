package nl.strohalm.cyclos.entities.utils;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class AmountTypeAttributeConverter extends StringValuedEnumAttributeConverter<Amount.Type> {
}
