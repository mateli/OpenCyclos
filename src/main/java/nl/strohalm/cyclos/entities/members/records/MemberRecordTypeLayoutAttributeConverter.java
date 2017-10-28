package nl.strohalm.cyclos.entities.members.records;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;
import nl.strohalm.cyclos.entities.members.messages.Message;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemberRecordTypeLayoutAttributeConverter extends StringValuedEnumAttributeConverter<MemberRecordType.Layout> {
}
