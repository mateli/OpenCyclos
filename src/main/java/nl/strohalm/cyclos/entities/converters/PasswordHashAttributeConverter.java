package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.entities.Application;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PasswordHashAttributeConverter extends StringValuedEnumAttributeConverter<Application.PasswordHash> {
}
