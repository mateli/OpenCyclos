package nl.strohalm.cyclos.entities.access;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CredentialsAttributeConverter extends StringValuedEnumAttributeConverter<Channel.Credentials> {
}
