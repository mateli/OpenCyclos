package nl.strohalm.cyclos.entities.sms;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.settings.Setting;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class SmsLogErrorTypeAttributeConverter extends StringValuedEnumAttributeConverter<SmsLog.ErrorType> {
}
