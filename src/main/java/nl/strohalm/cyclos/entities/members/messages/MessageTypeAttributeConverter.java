package nl.strohalm.cyclos.entities.members.messages;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.groups.BasicGroupSettings;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class MessageTypeAttributeConverter extends StringValuedEnumAttributeConverter<Message.Type> {
}
