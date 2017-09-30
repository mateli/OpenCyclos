package nl.strohalm.cyclos.entities.access;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PrincipalAttributeConverter extends StringValuedEnumAttributeConverter<Channel.Principal> {
}
