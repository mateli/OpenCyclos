package nl.strohalm.cyclos.entities.members.imports;

import nl.strohalm.cyclos.entities.converters.StringValuedEnumAttributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImportedMemberStatusAttributeConverter extends StringValuedEnumAttributeConverter<ImportedMember.Status> implements AttributeConverter<ImportedMember.Status, String> {
}
