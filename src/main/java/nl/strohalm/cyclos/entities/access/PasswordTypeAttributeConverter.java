package nl.strohalm.cyclos.entities.access;

import nl.strohalm.cyclos.entities.access.PasswordHistoryLog;
import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PasswordTypeAttributeConverter extends StringValuedEnumAttributeConverter<PasswordHistoryLog.PasswordType> {
}
