package nl.strohalm.cyclos.entities.converters;

import nl.strohalm.cyclos.entities.Application;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PasswordHashAttributeConverter extends StringValuedEnumAttributeConverter<Application.PasswordHash>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<Application.PasswordHash, String> {
}
