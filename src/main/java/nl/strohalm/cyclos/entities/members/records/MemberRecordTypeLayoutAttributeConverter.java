package nl.strohalm.cyclos.entities.members.records;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemberRecordTypeLayoutAttributeConverter extends StringValuedEnumAttributeConverter<MemberRecordType.Layout> implements AttributeConverter<MemberRecordType.Layout, String> {
}
