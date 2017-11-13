package nl.strohalm.cyclos.entities.access;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PasswordTypeAttributeConverter extends StringValuedEnumAttributeConverter<PasswordHistoryLog.PasswordType>
    /* https://bugs.eclipse.org/bugs/show_bug.cgi?id=415296 */ implements AttributeConverter<PasswordHistoryLog.PasswordType, String> {
}
