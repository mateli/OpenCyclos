package nl.strohalm.cyclos.entities.members.messages;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MessageTypeAttributeConverter extends StringValuedEnumAttributeConverter<Message.Type> implements AttributeConverter<Message.Type, String> {
}
