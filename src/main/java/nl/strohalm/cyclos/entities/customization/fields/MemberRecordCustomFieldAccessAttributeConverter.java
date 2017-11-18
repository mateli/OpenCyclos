package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemberRecordCustomFieldAccessAttributeConverter extends StringValuedEnumAttributeConverter<MemberRecordCustomField.Access> implements AttributeConverter<MemberRecordCustomField.Access, String> {
}
