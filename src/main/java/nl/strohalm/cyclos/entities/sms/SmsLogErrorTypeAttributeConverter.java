package nl.strohalm.cyclos.entities.sms;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SmsLogErrorTypeAttributeConverter extends StringValuedEnumAttributeConverter<SmsLog.ErrorType> implements AttributeConverter<SmsLog.ErrorType, String> {
}
